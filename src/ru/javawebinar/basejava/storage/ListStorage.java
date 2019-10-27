package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }


    @Override
    public void save(Resume resume) {
        saveElement(resume);
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

    protected void updateElement(int index, Resume resume) {
        storage.set(index, resume);
    }

    protected Resume getElement(int index) {
        return storage.get(index);
    }

    protected void insertElement(Resume resume, int i) {
        storage.add(resume);
    }

    protected void deleteElement(int index){
        storage.remove(index);
    }
}
