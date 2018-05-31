package lt.vu.mif.Logging;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lt.vu.mif.error.IErrorLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggedAspect {

    @Autowired
    private IErrorLogger errorLogger;

    @Pointcut("@within(lt.vu.mif.Logging.Logged) || @annotation(lt.vu.mif.Logging.Logged)")
    public void isLogged() {
    }

    @ConditionalOnExpression("${customLogging.controllers.enabled:true}")
    @Before("isLogged() && execution(public * lt.vu.mif.ui.controller..*(..))")
    public void log(JoinPoint joinPoint) {
        logMethod(joinPoint);
    }

    @ConditionalOnExpression("${customLogging.repositories.enabled:true}")
    @Before("isLogged() && execution(public * lt.vu.mif.repository.repository.implementations..*(..))")
    public void logRepo(JoinPoint joinPoint) {
        logMethod(joinPoint);
    }

    private void logMethod(JoinPoint joinPoint) {
        errorLogger.logError(
            "At: " + Instant.now() + " " + joinPoint.getTarget().getClass().getName() + "."
                + joinPoint.getSignature().getName() + " was called.");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            String username = authentication.getName();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            List<String> authsList = authorities.stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
            errorLogger.logError(
                "User: athorities - " + String.join("|", authsList) + ", email - " + username);
        } else {
            errorLogger.logError("User: null");
        }
    }
}
