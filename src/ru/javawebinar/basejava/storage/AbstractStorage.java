package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            updateElement(index, resume);
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if(index < 0){
            throw new NotExistStorageException(uuid);
        } else {
            return getElement(index);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if(index < 0){
            throw new NotExistStorageException(uuid);
        } else {
            deleteElement(index);
        }
    }

    protected void saveElement(Resume resume){
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            insertElement(resume, index);

        } else {
            throw new ExistStorageException(resume.getUuid());
        }    }

    protected abstract int getIndex(String uuid);
    protected abstract void updateElement(int index, Resume resume);
    protected abstract Resume getElement(int index);
    protected abstract void insertElement(Resume resume, int index);
    protected abstract void deleteElement(int index);
}
