package com.napier.devops;


/**
 * Represents the population details and urbanisation statistics for a continent.
 * <p>
 * Uses getters and setters for encapsulation and data hiding.
 * The {@code toString} method is overridden for easy printing of report details.
 */
public class Continent {
    private String continent;
    private long totalPopulation;
    private long cityPopulation;
    private long nonCityPopulation;
    private double cityPopulationPercentage;
    private double nonCityPopulationPercentage;


    /**
     * Gets the name of the continent.
     * @return the continent name
     */
    public String getContinent() { return continent;}

    /**
     * Sets the name of the continent.
     * @param continent the continent name
     */
    public void setContinent(String continent) { this.continent = continent;}

    /**
     * Gets the total population of the continent.
     * @return the total population
     */
    public long getTotalPopulation() { return totalPopulation;}

    /**
     * Sets the total population of the continent.
     * @param totalPopulation the total population
     */
    public void setTotalPopulation(long totalPopulation) { this.totalPopulation = totalPopulation; }

    /**
     * Gets the population living in cities within the continent.
     * @return the city population
     */
    public long getCityPopulation() { return cityPopulation; }

    /**
     * Sets the population living in cities within the continent.
     * @param cityPopulation the city population
     */
    public void setCityPopulation(long cityPopulation) { this.cityPopulation = cityPopulation;}

    /**
     * Gets the population NOT living in cities within the continent.
     * @return the non-city population
     */
    public long getNonCityPopulation() { return nonCityPopulation;}

    /**
     * Sets the population NOT living in cities within the continent.
     * @param nonCityPopulation the non-city population
     */
    public void setNonCityPopulation(long nonCityPopulation) { this.nonCityPopulation = nonCityPopulation;}

    /**
     * Gets the percentage of the population living in cities.
     * @return the city population percentage
     */
    public double getCityPopulationPercentage() { return cityPopulationPercentage;}

    /**
     * Sets the percentage of the population living in cities.
     * @param cityPopulationPercentage the city population percentage
     */
    public void setCityPopulationPercentage(double cityPopulationPercentage) { this.cityPopulationPercentage = cityPopulationPercentage;}

    /**
     * Gets the percentage of the population NOT living in cities.
     * @return the non-city population percentage
     */
    public double getNonCityPopulationPercentage() {return nonCityPopulationPercentage;}

    /**
     * Sets the percentage of the population NOT living in cities.
     * @param nonCityPopulationPercentage the non-city population percentage
     */
    public void setNonCityPopulationPercentage(double nonCityPopulationPercentage) {this.nonCityPopulationPercentage = nonCityPopulationPercentage;}

    /**
     * Sets all the fields of the ContinentPopulation class.
     * This method is meant to be used for unit testing.
     *
     * @param continent the continent name
     * @param totalPopulation the total population
     * @param cityPopulation the population in cities
     * @param nonCityPopulation the population not in cities
     * @param cityPopulationPercentage the percentage in cities
     * @param nonCityPopulationPercentage the percentage not in cities
     */
    public Continent setAll(String continent, long totalPopulation, long cityPopulation, long nonCityPopulation,
                            double cityPopulationPercentage, double nonCityPopulationPercentage) {
        this.setContinent(continent);
        this.setTotalPopulation(totalPopulation);
        this.setCityPopulation(cityPopulation);
        this.setNonCityPopulation(nonCityPopulation);
        this.setCityPopulationPercentage(cityPopulationPercentage);
        this.setNonCityPopulationPercentage(nonCityPopulationPercentage);
        return this;
    }


    /**
     * Returns a formatted string representation of the Continent Population details.
     *
     * @return a formatted string.
     */
    @Override
    public String toString() {
        return String.format(
                "| %-15s | %,-24d | %8.2f%% | %,-24d | %8.2f%% | %,-18d |",
                continent,
                nonCityPopulation,
                nonCityPopulationPercentage,
                cityPopulation,
                cityPopulationPercentage,
                totalPopulation
        );
    }
}