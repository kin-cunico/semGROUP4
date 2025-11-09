package com.napier.semGROUP4.helper;
import java.sql.*;

/** DatabaseHelper class
 * this class is used to create a connection and to stop a connection to a database
 */
public class DatabaseHelper {

    private Connection con = null;
    boolean connected = false;

    public DatabaseHelper() { }

    public void connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        while (!connected) {
            System.out.println("Trying to connect to database...");
            try {
                Thread.sleep(1500);

                // read from env, default to localhost (local dev) or "db" (compose)
                String host = System.getenv("DB_HOST");
                if (host == null || host.isBlank()) host = "localhost";

                con = DriverManager.getConnection(
                        "jdbc:mysql://" + host + ":3306/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root",
                        "semgroup4"
                );
                System.out.println("Connected to database at host: " + host);
                connected = true;
                Thread.sleep(100);
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database... " + sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Interrupted? Check code.");
            }
        }
    }

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

    public Connection getConnection() {
        return this.con;
    }
}