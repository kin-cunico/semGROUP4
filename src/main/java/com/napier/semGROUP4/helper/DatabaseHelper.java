package com.napier.semGROUP4.helper;

import java.sql.*;

/**
 * DatabaseHelper class
 * this class is used to create a connection and to stop a connection to a database
 */
public class DatabaseHelper {

    // initializing the connection to null
    private Connection con = null;
    boolean connected = false;

    /** empty constructor */
    public DatabaseHelper() {}

    /**
     * method connectDB()
     * used to connect to database by creating a sql driver
     * runs within a while loop
     */
    public void connectDB() {

        // tries to load sql driver for job
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // loop for trying to connect to database
        while (!connected) {
            System.out.println("Trying to connect to database...");
            try {
                Thread.sleep(3000);

                // ✅ get hostname dynamically (for GitHub/Docker/local)
                String host = System.getenv("DB_HOST");
                if (host == null || host.isEmpty()) {
                    host = "db"; // works in Docker Compose
                }

                // ✅ connect to database
                con = DriverManager.getConnection(
                        "jdbc:mysql://" + host + ":3306/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root",
                        "semgroup4"
                );

                System.out.println("Connected to database at host: " + host);
                connected = true;
                Thread.sleep(100);

            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database...");
            } catch (InterruptedException ie) {
                System.out.println("Interrupted? Check code.");
            }
        }
    }

    /** method closeDB() - closes the connection */
    public void closeDB() {
        if (con != null) {
            try {
                con.close();
                connected = false;
            } catch (Exception e) {
                System.out.println("Error closing connection " + e);
            }
        }
    }

    /** method getConnection() - returns current connection */
    public Connection getConnection() {
        return this.con;
    }
}