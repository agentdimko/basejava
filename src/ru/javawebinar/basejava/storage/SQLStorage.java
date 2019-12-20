package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SQLStorage implements Storage {
    private SqlHelper sqlHelper;

    public SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> {
            try {
//  https://jdbc.postgresql.org/documentation/81/load.html
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new StorageException(e);
            }
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        });
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }

            }
            try (PreparedStatement ps = conn.prepareStatement("Delete FROM contact WHERE resume_uuid=?")) {
                ps.setString(1, resume.getUuid());
                ps.execute();
            }
            if (resume.getContacts().size() != 0) {
                insertContact(resume, conn);
                insertTextSection(resume, conn);
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume ( full_name, uuid) VALUES (?,?)")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                ps.execute();
            }
            insertContact(resume, conn);
            insertTextSection(resume, conn);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT r.uuid, r.full_name, c.type, c.value FROM resume r LEFT JOIN contact c " +
                "ON r.uuid = c.resume_uuid WHERE r.uuid = ?", (ps) -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                checkContactValue(rs, resume);
            } while (rs.next());
            return resume;
        });
    }


    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", (ps) -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> resumeMap = sqlHelper.execute("SELECT uuid, full_name FROM resume ORDER BY full_name, uuid",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    Map<String, Resume> map = new LinkedHashMap<>();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        String fullName = rs.getString("full_name");
                        map.put(uuid, new Resume(uuid, fullName));
                    }
                    return map;
                });

        sqlHelper.execute("SELECT resume_uuid, type, value FROM contact", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("resume_uuid");
                String type = rs.getString("type");
                String value = rs.getString("value");
                resumeMap.computeIfPresent(uuid, (k, v) -> {
                    v.addContact(ContactType.valueOf(type), value);
                    return v;
                });
            }
            return resumeMap;
        });

        return sqlHelper.execute("SELECT resume_uuid, type, value FROM textsection", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("resume_uuid");
                String type = rs.getString("type");
                String value = rs.getString("value");
                resumeMap.computeIfPresent(uuid, (k, v) -> {
                    if (type.equals("OBJECTIVE") || type.equals("PERSONAL")) {
                        v.addSection(SectionType.valueOf(type), new TextSection(value));
                    } else if (type.equals("ACHIEVEMENT") || type.equals("QUALIFICATIONS")) {
                        v.addSection(SectionType.valueOf(type), new ListSection(value.replace("\\n", ", ")));
                    }
                    return v;
                });
            }
            return new ArrayList<>(resumeMap.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) AS count FROM resume", (ps) -> {
            ResultSet rs = ps.executeQuery();
            return (rs.next()) ? rs.getInt("count") : 0;
        });
    }

    private void insertContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (type, value, resume_uuid) "
                + "VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, e.getKey().name());
                ps.setString(2, e.getValue());
                ps.setString(3, resume.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertTextSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO textsection (type, value, resume_uuid) "
                + "VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                String type = e.getKey().name();
                ps.setString(1, type);
                if (type.equals("OBJECTIVE") || type.equals("PERSONAL")) {
                    ps.setString(2, ((TextSection) e.getValue()).getContent());
                } else if (type.equals("ACHIEVEMENT") || type.equals("QUALIFICATIONS")) {
                    ps.setString(2, getContent((ListSection) e.getValue()));
                }
                ps.setString(3, resume.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private String getContent(ListSection section) {
        StringJoiner valueJoiner = new StringJoiner("\\n");
        for (String value : section.getItems()) {
            valueJoiner.add(value);
        }
        return valueJoiner.toString();
    }

    private void checkContactValue(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

}
