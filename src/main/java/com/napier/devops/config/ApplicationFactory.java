package com.napier.devops.config;

import com.napier.devops.controller.CityController;
import com.napier.devops.controller.CountryController;
import com.napier.devops.database.DatabaseConnection;
import com.napier.devops.repository.CityRepository;
import com.napier.devops.repository.CountryRepository;
import com.napier.devops.service.CityService;
import com.napier.devops.service.CountryService;
import com.napier.devops.ui.MenuUI;

/**
 * Factory class for creating and wiring application components.
 * This class implements the Factory pattern and handles dependency injection.
 */
public class ApplicationFactory {
    
    private final DatabaseConnection dbConnection;
    
    public ApplicationFactory(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
    
    /**
     * Create and wire all application components.
     *
     * @return Configured MenuUI ready to use
     */
    public MenuUI createApplication() {
        // Create repositories
        CountryRepository countryRepository = createCountryRepository();
        CityRepository cityRepository = createCityRepository();
        
        // Create services
        CountryService countryService = createCountryService(countryRepository);
        CityService cityService = createCityService(cityRepository);
        
        // Create controllers
        CountryController countryController = createCountryController(countryService);
        CityController cityController = createCityController(cityService);
        
        // Create UI
        return createMenuUI(countryController, cityController);
    }
    
    private CountryRepository createCountryRepository() {
        return new CountryRepository(dbConnection);
    }
    
    private CityRepository createCityRepository() {
        return new CityRepository(dbConnection);
    }
    
    private CountryService createCountryService(CountryRepository repository) {
        return new CountryService(repository);
    }
    
    private CityService createCityService(CityRepository repository) {
        return new CityService(repository);
    }
    
    private CountryController createCountryController(CountryService service) {
        return new CountryController(service);
    }
    
    private CityController createCityController(CityService service) {
        return new CityController(service);
    }
    
    private MenuUI createMenuUI(CountryController countryController, CityController cityController) {
        return new MenuUI(countryController, cityController);
    }
}