package com.napier.semGROUP4.menu;

import java.sql.Connection;
import java.util.Scanner;
import com.napier.semGROUP4.City;
import com.napier.semGROUP4.CityService;
import com.napier.semGROUP4.Population;
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
                    1 - Query Population
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
                    1 - Calculate world population
                    2 - Calculate population of a continent
                    3 - Calculate population of a region
                    4 - Calculate population of a country
                    5 - Calculate population of a district
                    6 - Calculate population of a city
                    7 - Calculate the population of people living in and not living in cities for a continent
                    8 - Calculate the population of people living in and not living in cities for a region
                    9 - Calculate the population of people living in and not living in cities for a country
                    0 - Exit Population Reports
                    """);

            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {

                    long worldPop = PopulationService.calculateWorldPopulation();
                    System.out.print("World population: " + worldPop);
                    System.out.println();
                }

                case 2 -> {
                    System.out.print("Enter continent name: ");
                    String enteredContinent = scanner.nextLine();
                    if (enteredContinent != null) {
                        long continentPop = PopulationService.calculateContinentPopulation(enteredContinent);
                        System.out.print(enteredContinent + " population: " + continentPop);
                        System.out.println();
                    } else
                        System.out.println("continent not found.");
                }

                case 3 -> {
                    System.out.print("Enter region name: ");
                    String enteredRegion = scanner.nextLine();
                    if (enteredRegion != null) {
                        long regionPop = PopulationService.calculateRegionPopulation(enteredRegion);
                        System.out.print(enteredRegion + " population: " + regionPop);
                        System.out.println();
                    } else
                        System.out.println("region not found.");
                }

                case 4 -> {
                    System.out.print("Enter country name: ");
                    String enteredCountry = scanner.nextLine();
                    if (enteredCountry != null) {
                        long countryPop = PopulationService.calculateCountryPopulation(enteredCountry);
                        System.out.print(enteredCountry + " population: " + countryPop);
                        System.out.println();
                    } else
                        System.out.println("country not found.");
                }

                case 5 -> {
                    System.out.print("Enter district name: ");
                    String enteredDistrict = scanner.nextLine();
                    if (enteredDistrict != null) {
                        long districtPop = PopulationService.calculateDistrictPopulation(enteredDistrict);
                        System.out.print(enteredDistrict + " population: " + districtPop);
                        System.out.println();
                    } else
                        System.out.println("district not found.");
                }

                case 6 -> {
                    System.out.print("Enter city name: ");
                    String enteredCity = scanner.nextLine();
                    if (enteredCity != null) {
                        long cityPop = PopulationService.calculateCityPopulation(enteredCity);
                        System.out.print(enteredCity + " population: " + cityPop);
                        System.out.println();
                    } else
                        System.out.println("continent not found.");
                }

                case 7 -> {
                    System.out.print("Enter continent: ");
                    String enteredContinent = scanner.nextLine();
                    if (enteredContinent != null) {
                        Population stats = PopulationService.populationInContinent(enteredContinent);

                        double inCityPercentage = stats.total > 0 ? (stats.inCity * 100 / stats.total) : 0;
                        double outCityPercentage = stats.total > 0 ? (stats.outCity * 100 / stats.total) : 0;

                        System.out.print("Total population in " + enteredContinent + " " + stats.getTotal());
                        System.out.print(" The amount living in cities is: " + stats.getInCity() + " " + String.format("%.2f", inCityPercentage) + "%");
                        System.out.print(" The amount not living in cities is: " + stats.getOutCity() + " " + String.format("%.2f", outCityPercentage) + "%");
                        System.out.println();
                    } else
                        System.out.println("continent not found.");
                }

                case 8 -> {
                    System.out.print("Enter region: ");
                    String enteredRegion = scanner.nextLine();
                    if (enteredRegion != null) {
                        Population stats = PopulationService.populationInRegion(enteredRegion);

                        double inCityPercentage = stats.total > 0 ? (stats.inCity * 100 / stats.total) : 0;
                        double outCityPercentage = stats.total > 0 ? (stats.outCity * 100 / stats.total) : 0;

                        System.out.print("Total population in " + enteredRegion + " " + stats.getTotal());
                        System.out.print(" The amount living in cities is: " + stats.getInCity() + " " + String.format("%.2f", inCityPercentage) + "%");
                        System.out.print(" The amount not living in cities is: " + stats.getOutCity() + " " + String.format("%.2f", outCityPercentage) + "%");
                        System.out.println();
                    } else
                        System.out.println("region not found.");
                }

                case 9 -> {
                    System.out.print("Enter country: ");
                    String enteredCountry = scanner.nextLine();
                    if (enteredCountry != null) {
                        Population stats = PopulationService.populationInCountry(enteredCountry);

                        double inCityPercentage = stats.total > 0 ? (stats.inCity * 100 / stats.total) : 0;
                        double outCityPercentage = stats.total > 0 ? (stats.outCity * 100 / stats.total) : 0;

                        System.out.print("Total population in " + enteredCountry + " " + stats.getTotal());
                        System.out.print(" The amount living in cities is: " + stats.getInCity() + " " + String.format("%.2f", inCityPercentage) + "%");
                        System.out.print(" The amount not living in cities is: " + stats.getOutCity() + " " + String.format("%.2f", outCityPercentage) + "%");
                        System.out.println();
                    } else
                        System.out.println("country not found.");
                }

                case 0 -> {
                    populationExit = true;
                    System.out.println("Exiting City Reports...");
                }

                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}
