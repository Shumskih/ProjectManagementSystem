package dao.jdbc;

import dao.ConnectionUtil;
import dao.GenericDAO;
import dao.SqlQueries;
import model.Project;

import java.sql.*;

public class JdbcProjectDAOImpl implements GenericDAO<Project,Long> {
    private ConnectionUtil connectionUtil = new ConnectionUtil();

    @Override
    public void save(Project project) {
        Integer id = project.getId();
        String name = project.getName();
        String version = project.getVersion();
        Integer cost = project.getCost();

        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.INSERT_NEW_PROJECT)){

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, version);
            preparedStatement.setInt(4, cost);

            preparedStatement.executeUpdate();

            System.out.println("Project has created!");
        } catch (SQLException e) {
            System.out.println("Database error: ");
            System.out.println("----------------");
            e.printStackTrace();
        }
    }

    @Override
    public Project getById(int id) {
        Project project;
        Integer projectId = null;
        String name = null;
        String version = null;
        Integer cost = null;

        try (Connection connection = connectionUtil.getDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.SHOW_PROJECT)){

            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    projectId = resultSet.getInt("id");

                    if (projectId == id) {
                        name = resultSet.getString("name");
                        version = resultSet.getString("version");
                        cost = resultSet.getInt("cost");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: ");
            System.out.println("----------------");
            e.printStackTrace();
        }
        project = new Project(projectId, name, version, cost);
        System.out.println(project.toString());
        return project;
    }

    @Override
    public void getAll() {
        try (Connection connection = connectionUtil.getDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.SHOW_ALL_PROJECTS)){

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
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
            }
        } catch (SQLException e) {
            System.out.println("Database error: ");
            System.out.println("----------------");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Project project) {
        Integer id = project.getId();
        String name = project.getName();
        String version = project.getVersion();
        Integer cost = project.getCost();

        try (Connection connection = connectionUtil.getDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.UPDATE_PROJECT)){

            preparedStatement.setInt(4, id);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, version);
            preparedStatement.setInt(3, cost);

            preparedStatement.executeUpdate();

            System.out.println("Project has changed.");
        } catch (SQLException e) {
            System.out.println("Database error: ");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = connectionUtil.getDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.DELETE_PROJECT)){

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            System.out.println("Project with id = " + id + " has deleted");
        } catch (SQLException e) {
            System.out.println("Database error");
            System.out.println("______________");
            e.printStackTrace();
        }
    }
}
