package com.napier.semGROUP4;

import com.napier.semGROUP4.queriesClasses.City;
import com.napier.semGROUP4.services.CityService;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This file contains tests that talk to a real MySQL database.
 * These tests check that CityService works correctly when the database is available.
 * The tests will be skipped if the database cannot be reached.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CityServiceTest {

    private static Connection con;
    private static CityService cityService;
    private static boolean dbAvailable = false;

    /**
     * This runs before all tests.
     * It tries to connect to the MySQL "world" database.
     * If the connection works, tests will run.
     * If it fails, the tests that need the database will be skipped.
     */
    @BeforeAll
    static void init() {
        try {
            String host = System.getenv().getOrDefault("DB_HOST", "localhost");
            String port = System.getenv().getOrDefault("DB_PORT", "3306");

            String url = String.format(
                    "jdbc:mysql://%s:%s/world?useSSL=false&allowPublicKeyRetrieval=true",
                    host, port
            );

            System.out.println("Attempting DB connection at: " + url);

            con = DriverManager.getConnection(url, "root", "semgroup4");
            cityService = new CityService(con);
            dbAvailable = true;
            System.out.println("Connected to database for integration tests.");

        } catch (SQLException e) {
            System.out.println("Database not available, skipping tests: " + e.getMessage());
            dbAvailable = false;
        }
    }

    /**
     * This runs after all tests finish.
     * It closes the database connection if it is still open.
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

    // ----------------- INDIVIDUAL TESTS -----------------

    /**
     * Test 1:
     * Checks that asking for a real city (Kabul) returns correct information.
     */
    @Test
    @Order(1)
    @DisplayName("Get existing city returns valid data")
    void testGetCityValid() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        City city = cityService.getCity("Kabul");
        assertNotNull(city, "City should not be null");
        assertEquals("Kabul", city.name, "City name should match");
        assertTrue(city.population > 0, "Population should be positive");
    }

    /**
     * Test 2:
     * Checks that asking for a city that does not exist returns null.
     */
    @Test
    @Order(2)
    @DisplayName("Get invalid city returns null")
    void testGetCityInvalid() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        City city = cityService.getCity("FakeCityThatDoesNotExist");
        assertNull(city, "Invalid city should return null");
    }

    /**
     * Test 3:
     * Checks that getting all cities in the world returns many results.
     */
    @Test
    @Order(3)
    @DisplayName("Get all cities in world returns many results")
    void testGetAllCitiesInWorld() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        List<City> cities = cityService.getAllCitiesInWorld();
        assertNotNull(cities);
        assertTrue(cities.size() > 0, "Should return at least one city");
    }

    /**
     * Test 4:
     * Checks that getting cities in a real continent (Asia) works.
     */
    @Test
    @Order(4)
    @DisplayName("Get cities in Asia returns valid list")
    void testGetCitiesInContinent() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        List<City> cities = cityService.getCitiesInContinent("Asia");
        assertNotNull(cities);
        assertTrue(cities.size() > 0, "Asia should have cities");
    }

    /**
     * Test 5:
     * Checks that asking for a fake region returns an empty list.
     */
    @Test
    @Order(5)
    @DisplayName("Get cities in invalid region returns empty list")
    void testGetCitiesInRegionInvalid() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        List<City> cities = cityService.getCitiesInRegion("FakeRegion");
        assertNotNull(cities, "List should not be null");
        assertEquals(0, cities.size(), "Invalid region should have 0 results");
    }

    /**
     * Test 6:
     * Checks that asking for cities in a real country (Japan) works.
     */
    @Test
    @Order(6)
    @DisplayName("Get cities in Japan returns results")
    void testGetCitiesInCountry() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        List<City> cities = cityService.getCitiesInCountry("Japan");
        assertNotNull(cities);
        assertTrue(cities.size() > 0, "Japan should have cities");
    }

    /**
     * Test 7:
     * Checks that asking for cities in a known district works.
     */
    @Test
    @Order(7)
    @DisplayName("Get cities in California district returns results")
    void testGetCitiesInDistrict() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        List<City> cities = cityService.getCitiesInDistrict("California");
        assertNotNull(cities);
        assertTrue(cities.size() > 0, "California should have cities");
    }

    /**
     * Test 8:
     * Checks that getting the top 10 cities in the world returns 10 or fewer results.
     */
    @Test
    @Order(8)
    @DisplayName("Top 10 cities in the world returns up to 10 results")
    void testGetTopNCitiesInWorld() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        List<City> cities = cityService.getTopNCitiesInWorld(10);
        assertNotNull(cities);
        assertTrue(cities.size() <= 10, "Should return 10 or fewer cities");
    }

    /**
     * Test 9:
     * Checks that getting the top 5 cities in Asia returns 5 or fewer results.
     */
    @Test
    @Order(9)
    @DisplayName("Top 5 cities in Asia returns up to 5 results")
    void testGetTopNCitiesInContinent() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        List<City> cities = cityService.getTopNCitiesInContinent("Asia", 5);
        assertNotNull(cities);
        assertTrue(cities.size() <= 5);
    }

    /**
     * Test 10:
     * Checks that getting the top 3 cities in China works.
     */
    @Test
    @Order(10)
    @DisplayName("Top 3 cities in China returns up to 3 results")
    void testGetTopNCitiesInCountry() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        List<City> cities = cityService.getTopNCitiesInCountry("China", 3);
        assertNotNull(cities);
        assertTrue(cities.size() <= 3);
    }

    /**
     * Test 11:
     * Checks that getting the top 2 cities in a district returns 2 or fewer results.
     */
    @Test
    @Order(11)
    @DisplayName("Top 2 cities in California returns up to 2 results")
    void testGetTopNCitiesInDistrict() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        List<City> cities = cityService.getTopNCitiesInDistrict("California", 2);
        assertNotNull(cities);
        assertTrue(cities.size() <= 2);
    }
}
