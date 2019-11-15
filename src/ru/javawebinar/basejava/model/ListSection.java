package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class ListSection implements Section {
    private static final long serialVersionUID = 1L;

    private final List<String> items;

    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "Items must not be null");
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection section = (ListSection) o;
        return items.equals(section.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    @Override
    public String toString() {
        return items.toString();
    }
}
