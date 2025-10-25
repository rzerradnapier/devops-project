package com.napier.devops.ui;

import com.napier.devops.controller.CityController;
import com.napier.devops.controller.CountryController;
import com.napier.devops.service.CityService;
import com.napier.devops.service.CountryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MenuUI class.
 * Simplified to avoid System.in/out issues that cause JVM crashes.
 */
public class MenuUITest {

    private MenuUI menuUI;
    private CountryController countryController;
    private CityController cityController;

    @BeforeEach
    public void setUp() {
        // Create real controllers with null services to avoid mocking issues
        CountryService countryService = new CountryService(null);
        CityService cityService = new CityService(null);
        
        countryController = new CountryController(countryService);
        cityController = new CityController(cityService);
        
        menuUI = new MenuUI(countryController, cityController);
    }

    @Test
    public void testMenuUIInstantiation() {
        assertNotNull(menuUI);
    }

    @Test
    public void testMenuUIWithNullControllers() {
        assertDoesNotThrow(() -> {
            MenuUI nullMenuUI = new MenuUI(null, null);
            assertNotNull(nullMenuUI);
        });
    }

    @Test
    public void testMenuUIWithValidControllers() {
        CountryService countryService = new CountryService(null);
        CityService cityService = new CityService(null);
        CountryController cc = new CountryController(countryService);
        CityController cic = new CityController(cityService);
        
        MenuUI testMenuUI = new MenuUI(cc, cic);
        assertNotNull(testMenuUI);
    }

    @Test
    public void testClose() {
        assertDoesNotThrow(() -> menuUI.close());
    }

    @Test
    public void testCloseMultipleTimes() {
        assertDoesNotThrow(() -> {
            menuUI.close();
            menuUI.close();
            menuUI.close();
        });
    }

    @Test
    public void testMenuUIStateManagement() {
        assertNotNull(menuUI);
        
        // Test that MenuUI maintains state correctly
        menuUI.close();
        
        // Should still be able to create new instances
        MenuUI newMenuUI = new MenuUI(countryController, cityController);
        assertNotNull(newMenuUI);
        newMenuUI.close();
    }

    @Test
    public void testMenuUIWithDifferentControllerCombinations() {
        // Test various controller combinations
        assertDoesNotThrow(() -> {
            new MenuUI(countryController, null);
            new MenuUI(null, cityController);
            new MenuUI(countryController, cityController);
        });
    }

    @Test
    public void testMenuUIResourceManagement() {
        MenuUI testMenuUI = new MenuUI(countryController, cityController);
        assertNotNull(testMenuUI);
        
        // Test resource cleanup
        assertDoesNotThrow(() -> {
            testMenuUI.close();
        });
    }

    @AfterEach
    public void tearDown() {
        if (menuUI != null) {
            menuUI.close();
        }
    }
}