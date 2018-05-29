package lt.vu.mif.Logging;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggedAspect {

    private static final Logger log = Logger.getLogger(LoggedAspect.class.getName());

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
        log.info("At: " + Instant.now() + " " + joinPoint.getTarget().getClass().getName() + "."
            + joinPoint.getSignature().getName() + " was called.");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            String username = authentication.getName();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            List<String> authsList = authorities.stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
            log.info("User: athorities - " + String.join("|", authsList) + ", email - " + username);
        } else {
            log.info("User: null");
        }
    }
}
