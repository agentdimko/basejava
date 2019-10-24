package ru.javawebinar.basejava.storage;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    private final static SortedArrayStorage sortedArrayStorage = new SortedArrayStorage();

    public SortedArrayStorageTest() {
        super(sortedArrayStorage);
    }
}