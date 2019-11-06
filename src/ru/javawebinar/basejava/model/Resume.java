package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.exception.StorageException;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
    private final String uuid;
    private String fullName;
    private final Map<ContactType, HyperLinkStorage> contacts;
    private final Map<SectionType, Section> sections;

    //        https://www.baeldung.com/java-enum-map
    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        checkFullName(fullName, uuid);
        this.uuid = uuid;
        this.fullName = fullName.trim();
        contacts = new EnumMap<ContactType, HyperLinkStorage>(ContactType.class);
        sections = new EnumMap<SectionType, Section>(SectionType.class);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        checkFullName(fullName, uuid);
        this.fullName = fullName.trim();
    }

    public String getContact(ContactType key) {
        return contacts.get(key).toString();
    }

    public List<String> getAllContact() {
        List<String> list = new ArrayList<>();
        for (Map.Entry<ContactType, HyperLinkStorage> entry : contacts.entrySet()) {
            list.add(entry.getValue().toString());
        }
        return list;
    }

    public void addContact(ContactType key, String value) {
        contacts.put(key, new HyperLinkStorage(key, value));
    }

    public void deleteContact(ContactType key) {
        contacts.remove(key);
    }

    public void clearContacst() {
        contacts.clear();
    }

    public Section getSection(SectionType key) {
        return sections.get(key);
    }

    public List<Section> getAllSections() {
        List<Section> list = new ArrayList<>();
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    public void addSectionElement(SectionType key, Object value) {
        Section section = sections.get(key);
        if (section != null) {
            section.addElement(value);
        } else {
            section = createSection(key);
            section.addElement(value);
            sections.put(key, section);
        }
    }

    public void updateSectionElement(SectionType key, int index, Object value) {
        sections.get(key).updateElement(index, value);
    }

    public void deleteSectionElement(SectionType key, int index) {
        sections.get(key).removeElement(index);
    }

    public void deleteSection(SectionType key) {
        sections.remove(key);
    }

    public void clearSections() {
        sections.clear();
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

    private static void checkFullName(String fullName, String uuid) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new StorageException("Full name must not be empty", uuid);
        }
    }

    private Section createSection(SectionType type) {
        Section section = null;
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                section = new ListSection(new ArrayList<>(), type.getTitle());
                break;
            case EXPERIENCE:
            case EDUCATION:
                section = new InstitutionSection(new ArrayList<>(), type.getTitle());
                break;
            default:
                throw new StorageException("Unable to save data", uuid);
        }
        return section;
    }
}
