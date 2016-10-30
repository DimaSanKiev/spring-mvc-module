package guru.springframework.bootstrap;

import guru.springframework.domain.Customer;
import guru.springframework.domain.Product;
import guru.springframework.service.CustomerService;
import guru.springframework.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private ProductService productService;
    private CustomerService customerService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadProducts();
        loadCustomers();
    }

    private void loadProducts() {
        Product product1 = new Product();
        product1.setDescription("Product 1");
        product1.setPrice(new BigDecimal("12.99"));
        product1.setImageUrl("http://example.com/product1");
        productService.saveOrUpdate(product1);

        Product product2 = new Product();
        product2.setDescription("Product 2");
        product2.setPrice(new BigDecimal("14.99"));
        product2.setImageUrl("http://example.com/product2");
        productService.saveOrUpdate(product2);

        Product product3 = new Product();
        product3.setDescription("Product 3");
        product3.setPrice(new BigDecimal("34.99"));
        product3.setImageUrl("http://example.com/product3");
        productService.saveOrUpdate(product3);

        Product product4 = new Product();
        product4.setDescription("Product 4");
        product4.setPrice(new BigDecimal("44.99"));
        product4.setImageUrl("http://example.com/product4");
        productService.saveOrUpdate(product4);

        Product product5 = new Product();
        product5.setDescription("Product 5");
        product5.setPrice(new BigDecimal("25.99"));
        product5.setImageUrl("http://example.com/product5");
        productService.saveOrUpdate(product5);
    }

    private void loadCustomers() {
        Customer customer1 = new Customer();
        customer1.setFirstName("Alberta");
        customer1.setLastName("Shultz");
        customer1.setEmail("AlbertaGShultz@jourrapide.com");
        customer1.setPhoneNumber("732-619-7166");
        customer1.setAddressLineOne("3136 Webster Street");
        customer1.setAddressLineTwo("");
        customer1.setCity("Teterboro");
        customer1.setState("NJ");
        customer1.setZipCode("07608");
        customerService.saveOrUpdate(customer1);

        Customer customer2 = new Customer();
        customer2.setFirstName("Tami");
        customer2.setLastName("Cabezas");
        customer2.setEmail("TamiJCabezas@jourrapide.com");
        customer2.setPhoneNumber("205-271-9661");
        customer2.setAddressLineOne("2525 Retreat Avenue");
        customer2.setAddressLineTwo("");
        customer2.setCity("Birmingham");
        customer2.setState("AL");
        customer2.setZipCode("35203");
        customerService.saveOrUpdate(customer2);

        Customer customer3 = new Customer();
        customer3.setFirstName("William");
        customer3.setLastName("Philip");
        customer3.setEmail("WilliamGPhilip@dayrep.com");
        customer3.setPhoneNumber("214-225-1154");
        customer3.setAddressLineOne("482 Fancher Drive");
        customer3.setAddressLineTwo("");
        customer3.setCity("Dallas");
        customer3.setState("TX");
        customer3.setZipCode("75201");
        customerService.saveOrUpdate(customer3);

        Customer customer4 = new Customer();
        customer4.setFirstName("Paul");
        customer4.setLastName("Heslin");
        customer4.setEmail("PaulCHeslin@armyspy.com");
        customer4.setPhoneNumber("406-721-8674");
        customer4.setAddressLineOne("2479 Tibbs Avenue");
        customer4.setAddressLineTwo("");
        customer4.setCity("Missoula");
        customer4.setState("MT");
        customer4.setZipCode("59802");
        customerService.saveOrUpdate(customer4);

        Customer customer5 = new Customer();
        customer5.setFirstName("Douglas");
        customer5.setLastName("Scott");
        customer5.setEmail("DouglasLScott@jourrapide.com");
        customer5.setPhoneNumber("540-453-6394");
        customer5.setAddressLineOne("4471 Jehovah Drive");
        customer5.setAddressLineTwo("");
        customer5.setCity("Harrisonburg");
        customer5.setState("VA");
        customer5.setZipCode("22801");
        customerService.saveOrUpdate(customer5);
    }
}
