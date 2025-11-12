package com.napier.semGROUP4.menu;

import java.sql.Connection;
import java.util.Scanner;
import com.napier.semGROUP4.City;
import com.napier.semGROUP4.services.CityService;
import com.napier.semGROUP4.services.LanguageService;

/**
 * Console menu for user interaction.
 */
public class Menu {

    private final CityService cityService;
    private final LanguageService languageService;

    /**
     * Initializes the menu with CityService and LanguageService using the given connection.
     *
     * @param con the active database connection
     */
    public Menu(Connection con) {
        this.cityService = new CityService(con);
        this.languageService = new LanguageService(con);
    }

    /**
     * Starts the main interactive loop for the application menu.
     * Users can choose city reports, language reports, or exit.
     */
    public void menuStart() {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        while (!exit) {
            System.out.println("""
                    Please, choose an option below:
                    1 - Query a city
                    2 - Query a language
                    0 - Exit query menu
                    """);

            System.out.print("Enter your choice: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> cityReportMenu(scanner);
                case 2 -> languageReportMenu(scanner);
                case 0 -> {
                    exit = true;
                    System.out.println("Exiting query menu...");
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }

    /**
     * Displays and handles the Language Reports submenu.
     *
     * @param scanner the input scanner used to read user choices
     */
    private void languageReportMenu(Scanner scanner) {

        boolean languageExit = false;

        while (!languageExit) {
            System.out.println("""
                    --- Language Reports ---
                    1 - Get a language report
                    2 - Show top 5 languages in the world
                    3 - Show Organisation Languages
                    
                    0 - Exit Language Reports
                    """);
            System.out.println("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

            }
        }
    }

    /**
     * Displays and handles the City Reports submenu, including queries
     * by name, continent, region, country, district, and top-N listings.
     *
     * @param scanner the input scanner used to read user choices
     */
    private void cityReportMenu(Scanner scanner) {
        boolean cityExit = false;

        while (!cityExit) {
            System.out.println("""
                    --- City Reports ---
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
                    System.out.print("Enter city name: ");
                    String cityName = scanner.nextLine();
                    City city = cityService.getCity(cityName);
                    if (city != null)
                        System.out.println(city);
                    else
                        System.out.println("City not found.");
                }

                case 2 -> cityService.displayCities(cityService.getAllCitiesInWorld());

                case 3 -> {
                    System.out.print("Enter continent name: ");
                    cityService.displayCities(cityService.getCitiesInContinent(scanner.nextLine()));
                }

                case 4 -> {
                    System.out.print("Enter region name: ");
                    cityService.displayCities(cityService.getCitiesInRegion(scanner.nextLine()));
                }

                case 5 -> {
                    System.out.print("Enter country name: ");
                    cityService.displayCities(cityService.getCitiesInCountry(scanner.nextLine()));
                }

                case 6 -> {
                    System.out.print("Enter district name: ");
                    cityService.displayCities(cityService.getCitiesInDistrict(scanner.nextLine()));
                }

                case 7 -> {
                    System.out.print("Enter number of top cities to show: ");
                    int nWorld = Integer.parseInt(scanner.nextLine());
                    cityService.displayCities(cityService.getTopNCitiesInWorld(nWorld));
                }

                case 8 -> {
                    System.out.print("Enter continent name: ");
                    String cont = scanner.nextLine();
                    System.out.print("Enter number of top cities to show: ");
                    int nCont = Integer.parseInt(scanner.nextLine());
                    cityService.displayCities(cityService.getTopNCitiesInContinent(cont, nCont));
                }

                case 9 -> {
                    System.out.print("Enter region name: ");
                    String reg = scanner.nextLine();
                    System.out.print("Enter number of top cities to show: ");
                    int nReg = Integer.parseInt(scanner.nextLine());
                    cityService.displayCities(cityService.getTopNCitiesInRegion(reg, nReg));
                }

                case 10 -> {
                    System.out.print("Enter country name: ");
                    String ctry = scanner.nextLine();
                    System.out.print("Enter number of top cities to show: ");
                    int nCtry = Integer.parseInt(scanner.nextLine());
                    cityService.displayCities(cityService.getTopNCitiesInCountry(ctry, nCtry));
                }

                case 11 -> {
                    System.out.print("Enter district name: ");
                    String dist = scanner.nextLine();
                    System.out.print("Enter number of top cities to show: ");
                    int nDist = Integer.parseInt(scanner.nextLine());
                    cityService.displayCities(cityService.getTopNCitiesInDistrict(dist, nDist));
                }

                case 0 -> {
                    cityExit = true;
                    System.out.println("Exiting City Reports...");
                }

                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}
