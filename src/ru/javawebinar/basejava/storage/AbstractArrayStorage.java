package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected final static int STORAGE_LIMIT = 10_000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected abstract void deleteElement(int index);

    protected abstract void saveElement(int index, Resume resume);

    @Override
    protected void doDelete(Integer index) {
        deleteElement(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected void doUpdate(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected void doSave(Integer index, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveElement(index, resume);
        size++;
    }

    @Override
    protected Resume doGet(Integer index) {
        return storage[index];
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected List<Resume> getList() {
        Resume[] resumes = Arrays.copyOf(storage, size);
        return Arrays.asList(resumes);
    }

    @Override
    public int size() {
        return size;
    }
}
