package com.techelevator;

import java.math.BigDecimal;

public class Product {
    private String slot;
    private String name;
    private BigDecimal price;
    private String type;
    private int amount = 5;  // Initial amount of product
    private boolean isSoldOut = false;  // Indicates if the product is sold out

    // Constructor to initialize product details
    public Product(String slot, String name, BigDecimal price, String type) {
        this.slot = slot;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    // Getters for various product attributes
    public int getAmount() {
        return amount;
    }

    public String getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public boolean isSoldOut() {
        return isSoldOut;
    }

    // Method to dispense the product and update its availability
    public void dispense() {
        if (amount > 0) {
            this.amount--;
            if (amount == 0) {
                isSoldOut = true;
            }
        }
    }

    // Returns a message based on the product type when dispensed
    public String getDispenseMessage() {
        switch (this.type.toLowerCase()) {
            case "chip":
                return "Crunch Crunch, Yum!";
            case "candy":
                return "Munch Munch, Yum!";
            case "drink":
                return "Glug Glug, Yum!";
            case "gum":
                return "Chew Chew, Yum!";
            default:
                return "";
        }
    }
}