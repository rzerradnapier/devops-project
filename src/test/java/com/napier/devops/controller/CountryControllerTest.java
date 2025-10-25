package com.napier.devops.controller;

import com.napier.devops.Country;
import com.napier.devops.service.CountryService;
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
 * Unit tests for CountryController class.
 */
public class CountryControllerTest {

    @Mock
    private CountryService countryService;

    private CountryController countryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        countryController = new CountryController(countryService);
    }

    @Test
    void testConstructorWithValidService() {
        CountryService service = mock(CountryService.class);
        CountryController controller = new CountryController(service);
        assertNotNull(controller);
    }

    @Test
    void testConstructorWithNullService() {
        CountryController controller = new CountryController(null);
        assertNotNull(controller);
    }

    @Test
    void testDisplayCountryByCodeSuccess() {
        // Arrange
        String countryCode = "USA";
        Country expectedCountry = new Country().setAll("USA", "United States", "North America", "North America", 331000000, 3813);
        when(countryService.getCountryByCode(countryCode)).thenReturn(expectedCountry);

        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> countryController.displayCountryByCode(countryCode));
        verify(countryService, times(1)).getCountryByCode(countryCode);
    }

    @Test
    void testDisplayCountryByCodeNotFound() {
        // Arrange
        String countryCode = "XXX";
        when(countryService.getCountryByCode(countryCode)).thenReturn(null);

        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> countryController.displayCountryByCode(countryCode));
        verify(countryService, times(1)).getCountryByCode(countryCode);
    }

    @Test
    void testDisplayCountryByCodeWithNullService() {
        // Arrange
        CountryController controller = new CountryController(null);

        // Act & Assert - should throw NullPointerException
        assertThrows(NullPointerException.class, () -> controller.displayCountryByCode("USA"));
    }

    @Test
    void testDisplayAllCountriesByPopulationSuccess() {
        // Arrange
        List<Country> expectedCountries = Arrays.asList(
            new Country().setAll("CHN", "China", "Asia", "Eastern Asia", 1439323776, 1001),
            new Country().setAll("IND", "India", "Asia", "Southern and Central Asia", 1380004385, 1002),
            new Country().setAll("USA", "United States", "North America", "North America", 331000000, 1003)
        );
        when(countryService.getAllCountriesByPopulation()).thenReturn(expectedCountries);
        when(countryService.hasCountryData(expectedCountries)).thenReturn(true);

        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> countryController.displayAllCountriesByPopulation());
        verify(countryService, times(1)).getAllCountriesByPopulation();
        verify(countryService, times(1)).hasCountryData(expectedCountries);
    }

    @Test
    void testDisplayAllCountriesByPopulationEmpty() {
        // Arrange
        List<Country> emptyList = Collections.emptyList();
        when(countryService.getAllCountriesByPopulation()).thenReturn(emptyList);
        when(countryService.hasCountryData(emptyList)).thenReturn(false);

        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> countryController.displayAllCountriesByPopulation());
        verify(countryService, times(1)).getAllCountriesByPopulation();
        verify(countryService, times(1)).hasCountryData(emptyList);
    }

    @Test
    void testDisplayAllCountriesByPopulationWithNullService() {
        // Arrange
        CountryController controller = new CountryController(null);

        // Act & Assert - should throw NullPointerException
        assertThrows(NullPointerException.class, () -> controller.displayAllCountriesByPopulation());
    }

    @Test
    void testDisplayAllCountriesByPopulationServiceReturnsNull() {
        // Arrange
        when(countryService.getAllCountriesByPopulation()).thenReturn(null);
        when(countryService.hasCountryData(null)).thenReturn(false);

        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> countryController.displayAllCountriesByPopulation());
        verify(countryService, times(1)).getAllCountriesByPopulation();
        verify(countryService, times(1)).hasCountryData(null);
    }
}