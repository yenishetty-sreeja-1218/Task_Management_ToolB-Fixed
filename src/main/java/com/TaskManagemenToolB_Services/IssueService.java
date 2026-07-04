package com.TaskManagemenToolB_Services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManagementToolB_Entity.Issue;
import com.TaskManagementToolB_Entity.IssueComment;
import com.TaskManagementToolB_Entity.Sprint;
import com.TaskManagementToolB_Enum.IssuePriority;
import com.TaskManagementToolB_Enum.IssueStatus;
import com.TaskManagementToolB_Enum.IssueType;
import com.TaskManagementToolB_Enum.SprintState;
import com.TaskManagementToolB_Repositorye.IssueCommentRepository;
//import com.TaskmanagementToolB_Repository.IssueCommentRepository;
//import com.TaskmanagementToolB_Repository.IssueRepository;
//import com.TaskmanagementToolB_Repository.SprintRepository;
import com.TaskManagementToolB_Repositorye.IssueRepository;
import com.TaskManagementToolB_Repositorye.SprintRepository;

import jakarta.transaction.Transactional;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepo;

    @Autowired
    private IssueCommentRepository issueCommentRepo;

    @Autowired
    private SprintRepository sprintRepo;

    // Generate Issue Key
    private String generateKey(Long id) {
        return "PROJ-" + id;
    }

    // Create Issue
    @Transactional
    public Issue createIssue(Issue issue) {

        issue.setIssueType(
                issue.getIssueType() != null
                        ? issue.getIssueType()
                        : IssueType.TASK);

        issue.setPriority(
                issue.getPriority() != null
                        ? issue.getPriority()
                        : IssuePriority.MEDIUM);

        issue.setIssueStatus(IssueStatus.OPEN);

        Issue saved = issueRepo.save(issue);

        saved.setIssueKey(generateKey(saved.getId()));

        return issueRepo.save(saved);
    }

    
    // Find By Assignee
    public List<Issue> findIssueByAssigneeEmail(String userOfficialEmail) {
        return issueRepo.findByAssigneeEmail(userOfficialEmail);
    }

    // Find By Id
    public Issue findIssueById(Long id) {

        return issueRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
    }

    // Find Issues By Sprint
    public List<Issue> findIssueBySprint(Long sprintId) {
        return issueRepo.findBySprintId(sprintId);
    }

    // Update Status
    @Transactional
    public Issue updateIssueStatus(Long id, IssueStatus issueStatus) {

        Issue issue = findIssueById(id);

        if (issueStatus == null) {
            throw new RuntimeException("Status cannot be null");
        }

        issue.setIssueStatus(issueStatus);

        return issueRepo.save(issue);
    }

    // Add Comment
    @Transactional
    public IssueComment addComment(Long issueId,
                                   String authorEmail,
                                   String body) {

        Issue issue = findIssueById(issueId);

        IssueComment comment = new IssueComment();

        comment.setIssueId(issue.getId());
        comment.setAuthorEmail(authorEmail);
        comment.setBody(body);

        return issueCommentRepo.save(comment);
    }

    // Create Sprint
    @Transactional
    public Sprint createSprint(Sprint sprint) {

        if (sprint.getSprintstate() == null) {
            sprint.setSprintstate(SprintState.PLANNED);
        }

        return sprintRepo.save(sprint);
    }

    // Search Filters
    public List<Issue> search(Map<String, String> filters) {

        List<Issue> issues = issueRepo.findAll();

        // Filter By Assignee Email
        if (filters.containsKey("assigneeEmail")) {

            String email = filters.get("assigneeEmail");

            issues = issues.stream()
                    .filter(i -> email.equalsIgnoreCase(i.getAssigneeEmail()))
                    .collect(Collectors.toList());
        }

        // Filter By Status
        if (filters.containsKey("issueStatus")) {

            try {

                IssueStatus status =
                        IssueStatus.valueOf(
                                filters.get("issueStatus").toUpperCase());

                issues = issues.stream()
                        .filter(i -> i.getIssueStatus() == status)
                        .collect(Collectors.toList());

            } catch (Exception e) {

                throw new RuntimeException("Invalid status in filter");
            }
        }

        // Filter By Sprint
        if (filters.containsKey("sprint")) {

            Long sprintId =
                    Long.valueOf(filters.get("sprint"));

            issues = issues.stream()
                    .filter(i -> i.getSprintId().equals(sprintId))
                    .collect(Collectors.toList());
        }

        return issues;
    }
}