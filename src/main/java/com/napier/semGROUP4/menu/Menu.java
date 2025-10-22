package com.napier.semGROUP4.menu;

import java.io.Reader;
import java.util.Scanner;

public class Menu {

    public void menuStart() {
        boolean exit = false;

        while (!exit) {
            System.out.println("Please, choose a option below: ");
            System.out.printf(
                    """ 
                    1 - Query a city
                    2 - Query a country
                    3 - Query a continent
                    4 - Query a region
                    5 - Query a language
                    0 - Exit query menu
                    """);
            Scanner scanner = new Scanner(System.in);

            int option =  0; // Integer.parseInt(scanner.next());

            switch (option) {
                case 1:
                    // run query for city
                    break;
                case 2:
                    // run query for country
                    break;
                case 3:
                    // run query for continent
                    break;
                case 4:
                    // run query for region
                    break;
                case 5:
                    // run query for language
                    break;
                case 0:
                    // exit menu loop
                    exit = true;
                    break;
            }
        }
    }
}
