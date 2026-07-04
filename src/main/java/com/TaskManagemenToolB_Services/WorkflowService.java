package com.TaskManagemenToolB_Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TaskManagementToolB_Entity.Workflow;
import com.TaskManagementToolB_Entity.WorkflowTransaction;
//import com.TaskmanagementToolB_Repository.WorkFlowRepository;
//import com.TaskmanagementToolB_Repository.WorkFlowTransctionRqpository;
import com.TaskManagementToolB_Repositorye.WorkFlowRepository;
import com.TaskManagementToolB_Repositorye.WorkFlowTransctionRqpository;

@Service
public class WorkflowService {

    @Autowired
    private WorkFlowRepository workflowRepo;

    @Autowired
    private WorkFlowTransctionRqpository workflowTransactionRepo;

    public WorkFlowRepository getWorkflowRepo() {
        return workflowRepo;
    }

    public void setWorkflowRepo(WorkFlowRepository workflowRepo) {
        this.workflowRepo = workflowRepo;
    }

    public WorkFlowTransctionRqpository getWorkflowTransactionRepo() {
        return workflowTransactionRepo;
    }

    public void setWorkflowTransactionRepo(WorkFlowTransctionRqpository workflowTransactionRepo) {
        this.workflowTransactionRepo = workflowTransactionRepo;
    }

    @Transactional
    public Workflow createWorkflow(Workflow wf) {
        if (wf.getTransactions() != null) {
            wf.getTransactions().forEach(t -> t.setWorkflow(wf));
        }
        return workflowRepo.save(wf);
    }

    @Transactional(readOnly = true)
    public Workflow getWorkflowById(Long id) {
        return workflowRepo.findById(id).orElse(null);
    }

    @Transactional
    public WorkflowTransaction addTransaction(Long workflowId, WorkflowTransaction transaction) {

        Workflow wf = workflowRepo.findById(workflowId)
                .orElseThrow(() -> new RuntimeException("Workflow not found"));

        transaction.setWorkflow(wf);

        return workflowTransactionRepo.save(transaction);
    }
}