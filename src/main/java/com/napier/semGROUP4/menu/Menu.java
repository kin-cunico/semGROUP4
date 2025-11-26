package com.napier.semGROUP4.menu;

import java.sql.Connection;
import java.util.Scanner;

import com.napier.semGROUP4.queriesClasses.City;
import com.napier.semGROUP4.queriesClasses.Language;
import com.napier.semGROUP4.queriesClasses.Population;
import com.napier.semGROUP4.services.*;

/**
 * Console menu for user interaction.
 */
public class Menu {

    private final CityService cityService;
    private final CapitalCityService capitalCityService;
    private final LanguageService languageService;
    private final CountryService countryService;
    private final PopulationService populationService;

    /**
     * Initializes the menu with city, language, and capital city services.
     *
     * @param con active database connection used by services
     */
    public Menu(Connection con) {
        this.cityService = new CityService(con);
        this.languageService = new LanguageService(con);
        this.capitalCityService = new CapitalCityService(con);
        this.countryService = new CountryService(con);
        this.populationService = new PopulationService(con);
    }

    public void menuSample() {
        String cityName = "Edinburgh";
        String languageName = "Arabic";
        System.out.println("Printing sample to show app is working...");
        System.out.println("---- \n");
        City city = cityService.getCity(cityName);
        System.out.println(city);
        System.out.println("---- \n");
        Language language = languageService.getLanguage(languageName);
        System.out.println(language);
        System.out.println("---- \n");
        System.out.println(countryService.getAllCountriesByPop(5));
        System.out.println("---- \n");
        System.out.println(countryService.getAllCountriesInCont("Oceania", 4));
        System.out.println("---- \n");
        System.out.println(countryService.getAllCountriesByRegion("Central", 4));
        System.out.println("---- \n");
        System.out.println(languageService.getOrgLanguages());
        System.out.println("---- \n");
        System.out.println("Asia");
        System.out.println(populationService.populationInContinent("Asia"));
        System.out.println("---- \n");
        System.out.println("Exiting app...");
    }

