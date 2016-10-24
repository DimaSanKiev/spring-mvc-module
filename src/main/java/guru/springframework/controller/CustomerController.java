package guru.springframework.controller;

import guru.springframework.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping("/customers")
    public String getCustomersList(Model model) {
        model.addAttribute("customers", customerService.listAllCustomers());
        return "customers";
    }

    @RequestMapping("/customer/{id}")
    public String getCustomerById(@PathVariable Integer id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "customer";
    }
}
