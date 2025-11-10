package com.napier.devops;


/**
 * Represents the population details and urbanisation statistics for a Country.
 * <p>
 * Uses getters and setters for encapsulation and data hiding.
 * The {@code toString} method is overridden for easy printing of report details.
 */
public class CountryPopulation {
    private String CountryName;
    private long TotalPopulation;
    private long CityPopulation;
    private long NonCityPopulation;
    private String CityPercent;
    private String NonCityPercentage;

    // Getters and Setters

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String CountryName) {
        this.CountryName = CountryName;
    }

    public long getTotalPopulation() {
        return TotalPopulation;
    }

    public void setTotalPopulation(long TotalPopulation) {
        this.TotalPopulation = TotalPopulation;
    }

    public long getCityPopulation() {
        return CityPopulation;
    }

    public void setCityPopulation(long CityPopulation) {
        this.CityPopulation = CityPopulation;
    }

    public long getNonCityPopulation() {
        return NonCityPopulation;
    }

    public void setNonCityPopulation(long NonCityPopulation) {
        this.NonCityPopulation = NonCityPopulation;
    }

    public String getCityPercent() {
        return CityPercent;
    }

    public void setCityPercent(String CityPercent) {
        this.CityPercent = CityPercent;
    }

    public String getNonCityPercentage() {
        return NonCityPercentage;
    }

    public void setNonCityPercentage(String NonCityPercentage) {
        this.NonCityPercentage = NonCityPercentage;
    }

    /**
     * Returns a formatted string representation of the Country Population details.
     *
     * @return a formatted string.
     */
    @Override
    public String toString() {
        return String.format("%-47s %-20s %-20s %-12s %-20s %-11s",
                CountryName,
                TotalPopulation,
                CityPopulation,
                CityPercent,
                NonCityPopulation,
                NonCityPercentage);
    }
}