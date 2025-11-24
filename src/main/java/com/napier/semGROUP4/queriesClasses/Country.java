package com.napier.semGROUP4.queriesClasses;

public class Country {

    private String code;
    private String name;
    private String continent;
    private String region;
    private int population;
    private String capital;

    public Country() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }


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
