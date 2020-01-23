package haris.com.services;

import haris.com.domain.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {


    List<Product> listAllProducts();
    Product getProductById(Integer id);
    Product saveOrUpdateProduct(Product product);
    void deleteProduct(Integer id);
    BigDecimal getTotalCost();
    Float getTotalShippingCost();
}
