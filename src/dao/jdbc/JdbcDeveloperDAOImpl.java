package dao.jdbc;

import dao.ConnectionUtil;
import dao.GenericDAO;
import dao.SqlQueries;
import model.Developer;
import model.Project;
import model.Skill;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class JdbcDeveloperDAOImpl implements GenericDAO<Developer,Long> {
    private ConnectionUtil connectionUtil = new ConnectionUtil();

    private Skill skill;
    private Project project;

    private Set<Skill> skills = new LinkedHashSet<>();
    private Set<Project> projects = new LinkedHashSet<>();

    @Override
    public void save(Developer developer) {
        Integer id = developer.getId();
        String name = developer.getName();
        String specialization = developer.getSpecialization();
        Integer experience = developer.getExperience();
        Integer salary = developer.getSalary();

        try (Connection connection = connectionUtil.getDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.INSERT_NEW_DEVELOPER)){

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, specialization);
            preparedStatement.setInt(4, experience);
            preparedStatement.setInt(5, salary);

            preparedStatement.executeUpdate();

            System.out.println("Developer has created.");
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error: ");
            e.printStackTrace();
        }
    }

    @Override
    public Developer getById(int id) {
        Developer developer;
        Integer developerId = null;
        String name = null;
        String specialization = null;
        Integer experience = null;
        Integer salary = null;

        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.SHOW_DEVELOPER)){

            preparedStatement.setInt(1, id);

            try (PreparedStatement psReadDevelopersSkills = connection.prepareStatement(SqlQueries.SHOW_DEVELOPER_SKILLS)){
                psReadDevelopersSkills.setInt(1, id);

                try (PreparedStatement psReadProjects = connection.prepareStatement(SqlQueries.SHOW_PROJECTS_DEVELOPERS)){
                    psReadProjects.setInt(1, id);

                    try (ResultSet rsDeveloper = preparedStatement.executeQuery();
                        ResultSet rsDevelopersSkills = psReadDevelopersSkills.executeQuery();
                        ResultSet rsProjectsDeveloper = psReadProjects.executeQuery()){
                        while (rsDeveloper.next()) {
                            developerId = rsDeveloper.getInt("id");

                            if (developerId == id) {
                                name = rsDeveloper.getString("name");
                                specialization = rsDeveloper.getString("specialization");
                                experience = rsDeveloper.getInt("experience");
                                salary = rsDeveloper.getInt("salary");

                                while (rsDevelopersSkills.next()) {
                                    int developerID = rsDevelopersSkills.getInt("developer_id");
                                    int skillID = rsDevelopersSkills.getInt("skill_id");

                                    if (developerID == id) {
                                        try (PreparedStatement psSkills = connection.prepareStatement(SqlQueries.SHOW_SKILL)) {

                                        psSkills.setInt(1, skillID);
                                        try (ResultSet rsSkills = psSkills.executeQuery()){

                                            while (rsSkills.next()) {
                                                Integer skillId = rsSkills.getInt("id");
                                                String skillName = rsSkills.getString("name");

                                                if (skillID == skillId) {
                                                    skill = new Skill(skillId, skillName);
                                                    skills.add(skill);
                                                    skill = null;
                                                }
                                            }
                                        }
                                    }
                                    }
                                }
                                while (rsProjectsDeveloper.next()) {
                                    int developerID = rsProjectsDeveloper.getInt("developer_id");
                                    int projectID = rsProjectsDeveloper.getInt("project_id");

                                    if (developerID == id) {
                                        try(PreparedStatement psProjects = connection.prepareStatement(SqlQueries.SHOW_PROJECT)) {
                                            psProjects.setInt(1, projectID);

                                            try (ResultSet rsProjects = psProjects.executeQuery()) {
                                                while (rsProjects.next()) {
                                                    Integer projectId = rsProjects.getInt("id");
                                                    String projectName = rsProjects.getString("name");
                                                    String projectVersion = rsProjects.getString("version");
                                                    Integer projectCost = rsProjects.getInt("cost");

                                                    if (projectID == projectId) {
                                                        project = new Project(projectId, projectName, projectVersion, projectCost);
                                                        projects.add(project);
                                                        project = null;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                System.out.println();
                                System.out.println("==============");
                                System.out.println("ID: " + developerId + "\n" +
                                        "Name: " + name + "\n" +
                                        "Specialization: " + specialization + "\n" +
                                        "Experience: " + experience + " year" + "\n" +
                                        "Salary: " + salary + "\n" +
                                        "Skills: ");
                                for (Skill s : skills) {
                                    System.out.println("------" + s.getName());
                                }
                                System.out.println("Projects: ");
                                for (Project p : projects) {
                                    System.out.println("-------" + p.getName());
                                }
                                System.out.println("==============");
                                System.out.println();
                            }
                        }
                    }
                }
            }
            skills.clear();
            projects.clear();
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error: ");
            System.out.println("----------------");
            e.printStackTrace();
        }
        developer = new Developer(developerId, name, specialization, experience, salary, skills, projects);
        return developer;
    }

    public Developer getDeveloper(int developerId) {
        Developer developer;
        Integer id = null;
        String name = null;
        String specialization = null;
        Integer experience = null;
        Integer salary = null;

        try (Connection connection = connectionUtil.getDBConnection();
            PreparedStatement psReadDeveloper = connection.prepareStatement(SqlQueries.SHOW_DEVELOPER)){

            psReadDeveloper.setInt(1, developerId);

            try (ResultSet rsReadDeveloper = psReadDeveloper.executeQuery()) {
                while (rsReadDeveloper.next()) {
                    id = rsReadDeveloper.getInt("id");
                    name = rsReadDeveloper.getString("name");
                    specialization = rsReadDeveloper.getString("specialization");
                    experience = rsReadDeveloper.getInt("experience");
                    salary = rsReadDeveloper.getInt("salary");
                }
            }
            connectionUtil.putConnection(connection);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        developer = new Developer(id, name, specialization, experience, salary);
        return developer;
    }

    public void getListOfProjects(int developerID) {
        try (Connection connection = connectionUtil.getDBConnection();
            PreparedStatement psProjectsDevelopers = connection.prepareStatement(SqlQueries.SHOW_PROJECTS_DEVELOPERS)){

            psProjectsDevelopers.setInt(1, developerID);
            try(ResultSet rsProjectsDevelopers = psProjectsDevelopers.executeQuery()) {
                while (rsProjectsDevelopers.next()) {
                    Integer projectId = rsProjectsDevelopers.getInt("project_id");
                    Integer developerId = rsProjectsDevelopers.getInt("developer_id");

                    if (developerId == developerID) {
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
                if (projects.isEmpty()) {
                    System.out.println("Developer hasn't projects");
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

    public void getListOfSkills(int developerID) {
        try (Connection connection = connectionUtil.getDBConnection();
            PreparedStatement psDevelopersSkills = connection.prepareStatement(SqlQueries.SHOW_DEVELOPER_SKILLS)){

            psDevelopersSkills.setInt(1, developerID);

            try(ResultSet rsDevelopersSkills = psDevelopersSkills.executeQuery()) {
                while (rsDevelopersSkills.next()) {
                    Integer developerId = rsDevelopersSkills.getInt("developer_id");
                    Integer skillId = rsDevelopersSkills.getInt("skill_id");

                    if (developerId == developerID) {
                        try (PreparedStatement psSkills = connection.prepareStatement(SqlQueries.SHOW_SKILL)) {
                            psSkills.setInt(1, skillId);

                            try(ResultSet rsSkills = psSkills.executeQuery()) {


                                while (rsSkills.next()) {
                                    Integer skillID = rsSkills.getInt("id");

                                    if (skillID == skillId) {
                                        String skillName = rsSkills.getString("name");

                                        skill = new Skill(skillID, skillName);
                                        skills.add(skill);
                                        skill = null;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (skills.isEmpty()) {
                System.out.println("Developer hasn't skills");
            } else {
                for (Skill s : skills) {
                    System.out.println("ID: " + s.getId());
                    System.out.println("Name: " + s.getName());
                    System.out.println("===============");
                }
            }
            skills.clear();
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAll() {
        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.SHOW_ALL_DEVELOPERS);
             ResultSet resultSet = preparedStatement.executeQuery()){

            while(resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                Integer experience = resultSet.getInt("experience");
                Integer salary = resultSet.getInt("salary");

                System.out.println("================");
                System.out.println("ID: " + id + "\n" +
                                    "Name: " + name + "\n" +
                                    "Specialization: " + specialization + "\n" +
                                    "Experience: " + experience + " year" + "\n" +
                                    "Salary: " + salary);
            }
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error: ");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Developer developer) {
        Integer id = developer.getId();
        String name = developer.getName();
        String specialization = developer.getSpecialization();
        Integer experience = developer.getExperience();
        Integer salary = developer.getSalary();

        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.UPDATE_DEVELOPER)){

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, specialization);
            preparedStatement.setInt(3, experience);
            preparedStatement.setInt(4, salary);
            preparedStatement.setInt(5, id);

            preparedStatement.executeUpdate();

            System.out.println("Developer has updated.");
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error: ");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.DELETE_DEVELOPER)){

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            System.out.println("Developer with id = " + id + " has deleted");
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error: ");
            e.printStackTrace();
        }
    }

    public void insertProjDev(int projectId, int developerId) {
        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement psProjectsDevelopers = connection.prepareStatement(SqlQueries.INSERT_NEW_PROJECTS_DEVELOPERS)){

            psProjectsDevelopers.setInt(1, projectId);
            psProjectsDevelopers.setInt(2, developerId);

            psProjectsDevelopers.executeUpdate();
            System.out.println("Project has added");
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByProject(int projectId) {
        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement psProjectsDevelopers = connection.prepareStatement(SqlQueries.DELETE_PROJECT_FROM_PROJECTS_DEVELOPERS)){

            psProjectsDevelopers.setInt(1, projectId);
            psProjectsDevelopers.executeUpdate();

            System.out.println("Project has deleted");
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertDevSkill(int developerId, int skillId) {
        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement psDevelopersSkills = connection.prepareStatement(SqlQueries.INSERT_NEW_DEVELOPER_SKILLS)){

            psDevelopersSkills.setInt(1, developerId);
            psDevelopersSkills.setInt(2, skillId);

            psDevelopersSkills.executeUpdate();
            System.out.println("Skill has added");
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBySkill(int skillId) {
        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement psDevelopersSkills = connection.prepareStatement(SqlQueries.DELETE_SKILL_FROM_DEVELOPERS_SKILLS)){

            psDevelopersSkills.setInt(1, skillId);
            psDevelopersSkills.executeUpdate();

            System.out.println("Skill has deleted");
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
