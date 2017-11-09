package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionDAO {
    public static final String JDBC_DRIVER = "org.postgresql.Driver";
    public static final String URL_DATABASE = "jdbc:postgresql://localhost:5432/learndb";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "Unow6457773";

    public static final String INSERT_NEW_DEVELOPER_SKILLS = "INSERT INTO developers_skills VALUES(?,?)";
    public static final String INSERT_NEW_COMPANIES_PROJECTS = "INSERT INTO companies_projects VALUES(?,?)";
    public static final String INSERT_NEW_CUSTOMERS_PROJECTS = "INSERT INTO customers_projects VALUES(?,?)";
    public static final String INSERT_NEW_PROJECTS_DEVELOPERS = "INSERT INTO projects_developers VALUES(?,?)";
    public static final String SHOW_DEVELOPER_SKILLS = "SELECT * FROM developers_skills WHERE developer_id=?";
    public static final String SHOW_PROJECTS_DEVELOPERS = "SELECT * FROM projects_developers WHERE developer_id=?";
    public static final String SHOW_COMPANIES_PROJECTS = "SELECT * FROM companies_projects WHERE company_id=?";
    public static final String SHOW_CUSTOMERS_PROJECTS = "SELECT * FROM customers_projects WHERE customer_id=?";
    public static final String DELETE_COMPANIES_PROJECTS = "DELETE FROM companies_projects WHERE project_id=?";
    public static final String DELETE_CUSTOMERS_PROJECTS = "DELETE FROM customers_projects WHERE project_id=?";
    public static final String DELETE_COMPANY_FROM_COMPANIES_PROJECTS = "DELETE FROM companies_projects WHERE company_id=?";
    public static final String INSERT_CUSTOMERS_PROJECTS = "INSERT INTO customers_projects VALUES(?,?)";
    public static final String SELECT_CUSTOMERS_FROM_CUSTOMERS_PROJECTS = "SELECT * FROM customers_projects WHERE customer_id=?";
    public static final String DELETE_CUSTOMER_FROM_CUSTOMERS_PROJECTS = "DELETE FROM customers_projects WHERE customer_id=?";

    Connection connection = null;

    public void dbConnect() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL_DATABASE, USERNAME, PASSWORD);
        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}