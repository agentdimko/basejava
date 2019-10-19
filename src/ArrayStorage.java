import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int size;
    private Resume[] storage = new Resume[10000];

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int findResumeIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return i;
            }
        }
        System.out.println("Resume with uuid = " + uuid + " not found!");
        return -1;
    }

    public void update(Resume r) {
        int i = findResumeIndex(r.uuid);
        if (i != -1) {
            storage[i] = r;
        }
    }

    public void save(Resume r) {
        if (size == 10000) {
            System.out.println("Storage is full!!");
            return;
        }
        if (findResumeIndex(r.uuid) != -1) {
            System.out.println("Resume wit uuid = " + r.uuid + " is already exist");
            return;
        }
        storage[size] = r;
        size++;
    }

    public Resume get(String uuid) {
        int i = findResumeIndex(uuid);
        return (i != -1) ? storage[i] : null;
    }

    public void delete(String uuid) {
        int i = findResumeIndex(uuid);
        if (i != -1) {
            storage[i] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            return;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
