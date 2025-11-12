package com.napier.semGROUP4;

/**
 * Represents a city with its name, country, district, and population.
 */
public class City
{
    /**
     * City name.
     */
    public String name;

    /**
     * Country the city belongs to.
     */
    public String country;

    /**
     * District of the city.
     */
    public String district;

    /**
     * Population of the city.
     */
    public int population;

    /**
     * Default constructor for creating an empty City object.
     */
    public City () {
    }

    /**
     * Gets the city's name.
     *
     * @return the name of the city
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the country the city belongs to.
     *
     * @return the name of the country
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * Gets the city's district.
     *
     * @return the name of the district
     */
    public String getDistrict() {
        return this.district;
    }

    /**
     * Gets the city's population.
     *
     * @return the population count
     */
    public int getPopulation() {
        return this.population;
    }

    /**
     * Returns a formatted string with the city's details.
     *
     * @return a formatted string containing name, country, district, and population
     */
    @Override
    public String toString() {
        return """
                Name: %s,
                Country: %s,
                District: %s,
                Population: %d
                """.formatted(this.getName(), this.getCountry(), this.getDistrict(), this.getPopulation()) ;
    }
}
