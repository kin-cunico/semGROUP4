package com.napier.semGROUP4.services;

import com.napier.semGROUP4.queriesClasses.Country;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CountryService {
    private Connection con;

    public CountryService(Connection aCon) {this.con = aCon;
    }

    public ArrayList<Country> getAllCountriesByPop() {

        ArrayList<Country> countriesList = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name AS Capital FROM country " +
                    "JOIN city ON country.capital = city.ID " +
                    "ORDER BY country.population " +
                    "DESC LIMIT 5;";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                Country country = new Country();
                country.setCode(rset.getString("Code"));
                country.setName(rset.getString("Name"));
                country.setCapital(rset.getString("Capital"));
                country.setContinent(rset.getString("Continent"));
                country.setRegion(rset.getString("Region"));
                country.setPopulation(rset.getInt("Population"));
                countriesList.add(country);
            }

        } catch (SQLException e) {
            System.out.println("Error getting all the countries in the world: " + e.getMessage());
        }


        return countriesList;
    };
}
