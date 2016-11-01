package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.domain.enums.OrderStatus;
import guru.springframework.service.CustomerService;
import guru.springframework.service.ProductService;
import guru.springframework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private ProductService productService;
    private UserService userService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadProducts();
        loadUsersAndCustomers();
        loadCarts();
        loadOrderHistory();
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

    private void loadUsersAndCustomers() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password");
        Customer customer1 = new Customer();
        customer1.setFirstName("Alberta");
        customer1.setLastName("Shultz");
        customer1.setEmail("AlbertaGShultz@jourrapide.com");
        customer1.setPhoneNumber("732-619-7166");
        customer1.setBillingAddress(new Address());
        customer1.getBillingAddress().setAddressLineOne("3136 Webster Street");
        customer1.getBillingAddress().setAddressLineTwo("");
        customer1.getBillingAddress().setCity("Teterboro");
        customer1.getBillingAddress().setState("NJ");
        customer1.getBillingAddress().setZipCode("07608");
        user1.setCustomer(customer1);
        userService.saveOrUpdate(user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("password");
        Customer customer2 = new Customer();
        customer2.setFirstName("Tami");
        customer2.setLastName("Cabezas");
        customer2.setEmail("TamiJCabezas@jourrapide.com");
        customer2.setPhoneNumber("205-271-9661");
        customer2.setBillingAddress(new Address());
        customer2.getBillingAddress().setAddressLineOne("2525 Retreat Avenue");
        customer2.getBillingAddress().setAddressLineTwo("");
        customer2.getBillingAddress().setCity("Birmingham");
        customer2.getBillingAddress().setState("AL");
        customer2.getBillingAddress().setZipCode("35203");
        user2.setCustomer(customer2);
        userService.saveOrUpdate(user2);

        User user3 = new User();
        user3.setUsername("user3");
        user3.setPassword("password");
        Customer customer3 = new Customer();
        customer3.setFirstName("William");
        customer3.setLastName("Philip");
        customer3.setEmail("WilliamGPhilip@dayrep.com");
        customer3.setPhoneNumber("214-225-1154");
        customer3.setBillingAddress(new Address());
        customer3.getBillingAddress().setAddressLineOne("482 Fancher Drive");
        customer3.getBillingAddress().setAddressLineTwo("");
        customer3.getBillingAddress().setCity("Dallas");
        customer3.getBillingAddress().setState("TX");
        customer3.getBillingAddress().setZipCode("75201");
        user3.setCustomer(customer3);
        userService.saveOrUpdate(user3);

        User user4 = new User();
        user4.setUsername("user4");
        user4.setPassword("password");
        Customer customer4 = new Customer();
        customer4.setFirstName("Paul");
        customer4.setLastName("Heslin");
        customer4.setEmail("PaulCHeslin@armyspy.com");
        customer4.setPhoneNumber("406-721-8674");
        customer4.setBillingAddress(new Address());
        customer4.getBillingAddress().setAddressLineOne("2479 Tibbs Avenue");
        customer4.getBillingAddress().setAddressLineTwo("");
        customer4.getBillingAddress().setCity("Missoula");
        customer4.getBillingAddress().setState("MT");
        customer4.getBillingAddress().setZipCode("59802");
        user4.setCustomer(customer4);
        userService.saveOrUpdate(user4);

        User user5 = new User();
        user5.setUsername("user5");
        user5.setPassword("password");
        Customer customer5 = new Customer();
        customer5.setFirstName("Douglas");
        customer5.setLastName("Scott");
        customer5.setEmail("DouglasLScott@jourrapide.com");
        customer5.setPhoneNumber("540-453-6394");
        customer5.setBillingAddress(new Address());
        customer5.getBillingAddress().setAddressLineOne("4471 Jehovah Drive");
        customer5.getBillingAddress().setAddressLineTwo("");
        customer5.getBillingAddress().setCity("Harrisonburg");
        customer5.getBillingAddress().setState("VA");
        customer5.getBillingAddress().setZipCode("22801");
        user5.setCustomer(customer5);
        userService.saveOrUpdate(user5);
    }

    private void loadCarts() {
        List<User> users = (List<User>) userService.listAll();
        List<Product> products = (List<Product>) productService.listAll();

        users.forEach(user -> {
            user.setCart(new Cart());
            CartDetail cartDetail = new CartDetail();
            cartDetail.setProduct(products.get(0));
            cartDetail.setQuantity(2);
            user.getCart().addCartDetail(cartDetail);
            userService.saveOrUpdate(user);
        });
    }

    private void loadOrderHistory() {
        List<User> users = (List<User>) userService.listAll();
        List<Product> products = (List<Product>) productService.listAll();

        users.forEach(user -> {
            Order order = new Order();
            order.setCustomer(user.getCustomer());
            order.setOrderStatus(OrderStatus.SHIPPED);

            products.forEach(product -> {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setProduct(product);
                orderDetail.setQuantity(1);
                order.addtoOrderDetails(orderDetail);
            });
        });
    }
}
