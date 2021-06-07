package com.revature.pantry.util;

import com.revature.pantry.web.security.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final JwtConfig jwtConfig;

    @Autowired
    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String getUsernameFromToken(String token) {
        token = token.replaceAll(jwtConfig.getPrefix(), "");

        Claims jwtClaims = Jwts.parser()
                .setSigningKey(jwtConfig.getSigningKey())
                .parseClaimsJws(token)
                .getBody();

        return jwtClaims.getSubject();
    }
}
