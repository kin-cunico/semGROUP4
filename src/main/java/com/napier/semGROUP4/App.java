package com.napier.semGROUP4;

import com.napier.semGROUP4.helper.DatabaseHelper;

public class App {

    public static void main(String[] args) {

        // TODO: create a menu to pass user inputs to fetch from our database

        // logs app initialisation
        System.out.println("Initialising app...");

        DatabaseHelper db = new DatabaseHelper();

        db.connectDB();

        db.closeDB();
    }
}
