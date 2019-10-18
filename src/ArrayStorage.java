import java.util.ArrayList;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
    }

    void save(Resume r) {
        for (int i = 0; i < 10000; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                return;
            }
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage[i].equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < 10000; i++) {
            if ((storage[i].uuid).equals(uuid)) {
                if (i == storage.length - 1) {
                    storage[i] = null;
                    return;
                } else if (i == 0) {
                    System.arraycopy(storage, 1, storage, 0, storage.length - 1);
                    storage[storage.length - 1] = null;
                    return;
                } else {
                    System.arraycopy(storage, i + 1, storage, i, storage.length - i - 2);
                    storage[storage.length - 1] = null;
                    return;
                }
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int count = 0;
        for (Resume resume : storage) {
            if (resume != null) {
                count++;
            } else {
                break;
            }
        }
        return Arrays.copyOf(storage, count);
    }

    int size() {
        return getAll().length;
    }
}
