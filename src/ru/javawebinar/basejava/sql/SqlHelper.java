package ru.javawebinar.basejava.sql;

import org.postgresql.util.PSQLException;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

//    public <T> T executeQuery(String sqlQuery, QueryExecutor<T> executor) {
//        try (Connection conn = connectionFactory.getConnection();
//        PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
//           return executor.execute(ps);
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//    }

    public <T> T executeQuery(String sqlQuery, QueryExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            if (e instanceof PSQLException) {
                if (e.getSQLState().equals("23505")) {
                    throw new ExistStorageException(null);
                }
            }
            throw new StorageException(e);
        }
    }
}
