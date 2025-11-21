package com.napier.devops;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class AppMainIntegrationTest {

    private PrintStream originalOut;
    private PrintStream originalErr;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    @BeforeEach
    void setUpStreams() {
        originalOut = System.out;
        originalErr = System.err;

        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    /**
     * Integration-style test that exercises the main method end-to-end
     * using a running MySQL "world" database.
     *
     * Assumes:
     * - MySQL is available at jdbc:mysql://localhost:33060/world
     * - user "root", password as in App.connect
     */
    @Test
    void main_withValidHostAndDelay_runsAllUseCasesWithoutThrowing() {
        // Adjust host and port if your DB is different
        String hostAndPort = "localhost:33060";
        String delayMillis = "0";

        assertDoesNotThrow(() -> App.main(new String[]{hostAndPort, delayMillis}),
                "App.main should not throw when called with valid host and delay");

        String stdout = outContent.toString();
        String stderr = errContent.toString();

        // Basic sanity: we expect at least some of the use case headers
        assertTrue(stdout.contains("USE CASE 1:list of all countries sorted by population largest to smallest"),
                "Expected USE CASE 1 header in output");
        assertTrue(stdout.contains("USE CASE 17: Produce a Report on All Capital Cities in the World by Population"),
                "Expected USE CASE 17 header in output");
        assertTrue(stdout.contains("USE CASE 32: Produce a Report on Speakers of Major Languages."),
                "Expected USE CASE 32 header in output");
        assertTrue(stdout.contains("Get country by code USA"),
                "Expected 'Get country by code USA' marker in output");

        // Optional: ensure no obvious errors printed to stderr
        assertFalse(stderr.toLowerCase().contains("error"),
                "Did not expect 'error' in stderr for valid run");
    }

    /**
     * Negative test: invalid delay argument should cause NumberFormatException.
     * This covers the failure path of Integer.parseInt(args[1]).
     */
    @Test
    void main_withNonNumericDelay_throwsNumberFormatException() {
        String hostAndPort = "localhost:33060";
        String badDelay = "not-a-number";

        assertThrows(NumberFormatException.class,
                () -> App.main(new String[]{hostAndPort, badDelay}),
                "Expected NumberFormatException when delay argument is not numeric");
    }
}