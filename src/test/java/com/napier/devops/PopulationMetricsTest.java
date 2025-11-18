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
    private PopulationMetrics popmet;

    /**
     * Set up the data for testing.
     */
    @BeforeEach
    public void setUp() {
        // Initializing a new PopulationMetrics object before each test
        popmet = new PopulationMetrics();
    }

    /**
     * Test nameOfArea field (getter & setter).
     */
    @Test
    public void testNameOfArea() {
        String name = "Asia";
        popmet.setNameOfArea(name);
        assertEquals(name, popmet.getNameOfArea());
    }

    /**
     * Test reportType field (getter & setter).
     */
    @Test
    public void testReportType() {
        PopulationMetrics.ReportType type = PopulationMetrics.ReportType.CONTINENT;
        popmet.setReportType(type);
        assertEquals(type, popmet.getReportType());
    }

    /**
     * Test totalPopulation field (getter & setter).
     */
    @Test
    public void testTotalPopulation() {
        long pop = 7000000000L;
        popmet.setTotalPopulation(pop);
        assertEquals(pop, popmet.getTotalPopulation());
    }

    /**
     * Test cityPopulation field (getter & setter).
     */
    @Test
    public void testCityPopulation() {
        long population = 5000000000L;
        popmet.setCityPopulation(population);
        assertEquals(population, popmet.getCityPopulation());
    }

    /**
     * Test nonCityPopulation field (getter & setter).
     */
    @Test
    public void testNonCityPopulation() {
        long population = 2000000000L;
        popmet.setNonCityPopulation(population);
        assertEquals(population, popmet.getNonCityPopulation());
    }

    /**
     * Test cityPopulationPercentage field (getter & setter).
     */
    @Test
    public void testCityPopulationPercentage() {
        double percentage = 71.43;
        popmet.setCityPopulationPercentage(percentage);
        assertEquals(percentage, popmet.getCityPopulationPercentage());
    }

    /**
     * Test nonCityPopulationPercentage field (getter & setter).
     */
    @Test
    public void testNonCityPopulationPercentage() {
        double percentage = 28.57;
        popmet.setNonCityPopulationPercentage(percentage);
        assertEquals(percentage, popmet.getNonCityPopulationPercentage());
    }

    /**
     * Test the setAll method and verify all fields are set correctly.
     */

    @Test
    public void testSetAll() {
        // Test data
        String name = "Europe";
        PopulationMetrics.ReportType type = PopulationMetrics.ReportType.CONTINENT;
        long totalpop = 9000L;
        long citypop = 6000L;
        long nonCitypop = 3000L;
        double citypopPerc = 66.67;
        double nonCitypopPerc = 33.33;

        // Act
        PopulationMetrics result = popmet.setAll(name, type, totalpop, citypop, nonCitypop, citypopPerc, nonCitypopPerc);

        // Verify the chaining behavior

        assertSame(popmet, result);

        // Verify all fields were set correctly
        assertEquals(name, popmet.getNameOfArea());
        assertEquals(type, popmet.getReportType());
        assertEquals(totalpop, popmet.getTotalPopulation());
        assertEquals(citypop, popmet.getCityPopulation());
        assertEquals(nonCitypop, popmet.getNonCityPopulation());
        assertEquals(citypopPerc, popmet.getCityPopulationPercentage());
        assertEquals(nonCitypopPerc, popmet.getNonCityPopulationPercentage());
    }


// ToString Logic Tests (Coverage for Switch Statement)

    /**
     * Test toString method to make sure it provides the right format for the COUNTRY type.
     */
    @Test
    void testToString_Country() {
        // Arrange
        popmet.setAll("France", PopulationMetrics.ReportType.COUNTRY, 200L, 150L, 50L, 75.00, 25.00);

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
        assertEquals(expected, popmet.toString());
    }

    /**
     * Test toString method to make sure it provides the right format for the REGION type.
     */
    @Test
    public void testToString_Region() {
        // Arrange
        popmet.setAll("Caribbean", PopulationMetrics.ReportType.REGION, 3000L, 2000L, 1000L, 66.67, 33.33);

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
        assertEquals(expected, popmet.toString());
    }

    /**
     * Test toString method to make sure it provides the right format for the CONTINENT type.
     */
    @Test
    public void testToString_Continent() {
        // Arrange
        popmet.setAll("Asia", PopulationMetrics.ReportType.CONTINENT, 7000000000L, 5000000000L, 2000000000, 71.43, 28.57);

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
        assertEquals(expected, popmet.toString());
    }


    /**
     * Test toString method to make sure it provides the right format even if report type is null.
     * The PopulationMetrics class handles null report types differently than full reports.
     */
    @Test
    void toString_handlesNulls_consistently() {
        popmet.setNameOfArea("Unknown");
        popmet.setReportType(null); //triggers the default toString logic in the class

        String expected = "PopulationMetrics { name='Unknown' }";

        assertEquals(expected, popmet.toString());
    }
}