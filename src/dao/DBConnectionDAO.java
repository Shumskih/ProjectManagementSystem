package dao;

import java.sql.Connection;

public interface DBConnectionDAO {
    Connection getDBConnection();

    Connection getDBConnection(String dbUrl, String dbUsername, String dbPassword);
}
