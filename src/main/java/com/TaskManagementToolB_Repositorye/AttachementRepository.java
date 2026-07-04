package com.TaskManagementToolB_Repositorye;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagementToolB_Entity.Attachement;

@Repository
public interface AttachementRepository
        extends JpaRepository<Attachement, Long> {

    List<Attachement> findByIssueId(Long issueId);
}