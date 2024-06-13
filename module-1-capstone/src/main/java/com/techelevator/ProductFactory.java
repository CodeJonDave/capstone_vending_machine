
package com.techelevator;

import java.math.BigDecimal;
import java.util.List;

public class ProductFactory {
    // Factory method to create a Product object from a list of item details
    public static Product createProduct(List<String> item) {
        String slot = item.get(0);
        String name = item.get(1);
        BigDecimal price = new BigDecimal(item.get(2));
        String type = item.get(3);
        return new Product(slot, name, price, type);
    }
}
