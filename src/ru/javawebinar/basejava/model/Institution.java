package ru.javawebinar.basejava.model;

import java.time.LocalDate;

public class Institution {
    private String institution;
    private LocalDate startDate;
    private LocalDate endDate;
    private String position;
    private String description;

    public Institution(String institution, LocalDate startDate, LocalDate endDate, String position, String description) {
        this.institution = institution;
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.description = description;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
