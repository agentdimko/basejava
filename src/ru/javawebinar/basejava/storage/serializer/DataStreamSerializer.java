package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = resume.getSections();

            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                SectionType type = entry.getKey();
                dos.writeUTF(entry.getKey().name());
                Section section = sections.get(type);
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        writeTextSection(((TextSection) section).getContent(), dos);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeListSection(((ListSection) section).getItems(), dos);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeInstitutionSection(((InstitutionSection) section).getItems(), dos);
                        break;
                    default:
                        break;
                }
            }
        }
    }


    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            while (dis.available() > 0) {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(type, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(type, readListSection(dis));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.addSection(type, readInstitutionSection(dis));
                        break;
                    default:
                        break;
                }
            }
            return resume;
        }
    }

    private void writeTextSection(String content, DataOutputStream dos) throws IOException {
        dos.writeUTF(content);
    }

    private void writeListSection(List<String> items, DataOutputStream dos) throws IOException {
        dos.writeInt(items.size());
        for (String s : items) {
            dos.writeUTF(s);
        }
    }

    private ListSection readListSection(DataInputStream dis) throws IOException {
        int sectionSize = dis.readInt();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < sectionSize; i++) {
            list.add(dis.readUTF());
        }
        return new ListSection(list);
    }

    private void writeInstitutionSection(List<Institution> items, DataOutputStream dos)
            throws IOException {
        dos.writeInt(items.size());
        for (Institution institution : items) {
            if (institution.getHomePage().getLink() != null) {
                dos.writeUTF(institution.getHomePage().getLink());
            } else {
                dos.writeUTF("");
            }
            dos.writeUTF(institution.getHomePage().getText());
            int positionSize = institution.getPositions().size();
            dos.writeInt(positionSize);
            for (Institution.Position position : institution.getPositions()) {
                dos.writeUTF(position.getStartDate().toString());
                dos.writeUTF(position.getEndDate().toString());
                dos.writeUTF(position.getTitle());
                if (position.getDescription() != null) {
                    dos.writeUTF(position.getDescription());
                } else {
                    dos.writeUTF("");
                }
            }
        }
    }

    private InstitutionSection readInstitutionSection(DataInputStream dis) throws IOException {
        int sectionSize = dis.readInt();
        List<Institution> institutions = new ArrayList<>();
        for (int i = 0; i < sectionSize; i++) {
            String link = dis.readUTF();
            link = link.isEmpty() ? null : link;
            String text = dis.readUTF();
            HyperLink hyperLink = new HyperLink(link, text);
            int positionSize = dis.readInt();
            List<Institution.Position> positions = new ArrayList<>();
            for (int j = 0; j < positionSize; j++) {
                LocalDate startDate = LocalDate.parse(dis.readUTF());
                LocalDate endDate = LocalDate.parse(dis.readUTF());
                String title = dis.readUTF();
                String description = dis.readUTF();
                description = description.isEmpty() ? null : description;
                positions.add(new Institution.Position(startDate, endDate, title, description));
            }
            institutions.add(new Institution(hyperLink, positions));
        }
        return new InstitutionSection(institutions);
    }
}
