package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage{
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

//    @Override
//    public void delete(String uuid) {
//        storage.remove(uuid);
//    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[storage.size()];
        int count = 0;
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            resumes[count++] = entry.getValue();
        }
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int getIndex(String uuid) {
        if(storage.get(uuid) != null){
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
        if(index < 0){
            storage.put(resume.getUuid(), resume);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    protected Resume getElement(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void insertElement(int index, Resume resume) {

    }

    @Override
    protected void deleteElement(int index, String uuid) {
        storage.remove(uuid);
    }
}
