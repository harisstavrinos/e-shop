package haris.com.domain;

import java.math.BigDecimal;

/**
 * Created by jt on 11/6/15.
 */
public class Product {
    private Integer id;
    private String productName;
    private BigDecimal price;
    private Float shippingWeightFactor;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Float getShippingWeightFactor() {
        return shippingWeightFactor;
    }

    public void setShippingWeightFactor(Float shippingWeightFactor) {
        this.shippingWeightFactor = shippingWeightFactor;
    }


}