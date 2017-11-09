package controller;

import dao.JavaIOProjectDAOImpl;
import model.Project;

public class ProjectController {
    private JavaIOProjectDAOImpl javaIOProjectDAOImpl = new JavaIOProjectDAOImpl();

    public void create(Project project) {
        javaIOProjectDAOImpl.create(project);
    }

    public void read(int id) {
        javaIOProjectDAOImpl.read(id);
    }

    public void readAll() {
        javaIOProjectDAOImpl.readAll();
    }

    public void update(Project project) {
        javaIOProjectDAOImpl.update(project);
    }

    public void delete(int id) {
        javaIOProjectDAOImpl.delete(id);
    }
}
