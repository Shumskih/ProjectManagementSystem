package model;

import java.util.Set;

public class Developer {
    private Integer id;
    private String name;
    private String specialization;
    private Integer experience;
    private Integer salary;

    private Set<Skill> skills;
    private Set<Project> projects;

    public Developer() {

    }

    public Developer(Integer id, String name, String specialization, Integer experience, Integer salary) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.experience = experience;
        this.salary = salary;
    }

    public Developer(Integer id, String name, String specialization, Integer experience, Integer salary, Set<Skill> skills) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.experience = experience;
        this.salary = salary;
        this.skills = skills;
    }

    public Developer(Integer id, String name, String specialization, Integer experience, Integer salary, Set<Skill> skills, Set<Project> projects) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.experience = experience;
        this.salary = salary;
        this.skills = skills;
        this.projects = projects;
    }

    public int setId(Integer id) {
        return this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String setName(String name) {
        return this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String setSpecialization(String specialization) {
        return this.specialization = specialization;
    }

    public String getSpecialization() {
        return this.specialization;
    }

    public int setExperience(Integer experience) {
        return this.experience = experience;
    }

    public int getExperience() {
        return this.experience;
    }

    public int setSalary(Integer salary) {
        return this.salary = salary;
    }

    public int getSalary() {
        return this.salary;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
