package com.techelevator;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

class ProductTest {
    Candy item = new Candy("P6","Werthers",new BigDecimal("4.76"));

    @Test
    public void testConstructorAndGetters(){
        Assertions.assertEquals("P6", item.getSlot());
        Assertions.assertEquals("Werthers", item.getName());
        Assertions.assertEquals(new BigDecimal("4.76"), item.getPrice());
        Assertions.assertEquals(5,item.getAmount());
        Assertions.assertFalse(item.isSoldOut());
    }

    @Test
    public void testDispense(){
        item.dispense();
        Assertions.assertEquals(4,item.getAmount());
        item.dispense();
        Assertions.assertEquals(3,item.getAmount());
        item.dispense();
        Assertions.assertEquals(2,item.getAmount());
        item.dispense();
        Assertions.assertEquals(1,item.getAmount());
        item.dispense();
        Assertions.assertEquals(0,item.getAmount());
        Assertions.assertTrue(item.isSoldOut());
        item.dispense();
        Assertions.assertEquals(0,item.getAmount());
        Assertions.assertTrue(item.isSoldOut());
    }

    @Test
    public void testDispenseMessage(){
        Assertions.assertEquals("Munch Munch, Yum!",item.getDispenseMessage());
    }

}
