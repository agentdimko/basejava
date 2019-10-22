package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected final static int STORAGE_LIMIT = 10_000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];


    @Override
    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Unable to save. Storage is full!!");
            return;
        }
        int index = getIndex(resume.getUuid());
        if(index > 0) {
            System.out.println("Unable to save. Resume with uuid " + resume.getUuid() + " is already exist");
            return;
        }
        saveToConcreteStorage(resume);
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Unable to delete. Resume with uuid " + uuid + " not found.");
        } else {
            System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
            size--;
        }
    }

    protected abstract int getIndex(String uuid);
    protected abstract void saveToConcreteStorage(Resume resume);
}
