package com.techelevator;

import java.math.BigDecimal;

public class Drink extends Product{
    public Drink(String slot, String name, BigDecimal price) {
        super(slot, name, price);
    }

    @Override
    public String getDispenseMessage() {
        return "Glug Glug, Yum!";
    }
}
