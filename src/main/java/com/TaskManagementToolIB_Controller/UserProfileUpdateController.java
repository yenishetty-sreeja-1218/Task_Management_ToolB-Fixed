package com.TaskManagementToolIB_Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManagemenToolB_Services.UserProfileUpdateService;
import com.TaskManagementToolB_DTO.UserProfileUpdateDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user_profile_update")
@RequiredArgsConstructor
public class UserProfileUpdateController {

    @Autowired
    private UserProfileUpdateService userProfileUpdateService;


    // CREATE USER PROFILE
    @PostMapping("/create")
    public ResponseEntity<UserProfileUpdateDTO> updateUserProfile(
            @RequestBody UserProfileUpdateDTO userProfileUpdate) {

        return ResponseEntity.ok(
                userProfileUpdateService.updateUserProfile(userProfileUpdate));
    }


    // GET USER PROFILE BY EMAIL
    @GetMapping("/{userEmail}")
    public ResponseEntity<UserProfileUpdateDTO> getUserProfileByEmail(
            @PathVariable String userEmail) {

        return ResponseEntity.ok(
                userProfileUpdateService.getProfileByEmail(userEmail));
    }


    // GET ALL USER PROFILES
    @GetMapping("/all")
    public ResponseEntity<List<UserProfileUpdateDTO>> getAllUserProfile() {

        return ResponseEntity.ok(
                userProfileUpdateService.getAllProfile());
    }
}