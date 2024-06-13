package com.techelevator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

public class VendingCSI {
    private static final Scanner USER_INPUT = new Scanner(System.in);
    private static final VendingMachine VENDING_MACHINE = new VendingMachine();

    // Main method to start the vending machine interaction
    public static void main(String[] args) {
        Logger.getInstance().logAction(LocalDateTime.now(), "New customer interaction detected\n");

        VENDING_MACHINE.stockInventory();  // Loads inventory from CSV file
        Logger.getInstance().logAction(LocalDateTime.now(), "Inventory Restocked\n");

        boolean running = true;
        while (running) {
            running = mainMenu();  // Displays main menu and processes user input
        }
    }

    // Displays main menu options and handles user input
    public static boolean mainMenu() {
        System.out.println("(1) Display Vending Machine Items");
        System.out.println("(2) Purchase");
        System.out.println("(3) Exit");

        String input = USER_INPUT.nextLine();

        switch (input.toLowerCase()) {
            case "1":
                displayItems();  // Displays all available items in the vending machine
                break;
            case "2":
                Logger.getInstance().logAction(LocalDateTime.now(), "Purchase Menu accessed\n");
                purchaseMenu();  // Enters the purchase menu to buy items
                break;
            case "3":
                Logger.getInstance().logSalesReport(VENDING_MACHINE, LocalDateTime.now());
                return false;  // Exits the program
            case "4":
                Logger.getInstance().logSalesReport(VENDING_MACHINE, LocalDateTime.now());
                System.out.println(VENDING_MACHINE.generateSalesReport());  // Displays sales report
                break;
            default:
                System.out.println("Invalid option. Please try again");
        }
        return true;  // Continue running the main menu loop
    }

    // Displays all items available for purchase in the vending machine
    private static void displayItems() {
        System.out.println(VENDING_MACHINE.displayInventoryItems());  // Prints details of all items
        Logger.getInstance().logAction(LocalDateTime.now(), "Displayed Items");
    }

    // Handles the purchase menu options and user interaction
    private static void purchaseMenu() {
        boolean running = true;
        while (running) {
            System.out.println("Current Money Provided: $" + VENDING_MACHINE.getBalance() + "\n");
            System.out.println("(1) Feed Money");
            System.out.println("(2) Select Product");
            System.out.println("(3) Finish Transaction");

            String input = USER_INPUT.nextLine();

            switch (input.toLowerCase()) {
                case "1":
                    feedMoney();  // Allows user to add money to their balance
                    break;
                case "2":
                    selectItem();  // Prompts user to select a product to purchase
                    break;
                case "3":
                    finishTransaction();  // Completes the transaction and dispenses change
                    running = false;  // Exits the purchase menu loop
                    break;
                default:
                    System.out.println("Invalid option. Please try again");
            }
        }
    }

    // Allows user to add money to their current balance
    private static void feedMoney() {
        System.out.println("Please enter Bills:");
        String input = USER_INPUT.nextLine();
        if (!input.isEmpty()) {
            try {
                BigDecimal amount = new BigDecimal(input);
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Please enter a positive amount.");
                } else {
                    // Accepts whole dollar amounts
                    VENDING_MACHINE.addMoney(amount);  // Updates balance with the inserted amount
                    System.out.println("Your new balance is: $" + VENDING_MACHINE.getBalance());
                    Logger.getInstance().logAction(
                            LocalDateTime.now(),
                            ("FEED MONEY: $" + amount + " $" + VENDING_MACHINE.getBalance())  // Logs the action
                    );
                }
            } catch (NumberFormatException | ArithmeticException e) {
                System.out.println("Invalid entry. Please enter a valid numerical amount.");
                Logger.getInstance().logError("Invalid amount entered: " + input);  // Logs errors
            }
        } else {
            Logger.getInstance().logAction(LocalDateTime.now(), "Returned to purchase menu");
            System.out.println("Returning to the previous menu...");
        }
    }

    // Prompts the user to select a product for purchase
    private static void selectItem() {
        System.out.println("Please enter the Slot ID");
        String input = USER_INPUT.nextLine();
        VENDING_MACHINE.makePurchase(input);  // Initiates the purchase process for the selected product
    }

    // Completes the transaction and dispenses any remaining balance
    private static void finishTransaction() {
        String change = VENDING_MACHINE.getBalance();
        System.out.println("Returning $" + change);
        VENDING_MACHINE.cashOut();  // Dispenses change in coins
        Logger.getInstance().logAction(LocalDateTime.now(), ("GIVE CHANGE: $" + change + " $0.00"));  // Logs the change dispensed
    }
}