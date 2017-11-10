package controller;

import dao.JavaIODeveloperDAOImpl;
import model.Developer;

public class DeveloperController {
    private JavaIODeveloperDAOImpl javaIODeveloperDAOImpl = new JavaIODeveloperDAOImpl();

    public void create(Developer developer) {
        javaIODeveloperDAOImpl.create(developer);
    }

    public void read(int id) {
        javaIODeveloperDAOImpl.read(id);
    }

    public void readListOfProjects(int developerId) {
        javaIODeveloperDAOImpl.readListOfProjects(developerId);
    }

    public void readListOfSkills(int developerId) {
        javaIODeveloperDAOImpl.readListOfSkills(developerId);
    }

    public void readAll() { javaIODeveloperDAOImpl.readAll(); }

    public void update(Developer developer) {
        javaIODeveloperDAOImpl.update(developer);
    }

    public void delete(int id) {
        javaIODeveloperDAOImpl.delete(id);
    }
}
