package com.TaskManagementToolB_Security;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.TaskManagementToolB_Entity.UserAuth;
import com.TaskManagementToolB_Enum.Permission;
//import com.TaskmanagementToolB_Repository.UserAuthRepository;
import com.TaskManagementToolB_Repositorye.UserAuthRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserAuthRepository userRepo;

    // 🔑 REQUIRED by Spring Security
    @Override
    public UserDetails loadUserByUsername(String userOfficialEmail) throws UsernameNotFoundException {

        UserAuth user = userRepo.findByUserOfficialEmail(userOfficialEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Convert permissions → authorities
        Set<Permission> perms = RolePermissionConfig
                .getRoleBasedPermission()
                .get(user.getRole());

        List<SimpleGrantedAuthority> authorities =
                (perms == null ? List.of()
                        : perms.stream()
                               .map(p -> new SimpleGrantedAuthority(p.name()))
                               .collect(Collectors.toList()));

        return new org.springframework.security.core.userdetails.User(
                user.getUserOfficialEmail(),
                user.getPassword(),
                authorities
        );
    }

    // ✅ Optional helper (for your JWT filter)
    public UserDetails loadUserByEmail(String email) {
        return loadUserByUsername(email);
    }
}