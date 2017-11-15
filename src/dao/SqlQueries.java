package dao;

public class SqlQueries {
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

    public static final String INSERT_NEW_COMPANY = "INSERT INTO companies VALUES(?,?)";
    public static final String SHOW_COMPANY = "SELECT * FROM companies WHERE id=?";
    public static final String SHOW_ALL_COMPANIES = "SELECT * FROM companies";
    public static final String UPDATE_COMPANY = "UPDATE companies SET name=? WHERE id=?";
    public static final String DELETE_COMPANY   = "DELETE FROM companies WHERE id=?";

    public static final String INSERT_NEW_CUSTOMER = "INSERT INTO customers VALUES(?,?)";
    public static final String SHOW_CUSTOMER = "SELECT * FROM customers WHERE id=?";
    public static final String SHOW_ALL_CUSTOMERS = "SELECT * FROM customers";
    public static final String UPDATE_CUSTOMER = "UPDATE customers SET name=? WHERE id=?";
    public static final String DELETE_CUSTOMER = "DELETE FROM customers WHERE id=?";

    public static final String INSERT_NEW_PROJECT = "INSERT INTO projects VALUES(?,?,?,?)";
    public static final String SHOW_PROJECT = "SELECT * FROM projects WHERE id=?";
    public static final String SHOW_ALL_PROJECTS = "SELECT * FROM projects";
    public static final String UPDATE_PROJECT = "UPDATE projects SET name=?, version=?, cost=? WHERE id=?";
    public static final String DELETE_PROJECT = "DELETE FROM projects WHERE id=?";

    public static final String INSERT_NEW_SKILL = "INSERT INTO skills VALUES(?,?)";
    public static final String SHOW_SKILL = "SELECT * FROM skills WHERE id=?";
    public static final String SHOW_ALL_SKILLS = "SELECT * FROM skills";
    public static final String UPDATE_SKILL = "UPDATE skills SET name=? WHERE id=?";
    public static final String DELETE_SKILL = "DELETE FROM skills WHERE id=?";

    public static final String INSERT_NEW_DEVELOPER = "INSERT INTO developers VALUES(?,?,?,?,?)";
    public static final String SHOW_DEVELOPER = "SELECT * FROM developers WHERE id=?";
    public static final String SHOW_ALL_DEVELOPERS = "SELECT * FROM developers";
    public static final String UPDATE_DEVELOPER = "UPDATE developers SET name=?, specialization=?, experience=?, salary=? WHERE id=?";
    public static final String DELETE_DEVELOPER = "DELETE FROM developers WHERE id=?";
}
