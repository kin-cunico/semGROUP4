package com.napier.semGROUP4;

// Represents a Capital City

/**
 * Represents a capital city with its name, country, and population.
 */
public class CapitalCity {

    // Variables

    // Name of City
    public String name;

    // Country of City
    public String country;

    // Population of City
    public int population;

    /**
     * Retrieves the capital city's name.
     *
     * @return the name of the capital city
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the country the capital city belongs to.
     *
     * @return the name of the country
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * Retrieves the population of the capital city.
     *
     * @return the population of the city
     */
    public int getPopulation() {
        return this.population;
    }

    /**
     * Returns a formatted string representation of the capital city.
     *
     * @return a string containing the city's name, country, and population
     */
    @Override
    public String toString() {
        return """
                Name: %s,
                Country: %s,
                Population: %d
                """.formatted(this.getName(), this.getCountry(), this.getPopulation()) ;
    }
}

