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
        StringBuilder sb = new StringBuilder("City { ");

        if (getId() != null) sb.append("id='").append(getId()).append("', ");
        if (getName() != null) sb.append("name='").append(getName()).append("', ");
        if (country != null) sb.append("country='").append(country).append("', ");
        if (continent != null) sb.append("continent='").append(continent).append("', ");
        if (region != null) sb.append("region='").append(region).append("', ");
        if (getDistrict() != null) sb.append("district='").append(getDistrict()).append("', ");
        if (population != null) sb.append("population=").append(population).append(", ");

        // Remove trailing comma and space if present
        if (sb.lastIndexOf(", ") == sb.length() - 2) {
            sb.setLength(sb.length() - 2);
        }

        sb.append(" }\n");
        return sb.toString();
    }

}
