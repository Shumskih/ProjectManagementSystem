package dao;

import java.sql.*;

public class DeveloperSkillsDAO {
    private DBConnectionDAOImpl dbConnectionDAO = new DBConnectionDAOImpl();

    private Connection connection = null;
    private PreparedStatement PSDevelopersSkills = null;
    private PreparedStatement psDevelopersSkills = null;

    public void insert(int developerId, int skillId) {
        try {
            connection = dbConnectionDAO.getDBConnection();
            PSDevelopersSkills = connection.prepareStatement(DBConnectionDAOImpl.INSERT_NEW_DEVELOPER_SKILLS);

            PSDevelopersSkills.setInt(1, developerId);
            PSDevelopersSkills.setInt(2, skillId);

            PSDevelopersSkills.executeUpdate();
            System.out.println("Skill has added");
        } catch (SQLException e) {
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
            connection = dbConnectionDAO.getDBConnection();
            psDevelopersSkills = connection.prepareStatement(DBConnectionDAOImpl.DELETE_SKILL_FROM_DEVELOPERS_SKILLS);

            psDevelopersSkills.setInt(1, skillId);

            psDevelopersSkills.executeUpdate();
            System.out.println("Project has deleted");
        } catch (SQLException e) {
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
