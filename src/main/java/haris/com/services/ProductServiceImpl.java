package haris.com.services;

import haris.com.domain.Discount;
import haris.com.domain.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by jt on 11/6/15.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private Map<Integer,Product> products;

    public ProductServiceImpl() {
        loadProducts();
    }


    @Override
    public BigDecimal getTotalCost(){
        double sum= 0.0;
        for (Map.Entry<Integer, Product> pr:products.entrySet()) {
                sum = sum + pr.getValue().getPrice().doubleValue();
        }
        if (sum > 100.0){
            Discount.setDiscount(true);
            sum-= 0.1*sum;
        }
        else
            Discount.setDiscount(false);
        return new BigDecimal(sum);
    }
    @Override
    public Float getTotalShippingCost(){
        double sumShip= 0.0;
        for (Map.Entry<Integer, Product> pr:products.entrySet()) {
            sumShip += + 5*pr.getValue().getShippingWeightFactor().floatValue();
        }
        return new Float(sumShip);
    }

    @Override
    public List<Product> listAllProducts() {
        return new ArrayList<>(products.values());
    }
    @Override
    public Product getProductById(Integer id) {
        return products.get(id);
    }

    @Override
    public Product saveOrUpdateProduct(Product product) {
        if (product != null){
            if (product.getId() == null){
                product.setId(getNextKey());
            }

            if (product.getProductName().equals("tv")) {
                product.setPrice(new BigDecimal("40.02"));
                product.setShippingWeightFactor(new Float("0.3"));
            }
            else if(product.getProductName().equals("telephone")) {
                product.setPrice(new BigDecimal("20.33"));
                product.setShippingWeightFactor(new Float("0.1"));
            }
            else if(product.getProductName().equals("fridge")) {
                product.setPrice(new BigDecimal("75.2"));
                product.setShippingWeightFactor(new Float("0.8"));
            }
            else if(product.getProductName().equals("oven")) {
                product.setPrice(new BigDecimal("82.1"));
                product.setShippingWeightFactor(new Float("0.7"));
            }
            products.put(product.getId(), product);

            return product;
        } else {
            throw new RuntimeException("Product Can't be null");
        }
    }

    @Override
    public void deleteProduct(Integer id) {
        products.remove(id);
    }

    private Integer getNextKey(){
        if(products.isEmpty())
            return 1;
        return Collections.max(products.keySet()) + 1;
    }
    private void loadProducts(){
        products = new HashMap<>();

    }
}