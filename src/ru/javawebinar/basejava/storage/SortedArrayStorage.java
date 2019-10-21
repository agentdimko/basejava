package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("Resume wit uuid " + resume.getUuid() + " not found");
        }
    }

    @Override
    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Unabel to save. Storage is full!!");
            return;
        }
        if (getIndex(resume.getUuid()) >= 0) {
            System.out.println("Unable to save. Resume with uuid " + resume.getUuid() + " is already exist");
            return;
        }
        storage[size] = resume;
        size++;
        sortStorage();
    }


    @Override
    protected int getIndex(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }

    private void sortStorage() {
        for (int i = 0; i < size; i++) {
            int j = findLowestIndex(i);
            swapElements(i, j);
        }
    }

    private void swapElements(int i, int j) {
        Resume tmp = storage[i];
        storage[i] = storage[j];
        storage[j] = tmp;
    }

    private int findLowestIndex(int start) {
        int lowIndex = start;
        for (int i = 0; i < size; i++) {
            if ((storage[i].compareTo(storage[lowIndex])) == -1) {
                lowIndex = i;
            }
        }
        return lowIndex;
    }
}
