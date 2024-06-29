package com.techelevator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class VendingMachine {
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
    private final String INVENTORY_FILE = "vendingmachine.csv";
    private final Map<String, Product> inventory = new HashMap<>();
    private BigDecimal balance = BigDecimal.ZERO;
    private BigDecimal totalSales = BigDecimal.ZERO;


    // Getter methods for balance and total sales
    public String getTotalSales() {
        return currencyFormatter.format(totalSales);
    }

    public String getBalance() {
        return currencyFormatter.format(balance);
    }

    // Method to add money to balance
    public void addMoney(int amount) {
        balance = balance.add(new BigDecimal(amount));
    }

    public Map<String, Product> getInventory() {
        return inventory;
    }

    // Method to read inventory from a CSV file
    public void stockInventory() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INVENTORY_FILE)) {
            assert inputStream != null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    try {
                        String[] data = line.split("\\|");
                        String slot = data[0].trim();
                        String name = data[1].trim();
                        BigDecimal price = new BigDecimal(data[2].trim());
                        String Type = data[3].trim();

                        // Assuming your Product class has a constructor like this:
                        switch (Type) {
                            case "Chip":
                                Chip chip = new Chip(slot, name, price);
                                inventory.put(chip.getSlot(), chip);
                                break;
                            case "Candy":
                                Candy candy = new Candy(slot, name, price);
                                inventory.put(candy.getSlot(), candy);
                                break;
                            case "Drink":
                                Drink drink = new Drink(slot, name, price);
                                inventory.put(drink.getSlot(), drink);
                                break;
                            case "Gum":
                                Gum gum = new Gum(slot, name, price);
                                inventory.put(gum.getSlot(), gum);
                                break;
                        }

                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        // Log and handle specific errors related to parsing or incorrect format
                        Logger.getInstance().logError(LocalDateTime.now(), e.getMessage());
                        System.err.println("Error parsing inventory line: " + line);
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            Logger.getInstance().logError(LocalDateTime.now(), e.getMessage());
            System.err.println("Error reading the inventory file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to display inventory items
    public String displayInventoryItems() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Product> entry : inventory.entrySet()) {
            sb.append(entry.getKey()).append(" ")
                    .append(entry.getValue().getName()).append(" ")
                    .append(currencyFormatter.format(entry.getValue().getPrice())).append(" ")
                    .append(entry.getValue().isSoldOut() ? "SOLD OUT" : entry.getValue().getAmount())
                    .append("\n");
        }
        return sb.toString();
    }

    // Method to handle purchasing a product
    public void makePurchase(String location) {
        Product item = inventory.get(location);
        if (item == null) {
            System.out.println("Invalid product code.");
            return;
        }
        if (balance.compareTo(item.getPrice()) < 0) {
            System.out.println("Insufficient funds.");
            return;
        }
        if (item.isSoldOut()) {
            System.out.println(item.getName() + ": SOLD OUT");
            return;
        }

        balance = balance.subtract(item.getPrice());
        totalSales = totalSales.add(item.getPrice());
        item.dispense();
        System.out.println(item.getDispenseMessage());
        Logger.logAction(LocalDateTime.now(),
                (item.getName() + " "
                        + item.getSlot() + " "
                        + currencyFormatter.format(item.getPrice()) + " "
                        + getBalance())

        );
    }

    // Method to dispense change
    public void cashOut() {
        BigDecimal change = balance;
        int quarters = change.divide(BigDecimal.valueOf(0.25)).intValue();
        balance = balance.subtract(BigDecimal.valueOf(quarters).multiply(BigDecimal.valueOf(0.25)));
        int dimes = balance.divide(BigDecimal.valueOf(0.1)).intValue();
        balance = balance.subtract(BigDecimal.valueOf(dimes).multiply(BigDecimal.valueOf(0.1)));
        int nickels = balance.divide(BigDecimal.valueOf(0.05)).intValue();
        balance = balance.subtract(BigDecimal.valueOf(nickels).multiply(BigDecimal.valueOf(0.05)));

        if (quarters > 0) {
            System.out.println("Dispensing " + quarters + " quarters");
        }
        if (dimes > 0) {
            System.out.println("Dispensing " + dimes + " dimes");
        }
        if (nickels > 0) {
            System.out.println("Dispensing " + nickels + " nickels");
        }

        balance = BigDecimal.ZERO;  // Reset balance after dispensing change
    }

    // Method to generate a sales report
    public String generateSalesReport() {
        int startingQuantity = 5;  // Initial quantity of each product
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Product> entry : inventory.entrySet()) {
            sb.append(entry.getValue().getName())
                    .append("|").append(startingQuantity - entry.getValue().getAmount())
                    .append("\n");
        }
        sb.append("\n").append("**TOTAL SALES** ").append(currencyFormatter.format(totalSales));
        return sb.toString();
    }
}
