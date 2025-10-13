package com.napier.semGROUP4;

import java.sql.*;

public class CityService {
    private Connection con;

    public CityService(Connection con) {
        this.con = con;
    }

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
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }
}