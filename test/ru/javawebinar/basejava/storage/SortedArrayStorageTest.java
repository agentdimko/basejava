package ru.javawebinar.basejava.storage;

import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.assertEquals;

public class SortedArrayStorageTest extends AbstractArrayStorageTest{
    private final static SortedArrayStorage sortedArrayStorage = new SortedArrayStorage();

    public SortedArrayStorageTest() {
        super(sortedArrayStorage);
    }

    @Test
    public void getIndexTest() {
        assertEquals(-1, sortedArrayStorage.getIndex("dummy"));
        assertEquals(1, sortedArrayStorage.getIndex("uuid2"));
    }

    @Test
    public void insertElementTest() {
        Resume resume = new Resume("uuid122");
        sortedArrayStorage.insertElement(new Resume("uuid122"), -2);
        assertEquals(resume, sortedArrayStorage.storage[1]);
    }

    @Test
    public void fillDeletedElementTest() {
//        тестировать правильность сортировки не надо
    }
}