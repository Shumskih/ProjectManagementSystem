package dao.jdbc;

import dao.ConnectionUtil;
import dao.GenericDAO;
import dao.SqlQueries;
import model.Customer;
import model.Project;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class JdbcCustomerDAOImpl implements GenericDAO<Customer,Long> {
    private ConnectionUtil connectionUtil = new ConnectionUtil();

    private Project project;
    private Set<Project> projects = new LinkedHashSet<>();

    @Override
    public void save(Customer customer) {
        Integer id = customer.getId();
        String name = customer.getName();

        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.INSERT_NEW_CUSTOMER)){

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);

            preparedStatement.executeUpdate();

            System.out.println("Customer has created!");
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error: ");
            System.out.println("----------------");
            e.printStackTrace();
        }

    }

    @Override
    public Customer getById(int id) {
        Customer customer;
        Integer customerId = null;
        String customerName = null;

        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.SHOW_CUSTOMER)){

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    customerId = resultSet.getInt("id");

                    if (customerId == id) {
                        customerName = resultSet.getString("name");

                        try (PreparedStatement psReadCustomersProjects = connection.prepareStatement(SqlQueries.SELECT_CUSTOMERS_FROM_CUSTOMERS_PROJECTS)) {
                            psReadCustomersProjects.setInt(1, customerId);

                            try (ResultSet RSCustomersProjects = psReadCustomersProjects.executeQuery()) {
                                while (RSCustomersProjects.next()) {
                                    Integer customerID = RSCustomersProjects.getInt("customer_id");
                                    Integer projectID = RSCustomersProjects.getInt("project_id");

                                    if (customerId == customerID) {
                                        try (PreparedStatement psReadProjects = connection.prepareStatement(SqlQueries.SHOW_PROJECT)) {

                                            psReadProjects.setInt(1, projectID);

                                            try (ResultSet rsReadProject = psReadProjects.executeQuery()) {
                                                while (rsReadProject.next()) {
                                                    Integer projectId = rsReadProject.getInt("id");

                                                    if (projectID == projectId) {
                                                        String projectName = rsReadProject.getString("name");
                                                        String projectVersion = rsReadProject.getString("version");
                                                        Integer projectCost = rsReadProject.getInt("cost");

                                                        project = new Project(projectId, projectName, projectVersion, projectCost);
                                                        projects.add(project);
                                                        project = null;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    System.out.println();
                    System.out.println("====================");
                    System.out.println("ID: " + customerId + "\n" +
                            "Name: " + customerName);
                    if (projects.isEmpty()) {
                        System.out.println("Projects: no projects");
                    } else {
                        System.out.println("Projects:");
                        for (Project p : projects) {
                            System.out.println("---------" + p.getName());
                        }
                    }
                    System.out.println("====================");
                    System.out.println();
                    projects.clear();
                }
            }
            projects.clear();
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error: ");
            System.out.println("----------------");
            e.printStackTrace();
        }
        customer = new Customer(customerId, customerName);
        return customer;
    }

    public void getListOfProjects(int customerID) {
        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement psCustomersProjects = connection.prepareStatement(SqlQueries.SHOW_CUSTOMERS_PROJECTS)){

            psCustomersProjects.setInt(1, customerID);

            try(ResultSet rsCustomersProjects = psCustomersProjects.executeQuery()) {
                while (rsCustomersProjects.next()) {
                    Integer customerId = rsCustomersProjects.getInt("customer_id");
                    Integer projectId = rsCustomersProjects.getInt("project_id");

                    if (customerId == customerID) {
                        try(PreparedStatement psProjects = connection.prepareStatement(SqlQueries.SHOW_PROJECT)) {
                            psProjects.setInt(1, projectId);

                            try(ResultSet rsProjects = psProjects.executeQuery()) {
                                while (rsProjects.next()) {
                                    Integer projectID = rsProjects.getInt("id");

                                    if (projectID == projectId) {
                                        String projectName = rsProjects.getString("name");
                                        String projectVersion = rsProjects.getString("version");
                                        Integer projectCost = rsProjects.getInt("cost");

                                        project = new Project(projectID, projectName, projectVersion, projectCost);
                                        projects.add(project);
                                        project = null;
                                    }
                                }
                            }
                        }
                    }
                }
                System.out.println();
                System.out.println("====================");
                if (projects.isEmpty()) {
                    System.out.println("Customer hasn't projects");
                } else {
                    for (Project p : projects) {
                        System.out.println("ID: " + p.getId());
                        System.out.println("Name: " + p.getName());
                    }
                }
            }
            System.out.println("====================");
            System.out.println();
            projects.clear();
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAll() {
        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.SHOW_ALL_CUSTOMERS);
             ResultSet resultSet = preparedStatement.executeQuery();
             PreparedStatement psReadCustomersProjects = connection.prepareStatement(SqlQueries.SELECT_CUSTOMERS_FROM_CUSTOMERS_PROJECTS);
             PreparedStatement psReadProjects = connection.prepareStatement(SqlQueries.SHOW_PROJECT)){

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                psReadCustomersProjects.setInt(1, id);

                try(ResultSet rsReadCustomersProjects = psReadCustomersProjects.executeQuery()) {
                    while (rsReadCustomersProjects.next()) {
                        Integer customerID = rsReadCustomersProjects.getInt("customer_id");
                        Integer projectID = rsReadCustomersProjects.getInt("project_id");

                        if (customerID == id) {
                            psReadProjects.setInt(1, projectID);

                            try(ResultSet rsReadProject = psReadProjects.executeQuery()) {
                                while (rsReadProject.next()) {
                                    Integer projectId = rsReadProject.getInt("id");
                                    String projectName = rsReadProject.getString("name");
                                    String projectVersion = rsReadProject.getString("version");
                                    Integer projectCost = rsReadProject.getInt("cost");

                                    project = new Project(projectId, projectName, projectVersion, projectCost);
                                    projects.add(project);
                                    project = null;
                                }
                            }
                        }
                    }
                    System.out.println("================");
                    System.out.println("ID: " + id + "\n" +
                            "Name: " + name);
                    if (projects.isEmpty()) {
                        System.out.println("Projects: no projects");
                    } else {
                        System.out.println("Projects:");
                        for (Project p : projects) {
                            System.out.println("--------" + p.getName());
                        }
                    }
                }
            }
            projects.clear();
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error: ");
            System.out.println("----------------");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Customer customer) {
        Integer id = customer.getId();
        String name = customer.getName();

        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.UPDATE_CUSTOMER)){

            preparedStatement.setInt(2, id);
            preparedStatement.setString(1, name);

            preparedStatement.executeUpdate();

            System.out.println("Customer has changed.");
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error: ");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.DELETE_CUSTOMER)){

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            System.out.println("Customer with id = " + id + " has deleted");
            System.out.println();

            deleteByCustomer(id);
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error");
            e.printStackTrace();
        }
    }

    public void insertCustProj(int customerId, int projectId) {
        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement psCustomersProjects = connection.prepareStatement(SqlQueries.INSERT_CUSTOMERS_PROJECTS)){

            psCustomersProjects.setInt(1, customerId);
            psCustomersProjects.setInt(2, projectId);

            psCustomersProjects.executeUpdate();

            System.out.println("Project has added");
            System.out.println();
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByProject(int projectId) {
        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement psCustomersProjects = connection.prepareStatement(SqlQueries.DELETE_CUSTOMERS_PROJECTS)){

            psCustomersProjects.setInt(1, projectId);

            psCustomersProjects.executeUpdate();

            System.out.println("Project has deleted");
            System.out.println();
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByCustomer(int customerId) {
        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement psCustomersProjects = connection.prepareStatement(SqlQueries.DELETE_CUSTOMER_FROM_CUSTOMERS_PROJECTS)){

            psCustomersProjects.setInt(1, customerId);

            psCustomersProjects.executeUpdate();
            System.out.println("All related projects are deleted");
            System.out.println();
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}