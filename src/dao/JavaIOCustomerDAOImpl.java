package dao;

import model.Customer;
import model.Project;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class JavaIOCustomerDAOImpl implements CustomerDAO {

    public static final String JDBC_DRIVER = "org.postgresql.Driver";
    public static final String URL_DATABASE = "jdbc:postgresql://localhost:5432/learndb";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "Unow6457773";

    private final String INSERT_NEW_CUSTOMER = "INSERT INTO customers VALUES(?,?)";
    public static final String SHOW_CUSTOMER = "SELECT * FROM customers WHERE id=?";
    private final String SHOW_ALL_CUSTOMERS = "SELECT * FROM customers";
    private final String UPDATE_CUSTOMER = "UPDATE customers SET name=? WHERE id=?";
    private final String DELETE_CUSTOMER = "DELETE FROM customers WHERE id=?";

    private Project project;
    private Set<Project> projects = new LinkedHashSet<>();

    private Integer customerId;
    private String customerName;

    @Override
    public void create(Customer customer) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Integer id = customer.getId();
        String name = customer.getName();

        try {

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL_DATABASE, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(INSERT_NEW_CUSTOMER);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);

            preparedStatement.executeUpdate();

            System.out.println("Customer has created!");

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
        PreparedStatement PSReadCustomersProjects;
        PreparedStatement PSReadProjects;

        try {

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL_DATABASE, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(SHOW_CUSTOMER);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                customerId = resultSet.getInt("id");

                if (customerId == id) {
                    customerName = resultSet.getString("name");

                    PSReadCustomersProjects = connection.prepareStatement(DBConnectionDAO.SELECT_CUSTOMERS_FROM_CUSTOMERS_PROJECTS);
                    PSReadCustomersProjects.setInt(1, customerId);
                    ResultSet RSCustomersProjects = PSReadCustomersProjects.executeQuery();

                    while(RSCustomersProjects.next()) {
                        Integer customerID = RSCustomersProjects.getInt("customer_id");
                        Integer projectID = RSCustomersProjects.getInt("project_id");

                        if(customerId == customerID) {
                            PSReadProjects = connection.prepareStatement(JavaIOProjectDAOImpl.SHOW_PROJECT);
                            PSReadProjects.setInt(1, projectID);
                            ResultSet RSReadProject = PSReadProjects.executeQuery();

                            while(RSReadProject.next()) {
                                Integer projectId = RSReadProject.getInt("id");

                                if(projectID == projectId) {
                                    String projectName = RSReadProject.getString("name");
                                    String projectVersion = RSReadProject.getString("version");
                                    Integer projectCost = RSReadProject.getInt("cost");

                                    project = new Project(projectId, projectName, projectVersion, projectCost);
                                    projects.add(project);
                                }
                            }
                            RSReadProject.close();
                            PSReadProjects.close();
                        }
                    }
                    RSCustomersProjects.close();
                    PSReadCustomersProjects.close();
                }
                System.out.println("==========");
                System.out.println("ID: " + customerId + "\n" +
                                    "Name: " + customerName);
                if(projects.isEmpty()) {
                    System.out.println("Projects: no projects");
                } else {
                    System.out.println("Projects:");
                    for(Project p:projects) {
                        System.out.println("---------" + p.getName());
                    }
                 }
                System.out.println();
                 projects.clear();
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
        PreparedStatement psReadCustomersProjects;
        PreparedStatement psReadProjects;

        try {

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL_DATABASE, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(SHOW_ALL_CUSTOMERS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                psReadCustomersProjects = connection.prepareStatement(DBConnectionDAO.SELECT_CUSTOMERS_FROM_CUSTOMERS_PROJECTS);
                psReadCustomersProjects.setInt(1, id);
                ResultSet rsReadCustomersProjects = psReadCustomersProjects.executeQuery();

                while(rsReadCustomersProjects.next()) {
                    Integer customerID = rsReadCustomersProjects.getInt("customer_id");
                    Integer projectID = rsReadCustomersProjects.getInt("project_id");

                    if(customerID == id) {
                        psReadProjects = connection.prepareStatement(JavaIOProjectDAOImpl.SHOW_PROJECT);
                        psReadProjects.setInt(1, projectID);
                        ResultSet rsReadProject = psReadProjects.executeQuery();

                        while(rsReadProject.next()) {
                            Integer projectId = rsReadProject.getInt("id");
                            String projectName = rsReadProject.getString("name");
                            String projectVersion = rsReadProject.getString("version");
                            Integer projectCost = rsReadProject.getInt("cost");

                            project = new Project(projectId, projectName, projectVersion, projectCost);
                            projects.add(project);
                        }
                        rsReadProject.close();
                        psReadProjects.close();
                    }
                }

                System.out.println("================");
                System.out.println("ID: " + id + "\n" +
                                    "Name: " + name);
                if(projects.isEmpty()) {
                    System.out.println("Projects: no projects");
                } else {
                    System.out.println("Projects:");
                    for(Project p:projects) {
                        System.out.println("--------" + p.getName());
                    }
                }
                rsReadCustomersProjects.close();
                psReadCustomersProjects.close();
                projects.clear();
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
    public void update(Customer customer) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Integer id = customer.getId();
        String name = customer.getName();

        try {

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL_DATABASE, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(UPDATE_CUSTOMER);
            preparedStatement.setInt(2, id);
            preparedStatement.setString(1, name);

            preparedStatement.executeUpdate();

            System.out.println("Customer has changed.");

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
            preparedStatement = connection.prepareStatement(DELETE_CUSTOMER);
            preparedStatement.setInt(1, id);

            PreparedStatement PSDeleteCustomersProjects = connection.prepareStatement(DBConnectionDAO.DELETE_CUSTOMER_FROM_CUSTOMERS_PROJECTS);
            PSDeleteCustomersProjects.setInt(1, id);

            PSDeleteCustomersProjects.executeUpdate();
            preparedStatement.executeUpdate();

            System.out.println("Customer with id = " + id + " has deleted");

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database error");
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
