package guru.springframework.controller;

import guru.springframework.command.CustomerForm;
import guru.springframework.command.validator.CustomerFormValidator;
import guru.springframework.converter.CustomerToCustomerForm;
import guru.springframework.domain.Address;
import guru.springframework.domain.Customer;
import guru.springframework.domain.User;
import guru.springframework.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerController.setCustomerFormValidator(new CustomerFormValidator());
        customerController.setCustomerToCustomerForm(new CustomerToCustomerForm());

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testList() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());

        when(customerService.listAll()).thenReturn((List) customers);

        mockMvc.perform(get("/customer/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/list"))
                .andExpect(model().attribute("customers", hasSize(2)));
    }

    @Test
    public void testShow() throws Exception {
        Integer id = 1;

        when(customerService.getById(id)).thenReturn(new Customer());

        mockMvc.perform(get("/customer/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/show"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testEdit() throws Exception {
        Integer id = 1;

        User user = new User();
        Customer customer = new Customer();
        customer.setUser(user);

        when(customerService.getById(id)).thenReturn(customer);

        mockMvc.perform(get("/customer/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customerForm"))
                .andExpect(model().attribute("customerForm", instanceOf(CustomerForm.class)));
    }

    @Test
    public void testNewCustomer() throws Exception {
        verifyZeroInteractions(customerService);

        mockMvc.perform(get("/customer/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customerForm"))
                .andExpect(model().attribute("customerForm", instanceOf(CustomerForm.class)));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        Integer id = 1;
        String firstName = "Naomi";
        String lastName = "Bird";
        String email = "NaomiPBird@teleworm.us";
        String phoneNumber = "713-232-0235";
        String addressLineOne = "2 Clousson Road";
        String addressLineTwo= "Apt 21";
        String city = "Rosenberg";
        String state = "TX";
        String zipCode = "77471";
        String username = "testUser";
        String password = "password";

        Customer returnCustomer = new Customer();
        returnCustomer.setId(id);
        returnCustomer.setFirstName(firstName);
        returnCustomer.setLastName(lastName);
        returnCustomer.setEmail(email);
        returnCustomer.setPhoneNumber(phoneNumber);
        returnCustomer.setBillingAddress(new Address());
        returnCustomer.getBillingAddress().setAddressLineOne(addressLineOne);
        returnCustomer.getBillingAddress().setAddressLineTwo(addressLineTwo);
        returnCustomer.getBillingAddress().setCity(city);
        returnCustomer.getBillingAddress().setState(state);
        returnCustomer.getBillingAddress().setZipCode(zipCode);
        returnCustomer.setUser(new User());
        returnCustomer.getUser().setUsername(username);
        returnCustomer.getUser().setPassword(password);

        when(customerService.saveOrUpdateCustomerForm(Matchers.any())).thenReturn(returnCustomer);
        when(customerService.getById(Matchers.any())).thenReturn(returnCustomer);

        mockMvc.perform(post("/customer")
            .param("customerId", "1")
            .param("firstName", firstName)
            .param("lastName", lastName)
            .param("email", email)
            .param("phoneNumber", phoneNumber)
                .param("userName", username)
                .param("passwordText", password)
                .param("passwordTextConf", password)
            .param("shippingAddress.addressLineOne", addressLineOne)
            .param("shippingAddress.addressLineTwo", addressLineTwo)
            .param("shippingAddress.city", city)
            .param("shippingAddress.state", state)
            .param("shippingAddress.zipCode", zipCode))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:customer/show/1"));

        ArgumentCaptor<CustomerForm> customerCaptor = ArgumentCaptor.forClass(CustomerForm.class);
        verify(customerService).saveOrUpdateCustomerForm(customerCaptor.capture());

        CustomerForm boundCustomer = customerCaptor.getValue();

        assertEquals(id, boundCustomer.getCustomerId());
        assertEquals(firstName, boundCustomer.getFirstName());
        assertEquals(lastName, boundCustomer.getLastName());
        assertEquals(email, boundCustomer.getEmail());
        assertEquals(phoneNumber, boundCustomer.getPhoneNumber());
    }

    @Test
    public void testDelete() throws Exception {
        Integer id = 1;

        mockMvc.perform(get("/customer/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer/list"));

        verify(customerService, times(1)).delete(id);
    }
}