package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Institution {
    private HyperLink homePage;
    private LocalDate startDate;
    private LocalDate endDate;
    private String position;
    private String description;

    public Institution(HyperLink link, LocalDate startDate, LocalDate endDate, String position,
                       String description) {
        this.homePage = link;
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.description = description;
    }

    public HyperLink getHomePage() {
        return homePage;
    }

    public void setHomePage(HyperLink homePage) {
        this.homePage = homePage;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Institution that = (Institution) o;
        return homePage.equals(that.homePage) &&
                startDate.equals(that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(position, that.position) &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, startDate, endDate, position, description);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append(formatter.format(startDate)).append(" - ").append((endDate == null) ? "Сейчас" :
                formatter.format(endDate) + " ");
        sb.append(position == null ? " " : " " + position + " ");
        sb.append(description);
        return sb.toString();
    }
}

