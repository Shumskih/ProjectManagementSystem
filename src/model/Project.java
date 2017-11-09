package model;

public class Project {
    Integer id;
    String name;
    String version;
    Integer cost;

    public Project(Integer id, String name, String version, Integer cost) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.cost = cost;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
