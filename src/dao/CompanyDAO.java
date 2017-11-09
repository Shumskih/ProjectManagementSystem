package dao;

import model.Company;

public interface CompanyDAO {
    void create(Company company);

    void read(int id);

    void readAll();

    void update(Company company);

    void delete(int id);
}
