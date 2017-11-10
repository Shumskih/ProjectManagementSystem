package dao;

import java.sql.*;

public class DeveloperSkillsDAO {

    private Connection connection = null;
    private PreparedStatement PSDevelopersSkills = null;

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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
