package ru.javawebinar.basejava.storage;

public abstract class AbstractStorage implements Storage {
    protected abstract int getIndex(String uuid);
}
