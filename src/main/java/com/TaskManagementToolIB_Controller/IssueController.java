package com.TaskManagementToolIB_Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TaskManagemenToolB_Services.IssueService;
import com.TaskManagementToolB_Entity.Issue;
import com.TaskManagementToolB_Entity.IssueComment;
import com.TaskManagementToolB_Entity.Sprint;
import com.TaskManagementToolB_Enum.IssueStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {

    @Autowired
    private IssueService issueService;

    // Create Issue
    @PostMapping("/create")
    public ResponseEntity<String> createIssue(@RequestBody Issue issue) {

        issueService.createIssue(issue);

        return ResponseEntity.ok("Task created successfully");
    }

    // Get Issues By Assignee Email
    @GetMapping("/assignee/{userEmail}")
    public ResponseEntity<List<Issue>> getIssueByAssigneeEmail(
            @PathVariable String userEmail) {

        return ResponseEntity.ok(
                issueService.findIssueByAssigneeEmail(userEmail));
    }

    // Get Issue By Id
    @GetMapping("/id/{id}")
    public ResponseEntity<Issue> getIssueById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                issueService.findIssueById(id));
    }

    // Update Issue Status
    @PutMapping("/{id}/status")
    public ResponseEntity<Issue> updateIssueStatus(
            @PathVariable Long id,
            @RequestParam IssueStatus issueStatus) {

        return ResponseEntity.ok(
                issueService.updateIssueStatus(id, issueStatus));
    }

    // Add Comment
    @PostMapping("/addComments/{issueId}")
    public ResponseEntity<IssueComment> addComments(
            @PathVariable Long issueId,
            @RequestParam String authorEmail,
            @RequestBody String body) {

        return ResponseEntity.ok(
                issueService.addComment(issueId, authorEmail, body));
    }

    // Create Sprint
    @PostMapping("/sprint")
    public ResponseEntity<Sprint> createSprint(
            @RequestBody Sprint sprint) {

        return ResponseEntity.ok(
                issueService.createSprint(sprint));
    }

    // Get Issues By Sprint
    @GetMapping("/sprint/{sprintId}")
    public ResponseEntity<List<Issue>> getIssueBySprint(
            @PathVariable Long sprintId) {

        return ResponseEntity.ok(
                issueService.findIssueBySprint(sprintId));
    }

    // Search Issues
    @PostMapping("/search")
    public ResponseEntity<List<Issue>> search(
            @RequestBody Map<String, String> filters) {

        return ResponseEntity.ok(
                issueService.search(filters));
    }
}