package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Institution implements Serializable {
    private static final long serialVersionUID = 1L;

    private final HyperLink homePage;
    private final List<Position> positions;

    public Institution(HyperLink link, Position... positions) {
        Objects.requireNonNull(link, "Link must not be null");
        Objects.requireNonNull(positions, "Positions must not be null");
        this.homePage = link;
        this.positions = Arrays.asList(positions);
    }

    public static class Position implements Serializable {
        private LocalDate startDate;
        private LocalDate endDate;
        private String position;
        private String description;

        public Position(LocalDate startDate, LocalDate endDate, String position, String description) {
            Objects.requireNonNull(startDate, "Start date must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.position = position;
            this.description = description;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
            sb.append(formatter.format(startDate)).append(" - ").append((endDate == null) ? "Сейчас" :
                    formatter.format(endDate) + " ");
            sb.append(position == null ? " " : " " + position + " ");
            sb.append(description);
            return sb.toString();
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Institution that = (Institution) o;
        return homePage.equals(that.homePage) &&
                positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, positions);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (homePage != null) {
            sb.append(homePage.getLink()).append(" ").append(homePage.getText()).append(" ");
        }
        sb.append(positions.toString());
        return sb.toString();
    }
}

