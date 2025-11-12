package com.napier.semGROUP4;

import java.sql.*;

/**
 * Provides methods for retrieving population information
 * from the world's SQL database.
 */
public class PopulationService {

    private Connection con;

    /**
     * Creates a new PopulationService with an active database connection.
     *
     * @param con the database connection to use for queries
     */
    public PopulationService(Connection con) {
        this.con = con;
    }

    /**
     * Calculates the total population of the world.
     *
     * @return the sum of all country populations, or 0 if an error occurs
     */
    public long calculateWorldPopulation() {
        try {
            // Create a statement to send SQL commands to the database
            Statement stmt = con.createStatement();

            // Write the SQL query to calculate world population
            String strSelect = "SELECT SUM(Population) AS total FROM country";

            // Run the query and store the result
            ResultSet rset = stmt.executeQuery(strSelect);

            // If we find a result, return the total
            if (rset.next()) {
                return rset.getLong("total");
            } else {
                // If not return 0
                return 0;
            }

        } catch (Exception e) {
            // If something goes wrong, show an error message
            System.out.println("Error getting population details: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Calculates the total population of a given continent.
     *
     * @param enteredContinent the name of continent.
     * @return the sum of populations for all countries in the continent, or 0 if none found
     */
    public long calculateContinentPopulation(String enteredContinent) {
        try {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT SUM(Population) AS total FROM country WHERE Continent = '" + enteredContinent + "'";
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset.next()) {
                return rset.getLong("total");
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error getting population details: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Calculates the total population of a given region.
     *
     * @param enteredRegion the name of the region
     * @return the sum of populations for all countries in the region, or 0 if none found
     */
    public long calculateRegionPopulation(String enteredRegion) {
        try {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT SUM(Population) AS total FROM country WHERE Region = '" + enteredRegion + "'";
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset.next()) {
                return rset.getLong("total");
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error getting population details: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Calculates the total population of a given country.
     *
     * @param enteredCountry the name of the country
     * @return the country's total population, or 0 if not found
     */
    public long calculateCountryPopulation(String enteredCountry) {
        try {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT SUM(Population) AS total FROM country WHERE name = '" + enteredCountry + "'";
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset.next()) {
                return rset.getLong("total");
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error getting population details: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Calculates the total population of a given district.
     *
     * @param enteredDistrict the name of the district
     * @return the total population of all cities in that district, or 0 if not found
     */
    public long calculateDistrictPopulation(String enteredDistrict) {
        try {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT SUM(Population) AS total FROM city WHERE District = '" + enteredDistrict + "'";
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset.next()) {
                return rset.getLong("total");
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error getting population details: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Calculates the total population of a given city.
     *
     * @param enteredCity the name of the city
     * @return the city's total population,or 0 if not found
     */
    public long calculateCityPopulation(String enteredCity) {
        try {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT SUM(Population) AS total FROM city WHERE name = '" + enteredCity + "'";
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset.next()) {
                return rset.getLong("total");
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error getting population details: " + e.getMessage());
            return 0;
        }
    }
}
