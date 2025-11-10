package com.napier.devops;

/**
 * Represents the population details and urbanisation statistics for a Region.
 * <p>
 * Uses getters and setters for encapsulation and data hiding.
 * The {@code toString} method is overridden for easy printing of report details.
 */

public class Region {
    private String NameofRegion;
    private long totalPopulation;
    private long cityPopulation;
    private float cityPopulationPercentage;
    private long nonCityPopulation;
    private float nonCityPopulationPercentage;

    // Constructor, Getters, and Setters

    public String getNameofRegion() { return NameofRegion;}


    public void setNameofRegion(String regionName) { this.NameofRegion = regionName;}


    public long gettotalPopulation() { return totalPopulation;}


    public void settotalPopulation(long totalPopulation) { this.totalPopulation = totalPopulation;}


    public long getcityPopulation() { return cityPopulation;}


    public void setcityPopulation(long cityPopulation) { this.cityPopulation = cityPopulation;}


    public float getcityPopulationPercentage() { return cityPopulationPercentage;}


    public void setcityPopulationPercentage(float cityPopulationPercentage) { this.cityPopulationPercentage = cityPopulationPercentage;}


    public long getnonCityPopulation() { return nonCityPopulation;}


    public void setnonCityPopulation(long nonCityPopulation) { this.nonCityPopulation = nonCityPopulation;}


    public float getnonCityPopulationPercentage() { return nonCityPopulationPercentage;}


    public void setnonCityPopulationPercentage(float nonCityPopulationPercentage) { this.nonCityPopulationPercentage = nonCityPopulationPercentage;}



    @Override
    public String toString() {

        return String.format(
                "| %-25s | %16s | %12s (%.2f%%) | %12s (%.2f%%) |",
                NameofRegion,
                String.format("%,d", totalPopulation),
                String.format("%,d", cityPopulation),
                cityPopulationPercentage,
                String.format("%,d", nonCityPopulation),
                nonCityPopulationPercentage
        );
    }
}