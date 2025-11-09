package com.napier.semGROUP4.helper;

import java.sql.*;

/**
 * DatabaseHelper class — creates and closes a connection to the database.
 */
public class DatabaseHelper {

    private Connection con = null;
    private boolean connected = false;

    public DatabaseHelper() { }

    /**
     * Connects to a database with configurable location and delay.
     * Example: connectDB("localhost:3306", 3000)
     */
    public void connectDB(String location, int delay) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // Default connection details
        String host = (location == null || location.isEmpty()) ? "localhost:3306" : location;
        String url = "jdbc:mysql://localhost:3306/world?allowPublicKeyRetrieval=true&useSSL=false";
        String user = "root";
        String password = "semgroup4";

        int retries = 10;
        while (!connected && retries > 0) {
            System.out.println("Attempting to connect to database (" + retries + " retries left)...");
            try {
                Thread.sleep(delay);
                con = DriverManager.getConnection(url, user, password);
                connected = true;
                System.out.println(" Connected to database at " + host);
            } catch (SQLException e) {
                System.out.println("Failed to connect to database: " + e.getMessage());
                retries--;
            } catch (InterruptedException ie) {
                System.out.println("Interrupted while waiting to connect.");
            }
        }

        if (!connected) {
            System.out.println(" Could not establish a connection to database after multiple attempts.");
        }
    }

    /** Default connect for local debug (localhost:3306, 3s delay) */
    public void connectDB() {
        connectDB("localhost:3306", 3000);
    }

    /** Closes the database connection. */
    public void closeDB() {
        if (con != null) {
            try {
                con.close();
                connected = false;
                System.out.println(" Database connection closed.");
            } catch (SQLException e) {
                System.out.println("Error closing database: " + e.getMessage());
            }
        }
    }

    public Connection getConnection() {
        return this.con;
    }

    public boolean isConnected() {
        return this.connected;
    }
}