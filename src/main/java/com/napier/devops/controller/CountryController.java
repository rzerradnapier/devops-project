package com.napier.devops.controller;

import com.napier.devops.Country;
import com.napier.devops.service.CountryService;

import java.util.List;

/**
 * Controller class for handling Country use cases.
 */
public class CountryController {
    
    private final CountryService countryService;
    
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }
    
    /**
     * Execute Use Case 1: Display all countries by population.
     */
    public void displayAllCountriesByPopulation() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("           USE CASE 1: ALL COUNTRIES BY POPULATION");
        System.out.println("=".repeat(80));
        
        List<Country> countries = countryService.getAllCountriesByPopulation();
        
        if (!countryService.hasCountryData(countries)) {
            System.err.println("Error: No country data found.");
            return;
        }
        
        System.out.println("Total countries: " + countries.size());
        System.out.println("Sorted by population (largest to smallest)\n");
        
        for (Country country : countries) {
            System.out.println(country.toString());
        }
    }
    
    /**
     * Execute Use Case 2: Find country by code.
     *
     * @param countryCode Country code to search for
     */
    public void displayCountryByCode(String countryCode) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("           USE CASE 2: FIND COUNTRY BY CODE (" + countryCode + ")");
        System.out.println("=".repeat(80));
        
        Country country = countryService.getCountryByCode(countryCode);
        
        if (country != null) {
            System.out.println(country.toString());
        } else {
            System.out.println("No country information found for code: " + countryCode);
        }
    }
}