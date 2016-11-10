package guru.springframework.service.security;

import guru.springframework.domain.User;
import guru.springframework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class LoginFailureEventHandler implements ApplicationListener<LoginFailureEvent> {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(LoginFailureEvent event) {
        Authentication authentication = (Authentication) event.getSource();
        System.out.println("LoginEvent FAILURE for: " + authentication.getPrincipal());
        updateUserAccount(authentication);
    }

    private void updateUserAccount(Authentication authentication) {
        User user = userService.findByUserName((String) authentication.getPrincipal());

        if (user != null) {     // user found
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            System.out.println("Valid username, updating failed attempts");

            if (user.getFailedLoginAttempts() > 3) {
                user.setEnabled(false);
                System.out.println("LOCKING USER ACCOUNT!");
            }

            userService.saveOrUpdate(user);
        }
    }
}
