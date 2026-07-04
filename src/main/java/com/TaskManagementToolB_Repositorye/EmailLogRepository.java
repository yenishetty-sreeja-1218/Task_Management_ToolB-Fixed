package com.TaskManagementToolB_Repositorye;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagementToolB_Entity.EmailLog;

@Repository
public interface EmailLogRepository extends JpaRepository<EmailLog,Long>{
	

}