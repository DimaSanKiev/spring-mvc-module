package guru.springframework.controller;

import guru.springframework.command.ProductForm;
import guru.springframework.converter.ProductToProductForm;
import guru.springframework.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import guru.springframework.service.ProductService;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@RequestMapping("/product")
@Controller
public class ProductController {

    private ProductService productService;
    private ProductToProductForm productToProductForm;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setProductToProductForm(ProductToProductForm productToProductForm) {
        this.productToProductForm = productToProductForm;
    }

    @RequestMapping({"/list", "/"})
    public String listProducts(Model model) {
        model.addAttribute("products", productService.listAll());
        return "product/list";
    }

    @RequestMapping("/show/{id}")
    public String getProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.getById(id));
        return "product/show";
    }

    @RequestMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("productForm", new ProductForm());
        return "product/productForm";
    }

    @RequestMapping("/edit/{id}")
    public String editProduct(@PathVariable Integer id, Model model) {
        Product product = productService.getById(id);
        ProductForm productForm = productToProductForm.convert(product);
        model.addAttribute("productForm", productForm);
        return "product/productForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveOrUpdateProduct(@Valid ProductForm productForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product/productForm";
        }
        Product savedProduct = productService.saveOrUpdateProductForm(productForm);
        return "redirect:product/show/" + savedProduct.getId();
    }

    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
        return "redirect:/product/list";
    }
}
