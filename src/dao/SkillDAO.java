package dao;

import model.Skill;

public interface SkillDAO {
    void create(Skill skill);

    void read(int id);

    void readAll();

    void update(Skill skill);

    void delete(int id);
}
