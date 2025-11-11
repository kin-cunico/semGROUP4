package com.napier.semGROUP4.menu;

import java.sql.Connection;
import java.util.Scanner;
import com.napier.semGROUP4.City;
import com.napier.semGROUP4.CityService;
import com.napier.semGROUP4.PopulationService;

/**
 * Console menu for user interaction.
 */
public class Menu {

    private final PopulationService PopulationService;

    public Menu(Connection con) {
        this.PopulationService = new PopulationService(con);
    }

    public void menuStart() {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        while (!exit) {
            System.out.println("""
                    Please, choose an option below:
                    1 - Query a city
                    2 - Query population
                    0 - Exit query menu
                    """);

            System.out.print("Enter your choice: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> populationReportMenu(scanner);
                case 0 -> {
                    exit = true;
                    System.out.println("Exiting query menu...");
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }

    private void populationReportMenu(Scanner scanner) {
        boolean populationExit = false;

        while (!populationExit) {
            System.out.println("""
                    --- population Reports ---
                    1 - Get a single city by name
                    2 - List all cities in the world
                    3 - List cities in a continent
                    4 - List cities in a region
                    5 - List cities in a country
                    6 - List cities in a district
                    7 - Show top N cities in the world
                    8 - Show top N cities in a continent
                    9 - Show top N cities in a region
                    10 - Show top N cities in a country
                    11 - Show top N cities in a district
                    0 - Exit City Reports
                    """);

            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {

                }

                case 2 -> {}

                case 0 -> {
                    populationExit = true;
                    System.out.println("Exiting City Reports...");
                }

                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}
