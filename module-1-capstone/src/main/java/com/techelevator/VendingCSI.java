package com.techelevator;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class VendingCSI {
    // Scanner for user input
    private static final Scanner USER_INPUT = new Scanner(System.in);

    // Vending machine instance
    private static final VendingMachine VENDING_MACHINE = new VendingMachine();

    public static void main(String[] args) throws FileNotFoundException {
        // Log the start of a new customer interaction
        Logger.logAction(LocalDateTime.now(), "New customer interaction detected");


        // Stock inventory in the vending machine
        VENDING_MACHINE.stockInventory();


        Logger.logAction(LocalDateTime.now(), "Inventory Restocked");


        // Main loop for displaying menu options and handling user input
        boolean running = true;
        while (running) {
            running = mainMenu(); // Display the main menu and continue running based on user input
        }
    }

    // Method to display the main menu and handle user choices
    public static boolean mainMenu() {
        System.out.println("(1) Display Vending Machine Items");
        System.out.println("(2) Purchase");
        System.out.println("(3) Exit");

        String input = USER_INPUT.nextLine();

        switch (input.toLowerCase()) {
            case "1":
                displayItems();
                break;
            case "2":
                purchaseMenu();
                break;
            case "3":
                // Log sales report and exit the program
                Logger.logSalesReport(VENDING_MACHINE, LocalDateTime.now());

                return false;
            case "4":
                // Log sales report and display sales report

                Logger.logSalesReport(VENDING_MACHINE, LocalDateTime.now());

                System.out.println(VENDING_MACHINE.generateSalesReport());
                break;
            default:
                System.out.println("Invalid option. Please try again");
        }
        return true;
    }

    // Method to display vending machine items
    private static void displayItems() {
        System.out.println(VENDING_MACHINE.displayInventoryItems());
        Logger.logAction(LocalDateTime.now(), "Displayed Items");

    }

    // Method to handle purchase menu options
    private static void purchaseMenu() {
        boolean running = true;
        while (running) {
            System.out.println("Current Money Provided: " + VENDING_MACHINE.getBalance());
            System.out.println("(1) Feed Money");
            System.out.println("(2) Select Product");
            System.out.println("(3) Finish Transaction");

            String input = USER_INPUT.nextLine();

            switch (input.toLowerCase()) {
                case "1":
                    feedMoney();
                    break;
                case "2":
                    selectItem();
                    break;
                case "3":
                    finishTransaction();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again");
            }
        }
    }

    // Method to handle feeding money into the vending machine
    private static void feedMoney() {
        System.out.println("Please enter a whole dollar amount (press enter to go back):");
        String input = USER_INPUT.nextLine();
       if (!input.isEmpty()) {
            try {
                int amount = Integer.parseInt(input);
                if (amount < 0) {
                    System.out.println("Please enter a positive amount.");
                } else if (amount > 1000) {
                    System.out.println("Please enter a smaller amount.");
                } else {
                    VENDING_MACHINE.addMoney(amount);
                    System.out.println("Your new balance is: " + VENDING_MACHINE.getBalance());
                    // Log the action of feeding money into the vending machine

                    Logger.logAction(LocalDateTime.now(), "FEED MONEY: $" + amount + " " + VENDING_MACHINE.getBalance());
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid entry. Please use numerical #.## format.");
            }
        } else {

            Logger.logAction(LocalDateTime.now(), "Returned to purchase menu");

            System.out.println("Returning to the previous menu...");
        }
    }

    // Method to handle selecting a product from the vending machine
    private static void selectItem() {
        System.out.println(VENDING_MACHINE.displayInventoryItems());
        System.out.println("Please enter the Slot ID");
        String input = USER_INPUT.nextLine();
        VENDING_MACHINE.makePurchase(input); // Attempt to make a purchase with the given slot ID
    }

    // Method to finish the transaction and return change
    private static void finishTransaction() {
        String change = VENDING_MACHINE.getBalance();
        System.out.println("Returning " + change);
        VENDING_MACHINE.cashOut(); // Return the remaining balance
        Logger.logAction(LocalDateTime.now(), "GIVE CHANGE: " + change + " " + VENDING_MACHINE.getBalance());
        // Log the action of returning change
    }
}