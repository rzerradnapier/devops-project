package com.napier.devops;

/**
 * Represents a city.
 * <p>
 * Uses getters and setters for encapsulation and data hiding.
 * The {@code toString} method is overridden for easy printing of city details.
 */
public class City {
    private Integer id;
    private String name;
    private String countryCode;
    private String district;
    private Integer population;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    /**
     * Sets all the fields of the City class.
     * This method is meant to be used for unit testing.
     *
     * @param id          the city ID
     * @param name        the city name
     * @param countryCode the country code
     * @param district    the district
     * @param population  the population
     */
    public City setAll(Integer id, String name, String countryCode, String district, Integer population) {
        this.setId(id);
        this.setName(name);
        this.setCountryCode(countryCode);
        this.setDistrict(district);
        this.setPopulation(population);
        return this;
    }

    @Override
    public String toString() {
        return String.format(
                "City { id=%d, name='%s', countryCode='%s', district='%s', population=%d }",
                id, name, countryCode, district, population
        );
    }

}