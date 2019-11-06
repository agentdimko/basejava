package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Institution {
    private String institution;
    private String url;
    private LocalDate startDate;
    private LocalDate endDate;
    private String position;
    private String description;

    public Institution(String institution, String url, LocalDate startDate, LocalDate endDate, String position, String description) {
        this.institution = institution;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        return institution.equals(that.institution) &&
                url.equals(that.url) &&
                startDate.equals(that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(position, that.position) &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(institution, url, startDate, endDate, position, description);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        StringBuilder sb = new StringBuilder();
        if (url == null) {
            sb.append(institution).append("\n");
        } else {
            sb.append("<a href=\"").append(url).append("\">").append(institution).append("</a>\n");
        }
        sb.append(formatter.format(startDate)).append(" - ").append((endDate == null) ? "Сейчас" :
                formatter.format(endDate)).append(
                "\t")
                .append((position == null) ? "" : position + "\n")
                .append(description)
                .append("\n");
        return sb.toString();
    }
}
