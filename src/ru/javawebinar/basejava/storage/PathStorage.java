package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serializer.StreamSerializer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private StreamSerializer streamSerializer;

    protected PathStorage(String dir, StreamSerializer streamSerializer) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(streamSerializer, "Strategy must not be null");

        this.streamSerializer = streamSerializer;

        if (!Files.isDirectory(directory) || !(Files.isWritable(directory))) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        if (!isExist(path)) {
            throw new StorageException("File not found", resume.getUuid());
        }
        try {
            streamSerializer.doWrite(resume, Files.newOutputStream(path));
        } catch (IOException e) {
            throw new StorageException("Unable to write file", resume.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Path path, Resume resume) {
        if (!isExist(path)) {
            try {
                streamSerializer.doWrite(resume, Files.newOutputStream(path));
            } catch (IOException e) {
                throw new StorageException("Unable to write file (file already exist)", resume.getUuid(), e);
            }
        }
    }

    @Override
    protected Resume doGet(Path path) {
        if (isExist(path)) {
            try {
                return streamSerializer.doRead(new BufferedInputStream(Files.newInputStream(path)));
            } catch (IOException e) {
                throw new StorageException("Unable to read file", getFileName(path));
            }
        }
        throw new StorageException("File not found", getFileName(path));
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Unable to delete file", getFileName(path), e);
        }
    }

    @Override
    protected List<Resume> getList() {
        try {
            return Files.list(directory)
                    .filter(x -> !Files.isDirectory(x))
                    .map(this::doGet)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("IO error", null, e);
        }
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory)
                    .filter(x -> !Files.isDirectory(x))
                    .count();
        } catch (IOException e) {
            throw new StorageException("Directory read  error", e);
        }
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", e);
        }
    }
}
