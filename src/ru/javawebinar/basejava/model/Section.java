package ru.javawebinar.basejava.model;

public interface Section<E> {

    void addElement(E element);

    void updateElement(int index, E element);

    void removeElement(int index);

    int size();

    void clear();
}
