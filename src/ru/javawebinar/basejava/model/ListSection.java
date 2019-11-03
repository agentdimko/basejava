package ru.javawebinar.basejava.model;

import java.util.List;

public class ListSection implements Section<String> {
    private List<String> storage;

    public ListSection(List<String> storage) {
        this.storage = storage;
    }

    @Override
    public List<String> getAll() {
        return storage;
    }

    @Override
    public void addElement(String text) {
        storage.add(text);
    }

    @Override
    public void insertElement(int index, String text) {
        storage.add(index, text);
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
}
