package com.napier.semGROUP4;

import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for PopulationService using the real MySQL world database.
 * Works both locally and in CI (GitHub Actions).
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PopulationServiceTest {

    private static Connection con;
    private static PopulationService PopulationService;
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
            PopulationService = new PopulationService(con);
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

    // ----------------- INDIVIDUAL TESTS -----------------

    @Test
    @Order(1)
    @DisplayName("Check if an existing city population is not blank")
    void testCityPopulationValid() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        long testCityPop = PopulationService.calculateCityPopulation("Edinburgh");
        assertNotNull(testCityPop, "City population should not be null");
        assertEquals(450180, "City population name should match");
        assertTrue(testCityPop > 0, "Population should be positive");
    }


    @Test
    @Order(2)
    @DisplayName("Check if an existing country population is not blank")
    void testCountryPopulationValid() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        long testCountryPop = PopulationService.calculateCountryPopulation("Germany");
        assertNotNull(testCountryPop, "Country population should not be null");
        assertEquals(82164700, "Country population should match");
        assertTrue(testCountryPop > 0, "Population should be positive");
    }
}