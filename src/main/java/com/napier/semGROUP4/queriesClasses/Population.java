package com.napier.semGROUP4.queriesClasses;

/**
 * Represents the statistics for the amount of the population that lives in and not in cities along with the overall total
 */
public class Population {

    /**
     * Overall total population
     */
    public long total;

    /**
     * Total that live in cities
     */
    public long inCity;

    /**
     * Total that do not live in cities
     */
    public long outCity;


    public Population(long total, long inCity, long outCity) {
        this.total = total;
        this.inCity = inCity;
        this.outCity = outCity;
    }

    public long getTotal() { return  total; }
    public long getInCity() { return inCity;}
    public long getOutCity() { return outCity; }

    @Override
    public String toString() {
        return """
                Total population: %d,
                Living in cities: %d,
                Living outside cities: %d
                """.formatted(getTotal(), getInCity(), getOutCity());
    }
}

