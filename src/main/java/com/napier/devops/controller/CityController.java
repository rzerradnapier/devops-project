package com.napier.devops.controller;

import com.napier.devops.City;
import com.napier.devops.service.CityService;

import java.util.List;

/**
 * Controller class for handling City use cases.
 */
public class CityController {
    
    private final CityService cityService;
    
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }
    
    /**
     * Execute Use Case 7: Display all cities by population.
     */
    public void displayAllCitiesByPopulation() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("           USE CASE 7: ALL CITIES BY POPULATION");
        System.out.println("=".repeat(80));
        
        List<City> cities = cityService.getAllCitiesByPopulation();
        
        if (!cityService.hasCityData(cities)) {
            System.out.println("No city data found.");
            return;
        }
        
        System.out.println("\n=== REPORT: All Cities in the World by Population ===");
        System.out.println("Total cities: " + cities.size());
        System.out.println("Sorted by population (largest to smallest)\n");
        
        System.out.printf("%-8s %-35s %-12s %-20s %15s%n", 
                         "ID", "Name", "Country", "District", "Population");
        System.out.println("-".repeat(95));
        
        for (City city : cities) {
            System.out.printf("%-8d %-35s %-12s %-20s %,15d%n",
                             city.getId(),
                             city.getName(),
                             city.getCountryCode(),
                             city.getDistrict(),
                             city.getPopulation());
        }
        System.out.println("-".repeat(95));
    }
}