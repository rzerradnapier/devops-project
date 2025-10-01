package com.napier.devops;

/**
 * Represents a country.
 * <p>
 * Uses getters and setters for encapsulation and data hiding.
 * The {@code toString} method is overridden for easy printing of country details.
 */
public class Country {
    private String code;
    private String name;
    private String continent;
    private String region;
    private Integer population;
    private Integer capital;

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

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Integer getCapital() {
        return capital;
    }

    public void setCapital(Integer capital) {
        this.capital = capital;
    }

    @Override
    public String toString() {
        return "Country {\n" +
                "  code='" + code + "',\n" +
                "  name='" + name + "',\n" +
                "  continent='" + continent + "',\n" +
                "  region='" + region + "',\n" +
                "  population=" + population + ",\n" +
                "  capital='" + capital + "'\n" +
                '}';
    }

}
