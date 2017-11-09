package dao;

import model.Project;

public interface ProjectDAO {
    void create(Project project);

    void read(int id);

    void readAll();

    void update(Project project);

    void delete(int id);
}
