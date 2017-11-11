package dao;

import java.sql.Connection;

public interface DBConnectionDAO {
    Connection getDBConnection();

    void putConnection(Connection connection);
}
