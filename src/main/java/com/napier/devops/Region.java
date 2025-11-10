package com.napier.devops;

public class Region {
    private String NameofRegion;
    private long totalPopulation;
    private long cityPopulation;
    private float cityPopulationPercentage;
    private long nonCityPopulation;
    private float nonCityPopulationPercentage;

    // Constructor, Getters, and Setters would go here

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


    // The required toString() method for printing the report row
    @Override
    public String toString() {
        // Using the standard format from the previous use case
        return String.format(
                "| %-15s | %,-18d | %,-12d (%-6.2f%%) | %,-12d (%-6.2f%%) |",
                NameofRegion,
                totalPopulation,
                cityPopulation,
                cityPopulationPercentage,
                nonCityPopulation,
                nonCityPopulationPercentage
        );
    }
}