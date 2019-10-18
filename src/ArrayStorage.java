import java.util.ArrayList;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    int size;
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, 0, size() - 1, null);
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        incrementSize();
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                System.arraycopy(storage, i + 1, storage, i, size - i - 1);
                storage[size - 1] = null;
                decrementSize();
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    void incrementSize() {
        size++;
    }

    void decrementSize() {
        size--;
    }

    int size() {
        return size;
    }
}
