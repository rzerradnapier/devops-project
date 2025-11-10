package com.napier.devops;

import java.text.DecimalFormat;

/**
 * Represents the population details and urbanisation statistics for a continent.
 * <p>
 * Uses getters and setters for encapsulation and data hiding.
 * The {@code toString} method is overridden for easy printing of report details.
 */
public class Continent {
    private String Continent;
    private long TotalPopulation;
    private long CityPopulation;
    private long NonCityPopulation;
    private double CityPopulationPercentage;
    private double NonCityPopulationPercentage;


    /**
     * Gets the name of the continent.
     * @return the continent name
     */
    public String getContinent() { return Continent;}

    /**
     * Sets the name of the continent.
     * @param Continent the continent name
     */
    public void setContinent(String Continent) { this.Continent = Continent;}

    /**
     * Gets the total population of the continent.
     * @return the total population
     */
    public long getTotalPopulation() { return TotalPopulation;}

    /**
     * Sets the total population of the continent.
     * @param TotalPopulation the total population
     */
    public void setTotalPopulation(long TotalPopulation) { this.TotalPopulation = TotalPopulation; }

    /**
     * Gets the population living in cities within the continent.
     * @return the city population
     */
    public long getCityPopulation() { return CityPopulation; }

    /**
     * Sets the population living in cities within the continent.
     * @param CityPopulation the city population
     */
    public void setCityPopulation(long CityPopulation) { this.CityPopulation = CityPopulation;}

    /**
     * Gets the population NOT living in cities within the continent.
     * @return the non-city population
     */
    public long getNonCityPopulation() { return NonCityPopulation;}

    /**
     * Sets the population NOT living in cities within the continent.
     * @param NonCityPopulation the non-city population
     */
    public void setNonCityPopulation(long NonCityPopulation) { this.NonCityPopulation = NonCityPopulation;}

    /**
     * Gets the percentage of the population living in cities.
     * @return the city population percentage
     */
    public double getCityPopulationPercentage() { return CityPopulationPercentage;}

    /**
     * Sets the percentage of the population living in cities.
     * @param CityPopulationPercentage the city population percentage
     */
    public void setCityPopulationPercentage(double CityPopulationPercentage) { this.CityPopulationPercentage = CityPopulationPercentage;}

    /**
     * Gets the percentage of the population NOT living in cities.
     * @return the non-city population percentage
     */
    public double getNonCityPopulationPercentage() {return NonCityPopulationPercentage;}

    /**
     * Sets the percentage of the population NOT living in cities.
     * @param NonCityPopulationPercentage the non-city population percentage
     */
    public void setNonCityPopulationPercentage(double NonCityPopulationPercentage) {this.NonCityPopulationPercentage = NonCityPopulationPercentage;}

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
     * Note: This is designed for report output, not for simple debugging like the City toString().
     *
     * @return a formatted string.
     */
    @Override
    public String toString() {
        return String.format(
                "| %-15s | %,-12d (%-6.2f%%) | %,-12d (%-6.2f%%) | %,-18d |",
                Continent,
                NonCityPopulation,
                NonCityPopulationPercentage,
                CityPopulation,
                CityPopulationPercentage,
                TotalPopulation
        );
    }
}