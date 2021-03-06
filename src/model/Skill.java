package model;

public class Skill {
    Integer id;
    String name;

    public Skill(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        System.out.println();
        return "==========" + "\n" +
                "ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "==========" + "\n";
    }
}
