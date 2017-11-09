package controller;

import dao.JavaIOCompanyDAOImpl;
import model.Company;

public class CompanyController {
    private JavaIOCompanyDAOImpl javaIOCompanyDAOImpl = new JavaIOCompanyDAOImpl();

    public void create(Company company) {
        javaIOCompanyDAOImpl.create(company);
    }

    public void read(int id) {
        javaIOCompanyDAOImpl.read(id);
    }

    public void readAll() {
        javaIOCompanyDAOImpl.readAll();
    }

    public void update(Company company) {
        javaIOCompanyDAOImpl.update(company);
    }

    public void delete(int id) {
        javaIOCompanyDAOImpl.delete(id);
    }

}
