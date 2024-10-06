package com.example.jwt;

import com.example.entity.User;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TokenProvider {

    @Value("${jwt.token.secret}")
    private String jwtSecret = "test";

    @Value("${jwt.token.expired}")
    private long jwtExpirationInMs = 3600000;

    public String generateToken(Long id, String username, String password, Set<User.Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", id);
        claims.put("roles", getUserRoleNamesFromJWT(roles));
        claims.put("password", password);

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        User user = new User();
        user.setId(getUserIdFromJWT(token));
        user.setUsername(getUserUsernameFromJWT(token));
        user.setPassword(getUserPasswordFromJWT(token));
        user.setRoles(getUserRolesFromJWT(token));
        return new UsernamePasswordAuthenticationToken(user, token, user.getRoles());
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUserUsernameFromJWT(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public Long getUserIdFromJWT(String token) {
        Integer i = (Integer) Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("id");
        return Long.valueOf(i);
    }

    public String getUserPasswordFromJWT(String token) {
        return (String) Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("password");
    }

    public Set<User.Role> getUserRolesFromJWT(String token) {
        List<String> roles = (List<String>) Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("roles");
        return getUserRoleNamesFromJWT(roles);
    }

    private Set<String> getUserRoleNamesFromJWT(Set<User.Role> roles) {
        Set<String> result = new HashSet<>();
        roles.forEach(role -> result.add(role.getAuthority()));
        return result;
    }

    private Set<User.Role> getUserRoleNamesFromJWT(List<String> roles) {
        Set<User.Role> result = new HashSet<>();
        roles.forEach(s -> result.add(User.Role.valueOf(s)));
        return result;
    }

}
