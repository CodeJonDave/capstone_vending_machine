# Capstone Vending Machine

Capstone Vending Machine is a Java-based console application that simulates
a real-world vending machine with inventory management, transactional logic,
and persistent state.

The project emphasizes correctness, clear domain modeling, and defensive
handling of money-related operations.

---

## What the Application Does

- Loads vending machine inventory from a data file
- Displays items with pricing and stock counts
- Accepts money input
- Allows item purchases with balance validation
- Dispenses change using correct denominations
- Logs all transactions and state changes
- Persists inventory state across runs

---

## Architecture Overview

The application is structured around clear responsibilities:

- **Models** represent vending items and money
- **Services** manage purchasing and balance logic
- **CLI interface** handles user interaction
- **File I/O** manages inventory loading and logging

The design avoids monolithic logic and keeps concerns isolated.

---

## Key Features

- Accurate money handling using `BigDecimal`
- Inventory depletion enforcement
- Transaction logging to file
- Graceful handling of invalid input
- Deterministic behavior suitable for testing

---

## Tech Stack

- Java
- Maven
- JUnit (testing)
- File-based persistence
- Console (CLI) interface

---

## Repository Structure

capstone_vending_machine/
├── src/
│ ├── main/java/...
│ └── test/java/...
├── vendingmachine.csv
├── logs/
└── README.md


---

## How to Run

1. Clone the repository
2. Build the project:
   ```bash
   mvn clean package
Run the application:

java -jar target/<jar-name>.jar
Testing
Unit tests validate core purchasing and balance logic

Tests focus on correctness rather than UI behavior

Status
Core functionality: complete

Logging: implemented

Tests: present

UI: console-based by design

Enhancements possible:

REST API wrapper

Database-backed inventory

Web or GUI client

Portfolio Intent
This project demonstrates:

Strong Java fundamentals

Safe handling of financial values

Thoughtful separation of concerns

Practical testing and logging

Delivering a complete, working application

It is intentionally simple in scope but solid in execution.