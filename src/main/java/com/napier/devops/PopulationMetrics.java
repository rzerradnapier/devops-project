package com.napier.devops;

/**
 * Represents the population details and urbanisation statistics for a geographical area.
 */
public class PopulationMetrics {

    /**
     * Defines the type of geographical area this report represents.
     * This controls the formatting used by the toString() method.
     */
    public enum ReportType {
        CONTINENT,
        REGION,
        COUNTRY
    }

    //
    private String nameOfArea; // Continent, Region, or Country name
    private ReportType reportType;
    private long totalPopulation;
    private long cityPopulation;
    private long nonCityPopulation;
    private double cityPopulationPercentage;
    private double nonCityPopulationPercentage;

    // Getters and Setters

    /**
     * Getter for the name of the geographical area.
     * @return The name of the area (e.g., "Asia", "France").
     */
    public String getNameOfArea() {
        return nameOfArea;
    }

    /**
     * Setter for the name of the geographical area.
     * @param nameOfArea The new name of the area.
     */
    public void setNameOfArea(String nameOfArea) {
        this.nameOfArea = nameOfArea;
    }

    /**
     * Gets the report type (CONTINENT, REGION, COUNTRY).
     * @return The ReportType enum.
     */
    public ReportType getReportType() {
        return reportType;
    }

    /**
     * Sets the report type
     * @param reportType The ReportType enum.
     */
    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public long getTotalPopulation() { return totalPopulation; }
    public void setTotalPopulation(long totalPopulation) { this.totalPopulation = totalPopulation; }

    public long getCityPopulation() { return cityPopulation; }
    public void setCityPopulation(long cityPopulation) { this.cityPopulation = cityPopulation; }

    public long getNonCityPopulation() { return nonCityPopulation; }
    public void setNonCityPopulation(long nonCityPopulation) { this.nonCityPopulation = nonCityPopulation; }

    public double getCityPopulationPercentage() { return cityPopulationPercentage; }
    public void setCityPopulationPercentage(double cityPopulationPercentage) { this.cityPopulationPercentage = cityPopulationPercentage; }

    public double getNonCityPopulationPercentage() { return nonCityPopulationPercentage; }
    public void setNonCityPopulationPercentage(double nonCityPopulationPercentage) { this.nonCityPopulationPercentage = nonCityPopulationPercentage; }

    /**
     * Sets all the fields of the PopulationMetrics class.
     * (Fixed the bug: uses the parameter 'nameOfArea' instead of the field)
     *
     * @param nameOfArea                the area name
     * @param reportType                the type of report
     * @param totalPopulation           the total population
     * @param cityPopulation            the population in cities
     * @param nonCityPopulation         the population not in cities
     * @param cityPopulationPercentage  the percentage in cities
     * @param nonCityPopulationPercentage the percentage not in cities
     */
    public PopulationMetrics setAll(String nameOfArea, ReportType reportType, long totalPopulation, long cityPopulation, long nonCityPopulation,
                                    double cityPopulationPercentage, double nonCityPopulationPercentage) {

        this.setNameOfArea(nameOfArea);
        this.setReportType(reportType);
        this.setTotalPopulation(totalPopulation);
        this.setCityPopulation(cityPopulation);
        this.setNonCityPopulation(nonCityPopulation);
        this.setCityPopulationPercentage(cityPopulationPercentage);
        this.setNonCityPopulationPercentage(nonCityPopulationPercentage);
        return this;
    }


    /**
     * Returns a formatted string representation of the Population Report.
     * <p>
     * The format used depends on the {@code reportType} field
     * @return a formatted string.
     */
    @Override
    public String toString() {
        if (reportType == null) {
            return "PopulationMetrics { name='" + nameOfArea + "' }";
        }


        switch (reportType) {
            case REGION:
                return "Region {\t" +
                        "  name='" + nameOfArea + "',\t" +
                        "  totalPopulation=" + totalPopulation + ",\t" +
                        "  cityPopulation=" + cityPopulation + ",\t" +
                        "  nonCityPopulation=" + nonCityPopulation + ",\t" +
                        "  cityPopPercentage=" + String.format("%.2f", cityPopulationPercentage) + "%,\t" +
                        "  nonCityPopPercentage=" + String.format("%.2f", nonCityPopulationPercentage) + "%\t" +
                        '}';

            case COUNTRY:
                return "Country {\t" +
                        "  name='" + nameOfArea + "',\t" +
                        "  totalPopulation=" + totalPopulation + ",\t" +
                        "  cityPopulation=" + cityPopulation + ",\t" +
                        "  nonCityPopulation=" + nonCityPopulation + ",\t" +
                        "  cityPopPercentage=" + String.format("%.2f", cityPopulationPercentage) + "%,\t" +
                        "  nonCityPopPercentage=" + String.format("%.2f", nonCityPopulationPercentage) + "%\t" +
                        '}';

            case CONTINENT:

                return "Continent {\t" +
                        "  name='" + nameOfArea + "',\t" +
                        "  totalPopulation=" + totalPopulation + ",\t" +
                        "  cityPopulation=" + cityPopulation + ",\t" +
                        "  nonCityPopulation=" + nonCityPopulation + ",\t" +
                        "  cityPopPercentage=" + String.format("%.2f", cityPopulationPercentage) + "%,\t" +
                        "  nonCityPopPercentage=" + String.format("%.2f", nonCityPopulationPercentage) + "%\t" +
                        '}';

            default:
                return "PopulationMetrics { name='" + nameOfArea + "', type=" + reportType + " }";
        }
    }
}