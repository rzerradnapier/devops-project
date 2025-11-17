package com.napier.devops;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for PopulationMetrics class.
 * Tests the PopulationMetrics model class functionality.
 */
public class PopulationMetricsTest {

    // We will use this metrics instance for performing and testing operations on a PopulationMetrics object.
    // It is initialized in each test via the setUp method.
    private PopulationMetrics populationmetrics;

    /**
     * Set up the test data.
     */
    @BeforeEach
    public void setUp() {
        // Initializing a new PopulationMetrics object before each test
        populationmetrics = new PopulationMetrics();
    }

    /**
     * Test nameOfArea field (getter & setter).
     */
    @Test
    public void testNameOfArea() {
        String name = "Asia";
        populationmetrics.setNameOfArea(name);
        assertEquals(name, populationmetrics.getNameOfArea());
    }

    /**
     * Test reportType field (getter & setter).
     */
    @Test
    public void testReportType() {
        PopulationMetrics.ReportType type = PopulationMetrics.ReportType.CONTINENT;
        populationmetrics.setReportType(type);
        assertEquals(type, populationmetrics.getReportType());
    }

    /**
     * Test totalPopulation field (getter & setter).
     */
    @Test
    public void testTotalPopulation() {
        long pop = 7000000000L;
        populationmetrics.setTotalPopulation(pop);
        assertEquals(pop, populationmetrics.getTotalPopulation());
    }

    /**
     * Test cityPopulation field (getter & setter).
     */
    @Test
    public void testCityPopulation() {
        long pop = 5000000000L;
        populationmetrics.setCityPopulation(pop);
        assertEquals(pop, populationmetrics.getCityPopulation());
    }

    /**
     * Test nonCityPopulation field (getter & setter).
     */
    @Test
    public void testNonCityPopulation() {
        long pop = 2000000000L;
        populationmetrics.setNonCityPopulation(pop);
        assertEquals(pop, populationmetrics.getNonCityPopulation());
    }

    /**
     * Test cityPopulationPercentage field (getter & setter).
     */
    @Test
    public void testCityPopulationPercentage() {
        double percent = 71.43;
        populationmetrics.setCityPopulationPercentage(percent);
        assertEquals(percent, populationmetrics.getCityPopulationPercentage());
    }

    /**
     * Test nonCityPopulationPercentage field (getter & setter).
     */
    @Test
    public void testNonCityPopulationPercentage() {
        double percent = 28.57;
        populationmetrics.setNonCityPopulationPercentage(percent);
        assertEquals(percent, populationmetrics.getNonCityPopulationPercentage());
    }

    /**
     * Test for setAll method.
     */

    @Test
    public void testSetAll() {
        String name = "Europe";
        PopulationMetrics.ReportType type = PopulationMetrics.ReportType.CONTINENT;
        long total = 9000L;
        long city = 6000L;
        long nonCity = 3000L;
        double cityPerc = 66.67;
        double nonCityPerc = 33.33;

        // Act
        PopulationMetrics result = populationmetrics.setAll(name, type, total, city, nonCity, cityPerc, nonCityPerc);

        // Verify the chaining behavior

        assertSame(populationmetrics, result);

        // Verify all fields were set correctly
        assertEquals(name, populationmetrics.getNameOfArea());
        assertEquals(type, populationmetrics.getReportType());
        assertEquals(total, populationmetrics.getTotalPopulation());
        assertEquals(city, populationmetrics.getCityPopulation());
        assertEquals(nonCity, populationmetrics.getNonCityPopulation());
        assertEquals(cityPerc, populationmetrics.getCityPopulationPercentage());
        assertEquals(nonCityPerc, populationmetrics.getNonCityPopulationPercentage());
    }


// ToString Logic Tests (Coverage for Switch Statement)

    /**
     * Test toString method to make sure it provides the right format for the COUNTRY type.
     */
    @Test
    void testToString_Country() {
        // Arrange
        populationmetrics.setAll("France", PopulationMetrics.ReportType.COUNTRY, 200L, 150L, 50L, 75.00, 25.00);

        String expected =
                "Country {\t" +
                        "  name='France',\t" +
                        "  totalPopulation=200,\t" +
                        "  cityPopulation=150,\t" +
                        "  nonCityPopulation=50,\t" +
                        "  cityPopPercentage=75.00%,\t" +
                        "  nonCityPopPercentage=25.00%\t" +
                        "}";

        // Assert
        assertEquals(expected, populationmetrics.toString());
    }

    /**
     * Test toString method to make sure it provides the right format for the REGION type.
     */
    @Test
    public void testToString_Region() {
        // Arrange
        populationmetrics.setAll("Caribbean", PopulationMetrics.ReportType.REGION, 3000L, 2000L, 1000L, 66.67, 33.33);

        String expected =
                "Region {\t" +
                        "  name='Caribbean',\t" +
                        "  totalPopulation=3000,\t" +
                        "  cityPopulation=2000,\t" +
                        "  nonCityPopulation=1000,\t" +
                        "  cityPopPercentage=66.67%,\t" +
                        "  nonCityPopPercentage=33.33%\t" +
                        "}";

        // Assert
        assertEquals(expected, populationmetrics.toString());
    }

    /**
     * Test toString method to make sure it provides the right format for the CONTINENT type.
     */
    @Test
    public void testToString_Continent() {
        // Arrange
        populationmetrics.setAll("Asia", PopulationMetrics.ReportType.CONTINENT, 7000000000L, 5000000000L, 2000000000, 71.43, 28.57);

        String expected =
                "Continent {\t" +
                        "  name='Asia',\t" +
                        "  totalPopulation=7000000000,\t" +
                        "  cityPopulation=5000000000,\t" +
                        "  nonCityPopulation=2000000000,\t" +
                        "  cityPopPercentage=71.43%,\t" +
                        "  nonCityPopPercentage=28.57%\t" +
                        "}";

        // Assert
        assertEquals(expected, populationmetrics.toString());
    }


    /**
     * Test toString method to make sure it provides the right format even if report type is null.
     * The PopulationMetrics class handles null report types differently than full reports.
     */
    @Test
    void toString_handlesNulls_consistently() {
        populationmetrics.setNameOfArea("Unknown");
        populationmetrics.setReportType(null); //triggers the default toString logic in the class

        String expected = "PopulationMetrics { name='Unknown' }";

        assertEquals(expected, populationmetrics.toString());
    }
}