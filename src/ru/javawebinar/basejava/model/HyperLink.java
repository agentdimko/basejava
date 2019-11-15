package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.util.Objects;

public class HyperLink implements Serializable {
    private static final long serialVersionUID = 1L;

    private String link;
    private String text;

    public HyperLink(String link, String text) {
        checkText(text, "");
        this.text = text;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HyperLink that = (HyperLink) o;
        return Objects.equals(link, that.link) &&
                text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, text);
    }

    @Override
    public String toString() {
        return link + ", " + text;
    }

    private static void checkText(String fullName, String uuid) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new RuntimeException("Missing text description");
        }
    }

}
