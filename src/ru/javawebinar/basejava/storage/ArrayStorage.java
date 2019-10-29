package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveElement(int index, Resume resume) {
        storage[size] = resume;
        size++;
    }

    @Override
    protected void doDelete(Object index) {
        storage[(int) index] = storage[size - 1];
        size--;
    }
}
