package com.napier.semGROUP4;

/**
 * Represents a city
 */
public class City
{
    /**
     * City name
     */
    public String name;

    /**
     * Country the city belongs to
     */
    public String country;

    /**
     * District of the city
     */
    public String district;

    /**
     * Population of the city
     */
    public int population;


    public City () {
    }

    public String getName() {
        return this.name;
    }

    public String getCountry() {
        return this.country;
    }

    public String getDistrict() {
        return this.district;
    }

    public int getPopulation() {
        return this.population;
    }


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