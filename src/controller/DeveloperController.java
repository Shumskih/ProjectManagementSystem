package controller;

import dao.jdbc.JdbcDeveloperDAOImpl;
import model.Developer;

public class DeveloperController {
    private JdbcDeveloperDAOImpl jdbcDeveloperDAO = new JdbcDeveloperDAOImpl();

    public void save(Developer developer) {
        jdbcDeveloperDAO.save(developer);
    }

    public Developer getById(int id) {
        Developer developer;
        developer = jdbcDeveloperDAO.getById(id);
        return developer;
    }

    public Developer getDeveloper(int developerId) {
        return jdbcDeveloperDAO.getDeveloper(developerId);
    }

    public void getListOfProjects(int developerId) {
        jdbcDeveloperDAO.getListOfProjects(developerId);
    }

    public void getListOfSkills(int developerId) {
        jdbcDeveloperDAO.getListOfSkills(developerId);
    }

    public void getAll() {jdbcDeveloperDAO.getAll(); }

    public void update(Developer developer) {
        jdbcDeveloperDAO.update(developer);
    }

    public void delete(int id) {
        jdbcDeveloperDAO.delete(id);
    }

    public void deleteByProject(int projectId) {
        jdbcDeveloperDAO.deleteByProject(projectId);
    }

    public void insertProjDev(int projectId, int developerId) {
        jdbcDeveloperDAO.insertProjDev(projectId, developerId);
    }

    public void insertDevSkill(int developerId, int skillId) {
        jdbcDeveloperDAO.insertDevSkill(developerId, skillId);
    }

    public void deleteBySkill(int skillId) {
        jdbcDeveloperDAO.deleteBySkill(skillId);
    }
}
