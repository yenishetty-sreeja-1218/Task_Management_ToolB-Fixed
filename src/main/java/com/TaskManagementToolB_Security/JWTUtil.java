package com.TaskManagementToolB_Security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.TaskManagementToolB_Entity.UserAuth;
import com.TaskManagementToolB_Enum.Permission;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

    private final Key key;
    private final long validateToken = 1000L * 60 * 60 * 12; // 12 hours

    public JWTUtil() {

        String secret = System.getenv("JWT_SECRET");

        if (secret == null || secret.length() < 32) {
            // Strong fallback key (IMPORTANT)
            secret = "ThisIsASecretKeyForJWTAuthentication12345";
        }

        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // 🔐 Generate Token
    public String generateToken(UserAuth user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());

        // Add permissions into token
        Set<Permission> perms = RolePermissionConfig
                .getRoleBasedPermission()
                .get(user.getRole());

        if (perms != null) {
            claims.put("permissions",
                    perms.stream()
                         .map(Enum::name)
                         .collect(Collectors.toList()));
        }

        Date now = new Date();
        Date expire = new Date(now.getTime() + validateToken);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUserOfficialEmail())
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Validate Token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // 📦 Get Claims
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 📧 Extract Email
    public String getUserEmail(String token) {
        return getClaims(token).getSubject();
    }

    // 🔑 Extract Token from Header
    public String extractToken(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}