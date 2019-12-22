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
            if (insertOrUpdateResume(conn, resume, "UPDATE resume SET full_name=? WHERE uuid=?") == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            deleteResumeSection(resume, conn, "Delete FROM contact WHERE resume_uuid=?");
            deleteResumeSection(resume, conn, "Delete FROM section WHERE resume_uuid=?");
            insertContact(resume, conn);
            saveSection(resume, conn);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            insertOrUpdateResume(conn, resume, "INSERT INTO resume ( full_name, uuid) VALUES (?,?)");
            insertContact(resume, conn);
            saveSection(resume, conn);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume = null;
            try (PreparedStatement ps = conn.prepareStatement("SELECT r.uuid, r.full_name, c.type, c.value FROM " +
                    "resume r LEFT JOIN " +
                    "contact c " +
                    "ON r.uuid = c.resume_uuid WHERE r.uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
                do {
                    checkContactValue(rs, resume);
//                    String value = rs.getString("value");
//                    if (rs.getString("value") != null) {
//                        resume.addContact(ContactType.valueOf(rs.getString("type")), value);
//                    }
                } while (rs.next());
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT resume_uuid, type, value FROM section WHERE " +
                    "resume_uuid=?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(resume, rs);
                }
            }
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
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT uuid, full_name FROM resume ORDER BY full_name," +
                    " uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    String fullName = rs.getString("full_name");
                    map.put(uuid, new Resume(uuid, fullName));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT resume_uuid, type, value FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    String type = rs.getString("type");
                    Resume resume = map.get(uuid);
                    checkContactValue(rs, map.get(uuid));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT resume_uuid, type, value FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    map.computeIfPresent(uuid, (k, v) -> {
                        try {
                            addSection(v, rs);
                        } catch (SQLException e) {
                            throw new StorageException(e);
                        }
                        return v;
                    });
                }
            }
            return new ArrayList<>(map.values());
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
        if (resume.getContacts().size() == 0) {
            return;
        }
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

    private void saveSection(Resume resume, Connection conn) throws SQLException {
        if (resume.getSections().size() == 0) {
            return;
        }
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (type, value, resume_uuid) "
                + "VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                String type = e.getKey().name();
                ps.setString(1, type);
                switch (type) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        ps.setString(2, ((TextSection) e.getValue()).getContent());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        ps.setString(2, String.join("\n",
                                ((ListSection) e.getValue()).getItems()));
                        break;
                    default:
                        break;
                }
                ps.setString(3, resume.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void checkContactValue(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addSection(Resume resume, ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        String value = rs.getString("value");
        switch (type) {
            case "OBJECTIVE":
            case "PERSONAL":
                resume.addSection(SectionType.valueOf(type), new TextSection(value));
                break;
            case "ACHIEVEMENT":
            case "QUALIFICATIONS":
                String[] values = value.split("\n");
                resume.addSection(SectionType.valueOf(type),
                        new ListSection(new ArrayList<>(Arrays.asList(values))));
                break;
            default:
                break;
        }
    }

    private void deleteResumeSection(Resume resume, Connection conn, String query) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private int insertOrUpdateResume(Connection conn, Resume resume, String query) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            return ps.executeUpdate();
        }
    }

}
