package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionDAOImpl implements DBConnectionDAO  {
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String URL_DATABASE = "jdbc:postgresql://localhost:5432/learndb";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Unow6457773";

    public static final String INSERT_NEW_DEVELOPER_SKILLS = "INSERT INTO developers_skills VALUES(?,?)";
    public static final String INSERT_NEW_COMPANIES_PROJECTS = "INSERT INTO companies_projects VALUES(?,?)";
    public static final String INSERT_NEW_CUSTOMERS_PROJECTS = "INSERT INTO customers_projects VALUES(?,?)";
    public static final String INSERT_NEW_PROJECTS_DEVELOPERS = "INSERT INTO projects_developers VALUES(?,?)";
    public static final String INSERT_CUSTOMERS_PROJECTS = "INSERT INTO customers_projects VALUES(?,?)";

    public static final String SHOW_DEVELOPER_SKILLS = "SELECT * FROM developers_skills WHERE developer_id=?";
    public static final String SHOW_PROJECTS_DEVELOPERS = "SELECT * FROM projects_developers WHERE developer_id=?";
    public static final String SHOW_COMPANIES_PROJECTS = "SELECT * FROM companies_projects WHERE company_id=?";
    public static final String SHOW_CUSTOMERS_PROJECTS = "SELECT * FROM customers_projects WHERE customer_id=?";
    public static final String SHOW_SKILLS = "SELECT * FROM skills WHERE id=?";
    public static final String SHOW_PROJECTS = "SELECT * FROM projects WHERE id=?";

    public static final String DELETE_COMPANIES_PROJECTS = "DELETE FROM companies_projects WHERE project_id=?";
    public static final String DELETE_CUSTOMERS_PROJECTS = "DELETE FROM customers_projects WHERE project_id=?";
    public static final String DELETE_COMPANY_FROM_COMPANIES_PROJECTS = "DELETE FROM companies_projects WHERE company_id=?";
    public static final String DELETE_CUSTOMER_FROM_CUSTOMERS_PROJECTS = "DELETE FROM customers_projects WHERE customer_id=?";
    public static final String DELETE_PROJECT_FROM_PROJECTS_DEVELOPERS = "DELETE FROM projects_developers WHERE project_id=?";
    public static final String DELETE_SKILL_FROM_DEVELOPERS_SKILLS = "DELETE FROM developers_skills WHERE skill_id=?";

    public static final String SELECT_CUSTOMERS_FROM_CUSTOMERS_PROJECTS = "SELECT * FROM customers_projects WHERE customer_id=?";

    DBConnectionDAOImpl(){
        try {
            Class.forName(JDBC_DRIVER);
        } catch(ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
        }
    }

    public Connection getDBConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL_DATABASE, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Database connection error");
            e.printStackTrace();
        }

        return connection;
    }

    public void putConnection(Connection connection) {
        try {
            connection.close();
        } catch(SQLException e) {
            System.out.println("Close connection error");
            e.printStackTrace();
        }

    }
}
