package guru.springframework.controller;

import guru.springframework.domain.Customer;
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
        when(customerService.getById(1)).thenReturn(new Customer());

        mockMvc.perform(get("/customer/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customerForm"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testNewCustomer() throws Exception {
        verifyZeroInteractions(customerService);

        mockMvc.perform(get("/customer/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customerForm"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        Integer id = 1;
        String firstName = "Naomi";
        String lastName = "Bird";
        String email = "NaomiPBird@teleworm.us";
        String phoneNumber = "713-232-0235";
        String addressLineOne = "3865 Clousson Road";
        String addressLineTwo= "";
        String city = "Rosenberg";
        String state = "TX";
        String zipCode = "77471";

        Customer returnCustomer = new Customer();
        returnCustomer.setId(id);
        returnCustomer.setFirstName(firstName);
        returnCustomer.setLastName(lastName);
        returnCustomer.setEmail(email);
        returnCustomer.setPhoneNumber(phoneNumber);
        returnCustomer.setAddressLineOne(addressLineOne);
        returnCustomer.setAddressLineTwo(addressLineTwo);
        returnCustomer.setCity(city);
        returnCustomer.setState(state);
        returnCustomer.setZipCode(zipCode);

        when(customerService.saveOrUpdate(Matchers.any())).thenReturn(returnCustomer);

        mockMvc.perform(post("/customer")
            .param("id", "1")
            .param("firstName", firstName)
            .param("lastName", lastName)
            .param("email", email)
            .param("phoneNumber", phoneNumber)
            .param("addressLineOne", addressLineOne)
            .param("addressLineTwo", addressLineTwo)
            .param("city", city)
            .param("state", state)
            .param("zipCode", zipCode))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:customer/show/1"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)))
                .andExpect(model().attribute("customer", hasProperty("id", is(id))))
                .andExpect(model().attribute("customer", hasProperty("firstName", is(firstName))))
                .andExpect(model().attribute("customer", hasProperty("lastName", is(lastName))))
                .andExpect(model().attribute("customer", hasProperty("email", is(email))))
                .andExpect(model().attribute("customer", hasProperty("phoneNumber", is(phoneNumber))))
                .andExpect(model().attribute("customer", hasProperty("addressLineOne", is(addressLineOne))))
                .andExpect(model().attribute("customer", hasProperty("addressLineTwo", is(addressLineTwo))))
                .andExpect(model().attribute("customer", hasProperty("city", is(city))))
                .andExpect(model().attribute("customer", hasProperty("state", is(state))))
                .andExpect(model().attribute("customer", hasProperty("zipCode", is(zipCode))));

        ArgumentCaptor<Customer> boundProduct = ArgumentCaptor.forClass(Customer.class);
        verify(customerService).saveOrUpdate(boundProduct.capture());

        assertEquals(id, boundProduct.getValue().getId());
        assertEquals(firstName, boundProduct.getValue().getFirstName());
        assertEquals(lastName, boundProduct.getValue().getLastName());
        assertEquals(email, boundProduct.getValue().getEmail());
        assertEquals(phoneNumber, boundProduct.getValue().getPhoneNumber());
        assertEquals(addressLineOne, boundProduct.getValue().getAddressLineOne());
        assertEquals(addressLineTwo, boundProduct.getValue().getAddressLineTwo());
        assertEquals(city, boundProduct.getValue().getCity());
        assertEquals(state, boundProduct.getValue().getState());
        assertEquals(zipCode, boundProduct.getValue().getZipCode());
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