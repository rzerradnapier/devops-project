package com.napier.pojo;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * POJO class to represent language report data.
 *
 * @author Group 3
 * @since 11th November 2025
 */
public class LanguageReportPojo {
    private String language;
    private Long speakers = 0L;
    private Long worldPopulation = 0L;
    private Double percentageOfWorld = 0.0;

    // Getters and setters
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getSpeakers() {
        return speakers;
    }

    public void setSpeakers(Long speakers) {
        this.speakers = speakers;
    }

    public Long getWorldPopulation() {
        return worldPopulation;
    }

    public void setWorldPopulation(Long worldPopulation) {
        this.worldPopulation = worldPopulation;
    }

    public Double getPercentageOfWorld() {
        return percentageOfWorld;
    }

    public void setPercentageOfWorld(Double percentageOfWorld) {
        this.percentageOfWorld = percentageOfWorld;
    }

    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        String formattedSpeakers = nf.format(speakers);

        return String.format("%-10s | %15s | %6.2f%% of world population",
                language, formattedSpeakers, percentageOfWorld);
    }
}
