package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int getIndex(String uuid) {
        if (storage.get(uuid) != null) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    protected void updateElement(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void saveElement(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteElement(int index, String uuid) {
        storage.remove(uuid);
    }
}
