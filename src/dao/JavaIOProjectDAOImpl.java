package dao;

import model.Project;

import java.sql.*;
import java.util.Set;

public class JavaIOProjectDAOImpl implements ProjectDAO {

    public static final String JDBC_DRIVER = "org.postgresql.Driver";
    public static final String URL_DATABASE = "jdbc:postgresql://localhost:5432/learndb";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "Unow6457773";

    public static final String INSERT_NEW_PROJECT = "INSERT INTO projects VALUES(?,?,?,?)";
    public static final String SHOW_PROJECT = "SELECT * FROM projects WHERE id=?";
    private static final String SHOW_ALL_PROJECTS = "SELECT * FROM projects";
    private static final String UPDATE_PROJECT = "UPDATE projects SET name=?, version=?, cost=? WHERE id=?";
    public static final String DELETE_PROJECT = "DELETE FROM projects WHERE id=?";

    @Override
    public void create(Project project) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Integer id = project.getId();
        String name = project.getName();
        String version = project.getVersion();
        Integer cost = project.getCost();

        try {

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL_DATABASE, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(INSERT_NEW_PROJECT);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, version);
            preparedStatement.setInt(4, cost);

            preparedStatement.executeUpdate();

            System.out.println("Project has created!");

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found: ");
            System.out.println("-----------------------");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database error: ");
            System.out.println("----------------");
            e.printStackTrace();
        } finally {

            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println("Can't close connection: ");
                System.out.println("------------------------");
                e.printStackTrace();
            }

        }

    }

    @Override
    public void read(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL_DATABASE, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(SHOW_PROJECT);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int projectId = resultSet.getInt("id");

                if (projectId == id) {
                    String name = resultSet.getString("name");
                    String version = resultSet.getString("version");
                    Integer cost = resultSet.getInt("cost");

                    System.out.println();
                    System.out.println("====================");
                    System.out.println("ID: " + projectId + "\n" +
                            "Name: " + name + "\n" +
                            "Version: " + version + "\n" +
                            "Cost: " + cost);
                    System.out.println("====================");
                    System.out.println();
                }
            }

            resultSet.close();

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found: ");
            System.out.println("-----------------------");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database error: ");
            System.out.println("----------------");
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println("Can't close connection: ");
                System.out.println("------------------------");
                e.printStackTrace();
            }
        }

    }

    @Override
    public void readAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL_DATABASE, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(SHOW_ALL_PROJECTS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String version = resultSet.getString("version");
                Integer cost = resultSet.getInt("cost");

                System.out.println("================");
                System.out.println("ID: " + id + "\n" +
                                    "Name: " + name + "\n" +
                                    "Version: " + version + "\n" +
                                    "Cost: " + cost);
            }

            resultSet.close();

        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: ");
            System.out.println("------------------");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database error: ");
            System.out.println("----------------");
            e.printStackTrace();
        } finally {
            try {

                preparedStatement.close();
                connection.close();

            } catch (SQLException e) {
                System.out.println("Can't close connection: ");
                System.out.println("------------------------");
                e.printStackTrace();
            }
        }

    }

    @Override
    public void update(Project project) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Integer id = project.getId();
        String name = project.getName();
        String version = project.getVersion();
        Integer cost = project.getCost();

        try {

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL_DATABASE, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(UPDATE_PROJECT);
            preparedStatement.setInt(4, id);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, version);
            preparedStatement.setInt(3, cost);

            preparedStatement.executeUpdate();

            System.out.println("Project has changed.");

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found: ");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database error: ");
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL_DATABASE, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(DELETE_PROJECT);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            System.out.println("Project with id = " + id + " has deleted");

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            System.out.println("---------------------");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database error");
            System.out.println("______________");
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
