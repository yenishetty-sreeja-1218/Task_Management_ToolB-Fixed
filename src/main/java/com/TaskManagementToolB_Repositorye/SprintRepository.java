package com.TaskManagementToolB_Repositorye;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagementToolB_Entity.Sprint;
import com.TaskManagementToolB_Enum.SprintState;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {

    List<Sprint> findByProjectId(Long projectId);

    List<Sprint> findBySprintState(SprintState sprintState);
}