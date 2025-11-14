package com.napier.devops.service;


import com.napier.devops.Country;
import com.napier.pojo.LanguageReportPojo;
import com.napier.pojo.PopulationReportPojo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the CountryReportService class using mocked database connections.
 */
public class CountryReportServiceTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private CountryReportService countryReportService;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        countryReportService = new CountryReportService(mockConnection);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    }

    /**
     * Test getCountryByCode with mock data.
     */
    @Test
    void testGetCountryByCode() throws SQLException {
        // Setup mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("code")).thenReturn("USA");
        when(mockResultSet.getString("name")).thenReturn("United States");
        when(mockResultSet.getString("continent")).thenReturn("North America");
        when(mockResultSet.getString("region")).thenReturn("North America");
        when(mockResultSet.getInt("population")).thenReturn(278357000);
        when(mockResultSet.getInt("capital")).thenReturn(3813);

        // Call the method
        Country country = countryReportService.getCountryByCode("USA");

        // Verify results
        assertNotNull(country);
        assertEquals("USA", country.getCode());
        assertEquals("United States", country.getName());
        assertEquals("North America", country.getContinent());
        assertEquals("North America", country.getRegion());
        assertEquals(278357000, country.getPopulation());
        assertEquals(3813, country.getCapital());

        // Verify that the prepared statement was called with the correct country code
        verify(mockPreparedStatement).setString(1, "USA");
    }

    /**
     * Test getCountryByCode with no results.
     */
    @Test
    void testGetCountryByCodeWithNoResults() throws SQLException {
        // Setup mock behavior for no results
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        // Call the method
        Country country = countryReportService.getCountryByCode("XXX");

        // Verify that null is returned
        assertNull(country);
    }

    /**
     * Test getCountryByCode with SQL exception.
     */
    @Test
    void testGetCountryByCodeWithSQLException() throws SQLException {
        // Setup mock to throw SQLException
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Call the method
        Country country = countryReportService.getCountryByCode("USA");

        // Verify that null is returned when there's an exception
        assertNull(country);
    }

    /**
     * Test getAllCountriesByPopulationLargestToSmallest with mock data.
     */
    @Test
    void testGetAllCountriesByPopulationLargestToSmallest() throws SQLException {
        // Setup mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, true, false);

        when(mockResultSet.getString("code")).thenReturn("CHN", "IND", "USA");
        when(mockResultSet.getString("name")).thenReturn("China", "India", "United States");
        when(mockResultSet.getString("continent")).thenReturn("Asia", "Asia", "North America");
        when(mockResultSet.getString("region")).thenReturn("Eastern Asia", "Southern and Central Asia", "North America");
        when(mockResultSet.getInt("population")).thenReturn(1277558000, 1013662000, 278357000);
        when(mockResultSet.getInt("capital")).thenReturn(1891, 1109, 3813);

        // Call the method
        List<Country> countries = countryReportService.getAllCountriesByPopulationLargestToSmallest();

        // Verify results
        assertNotNull(countries);
        assertEquals(3, countries.size());

        // Verify first country
        Country firstCountry = countries.get(0);
        assertEquals("CHN", firstCountry.getCode());
        assertEquals("China", firstCountry.getName());
        assertEquals("Asia", firstCountry.getContinent());
        assertEquals("Eastern Asia", firstCountry.getRegion());
        assertEquals(1277558000, firstCountry.getPopulation());
        assertEquals(1891, firstCountry.getCapital());

        // Verify descending order
        assertTrue(countries.get(0).getPopulation() >= countries.get(1).getPopulation());
        assertTrue(countries.get(1).getPopulation() >= countries.get(2).getPopulation());
    }

    /**
     * Test getAllCountriesByPopulationLargestToSmallest with SQL exception.
     */
    @Test
    void testGetAllCountriesByPopulationLargestToSmallestWithSQLException() throws SQLException {
        // Setup mock to throw SQLException
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Call the method
        List<Country> countries = countryReportService.getAllCountriesByPopulationLargestToSmallest();

        // Verify that an empty list is returned when there's an exception
        assertNotNull(countries);
        assertTrue(countries.isEmpty());
    }

    /**
     * Test getAllCountriesByPopulationLargestToSmallest with empty result set.
     */
    @Test
    void testGetAllCountriesByPopulationLargestToSmallestWithEmptyResult() throws SQLException {
        // Setup mock behavior for empty result
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // No results

        // Call the method
        List<Country> countries = countryReportService.getAllCountriesByPopulationLargestToSmallest();

        // Verify that an empty list is returned
        assertNotNull(countries);
        assertTrue(countries.isEmpty());
    }

    /**
     * Test printAllCountriesByPopulationLargestToSmallest with null list.
     */
    @Test
    void testPrintAllCountriesByPopulationLargestToSmallestWithNullList() throws SQLException {
        // Setup mock to return null (simulate database connection failure)
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Connection failed"));

        // This should not throw an exception and should handle null gracefully
        assertDoesNotThrow(() -> {
            countryReportService.printAllCountriesByPopulationLargestToSmallest();
        });
    }

    /**
     * Test getAllCountriesInContinentByPopulationLargestToSmallest with mock data.
     */
    @Test
    void testGetAllCountriesInContinentByPopulationLargestToSmallest() throws SQLException {
        // Setup mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, true, false);

        when(mockResultSet.getString("code")).thenReturn("CHN", "JAP", "THA");
        when(mockResultSet.getString("name")).thenReturn("China", "Japan", "Thailand");
        when(mockResultSet.getString("continent")).thenReturn("Asia", "Asia", "Asia");
        when(mockResultSet.getString("region")).thenReturn("Eastern Asia", "Eastern Europe", "North America");
        when(mockResultSet.getInt("population")).thenReturn(1277558000, 1240216107, 744216107);
        when(mockResultSet.getInt("capital")).thenReturn(1891, 1444, 3813);

        // Call the method
        List<Country> countries = countryReportService.getAllCountriesInContinentByPopulationLargestToSmallest("Asia");

        // Verify results
        assertNotNull(countries);
        assertEquals(3, countries.size());

        // Verify first country
        Country firstCountry = countries.get(0);
        assertEquals("CHN", firstCountry.getCode());
        assertEquals("China", firstCountry.getName());
        assertEquals("Asia", firstCountry.getContinent());
        assertEquals("Eastern Asia", firstCountry.getRegion());
        assertEquals(1277558000, firstCountry.getPopulation());
        assertEquals(1891, firstCountry.getCapital());

        // Verify descending order
        assertTrue(countries.get(0).getPopulation() >= countries.get(1).getPopulation());
        assertTrue(countries.get(1).getPopulation() >= countries.get(2).getPopulation());
    }

    /**
     * Test getAllCountriesInContinentByPopulationLargestToSmallest with SQL exception.
     */
    @Test
    void testGetAllCountriesInContinentByPopulationLargestToSmallestWithSQLException() throws SQLException {
        // Setup mock to throw SQLException
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Call the method
        List<Country> countries = countryReportService.getAllCountriesInContinentByPopulationLargestToSmallest("Asia");

        // Verify that an empty list is returned when there's an exception
        assertNotNull(countries);
        assertTrue(countries.isEmpty());
    }

    /**
     * Test getAllCountriesInContinentByPopulationLargestToSmallest with empty result set.
     */
    @Test
    void testGetAllCountriesInContinentByPopulationLargestToSmallestWithEmptyResult() throws SQLException {
        // Setup mock behavior for empty result
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // No results

        // Call the method
        List<Country> countries = countryReportService.getAllCountriesInContinentByPopulationLargestToSmallest("Asia");

        // Verify that an empty list is returned
        assertNotNull(countries);
        assertTrue(countries.isEmpty());
    }

    @Test
    void testPrintAllCountriesByPopulationInAContinentLargestToSmallestWithNullList() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Connection failed"));

        assertDoesNotThrow(() -> {
            countryReportService.printAllCountriesByPopulationInAContinentLargestToSmallest("Asia");
        });
    }

    /**
     * Test getAllCountriesInRegionByPopulationLargestToSmallest with mock data.
     */
    @Test
    void testGetAllCountriesInRegionByPopulationLargestToSmallest() throws SQLException {
        // Setup mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, true, false);

        when(mockResultSet.getString("code")).thenReturn("USA", "CAN", "MEX");
        when(mockResultSet.getString("name")).thenReturn("United States", "Mexico", "Canada");
        when(mockResultSet.getString("continent")).thenReturn("North America", "North America", "North America");
        when(mockResultSet.getString("region")).thenReturn("North America", "North America", "North America");
        when(mockResultSet.getInt("population")).thenReturn(331002651, 128932753, 37742154);
        when(mockResultSet.getInt("capital")).thenReturn(3813, 1822, 2514);

        // Call the method
        List<Country> countries = countryReportService.getAllCountriesInRegionByPopulationLargestToSmallest("North America");

        // Verify results
        assertNotNull(countries);
        assertEquals(3, countries.size());

        // Verify first country
        Country firstCountry = countries.get(0);
        assertEquals("USA", firstCountry.getCode());
        assertEquals("United States", firstCountry.getName());
        assertEquals("North America", firstCountry.getContinent());
        assertEquals("North America", firstCountry.getRegion());
        assertEquals(331002651, firstCountry.getPopulation());
        assertEquals(3813, firstCountry.getCapital());

        // Verify descending order
        assertTrue(countries.get(0).getPopulation() >= countries.get(1).getPopulation());
        assertTrue(countries.get(1).getPopulation() >= countries.get(2).getPopulation());
    }

    /**
     * Test getAllCountriesInRegionByPopulationLargestToSmallest with SQL exception.
     */
    @Test
    void testGetAllCountriesInRegionByPopulationLargestToSmallestWithSQLException() throws SQLException {
        // Setup mock to throw SQLException
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Call the method
        List<Country> countries = countryReportService.getAllCountriesInRegionByPopulationLargestToSmallest("North America");

        // Verify that an empty list is returned when there's an exception
        assertNotNull(countries);
        assertTrue(countries.isEmpty());
    }

    /**
     * Test getAllCountriesInRegionByPopulationLargestToSmallest with empty result set.
     */
    @Test
    void testGetAllCountriesInRegionByPopulationLargestToSmallestWithEmptyResult() throws SQLException {
        // Setup mock behavior for empty result
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // No results

        // Call the method
        List<Country> countries = countryReportService.getAllCountriesInRegionByPopulationLargestToSmallest("North America");

        // Verify that an empty list is returned
        assertNotNull(countries);
        assertTrue(countries.isEmpty());
    }

    @Test
    void testPrintAllCountriesByPopulationInARegionLargestToSmallestWithNullList() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Connection failed"));

        assertDoesNotThrow(() -> {
            countryReportService.printAllCountriesByPopulationInARegionLargestToSmallest("North America");
        });
    }

    /**
     * Test printTopNCountriesByPopulation with mock data.
     */
    @Test
    void testPrintTopNCountriesByPopulation() throws SQLException {
        // Setup mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);

        when(mockResultSet.getString("code")).thenReturn("CHN", "IND");
        when(mockResultSet.getString("name")).thenReturn("China", "India");
        when(mockResultSet.getString("continent")).thenReturn("Asia", "Asia");
        when(mockResultSet.getString("region")).thenReturn("Eastern Asia", "Southern and Central Asia");
        when(mockResultSet.getInt("population")).thenReturn(1277558000, 1013662000);
        when(mockResultSet.getInt("capital")).thenReturn(1891, 1109);

        // Call the method
        assertDoesNotThrow(() -> countryReportService.printTopNCountriesByPopulation(2));
    }

    /**
     * Test printTopNCountriesInContinentByPopulation with mock data.
     */
    @Test
    void testPrintTopNCountriesInContinentByPopulation() throws SQLException {
        // Setup mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);

        when(mockResultSet.getString("code")).thenReturn("CHN", "IND");
        when(mockResultSet.getString("name")).thenReturn("China", "India");
        when(mockResultSet.getString("continent")).thenReturn("Asia", "Asia");
        when(mockResultSet.getString("region")).thenReturn("Eastern Asia", "Southern and Central Asia");
        when(mockResultSet.getInt("population")).thenReturn(1277558000, 1013662000);
        when(mockResultSet.getInt("capital")).thenReturn(1891, 1109);

        // Call the method
        assertDoesNotThrow(() -> countryReportService.printTopNCountriesInContinentByPopulation("Asia", 2));
    }

    /**
     * Test printTopNCountriesByPopulation with SQL exception.
     */
    @Test
    void testPrintTopNCountriesByPopulationWithSQLException() throws SQLException {
        // Setup mock to throw SQLException
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Call the method and ensure it does not throw an exception
        assertDoesNotThrow(() -> countryReportService.printTopNCountriesByPopulation(2));
    }

    /**
     * Test printTopNCountriesByPopulation with no results.
     */
    @Test
    void testPrintTopNCountriesByPopulationWithNoResults() throws SQLException {
        // Setup mock behavior for no results
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        // Call the method and ensure it does not throw an exception
        assertDoesNotThrow(() -> countryReportService.printTopNCountriesByPopulation(2));
    }


    @Test
    void testPrintTopNCountriesInRegionByPopulationWithMockData() throws SQLException {
        // Setup mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);

        when(mockResultSet.getString("code")).thenReturn("USA", "CAN");
        when(mockResultSet.getString("name")).thenReturn("United States", "Canada");
        when(mockResultSet.getString("continent")).thenReturn("North America", "North America");
        when(mockResultSet.getString("region")).thenReturn("North America", "North America");
        when(mockResultSet.getInt("population")).thenReturn(331002651, 37742154);
        when(mockResultSet.getInt("capital")).thenReturn(3813, 2514);

        // Call the method
        assertDoesNotThrow(() -> countryReportService.printTopNCountriesInRegionByPopulation("North America", 2));
    }


    /**
     * Unit tests for {@link CountryReportService#getRegionPopulationReport(String)}.
     * Use Case 28 - Retrieve the Population of a Region: Test null region input.
     */
    @Test
    void testNullRegion() {
        PopulationReportPojo report = countryReportService.getRegionPopulationReport(null);
        assertNull(report);
    }

    /**
     * Unit tests for {@link CountryReportService#getRegionPopulationReport(String)}.
     * Use Case 28 - Retrieve the Population of a Region: Test empty region input.
     */
    @Test
    void testEmptyRegion() {
        PopulationReportPojo report = countryReportService.getRegionPopulationReport("   ");
        assertNull(report);
    }

    /**
     * Unit tests for {@link CountryReportService#getRegionPopulationReport(String)}.
     * Use Case 28 - Retrieve the Population of a Region: Test valid region input returning correct report.
     */
    @Test
    void testValidRegionPopulationReport() throws Exception {
        PreparedStatement stmtTotal = mock(PreparedStatement.class);
        PreparedStatement stmtCity = mock(PreparedStatement.class);
        ResultSet rsTotal = mock(ResultSet.class);
        ResultSet rsCity = mock(ResultSet.class);

        when(mockConnection.prepareStatement("SELECT SUM(population) AS total_population FROM country WHERE region = ?"))
                .thenReturn(stmtTotal);
        when(mockConnection.prepareStatement(anyString()))
                .thenReturn(stmtTotal)
                .thenReturn(stmtCity);

        when(stmtTotal.executeQuery()).thenReturn(rsTotal);
        when(stmtCity.executeQuery()).thenReturn(rsCity);

        when(rsTotal.next()).thenReturn(true);
        when(rsTotal.getLong("total_population")).thenReturn(5000000L);

        when(rsCity.next()).thenReturn(true);
        when(rsCity.getLong("city_population")).thenReturn(3000000L);

        PopulationReportPojo report = countryReportService.getRegionPopulationReport("Western Europe");

        assertNotNull(report);
        assertEquals("Western Europe", report.getName());
        assertEquals(5000000L, report.getTotalPopulation());
        assertEquals(3000000L, report.getPopulationInCities());
        assertEquals(2000000L, report.getPopulationNotInCities());
        assertEquals(60.0, report.getPercentageInCities(), 0.01);
        assertEquals(40.0, report.getPercentageNotInCities(), 0.01);
    }

    /**
     * Unit tests for {@link CountryReportService#getRegionPopulationReport(String)}.
     * Use Case 28 - Retrieve the Population of a Region: Test region with no population data.
     */
    @Test
    void testSQLExceptionHandling() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));
        PopulationReportPojo report = countryReportService.getRegionPopulationReport("Asia");
        assertNull(report);
    }

    /**
     * Unit tests for {@link CountryReportService#getCountryPopulationReport(String)}
     * USE CASE 29 - Retrieve the Population of a Country: Testing null country input.
     */
    @Test
    void testNullCountry() {
        assertNull(countryReportService.getCountryPopulationReport(null));
    }

    /**
     * Unit tests for {@link CountryReportService#getCountryPopulationReport(String)}
     * USE CASE 29 - Retrieve the Population of a Country: Testing empty country input.
     */
    @Test
    void testEmptyCountry() {
        assertNull(countryReportService.getCountryPopulationReport(" "));
    }

    /**
     * Unit tests for {@link CountryReportService#getCountryPopulationReport(String)}
     * USE CASE 29 - Retrieve the Population of a Country: Testing valid country input returning correct report.
     */
    @Test
    void testValidCountryPopulationReport() throws Exception {
        // Mocks
        PreparedStatement totalStmt = mock(PreparedStatement.class);
        ResultSet totalRS = mock(ResultSet.class);
        PreparedStatement cityStmt = mock(PreparedStatement.class);
        ResultSet cityRS = mock(ResultSet.class);

        // Mock total population query
        when(mockConnection.prepareStatement("SELECT Population FROM country WHERE Name = ?"))
                .thenReturn(totalStmt);
        when(totalStmt.executeQuery()).thenReturn(totalRS);
        when(totalRS.next()).thenReturn(true);
        when(totalRS.getLong("Population")).thenReturn(1000000L);

        // Mock city population query
        when(mockConnection.prepareStatement(contains("AS city_population")))
                .thenReturn(cityStmt);
        when(cityStmt.executeQuery()).thenReturn(cityRS);
        when(cityRS.next()).thenReturn(true);
        when(cityRS.getLong("city_population")).thenReturn(400000L);

        // Execute
        PopulationReportPojo report = countryReportService.getCountryPopulationReport("Testland");

        // Verify
        assertNotNull(report);
        assertEquals("Testland", report.getName());
        assertEquals(1000000L, report.getTotalPopulation());
        assertEquals(400000L, report.getPopulationInCities());
        assertEquals(600000L, report.getPopulationNotInCities());
        assertEquals(40.0, report.getPercentageInCities(), 0.1);
        assertEquals(60.0, report.getPercentageNotInCities(), 0.1);
    }

    /**
     * Unit tests for {@link CountryReportService#getCountryPopulationReport(String)}
     * USE CASE 29 - Retrieve the Population of a Country: Testing country with no population data.
     */
    @Test
    void testSQLException() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));
        assertNull(countryReportService.getCountryPopulationReport("Nowhere"));
    }

    /**
     * Unit tests for {@link CountryReportService#getMajorLanguageReport()}
     * USE CASE 32 - Produce a Report on Speakers of Major Languages: Testing valid major language report generation.
     */
    @Test
    void testValidMajorLanguageReport() throws Exception {
        PreparedStatement stmtWorld = mock(PreparedStatement.class);
        ResultSet rsWorld = mock(ResultSet.class);

        PreparedStatement stmtLang = mock(PreparedStatement.class);
        ResultSet rsLang = mock(ResultSet.class);

        // Mock world population query
        when(mockConnection.prepareStatement("SELECT SUM(Population) AS world_population FROM country"))
                .thenReturn(stmtWorld);
        when(stmtWorld.executeQuery()).thenReturn(rsWorld);
        when(rsWorld.next()).thenReturn(true);
        when(rsWorld.getLong("world_population")).thenReturn(1_000_000_000L);

        // Mock language query (note: make it match DISTINCTLY)
        when(mockConnection.prepareStatement(contains("FROM countrylanguage")))
                .thenReturn(stmtLang);
        when(stmtLang.executeQuery()).thenReturn(rsLang);

        when(rsLang.next()).thenReturn(true, true, true, true, true, false);
        when(rsLang.getString("Language"))
                .thenReturn("Chinese", "English", "Hindi", "Spanish", "Arabic");
        when(rsLang.getLong("speakers"))
                .thenReturn(300_000_000L, 200_000_000L, 150_000_000L, 100_000_000L, 50_000_000L);

        // Execute
        List<LanguageReportPojo> reports = countryReportService.getMajorLanguageReport();

        // Verify
        assertEquals(5, reports.size());
        assertEquals("Chinese", reports.get(0).getLanguage());
        assertTrue(reports.get(0).getPercentageOfWorld() > reports.get(4).getPercentageOfWorld());
    }


    /**
     * Unit tests for {@link CountryReportService#getMajorLanguageReport()}
     * USE CASE 32 - Produce a Report on Speakers of Major Languages: Testing valid major language report generation.
     */
    @Test
    void testWorldPopulationZeroReturnsEmptyList() throws Exception {
        PreparedStatement stmtWorld = mock(PreparedStatement.class);
        ResultSet rsWorld = mock(ResultSet.class);

        when(mockConnection.prepareStatement("SELECT SUM(Population) AS world_population FROM country"))
                .thenReturn(stmtWorld);
        when(stmtWorld.executeQuery()).thenReturn(rsWorld);
        when(rsWorld.next()).thenReturn(true);
        when(rsWorld.getLong("world_population")).thenReturn(0L);


        List<LanguageReportPojo> reports = countryReportService.getMajorLanguageReport();
        assertTrue(reports.isEmpty());
    }
}
