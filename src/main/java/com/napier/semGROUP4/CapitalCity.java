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
     * Gets the capital city's name.
     *
     * @return the name of the capital city
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the country the capital city belongs to.
     *
     * @return the name of the country
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * Gets the population of the capital city.
     *
     * @return the population
     */
    public int getPopulation() {
        return this.population;
    }

    /**
     * Returns a formatted string showing the capital city's details.
     *
     * @return formatted string with name, country, and population
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
