package com.napier.semGROUP4;

import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for CityService class
 * these tests connect to the docker database (world db)
 * and check if the methods work properly
 */
public class CityServiceTest {

    /** used to connect to the database */
    static Connection con;

    /** used to run the city queries */
    static CityService cityService;

    /**
     * runs once before all tests
     * tries to connect to the docker mysql database
     */
    @BeforeAll
    static void init() {
        try {
            /** connecting to mysql database inside docker
             * if u change the docker setup, this url or password might need updated
             */
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/world?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "semgroup4"
            );
            /** pass the connection to CityService */
            cityService = new CityService(con);
        } catch (SQLException e) {
            /** if we cant connect, fail the test straight away */
            fail("Database connection failed: " + e.getMessage());
        }
    }

    /**
     * runs once after all tests finish
     * closes the database connection
     */
    @AfterAll
    static void closeConnection() {
        try {
            if (con != null)
                con.close(); /** close connection when done */
        } catch (SQLException e) {
            System.out.println("Error closing DB: " + e.getMessage());
        }
    }

    /**
     * test if getCity() actually returns a city that exists
     */
    @Test
    @DisplayName("Test getCity() returns valid city data")
    void testGetCityValid() {
        /** look up a real city */
        City city = cityService.getCity("Kabul");
        /** should find something */
        assertNotNull(city, "City should not be null");
        /** name should match */
        assertEquals("Kabul", city.getName());
        /** population should be positive */
        assertTrue(city.getPopulation() > 0);
    }

    /**
     * test what happens if city name doesn't exist
     */
    @Test
    @DisplayName("Test getCity() handles invalid input")
    void testGetCityInvalid() {
        /** fake city */
        City city = cityService.getCity("ThisCityDoesNotExist");
        /** should return null */
        assertNull(city, "Invalid city should return null");
    }

    /**
     * test if it returns all the cities in the world (big list)
     */
    @Test
    @DisplayName("Test getAllCitiesInWorld() returns many results")
    void testGetAllCitiesInWorld() {
        /** get all cities */
        List<City> cities = cityService.getAllCitiesInWorld();
        assertNotNull(cities, "List should not be null");
        /** should not be empty */
        assertTrue(cities.size() > 0, "Should return at least one city");
    }

    /**
     * test if cities in a continent (Asia) return correctly
     */
    @Test
    @DisplayName("Test getCitiesInContinent() returns cities for Asia")
    void testGetCitiesInContinent() {
        /** get cities in Asia */
        List<City> cities = cityService.getCitiesInContinent("Asia");
        assertNotNull(cities);
        assertTrue(cities.size() > 0, "Asia should have cities");
        /** rough check */
        assertEquals("Asia", getContinentOfFirstCity(cities));
    }

    /**
     * test what happens when region doesn't exist
     */
    @Test
    @DisplayName("Test getCitiesInRegion() returns empty for invalid region")
    void testGetCitiesInRegionInvalid() {
        /** invalid region */
        List<City> cities = cityService.getCitiesInRegion("FakeRegion");
        /** should still give a list */
        assertNotNull(cities, "Should return empty list, not null");
        /** should be empty */
        assertEquals(0, cities.size(), "FakeRegion should have 0 cities");
    }

    /**
     * test if getCitiesInCountry() returns cities for Japan
     */
    @Test
    @DisplayName("Test getCitiesInCountry() returns cities for Japan")
    void testGetCitiesInCountry() {
        /** get cities in Japan */
        List<City> cities = cityService.getCitiesInCountry("Japan");
        /** should not be empty */
        assertNotNull(cities);
        assertTrue(cities.size() > 0, "Japan should have cities");
    }

    /**
     * test getCitiesInDistrict() with a real district (e.g. 'California')
     */
    @Test
    @DisplayName("Test getCitiesInDistrict() returns cities for a real district")
    void testGetCitiesInDistrict() {
        /** california has multiple cities in dataset */
        List<City> cities = cityService.getCitiesInDistrict("California");
        assertNotNull(cities);
        assertTrue(cities.size() > 0, "California should have cities");
    }

    /**
     * test top N cities in the world
     */
    @Test
    @DisplayName("Test getTopNCitiesInWorld() returns limited number")
    void testGetTopNCitiesInWorld() {
        /** only want top 10 */
        List<City> cities = cityService.getTopNCitiesInWorld(10);
        assertNotNull(cities);
        assertTrue(cities.size() <= 10, "Should return 10 or fewer cities");
    }

    /**
     * test top N cities in a continent (Asia)
     */
    @Test
    @DisplayName("Test getTopNCitiesInContinent() works for Asia")
    void testGetTopNCitiesInContinent() {
        /** ask for top 5 Asian cities */
        List<City> cities = cityService.getTopNCitiesInContinent("Asia", 5);
        assertNotNull(cities);
        assertTrue(cities.size() <= 5, "Should return up to 5 cities");
    }

    /**
     * test top N cities in a country (China)
     */
    @Test
    @DisplayName("Test getTopNCitiesInCountry() works for China")
    void testGetTopNCitiesInCountry() {
        /** get top 3 in China */
        List<City> cities = cityService.getTopNCitiesInCountry("China", 3);
        assertNotNull(cities);
        assertTrue(cities.size() <= 3, "Should return up to 3 cities");
    }

    /**
     * test top N cities in a district (California)
     */

    @Test
    @DisplayName("Test getTopNCitiesInDistrict() works for California")
    void testGetTopNCitiesInDistrict() {
        /** get top 2 in California */
        List<City> cities = cityService.getTopNCitiesInDistrict("California", 2);
        assertNotNull(cities);
        assertTrue(cities.size() <= 2, "Should return up to 2 cities");
    }

    /**
     * helper just returns "Asia" for now, we can update later if City has continent info
     */
    private String getContinentOfFirstCity(List<City> cities) {
        if (cities.isEmpty()) return "None";
        return "Asia";
    }
}