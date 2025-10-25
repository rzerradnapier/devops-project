package com.napier.devops.ui;

import com.napier.devops.controller.CityController;
import com.napier.devops.controller.CountryController;

import java.util.Scanner;

import static com.napier.constant.Constant.DEFAULT_COUNTRY_CODE;

/**
 * User Interface class for displaying menu and handling user interactions.
 */
public class MenuUI {
    
    private final CountryController countryController;
    private final CityController cityController;
    private final Scanner scanner;
    
    public MenuUI(CountryController countryController, CityController cityController) {
        this.countryController = countryController;
        this.cityController = cityController;
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Display interactive menu for selecting use cases.
     */
    public void displayMenu() {
        boolean running = true;
        
        while (running) {
            printMenuHeader();
            printMenuOptions();
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                running = handleMenuChoice(choice);
                
                if (running && choice >= 1 && choice <= 3) {
                    waitForUserInput();
                }
                
            } catch (Exception e) {
                System.out.println("\n❌ Please enter a valid number!");
                scanner.nextLine(); // clear invalid input
            }
        }
    }
    
    /**
     * Print menu header.
     */
    private void printMenuHeader() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           WORLD DATABASE REPORTING SYSTEM");
        System.out.println("=".repeat(60));
        System.out.println("Select a use case to execute:");
        System.out.println();
    }
    
    /**
     * Print menu options.
     */
    private void printMenuOptions() {
        System.out.println("1. Display all countries (sorted by population)");
        System.out.println("2. Find country by code (USA)");
        System.out.println("3. USE CASE 7: All cities by population");
        System.out.println("4. Additional use cases (coming soon...)");
        System.out.println("0. Exit");
        System.out.println("=".repeat(60));
        System.out.print("Enter your choice (0-4): ");
    }
    
    /**
     * Handle user menu choice.
     *
     * @param choice User's menu choice
     * @return true to continue running, false to exit
     */
    private boolean handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                countryController.displayAllCountriesByPopulation();
                break;
            case 2:
                countryController.displayCountryByCode(DEFAULT_COUNTRY_CODE);
                break;
            case 3:
                cityController.displayAllCitiesByPopulation();
                break;
            case 4:
                System.out.println("\n⚠️  This use case will be added in the future!");
                break;
            case 0:
                System.out.println("\n👋 Thank you for using the system! Goodbye!");
                return false;
            default:
                System.out.println("\n❌ Invalid choice! Please select from 0-4.");
        }
        return true;
    }
    
    /**
     * Wait for user input to continue.
     */
    private void waitForUserInput() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Close scanner resources.
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}