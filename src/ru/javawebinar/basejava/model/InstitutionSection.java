package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class InstitutionSection implements Section {
    private static final long serialVersionUID = 1L;

    private final List<Institution> items;

    public InstitutionSection(List<Institution> items) {
        Objects.requireNonNull(items, "Items must not be null");
        this.items = items;
    }

    public List<Institution> getStorage() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstitutionSection that = (InstitutionSection) o;
        return items.equals(that.items);
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
