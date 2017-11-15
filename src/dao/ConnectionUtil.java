package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private String jdbcDriver;
    private String dbURL;
    private String username;
    private String password;

    private static final String filePath = "src/resources/database.properties";

    private void readDBProperties() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] properties;

            while((line = reader.readLine()) != null) {
                properties = line.split("=");

                if (properties[0].equals("jdbc_driver") & properties[1] != null) {
                    jdbcDriver = properties[1];
                } else if (properties[0].equals("DatabaseURL") & properties[1] != null) {
                    dbURL = properties[1];
                } else if(properties[0].equals("username") & properties[1] != null) {
                    username = properties[1];
                } else if(properties[0].equals("password") & properties[1] != null) {
                    password = properties[1];
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + filePath + " not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConnectionUtil(){
        try {
            readDBProperties();
            Class.forName(jdbcDriver);
        } catch(ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
        }
    }

    public Connection getDBConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbURL, username, password);
        } catch (SQLException e) {
            System.out.println("Database connection error");
            e.printStackTrace();
        }
        return connection;
    }

    public void putConnection(Connection connection) {
        try {
            connection.close();
        } catch(SQLException e) {
            System.out.println("Close connection error");
            e.printStackTrace();
        }
    }
}
