package guru.springframework.controller;

import guru.springframework.command.ProductForm;
import guru.springframework.converter.ProductToProductForm;
import guru.springframework.domain.Product;
import guru.springframework.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ProductControllerTest {

    @Mock   // Mockito Mock object
    private ProductService productService;

    @InjectMocks    // setups up controller, and injects mock objects into it
    private ProductController productController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this); // initializes controller and mocks
        productController.setProductToProductForm(new ProductToProductForm());
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testList() throws Exception {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());

        // specific Mockito interaction, tell stub to return list of products
        when(productService.listAll()).thenReturn((List) products); // need to strip generics to keep Mockito happy

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/list"))
                .andExpect(model().attribute("products", hasSize(2)));
    }

    @Test
    public void testShow() throws Exception {
        Integer id = 1;

        // tell Mockito stub to return new product for ID 1
        when(productService.getById(id)).thenReturn(new Product());

        mockMvc.perform(get("/product/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/show"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    @Test
    public void testEdit() throws Exception {
        Integer id = 1;

        // tell Mockito stub to return new product for ID 1
        when(productService.getById(id)).thenReturn(new Product());

        mockMvc.perform(get("/product/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/productForm"))
                .andExpect(model().attribute("productForm", instanceOf(ProductForm.class)));
    }

    @Test
    public void testNewProduct() throws Exception {
        // should not call service
        verifyZeroInteractions(productService);

        mockMvc.perform(get("/product/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/productForm"))
                .andExpect(model().attribute("productForm", instanceOf(ProductForm.class)));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        Integer id = 1;
        String description = "Test Description";
        BigDecimal price = new BigDecimal("12.00");
        String imageUrl = "http://www.example.com";

        Product returnProduct = new Product();
        returnProduct.setId(id);
        returnProduct.setDescription(description);
        returnProduct.setPrice(price);
        returnProduct.setImageUrl(imageUrl);

        when(productService.saveOrUpdateProductForm(Matchers.any())).thenReturn(returnProduct);

        mockMvc.perform(post("/product")
            .param("id", "1")
            .param("description", description)
            .param("price", "12.00")
            .param("imageUrl", "http://www.example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:product/show/1"))
                .andExpect(model().attribute("productForm", instanceOf(ProductForm.class)))
                .andExpect(model().attribute("productForm", hasProperty("id", is(id))))
                .andExpect(model().attribute("productForm", hasProperty("description", is(description))))
                .andExpect(model().attribute("productForm", hasProperty("price", is(price))))
                .andExpect(model().attribute("productForm", hasProperty("imageUrl", is(imageUrl))));

        // verify properties of bound object
        ArgumentCaptor<ProductForm> boundProduct = ArgumentCaptor.forClass(ProductForm.class);
        verify(productService).saveOrUpdateProductForm(boundProduct.capture());

        assertEquals(id, boundProduct.getValue().getId());
        assertEquals(description, boundProduct.getValue().getDescription());
        assertEquals(price, boundProduct.getValue().getPrice());
        assertEquals(imageUrl, boundProduct.getValue().getImageUrl());
    }

    @Test
    public void testDelete() throws Exception {
        Integer id = 1;

        mockMvc.perform(get("/product/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/list"));

        verify(productService, times(1)).delete(id);
    }
}