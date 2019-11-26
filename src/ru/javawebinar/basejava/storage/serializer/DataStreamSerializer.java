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
            for (SectionType type : SectionType.values()) {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        writeTextSection(type, dos, sections);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeListSection(type, dos, sections);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeInstitutionSection(type, dos, sections);
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
                String type = dis.readUTF();
                switch (type) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        resume.addSection(SectionType.valueOf(type), new TextSection(dis.readUTF()));
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        ListSection listSection = readListSection(dis);
                        resume.addSection(SectionType.valueOf(type), listSection);
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        InstitutionSection institutionSection = readInstitutionSection(dis);
                        resume.addSection(SectionType.valueOf(type), institutionSection);
                        break;
                    default:
                        break;
                }
            }
            return resume;
        }
    }

    private void writeTextSection(SectionType type, DataOutputStream dos, Map<SectionType, Section> sections) throws IOException {
        if (sections.get(type) == null) {
            return;
        }
        dos.writeUTF(type.name());
        TextSection textSection = (TextSection) sections.get(type);
        dos.writeUTF(textSection.getContent());
    }

    private TextSection readTextSection(SectionType type, DataInputStream dis, Resume resume) throws IOException {
        TextSection textSection = null;
        String sectionName = dis.readUTF();
        if (sectionName.equals("OBJECTIVE") || sectionName.equals("PERSONAL")) {
            textSection = new TextSection(dis.readUTF());
        }
        return textSection;
    }

    private void writeListSection(SectionType type, DataOutputStream dos, Map<SectionType, Section> sections) throws IOException {
        if (sections.get(type) == null) {
            return;
        }

        dos.writeUTF(type.name());
        ListSection listSection = (ListSection) sections.get(type);
        int size = listSection.getItems().size();
        dos.writeInt(size);
        for (String s : listSection.getItems()) {
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

    private void writeInstitutionSection(SectionType type, DataOutputStream dos, Map<SectionType, Section> sections)
            throws IOException {
        if (sections.get(type) == null) {
            return;
        }
        dos.writeUTF(type.name());
        InstitutionSection institutionSection = (InstitutionSection) sections.get(type);
        int sectionSize = institutionSection.getItems().size();
        dos.writeInt(sectionSize);
        for (Institution institution : institutionSection.getItems()) {
            if (institution.getHomePage().getLink() != null) {
                dos.writeUTF(institution.getHomePage().getLink());
            } else {
                dos.writeUTF("null");
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
                    dos.writeUTF("null");
                }
            }
        }
    }

    private InstitutionSection readInstitutionSection(DataInputStream dis) throws IOException {
        int sectionSize = dis.readInt();
        List<Institution> institutions = new ArrayList<>();
        for (int i = 0; i < sectionSize; i++) {
            String link = dis.readUTF();
            link = link.equals("null") ? null : link;
            String text = dis.readUTF();
            HyperLink hyperLink = new HyperLink(link, text);
            int positionSize = dis.readInt();
            List<Institution.Position> positions = new ArrayList<>();
            for (int j = 0; j < positionSize; j++) {
                LocalDate startDate = LocalDate.parse(dis.readUTF());
                LocalDate endDate = LocalDate.parse(dis.readUTF());
                String title = dis.readUTF();
                String description = dis.readUTF();
                description = description.equals("null") ? null : description;
                positions.add(new Institution.Position(startDate, endDate, title, description));
            }
            institutions.add(new Institution(hyperLink, positions));
        }
        return new InstitutionSection(institutions);
    }
}
