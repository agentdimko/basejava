package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected final static int STORAGE_LIMIT = 10_000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];


    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("Unable to update. Resume with uuid " + resume.getUuid() + " not found");
        } else {
            storage[index] = resume;
        }
    }

    @Override
    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Unable to save. Storage is full!!");
            return;
        }
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            saveElement(resume, index);
            size++;
        } else {
            System.out.println("Unable to save. Resume with uuid " + resume.getUuid() + " is already exist");
        }

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
        Resume resume = null;
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not exist");
        } else {
            resume = storage[index];
        }
        return resume;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteElement(index);
            size--;
        } else {
            System.out.println("Unable to delete. Resume with uuid " + uuid + " not found.");
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract void saveElement(Resume resume, int index);

    protected abstract void deleteElement(int index);
}
