package controller;

import dao.jdbc.JdbcSkillDAOImpl;
import model.Skill;

public class SkillController {
    private JdbcSkillDAOImpl jdbcSkillDAO = new JdbcSkillDAOImpl();

    public void save(Skill skill) {
        jdbcSkillDAO.save(skill);
    }

    public Skill getById(int id) {
        Skill skill;
        skill = jdbcSkillDAO.getById(id);
        return skill;
    }

    public void getAll() {
        jdbcSkillDAO.getAll();
    }

    public void update(Skill skill) {
        jdbcSkillDAO.update(skill);
    }

    public void delete(int id) {
        jdbcSkillDAO.delete(id);
    }
}
