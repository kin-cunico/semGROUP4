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

            // Write the SQL query to find the city, including its country name
            String strSelect = "SELECT SUM(*) AS total" +
                    "FROM continent ";

            // Run the query and store the results
            ResultSet rset = stmt.executeQuery(strSelect);

            // If we find a result, fill a City object with the data
            if (rset.next()) {
                return rset.getLong("total");
            } else {
                // If no city was found with that name, return null
                return 0;
            }

        } catch (Exception e) {
            // If something goes wrong, show an error message
            System.out.println("Error getting population details: " + e.getMessage());
            return 0;
        }
    }

}

