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

    protected abstract Resume doRead(File file) throws IOException;

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
                throw new StorageException("Unable to update ", resume.getUuid(), e);
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
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        if (file.exists()) {
            try {
                return doRead(file);
            } catch (IOException e) {
                throw new StorageException("File read error", file.getName(), e);
            }
        }
        throw new StorageException("File not found", file.getName());
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected List<Resume> getList() {
        File[] listOfFiles = directory.listFiles();
        if (listOfFiles != null) {
            List<Resume> resumes = new ArrayList<>();
            for (File file : listOfFiles) {
                try {
                    resumes.add(doRead(file));
                } catch (IOException e) {
                    throw new StorageException("File read error", file.getName(), e);
                }
            }
            return resumes;
        }
        throw new StorageException("Directory read error", null);
    }

    @Override
    public void clear() {
        File[] listOfFiles = directory.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                doDelete(file);
            }
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list != null) {
            return list.length;
        }
        throw new StorageException("Directory read error", null);
    }
}
