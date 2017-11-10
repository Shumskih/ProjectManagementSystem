package dao;

import model.Developer;
import model.Project;
import model.Skill;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class JavaIODeveloperDAOImpl implements DeveloperDAO {

    public static final String JDBC_DRIVER = "org.postgresql.Driver";
    public static final String URL_DATABASE = "jdbc:postgresql://localhost:5432/learndb";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "Unow6457773";

    private static final String INSERT_NEW_DEVELOPER = "INSERT INTO developers VALUES(?,?,?,?,?)";
    public static final String SHOW_DEVELOPER = "SELECT * FROM developers WHERE id=?";
    private static final String SHOW_ALL_DEVELOPERS = "SELECT * FROM developers";
    private static final String UPDATE_DEVELOPER = "UPDATE developers SET name=?, specialization=?, experience=?, salary=? WHERE id=?";
    private static final String DELETE_DEVELOPER = "DELETE FROM developers WHERE id=?";

    @Override
    public void create(Developer developer) {
        Connection connection =  null;
        PreparedStatement preparedStatement = null;

        Integer id = developer.getId();
        String name = developer.getName();
        String specialization = developer.getSpecialization();
        Integer experience = developer.getExperience();
        Integer salary = developer.getSalary();

        Skill skill;
        Project project;



        Set<Skill> skills = new LinkedHashSet<>();
        Set<Project> projects = new LinkedHashSet<>();



        try {

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL_DATABASE, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(INSERT_NEW_DEVELOPER);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, specialization);
            preparedStatement.setInt(4, experience);
            preparedStatement.setInt(5, salary);

            preparedStatement.executeUpdate();

            System.out.println("Developer has created.");

        } catch (ClassNotFoundException e) {
            System.out.print("Driver not found: ");
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
    public void read(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement prepStatReadDevelopersSkills = null;
        PreparedStatement prepStatReadProjects = null;

        Skill skill;
        Project project;

        Set<Skill> skills = new LinkedHashSet<>();
        Set<Project> projects = new LinkedHashSet<>();

        try {

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL_DATABASE, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(SHOW_DEVELOPER);
            preparedStatement.setInt(1, id);

            prepStatReadDevelopersSkills = connection.prepareStatement(DBConnectionDAO.SHOW_DEVELOPER_SKILLS);
            prepStatReadDevelopersSkills.setInt(1, id);

            prepStatReadProjects = connection.prepareStatement(DBConnectionDAO.SHOW_PROJECTS_DEVELOPERS);
            prepStatReadProjects.setInt(1, id);

            ResultSet resultSetDeveloper = preparedStatement.executeQuery();
            ResultSet resultSetDevelopersSkills = prepStatReadDevelopersSkills.executeQuery();
            ResultSet resultSetProjectsDeveloper = prepStatReadProjects.executeQuery();

            while(resultSetDeveloper.next()) {
                int developerId = resultSetDeveloper.getInt("id");

                if (developerId == id) {

                    String name = resultSetDeveloper.getString("name");
                    String specialization = resultSetDeveloper.getString("specialization");
                    Integer experience = resultSetDeveloper.getInt("experience");
                    Integer salary = resultSetDeveloper.getInt("salary");

                    while(resultSetDevelopersSkills.next()) {
                        int developerID = resultSetDevelopersSkills.getInt("developer_id");
                        int skillID = resultSetDevelopersSkills.getInt("skill_id");

                        if(developerID == id) {
                            PreparedStatement prepStatSkills;
                            prepStatSkills = connection.prepareStatement(JavaIOSkillDAOImpl.SHOW_SKILL);
                            prepStatSkills.setInt(1, skillID);
                            ResultSet resultSet = prepStatSkills.executeQuery();

                            while(resultSet.next()) {
                                Integer skillId = resultSet.getInt("id");
                                String skillName = resultSet.getString("name");

                                if(skillID == skillId) {
                                    skill = new Skill(skillId, skillName);
                                    skills.add(skill);
                                    skill = null;
                                }
                            }
                            resultSet.close();
                        }
                    }

                    while(resultSetProjectsDeveloper.next()) {
                        int developerID = resultSetProjectsDeveloper.getInt("developer_id");
                        int projectID = resultSetProjectsDeveloper.getInt("project_id");

                        if(developerID == id) {
                            PreparedStatement prepStatProjects;
                            prepStatProjects = connection.prepareStatement(JavaIOProjectDAOImpl.SHOW_PROJECT);
                            prepStatProjects.setInt(1, projectID);
                            ResultSet resultSet = prepStatProjects.executeQuery();

                            while(resultSet.next()) {
                                Integer projectId = resultSet.getInt("id");
                                String projectName = resultSet.getString("name");
                                String projectVersion = resultSet.getString("version");
                                Integer projectCost = resultSet.getInt("cost");

                                if(projectID == projectId) {
                                    project = new Project(projectId, projectName, projectVersion, projectCost);
                                    projects.add(project);
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
                    for(Skill s:skills) {
                        System.out.println("------" + s.getName());
                    }
                    System.out.println("Projects: ");
                    for(Project p:projects) {
                        System.out.println("-------" + p.getName());
                    }
                    System.out.println("==============");
                    System.out.println();
                }

            }
            resultSetDeveloper.close();

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

    public Developer readDeveloper(int developerId) {
        Connection connection = null;
        PreparedStatement psReadDeveloper = null;

        Integer id = null;
        String name = null;
        String specialization = null;
        Integer experience = null;
        Integer salary = null;

        try {
            Class.forName(DBConnectionDAO.JDBC_DRIVER);
            connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
            psReadDeveloper = connection.prepareStatement(SHOW_DEVELOPER);

            psReadDeveloper.setInt(1, developerId);
            ResultSet rsReadDeveloper = psReadDeveloper.executeQuery();

            while(rsReadDeveloper.next()) {
                id = rsReadDeveloper.getInt("id");
                name = rsReadDeveloper.getString("name");
                specialization = rsReadDeveloper.getString("specialization");
                experience = rsReadDeveloper.getInt("experience");
                salary = rsReadDeveloper.getInt("salary");
            }
            rsReadDeveloper.close();
        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                psReadDeveloper.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Developer developer = new Developer(id, name, specialization, experience, salary);

        return developer;
    }

    public void readListOfProjects(int developerID) {
        Connection connection = null;
        PreparedStatement psProjectsDevelopers = null;
        PreparedStatement psProjects = null;

        Project project;
        Set<Project> projects = new LinkedHashSet<>();

        try {
            Class.forName(DBConnectionDAO.JDBC_DRIVER);
            connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
            psProjectsDevelopers = connection.prepareStatement(DBConnectionDAO.SHOW_PROJECTS_DEVELOPERS);
            psProjectsDevelopers.setInt(1, developerID);
            ResultSet rsProjectsDevelopers = psProjectsDevelopers.executeQuery();

            while (rsProjectsDevelopers.next()) {
                Integer projectId = rsProjectsDevelopers.getInt("project_id");
                Integer developerId = rsProjectsDevelopers.getInt("developer_id");

                if (developerId == developerID) {
                    psProjects = connection.prepareStatement(JavaIOProjectDAOImpl.SHOW_PROJECT);
                    psProjects.setInt(1, projectId);
                    ResultSet rsProjects = psProjects.executeQuery();

                    while (rsProjects.next()) {
                        Integer projectID = rsProjects.getInt("id");

                        if (projectID == projectId) {
                            String projectName = rsProjects.getString("name");
                            String projectVersion =rsProjects.getString("version");
                            Integer projectCost = rsProjects.getInt("cost");

                            project = new Project(projectID, projectName, projectVersion, projectCost);
                            projects.add(project);
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
            rsProjectsDevelopers.close();
            projects.clear();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                psProjectsDevelopers.close();
                psProjects.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void readListOfSkills(int developerID) {
        Connection connection = null;
        PreparedStatement psDevelopersSkills = null;
        PreparedStatement psSkills = null;

        Skill skill;
        Set<Skill> skills = new LinkedHashSet<>();

        try {
            Class.forName(DBConnectionDAO.JDBC_DRIVER);
            connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
            psDevelopersSkills = connection.prepareStatement(DBConnectionDAO.SHOW_DEVELOPER_SKILLS);
            psDevelopersSkills.setInt(1, developerID);
            ResultSet rsDevelopersSkills = psDevelopersSkills.executeQuery();

            while (rsDevelopersSkills.next()) {
                Integer developerId = rsDevelopersSkills.getInt("developer_id");
                Integer skillId = rsDevelopersSkills.getInt("skill_id");

                if (developerId == developerID) {
                    psSkills = connection.prepareStatement(JavaIOSkillDAOImpl.SHOW_SKILL);
                    psSkills.setInt(1, skillId);
                    ResultSet rsSkills = psSkills.executeQuery();

                    while (rsSkills.next()) {
                        Integer skillID = rsSkills.getInt("id");

                        if (skillID == skillId) {
                            String skillName = rsSkills.getString("name");

                            skill = new Skill(skillID, skillName);
                            skills.add(skill);
                        }
                    }
                    rsSkills.close();
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
            rsDevelopersSkills.close();
            skills.clear();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                psDevelopersSkills.close();
                psSkills.close();
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

        try {

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL_DATABASE, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(SHOW_ALL_DEVELOPERS);
            ResultSet resultSet = preparedStatement.executeQuery();

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
            resultSet.close();

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
                System.out.println("Database error: ");
                e.printStackTrace();
            }

        }

    }

    @Override
    public void update(Developer developer) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement prepStatConnectToSkills = null;
        PreparedStatement prepStatConnectToProjects = null;

        Integer id = developer.getId();
        String name = developer.getName();
        String specialization = developer.getSpecialization();
        Integer experience = developer.getExperience();
        Integer salary = developer.getSalary();

        try {

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL_DATABASE, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(UPDATE_DEVELOPER);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, specialization);
            preparedStatement.setInt(3, experience);
            preparedStatement.setInt(4, salary);
            preparedStatement.setInt(5, id);

            preparedStatement.executeUpdate();

            System.out.println("Developer has updated.");


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
                System.out.println("Can't close connection: ");
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
            preparedStatement = connection.prepareStatement(DELETE_DEVELOPER);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            System.out.println("Developer with id = " + id + " has deleted");

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
                System.out.println("Can't close connection: " + e);
            }

        }

    }
}
