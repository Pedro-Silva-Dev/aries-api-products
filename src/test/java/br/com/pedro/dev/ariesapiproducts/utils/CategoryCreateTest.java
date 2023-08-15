package br.com.pedro.dev.ariesapiproducts.utils;

import br.com.pedro.dev.ariesapiproducts.models.Category;

public class CategoryCreateTest {

    public static Category getCreateCategory() {
        return Category.builder()
        .setName("Novidades")
        .setActive(true)
        .build();
    }

    public static Category getUpdateCategory(Long id) {
        return Category.builder()
        .setId(id)
        .setName("Novidades")
        .setActive(true)
        .build();
    }

    public static Category getCategoryValid(Long id) {
        return Category.builder()
        .setId(id)
        .setName("Novidades")
        .setActive(true)
        .build();
    }
}
