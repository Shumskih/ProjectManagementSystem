package dao;

import model.Developer;

public interface DeveloperDAO {
    void create(Developer developer);

    void read(int id);

    void readAll();

    void update(Developer developer);

    void delete(int id);
}
