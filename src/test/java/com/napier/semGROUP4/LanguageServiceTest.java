package com.napier.semGROUP4;

import com.napier.semGROUP4.queries.Language;
import com.napier.semGROUP4.services.LanguageService;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the LanguageService class.
 * Ensures language data is correctly retrieved from the database.
 */
public class LanguageServiceTest {
    private static Connection con;
    private static LanguageService languageService;
    private static boolean dbAvailable = false;

    /**
     * Sets up a database connection before running all tests.
     * Uses environment variables for host and port if available.
     */
    @BeforeAll
    static void init() {
        try {
            // Allow configurable host and port for flexibility
            String host = System.getenv().getOrDefault("DB_HOST", "localhost");
            String port = System.getenv().getOrDefault("DB_PORT", "3306");

            String url = String.format(
                    "jdbc:mysql://%s:%s/world?useSSL=false&allowPublicKeyRetrieval=true",
                    host, port
            );

            System.out.println("Attempting DB connection at: " + url);

            con = DriverManager.getConnection(url, "root", "semgroup4");
            languageService = new LanguageService(con);
            dbAvailable = true;
            System.out.println("Connected to database for integration tests.");

        } catch (SQLException e) {
            System.out.println("Database not available, skipping tests: " + e.getMessage());
            dbAvailable = false;
        }
    }

    /**
     * Closes the database connection after all tests have completed.
     */
    @AfterAll
    static void tearDown() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Database connection closed after tests.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing DB connection: " + e.getMessage());
        }
    }

    /**
     * Tests that a valid language query returns correct data.
     * Verifies that a known language like "Chinese" exists and has a positive speaker count.
     */
    @Test
    @Order(1)
    @DisplayName("Get existing language returns valid data")
    void testGetLanguageValid() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        Language language = languageService.getLanguage("Chinese");
        assertNotNull(language, "Language should not be null");
        assertEquals("Chinese", language.getName(), "Language name should match");
        assertTrue(language.getNumOfSpeakers() > 0, "Population should be positive");
    }

    /**
     * Tests that an invalid language query returns null.
     * Verifies that a non-existent language produces no data.
     */
    @Test
    @Order(2)
    @DisplayName("Get invalid language returns null")
    void testGetLanguageInvalid() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        Language language = languageService.getLanguage("fakelanguage");
        assertNull(language, "Invalid language should return null");
    }
}
