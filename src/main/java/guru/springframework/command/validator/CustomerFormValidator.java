package guru.springframework.command.validator;

import guru.springframework.command.CustomerForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CustomerFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CustomerForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerForm customerForm = (CustomerForm) target;

        if (!customerForm.getPasswordText().equals(customerForm.getPasswordTextConf())) {
            errors.rejectValue("passwordText", "PasswordsDontMatch.customerForm.passwordText", "Passwords don't match");
            errors.rejectValue("passwordTextConf", "PasswordsDontMatch.customerForm.passwordTextConf", "Passwords don't match");
        }
    }
}
