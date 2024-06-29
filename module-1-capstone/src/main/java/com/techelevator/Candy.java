package com.techelevator;

import java.math.BigDecimal;

public class Candy extends Product{
    public Candy(String slot, String name, BigDecimal price) {
        super(slot, name, price);
    }

    @Override
    public String getDispenseMessage() {
        return "Munch Munch, Yum!";
    }
}
