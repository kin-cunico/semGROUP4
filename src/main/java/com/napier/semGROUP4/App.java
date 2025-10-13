package com.napier.semGROUP4;

public class App {

    private final DatabaseConnectionManager databaseConnectionManager;

    public App(DatabaseConnectionManager databaseConnectionManager) {
        this.databaseConnectionManager = databaseConnectionManager;
    }

    public void run() {
        databaseConnectionManager.connect();
        // TODO: Add application logic that uses databaseConnectionManager.getConnection().
    }

    public static void main(String[] args) {
        System.out.println("Initialising app...");

        try (DatabaseConnectionManager connectionManager = DatabaseConnectionManager.fromEnvironment()) {
            App application = new App(connectionManager);
            application.run();
        } catch (IllegalStateException ex) {
            System.err.println("Application failed to connect to the database: " + ex.getMessage());
        }
    }
}
