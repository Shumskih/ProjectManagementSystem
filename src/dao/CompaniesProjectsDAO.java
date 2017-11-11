package dao;

import model.Project;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class CompaniesProjectsDAO {
    DBConnectionDAOImpl dbConnectionDAO = new DBConnectionDAOImpl();

    private Connection connection = null;
    private PreparedStatement prepStatProjects = null;
    private PreparedStatement PSCompaniesProjects = null;

    Project project;
    Set<Project> projects = new LinkedHashSet<>();


    public void insert(int companyId, int projectId) {
        try {
            connection = dbConnectionDAO.getDBConnection();
            PSCompaniesProjects = connection.prepareStatement(DBConnectionDAOImpl.INSERT_NEW_COMPANIES_PROJECTS);

            PSCompaniesProjects.setInt(1, companyId);
            PSCompaniesProjects.setInt(2, projectId);

            PSCompaniesProjects.executeUpdate();
            System.out.println("Project has added");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void readListOfProjects(int companyID) {
        try {
            connection = dbConnectionDAO.getDBConnection();
            PSCompaniesProjects = connection.prepareStatement(DBConnectionDAOImpl.SHOW_COMPANIES_PROJECTS);
            PSCompaniesProjects.setInt(1, companyID);
            ResultSet resSetCompaniesProjects = PSCompaniesProjects.executeQuery();

            while (resSetCompaniesProjects.next()) {
                Integer companyId = resSetCompaniesProjects.getInt("company_id");
                Integer projectId = resSetCompaniesProjects.getInt("project_id");

                if (companyId == companyID) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByProject(int projectId) {
        try {
            connection = dbConnectionDAO.getDBConnection();
            PSCompaniesProjects = connection.prepareStatement(DBConnectionDAOImpl.DELETE_COMPANIES_PROJECTS);

            PSCompaniesProjects.setInt(1, projectId);

            PSCompaniesProjects.executeUpdate();
            System.out.println("Project has deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
