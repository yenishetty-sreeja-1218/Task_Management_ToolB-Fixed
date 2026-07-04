package com.TaskManagementToolB_DTO;

import com.TaskManagementToolB_Enum.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {
	
	public String userName;
	public String userOfficcialEmail;
	public String password;
	public Role role;
	

}
