package dao.jdbc;

import dao.ConnectionUtil;
import dao.GenericDAO;
import dao.SqlQueries;
import model.Skill;

import java.sql.*;

public class JdbcSkillDAOImpl implements GenericDAO<Skill,Long> {
    private ConnectionUtil connectionUtil = new ConnectionUtil();

    @Override
    public void save(Skill skill) {
        Integer id = skill.getId();
        String name = skill.getName();

        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.INSERT_NEW_SKILL)){

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);

            preparedStatement.executeUpdate();

            System.out.println("Skill has created!");
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error: ");
            System.out.println("----------------");
            e.printStackTrace();
        }
    }

    @Override
    public Skill getById(int id) {
        Skill skill;
        Integer skillId = null;
        String name = null;

        try (Connection connection = connectionUtil.getDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.SHOW_SKILL)){

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    skillId = resultSet.getInt("id");

                    if (skillId == id) {
                        name = resultSet.getString("name");
                    }
                }
            }
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error: ");
            System.out.println("----------------");
            e.printStackTrace();
        }
        skill = new Skill(skillId, name);
        System.out.println(skill.toString());
        return skill;
    }

    @Override
    public void getAll() {
        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.SHOW_ALL_SKILLS)){

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Integer id = resultSet.getInt("id");
                    String name = resultSet.getString("name");

                    System.out.println("================");
                    System.out.println("ID: " + id + "\n" +
                            "Name: " + name);
                }
            }
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error: ");
            System.out.println("----------------");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Skill skill) {
        Integer id = skill.getId();
        String name = skill.getName();

        try (Connection connection = connectionUtil.getDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.UPDATE_SKILL)){

            preparedStatement.setInt(2, id);
            preparedStatement.setString(1, name);

            preparedStatement.executeUpdate();

            System.out.println("Skill has changed.");
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error: ");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = connectionUtil.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.DELETE_SKILL)){

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            System.out.println("Skill with id = " + id + " has deleted");
            connectionUtil.putConnection(connection);
        } catch (SQLException e) {
            System.out.println("Database error");
            e.printStackTrace();
        }
    }
}
