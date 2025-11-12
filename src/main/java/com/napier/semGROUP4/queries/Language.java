package com.napier.semGROUP4.queries;

/**
 * Represents a language with its name, number of speakers,
 * and percentage of the world population.
 */
public class Language {

    private String name;
    private int numOfSpeakers;
    private double percentageOfSpeakers;

    /**
     * Gets the language name.
     *
     * @return the name of the language
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the language name.
     *
     * @param name the name of the language
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the total number of people who speak this language.
     *
     * @return the number of speakers
     */
    public int getNumOfSpeakers() {
        return numOfSpeakers;
    }

    /**
     * Sets the total number of people who speak this language.
     *
     * @param numOfSpeakers the number of speakers
     */
    public void setNumOfSpeakers(int numOfSpeakers) {
        this.numOfSpeakers = numOfSpeakers;
    }

    /**
     * Gets the percentage of the world population
     * that speaks this language.
     *
     * @return the percentage of speakers globally
     */
    public double getPercentageOfSpeakers() {
        return percentageOfSpeakers;
    }

    /**
     * Sets the percentage of the world population
     * that speaks this language.
     *
     * @param percentageOfSpeakers the percentage of speakers globally
     */
    public void setPercentageOfSpeakers(double percentageOfSpeakers) {
        this.percentageOfSpeakers = percentageOfSpeakers;
    }
}
