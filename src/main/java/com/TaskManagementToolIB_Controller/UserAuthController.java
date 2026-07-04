package com.TaskManagementToolIB_Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManagemenToolB_Services.UserAuthService;
import com.TaskManagementToolB_DTO.AuthResponseDTO;
import com.TaskManagementToolB_DTO.LoginRequestDTO;
import com.TaskManagementToolB_DTO.RegisterRequestDTO;

//import com.TaskManagementTool_b72.DTO.AuthResponseDTO;
//import com.TaskManagementTool_b72.DTO.LoginRequestDTO;
//import com.TaskManagementTool_b72.DTO.RegisterRequestDTO;

//import TaskManagementTool_b72.Service.UserAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user_auth")
@RequiredArgsConstructor
public class UserAuthController {
	
	@Autowired
	private UserAuthService userService;
	
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponseDTO>register(@RequestBody RegisterRequestDTO register){
		return ResponseEntity.ok(userService.register(register));
	}

	@PostMapping("/login")
	public ResponseEntity<String>login(@RequestBody LoginRequestDTO login){
		userService.login(login);
		return ResponseEntity.ok("Login successful");
	}
	
	@PostMapping("/forgot_password")
	public ResponseEntity<String>forgotPasswod(@RequestParam String email){
		userService.forgotPassword(email);
		return ResponseEntity.ok("Reset password-Email sent overyour email");
	}
	
	@PostMapping("/reset_password")
	public ResponseEntity<String>resetPassword(@PathVariable String token,@PathVariable String newPassword){
		userService.resetPassword(token,newPassword );
		return ResponseEntity.ok("Password reset successful");
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String>logout(HttpServletRequest request){
		return ResponseEntity.ok(userService.logout(request));
	}
}

