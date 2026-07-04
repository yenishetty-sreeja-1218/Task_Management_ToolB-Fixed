package com.TaskManagementToolB_Repositorye;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.TaskManagementToolB_Entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByProjectKey(String projectKey);
}
