package ru.javawebinar.basejava.model;

import java.util.*;

public class InstitutionSection implements Section {
    private final LinkedHashMap<HyperLink, List<Institution>> items;

    public InstitutionSection(LinkedHashMap<HyperLink, List<Institution>> items) {
        Objects.requireNonNull(items, "Items must not be null");
        this.items = items;
    }

    public LinkedHashMap<HyperLink, List<Institution>> getStorage() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstitutionSection section = (InstitutionSection) o;
        return items.equals(section.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ArrayList<Institution> list = null;
        for (Map.Entry entry : items.entrySet()) {
            sb.append(entry.getKey()).append(" ").append(entry.getValue());
        }
        return sb.toString();
    }
}
