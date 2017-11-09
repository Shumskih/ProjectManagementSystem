package dao;

import model.Customer;

public interface CustomerDAO {
    void create(Customer customer);

    void read(int id);

    void readAll();

    void update(Customer customer);

    void delete(int id);
}
