package guru.springframework.service;

import guru.springframework.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> listAllProducts();
}
