package guru.springframework.service.security;

import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginAspect {

    private LoginFailureEventPublisher failureEventPublisher;
    private LoginSuccessEventPublisher successEventPublisher;

    @Autowired
    public void setFailureEventPublisher(LoginFailureEventPublisher failureEventPublisher) {
        this.failureEventPublisher = failureEventPublisher;
    }

    @Autowired
    public void setSuccessEventPublisher(LoginSuccessEventPublisher successEventPublisher) {
        this.successEventPublisher = successEventPublisher;
    }

    @Pointcut("execution(* org.springframework.security.authentication.AuthenticationProvider.authenticate(..))")
    public void doAuthenticate() {

    }

    @Before("guru.springframework.service.security.LoginAspect.doAuthenticate() && args(authentication)")
    public void logBeforeAuthenticate(Authentication authentication) {
        System.out.println("This is before the Authenticate method authentication: " + authentication.isAuthenticated());
    }

    @AfterReturning(value = "guru.springframework.service.security.LoginAspect.doAuthenticate()",
            returning = "authentication")
    public void logAfterAuthenticate(Authentication authentication) {
        System.out.println("This is after the Authenticate method authentication: " + authentication.isAuthenticated());
        successEventPublisher.publishEvent(new LoginSuccessEvent(authentication));
    }

    @AfterThrowing("guru.springframework.service.security.LoginAspect.doAuthenticate() && args(authentication)")
    public void logAuthenticationException(Authentication authentication) {
        String userDetails = (String) authentication.getPrincipal();
        System.out.println("Login failed for user: " + userDetails);
        failureEventPublisher.publish(new LoginFailureEvent(authentication));
    }
}
