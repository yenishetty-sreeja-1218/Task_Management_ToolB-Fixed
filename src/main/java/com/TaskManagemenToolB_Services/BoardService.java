package com.TaskManagemenToolB_Services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManagementToolB_Entity.Board;
import com.TaskManagementToolB_Entity.BoardCards;
import com.TaskManagementToolB_Entity.BoardColumn;
import com.TaskManagementToolB_Entity.Issue;
import com.TaskManagementToolB_Repositorye.BoardCardRepository;
import com.TaskManagementToolB_Repositorye.BoardColumnRepository;
//import com.TaskmanagementToolB_Repository.BoardCardRepository;
//import com.TaskmanagementToolB_Repository.BoardColumnRepository;
//import com.TaskmanagementToolB_Repository.BoardRepository;
//import com.TaskmanagementToolB_Repository.IssueRepository;
import com.TaskManagementToolB_Repositorye.BoardRepository;
import com.TaskManagementToolB_Repositorye.IssueRepository;

import jakarta.transaction.Transactional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepo;

    @Autowired
    private BoardColumnRepository boardColumnRepo;

    @Autowired
    private BoardCardRepository boardCardRepo;

    @Autowired
    private IssueRepository issueRepo;

    // Create a new board
    public Board createBoard(Board board) {
        return boardRepo.save(board);
    }

    // Find board by ID
    public Board findById(Long id) {
        return boardRepo.findById(id).orElseThrow(() -> new RuntimeException("Board not found with id: " + id));
    }

    // Get all columns for a board
    public List<BoardColumn> getColumn(Long boardId) {
        return boardColumnRepo.findByBoardIdOrderByPositionInOrd(boardId);
    }

    // Get all cards in a column
    public List<BoardCards> getCardsAndColumn(Long boardId, Long columnId) {
        return boardCardRepo.findByBoardIdAndColumnIdOrderByPositionInOrd(boardId, columnId);
    }

    // Add an issue to a board column
    @Transactional
    public BoardCards addIssueToBoard(Long boardId, Long columnId, Long issueId) {
        Issue issue = issueRepo.findById(issueId).orElseThrow(() -> new RuntimeException("Issue not found with id: " + issueId));

        // Remove existing card for this issue if present
        boardCardRepo.findByIssueId(issueId).ifPresent(boardCardRepo::delete);

        BoardColumn column = boardColumnRepo.findById(columnId).orElseThrow(() -> new RuntimeException("Column not found with id: " + columnId));

        // Enforce WIP limit
        if (column.getWipLimit() != null && column.getWipLimit() > 0) {
            long count = boardCardRepo.countByBoardIdAndColumnId(boardId, columnId);
            if (count >= column.getWipLimit()) {
                throw new RuntimeException("WIP limit reached for column: " + column.getName());
            }
        }

        // Position card at end of column
        List<BoardCards> existing = boardCardRepo.findByBoardIdAndColumnIdOrderByPositionInOrd(boardId, columnId);
        int position = existing.size();

        BoardCards card = new BoardCards();
        card.setBoardId(boardId);
        card.setColumn(column);
        card.setIssueId(issueId);
        card.setPositionInOrd(position);
        boardCardRepo.save(card);

        // Update issue status based on column
        if (column.getStatusKey() != null) {
            issue.setIssueStatus(column.getStatusKey());
            issueRepo.save(issue);
        }

        return card;
    }

    // Move a card between columns
    @Transactional
    public void moveCard(Long boardId, Long cardId, Long columnId, int toPosition, String performBy) {
        BoardCards card = boardCardRepo.findById(cardId).orElseThrow(() -> new RuntimeException("Card not found with id: " + cardId));
        BoardColumn from = card.getColumn();
        BoardColumn to = boardColumnRepo.findById(columnId).orElseThrow(() -> new RuntimeException("Column not found with id: " + columnId));

        // Enforce WIP limit
        if (to.getWipLimit() != null && to.getWipLimit() > 0) {
            long count = boardCardRepo.countByBoardIdAndColumnId(boardId, columnId);
            if (!Objects.equals(from.getId(), to.getId()) && count >= to.getWipLimit()) {
                throw new RuntimeException("WIP limit exceeded for column: " + to.getName());
            }
        }

        // Adjust positions in source column
        List<BoardCards> fromList = boardCardRepo.findByBoardIdAndColumnIdOrderByPositionInOrd(boardId, from.getId());
        for (BoardCards c : fromList) {
            if (c.getPositionInOrd() > card.getPositionInOrd()) {
                c.setPositionInOrd(c.getPositionInOrd() - 1);
                boardCardRepo.save(c);
            }
        }

        // Adjust positions in destination column
        List<BoardCards> toList = boardCardRepo.findByBoardIdAndColumnIdOrderByPositionInOrd(boardId, to.getId());
        for (BoardCards c : toList) {
            if (c.getPositionInOrd() >= toPosition) {
                c.setPositionInOrd(c.getPositionInOrd() + 1);
                boardCardRepo.save(c);
            }
        }

        // Move card
        card.setColumn(to);
        card.setPositionInOrd(toPosition);
        boardCardRepo.save(card);

        // Update issue status
        issueRepo.findById(card.getIssueId()).ifPresent(issue -> {
            if (to.getStatusKey() != null) {
                issue.setIssueStatus(to.getStatusKey());
                issueRepo.save(issue);
            }
        });
    }

    // Reorder cards in a column
    @Transactional
    public void recordColumn(Long boardId, Long columnId, List<Long> orderedCardIds) {
        int pos = 0;
        for (Long cardId : orderedCardIds) {
            BoardCards card = boardCardRepo.findById(cardId).orElseThrow(() -> new RuntimeException("Card not found with id: " + cardId));
            card.setPositionInOrd(pos++);
            boardCardRepo.save(card);
        }
    }

    @Transactional
    public void startSprint(Long sprintId) {
        // TODO: implement sprint start logic
    }

    @Transactional
    public void closeSprint(Long sprintId) {
        // TODO: implement sprint close logic
    }
}
