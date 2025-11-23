package com.napier.semGROUP4.services;

import com.napier.semGROUP4.queriesClasses.CapitalCity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CapitalCityService {

    private Connection con;
    public CapitalCityService(Connection con) {
        this.con = con;
    }


    /**
     *      [ 1 ] This Displays all the Capitals In the world
     */

    public List<CapitalCity> getAllCapitalsInWorld() {
        List<CapitalCity> CapitalCities = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT city.Name, country.Name AS Country, city.Population " +
                    "FROM country " +
                    "JOIN city ON country.Capital  = city.ID " +
                    "ORDER BY city.Population DESC, city.Name ASC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                CapitalCity capitalCity = new CapitalCity();
                capitalCity.name = rset.getString("Name");
                capitalCity.country = rset.getString("Country");
                capitalCity.population = rset.getInt("Population");
                CapitalCities.add(capitalCity);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all cities in the world: " + e.getMessage());
        }

        return CapitalCities;
    }

    /**
     *      [ 2 ] This Displays all the Capitals In a Continent
     */

    public List<CapitalCity> getAllCapitalsInContinent(String continentName) {
        List<CapitalCity> CapitalCities = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT city.Name, country.Name AS Country, city.Population " +
                    "FROM country " +
                    "JOIN city ON country.Capital  = city.ID " +
                    "WHERE country.continent = '" + continentName + "'" +
                    "ORDER BY city.Population DESC";

            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                CapitalCity capitalCity = new CapitalCity();
                capitalCity.name = rset.getString("Name");
                capitalCity.country = rset.getString("Country");
                capitalCity.population = rset.getInt("Population");
                CapitalCities.add(capitalCity);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all cities in the world: " + e.getMessage());
        }

        return CapitalCities;
    }

    /**
     *      [ 3 ] This Displays all the Capitals In a Continent
     */

    public List<CapitalCity> getAllCapitalsInRegion(String regionName) {
        List<CapitalCity> CapitalCities = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT city.Name, country.Name AS Country, city.Population " +
                    "FROM country " +
                    "JOIN city ON country.Capital  = city.ID " +
                    "WHERE country.Region = '" + regionName + "'" +
                    "ORDER BY city.Population DESC";

            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                CapitalCity capitalCity = new CapitalCity();
                capitalCity.name = rset.getString("Name");
                capitalCity.country = rset.getString("Country");
                capitalCity.population = rset.getInt("Population");
                CapitalCities.add(capitalCity);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all cities in the world: " + e.getMessage());
        }

        return CapitalCities;
    }

    /**
     *      [ 4 ] This Displays all the Capitals In a Continent
     */

    public List<CapitalCity>  worldTopPopulatedCapitals(int listSize) {
        List<CapitalCity> CapitalCities = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT city.Name, country.Name AS Country, city.Population " +
                    "FROM country " +
                    "JOIN city ON country.Capital  = city.ID " +
                    "ORDER BY city.Population DESC, city.Name ASC" +
                    "LIMIT " + listSize;

            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                CapitalCity capitalCity = new CapitalCity();
                capitalCity.name = rset.getString("Name");
                capitalCity.country = rset.getString("Country");
                capitalCity.population = rset.getInt("Population");
                CapitalCities.add(capitalCity);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all cities in the world: " + e.getMessage());
        }

        return CapitalCities;
    }

    /**
     *      [ 5 ] This Displays all the Capitals In a Continent
     */

    public List<CapitalCity>   continentTopPopulatedCapitals(String continent, int listSize) {
        List<CapitalCity> CapitalCities = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT city.Name, country.Name, city.Population " +
                    "FROM country " +
                    "JOIN city ON country.Capital  = city.ID " +
                    "WHERE country.continent = '" + continent + "'" +
                    "ORDER BY city.Population DESC, city.Name ASC" +
                    "LIMIT " + listSize;

            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                CapitalCity capitalCity = new CapitalCity();
                capitalCity.name = rset.getString("Name");
                capitalCity.country = rset.getString("Country");
                capitalCity.population = rset.getInt("Population");
                CapitalCities.add(capitalCity);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all cities in the world: " + e.getMessage());
        }

        return CapitalCities;
    }

    /**
     *      [ 6 ] This Displays all the Capitals In a Continent
     */

    public List<CapitalCity> regionTopPopulatedCapitals(String region, int listSize) {
        List<CapitalCity> CapitalCities = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT city.Name, country.Name, city.Population " +
                    "FROM country " +
                    "JOIN city ON country.Capital  = city.ID " +
                    "WHERE country.Region = '" + region + "'" +
                    "ORDER BY city.Population DESC, city.Name ASC" +
                    "LIMIT " + listSize;

            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                CapitalCity capitalCity = new CapitalCity();
                capitalCity.name = rset.getString("Name");
                capitalCity.country = rset.getString("Country");
                capitalCity.population = rset.getInt("Population");
                CapitalCities.add(capitalCity);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all cities in the world: " + e.getMessage());
        }

        return CapitalCities;
    }

    /**
     * This display Capital Cities from the table
     */

    public void displayCapitalCities(List<CapitalCity> capitalCities) {
        if (capitalCities == null || capitalCities.isEmpty()) {
            System.out.println("No Capital Cities found.");
            return;
        }

        System.out.printf("%-30s %-30s %-15s%n",
                "Name", "Country", "Population");
        System.out.println("-----------------------------------------------------------------------------------");

        for (CapitalCity capitalCity : capitalCities) {
            System.out.printf("%-30s %-30s %-15d%n",
                    capitalCity.name, capitalCity.country, capitalCity.population);
        }
    }
}
