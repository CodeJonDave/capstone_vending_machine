package com.techelevator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class VendingMachineTest {
    private VendingMachine vendingMachine = new VendingMachine();

    @Before
    public void setUp() {

        vendingMachine = new VendingMachine();
        vendingMachine.stockInventory();
    }

    @After
    public void tearDown(){

    }

    @Test
    public void testAddMoney() {
        vendingMachine.addMoney(5);
        assertEquals("$5.00", vendingMachine.getBalance());
    }

    @Test
    public void testDisplayInventoryItems() {
        String inventoryDisplay = vendingMachine.displayInventoryItems();
        assertTrue(inventoryDisplay.contains("A1 Potato Crisps $3.05 5"));
        assertTrue(inventoryDisplay.contains("B1 Moonpie $1.80 5"));
        assertTrue(inventoryDisplay.contains("C1 Cola $1.25 5"));
    }

    @Test
    public void testMakePurchase() {
        vendingMachine.addMoney(5);
        vendingMachine.makePurchase("A1");
        assertEquals("$1.95", vendingMachine.getBalance());
        assertEquals(4, vendingMachine.getInventory().get("A1").getAmount());
    }

    @Test
    public void testMakePurchaseInsufficientFunds() {
        vendingMachine.addMoney(1);
        vendingMachine.makePurchase("A1");
        assertEquals("$1.00", vendingMachine.getBalance());
        assertEquals(5, vendingMachine.getInventory().get("A1").getAmount());
    }

    @Test
    public void testMakePurchaseSoldOut() {
        // Add enough money to cover multiple purchases
        vendingMachine.addMoney(25);

        // Purchase the product until it is sold out
        for (int i = 0; i < 5; i++) {
            vendingMachine.makePurchase("A1");
        }

        // Attempt to purchase once more after it is sold out
        vendingMachine.makePurchase("A1");

        // Check the balance after attempting to purchase a sold-out product
        assertEquals("$9.75", vendingMachine.getBalance());
        assertTrue(vendingMachine.getInventory().get("A1").isSoldOut());
    }

    @Test
    public void testCashOut() {
        vendingMachine.addMoney(5);
        vendingMachine.cashOut();
        assertEquals("$0.00", vendingMachine.getBalance());
    }

    @Test
    public void testGenerateSalesReport() {
        vendingMachine.addMoney(5);
        vendingMachine.makePurchase("A1");
        String salesReport = vendingMachine.generateSalesReport();
        assertTrue(salesReport.contains("Potato Crisps|1"));
        assertTrue(salesReport.contains("**TOTAL SALES** $3.05"));
    }

    @Test
    public void testLoggerSingleton() {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        assertSame(logger1, logger2);
    }

    @Test
    public void testLogAction() {
        Logger.logAction(LocalDateTime.now(), "Test action");
        File logFile = new File("logs/log.txt");
        assertTrue(logFile.exists());
    }

    @Test
    public void testLogSalesReport() {
        Logger.logSalesReport(vendingMachine,LocalDateTime.now());
        File salesReportDir = new File("logs/Tests/");
        assertTrue(salesReportDir.exists());
        File[] files = salesReportDir.listFiles((dir, name) -> name.endsWith(".txt"));
        assertNotNull(files);
        assertTrue(files.length > 0);
    }
}