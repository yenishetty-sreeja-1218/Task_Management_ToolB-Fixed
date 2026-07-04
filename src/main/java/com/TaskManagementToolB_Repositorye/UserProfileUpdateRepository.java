package com.TaskManagementToolB_Repositorye;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagementToolB_Entity.UserProfileUpdate;

@Repository
public interface UserProfileUpdateRepository
        extends JpaRepository<UserProfileUpdate, Long> {

    Optional<UserProfileUpdate> findByUserEmail(String userEmail);

}
