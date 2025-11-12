package com.napier.semGROUP4.services;

import com.napier.semGROUP4.City;
import com.napier.semGROUP4.queries.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class LanguageService {
    private Connection con;
    private ArrayList<String> OrganisationLanguages;
    private ArrayList<Language> OrgsLangs;

    /**
     * Sets up LanguageService with an existing database connection.
     * @param con an active database connection passed from the main app
     */
    public LanguageService(Connection con) {
        this.con = con;
    }
    /**
     * Retrieves information about a specific language, including
     * the total number of speakers and their percentage of the world population.
     *
     * @param language the name of the language to search for
     * @return a Language object with details if found, otherwise null
     */
    public Language getLanguage(String language) {
        try {
            // Write the SQL query to find the language, including its country name
            String strSelect =
                    "SELECT " + "'" + language + "' AS Name, " +
                            "ROUND(SUM(C.Population * (CL.Percentage / 100.0)), 0) AS TotalSpeakers, " +
                            "ROUND((SUM(C.Population * (CL.Percentage / 100.0)) / (SELECT SUM(Population) FROM country)) * 100.0, 2) AS WorldPopPercentage " +
                            "FROM " +
                            "countrylanguage AS CL " +
                            "JOIN " +
                            "country AS C ON CL.CountryCode = C.Code " +
                            "WHERE " +
                            "CL.Language =  " + "'" + language + "'";

            // Create a statement to send SQL commands to the database
            Statement stmt = con.createStatement();
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


    public int getOrgLanguages() {
        ArrayList<String> OrganisationLanguages = new ArrayList<String>(Arrays.asList(
                "Chinese",
                "English",
                "Hindi",
                "Arabic",
                "Spanish"
        ));

        ArrayList<Language> OrgsLangs = new ArrayList<>();
        for (int i = 0; i < OrganisationLanguages.size(); i++) {
            OrgsLangs.add(getLanguage(OrganisationLanguages.get(i)));
        }

        // sorting from greatest to lowest
        OrgsLangs.sort(Comparator.comparing(Language::getNumOfSpeakers).reversed());

        for (Language l : OrgsLangs) {
            System.out.println(l);
        }
        return OrgsLangs.size();
    }
}
