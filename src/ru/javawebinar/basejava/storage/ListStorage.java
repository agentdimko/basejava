package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index == -1) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage.set(index, resume);
        }
    }

    @Override
    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) == -1) {
            storage.add(resume);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        if (getIndex(uuid) == -1) {
            throw new NotExistStorageException(uuid);
        } else {
            return storage.get(getIndex(uuid));
        }
    }

    @Override
    public void delete(String uuid) {
        if (getIndex(uuid) == -1) {
            throw new NotExistStorageException(uuid);
        } else {
            storage.remove(getIndex(uuid));
        }
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
