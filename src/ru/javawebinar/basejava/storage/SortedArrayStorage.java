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
        int index = getIndex(resume.getUuid());
        if(index > 0) {
            System.out.println("Unable to save. Resume with uuid " + resume.getUuid() + " is already exist");
            return;
        }
        index = - (index + 1);
        if (index == size){
            storage[size] = resume;
            size++;
            return;
        }
        System.arraycopy(storage, index, storage, index +1, size - index);
        storage[index] = resume;
        size++;
    }


    @Override
    protected int getIndex(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }
}
