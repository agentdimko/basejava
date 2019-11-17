package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected abstract void doWrite(Resume resume, Path path) throws IOException;

    protected abstract Resume doRead(Path path) throws IOException;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !(Files.isWritable(directory))) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.directory = directory;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return Files.exists(searchKey);
    }

    @Override
    protected void doUpdate(Path searchKey, Resume resume) {
        if (!isExist(searchKey)) {
            new StorageException("File not found", resume.getUuid());
        }
        try {
            doWrite(resume, searchKey);
        } catch (IOException e) {
            throw new StorageException("Unable to write file", resume.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Path searchKey, Resume resume) {
        if (!isExist(searchKey)) {
            try {
                doWrite(resume, searchKey);
            } catch (IOException e) {
                throw new StorageException("Unable to write file", resume.getUuid(), e);
            }
        }
    }

    @Override
    protected Resume doGet(Path searchKey) {
        if (isExist(searchKey)) {
            try {
                return doRead(searchKey);
            } catch (IOException e) {
                throw new StorageException("Unable to read file", searchKey.getFileName().toString());
            }
        }
        throw new StorageException("File not found", searchKey.getFileName().toString());
    }

    @Override
    protected void doDelete(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new StorageException("Unable to delete file", searchKey.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> getList() {
        try {
            return Files.list(directory)
                    .filter(x -> !Files.isDirectory(x))
                    .map(path -> {
                        try {
                            return doRead(path);
                        } catch (IOException e) {
                            throw new StorageException("Unable to read file" + path.getFileName(), null, e);
                        }
                    }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("IO error", null, e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory)
                    .filter(x -> !Files.isDirectory(x))
                    .count();
        } catch (IOException e) {
            throw new StorageException("IO error", null, e);
        }
    }
}
