package com.napier.semGROUP4.services;

import com.napier.semGROUP4.CapitalCity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CapitalCityService {

    private Connection con;
    public CapitalCityService(Connection con) {
        this.con = con;
    }



    public CapitalCity getCapitalCityByCountryCode(String countryCode) {
        try {
            // Create a statement to send SQL commands to the database
            Statement stmt = con.createStatement();

            // Write the SQL query to find the city, including its country name
            String strSelect = "SELECT country.Name, city.Name, city.population " +
                    "FROM country " +
                    "JOIN city ON country.Capital = city.ID " +
                    "WHERE country.Code = '" + countryCode + "'";



            // Run the query and store the results
            ResultSet rset = stmt.executeQuery(strSelect);

            // If we find a result, fill a City object with the data
            if (rset.next()) {
                CapitalCity capitalCity = new CapitalCity();
                capitalCity.name = rset.getString("Name");
                capitalCity.country = rset.getString("Country");
                capitalCity.population = rset.getInt("Population");
                return capitalCity;
            } else {
                // If no capitalCity was found with that name, return null
                return null;
            }

        } catch (Exception e) {
            // If something goes wrong, show an error message
            System.out.println("Error getting capitalCity details: " + e.getMessage());
            return null;
        }
    }

}
