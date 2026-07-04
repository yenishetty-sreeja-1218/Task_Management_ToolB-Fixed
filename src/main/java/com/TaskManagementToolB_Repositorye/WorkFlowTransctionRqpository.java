package com.TaskManagementToolB_Repositorye;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagementToolB_Entity.WorkflowTransaction;

@Repository
public interface WorkFlowTransctionRqpository extends JpaRepository<WorkflowTransaction, Long> {

}