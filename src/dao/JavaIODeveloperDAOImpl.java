package dao;

import model.Developer;
import model.Project;
import model.Skill;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class JavaIODeveloperDAOImpl implements DeveloperDAO {
    private DBConnectionDAOImpl dbConnectionDAO = new DBConnectionDAOImpl();

    private static final String INSERT_NEW_DEVELOPER = "INSERT INTO developers VALUES(?,?,?,?,?)";
    public static final String SHOW_DEVELOPER = "SELECT * FROM developers WHERE id=?";
    private static final String SHOW_ALL_DEVELOPERS = "SELECT * FROM developers";
    private static final String UPDATE_DEVELOPER = "UPDATE developers SET name=?, specialization=?, experience=?, salary=? WHERE id=?";
    private static final String DELETE_DEVELOPER = "DELETE FROM developers WHERE id=?";

    private Skill skill;
    private Project project;

    private Set<Skill> skills = new LinkedHashSet<>();
    private Set<Project> projects = new LinkedHashSet<>();

    @Override
    public void create(Developer developer) {
        Connection connection =  null;
        PreparedStatement preparedStatement = null;

        Integer id = developer.getId();
        String name = developer.getName();
        String specialization = developer.getSpecialization();
        Integer experience = developer.getExperience();
        Integer salary = developer.getSalary();

        try {
            connection = dbConnectionDAO.getDBConnection();
            preparedStatement = connection.prepareStatement(INSERT_NEW_DEVELOPER);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, specialization);
            preparedStatement.setInt(4, experience);
            preparedStatement.setInt(5, salary);

            preparedStatement.executeUpdate();

            System.out.println("Developer has created.");
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
    public void read(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement psReadDevelopersSkills = null;
        PreparedStatement psReadProjects = null;
        PreparedStatement psSkills = null;
        PreparedStatement psProjects = null;

        ResultSet rsDeveloper = null;
        ResultSet rsDevelopersSkills = null;
        ResultSet rsProjectsDeveloper = null;
        ResultSet rsSkills = null;
        ResultSet rsProjects = null;

        try {
            connection = dbConnectionDAO.getDBConnection();
            preparedStatement = connection.prepareStatement(SHOW_DEVELOPER);
            preparedStatement.setInt(1, id);

            psReadDevelopersSkills = connection.prepareStatement(DBConnectionDAOImpl.SHOW_DEVELOPER_SKILLS);
            psReadDevelopersSkills.setInt(1, id);

            psReadProjects = connection.prepareStatement(DBConnectionDAOImpl.SHOW_PROJECTS_DEVELOPERS);
            psReadProjects.setInt(1, id);

            rsDeveloper = preparedStatement.executeQuery();
            rsDevelopersSkills = psReadDevelopersSkills.executeQuery();
            rsProjectsDeveloper = psReadProjects.executeQuery();

            while(rsDeveloper.next()) {
                int developerId = rsDeveloper.getInt("id");

                if (developerId == id) {
                    String name = rsDeveloper.getString("name");
                    String specialization = rsDeveloper.getString("specialization");
                    Integer experience = rsDeveloper.getInt("experience");
                    Integer salary = rsDeveloper.getInt("salary");

                    while(rsDevelopersSkills.next()) {
                        int developerID = rsDevelopersSkills.getInt("developer_id");
                        int skillID = rsDevelopersSkills.getInt("skill_id");

                        if(developerID == id) {
                            psSkills = connection.prepareStatement(JavaIOSkillDAOImpl.SHOW_SKILL);
                            psSkills.setInt(1, skillID);
                            rsSkills = psSkills.executeQuery();

                            while(rsSkills.next()) {
                                Integer skillId = rsSkills.getInt("id");
                                String skillName = rsSkills.getString("name");

                                if(skillID == skillId) {
                                    skill = new Skill(skillId, skillName);
                                    skills.add(skill);
                                    skill = null;
                                }
                            }
                        }
                    }
                    while(rsProjectsDeveloper.next()) {
                        int developerID = rsProjectsDeveloper.getInt("developer_id");
                        int projectID = rsProjectsDeveloper.getInt("project_id");

                        if(developerID == id) {
                            psProjects = connection.prepareStatement(JavaIOProjectDAOImpl.SHOW_PROJECT);
                            psProjects.setInt(1, projectID);
                            rsProjects = psProjects.executeQuery();

                            while(rsProjects.next()) {
                                Integer projectId = rsProjects.getInt("id");
                                String projectName = rsProjects.getString("name");
                                String projectVersion = rsProjects.getString("version");
                                Integer projectCost = rsProjects.getInt("cost");

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
        } catch (SQLException e) {
            System.out.println("Database error: ");
            System.out.println("----------------");
            e.printStackTrace();
        } finally {
            try {
                if(rsDeveloper != null) {
                    rsDeveloper.close();
                }
                if(rsDevelopersSkills != null) {
                    rsDevelopersSkills.close();
                }
                if(rsProjectsDeveloper != null) {
                    rsProjectsDeveloper.close();
                }
                if(rsSkills != null) {
                    rsSkills.close();
                }
                if(rsProjects != null) {
                    rsProjects.close();
                }

                if(preparedStatement != null) {
                    preparedStatement.close();
                }
                if(psReadDevelopersSkills != null) {
                    psReadDevelopersSkills.close();
                }
                if(psReadProjects != null) {
                    psReadProjects.close();
                }
                if(psSkills != null) {
                    psSkills.close();
                }
                if(psProjects != null) {
                    psProjects.close();
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

    public Developer readDeveloper(int developerId) {
        Connection connection = null;
        PreparedStatement psReadDeveloper = null;
        ResultSet rsReadDeveloper = null;

        Developer developer;

        Integer id = null;
        String name = null;
        String specialization = null;
        Integer experience = null;
        Integer salary = null;

        try {
            connection = dbConnectionDAO.getDBConnection();
            psReadDeveloper = connection.prepareStatement(SHOW_DEVELOPER);

            psReadDeveloper.setInt(1, developerId);
            rsReadDeveloper = psReadDeveloper.executeQuery();

            while(rsReadDeveloper.next()) {
                id = rsReadDeveloper.getInt("id");
                name = rsReadDeveloper.getString("name");
                specialization = rsReadDeveloper.getString("specialization");
                experience = rsReadDeveloper.getInt("experience");
                salary = rsReadDeveloper.getInt("salary");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rsReadDeveloper != null) {
                    rsReadDeveloper.close();
                }
                if(psReadDeveloper != null) {
                    psReadDeveloper.close();
                }
                if(connection != null) {
                    dbConnectionDAO.putConnection(connection);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        developer = new Developer(id, name, specialization, experience, salary);

        return developer;
    }

    public void readListOfProjects(int developerID) {
        Connection connection = null;
        PreparedStatement psProjectsDevelopers = null;
        PreparedStatement psProjects = null;

        try {
            connection = dbConnectionDAO.getDBConnection();
            psProjectsDevelopers = connection.prepareStatement(DBConnectionDAOImpl.SHOW_PROJECTS_DEVELOPERS);
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
        } catch (SQLException e) {
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

        try {
            connection = dbConnectionDAO.getDBConnection();
            psDevelopersSkills = connection.prepareStatement(DBConnectionDAOImpl.SHOW_DEVELOPER_SKILLS);
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
        } catch (SQLException e) {
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
            connection = dbConnectionDAO.getDBConnection();
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

        Integer id = developer.getId();
        String name = developer.getName();
        String specialization = developer.getSpecialization();
        Integer experience = developer.getExperience();
        Integer salary = developer.getSalary();

        try {
            connection = dbConnectionDAO.getDBConnection();
            preparedStatement = connection.prepareStatement(UPDATE_DEVELOPER);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, specialization);
            preparedStatement.setInt(3, experience);
            preparedStatement.setInt(4, salary);
            preparedStatement.setInt(5, id);

            preparedStatement.executeUpdate();

            System.out.println("Developer has updated.");
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
            connection = dbConnectionDAO.getDBConnection();
            preparedStatement = connection.prepareStatement(DELETE_DEVELOPER);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            System.out.println("Developer with id = " + id + " has deleted");
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
