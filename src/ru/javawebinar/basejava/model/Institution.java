package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.basejava.util.DateUtil.NOW;
import static ru.javawebinar.basejava.util.DateUtil.of;

@XmlAccessorType(XmlAccessType.FIELD)
public class Institution implements Serializable {
    private static final long serialVersionUID = 1L;

    private HyperLink homePage;
    private List<Position> positions;

    public Institution() {
    }

    public Institution(String text, String link, Position... positions) {
        Objects.requireNonNull(text, "Text must not be null");
        Objects.requireNonNull(positions, "Positions must not be null");
        this.homePage = new HyperLink(link, text);
        this.positions = Arrays.asList(positions);
    }

    public Institution(HyperLink homePage, List<Position> positions) {
        this.homePage = homePage;
        this.positions = positions;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String position;
        private String description;

        public Position() {
        }

        public Position(int startYear, Month startMonth, String title, String description) {
            this(of(startYear, startMonth), NOW, title, description);
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), title, description);
        }

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

