package com.napier.devops.service;

import com.napier.devops.City;
import com.napier.devops.repository.CityRepository;

import java.util.List;

/**
 * Service class for City business logic operations.
 */
public class CityService {
    
    private final CityRepository cityRepository;
    
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }
    
    /**
     * Get all cities sorted by population.
     *
     * @return List of cities ordered by population descending
     */
    public List<City> getAllCitiesByPopulation() {
        if (cityRepository == null) {
            return null;
        }
        return cityRepository.findAllOrderByPopulationDesc();
    }
    
    /**
     * Validate if cities list is not empty.
     *
     * @param cities List of cities to validate
     * @return true if list has data, false otherwise
     */
    public boolean hasCityData(List<City> cities) {
        return cities != null && !cities.isEmpty();
    }
}