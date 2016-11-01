package guru.springframework.service;

import guru.springframework.domain.Customer;
import guru.springframework.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomerServiceJpaDaoImplTest {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Test
    public void testList() throws Exception {
        List<Customer> customers = (List<Customer>) customerService.listAll();

        assertNotNull(customers);
        assertEquals(5, customers.size());
    }

    @Test
    public void testSaveWithUser() throws Exception {
        Customer customer = new Customer();
        User user = new User();
        user.setUsername("MyUsername");
        user.setPassword("MyPassword");
        customer.setUser(user);

        Customer savedCustomer = customerService.saveOrUpdate(customer);

        assert savedCustomer.getUser().getId() != null;
    }
}