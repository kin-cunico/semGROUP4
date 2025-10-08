package com.napier.semGROUP4;
import java.sql.*;
public class App {
    private Connection con = null;


    public void connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }
        int retries = 50;

        for (int i = 0; i < retries; i++) {
            System.out.println("Trying to connect to database...");

            try {
                Thread.sleep(30000);

                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "semgroup4");
                System.out.println("Connected to database!");

                Thread.sleep(1000);
            }
            catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
            }
            catch (InterruptedException ie) {
                System.out.println("Interrupted? Check code.");
            }
        }
    }


    public void closeDB() {
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
        System.out.println("Initialising app...");

        App a = new App();

        a.connectDB();
        a.closeDB();
    }
}
