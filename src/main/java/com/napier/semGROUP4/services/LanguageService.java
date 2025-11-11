package com.napier.semGROUP4.services;

import com.napier.semGROUP4.City;
import com.napier.semGROUP4.queries.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LanguageService {
    private Connection con;

    /**
     * Sets up LanguageService with an existing database connection.
     * @param con an active database connection passed from the main app
     */
    public LanguageService(Connection con) {
        this.con = con;
    }


    public Language getLanguage(String language) {
        try {
            // Write the SQL query to find the language, including its country name
            String strSelect =
                    "SELECT " +
                            "ROUND(SUM(C.Population * (CL.Percentage / 100.0)), 0) AS TotalSpeakers, " +
                            "ROUND((SUM(C.Population * (CL.Percentage / 100.0)) / (SELECT SUM(Population) FROM country)) * 100.0, 2) AS WorldPopPercentage " +
                            "FROM " +
                            "countrylanguage AS CL " +
                            "JOIN " +
                            "country AS C ON CL.CountryCode = C.Code " +
                            "WHERE " +
                            "CL.Language = ? ";

            // Create a statement to send SQL commands to the database
            PreparedStatement stmt = con.prepareStatement(strSelect);

            stmt.setString(1, language);
            // Run the query and store the results
            ResultSet rset = stmt.executeQuery();

// Check if any result exists
            if (rset.next()) {
                // Get the numbers from the result
                int totalSpeakers = rset.getInt("TotalSpeakers");
                double worldPop = rset.getDouble("WorldPopPercentage");

                //  If the result is empty or zero, treat it as no data found
                if (rset.wasNull() || totalSpeakers == 0) {
                    return null;
                }

                // Otherwise build the Language object normally
                Language language1 = new Language();
                language1.setName(language);
                language1.setNumOfSpeakers(totalSpeakers);
                language1.setPercentageOfSpeakers(worldPop);
                return language1;
            } else {
                // No rows at all, also return null
                return null;
            }
        } catch (Exception e) {
            // If something goes wrong, show an error message
            System.out.println("Error getting language details: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
