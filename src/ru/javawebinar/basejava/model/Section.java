package ru.javawebinar.basejava.model;

import java.util.List;

public interface Section<E> {

    List<E> getAll();

    void addElement(E element);

    void insertElement(int index, E element);

    void removeElement(int index);

    void clear();

    int size();
}
