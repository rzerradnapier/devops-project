package com.napier.devops;

/**
 * Represents the population details and urbanisation statistics for a Region.
 * <p>
 * Uses getters and setters for encapsulation and data hiding.
 * The {@code toString} method is overridden for easy printing of report details.
 */

public class Region {
    private String nameofRegion;
    private long totalPopulation;
    private long cityPopulation;
    private float cityPopulationPercentage;
    private long nonCityPopulation;
    private float nonCityPopulationPercentage;

    // Constructor, Getters, and Setters


    public String getNameOfRegion() { return nameofRegion;}

    public void setNameOfRegion(String nameofRegion) { this.nameofRegion = nameofRegion;}


    public long getTotalPopulation() { return totalPopulation;}

    public void setTotalPopulation(long totalPopulation) { this.totalPopulation = totalPopulation;}


    public long getCityPopulation() { return cityPopulation;}

    public void setCityPopulation(long cityPopulation) { this.cityPopulation = cityPopulation;}


    public float getCityPopulationPercentage() { return cityPopulationPercentage;}

    public void setCityPopulationPercentage(float cityPopulationPercentage) { this.cityPopulationPercentage = cityPopulationPercentage;}


    public long getNonCityPopulation() { return nonCityPopulation;}

    public void setNonCityPopulation(long nonCityPopulation) { this.nonCityPopulation = nonCityPopulation;}


    public float getNonCityPopulationPercentage() { return nonCityPopulationPercentage;}

    public void setNonCityPopulationPercentage(float nonCityPopulationPercentage) { this.nonCityPopulationPercentage = nonCityPopulationPercentage;}

    /**
     * Returns a formatted string representation of the Region Population details.
     *
     * @return a formatted string.
     */

    @Override
    public String toString() {

        return String.format(
                "| %-25s | %,16d | %,18d | %17.2f%% | %,18d | %17.2f%% |",
                nameofRegion,
                totalPopulation,
                cityPopulation,
                cityPopulationPercentage,
                nonCityPopulation,
                nonCityPopulationPercentage
        );
    }
}