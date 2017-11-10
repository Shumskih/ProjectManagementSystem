package view;

import controller.CompanyController;
import controller.ProjectController;
import controller.SkillController;
import dao.*;
import model.Company;
import model.Project;
import model.Skill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class CompanyView {
    private CompanyController companyController = new CompanyController();
    private ProjectController projectController = new ProjectController();

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private Integer companyId;
    private String companyName;

    private Project project;
    private Set<Project> projects = new LinkedHashSet<>();

    private String userInput;

    public void createCompany() {
        boolean exit = false;

        try {
            do {
                System.out.println("Enter ID of company or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();
                if (userInput.equals("c")) {
                    exit = true;
                } else {
                    companyId = Integer.parseInt(userInput);
                }

                if (!exit) {
                    System.out.println("Enter name of company or c to cancel:");
                    userInput = br.readLine().trim().toLowerCase();
                    if (userInput.equals("c")) {
                        exit = true;
                    } else {
                        companyName = userInput;
                    }
                }

                if(!exit) {
                    Company company = new Company(companyId, companyName);
                    companyController.create(company);
                    System.out.println("Company created");
                }

                if (!exit) {
                    System.out.println("Add project to company? y = yes or n = no:");
                    userInput = br.readLine().trim().toLowerCase();
                    if (userInput.equals("n")) {
                        exit = true;
                    } else {
                        System.out.println("There is list of projects:");
                        projectController.readAll();
                        System.out.println();

                        do {
                            System.out.println("Enter ID of project you're going to add:");
                            userInput = br.readLine().trim().toLowerCase();

                            try {
                                Class.forName(DBConnectionDAO.JDBC_DRIVER);
                                Connection connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
                                PreparedStatement prepStatReadProject = connection.prepareStatement(JavaIOProjectDAOImpl.SHOW_PROJECT);
                                prepStatReadProject.setInt(1, Integer.parseInt(userInput));
                                ResultSet resultSet = prepStatReadProject.executeQuery();

                                while (resultSet.next()) {
                                    int projectId = resultSet.getInt("id");

                                    if(projectId == Integer.parseInt(userInput)) {
                                        String projectName = resultSet.getString("name");
                                        String projectVersion = resultSet.getString("version");
                                        Integer projectCost = resultSet.getInt("cost");

                                        project = new Project(projectId, projectName, projectVersion, projectCost);
                                        projects.add(project);
                                    }

                                }
                                PreparedStatement preparedStatement = connection.prepareStatement(DBConnectionDAO.INSERT_NEW_COMPANIES_PROJECTS);
                                preparedStatement.setInt(1, companyId);
                                preparedStatement.setInt(2, Integer.parseInt(userInput));

                                preparedStatement.executeUpdate();

                                System.out.println("Project added");

                                resultSet.close();
                                prepStatReadProject.close();
                                preparedStatement.close();
                                connection.close();
                            } catch (ClassNotFoundException | SQLException e) {
                                e.printStackTrace();
                            }

                            System.out.println("Add another project to company? y = yes or n = no:");
                            userInput = br.readLine().trim().toLowerCase();
                            if(userInput.equals("n")) {
                                projects.clear();
                                project = null;
                                returnToMainMenuBar();
                                exit = true;
                            }
                        } while(!exit);
                    }
                }

                if (!exit) {
                    Company company = new Company(companyId, companyName);
                    companyController.create(company);
                    exit = true;
                    returnToMainMenuBar();
                }

            } while (!exit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCompanyById() {
        try {
            do {
                System.out.println("Enter ID of company or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();
                if (!userInput.equals("c")) {
                    companyController.read(Integer.parseInt(userInput));
                    break;
                } else {
                    break;
                }
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAllCompanies() {
        companyController.readAll();
    }

    public void updateCompany() {
        Connection connection = null;
        PreparedStatement preparedStatement;
        PreparedStatement prepStatCompaniesProjects;
        PreparedStatement prepStatProjects;

        String userInputCompanyName;
        boolean exit = false;

        Integer id = null;

        try {
            System.out.println("Enter ID of company you're going to update or c to cancel:");
            userInput = br.readLine().trim().toLowerCase();
            if (userInput.equals("c")) {
                exit = true;
                returnToMainMenuBar();
            } else {
                id = Integer.parseInt(userInput);
                companyController.read(id);
            }

            do {
                System.out.println("Change name? y = yes, n = no:");
                userInput = br.readLine().trim().toLowerCase();
                if (userInput.equals("n")) {
                    exit = true;
                } else {
                    System.out.println("Enter new company name:");
                    userInputCompanyName = br.readLine().trim();

                    Company company = new Company(companyId, userInputCompanyName);
                    companyController.update(company);
                    break;
                }
            } while(!exit);

            do {
                do {
                    System.out.println("Change projects? y = yes, n = no:");
                    userInput = br.readLine().trim().toLowerCase();
                } while(!userInput.equals("y") & !userInput.equals("n"));
                if (userInput.equals("n")) {
                    exit = true;
                    returnToMainMenuBar();
                    break;
                } else {
                    System.out.println("There is list of projects company has:");
                    System.out.println("--------------------------------------");
                    try {
                        Class.forName(DBConnectionDAO.JDBC_DRIVER);
                        connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
                        prepStatCompaniesProjects = connection.prepareStatement(DBConnectionDAO.SHOW_COMPANIES_PROJECTS);
                        prepStatCompaniesProjects.setInt(1, id);
                        ResultSet resSetCompaniesProjects = prepStatCompaniesProjects.executeQuery();

                        while (resSetCompaniesProjects.next()) {
                            Integer companyId = resSetCompaniesProjects.getInt("company_id");
                            Integer projectId = resSetCompaniesProjects.getInt("project_id");

                            if (companyId == id) {
                                prepStatProjects = connection.prepareStatement(JavaIOProjectDAOImpl.SHOW_PROJECT);
                                prepStatProjects.setInt(1, projectId);
                                ResultSet resSetProjects = prepStatProjects.executeQuery();

                                while (resSetProjects.next()) {
                                    Integer projectID = resSetProjects.getInt("id");

                                    if (projectID == projectId) {
                                        String projectName = resSetProjects.getString("name");
                                        String projectVersion = resSetProjects.getString("version");
                                        Integer projectCost = resSetProjects.getInt("cost");

                                        project = new Project(projectID, projectName, projectVersion, projectCost);
                                        projects.add(project);
                                    }
                                }
                            }
                        }
                        if (projects.isEmpty()) {
                            System.out.println("Company hasn't projects");
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

            exit = false;
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
                            PreparedStatement prepSatDeleteFromCompaniesProjects = connection.prepareStatement(DBConnectionDAO.DELETE_COMPANIES_PROJECTS);
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
                            PreparedStatement prepStatAddProjectToCompany = connection.prepareStatement(DBConnectionDAO.INSERT_NEW_COMPANIES_PROJECTS);
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

    public void deleteCompany() {
        Connection connection = null;
        boolean exit = false;
        try {
            do {
                System.out.println("There is lis of companies:");
                System.out.println("--------------------------");
                companyController.readAll();

                System.out.println("Enter ID of company you are going to delete or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();
                if (!userInput.equals("c")) {
                    companyController.delete(Integer.parseInt(userInput));
                    try {
                        Class.forName(DBConnectionDAO.JDBC_DRIVER);
                        connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);

                    } catch(ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }
                    returnToMainMenuBar();
                    exit = true;
                } else {
                    exit = true;
                }
            } while (!exit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void returnToMainMenuBar() {
        try {
            System.out.println();
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
