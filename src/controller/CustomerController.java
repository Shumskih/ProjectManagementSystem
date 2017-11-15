package controller;

import dao.jdbc.JdbcCustomerDAOImpl;
import model.Customer;

public class CustomerController {
    private JdbcCustomerDAOImpl jdbcCustomerDAO = new JdbcCustomerDAOImpl();

    public void save(Customer customer) {
        jdbcCustomerDAO.save(customer);
    }

    public void getById(int id) {
        jdbcCustomerDAO.getById(id);
    }

    public void getListOfProjects(int customerId) {
        jdbcCustomerDAO.getListOfProjects(customerId);
    }

    public void getAll() {
        jdbcCustomerDAO.getAll();
    }

    public void update(Customer customer) {
        jdbcCustomerDAO.update(customer);
    }

    public void delete(int id) {
        jdbcCustomerDAO.delete(id);
    }

    public void insertCustProj(int customerId, int projectId) {
        jdbcCustomerDAO.insertCustProj(customerId, projectId);
    }

    public void deleteByProject(int projectId) {
        jdbcCustomerDAO.deleteByProject(projectId);
    }

    public void deleteByCustomer(int customerId) {
        jdbcCustomerDAO.deleteByCustomer(customerId);
    }
}
