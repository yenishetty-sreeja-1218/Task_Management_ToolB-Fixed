package com.TaskManagementToolB_Repositorye;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagementToolB_Entity.IssueComment;

@Repository
public interface IssueCommentRepository extends JpaRepository<IssueComment,Long> {
	List<IssueComment>findByissueIdOrderdByCretedAsAsc(Long issueId); 

}
