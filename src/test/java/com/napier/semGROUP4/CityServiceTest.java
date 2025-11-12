package com.napier.semGROUP4;

import com.napier.semGROUP4.services.CityService;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for CityService using the real MySQL world database.
 * Works both locally and in CI (GitHub Actions).
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CityServiceTest {

    private static Connection con;
    private static CityService cityService;
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
            cityService = new CityService(con);
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
    @DisplayName("Get existing city returns valid data")
    void testGetCityValid() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        City city = cityService.getCity("Kabul");
        assertNotNull(city, "City should not be null");
        assertEquals("Kabul", city.name, "City name should match");
        assertTrue(city.population > 0, "Population should be positive");
    }

    @Test
    @Order(2)
    @DisplayName("Get invalid city returns null")
    void testGetCityInvalid() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        City city = cityService.getCity("FakeCityThatDoesNotExist");
        assertNull(city, "Invalid city should return null");
    }

    @Test
    @Order(3)
    @DisplayName("Get all cities in world returns many results")
    void testGetAllCitiesInWorld() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        List<City> cities = cityService.getAllCitiesInWorld();
        assertNotNull(cities);
        assertTrue(cities.size() > 0, "Should return at least one city");
    }

    @Test
    @Order(4)
    @DisplayName("Get cities in Asia returns valid list")
    void testGetCitiesInContinent() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        List<City> cities = cityService.getCitiesInContinent("Asia");
        assertNotNull(cities);
        assertTrue(cities.size() > 0, "Asia should have cities");
    }

    @Test
    @Order(5)
    @DisplayName("Get cities in invalid region returns empty list")
    void testGetCitiesInRegionInvalid() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        List<City> cities = cityService.getCitiesInRegion("FakeRegion");
        assertNotNull(cities, "List should not be null");
        assertEquals(0, cities.size(), "Invalid region should have 0 results");
    }

    @Test
    @Order(6)
    @DisplayName("Get cities in Japan returns results")
    void testGetCitiesInCountry() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        List<City> cities = cityService.getCitiesInCountry("Japan");
        assertNotNull(cities);
        assertTrue(cities.size() > 0, "Japan should have cities");
    }

    @Test
    @Order(7)
    @DisplayName("Get cities in California district returns results")
    void testGetCitiesInDistrict() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        List<City> cities = cityService.getCitiesInDistrict("California");
        assertNotNull(cities);
        assertTrue(cities.size() > 0, "California should have cities");
    }

    @Test
    @Order(8)
    @DisplayName("Top 10 cities in the world returns up to 10 results")
    void testGetTopNCitiesInWorld() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        List<City> cities = cityService.getTopNCitiesInWorld(10);
        assertNotNull(cities);
        assertTrue(cities.size() <= 10, "Should return 10 or fewer cities");
    }

    @Test
    @Order(9)
    @DisplayName("Top 5 cities in Asia returns up to 5 results")
    void testGetTopNCitiesInContinent() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        List<City> cities = cityService.getTopNCitiesInContinent("Asia", 5);
        assertNotNull(cities);
        assertTrue(cities.size() <= 5);
    }

    @Test
    @Order(10)
    @DisplayName("Top 3 cities in China returns up to 3 results")
    void testGetTopNCitiesInCountry() {
        Assumptions.assumeTrue(dbAvailable, "Database not available for testing");
        List<City> cities = cityService.getTopNCitiesInCountry("China", 3);
        assertNotNull(cities);
        assertTrue(cities.size() <= 3);
    }

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
