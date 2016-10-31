package guru.springframework.service;

import guru.springframework.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("jpadao")
public class UserServiceJpaDaoImplTest {

    private UserService userService;
    private ProductService productService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        User user = new User();
        user.setUsername("testusername");
        user.setPassword("testpassword");

        User savedUser = userService.saveOrUpdate(user);

        assert savedUser.getId() != null;
        assert savedUser.getEncryptedPassword() != null;

        System.out.println("Encrypted password: " + savedUser.getEncryptedPassword());
    }

    @Test
    public void testSaveUserWithCustomer() throws Exception {
        User user = new User();
        user.setUsername("testUsername");
        user.setPassword("testPassword");

        Customer customer = new Customer();
        customer.setFirstName("CustomerFirstName");
        customer.setLastName("CustomerLastName");

        user.setCustomer(customer);

        User savedUser = userService.saveOrUpdate(user);

        assertNotNull(savedUser.getId());
        assertNotNull(savedUser.getVersion());
        assertNotNull(savedUser.getCustomer());
        assertNotNull(savedUser.getCustomer().getId());
    }

    @Test
    public void testAddCartToUser() throws Exception {
        User user = new User();
        user.setUsername("testUsername");
        user.setPassword("testPassword");
        user.setCart(new Cart());

        User savedUser = userService.saveOrUpdate(user);

        assertNotNull(savedUser.getId());
        assertNotNull(savedUser.getVersion());
        assertNotNull(savedUser.getCart());
        assertNotNull(savedUser.getCart().getId());
    }

    @Test
    public void testAddCartToUserWithCartDetails() throws Exception {
        User user = new User();
        user.setUsername("testUsername");
        user.setPassword("testPassword");
        user.setCart(new Cart());

        List<Product> storedProducts = (List<Product>) productService.listAll();

        CartDetail cartItemOne = new CartDetail();
        cartItemOne.setProduct(storedProducts.get(0));
        user.getCart().addCartDetail(cartItemOne);

        CartDetail cartItemTwo = new CartDetail();
        cartItemTwo.setProduct(storedProducts.get(1));
        user.getCart().addCartDetail(cartItemTwo);

        User savedUser = userService.saveOrUpdate(user);

        assertNotNull(savedUser.getId());
        assertNotNull(savedUser.getVersion());
        assertNotNull(savedUser.getCart());
        assertNotNull(savedUser.getCart().getId());
        assertEquals(2, savedUser.getCart().getCartDetails().size());
    }

    @Test
    public void testAddAndRemoveCartToUserWithCartDetails() throws Exception {
        User user = new User();
        user.setUsername("testUsername");
        user.setPassword("testPassword");
        user.setCart(new Cart());

        List<Product> storedProducts = (List<Product>) productService.listAll();

        CartDetail cartItemOne = new CartDetail();
        cartItemOne.setProduct(storedProducts.get(0));
        user.getCart().addCartDetail(cartItemOne);

        CartDetail cartItemTwo = new CartDetail();
        cartItemTwo.setProduct(storedProducts.get(1));
        user.getCart().addCartDetail(cartItemTwo);

        User savedUser = userService.saveOrUpdate(user);

        assertEquals(2, savedUser.getCart().getCartDetails().size());

        savedUser.getCart().removeCartDetail(savedUser.getCart().getCartDetails().get(0));
        userService.saveOrUpdate(savedUser);

        assertEquals(1, savedUser.getCart().getCartDetails().size());
    }
}