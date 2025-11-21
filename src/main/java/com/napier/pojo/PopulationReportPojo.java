package com.napier.pojo;

/**
 * Represents a population report for a continent, region, or country.
 * @since 9th November 2025
 */
public class PopulationReportPojo {
    private String name;
    private long totalPopulation;
    private long populationInCities;
    private double percentageInCities;
    private long populationNotInCities;
    private double percentageNotInCities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalPopulation() {
        return totalPopulation;
    }

    public void setTotalPopulation(long totalPopulation) {
        this.totalPopulation = totalPopulation;
    }

    public long getPopulationInCities() {
        return populationInCities;
    }

    public void setPopulationInCities(long populationInCities) {
        this.populationInCities = populationInCities;
    }

    public double getPercentageInCities() {
        return percentageInCities;
    }

    public void setPercentageInCities(double percentageInCities) {
        this.percentageInCities = percentageInCities;
    }

    public long getPopulationNotInCities() {
        return populationNotInCities;
    }

    public void setPopulationNotInCities(long populationNotInCities) {
        this.populationNotInCities = populationNotInCities;
    }

    public double getPercentageNotInCities() {
        return percentageNotInCities;
    }

    public void setPercentageNotInCities(double percentageNotInCities) {
        this.percentageNotInCities = percentageNotInCities;
    }

    @Override
    public String toString() {
        return String.format(
                """
                Population Report for %s:
                Total Population: %,d
                Living in Cities: %,d (%.2f%%)
                Not Living in Cities: %,d (%.2f%%)""",
                name, totalPopulation, populationInCities, percentageInCities,
                populationNotInCities, percentageNotInCities
        );
    }

}
