package com.napier.semGROUP4;

import com.napier.semGROUP4.helper.DatabaseHelper;
import com.napier.semGROUP4.menu.Menu;

/**
 * Main entry point for the application.
 * Initializes the database connection and launches the main menu.
 */
public class App {

    /**
     * Starts the application, connects to the database,
     * and opens the interactive menu.
     *
     * @param args optional command-line arguments:
     *             host:port and connection delay in milliseconds
     */
    public static void main(String[] args) {

        System.out.println("Initialising app...");
        DatabaseHelper db = new DatabaseHelper();

        // If command-line args are supplied, use them; otherwise default to local.
        if (args.length < 2) {
            System.out.println("No command-line arguments found. Connecting to localhost for local debugging...");
            db.connectDB("localhost:3306", 3000);
        } else {
            db.connectDB(args[0], Integer.parseInt(args[1]));
        }

        // Run menu if connected
        if (db.isConnected()) {
            Menu menu = new Menu(db.getConnection());
            menu.menuStart();
        } else {
            System.out.println("Database connection failed. Exiting...");
        }

        db.closeDB();
        System.out.println("Application finished.");
    }
}
