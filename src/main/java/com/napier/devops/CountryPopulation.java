package com.napier.devops;


/**
 * Represents the population details and urbanisation statistics for a Country Population.
 * <p>
 * Uses getters and setters for encapsulation and data hiding.
 * The {@code toString} method is overridden for easy printing of report details.
 */
public class CountryPopulation {
    private String countryName;
    private long totalPopulation;
    private long cityPopulation;
    private long nonCityPopulation;
    private String cityPercent;
    private String nonCityPercentage;


    // Getters and Setters

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public long getTotalPopulation() {
        return totalPopulation;
    }

    public void setTotalPopulation(long totalPopulation) {
        this.totalPopulation = totalPopulation;
    }

    public long getCityPopulation() {
        return cityPopulation;
    }

    public void setCityPopulation(long cityPopulation) {
        this.cityPopulation = cityPopulation;
    }

    public long getNonCityPopulation() {
        return nonCityPopulation;
    }

    public void setNonCityPopulation(long nonCityPopulation) {
        this.nonCityPopulation = nonCityPopulation;
    }

    public String getCityPercent() {
        return cityPercent;
    }

    public void setCityPercent(String CityPercent) {
        this.cityPercent = CityPercent;
    }

    public String getNonCityPercentage() {
        return nonCityPercentage;
    }

    public void setNonCityPercentage(String nonCityPercentage) {
        this.nonCityPercentage = nonCityPercentage;
    }

    /**
     * Returns a formatted string representation of the Country Population details.
     *
     * @return a formatted string.
     */
    @Override
    public String toString() {
        return String.format("%-47s %-20s %-20s %-12s %-20s %-11s",
                countryName,
                totalPopulation,
                cityPopulation,
                cityPercent,
                nonCityPopulation,
                nonCityPercentage);
    }
}