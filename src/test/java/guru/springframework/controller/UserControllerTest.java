package guru.springframework.controller;

import guru.springframework.domain.User;
import guru.springframework.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testList() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userService.listAll()).thenReturn((List) users);

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attribute("users", hasSize(2)));
    }

    @Test
    public void testShow() throws Exception {
        Integer id = 1;

        when(userService.getById(id)).thenReturn(new User());

        mockMvc.perform(get("/user/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/show"))
                .andExpect(model().attribute("user", instanceOf(User.class)));
    }

    @Test
    public void testEdit() throws Exception {
        Integer id = 1;

        when(userService.getById(id)).thenReturn(new User());

        mockMvc.perform(get("/user/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/userForm"))
                .andExpect(model().attribute("user", instanceOf(User.class)));
    }

    @Test
    public void testNewUser() throws Exception {
        verifyZeroInteractions(userService);

        mockMvc.perform(get("/user/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/userForm"))
                .andExpect(model().attribute("user", instanceOf(User.class)));
    }

    @Test
    public void testDelete() throws Exception {
        Integer id = 1;

        mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));

        verify(userService, times(1)).delete(id);
    }
}