package ru.javawebinar.basejava.storage;

import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.assertEquals;

public class ArrayStorageTest extends AbstractArrayStorageTest{
    private final static ArrayStorage arrayStorage = new ArrayStorage();

    public ArrayStorageTest() {
        super(arrayStorage);
    }

    @Test
    public void getIndexTest() {
        assertEquals(-1, arrayStorage.getIndex("dummy"));
        assertEquals(0, arrayStorage.getIndex("uuid1"));
    }

    @Test
    public void insertElementTest() {
        Resume resume = new Resume("dummy");
        arrayStorage.insertElement(new Resume("dummy"), 0);
        arrayStorage.size++;
        assertEquals(resume, arrayStorage.get("dummy"));
    }

    @Test
    public void fillDeletedElementTest() {
        Resume resume = arrayStorage.storage[arrayStorage.size - 1];
        arrayStorage.delete("uuid1");
        assertEquals(resume, arrayStorage.storage[0]);
    }
}