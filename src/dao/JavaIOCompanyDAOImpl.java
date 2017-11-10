package dao;

import model.Company;
import model.Project;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class JavaIOCompanyDAOImpl implements CompanyDAO{

    private static final String INSERT_NEW_COMPANY = "INSERT INTO companies VALUES(?,?)";
    public static final String SHOW_COMPANY = "SELECT * FROM companies WHERE id=?";
    private static final String SHOW_ALL_COMPANIES = "SELECT * FROM companies";
    private static final String UPDATE_COMPANY = "UPDATE companies SET name=? WHERE id=?";
    private static final String DELETE_COMPANY   = "DELETE FROM companies WHERE id=?";

    private Project project;

    private Set<Project> projects = new LinkedHashSet<>();

    @Override
    public void create(Company company) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Integer id = company.getId();
        String name = company.getName();

        try {

            Class.forName(DBConnectionDAO.JDBC_DRIVER);
            connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
            preparedStatement = connection.prepareStatement(INSERT_NEW_COMPANY);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);

            preparedStatement.executeUpdate();

            System.out.println("Company has created");


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

    @Override
    public void read(int id) {
        Connection connection = null;
        PreparedStatement prepStatShowCompany = null;
        PreparedStatement prepStatReadProject;

        try {

            Class.forName(DBConnectionDAO.JDBC_DRIVER);
            connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
            prepStatShowCompany = connection.prepareStatement(SHOW_COMPANY);
            prepStatShowCompany.setInt(1, id);
            ResultSet resultSetCompany = prepStatShowCompany.executeQuery();

            prepStatReadProject = connection.prepareStatement(DBConnectionDAO.SHOW_COMPANIES_PROJECTS);
            prepStatReadProject.setInt(1, id);
            ResultSet resSetCompaniesProjects = prepStatReadProject.executeQuery();

            while(resultSetCompany.next()) {
                int companyId = resultSetCompany.getInt("id");

                if (companyId == id) {
                    String name = resultSetCompany.getString("name");

                    while(resSetCompaniesProjects.next()) {
                        int companyID = resSetCompaniesProjects.getInt("company_id");
                        int projectID = resSetCompaniesProjects.getInt("project_id");

                        if(companyID == companyId) {
                            PreparedStatement prepStatProjects = connection.prepareStatement(JavaIOProjectDAOImpl.SHOW_PROJECT);
                            prepStatProjects.setInt(1, projectID);
                            ResultSet resultSet = prepStatProjects.executeQuery();

                            while(resultSet.next()) {
                                Integer projectId = resultSet.getInt("id");
                                String projectName = resultSet.getString("name");
                                String projectVersion = resultSet.getString("version");
                                Integer projectCost = resultSet.getInt("cost");

                                project = new Project(projectId, projectName, projectVersion, projectCost);
                                projects.add(project);
                            }
                        }
                    }

                    System.out.println();
                    System.out.println("====================");
                    System.out.println("ID: " + companyId + "\n" +
                                        "Name: " + name + "\n" +
                                        "Projects: ");
                    for(Project p:projects) {
                        System.out.println("------" + p.getName());
                    }
                    System.out.println("====================");
                    System.out.println();
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database error");
            e.printStackTrace();
        } finally {
            try {
                prepStatShowCompany.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void readAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement prepStatCompaniesProjects;
        PreparedStatement prepStatProjects;

        try {

            Class.forName(DBConnectionDAO.JDBC_DRIVER);
            connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
            preparedStatement = connection.prepareStatement(SHOW_ALL_COMPANIES);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                prepStatCompaniesProjects = connection.prepareStatement(DBConnectionDAO.SHOW_COMPANIES_PROJECTS);
                prepStatCompaniesProjects.setInt(1, id);
                ResultSet resSetCompaniesProjects = prepStatCompaniesProjects.executeQuery();

                while(resSetCompaniesProjects.next()) {
                    Integer companyId = resSetCompaniesProjects.getInt("company_id");
                    Integer projectId = resSetCompaniesProjects.getInt("project_id");

                    if (companyId == id) {
                        prepStatProjects = connection.prepareStatement(JavaIOProjectDAOImpl.SHOW_PROJECT);
                        prepStatProjects.setInt(1, projectId);
                        ResultSet resSetShowProject = prepStatProjects.executeQuery();



                        while (resSetShowProject.next()) {
                            Integer projectID = resSetShowProject.getInt("id");

                            if(projectId == projectID) {
                                String projectName = resSetShowProject.getString("name");
                                String projectVersion = resSetShowProject.getString("version");
                                Integer projectCost = resSetShowProject.getInt("cost");

                                project = new Project(projectID, projectName, projectVersion, projectCost);
                                projects.add(project);
                            }
                        }
                    }
                }
                System.out.println("================");
                System.out.println("ID: " + id + "\n" +
                        "Name: " + name);
                if(projects.isEmpty()) {
                    System.out.print("Projects: ");
                    System.out.println("no projects");
                } else {
                    System.out.println("Projects:");
                    for (Project p : projects) {
                        System.out.println("-------" + p.getName());
                    }
                }

                projects.clear();
            }
            resultSet.close();


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
                System.out.println("Can't close connection");
                e.printStackTrace();
            }
        }

    }

    @Override
    public void update(Company company) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Integer id = company.getId();
        String name = company.getName();

        try {

            Class.forName(DBConnectionDAO.JDBC_DRIVER);
            connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
            preparedStatement = connection.prepareStatement(UPDATE_COMPANY);
            preparedStatement.setInt(2, id);
            preparedStatement.setString(1, name);

            preparedStatement.executeUpdate();

            System.out.println("Company has updated");

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

            Class.forName(DBConnectionDAO.JDBC_DRIVER);
            connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
            preparedStatement = connection.prepareStatement(DELETE_COMPANY);
            preparedStatement.setInt(1, id);

            PreparedStatement PSDeleteCompaniesProjects = connection.prepareStatement(DBConnectionDAO.DELETE_COMPANY_FROM_COMPANIES_PROJECTS);
            PSDeleteCompaniesProjects.setInt(1, id);

            PSDeleteCompaniesProjects.executeUpdate();
            preparedStatement.executeUpdate();

            System.out.println("Company with id = " + id + " has deleted");

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
