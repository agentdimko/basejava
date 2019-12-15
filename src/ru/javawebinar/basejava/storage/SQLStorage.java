package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLStorage implements Storage {
    private SqlHelper sqlHelper;

    public SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    //готово
    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.execute("UPDATE resume SET full_name=? WHERE uuid=?", (ps) -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    //готово
    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES " +
                            "(?, ?,?)")) {
                        for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                            ps.setString(1, resume.getUuid());
                            ps.setString(2, e.getKey().name());

                            ps.setString(3, e.getValue());
                            ps.addBatch();
                        }
                        ps.executeBatch();
                    }
                    return null;
                }
        );
    }

    //Готово
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
                ContactType type = ContactType.valueOf(rs.getString("type"));
                resume.addContact(type, value);
            } while (rs.next());
            return resume;
        });
    }


    //Готово
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

        return sqlHelper.execute("SELECT * FROM resume ORDER BY full_name, uuid", (ps) -> {
            List<Resume> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String fullName = rs.getString("full_name");
                list.add(new Resume(uuid, fullName));
            }
            return list;
        });
    }


    //Готово
    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) AS count FROM resume", (ps) -> {
            ResultSet rs = ps.executeQuery();
            return (rs.next()) ? rs.getInt("count") : 0;
        });
    }
}
