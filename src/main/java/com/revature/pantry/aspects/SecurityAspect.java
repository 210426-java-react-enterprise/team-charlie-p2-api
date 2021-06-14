package com.revature.pantry.aspects;

import com.revature.pantry.exceptions.AuthenticationException;
import com.revature.pantry.exceptions.AuthorizationException;
import com.revature.pantry.web.dtos.Principal;
import com.revature.pantry.web.security.Secured;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Secures any method that has the <code>@Secured</code> annotation. Checks the user's list of roles against the roles allowed for that method.
 * Throws an AuthenticationException if a user tries to access a secured method with no authorization. Throws an AuthorizationException if
 * the user does not have the required role.
 *
 * @author Richard Taylor
 * @author Wezley Singleton
 */
@Aspect
@Component
public class SecurityAspect {

    @Around("@annotation(com.revature.pantry.web.security.Secured)")
    public Object secureEndpoint(ProceedingJoinPoint pjp) throws Throwable {

        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Secured seciredAnno = method.getAnnotation(Secured.class);
        List<String> allowedRoles = Arrays.asList(seciredAnno.allowedRoles());

        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Principal principal = (Principal) req.getAttribute("principal");

        if (principal == null) {
            throw new AuthenticationException("An unauthenticated request was made to a protected endpoint");
        }

        if (!allowedRoles.contains(principal.getRole().toString())) {
            throw new AuthorizationException("A forbidden request was made by: " + principal.getUsername());
        }
        return pjp.proceed();
    }
}
