package com.napier.devops.service;

import com.napier.devops.Country;
import com.napier.devops.repository.CountryRepository;

import java.util.List;

/**
 * Service class for Country business logic operations.
 */
public class CountryService {
    
    private final CountryRepository countryRepository;
    
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
    
    /**
     * Get country by code with validation.
     *
     * @param countryCode Country code to search for
     * @return Country object or null if not found
     */
    public Country getCountryByCode(String countryCode) {
        if (countryCode == null || countryCode.trim().isEmpty()) {
            System.out.println("Country code cannot be null or empty");
            return null;
        }
        
        if (countryRepository == null) {
            return null;
        }
        
        return countryRepository.findByCode(countryCode.toUpperCase());
    }
    
    /**
     * Get all countries sorted by population.
     *
     * @return List of countries ordered by population descending
     */
    public List<Country> getAllCountriesByPopulation() {
        if (countryRepository == null) {
            return null;
        }
        return countryRepository.findAllOrderByPopulationDesc();
    }
    
    /**
     * Validate if countries list is not empty.
     *
     * @param countries List of countries to validate
     * @return true if list has data, false otherwise
     */
    public boolean hasCountryData(List<Country> countries) {
        return countries != null && !countries.isEmpty();
    }
}