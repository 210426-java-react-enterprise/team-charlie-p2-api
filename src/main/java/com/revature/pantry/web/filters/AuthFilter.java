package com.revature.pantry.web.filters;

import com.revature.pantry.web.dtos.Principal;
import com.revature.pantry.web.security.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter("/*")
@Component
public class AuthFilter implements Filter {

    private Logger logger = LogManager.getLogger();
    private JwtConfig jwtConfig;

    @Override
    public void init(FilterConfig cfg) {
        ApplicationContext container = WebApplicationContextUtils.getRequiredWebApplicationContext(cfg.getServletContext());
        this.jwtConfig = container.getBean(JwtConfig.class);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        parseToken(request);
        chain.doFilter(request, response);
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

            Principal principal = new Principal(jwtClaims);
            req.setAttribute("principal", principal);

        } catch (Exception e) {
            logger.warn(String.format("%s was passed from AuthFilter with message: %s", e.getClass().getSimpleName(), e.getMessage()));
        }
    }
}
