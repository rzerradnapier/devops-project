package com.napier.devops.service;

import com.napier.devops.City;
import com.napier.devops.Country;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple tests for service layer without complex mocking.
 */
public class ServiceLayerTest {

    @Test
    public void testCountryServiceInstantiation() {
        CountryService service = new CountryService(null);
        assertNotNull(service);
    }

    @Test
    public void testCityServiceInstantiation() {
        CityService service = new CityService(null);
        assertNotNull(service);
    }

    @Test
    public void testCountryServiceValidation() {
        CountryService service = new CountryService(null);
        
        // Test null input validation
        Country result1 = service.getCountryByCode(null);
        assertNull(result1);
        
        // Test empty input validation
        Country result2 = service.getCountryByCode("");
        assertNull(result2);
        
        // Test whitespace input validation
        Country result3 = service.getCountryByCode("   ");
        assertNull(result3);
    }

    @Test
    public void testCountryServiceHasCountryData() {
        CountryService service = new CountryService(null);
        
        // Test with null list
        assertFalse(service.hasCountryData(null));
        
        // Test with empty list
        assertFalse(service.hasCountryData(Collections.emptyList()));
        
        // Test with valid data
        List<Country> countries = new ArrayList<>();
        countries.add(new Country());
        assertTrue(service.hasCountryData(countries));
    }

    @Test
    public void testCityServiceHasCityData() {
        CityService service = new CityService(null);
        
        // Test with null list
        assertFalse(service.hasCityData(null));
        
        // Test with empty list
        assertFalse(service.hasCityData(Collections.emptyList()));
        
        // Test with valid data
        List<City> cities = new ArrayList<>();
        cities.add(new City());
        assertTrue(service.hasCityData(cities));
    }

    @Test
    public void testCountryServiceWithValidInput() {
        CountryService service = new CountryService(null);
        
        // Test validation with valid format but null repository
        // This should return null due to null repository validation
        Country result = service.getCountryByCode("USA");
        assertNull(result, "Should return null when repository is null");
    }

    @Test
    public void testServiceLayerDataValidation() {
        CountryService countryService = new CountryService(null);
        CityService cityService = new CityService(null);
        
        // Test validation methods that don't require repository
        assertFalse(countryService.hasCountryData(new ArrayList<>()));
        assertFalse(cityService.hasCityData(new ArrayList<>()));
        
        // Test with actual data
        List<Country> countries = List.of(new Country().setAll("USA", "United States", "North America", "North America", 331000000, 3813));
        List<City> cities = List.of(new City().setAll(1, "New York", "USA", "New York", 8000000));
        
        assertTrue(countryService.hasCountryData(countries));
        assertTrue(cityService.hasCityData(cities));
    }
}