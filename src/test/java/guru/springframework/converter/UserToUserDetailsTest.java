package guru.springframework.converter;

import guru.springframework.domain.User;
import guru.springframework.domain.security.Role;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserToUserDetailsTest {

    private Converter<User, UserDetails> converter;

    @Before
    public void setUp() throws Exception {
        converter = new UserToUserDetails();
    }

    @Test
    public void testUserConvert() throws Exception {
        String username = "Dima";
        String password = "password";
        String roleName1 = "USER";
        String roleName2 = "ADMIN";
        Role role1 = new Role();
        role1.setRole(roleName1);
        Role role2 = new Role();
        role2.setRole(roleName2);

        User user = new User();
        user.setUsername(username);
        user.setEncryptedPassword(password);
        user.addRole(role1);
        user.addRole(role2);

        UserDetails userDetails = converter.convert(user);

        assertTrue(userDetails.getUsername().equals(username));
        assertTrue(userDetails.getPassword().equals(password));
        assertEquals(2, userDetails.getAuthorities().size());
    }
}