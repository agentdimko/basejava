package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.exception.StorageException;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;

    private final String uuid;
    private String fullName;
    private final Map<ContactType, String> contacts;
    private final Map<SectionType, Section> sections;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    //  https://www.baeldung.com/java-enum-map
    public Resume(String uuid, String fullName) {
        checkParameters(uuid, fullName);
        this.uuid = uuid;
        this.fullName = fullName.trim();
        contacts = new EnumMap<>(ContactType.class);
        sections = new EnumMap<>(SectionType.class);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContact(ContactType key) {
        return contacts.get(key);
    }

    public void setContact(ContactType key, String text) {
        contacts.put(key, text);
    }

    public Section getSection(SectionType key) {
        return sections.get(key);
    }

    public void setSection(SectionType key, Section value) {
        sections.put(key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    @Override
    public int compareTo(Resume o) {
        int i = fullName.compareTo(o.fullName);
        return i != 0 ? i : uuid.compareTo(o.getUuid());
    }

    private static void checkParameters(String uuid, String fullName) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new StorageException("UUID must not be empty", "");
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new StorageException("FullName must not be empty", uuid);
        }
    }
}
