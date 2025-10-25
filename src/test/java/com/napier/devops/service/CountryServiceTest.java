package com.napier.devops.service;

import com.napier.devops.Country;
import com.napier.devops.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CountryService class.
 */
public class CountryServiceTest {

    private CountryService countryService;
    private CountryRepository mockCountryRepository;

    @BeforeEach
    public void setUp() {
        mockCountryRepository = mock(CountryRepository.class);
        countryService = new CountryService(mockCountryRepository);
    }

    @Test
    public void testGetCountryByCodeSuccess() {
        Country mockCountry = new Country();
        mockCountry.setCode("USA");
        mockCountry.setName("United States");
        
        when(mockCountryRepository.findByCode("USA")).thenReturn(mockCountry);

        Country result = countryService.getCountryByCode("usa");

        assertNotNull(result);
        assertEquals("USA", result.getCode());
        assertEquals("United States", result.getName());
        verify(mockCountryRepository).findByCode("USA");
    }

    @Test
    public void testGetCountryByCodeNullInput() {
        Country result = countryService.getCountryByCode(null);

        assertNull(result);
        verify(mockCountryRepository, never()).findByCode(anyString());
    }

    @Test
    public void testGetCountryByCodeEmptyInput() {
        Country result = countryService.getCountryByCode("");

        assertNull(result);
        verify(mockCountryRepository, never()).findByCode(anyString());
    }

    @Test
    public void testGetCountryByCodeWhitespaceInput() {
        Country result = countryService.getCountryByCode("   ");

        assertNull(result);
        verify(mockCountryRepository, never()).findByCode(anyString());
    }

    @Test
    public void testGetCountryByCodeNotFound() {
        when(mockCountryRepository.findByCode("XXX")).thenReturn(null);

        Country result = countryService.getCountryByCode("xxx");

        assertNull(result);
        verify(mockCountryRepository).findByCode("XXX");
    }

    @Test
    public void testGetAllCountriesByPopulation() {
        List<Country> mockCountries = Arrays.asList(
            new Country().setAll("CHN", "China", "Asia", "Eastern Asia", 1439323776, 1891),
            new Country().setAll("IND", "India", "Asia", "Southern and Central Asia", 1380004385, 1109)
        );
        
        when(mockCountryRepository.findAllOrderByPopulationDesc()).thenReturn(mockCountries);

        List<Country> result = countryService.getAllCountriesByPopulation();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("CHN", result.get(0).getCode());
        assertEquals("IND", result.get(1).getCode());
        verify(mockCountryRepository).findAllOrderByPopulationDesc();
    }

    @Test
    public void testHasCountryDataWithValidData() {
        List<Country> countries = Arrays.asList(
            new Country().setAll("USA", "United States", "North America", "North America", 331000000, 3813)
        );

        boolean result = countryService.hasCountryData(countries);

        assertTrue(result);
    }

    @Test
    public void testHasCountryDataWithEmptyList() {
        List<Country> countries = Collections.emptyList();

        boolean result = countryService.hasCountryData(countries);

        assertFalse(result);
    }

    @Test
    public void testHasCountryDataWithNullList() {
        boolean result = countryService.hasCountryData(null);

        assertFalse(result);
    }
}