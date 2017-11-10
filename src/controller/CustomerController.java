package controller;

import dao.JavaIOCustomerDAOImpl;
import model.Customer;

public class CustomerController {
    private JavaIOCustomerDAOImpl javaIOCustomerDAOImpl = new JavaIOCustomerDAOImpl();

    public void create(Customer customer) {
        javaIOCustomerDAOImpl.create(customer);
    }

    public void read(int id) {
        javaIOCustomerDAOImpl.read(id);
    }

    public void readListOfProjects(int customerId) {
        javaIOCustomerDAOImpl.readListOfProjects(customerId);
    }

    public void readAll() {
        javaIOCustomerDAOImpl.readAll();
    }

    public void update(Customer customer) {
        javaIOCustomerDAOImpl.update(customer);
    }

    public void delete(int id) {
        javaIOCustomerDAOImpl.delete(id);
    }
}
