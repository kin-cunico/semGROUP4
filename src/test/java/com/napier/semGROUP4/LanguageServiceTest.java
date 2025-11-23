package com.napier.semGROUP4;

import com.napier.semGROUP4.queriesClasses.Language;
import com.napier.semGROUP4.services.LanguageService;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class LanguageServiceTest {
    private static Connection con;
    private static LanguageService languageService;
    private static boolean dbAvailable = false;

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

    @Test
    @Order(2)
    @DisplayName("Get invalid language returns null")
    void testGetLanguageInvalid() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        Language language = languageService.getLanguage("fakelanguage");
        assertNull(language, "Invalid language should return null");
    }

    @Test
    @Order(3)
    @DisplayName("Get Organisation languages")
    void testGetOrganisationLanguages() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        assertEquals(5, languageService.getOrgLanguages());
    }
}
