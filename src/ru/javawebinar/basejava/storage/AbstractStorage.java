package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getIndex(String uuid);

    protected abstract boolean isResumeExist(Object index);

    protected abstract void doUpdate(Object index, Resume resume);

    protected abstract void doSave(Object index, Resume resume);

    protected abstract Resume doGet(Object index);

    protected abstract void doDelete(Object index);

    @Override
    public void update(Resume resume) {
        Object index = getIndex(resume.getUuid());
        if (isResumeExist(index)) {
            doUpdate(index, resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        Object index = getIndex(resume.getUuid());
        if (isResumeExist(index)) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            doSave(index, resume);
        }
    }

    @Override
    public Resume get(String uuid) {
        Object index = getIndex(uuid);
        if (isResumeExist(index)) {
            return doGet(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        Object index = getIndex(uuid);
        if (isResumeExist(index)) {
            doDelete(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }
}
