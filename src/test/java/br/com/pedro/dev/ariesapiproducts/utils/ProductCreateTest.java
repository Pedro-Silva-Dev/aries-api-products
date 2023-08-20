package br.com.pedro.dev.ariesapiproducts.utils;

import java.math.BigDecimal;

import br.com.pedro.dev.ariesapiproducts.models.Product;

public class ProductCreateTest {

    public static Product getCreateProduct(Long categoryId) {
        return Product.builder()
        .setName("Novidades")
        .setDescription("Produto Novo")
        .setPrice(new BigDecimal(1.49))
        .setStock(10)
        .setCategoryId(categoryId)
        .setActive(true)
        .build();
    }

    public static Product getUpdateProduct(Long id, Long categoryId) {
        return Product.builder()
        .setId(id)
        .setName("Novidades")
        .setDescription("Produto Novo")
        .setPrice(new BigDecimal(1.49))
        .setStock(10)
        .setCategoryId(categoryId)
        .setActive(true)
        .build();
    }

    public static Product getProductValid(Long id, Long categoryId) {
        return Product.builder()
        .setId(id)
        .setName("Novidades")
        .setDescription("Produto Novo")
        .setPrice(new BigDecimal(1.49))
        .setStock(10)
        .setCategoryId(categoryId)
        .setActive(true)
        .build();
    }
    
}
