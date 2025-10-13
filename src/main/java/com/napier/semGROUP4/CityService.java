package com.napier.semGROUP4;

import java.sql.*;

/**
 * Handles database operations for city information.
 * This class talks to the database and retrieves city data.
 */
public class CityService {
    // The database connection. We need this to send queries and get results.
    private Connection con;

    /**
     * Constructor for CityService.
     * Takes a database connection from the main app so we can use it here.
     */
    public CityService(Connection con) {
        this.con = con;
    }

    /**
     * Finds a city in the database by its name.
     *
     * @param cityName The name of the city to look for
     * @return A City object with all the information if found, or null if not found
     */
    public City getCity(String cityName) {
        try {
            // Create a SQL statement object to send commands to the database
            Statement stmt = con.createStatement();

            // Build the SQL query string.
            // We are selecting the city name, its country, district, and population.
            // Joining with the country table so we can get the country name.
            // Only return rows where the city name matches what the user asked for.
            String strSelect = "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                    "FROM city " +
                    "JOIN country ON city.CountryCode = country.Code " +
                    "WHERE city.Name = '" + cityName + "'";

            // Execute the query and get the results
            ResultSet rset = stmt.executeQuery(strSelect);

            // Check if the query returned any rows
            if (rset.next()) {
                // If it did, create a new City object to store the data
                City city = new City();
                city.name = rset.getString("Name");        // The city’s name
                city.country = rset.getString("Country");  // The country it belongs to
                city.district = rset.getString("District");// The district of the city
                city.population = rset.getInt("Population");// Population count

                // Return the City object to whoever called this method
                return city;
            } else {
                // If no city was found, return null
                return null;
            }

        } catch (Exception e) {
            // If anything goes wrong, print the error so we can debug it
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");

            // Return null if we couldn’t fetch the city
            return null;
        }
    }
}