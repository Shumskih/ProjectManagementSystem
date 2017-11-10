package dao;

import java.sql.*;

public class DeveloperSkillsDAO {
    private Connection connection = null;
    private PreparedStatement PSDevelopersSkills = null;
    private PreparedStatement psDevelopersSkills = null;

    public void insert(int developerId, int skillId) {
        try {
            Class.forName(JavaIODeveloperDAOImpl.JDBC_DRIVER);
            connection = DriverManager.getConnection(JavaIODeveloperDAOImpl.URL_DATABASE, JavaIODeveloperDAOImpl.USERNAME, JavaIODeveloperDAOImpl.PASSWORD);
            PSDevelopersSkills = connection.prepareStatement(DBConnectionDAO.INSERT_NEW_DEVELOPER_SKILLS);

            PSDevelopersSkills.setInt(1, developerId);
            PSDevelopersSkills.setInt(2, skillId);

            PSDevelopersSkills.executeUpdate();
            System.out.println("Skill has added");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                PSDevelopersSkills.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteBySkill(int skillId) {
        try {
            Class.forName(DBConnectionDAO.JDBC_DRIVER);
            connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
            psDevelopersSkills = connection.prepareStatement(DBConnectionDAO.DELETE_SKILL_FROM_DEVELOPERS_SKILLS);

            psDevelopersSkills.setInt(1, skillId);

            psDevelopersSkills.executeUpdate();
            System.out.println("Project has deleted");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                psDevelopersSkills.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
