package com.techelevator;

import java.math.BigDecimal;

public class Gum extends Product{
    public Gum(String slot, String name, BigDecimal price) {
        super(slot, name, price);
    }

    @Override
    public String getDispenseMessage() {
        return "Chew Chew, Yum!";
    }
}
