package com.napier.semGROUP4;

import com.napier.semGROUP4.helper.DatabaseHelper;

public class App {

    public static void main(String[] args) {

        // TODO: create a menu to pass user inputs to fetch from our database

        // logs app initialisation
        System.out.println("Initialising app...");

        DatabaseHelper db = new DatabaseHelper();

        db.connectDB();

// We are about to test our CityService class to see if it can fetch a city's details from the database
        try {
            // Create a CityService object, giving it the current active database connection
            CityService cityService = new CityService(db.getConnection());

            // Ask the CityService to find the city named "London"
            City city = cityService.getCity("London");

            // Check if the city was found in the database
            if (city != null) {
                // If found, print out all the details of the city
//                System.out.println("\nCity details retrieved successfully:");
//                System.out.println("Name: " + city.name);        // The name of the city
//                System.out.println("Country: " + city.country); // The country the city belongs to
//                System.out.println("District: " + city.district); // The district or state where the city is located
//                System.out.println("Population: " + city.population); // Number of people living in the city
                System.out.println(city.toString());
            } else {
                // If not found, inform the user
                System.out.println("City not found in the database.");
            }
        } catch (Exception e) {
            // If there is any problem while trying to fetch the city, display a simple error message
            System.out.println("Error testing CityService: " + e.getMessage());
        }

// Close the database connection now that we are done with it
        db.closeDB();
    }
}
