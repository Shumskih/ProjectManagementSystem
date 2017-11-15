package dao.jdbc;

import dao.ConnectionUtil;
import dao.GenericDAO;
import dao.SqlQueries;
import model.Company;
import model.Project;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class JdbcCompanyDAOImpl implements GenericDAO<Company,Long> {
    private ConnectionUtil connectionUtil = new ConnectionUtil();

    private Project project;

    private Set<Project> projects = new LinkedHashSet<>();

    @Override
    public void save(Company company) {
        Integer id = company.getId();
        String name = company.getName();

        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.INSERT_NEW_COMPANY)){

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);

            preparedStatement.executeUpdate();

            System.out.println("Company has created");
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error");
            e.printStackTrace();
        }
    }

    @Override
    public Company getById(int id) {
        Company company;
        Integer companyId = null;
        String companyName = null;

        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement psShowCompany = connection.prepareStatement(SqlQueries.SHOW_COMPANY);
             PreparedStatement psReadProject = connection.prepareStatement(SqlQueries.SHOW_COMPANIES_PROJECTS);
             PreparedStatement psProjects = connection.prepareStatement(SqlQueries.SHOW_PROJECT)){

           psShowCompany.setInt(1, id);

            try(ResultSet rsCompany = psShowCompany.executeQuery()) {
                psReadProject.setInt(1, id);

                try(ResultSet rsCompaniesProjects = psReadProject.executeQuery()) {
                    while (rsCompany.next()) {
                        companyId = rsCompany.getInt("id");

                        if (companyId == id) {
                            companyName = rsCompany.getString("name");

                            while (rsCompaniesProjects.next()) {
                                int companyID = rsCompaniesProjects.getInt("company_id");
                                int projectID = rsCompaniesProjects.getInt("project_id");

                                if (companyID == companyId) {
                                    psProjects.setInt(1, projectID);

                                    try(ResultSet resultSet =psProjects.executeQuery()) {
                                        while (resultSet.next()) {
                                            Integer projectId = resultSet.getInt("id");
                                            String projectName = resultSet.getString("name");
                                            String projectVersion = resultSet.getString("version");
                                            Integer projectCost = resultSet.getInt("cost");

                                            project = new Project(projectId, projectName, projectVersion, projectCost);
                                            projects.add(project);
                                            project = null;
                                        }
                                    }
                                }
                            }
                            System.out.println();
                            System.out.println("====================");
                            System.out.println("ID: " + companyId + "\n" +
                                    "Name: " + companyName);
                            if (projects.isEmpty()) {
                                System.out.println("Projects: no projects");
                            } else {
                                System.out.println("Projects:");
                                for (Project p : projects) {
                                    System.out.println("------" + p.getName());
                                }
                            }
                            System.out.println("====================");
                            System.out.println();
                        }
                    }
                }
            }
            projects.clear();
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error");
            e.printStackTrace();
        }
        company = new Company(companyId, companyName);
        return company;
    }

    @Override
    public void getAll() {
        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.SHOW_ALL_COMPANIES);
             ResultSet resultSet = preparedStatement.executeQuery();
             PreparedStatement psCompaniesProjects = connection.prepareStatement(SqlQueries.SHOW_COMPANIES_PROJECTS);
             PreparedStatement psProjects = connection.prepareStatement(SqlQueries.SHOW_PROJECT)){

            while(resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                psCompaniesProjects.setInt(1, id);

                try(ResultSet resSetCompaniesProjects =psCompaniesProjects.executeQuery()) {


                    while (resSetCompaniesProjects.next()) {
                        Integer companyId = resSetCompaniesProjects.getInt("company_id");
                        Integer projectId = resSetCompaniesProjects.getInt("project_id");

                        if (companyId == id) {
                            psProjects.setInt(1, projectId);

                            try(ResultSet resSetShowProject = psProjects.executeQuery()) {
                                while (resSetShowProject.next()) {
                                    Integer projectID = resSetShowProject.getInt("id");

                                    if (projectId == projectID) {
                                        String projectName = resSetShowProject.getString("name");
                                        String projectVersion = resSetShowProject.getString("version");
                                        Integer projectCost = resSetShowProject.getInt("cost");

                                        project = new Project(projectID, projectName, projectVersion, projectCost);
                                        projects.add(project);
                                        project = null;
                                    }
                                }
                            }
                        }
                    }
                    System.out.println("================");
                    System.out.println("ID: " + id + "\n" +
                            "Name: " + name);
                    if (projects.isEmpty()) {
                        System.out.print("Projects: ");
                        System.out.println("no projects");
                    } else {
                        System.out.println("Projects:");
                        for (Project p : projects) {
                            System.out.println("-------" + p.getName());
                        }
                    }
                }
            }
            projects.clear();
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Company company) {
        Integer id = company.getId();
        String name = company.getName();

        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.UPDATE_COMPANY)){

            preparedStatement.setInt(2, id);
            preparedStatement.setString(1, name);

            preparedStatement.executeUpdate();

            System.out.println("Company has updated");
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error: ");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.DELETE_COMPANY);
             PreparedStatement psDeleteCompaniesProjects = connection.prepareStatement(SqlQueries.DELETE_COMPANY_FROM_COMPANIES_PROJECTS);){

            preparedStatement.setInt(1, id);

            psDeleteCompaniesProjects.setInt(1, id);

            psDeleteCompaniesProjects.executeUpdate();
            preparedStatement.executeUpdate();

            System.out.println("Company with id = " + id + " has deleted");
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error");
            e.printStackTrace();
        }
    }

    public void insertComProj(int companyId, int projectId) {
        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement psCompaniesProjects = connection.prepareStatement(SqlQueries.INSERT_NEW_COMPANIES_PROJECTS)){

            psCompaniesProjects.setInt(1, companyId);
            psCompaniesProjects.setInt(2, projectId);

            psCompaniesProjects.executeUpdate();
            System.out.println("Project has added");
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getListOfProjects(int companyID) {
        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement psCompaniesProjects = connection.prepareStatement(SqlQueries.SHOW_COMPANIES_PROJECTS);
             PreparedStatement psProjects = connection.prepareStatement(SqlQueries.SHOW_PROJECT);){

            psCompaniesProjects.setInt(1, companyID);

            try(ResultSet rsCompaniesProjects = psCompaniesProjects.executeQuery()) {
                while (rsCompaniesProjects.next()) {
                    Integer companyId =rsCompaniesProjects.getInt("company_id");
                    Integer projectId =rsCompaniesProjects.getInt("project_id");

                    if (companyId == companyID) {
                        psProjects.setInt(1, projectId);

                        try(ResultSet resSetProjects = psProjects.executeQuery()) {
                            while (resSetProjects.next()) {
                                Integer projectID = resSetProjects.getInt("id");

                                if (projectID == projectId) {
                                    String projectName = resSetProjects.getString("name");
                                    String projectVersion = resSetProjects.getString("version");
                                    Integer projectCost = resSetProjects.getInt("cost");

                                    project = new Project(projectID, projectName, projectVersion, projectCost);
                                    projects.add(project);
                                    project = null;
                                }
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
            }
            projects.clear();
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByProject(int projectId) {
        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement psCompaniesProjects = connection.prepareStatement(SqlQueries.DELETE_COMPANIES_PROJECTS)){

            psCompaniesProjects.setInt(1, projectId);

            psCompaniesProjects.executeUpdate();
            System.out.println("Project has deleted");
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
