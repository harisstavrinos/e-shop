package haris.com.controllers;

import haris.com.domain.Product;
import haris.com.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest {

    @Mock //Mockito Mock object
    private ProductService productService;

    @InjectMocks //setups up controller, and injects mock objects into it.
    private ProductController productController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this); //initilizes controller and mocks

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }



    @Test
    public void testNewProduct() throws Exception {
        Integer id = 1;

        //should not call service
        verifyZeroInteractions(productService);

        mockMvc.perform(get("/product/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("productform"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        Integer id = 1;
        String productName = "tv";
        BigDecimal price = new BigDecimal("12.00");
        Float shippingWeightFactor = new Float("0.2");


        Product returnProduct = new Product();
        returnProduct.setId(id);
        returnProduct.setProductName(productName);
        returnProduct.setPrice(price);
        returnProduct.setShippingWeightFactor(shippingWeightFactor);

        // when(productService.saveOrUpdateProduct(Matchers.<Product>any())).thenReturn(returnProduct);

        mockMvc.perform(post("/product")
                .param("id", "1")
                .param("productName", productName)
                .param("price", "12.00")
                .param("shippingWeightFactor", "0.2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products"))
                .andExpect(model().attribute("product", instanceOf(Product.class)))
                .andExpect(model().attribute("product", hasProperty("id", is(id))))
                .andExpect(model().attribute("product", hasProperty("productName", is(productName))))
                .andExpect(model().attribute("product", hasProperty("price", is(price))))
                .andExpect(model().attribute("product", hasProperty("shippingWeightFactor", is(shippingWeightFactor))));

        //verify properties of bound object
        ArgumentCaptor<Product> boundProduct = ArgumentCaptor.forClass(Product.class);
        verify(productService).saveOrUpdateProduct(boundProduct.capture());

        assertEquals(id, boundProduct.getValue().getId());
        assertEquals(productName, boundProduct.getValue().getProductName());
        assertEquals(price, boundProduct.getValue().getPrice());
        assertEquals(shippingWeightFactor, boundProduct.getValue().getShippingWeightFactor());
    }

    @Test
    public void testDelete() throws Exception {
        Integer id = 1;

        mockMvc.perform(get("/product/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products"));

        verify(productService, times(1)).deleteProduct(id);
    }
    /*
    @Test
    public void testListProducts() throws Exception {

        List<Product> products = new ArrayList<>();


        Integer id = 1;
        String productName = "tv";
        BigDecimal price = new BigDecimal("12.00");
        Float shippingWeightFactor = new Float("0.2");
        float total = 89.2f;
        boolean discount = true;
        float totalShipping =3.2f;

        Product returnProduct = new Product();
        returnProduct.setId(id);
        returnProduct.setProductName(productName);
        returnProduct.setPrice(price);
        returnProduct.setShippingWeightFactor(shippingWeightFactor);
        products.add(returnProduct);

        Product returnProduct2 = new Product();
        id=2;
        returnProduct2.setId(id);
        returnProduct2.setProductName(productName);
        returnProduct2.setPrice(price);
        returnProduct2.setShippingWeightFactor(shippingWeightFactor);

        products.add(returnProduct2);


        //when(productService.listAllProducts()).thenReturn((List) products); //need to strip generics to keep Mockito happy.

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attribute("products", hasSize(2)))
                .andExpect(model().attribute("total", is(89.2)))
                .andExpect(model().attribute("discount", is(true)))
                .andExpect(model().attribute("totalShipping", is(3.2)));
    }
*/
}

