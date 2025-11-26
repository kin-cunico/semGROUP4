package com.napier.semGROUP4.queriesClasses;

/**
 * Represents a country and its basic details from the database.
 */
public class Country {

    private String code;
    private String name;
    private String continent;
    private String region;
    private int population;
    private String capital;

    /** Empty constructor for creating a blank Country object. */
    public Country() {
    }

    /** @return the country's code (e.g. "USA", "GBR"). */
    public String getCode() {
        return code;
    }

    /** @param code sets the country code. */
    public void setCode(String code) {
        this.code = code;
    }

    /** @return the country's name. */
    public String getName() {
        return name;
    }

    /** @param name sets the country's name. */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the continent the country belongs to. */
    public String getContinent() {
        return continent;
    }

    /** @param continent sets the continent. */
    public void setContinent(String continent) {
        this.continent = continent;
    }

    /** @return the region the country belongs to. */
    public String getRegion() {
        return region;
    }

    /** @param region sets the region. */
    public void setRegion(String region) {
        this.region = region;
    }

    /** @return the population of the country. */
    public int getPopulation() {
        return population;
    }

    /** @param population sets the country's population. */
    public void setPopulation(int population) {
        this.population = population;
    }

    /** @return the name of the capital city. */
    public String getCapital() {
        return capital;
    }

    /** @param capital sets the country's capital city name. */
    public void setCapital(String capital) {
        this.capital = capital;
    }

    /**
     * Formats and returns the country details as a readable string.
     */
    @Override
    public String toString() {
        return """
                --------------
                Name: %s,
                Capital: %s,
                Continent: %s,
                Region: %s,
                Population: %d
                --------------
                """.formatted(this.getName(), this.getCapital(), this.getContinent(), this.getRegion(), this.getPopulation());
    }
}
