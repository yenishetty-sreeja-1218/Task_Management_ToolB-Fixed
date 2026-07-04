package com.TaskManagementToolB_Entity;

import java.time.LocalDateTime;

import com.TaskManagementToolB_Enum.IssuePriority;
import com.TaskManagementToolB_Enum.IssueStatus;
import com.TaskManagementToolB_Enum.IssueType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="issue")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Issue {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true,nullable=false)
	private String issueKey;
	
	@Column(nullable=false)
	private String issueTitle;
	
	@Column(length = 2000)
	private String issueDescriptions;
	
	@Enumerated(EnumType.STRING)
	private IssueType issueType;
	
	@Enumerated(EnumType.STRING)
	private IssuePriority priority;
	
	@Enumerated(EnumType.STRING)
	private IssueStatus issueStatus;
	
	private String assigneeEmail;
	private String reporterEmail;
	
	private Long epicId;
	private Long sprintId;
	
	private LocalDateTime createdAt= LocalDateTime.now();
	private LocalDateTime updatedAt=LocalDateTime.now();
	
	private LocalDateTime dueDate;

	private int positionInOrder;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIssueKey() {
		return issueKey;
	}

	public void setIssueKey(String issueKey) {
		this.issueKey = issueKey;
	}

	public String getIssueTitle() {
		return issueTitle;
	}

	public void setIssueTitle(String issueTitle) {
		this.issueTitle = issueTitle;
	}

	public String getIssueDescriptions() {
		return issueDescriptions;
	}

	public void setIssueDescriptions(String issueDescriptions) {
		this.issueDescriptions = issueDescriptions;
	}

	public IssueType getIssueType() {
		return issueType;
	}

	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}

	public IssuePriority getPriority() {
		return priority;
	}

	public void setPriority(IssuePriority priority) {
		this.priority = priority;
	}

	public IssueStatus getIssueStatus() {
		return issueStatus;
	}

	public void setIssueStatus(IssueStatus issueStatus) {
		this.issueStatus = issueStatus;
	}

	public String getAssigneeEmail() {
		return assigneeEmail;
	}

	public void setAssigneeEmail(String assigneeEmail) {
		this.assigneeEmail = assigneeEmail;
	}

	public String getReporterEmail() {
		return reporterEmail;
	}

	public void setReporterEmail(String reporterEmail) {
		this.reporterEmail = reporterEmail;
	}

	public Long getEpicId() {
		return epicId;
	}

	public void setEpicId(Long epicId) {
		this.epicId = epicId;
	}

	public Long getSprintId() {
		return sprintId;
	}

	public void setSprintId(Long sprintId) {
		this.sprintId = sprintId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}
	// Set backlog position using int
	public void setBackLogPosition(int position) {
	    this.setPositionInOrder(position);
	}

	// Overload for Object (not really needed, but if you want to keep it)
	public void setBackLogPosition(Object position) {
	    if (position instanceof Integer) {
	        this.setPositionInOrder((Integer) position);
	    } else {
	        throw new IllegalArgumentException("Backlog position must be an Integer");
	    }
	}

	// Get parent issue ID
	public Long getParentIssueId() {
	    return this.getParentIssueId();
	}

	public int getPositionInOrder() {
		return positionInOrder;
	}

	public void setPositionInOrder(int positionInOrder) {
		this.positionInOrder = positionInOrder;
	}

	
	
	
	
	

}