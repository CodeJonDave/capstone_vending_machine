package com.techelevator;

import java.math.BigDecimal;

public class Chip extends Product{
    public Chip(String slot, String name, BigDecimal price) {
        super(slot, name, price);
    }

    @Override
    public String getDispenseMessage() {
        return "Crunch Crunch, Yum!";
    }


}
