package dao;

import model.Project;

import java.sql.*;

public class JavaIOProjectDAOImpl implements ProjectDAO {
    private DBConnectionDAOImpl dbConnectionDAO = new DBConnectionDAOImpl();

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
            connection = dbConnectionDAO.getDBConnection();
            preparedStatement = connection.prepareStatement(INSERT_NEW_PROJECT);
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
        } finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
                if(connection != null) {
                    dbConnectionDAO.putConnection(connection);
                }
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
        ResultSet resultSet = null;

        try {
            connection = dbConnectionDAO.getDBConnection();
            preparedStatement = connection.prepareStatement(SHOW_PROJECT);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

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
        } catch (SQLException e) {
            System.out.println("Database error: ");
            System.out.println("----------------");
            e.printStackTrace();
        } finally {
            try {
                if(resultSet != null) {
                    resultSet.close();
                }
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
                if(connection != null) {
                    dbConnectionDAO.putConnection(connection);
                }
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
        ResultSet resultSet = null;

        try {
            connection = dbConnectionDAO.getDBConnection();
            preparedStatement = connection.prepareStatement(SHOW_ALL_PROJECTS);
            resultSet = preparedStatement.executeQuery();

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
        } catch (SQLException e) {
            System.out.println("Database error: ");
            System.out.println("----------------");
            e.printStackTrace();
        } finally {
            try {
                if(resultSet != null) {
                    resultSet.close();
                }
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
                if(connection != null) {
                    dbConnectionDAO.putConnection(connection);
                }

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
            connection = dbConnectionDAO.getDBConnection();
            preparedStatement = connection.prepareStatement(UPDATE_PROJECT);
            preparedStatement.setInt(4, id);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, version);
            preparedStatement.setInt(3, cost);

            preparedStatement.executeUpdate();

            System.out.println("Project has changed.");
        } catch (SQLException e) {
            System.out.println("Database error: ");
            e.printStackTrace();
        } finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
                if(connection != null) {
                    dbConnectionDAO.putConnection(connection);
                }
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
            connection = dbConnectionDAO.getDBConnection();
            preparedStatement = connection.prepareStatement(DELETE_PROJECT);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            System.out.println("Project with id = " + id + " has deleted");
        } catch (SQLException e) {
            System.out.println("Database error");
            System.out.println("______________");
            e.printStackTrace();
        } finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
                if(connection != null) {
                    dbConnectionDAO.putConnection(connection);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
