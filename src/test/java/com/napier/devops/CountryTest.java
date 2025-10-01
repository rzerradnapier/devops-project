package com.napier.devops;

import org.junit.jupiter.api.Test;
import static com.napier.constant.Constant.DEFAULT_COUNTRY_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test for Country class.
 */
public class CountryTest extends AppTest{

    /**
     * Tests the {@link App#getCountryByCode(String)} method to ensure it retrieves
     * a country object correctly based on the provided country code.
     * Verifies that the returned country is not null and that the code matches
     * the expected value.
     */
    @Test
    public void testGetCountry() {
        Country country = app.getCountryByCode(DEFAULT_COUNTRY_CODE);
        assertNotNull(country);
        assertEquals(DEFAULT_COUNTRY_CODE, country.getCode());
    }

}
