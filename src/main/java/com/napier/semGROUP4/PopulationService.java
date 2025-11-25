package com.napier.semGROUP4;

import java.sql.*;

/**
    This class is used to talk to the database and for getting population information
 */
public class PopulationService {

    private Connection con;

    public PopulationService(Connection con) {
        this.con = con;
    }

    public long calculateWorldPopulation(){

        try {
            // Create a statement to send SQL commands to the database
            Statement stmt = con.createStatement();

            //SQL query to calculate world population
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

    public long calculateContinentPopulation(String enteredContinent){

        try {
            // Create a statement to send SQL commands to the database
            Statement stmt = con.createStatement();

            //SQL query to calculate continent population
            String strSelect = "SELECT SUM(Population) AS total FROM country WHERE Continent = '" + enteredContinent + "'";

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

    public long calculateRegionPopulation(String enteredRegion){

        try {
            // Create a statement to send SQL commands to the database
            Statement stmt = con.createStatement();

            //SQL query to calculate region population
            String strSelect = "SELECT SUM(Population) AS total FROM country WHERE Region = '" + enteredRegion + "'";

            // Run the query and store the result
            ResultSet rset = stmt.executeQuery(strSelect);

            // If we find a result, return the total
            if (rset.next()) {
                return rset.getLong("total");
            } else {
                // If not return 01

                return 0;
            }

        } catch (Exception e) {
            // If something goes wrong, show an error message
            System.out.println("Error getting population details: " + e.getMessage());
            return 0;
        }
    }

    public long calculateCountryPopulation(String enteredCountry){

        try {
            // Create a statement to send SQL commands to the database
            Statement stmt = con.createStatement();

            //SQL query to calculate country population
            String strSelect = "SELECT SUM(Population) AS total FROM country WHERE name = '" + enteredCountry + "'";

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

    public long calculateDistrictPopulation(String enteredDistrict){

        try {
            // Create a statement to send SQL commands to the database
            Statement stmt = con.createStatement();

            //SQL query to calculate district population
            String strSelect = "SELECT SUM(Population) AS total FROM city WHERE District = '" + enteredDistrict + "'";

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

    public long calculateCityPopulation(String enteredCity){

        try {
            // Create a statement to send SQL commands to the database
            Statement stmt = con.createStatement();

            //SQL query to calculate city population
            String strSelect = "SELECT SUM(Population) AS total FROM city WHERE name = '" + enteredCity + "'";

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

    public Population populationInContinent(String enteredContinent){

        try {
            // Create a statement to send SQL commands to the database
            Statement stmt = con.createStatement();

            //SQL query to calculate the amount of the population in a continent that lives in and not in cities
            String strSelect = "SELECT SUM(DISTINCT country.Population) AS total, SUM(city.Population) as inCity, " +
                    "SUM(DISTINCT country.Population) - SUM(city.population) as outCity " +
                    "FROM country JOIN city ON country.code = city.CountryCode " +
                    "WHERE continent = '" + enteredContinent + "'";

            // Run the query and store the result
            ResultSet rset = stmt.executeQuery(strSelect);

            // If we find a result, return the total, inCity and outCity
            if (rset.next()) {
                long total = rset.getLong("total");
                long inCity = rset.getLong("inCity");
                long outCity = rset.getLong("outCity");
                return new Population(total, inCity, outCity);
            } else {
                // If not return 0
                return new Population(0, 0, 0);
            }


        } catch (Exception e) {
            // If something goes wrong, show an error message
            System.out.println("Error getting population details: " + e.getMessage());
            return new Population(0, 0,0);
        }
    }

    public Population populationInRegion(String enteredRegion){

        try {
            // Create a statement to send SQL commands to the database
            Statement stmt = con.createStatement();

            //SQL query to calculate the amount of the population in a region that lives in and not in cities
            String strSelect = "SELECT SUM(DISTINCT country.Population) AS total, SUM(city.Population) as inCity, " +
                    "SUM(DISTINCT country.Population) - SUM(city.population) as outCity " +
                    "FROM country JOIN city ON country.code = city.CountryCode " +
                    "WHERE region = '" + enteredRegion + "'";

            // Run the query and store the result
            ResultSet rset = stmt.executeQuery(strSelect);

            // If we find a result, return the total, inCity, outCity
            if (rset.next()) {
                long total = rset.getLong("total");
                long inCity = rset.getLong("inCity");
                long outCity = rset.getLong("outCity");
                return new Population(total, inCity, outCity);
            } else {
                // If not return 0
                return new Population(0, 0, 0);
            }


        } catch (Exception e) {
            // If something goes wrong, show an error message
            System.out.println("Error getting population details: " + e.getMessage());
            return new Population(0, 0,0);
        }


    }

    public Population populationInCountry(String enteredCountry){

        try {
            // Create a statement to send SQL commands to the database
            Statement stmt = con.createStatement();

            //SQL query to calculate the amount of the population in a country that lives in and not in cities
            String strSelect = "SELECT SUM(DISTINCT country.Population) AS total, SUM(city.Population) as inCity, " +
                    "SUM(DISTINCT country.Population) - SUM(city.population) as outCity " +
                    "FROM country JOIN city ON country.code = city.CountryCode " +
                    "WHERE country.name = '" + enteredCountry + "'";

            // Run the query and store the result
            ResultSet rset = stmt.executeQuery(strSelect);

            // If we find a result, return the total, inCity, outCity
            if (rset.next()) {
                long total = rset.getLong("total");
                long inCity = rset.getLong("inCity");
                long outCity = rset.getLong("outCity");
                return new Population(total, inCity, outCity);
            } else {
                // If not return 0
                return new Population(0, 0, 0);
            }


        } catch (Exception e) {
            // If something goes wrong, show an error message
            System.out.println("Error getting population details: " + e.getMessage());
            return new Population(0, 0,0);
        }
    }



}

