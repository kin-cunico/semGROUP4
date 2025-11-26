package com.napier.semGROUP4;

/**
 * Represents a capital city and its basic details.
 */
public class CapitalCity {

    // Name of the capital city
    public String name;

    // Country the capital city belongs to
    public String country;

    // Population of the capital city
    public int population;

    /** @return the name of the capital city */
    public String getName() {
        return this.name;
    }

    /** @return the country this capital city is in */
    public String getCountry() {
        return this.country;
    }

    /** @return the population of the capital city */
    public int getPopulation() {
        return this.population;
    }

    /**
     * Returns the capital city's details as a formatted string.
     */
    @Override
    public String toString() {
        return """
                Name: %s,
                Country: %s,
                Population: %d
                """.formatted(this.getName(), this.getCountry(), this.getPopulation());
    }
}
