/**
 * Copyright Byteworks Technology Solutions Ltd
 */
package com.napier.devops.pojo;

import com.napier.devops.City;

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
}
