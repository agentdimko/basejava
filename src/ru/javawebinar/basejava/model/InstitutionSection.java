package ru.javawebinar.basejava.model;

import java.util.List;

public class InstitutionSection implements Section<Institution> {
    private List<Institution> storage;

    @Override
    public List<Institution> getAll() {
        return storage;
    }

    @Override
    public void addElement(Institution description) {
        storage.add(description);
    }

    @Override
    public void insertElement(int index, Institution description) {
        storage.add(index, description);
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
