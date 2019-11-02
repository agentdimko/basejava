package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
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
    protected boolean isExist(Integer index) {
        return index != -1;
    }

    @Override
    protected void doUpdate(Integer index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void doSave(Integer index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume doGet(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void doDelete(Integer index) {
        storage.remove(index.intValue());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> getList() {
        return storage;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
