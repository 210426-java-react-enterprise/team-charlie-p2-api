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
