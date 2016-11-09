package guru.springframework.command.validator;

import guru.springframework.command.CustomerForm;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CustomerFormValidatorTest {

    private Validator validator;
    private CustomerForm customerForm;
    private Errors errors;

    @Before
    public void setUp() throws Exception {
        validator = new CustomerFormValidator();
        customerForm = new CustomerForm();
        errors = new BeanPropertyBindingResult(customerForm, "customerForm");
    }

    @Test
    public void testNoErrors() throws Exception {
        customerForm.setPasswordText("password");
        customerForm.setPasswordTextConf("password");

        validator.validate(customerForm, errors);

        assert !errors.hasErrors();
    }

    @Test
    public void testHasErrors() throws Exception {
        customerForm.setPasswordText("password");
        customerForm.setPasswordTextConf("PASSWORD");

        validator.validate(customerForm, errors);

        assert errors.hasErrors();
    }
}