package ru.javawebinar.basejava.storage;

public class ArrayStorageTest extends AbstractArrayStorageTest{
    private final static ArrayStorage arrayStorage = new ArrayStorage();

    public ArrayStorageTest() {
        super(arrayStorage);
    }
}