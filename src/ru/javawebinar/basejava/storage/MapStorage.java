package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Object getIndex(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isResumeExist(Object index) {
        if (storage.containsKey(index)) {
            return true;
        }
        return false;
    }

    @Override
    protected void doUpdate(Object index, Resume resume) {
        storage.replace((String) index, resume);
    }

    @Override
    protected void doSave(Object index, Resume resume) {
        storage.put((String) index, resume);
    }

    @Override
    protected Resume doGet(Object index) {
        return storage.get((String) index);
    }

    @Override
    protected void doDelete(Object index) {
        storage.remove((String) index);
    }

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
}
