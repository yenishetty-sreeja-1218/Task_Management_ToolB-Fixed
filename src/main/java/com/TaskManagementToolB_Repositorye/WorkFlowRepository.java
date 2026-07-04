package com.TaskManagementToolB_Repositorye;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagementToolB_Entity.Workflow;

@Repository
public interface WorkFlowRepository extends JpaRepository<Workflow, Long> {

    

}
