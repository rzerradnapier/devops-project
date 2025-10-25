package com.napier.devops.service;

import com.napier.devops.City;
import com.napier.devops.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CityService class.
 */
public class CityServiceTest {

    private CityService cityService;
    private CityRepository mockCityRepository;

    @BeforeEach
    public void setUp() {
        mockCityRepository = mock(CityRepository.class);
        cityService = new CityService(mockCityRepository);
    }

    @Test
    public void testGetAllCitiesByPopulation() {
        List<City> mockCities = Arrays.asList(
            new City().setAll(1024, "Mumbai", "IND", "Maharashtra", 10500000),
            new City().setAll(2331, "Seoul", "KOR", "Seoul", 9981619)
        );
        
        when(mockCityRepository.findAllOrderByPopulationDesc()).thenReturn(mockCities);

        List<City> result = cityService.getAllCitiesByPopulation();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Mumbai", result.get(0).getName());
        assertEquals("Seoul", result.get(1).getName());
        verify(mockCityRepository).findAllOrderByPopulationDesc();
    }

    @Test
    public void testHasCityDataWithValidData() {
        List<City> cities = Arrays.asList(
            new City().setAll(1, "New York", "USA", "New York", 8000000)
        );

        boolean result = cityService.hasCityData(cities);

        assertTrue(result);
    }

    @Test
    public void testHasCityDataWithEmptyList() {
        List<City> cities = Collections.emptyList();

        boolean result = cityService.hasCityData(cities);

        assertFalse(result);
    }

    @Test
    public void testHasCityDataWithNullList() {
        boolean result = cityService.hasCityData(null);

        assertFalse(result);
    }
}