package guru.springframework.service;

import guru.springframework.command.CustomerForm;
import guru.springframework.domain.Customer;

public interface CustomerService extends CrudService<Customer> {

    Customer saveOrUpdateCustomerForm(CustomerForm customerForm);

}
