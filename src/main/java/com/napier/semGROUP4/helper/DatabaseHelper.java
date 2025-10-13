package com.napier.semGROUP4.helper;
import java.sql.*;

public class DatabaseHelper {

    // initializing the connection to null
    private Connection con = null;


    public DatabaseHelper() {
    }

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

    // Allows other parts of the program to call this method and get connection.
    public Connection getConnection() {
        return con;
    }

}
