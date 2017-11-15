package controller;

import dao.jdbc.JdbcProjectDAOImpl;
import model.Project;

public class ProjectController {
    private JdbcProjectDAOImpl jdbcIOProjectDAO = new JdbcProjectDAOImpl();

    public void save(Project project) {
        jdbcIOProjectDAO.save(project);
    }

    public Project getById(int id) {
        Project project;
        project = jdbcIOProjectDAO.getById(id);
        return project;
    }

    public void getAll() {
        jdbcIOProjectDAO.getAll();
    }

    public void update(Project project) {
        jdbcIOProjectDAO.update(project);
    }

    public void delete(int id) {
       jdbcIOProjectDAO.delete(id);
    }
}
