package com.napier.semGROUP4.services;

import com.napier.semGROUP4.City;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;


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
            Statement stmt = con.createStatement();
            String strSelect = "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                    "FROM city " +
                    "JOIN country ON city.CountryCode = country.Code " +
                    "WHERE city.Name = '" + cityName + "'";
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                return city;
            } else {
                return null;
            }

        } catch (Exception e) {
            System.out.println("Error getting city details: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves all cities in the world, ordered by population in descending order.
     *
     * @return a list of all City objects in the world
     */
    public List<City> getAllCitiesInWorld() {
        List<City> cities = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                    "FROM city " +
                    "JOIN country ON city.CountryCode = country.Code " +
                    "ORDER BY city.Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all cities in the world: " + e.getMessage());
        }

        return cities;
    }

    /**
     * Retrieves all cities within a specified continent.
     *
     * @param continent the name of the continent
     * @return a list of City objects within that continent
     */
    public List<City> getCitiesInContinent(String continent) {
        List<City> cities = new ArrayList<>();

        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "WHERE country.Continent = ? " +
                            "ORDER BY city.Population DESC"
            );

            stmt.setString(1, continent);
            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Error getting cities in continent: " + e.getMessage());
        }

        return cities;
    }

    /**
     * Retrieves all cities within a specified region.
     *
     * @param region the name of the region
     * @return a list of City objects within that region
     */
    public List<City> getCitiesInRegion(String region) {
        List<City> cities = new ArrayList<>();

        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "WHERE country.Region = ? " +
                            "ORDER BY city.Population DESC"
            );

            stmt.setString(1, region);
            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Error getting cities in region: " + e.getMessage());
        }

        return cities;
    }

    /**
     * Retrieves all cities within a specified country.
     *
     * @param countryName the name of the country
     * @return a list of City objects within that country
     */
    public List<City> getCitiesInCountry(String countryName) {
        List<City> cities = new ArrayList<>();

        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "WHERE country.Name = ? " +
                            "ORDER BY city.Population DESC"
            );

            stmt.setString(1, countryName);
            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Error getting cities in country: " + e.getMessage());
        }

        return cities;
    }

    /**
     * Retrieves all cities within a specified district.
     *
     * @param district the name of the district
     * @return a list of City objects within that district
     */
    public List<City> getCitiesInDistrict(String district) {
        List<City> cities = new ArrayList<>();

        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "WHERE city.District = ? " +
                            "ORDER BY city.Population DESC"
            );

            stmt.setString(1, district);
            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Error getting cities in district: " + e.getMessage());
        }

        return cities;
    }

    /**
     * Retrieves the top N most populated cities in the world.
     *
     * @param n the number of top cities to return
     * @return a list of the top N City objects in the world
     */
    public List<City> getTopNCitiesInWorld(int n) {
        List<City> cities = new ArrayList<>();

        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "ORDER BY city.Population DESC " +
                            "LIMIT ?"
            );

            stmt.setInt(1, n);
            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Error getting top N cities in the world: " + e.getMessage());
        }

        return cities;
    }

    /**
     * Retrieves the top N most populated cities within a continent.
     *
     * @param continent the continent to search within
     * @param n the number of top cities to return
     * @return a list of the top N City objects within the specified continent
     */
    public List<City> getTopNCitiesInContinent(String continent, int n) {
        List<City> cities = new ArrayList<>();

        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "WHERE country.Continent = ? " +
                            "ORDER BY city.Population DESC " +
                            "LIMIT ?"
            );

            stmt.setString(1, continent);
            stmt.setInt(2, n);
            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Error getting top N cities in continent: " + e.getMessage());
        }

        return cities;
    }

    /**
     * Retrieves the top N most populated cities within a region.
     *
     * @param region the region to search within
     * @param n the number of top cities to return
     * @return a list of the top N City objects within the specified region
     */
    public List<City> getTopNCitiesInRegion(String region, int n) {
        List<City> cities = new ArrayList<>();

        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "WHERE country.Region = ? " +
                            "ORDER BY city.Population DESC " +
                            "LIMIT ?"
            );

            stmt.setString(1, region);
            stmt.setInt(2, n);
            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Error getting top N cities in region: " + e.getMessage());
        }

        return cities;
    }

    /**
     * Retrieves the top N most populated cities within a country.
     *
     * @param country the name of the country
     * @param n the number of top cities to return
     * @return a list of the top N City objects within the specified country
     */
    public List<City> getTopNCitiesInCountry(String country, int n) {
        List<City> cities = new ArrayList<>();

        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "WHERE country.Name = ? " +
                            "ORDER BY city.Population DESC " +
                            "LIMIT ?"
            );

            stmt.setString(1, country);
            stmt.setInt(2, n);
            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Error getting top N cities in country: " + e.getMessage());
        }

        return cities;
    }

    /**
     * Displays a formatted table of city information in the console.
     *
     * @param cities the list of City objects to display
     */
    public void displayCities(List<City> cities) {
        if (cities == null || cities.isEmpty()) {
            System.out.println("No cities found.");
            return;
        }

        System.out.printf("%-30s %-30s %-30s %-15s%n",
                "Name", "Country", "District", "Population");
        System.out.println("-----------------------------------------------------------------------------------");

        for (City city : cities) {
            System.out.printf("%-30s %-30s %-30s %-15d%n",
                    city.name, city.country, city.district, city.population);
        }
    }

    /**
     * Retrieves the top N most populated cities within a district.
     *
     * @param district the district name
     * @param n the number of top cities to return
     * @return a list of the top N City objects within the specified district
     */
    public List<City> getTopNCitiesInDistrict(String district, int n) {
        List<City> cities = new ArrayList<>();

        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "WHERE city.District = ? " +
                            "ORDER BY city.Population DESC " +
                            "LIMIT ?"
            );

            stmt.setString(1, district);
            stmt.setInt(2, n);
            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Error getting top N cities in district: " + e.getMessage());
        }

        return cities;
    }
}
