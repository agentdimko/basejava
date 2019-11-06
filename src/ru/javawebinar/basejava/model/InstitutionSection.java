package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class InstitutionSection implements Section<Institution> {
    private final List<Institution> storage;
    private String title;

    public InstitutionSection(List<Institution> storage, String title) {
        this.storage = storage;
        this.title = title;
    }

    @Override
    public void addElement(Institution description) {
        storage.add(description);
    }

    @Override
    public void updateElement(int index, Institution element) {
        storage.set(index, element);
    }

    @Override
    public void removeElement(int index) {
        storage.remove(index);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstitutionSection that = (InstitutionSection) o;
        return storage.equals(that.storage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storage);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append("\n");
        for (Institution institution : storage) {
            sb.append(institution.toString());
        }
        return sb.toString();
    }
}
