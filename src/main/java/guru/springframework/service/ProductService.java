package guru.springframework.service;

import guru.springframework.command.ProductForm;
import guru.springframework.domain.Product;

public interface ProductService extends CrudService<Product> {

    Product saveOrUpdateProductForm(ProductForm productForm);

}
