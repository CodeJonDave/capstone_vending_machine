package com.techelevator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.*;


public class VendingCSITest {
    private final VendingMachine vendingMachine = new VendingMachine();
    private ByteArrayOutputStream outputStream;
    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;

    @BeforeEach
    public void setup() {
        vendingMachine.stockInventory();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void finish() {
        System.setOut(originalSystemOut);
        System.setIn(originalSystemIn);
        try {
            outputStream.close();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    @Test
    public void testMenu() throws FileNotFoundException {
        String input = "1\n2\n1\n5.00\n2\nA1\n2\n\n3\n4\n3\n"; // Enter purchase menu and finish transaction
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        VendingCSI.main(new String[]{});

        String output = outputStream.toString();
        Assertions.assertTrue(output.contains("Display"));
        Assertions.assertTrue(output.contains(vendingMachine.displayInventoryItems()));
        Assertions.assertTrue(output.contains("Current Money Provided:"));
        Assertions.assertTrue(output.contains("Please enter the Slot ID"));
        Assertions.assertTrue(output.contains("Returning $"));
        Assertions.assertTrue(output.contains("|"));
    }
}