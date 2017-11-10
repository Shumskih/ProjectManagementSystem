package dao;

import java.sql.*;

public class ProjectsDeveloperDAO {
    private Connection connection = null;
    private PreparedStatement psProjectsDevelopers = null;

    public void insert(int projectId, int developerId) {
        try {
            Class.forName(DBConnectionDAO.JDBC_DRIVER);
            connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
            psProjectsDevelopers = connection.prepareStatement(DBConnectionDAO.INSERT_NEW_PROJECTS_DEVELOPERS);

            psProjectsDevelopers.setInt(1, projectId);
            psProjectsDevelopers.setInt(2, developerId);

            psProjectsDevelopers.executeUpdate();
            System.out.println("Project has added");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                psProjectsDevelopers.close();
                connection.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteByProject(int projectId) {
        try {
            Class.forName(DBConnectionDAO.JDBC_DRIVER);
            connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
            psProjectsDevelopers = connection.prepareStatement(DBConnectionDAO.DELETE_PROJECT_FROM_PROJECTS_DEVELOPERS);

            psProjectsDevelopers.setInt(1, projectId);

            psProjectsDevelopers.executeUpdate();
            System.out.println("Project has deleted");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
