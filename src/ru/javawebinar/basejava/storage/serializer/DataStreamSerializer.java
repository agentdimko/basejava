package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();

            writeCollection(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeCollection(dos, resume.getSections().entrySet(), entry -> {
                SectionType type = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(type.name());
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dos, ((ListSection) section).getItems(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dos, ((InstitutionSection) section).getItems(), item -> {
                            if (item.getHomePage().getLink() != null) {
                                dos.writeUTF(item.getHomePage().getLink());
                            } else {
                                dos.writeUTF("");
                            }
                            dos.writeUTF(item.getHomePage().getText());

                            writeCollection(dos, item.getPositions(), position -> {
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(position.getTitle());
                                if (position.getDescription() != null) {
                                    dos.writeUTF(position.getDescription());
                                } else {
                                    dos.writeUTF("");
                                }
                            });
                        });
                        break;
                    default:
                        break;
                }
            });
        }
    }

    private <E> void writeCollection(DataOutputStream dos, Collection<E> collection, ItemWriter<E> itemWriter) throws IOException {
        dos.writeInt(collection.size());
        for (E element : collection) {
            itemWriter.write(element);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            readItems(dis, () -> {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            });

            readItems(dis, () -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                resume.addSection(type, readSection(dis, type));
            });
            return resume;
        }
    }

    private void readItems(DataInputStream dis, ItemHandler itemHandler) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            itemHandler.handle();
        }
    }

    private Section readSection(DataInputStream dis, SectionType type) throws IOException {
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readList(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new InstitutionSection(readList(dis, () -> {
                    String link = dis.readUTF();
                    link = link.isEmpty() ? null : link;
                    String text = dis.readUTF();
                    HyperLink hyperLink = new HyperLink(link, text);
                    return new Institution(hyperLink, readList(dis, () -> {
                        LocalDate startDate = LocalDate.parse(dis.readUTF());
                        LocalDate endDate = LocalDate.parse(dis.readUTF());
                        String title = dis.readUTF();
                        String description = dis.readUTF();
                        description = description.isEmpty() ? null : description;
                        return new Institution.Position(startDate, endDate, title, description);
                    }));
                }));
            default:
                throw new IllegalStateException();
        }
    }

    private <E> List<E> readList(DataInputStream dis, ItemHReader<E> reader) throws IOException {
        int size = dis.readInt();
        List<E> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }
}
