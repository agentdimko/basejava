package com.dimko.webapp.storage;

import com.dimko.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final static int STORAGE_FULL_SIZE = 10_000;
    private int size;
    private Resume[] storage = new Resume[STORAGE_FULL_SIZE];

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
        }
    }

    public void save(Resume r) {
        if (size == STORAGE_FULL_SIZE) {
            System.out.println("Storage is full!!");
            return;
        }
        if (findIndex(r.getUuid()) != -1) {
            System.out.println("Resume wit uuid = " + r.getUuid() + " is already exist");
            return;
        }
        storage[size] = r;
        size++;
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        return (index != -1) ? storage[index] : null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        System.out.println("Resume with uuid = " + uuid + " not found!");
        return -1;
    }
}
