package dao;

import java.sql.*;

public class ProjectsDeveloperDAO {
    private DBConnectionDAOImpl dbConnectionDAO = new DBConnectionDAOImpl();

    private Connection connection = null;
    private PreparedStatement psProjectsDevelopers = null;

    public void insert(int projectId, int developerId) {
        try {
            connection = dbConnectionDAO.getDBConnection();
            psProjectsDevelopers = connection.prepareStatement(DBConnectionDAOImpl.INSERT_NEW_PROJECTS_DEVELOPERS);

            psProjectsDevelopers.setInt(1, projectId);
            psProjectsDevelopers.setInt(2, developerId);

            psProjectsDevelopers.executeUpdate();
            System.out.println("Project has added");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(psProjectsDevelopers != null) {
                    psProjectsDevelopers.close();
                }
                if(connection != null) {
                    dbConnectionDAO.putConnection(connection);
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteByProject(int projectId) {
        try {
            connection = dbConnectionDAO.getDBConnection();
            psProjectsDevelopers = connection.prepareStatement(DBConnectionDAOImpl.DELETE_PROJECT_FROM_PROJECTS_DEVELOPERS);

            psProjectsDevelopers.setInt(1, projectId);

            psProjectsDevelopers.executeUpdate();
            System.out.println("Project has deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(psProjectsDevelopers != null) {
                    psProjectsDevelopers.close();
                }
                if(connection != null) {
                    dbConnectionDAO.putConnection(connection);
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
