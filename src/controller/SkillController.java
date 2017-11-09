package controller;

import dao.JavaIOSkillDAOImpl;
import model.Skill;

public class SkillController {
    private JavaIOSkillDAOImpl javaIOSkillDAOImpl = new JavaIOSkillDAOImpl();

    public void create(Skill skill) {
        javaIOSkillDAOImpl.create(skill);
    }

    public void read(int id) {
        javaIOSkillDAOImpl.read(id);
    }

    public void readAll() {
        javaIOSkillDAOImpl.readAll();
    }

    public void update(Skill skill) {
        javaIOSkillDAOImpl.update(skill);
    }

    public void delete(int id) {
        javaIOSkillDAOImpl.delete(id);
    }
}
