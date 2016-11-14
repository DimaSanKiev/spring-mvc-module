package guru.springframework.controller;

import guru.springframework.command.CustomerForm;
import guru.springframework.command.validator.CustomerFormValidator;
import guru.springframework.converter.CustomerToCustomerForm;
import guru.springframework.domain.Customer;
import guru.springframework.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@RequestMapping("/customer")
@Controller
public class CustomerController {

    private CustomerService customerService;
    private Validator customerFormValidator;
    private CustomerToCustomerForm customerToCustomerForm;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    @Qualifier("customerFormValidator")
    public void setCustomerFormValidator(CustomerFormValidator customerFormValidator) {
        this.customerFormValidator = customerFormValidator;
    }

    @Autowired
    public void setCustomerToCustomerForm(CustomerToCustomerForm customerToCustomerForm) {
        this.customerToCustomerForm = customerToCustomerForm;
    }

    @RequestMapping({"/list", "/"})
    public String getCustomersList(Model model) {
        model.addAttribute("customers", customerService.listAll());
        return "customer/list";
    }

    @RequestMapping("/show/{id}")
    public String getCustomerById(@PathVariable Integer id, Model model) {
        model.addAttribute("customer", customerService.getById(id));
        return "customer/show";
    }

    @RequestMapping("/new")
    public String newCustomer(Model model) {
        model.addAttribute("customerForm", new CustomerForm());
        return "customer/customerForm";
    }

    @RequestMapping("/edit/{id}")
    public String editCustomer(@PathVariable Integer id, Model model) {
        Customer customer = customerService.getById(id);

        model.addAttribute("customerForm", customerToCustomerForm.convert(customer));
        return "customer/customerForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveOrUpdateCustomer(@Valid CustomerForm customerForm, BindingResult bindingResult) {

        customerFormValidator.validate(customerForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "customer/customerForm";
        }

        Customer savedCustomer = customerService.saveOrUpdateCustomerForm(customerForm);
        return "redirect:customer/show/" + savedCustomer.getId();
    }

    @RequestMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Integer id) {
        customerService.delete(id);
        return "redirect:/customer/list";
    }
}
