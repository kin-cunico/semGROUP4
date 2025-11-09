package com.napier.semGROUP4.menu;

import java.util.Scanner;
import java.sql.Connection;
import com.napier.semGROUP4.City;
import com.napier.semGROUP4.CityService;

public class Menu {

    private final CityService cityService;

    // constructor to give this menu access to CityService
    public Menu(Connection con) {
        this.cityService = new CityService(con);
    }

    public void menuStart() {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        while (!exit) {
            System.out.println("Please, choose an option below:");
            System.out.print("""
                    1 - Query a city
                    2 - Query a country
                    3 - Query a continent
                    4 - Query a region
                    5 - Query a language
                    0 - Exit query menu
                    """);

            System.out.print("Enter your choice: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    boolean cityExit = false;
                    Scanner cityScanner = new Scanner(System.in);

                    while (!cityExit) {
                        System.out.println("\n--- City Reports ---");
                        System.out.print("""
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
                        int choice = cityScanner.nextInt();
                        cityScanner.nextLine(); // consume leftover newline

                        switch (choice) {
                            case 1:
                                System.out.print("Enter city name: ");
                                String cityName = cityScanner.nextLine();
                                City city = cityService.getCity(cityName);
                                if (city != null) {
                                    System.out.printf("Name: %s, Country: %s, District: %s, Population: %d%n",
                                            city.name, city.country, city.district, city.population);
                                } else {
                                    System.out.println("City not found.");
                                }
                                break;

                            case 2:
                                cityService.displayCities(cityService.getAllCitiesInWorld());
                                break;

                            case 3:
                                System.out.print("Enter continent name: ");
                                String continent = cityScanner.nextLine();
                                cityService.displayCities(cityService.getCitiesInContinent(continent));
                                break;

                            case 4:
                                System.out.print("Enter region name: ");
                                String region = cityScanner.nextLine();
                                cityService.displayCities(cityService.getCitiesInRegion(region));
                                break;

                            case 5:
                                System.out.print("Enter country name: ");
                                String country = cityScanner.nextLine();
                                cityService.displayCities(cityService.getCitiesInCountry(country));
                                break;

                            case 6:
                                System.out.print("Enter district name: ");
                                String district = cityScanner.nextLine();
                                cityService.displayCities(cityService.getCitiesInDistrict(district));
                                break;

                            case 7:
                                System.out.print("Enter number of top cities to show: ");
                                int nWorld = cityScanner.nextInt();
                                cityService.displayCities(cityService.getTopNCitiesInWorld(nWorld));
                                break;

                            case 8:
                                System.out.print("Enter continent name: ");
                                String cont = cityScanner.nextLine();
                                System.out.print("Enter number of top cities to show: ");
                                int nCont = cityScanner.nextInt();
                                cityService.displayCities(cityService.getTopNCitiesInContinent(cont, nCont));
                                break;

                            case 9:
                                System.out.print("Enter region name: ");
                                String reg = cityScanner.nextLine();
                                System.out.print("Enter number of top cities to show: ");
                                int nReg = cityScanner.nextInt();
                                cityService.displayCities(cityService.getTopNCitiesInRegion(reg, nReg));
                                break;

                            case 10:
                                System.out.print("Enter country name: ");
                                String ctry = cityScanner.nextLine();
                                System.out.print("Enter number of top cities to show: ");
                                int nCtry = cityScanner.nextInt();
                                cityService.displayCities(cityService.getTopNCitiesInCountry(ctry, nCtry));
                                break;

                            case 11:
                                System.out.print("Enter district name: ");
                                String dist = cityScanner.nextLine();
                                System.out.print("Enter number of top cities to show: ");
                                int nDist = cityScanner.nextInt();
                                cityService.displayCities(cityService.getTopNCitiesInDistrict(dist, nDist));
                                break;

                            case 0:
                                cityExit = true;
                                System.out.println("Exiting City Reports...");
                                break;

                            default:
                                System.out.println("Invalid option. Try again.");
                        }
                    }
                    break;

                case 0:
                    exit = true;
                    System.out.println("Exiting query menu...");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }
}
