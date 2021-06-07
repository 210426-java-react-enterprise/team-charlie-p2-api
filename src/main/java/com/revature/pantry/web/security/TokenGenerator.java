package com.revature.pantry.web.security;

import com.revature.pantry.models.User;
import com.revature.pantry.web.security.JwtConfig;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenGenerator {

    private JwtConfig jwtConfig;

    @Autowired
    public TokenGenerator(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String createJwt(User subject) {

        long now = System.currentTimeMillis();

        JwtBuilder builder = Jwts.builder()
                .setId(Integer.toString(subject.getId()))
                .setSubject(subject.getUsername())
                .setIssuer("revature")
                .claim("role", subject.getRole())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration()))
                .signWith(jwtConfig.getSigAlg(), jwtConfig.getSigningKey());

        return jwtConfig.getPrefix() + builder.compact();

    }

    public JwtConfig getJwtConfig() {
        return jwtConfig;
    }
}