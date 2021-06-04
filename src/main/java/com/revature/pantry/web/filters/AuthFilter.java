package com.revature.pantry.web.filters;

import com.revature.pantry.web.dtos.Principal;
import com.revature.pantry.web.security.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter extends HttpFilter {

    private Logger logger = LogManager.getLogger();
    private JwtConfig jwtConfig;

    public void init(FilterConfig cfg) {
        ApplicationContext container = WebApplicationContextUtils.getRequiredWebApplicationContext(cfg.getServletContext());
        this.jwtConfig = container.getBean(JwtConfig.class);
    }

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        parseToken(req);
        chain.doFilter(req, resp);
    }

    private void parseToken(HttpServletRequest req) {

        try {

            String header = req.getHeader(jwtConfig.getHeader());

            if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
                logger.info("Request originates from an unauthenticated source.");
                return;
            }

            String token = header.replaceAll(jwtConfig.getPrefix(), "");

            Claims jwtClaims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSigningKey())
                    .parseClaimsJws(token)
                    .getBody();

            req.setAttribute("principal", new Principal(jwtClaims));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
