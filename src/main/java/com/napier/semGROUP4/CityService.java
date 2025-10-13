package com.napier.semGROUP4;

import java.sql.*;

/**
 * This class is responsible for talking to the database
 * and getting information about cities.
 */
public class CityService {
    // This stores the connection to the database so we can run queries
    private Connection con;

    /**
     * Sets up CityService with an existing database connection.
     * @param con an active database connection passed from the main app
     */
    public CityService(Connection con) {
        this.con = con;
    }

    /**
     * Looks up a city in the database using its name.
     *
     * @param cityName the name of the city to search for
     * @return a City object with details if found, otherwise null
     */
    public City getCity(String cityName) {
        try {
            // Create a statement to send SQL commands to the database
            Statement stmt = con.createStatement();

            // Write the SQL query to find the city, including its country name
            String strSelect = "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                    "FROM city " +
                    "JOIN country ON city.CountryCode = country.Code " +
                    "WHERE city.Name = '" + cityName + "'";

            // Run the query and store the results
            ResultSet rset = stmt.executeQuery(strSelect);

            // If we find a result, fill a City object with the data
            if (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                return city;
            } else {
                // If no city was found with that name, return null
                return null;
            }

        } catch (Exception e) {
            // If something goes wrong, show an error message
            System.out.println("Error getting city details: " + e.getMessage());
            return null;
        }
    }
}