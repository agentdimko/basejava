package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object index) {
        return (int) index != -1;
    }

    @Override
    protected void doUpdate(Object index, Resume resume) {
        storage.set((int) index, resume);
    }

    @Override
    protected void doSave(Object index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume doGet(Object index) {
        return storage.get((int) index);
    }

    @Override
    protected void doDelete(Object index) {
        storage.remove((int) index);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        storage.sort((o1, o2) -> {
            return o1.getFullName().compareTo(o2.getFullName());
        });
        return storage;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
