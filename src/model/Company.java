package model;

import java.util.Set;

public class Company {
    Integer id;
    String name;
    Set<Project> projects;

    public Company() {

    }

    public Company(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Company(Integer id, String name, Set<Project> projects) {
        this.id = id;
        this.name = name;
        this.projects = projects;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        System.out.println("==========" +
                    "ID: " + id + "\n" +
                    "Name: " + name + "\n" +
                    "Projects: " + projects + "\n" +
                    "==========");

        return null;
    }
}
