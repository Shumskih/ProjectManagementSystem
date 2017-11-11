package dao;

import model.Skill;

import java.sql.*;

public class JavaIOSkillDAOImpl implements SkillDAO {
    private DBConnectionDAOImpl dbConnectionDAO = new DBConnectionDAOImpl();

    public static final String INSERT_NEW_SKILL = "INSERT INTO skills VALUES(?,?)";
    public static final String SHOW_SKILL = "SELECT * FROM skills WHERE id=?";
    public static final String SHOW_ALL_SKILLS = "SELECT * FROM skills";
    public static final String UPDATE_SKILL = "UPDATE skills SET name=? WHERE id=?";
    public static final String DELETE_SKILL = "DELETE FROM skills WHERE id=?";

    @Override
    public void create(Skill skill) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Integer id = skill.getId();
        String name = skill.getName();

        try {
            connection = dbConnectionDAO.getDBConnection();
            preparedStatement = connection.prepareStatement(INSERT_NEW_SKILL);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);

            preparedStatement.executeUpdate();

            System.out.println("Skill has created!");
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

    @Override
    public void read(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dbConnectionDAO.getDBConnection();
            preparedStatement = connection.prepareStatement(SHOW_SKILL);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int skillId = resultSet.getInt("id");

                if (skillId == id) {
                    String name = resultSet.getString("name");

                    System.out.println();
                    System.out.println("==========");
                    System.out.println("ID: " + skillId + "\n" +
                            "Name: " + name);
                    System.out.println("==========");
                    System.out.println();
                }
            }

            resultSet.close();
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

    @Override
    public void readAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dbConnectionDAO.getDBConnection();
            preparedStatement = connection.prepareStatement(SHOW_ALL_SKILLS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                System.out.println("================");
                System.out.println("ID: " + id + "\n" +
                        "Name: " + name);
            }

            resultSet.close();
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

    @Override
    public void update(Skill skill) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Integer id = skill.getId();
        String name = skill.getName();

        try {
            connection = dbConnectionDAO.getDBConnection();
            preparedStatement = connection.prepareStatement(UPDATE_SKILL);
            preparedStatement.setInt(2, id);
            preparedStatement.setString(1, name);

            preparedStatement.executeUpdate();

            System.out.println("Skill has changed.");
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
            connection = dbConnectionDAO.getDBConnection();
            preparedStatement = connection.prepareStatement(DELETE_SKILL);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            System.out.println("Skill with id = " + id + " has deleted");
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
