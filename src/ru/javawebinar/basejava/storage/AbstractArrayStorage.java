package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected final static int STORAGE_LIMIT = 10_000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    protected void updateElement(int index, Resume resume) {
        storage[index] = resume;
    }

    protected void saveElement(int index, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insertElement(index, resume);
    }

    protected Resume getElement(int index, String uuid) {
        return storage[index];
    }

    protected void deleteElement(int index, String uuid) {
        fillDeletedElement(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void insertElement(int index, Resume resume);
    protected abstract void fillDeletedElement(int index);
}