    /**
     * Starts the main menu loop, routing to city, language,
     * or capital city report submenus until the user exits.
     */
    public void menuStart() {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        while (!exit) {
            System.out.println("""
                    Please, choose an option below:
                    1 - Query a city
                    2 - Query a language
                    3 - Query a Capital city :DDDDDD
                    4 - Query Population
                    0 - Exit query menu
                    """);

            System.out.print("Enter your choice: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> cityReportMenu(scanner);
                case 2 -> languageReportMenu(scanner);
                case 3 -> capitalCityReportMenu(scanner);
                case 4 -> populationReportMenu(scanner);
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
     * Displays and handles the Capital City Reports submenu.
     *
     * @param scanner scanner used for user input
     */
    private void capitalCityReportMenu(Scanner scanner) {

        boolean capitalExit = false;

        while (!capitalExit) {
            System.out.println("""
                    --- Capital City Reports ---
                    1 - All the capital cities in the world organised from largest to smallest.
                    2 - All the capital cities in a continent
                    3 - All the capital cities in a region
                    4 - Top populated capital cities in the world which the list size is chosen by the user.
                    5 - Top populated capital cities in a continent which the list size is chosen by the user.
                    6 - Get the top populated capital cities in a region which the list size is organised by the user.
                    
                    0 - Exit CapitalCity Reports
                    """);

            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {capitalCityService.displayCapitalCities(capitalCityService.getAllCapitalsInWorld());}

                case 2 -> {
                    System.out.print("Enter continent name: ");
                    capitalCityService.displayCapitalCities(capitalCityService.getAllCapitalsInContinent(scanner.nextLine()));
                }

                case 3 -> {
                    System.out.print("Enter region name: ");
                    capitalCityService.displayCapitalCities(capitalCityService.getAllCapitalsInRegion(scanner.nextLine()));
                }

                case 4 -> {
                    System.out.print("Enter how many top city capitals to show: ");
                    capitalCityService.displayCapitalCities(capitalCityService.worldTopPopulatedCapitals(Integer.parseInt(scanner.nextLine())));
                }

                case 5 -> {
                    System.out.print("Enter continent name: ");
                    String continent = scanner.nextLine();
                    System.out.print("Enter number of continents to show: ");
                    int listSize = Integer.parseInt(scanner.nextLine());
                    capitalCityService.displayCapitalCities(capitalCityService.continentTopPopulatedCapitals(continent, listSize));
                }

                case 6 -> {
                    System.out.print("Enter region name: ");
                    String region = scanner.nextLine();
                    System.out.print("Enter number of regions to show: ");
                    int listSize = Integer.parseInt(scanner.nextLine());
                    capitalCityService.displayCapitalCities(capitalCityService.regionTopPopulatedCapitals(region, listSize));
                }

                case 0 -> {
                    capitalExit = true;
                    System.out.println("Exiting Capital City Reports...");
                }

                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Displays and handles the Language Reports submenu.
     *
     * @param scanner scanner used for user input
     */
    private void languageReportMenu(Scanner scanner) {

        boolean languageExit = false;

        while (!languageExit) {
            System.out.println("""
                    --- Language Reports ---
                    1 - Get a language report
                    2 - Show Organisation Languages Report
                    
                    0 - Exit Language Reports
                    """);
            System.out.println("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());


            switch (choice) {
                case 1 -> {
                    System.out.println("Type a language: ");
                    String languageName = scanner.nextLine();
                    Language language = languageService.getLanguage(languageName);

                    if (language != null) {
                        System.out.println(language);
                    }
                    else {
                        System.out.println("Language not found.");
                    }

                }
                case 2 -> {
                    System.out.println("--- Organisation languages: ");
                    languageService.getOrgLanguages();
                }

                case 0 -> {
                    languageExit = true;
                    System.out.println("Exiting Language Reports...");
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Displays and manages the city report submenu.
     * Provides multiple options for querying and listing cities
     * by continent, region, country, district, or population rank.
     *
     * @param scanner the Scanner instance used for reading user input
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

                    long worldPop = populationService.calculateWorldPopulation();
                    System.out.print("World population: " + worldPop);
                    System.out.println();
                }

                case 2 -> {
                    System.out.print("Enter continent name: ");
                    String enteredContinent = scanner.nextLine();
                    if (enteredContinent != null) {
                        long continentPop = populationService.calculateContinentPopulation(enteredContinent);
                        System.out.print(enteredContinent + " population: " + continentPop);
                        System.out.println();
                    } else
                        System.out.println("continent not found.");
                }

                case 3 -> {
                    System.out.print("Enter region name: ");
                    String enteredRegion = scanner.nextLine();
                    if (enteredRegion != null) {
                        long regionPop = populationService.calculateRegionPopulation(enteredRegion);
                        System.out.print(enteredRegion + " population: " + regionPop);
                        System.out.println();
                    } else
                        System.out.println("region not found.");
                }

                case 4 -> {
                    System.out.print("Enter country name: ");
                    String enteredCountry = scanner.nextLine();
                    if (enteredCountry != null) {
                        long countryPop = populationService.calculateCountryPopulation(enteredCountry);
                        System.out.print(enteredCountry + " population: " + countryPop);
                        System.out.println();
                    } else
                        System.out.println("country not found.");
                }

                case 5 -> {
                    System.out.print("Enter district name: ");
                    String enteredDistrict = scanner.nextLine();
                    if (enteredDistrict != null) {
                        long districtPop = populationService.calculateDistrictPopulation(enteredDistrict);
                        System.out.print(enteredDistrict + " population: " + districtPop);
                        System.out.println();
                    } else
                        System.out.println("district not found.");
                }

                case 6 -> {
                    System.out.print("Enter city name: ");
                    String enteredCity = scanner.nextLine();
                    if (enteredCity != null) {
                        long cityPop = populationService.calculateCityPopulation(enteredCity);
                        System.out.print(enteredCity + " population: " + cityPop);
                        System.out.println();
                    } else
                        System.out.println("continent not found.");
                }

                case 7 -> {
                    System.out.print("Enter continent: ");
                    String enteredContinent = scanner.nextLine();
                    if (enteredContinent != null) {
                        Population stats = populationService.populationInContinent(enteredContinent);

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
                        Population stats = populationService.populationInRegion(enteredRegion);

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
                        Population stats = populationService.populationInCountry(enteredCountry);

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

