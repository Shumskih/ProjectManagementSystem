package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomersProjectsDAO {
    private Connection connection = null;
    private PreparedStatement PSCustomersProjects = null;

    public void insert(int customerId, int projectId) {
        try {
            Class.forName(DBConnectionDAO.JDBC_DRIVER);
            connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
            PSCustomersProjects = connection.prepareStatement(DBConnectionDAO.INSERT_CUSTOMERS_PROJECTS);

            PSCustomersProjects.setInt(1, customerId);
            PSCustomersProjects.setInt(2, projectId);

            PSCustomersProjects.executeUpdate();

            System.out.println("Project has added");
            System.out.println();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                PSCustomersProjects.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println("Ошибка при закрытие PreparedStatement и Connection");
                e.printStackTrace();
            }
        }
    }

    public void deleteByProject(int projectId) {
        try {
            Class.forName(DBConnectionDAO.JDBC_DRIVER);
            connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
            PSCustomersProjects = connection.prepareStatement(DBConnectionDAO.DELETE_CUSTOMERS_PROJECTS);

            PSCustomersProjects.setInt(1, projectId);

            PSCustomersProjects.executeUpdate();

            System.out.println("Project has deleted");
            System.out.println();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                PSCustomersProjects.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println("Ошибка при закрытие PreparedStatement и Connection");
                e.printStackTrace();
            }
        }
    }

    public void deleteByCustomer(int customerId) {
        try {
            Class.forName(DBConnectionDAO.JDBC_DRIVER);
            connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
            PSCustomersProjects = connection.prepareStatement(DBConnectionDAO.DELETE_CUSTOMER_FROM_CUSTOMERS_PROJECTS);

            PSCustomersProjects.setInt(1, customerId);

            PSCustomersProjects.executeUpdate();
            System.out.println("All related projects are deleted");
            System.out.println();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                PSCustomersProjects.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println("Ошибка при закрытие PreparedStatement и Connection");
                e.printStackTrace();
            }
        }
    }
}
