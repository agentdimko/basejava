package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class ListSection implements Section<String> {
    private final List<String> storage;
    private String title;

    public ListSection(List<String> storage, String title) {
        this.storage = storage;
        this.title = title;
    }

    @Override
    public void addElement(String text) {
        storage.add(text);
    }

    @Override
    public void updateElement(int index, String element) {
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
        ListSection section = (ListSection) o;
        return storage.equals(section.storage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storage);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append("\n");
        for (String s : storage) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }
}
