package com.TaskManagementToolB_Repositorye;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagementToolB_Entity.UserAuth;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth,Long> {
	
	Optional<UserAuth>findByUserOfficialEmail(String userOfficilEmail);
	Optional<UserAuth>findByResetToken(String resetToken);


}

