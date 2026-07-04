package com.TaskManagementToolB_Repositorye;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagementToolB_Entity.Issue;
import com.TaskManagementToolB_Enum.IssueStatus;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    Optional<Issue> findByIssueKey(String issueKey);

    List<Issue> findByAssigneeEmail(String assigneeEmail);

    List<Issue> findByIssueStatus(IssueStatus issueStatus);

    List<Issue> findBySprintId(Long sprintId);

	List<Issue> findByProjectIdAndSprintIsIsNullOrderedByBackLogPosition(Long projectId);

	List<Issue> findByProjectIdAndSprintIsIsNullOrderedByBackLogPosition1(Long projectId);
}