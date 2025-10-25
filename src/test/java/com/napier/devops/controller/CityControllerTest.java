package com.napier.devops.controller;

import com.napier.devops.City;
import com.napier.devops.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CityController class.
 */
public class CityControllerTest {

    @Mock
    private CityService cityService;

    private CityController cityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cityController = new CityController(cityService);
    }

    @Test
    void testConstructorWithValidService() {
        CityService service = mock(CityService.class);
        CityController controller = new CityController(service);
        assertNotNull(controller);
    }

    @Test
    void testConstructorWithNullService() {
        CityController controller = new CityController(null);
        assertNotNull(controller);
    }

    @Test
    void testDisplayAllCitiesByPopulationSuccess() {
        // Arrange
        List<City> expectedCities = Arrays.asList(
            new City().setAll(1, "Tokyo", "JPN", "Tokyo-to", 37400068),
            new City().setAll(2, "Delhi", "IND", "Delhi", 28514000),
            new City().setAll(3, "Shanghai", "CHN", "Shanghai", 25582000)
        );
        when(cityService.getAllCitiesByPopulation()).thenReturn(expectedCities);
        when(cityService.hasCityData(expectedCities)).thenReturn(true);

        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> cityController.displayAllCitiesByPopulation());
        verify(cityService, times(1)).getAllCitiesByPopulation();
        verify(cityService, times(1)).hasCityData(expectedCities);
    }

    @Test
    void testDisplayAllCitiesByPopulationEmpty() {
        // Arrange
        List<City> emptyList = Collections.emptyList();
        when(cityService.getAllCitiesByPopulation()).thenReturn(emptyList);
        when(cityService.hasCityData(emptyList)).thenReturn(false);

        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> cityController.displayAllCitiesByPopulation());
        verify(cityService, times(1)).getAllCitiesByPopulation();
        verify(cityService, times(1)).hasCityData(emptyList);
    }

    @Test
    void testDisplayAllCitiesByPopulationWithNullService() {
        // Arrange
        CityController controller = new CityController(null);

        // Act & Assert - should throw NullPointerException
        assertThrows(NullPointerException.class, () -> controller.displayAllCitiesByPopulation());
    }

    @Test
    void testDisplayAllCitiesByPopulationServiceReturnsNull() {
        // Arrange
        when(cityService.getAllCitiesByPopulation()).thenReturn(null);
        when(cityService.hasCityData(null)).thenReturn(false);

        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> cityController.displayAllCitiesByPopulation());
        verify(cityService, times(1)).getAllCitiesByPopulation();
        verify(cityService, times(1)).hasCityData(null);
    }
}