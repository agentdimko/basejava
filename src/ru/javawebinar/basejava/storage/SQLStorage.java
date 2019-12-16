package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SQLStorage implements Storage {
    private SqlHelper sqlHelper;

    public SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                processResume(resume, ps);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }

            if (resume.getContacts().size() != 0) {
                try (PreparedStatement ps = conn.prepareStatement("UPDATE contact SET type=?, value=? WHERE resume_uuid=?")) {
                    processContact(resume, ps);
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement("Delete FROM contact WHERE resume_uuid=?")) {
                    ps.setString(1, resume.getUuid());
                    ps.execute();
                }
            }
            return null;
        });
    }


    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps =
                         conn.prepareStatement("INSERT INTO resume ( full_name, uuid) VALUES (?,?)")) {
                processResume(resume, ps);
                    }

            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (type, value, " +
                    "resume_uuid) VALUES (?, ?, ?)")) {
                processContact(resume, ps);
                    }

                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT r.uuid, r.full_name, c.type, c.value FROM resume r\n" +
                "LEFT JOIN contact c\n" +
                "ON r.uuid = c.resume_uuid\n" +
                "WHERE r.uuid = ?", (ps) -> {
            ps.setString(1, uuid);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }

            Resume resume = new Resume(uuid, rs.getString("full_name"));

            do {
                String value = rs.getString("value");
                if (value == null) {
                    break;
                }
                ContactType type = ContactType.valueOf(rs.getString("type"));
                resume.addContact(type, value);
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
        return sqlHelper.execute("SELECT r.uuid, r.full_name, c.type, c.value FROM resume r\n" +
                "LEFT JOIN contact c\n" +
                "ON r.uuid = c.resume_uuid\n", (ps -> {
            ResultSet rs = ps.executeQuery();
            Map<String, Resume> map = new HashMap<>();
            Resume resume = null;

            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String fullName = rs.getString("full_name");
                String type = rs.getString("type");
                String value = rs.getString("value");

                if (map.containsKey(uuid)) {
                    checkContact(resume, type, value);
                } else {
                    resume = new Resume(uuid, fullName);
                    checkContact(resume, type, value);
                }
                map.put(uuid, resume);
            }
//          https://www.mkyong.com/java8/java-8-convert-map-to-list/
            return map.values().stream().sorted().collect(Collectors.toList());
        }));
    }


    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) AS count FROM resume", (ps) -> {
            ResultSet rs = ps.executeQuery();
            return (rs.next()) ? rs.getInt("count") : 0;
        });
    }

    private void processContact(Resume resume, PreparedStatement ps) throws SQLException {
        for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
            ps.setString(1, e.getKey().name());
            ps.setString(2, e.getValue());
            ps.setString(3, resume.getUuid());
            ps.addBatch();
        }
        ps.executeBatch();
    }

    private void processResume(Resume resume, PreparedStatement ps) throws SQLException {
        ps.setString(1, resume.getFullName());
        ps.setString(2, resume.getUuid());
        if (ps.executeUpdate() == 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    private void checkContact(Resume resume, String type, String value) {
        if (type != null)
            resume.addContact(ContactType.valueOf(type), value);
    }
}
