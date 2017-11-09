package view;

import controller.CustomerController;
import controller.ProjectController;
import dao.DBConnectionDAO;
import dao.JavaIOCustomerDAOImpl;
import dao.JavaIOProjectDAOImpl;
import model.Customer;
import model.Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class CustomerView {
    private CustomerController customerController = new CustomerController();
    private ProjectController projectController = new ProjectController();
    private DBConnectionDAO dbConnectionDAO = new DBConnectionDAO();

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    Connection connection = null;

    private Integer customerId;
    private String customerName;

    private Project project;
    private Set<Project> projects = new LinkedHashSet<>();

    private String userInput;

    public void createCustomer() {
        PreparedStatement PSCustomersProjects;
        boolean exit = false;

        try {
            while(!exit){
                System.out.println("Enter customer's ID or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();
                if(userInput.equals("c")) {
                    exit = true;
                } else {
                    customerId = Integer.parseInt(userInput);
                    break;
                }
            }

            while(!exit){
                System.out.println("Enter customer's name or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();
                if(userInput.equals("c")) {
                    exit = true;
                } else {
                    customerName = userInput;

                    Customer customer = new Customer(customerId, customerName);
                    customerController.create(customer);
                    break;
                }
            }

            while(!exit) {
                System.out.println("Add project to customer? y = yes, n = no:");
                userInput = br.readLine().trim().toLowerCase();
                if(!userInput.equals("y") && !userInput.equals("n")) {
                    do {
                        System.out.println("Add project to customer? y = yes, n = no:");
                        userInput = br.readLine().trim().toLowerCase();
                    } while (!userInput.equals("y") && !userInput.equals("n"));
                }

                if(userInput.equals("n")) {
                    exit = true;
                } else {
                    System.out.println("There is list of projects:");
                    System.out.println("--------------------------");
                    projectController.readAll();
                    System.out.println("Enter ID of project you're going to add:");
                    userInput = br.readLine().trim().toLowerCase();

                    try {
                        Class.forName(DBConnectionDAO.JDBC_DRIVER);
                        connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
                        PSCustomersProjects = connection.prepareStatement(DBConnectionDAO.INSERT_CUSTOMERS_PROJECTS);
                        PSCustomersProjects.setInt(2, Integer.parseInt(userInput));
                        PSCustomersProjects.setInt(1, customerId);
                        PSCustomersProjects.executeUpdate();
                        System.out.println("Project has added");
                    } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Add another project? y = yes, n = no:");
                    userInput = br.readLine().trim().toLowerCase();
                    if(userInput.equals("n")) {
                        exit = true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCustomerById() {
        boolean exit = false;
        try {
            do {
                System.out.println("Enter ID of customer or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();
                if(!userInput.equals("c")) {
                    customerController.read(Integer.parseInt(userInput));
                    exit = true;
                    returnToMainMenuBar();
                } else {
                    exit = true;
                    returnToMainMenuBar();
                }
            } while(!exit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAllCustomers() {
        customerController.readAll();
        System.out.println();
        returnToMainMenuBar();
    }

    public void updateCustomer() {
        Connection connection = null;
        PreparedStatement preparedStatement;
        PreparedStatement psCustomersProjects;
        PreparedStatement psProjects;

        String userInputCustomerName;
        boolean exit = false;

        Integer id = null;

        try {
            System.out.println("Enter ID of customer you're going to update:");
            userInput = br.readLine().trim().toLowerCase();
            if(userInput.equals("c")) {
                exit = true;
                returnToMainMenuBar();
            } else {
                id = Integer.parseInt(userInput);
                System.out.println("This is a customer you're going to update");
                System.out.println("-----------------------------------------");
                try {
                    Class.forName(JavaIOCustomerDAOImpl.JDBC_DRIVER);
                    connection = DriverManager.getConnection(JavaIOCustomerDAOImpl.URL_DATABASE, JavaIOCustomerDAOImpl.USERNAME, JavaIOCustomerDAOImpl.PASSWORD);
                    preparedStatement = connection.prepareStatement(JavaIOCustomerDAOImpl.SHOW_CUSTOMER);
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while(resultSet.next()) {
                        customerId = resultSet.getInt("id");

                        if(customerId == id) {
                            customerName = resultSet.getString("name");

                            System.out.println("ID: " + customerId + "\n" +
                                                "Name: " + customerName);
                            System.out.println("=================================");
                        }
                    }
                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                } catch (ClassNotFoundException e) {
                    System.out.println("JDBC driver not found");
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            do {
                System.out.println("Change name? y = yes, n = no:");
                userInput = br.readLine().trim().toLowerCase();
                if(userInput.equals("n")) {
                    break;
                } else {
                    System.out.println("Enter new customer name:");
                    userInputCustomerName = br.readLine().trim();

                    Customer customer = new Customer(customerId, userInputCustomerName);
                    customerController.update(customer);
                    break;
                }
            } while(!exit);

            do {
                do {
                    System.out.println("Change project? y = yes, n = no:");
                    userInput = br.readLine().trim().toLowerCase();
                } while(!userInput.equals("y") & !userInput.equals("n"));
                if (userInput.equals("n")) {
                    exit = true;
                    returnToMainMenuBar();
                    break;
                } else {
                    System.out.println("There is list of projects customer has:");
                    System.out.println("--------------------------------------");
                    try {
                        Class.forName(DBConnectionDAO.JDBC_DRIVER);
                        connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
                        psCustomersProjects = connection.prepareStatement(DBConnectionDAO.SHOW_CUSTOMERS_PROJECTS);
                        psCustomersProjects.setInt(1, id);
                        ResultSet rsCustomersProjects = psCustomersProjects.executeQuery();

                        while (rsCustomersProjects.next()) {
                            Integer customerId = rsCustomersProjects.getInt("customer_id");
                            Integer projectId = rsCustomersProjects.getInt("project_id");

                            if (customerId == id) {
                                psProjects = connection.prepareStatement(JavaIOProjectDAOImpl.SHOW_PROJECT);
                                psProjects.setInt(1, projectId);
                                ResultSet rsProjects = psProjects.executeQuery();

                                while (rsProjects.next()) {
                                    Integer projectID = rsProjects.getInt("id");

                                    if (projectID == projectId) {
                                        String projectName = rsProjects.getString("name");
                                        String projectVersion = rsProjects.getString("version");
                                        Integer projectCost = rsProjects.getInt("cost");

                                        project = new Project(projectID, projectName, projectVersion, projectCost);
                                        projects.add(project);
                                    }
                                }
                            }
                        }
                        if (projects.isEmpty()) {
                            System.out.println("Customer hasn't projects");
                        } else {
                            for (Project p : projects) {
                                System.out.println("ID: " + p.getId());
                                System.out.println("Name: " + p.getName());
                                System.out.println("===============");
                            }
                        }
                    } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            } while(!exit);

            if(!exit) {
                System.out.println("Delete project or insert new? d = delete, i = insert new:");
                userInput = br.readLine().trim().toLowerCase();
                if (!userInput.equals("d") && !userInput.equals("i")) {
                    do {
                        System.out.println("Enter d to delete project or i to insert new:");
                        userInput = br.readLine().trim().toLowerCase();
                    } while (!userInput.equals("d") && !userInput.equals("i"));
                } else {
                    if (userInput.equals("d")) {
                        System.out.println("Enter ID of project you're going to delete:");
                        userInput = br.readLine().trim().toLowerCase();
                        try {
                            connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
                            PreparedStatement prepSatDeleteFromCompaniesProjects = connection.prepareStatement(DBConnectionDAO.DELETE_CUSTOMERS_PROJECTS);
                            prepSatDeleteFromCompaniesProjects.setInt(1, Integer.parseInt(userInput));
                            prepSatDeleteFromCompaniesProjects.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Project has deleted");
                        returnToMainMenuBar();
                    } else {
                        System.out.println("There is list of projects:");
                        projectController.readAll();
                        System.out.println();
                        System.out.println("Enter ID of project you're going to add:");
                        userInput = br.readLine().trim().toLowerCase();

                        try {
                            PreparedStatement prepStatAddProjectToCompany = connection.prepareStatement(DBConnectionDAO.INSERT_NEW_CUSTOMERS_PROJECTS);
                            prepStatAddProjectToCompany.setInt(1, id);
                            prepStatAddProjectToCompany.setInt(2, Integer.parseInt(userInput));
                            prepStatAddProjectToCompany.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Project has added");
                        returnToMainMenuBar();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteCustomer() {
        boolean exit = false;

        try {
            do {
                System.out.println("There is lis of customers:");
                System.out.println("--------------------------");
                customerController.readAll();

                System.out.println("Enter ID of customer you're going to delete:");
                userInput = br.readLine().trim().toLowerCase();
                if(!userInput.equals("c")) {
                    customerController.delete(Integer.parseInt(userInput));
                    returnToMainMenuBar();
                    exit = true;
                } else {
                    returnToMainMenuBar();
                    exit = true;
                }
            } while(!exit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void returnToMainMenuBar() {
        try {
            System.out.print("Returning to main menu.");
            Thread.currentThread().sleep(300);
            System.out.print(".");
            Thread.currentThread().sleep(300);
            System.out.print(".");
            Thread.currentThread().sleep(300);
            System.out.print(".");
            Thread.currentThread().sleep(300);
            System.out.print(".");
            Thread.currentThread().sleep(300);
            System.out.print(".");
            Thread.currentThread().sleep(300);
            System.out.print(".");
            Thread.currentThread().sleep(300);
            System.out.println();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
