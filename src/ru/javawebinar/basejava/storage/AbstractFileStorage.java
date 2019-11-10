package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file);

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "Directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory ");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        if (file.exists()) {
            try {
                doWrite(resume, file);
            } catch (IOException e) {
                throw new StorageException("Unable to update ", file.getName(), e);
            }
        }
        throw new StorageException("File not found ", file.getName());
    }

    @Override
    protected void doSave(File file, Resume resume) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        if (file.exists()) {
            return doRead(file);
        }
        throw new StorageException("File not found", file.getName());
    }

    @Override
    protected void doDelete(File file) {
        if (isExist(file)) {
            try {
                file.delete();
            } catch (SecurityException e) {
                throw new StorageException("Unable to delete ", file.getName(), e);
            }
        }
    }

    @Override
    protected List<Resume> getList() {
        File[] listOfFiles = directory.listFiles();
        ArrayList<Resume> resumes = new ArrayList<>();
        if (listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                resumes.add(doRead(listOfFiles[i]));
            }
            return resumes;
        }
        throw new StorageException("File storage does not exsist", "");
    }

    @Override
    public void clear() {
        File[] listOfFiles = directory.listFiles();
        if (listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                doDelete(listOfFiles[i]);
            }
        }
        throw new StorageException("File storage does not exsist", "");
    }

    @Override
    public int size() {
        String[] listOfFileNames = directory.list();
        if (listOfFileNames != null) {
            return directory.list().length;
        }
        throw new StorageException("File storage does not exsist", "");
    }
}
