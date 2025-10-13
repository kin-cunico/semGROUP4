package com.napier.semGROUP4;
import java.sql.*;
public class App {

    // initializing the connection to null
    private Connection con = null;

    // connect to database method TODO: create a class that handles utilities
    public void connectDB() {

        // tries to load sql driver for job
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }
        // number of times app should try to connect to database
        int retries = 30;

        // loop for trying to connect to database
        // TODO: this method is a copy paste from our lab3, we should plan a method that just keeps the connection open
        for (int i = 0; i < retries; i++) {
            System.out.println("Trying to connect to database...");

            try {

                // waits 3 seconds before trying to assign connection
                Thread.sleep(3000);

                // assigns user and password to connection to connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "semgroup4");
                System.out.println("Connected to database!");

                // wait 100ms to exit this trial
                Thread.sleep(100);
            }
            catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
            }
            catch (InterruptedException ie) {
                System.out.println("Interrupted? Check code.");
            }
        }
    }


    // close database connection method
    // TODO: as with our connection method, we should move this into a class that handle utilities
    public void closeDB() {

        // if our connection is null we try to close it
        if (con != null) {
            try {
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection " + e);
            }
        }
    }

    public static void main(String[] args) {

        // TODO: create a menu to pass user inputs to fetch from our database

        // logs app initialisation
        System.out.println("Initialising app...");

        // New print statement
        System.out.println("Test");

        // creates an app object to start our methods
        // TODO: since the methods should have their own class, we will need to change the object for that new class
        App a = new App();

        // call connectDB method on our app
        a.connectDB();

        // call closeDB method on our app
        a.closeDB();
    }
}
