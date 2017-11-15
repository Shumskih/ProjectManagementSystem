package controller;

import dao.jdbc.JdbcCompanyDAOImpl;
import model.Company;

public class CompanyController {
    private JdbcCompanyDAOImpl jdbcCompanyDAO = new JdbcCompanyDAOImpl();

    public void save(Company company) {
        jdbcCompanyDAO.save(company);
    }

    public void getById(int id) {
        jdbcCompanyDAO.getById(id);
    }

    public void getAll() {
        jdbcCompanyDAO.getAll();
    }

    public void update(Company company) {
        jdbcCompanyDAO.update(company);
    }

    public void delete(int id) {
        jdbcCompanyDAO.delete(id);
    }

    public void insertComProj(int companyId, int projectId) {
       jdbcCompanyDAO.insertComProj(companyId, projectId);
    }

    public void getListOfProjects(int companyId) {
        jdbcCompanyDAO.getListOfProjects(companyId);
    }

    public void deleteByProject(int projectId) {
        jdbcCompanyDAO.deleteByProject(projectId);
    }

}
