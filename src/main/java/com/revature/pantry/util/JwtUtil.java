package com.revature.pantry.util;

import com.revature.pantry.exceptions.AuthenticationException;
import com.revature.pantry.web.security.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JwtUtil
 *
 * A utility class for dealing with tokens. Currently only used to extract the username from the token, but this was made
 * to save repeats of code.
 */
@Component
public class JwtUtil {

    private final JwtConfig jwtConfig;
    private final Logger logger = LogManager.getLogger();

    @Autowired
    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String getUsernameFromToken(String token) {
       try {
           token = token.replaceAll(jwtConfig.getPrefix(), "");

           Claims jwtClaims = Jwts.parser()
                   .setSigningKey(jwtConfig.getSigningKey())
                   .parseClaimsJws(token)
                   .getBody();

           return jwtClaims.getSubject();

       } catch (ExpiredJwtException e) {
           logger.info(String.format("A user tried to authenticate using an expired token. Nested exception: %s with message: %s",
                   e.getClass().getSimpleName(), e.getMessage()));
           throw new AuthenticationException();
       }
    }
}
