package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractStorageTest {
    protected static File STORAGE_DIR = Config.getInstance().getStorageDir();

    //https://www.baeldung.com/java-uuid
//    private static final String UUID_1 = UUID.randomUUID().toString();
//    private static final String UUID_2 = UUID.randomUUID().toString();
//    private static final String UUID_3 = UUID.randomUUID().toString();
//    private static final String DUMMY = UUID.randomUUID().toString();

    private static final String UUID_1 = "9266be7d-f269-4094-a180-205259146ceb";
    private static final String UUID_2 = "bdc6be7d-f269-4094-a180-205259146ceb";
    private static final String UUID_3 = "330dea91-8c3c-4b2c-a3da-35780e988f89";
    private static final String DUMMY = "421dea91-8c3c-4b2c-a3da-35780e988f89";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume DUMMY_RESUME;

    protected Storage storage;

    static {
        RESUME_1 = new Resume(UUID_1, "Name1");
        RESUME_2 = new Resume(UUID_2, "Name2");
        RESUME_3 = new Resume(UUID_3, "Name3");
        DUMMY_RESUME = new Resume(DUMMY, "Name4");

        RESUME_1.addContact(ContactType.EMAIL, "mail1@ya.ru");
        RESUME_1.addContact(ContactType.PHONE, "11111");
        RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        RESUME_1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListSection("Achivment11", "Achivment12", "Achivment13"));
        RESUME_1.addSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
//        RESUME_1.addSection(SectionType.EXPERIENCE,
//                new InstitutionSection(
//                        new Institution("Institution11", "http://Institution11.ru",
//                                new Institution.Position(2005, Month.JANUARY, "position1", "content1"),
//                                new Institution.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
//        RESUME_1.addSection(SectionType.EDUCATION,
//                new InstitutionSection(
//                        new Institution("Institute", null,
//                                new Institution.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
//                                new Institution.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
//                        new Institution("Institution12", "http://Institution12.ru")));
        RESUME_3.addContact(ContactType.SKYPE, "skype2");
        RESUME_3.addContact(ContactType.PHONE, "22222");
//        RESUME_2.addSection(SectionType.EXPERIENCE,
//                new InstitutionSection(
//                        new Institution("Institution2", "http://Institution2.ru",
//                                new Institution.Position(2015, Month.JANUARY, "position1", "content1"))));
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void updateExist() throws Exception {
        Resume newResume = new Resume(UUID_1, "New Name");
        storage.update(newResume);
        assertTrue(newResume.equals(storage.get(UUID_1)));


        newResume.addContact(ContactType.SKYPE, "myskype");
        newResume.addContact(ContactType.EMAIL, "mymail@mail.ru");
        storage.update(newResume);
        assertTrue(newResume.equals(storage.get(UUID_1)));

        newResume = new Resume(UUID_1, "New Name");
        newResume.addContact(ContactType.PHONE, "12345678");
        storage.update(newResume);
        assertTrue(newResume.equals(storage.get(UUID_1)));

        newResume = new Resume(UUID_1, "New Name");
        storage.update(newResume);
        assertTrue(newResume.equals(storage.get(UUID_1)));
        System.out.println("step4");

    }

//    @Test
//    public void updateExist() throws Exception {
//        Resume newResume = new Resume(UUID_1, "New Name");
//        storage.update(newResume);
//        assertTrue(newResume.equals(storage.get(UUID_1)));
//    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(DUMMY_RESUME);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void saveNotExist() {
        storage.save(DUMMY_RESUME);
        assertGet(DUMMY_RESUME);
        assertSize(4);
    }

    @Test
    public void getExist() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteExist() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(DUMMY);
    }

    @Test
    public void getAllSortedTest() throws Exception {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(list, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
    }

    @Test
    public void size() {
        assertSize(3);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}