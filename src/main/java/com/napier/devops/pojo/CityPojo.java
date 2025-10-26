package com.napier.devops.pojo;

import com.napier.devops.City;

/**
 * City POJO class extending City
 * @author group 3
 * @since 26th October 2025
 * @version 1.0
 */
public class CityPojo extends City {
    private String country;
    private String continent;
    private String region;
    private Integer population;

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return String.format(
                "City[name=%s, country=%s, continent=%s, region=%s, district=%s, population=%d]",
                getName(), country, continent, region, getDistrict(), population
        );
    }

}
