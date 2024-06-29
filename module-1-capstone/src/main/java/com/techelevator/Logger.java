package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static Logger instance;
    private static final File LOG_FILE = new File("logs/log.txt");
    private static final String SALES_REPORT_DIR = "logs/sales-reports/";


    private Logger() {
    }

    // Singleton method to ensure only one instance of Logger exists
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }


    // Logs an action with a timestamp and description to the log file
    public static void logAction(LocalDateTime time, String action) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(LOG_FILE, true))) {
            writer.println(time.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss a")) + " " + action);
        } catch (FileNotFoundException e) {
            System.err.println("Could not find log file: " + e.getMessage());
        }
    }


    // Logs an error message with a timestamp to the log file
    public void logError(LocalDateTime now, String error) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(LOG_FILE, true))) {
            writer.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss a")) + " ERROR: " + error);
        } catch (FileNotFoundException e) {
            System.err.println("Could not find log file: " + e.getMessage());
        }
    }

    // Generates and logs a sales report to a specific file
    public static void logSalesReport(VendingMachine vendingMachine, LocalDateTime time) {
        File salesReportDir = new File(SALES_REPORT_DIR);
        if (!salesReportDir.exists()) {
            salesReportDir.mkdirs();  // Creates directory if it doesn't exist
        }

        String fileName = SALES_REPORT_DIR + time.format(DateTimeFormatter.ofPattern("MM-dd-yyyy_HH-mm-ss")) + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(fileName, true))) {
            writer.println(vendingMachine.generateSalesReport());  // Writes sales report content to file
        } catch (FileNotFoundException e) {
            System.err.println("Could not find sales report file: " + e.getMessage());
        }
    }
}
