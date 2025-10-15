package com.napier.semGROUP4;

// Represents a Capital City

public class CapitalCity {

    // Variables

    // Name of City
    public String name;

    // Country of City
    public String country;

    // Population of City
    public int population;

    public String getName() {
        return this.name;
    }
    public String getCountry() {
        return this.country;
    }

    public int getPopulation() {
        return this.population;
    }

    @Override
    public String toString() {
        return """
                Name: %s,
                Country: %s,
                Population: %d
                """.formatted(this.getName(), this.getCountry(), this.getPopulation()) ;
    }
}
