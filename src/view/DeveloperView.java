package view;

import controller.DeveloperController;
import controller.ProjectController;
import controller.SkillController;
import dao.DBConnectionDAO;
import dao.JavaIODeveloperDAOImpl;
import dao.JavaIOProjectDAOImpl;
import dao.JavaIOSkillDAOImpl;
import model.Developer;
import model.Project;
import model.Skill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class DeveloperView {
    private DeveloperController developerController = new DeveloperController();
    private SkillController skillController = new SkillController();
    private ProjectController projectController = new ProjectController();
    private DBConnectionDAO dbConnectionDAO = new DBConnectionDAO();

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private Integer developerId;
    private String developerName;
    private String developerSpecialization;
    private Integer developerExperience;
    private Integer developerSalary;

    private Set<Skill> skills = new LinkedHashSet<>();
    private Skill skill;
    private Set<Project> projects = new LinkedHashSet<>();
    private Project project;

    private String userInput;

    private static final String INSERT_NEW_DEVELOPER_SKILLS = "INSERT INTO developers_skills VALUES(?,?)";
    private static final String INSERT_NEW_PROJECTS_DEVELOPERS = "INSERT INTO projects_developers VALUES(?,?)";

    public void createDeveloper() {
        boolean exit = false;

        try {
            do {
                do {
                    System.out.println("Enter developer's ID or c to cancel:");
                    userInput = br.readLine().trim().toLowerCase();
                    if (userInput.equals("c")) {
                        returnToMainMenuBar();
                        exit = true;
                        break;
                    } else {
                        developerId = Integer.parseInt(userInput);
                    }
                    break;
                } while (true);

                do {
                    if (!exit) {
                        System.out.println("Enter developer's name or c to cancel:");
                        userInput = br.readLine().trim().toLowerCase();
                        if (userInput.equals("c")) {
                            returnToMainMenuBar();
                            exit = true;
                            break;
                        } else {
                            developerName = userInput;
                        }
                        break;
                    } else {
                        returnToMainMenuBar();
                        break;
                    }
                } while (true);

                do {
                    if (!exit) {
                        System.out.println("Enter developer's specialization or c to cancel:");
                        userInput = br.readLine().trim();
                        if (userInput.equals("c")) {
                            returnToMainMenuBar();
                            exit = true;
                            break;
                        } else {
                            developerSpecialization = userInput;
                        }
                        break;
                    } else {
                        returnToMainMenuBar();
                        break;
                    }
                } while (true);

                do {
                    if (!exit) {
                        System.out.println("Enter developer's experience or c to cancel:");
                        userInput = br.readLine().trim();
                        if (userInput.equals("c")) {
                            returnToMainMenuBar();
                            exit = true;
                            break;
                        } else {
                            developerExperience = Integer.parseInt(userInput);
                        }
                        break;
                    } else {
                        break;
                    }
                } while (true);

                do {
                    if (!exit) {
                        System.out.println("Enter developer's salary or c to cancel:");
                        userInput = br.readLine().trim();
                        if (userInput.equals("c")) {
                            returnToMainMenuBar();
                            exit = true;
                            break;
                        } else {
                            developerSalary = Integer.parseInt(userInput);
                        }
                        break;
                    } else {
                        break;
                    }
                } while (true);

                if (!exit) {
                    Developer developer = new Developer(developerId, developerName, developerSpecialization, developerExperience, developerSalary);
                    developerController.create(developer);
                }

                do {
                    System.out.println("Add skill to developer? y = yes, n = no:");
                    userInput = br.readLine().trim().toLowerCase();
                    if (userInput.equals("n")) {
                        returnToMainMenuBar();
                        exit = true;
                        break;
                    } else {
                        System.out.println("There is list of skills:");
                        System.out.println("------------------------");

                        try {
                            Thread.currentThread().sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        skillController.readAll();


                        do {
                            System.out.println("Enter ID of skill you're going to add:");
                            userInput = br.readLine().trim().toLowerCase();

                            try {
                                Class.forName(JavaIODeveloperDAOImpl.JDBC_DRIVER);
                                Connection connection = DriverManager.getConnection(JavaIODeveloperDAOImpl.URL_DATABASE, JavaIODeveloperDAOImpl.USERNAME, JavaIODeveloperDAOImpl.PASSWORD);
                                PreparedStatement prepStatReadSkills = connection.prepareStatement(JavaIOSkillDAOImpl.SHOW_SKILL);
                                prepStatReadSkills.setInt(1, Integer.parseInt(userInput));
                                ResultSet resultSet = prepStatReadSkills.executeQuery();

                                while(resultSet.next()) {
                                    int skillId = resultSet.getInt("id");

                                    if (skillId == Integer.parseInt(userInput)) {
                                        String skillName = resultSet.getString("name");

                                        skill = new Skill(skillId, skillName);
                                        skills.add(skill);
                                    }
                                }

                                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_DEVELOPER_SKILLS);
                                preparedStatement.setInt(1, developerId);
                                preparedStatement.setInt(2, Integer.parseInt(userInput));

                                preparedStatement.executeUpdate();

                                resultSet.close();
                                prepStatReadSkills.close();
                                connection.close();
                            } catch (ClassNotFoundException | SQLException e) {
                                e.printStackTrace();
                            }

                            System.out.println("Add another one skill? y = yes, n = no:");
                            userInput = br.readLine().trim().toLowerCase();
                            if(userInput.equals("n")) {
                                returnToMainMenuBar();
                                exit = true;
                                break;
                            }
                        } while(!exit);
                        break;
                    }
                } while (true);

                do {
                    System.out.println("Add project to developer? y = yes, n = no:");
                    userInput = br.readLine().trim().toLowerCase();
                    if (userInput.equals("n")) {
                        Developer developer = new Developer(developerId, developerName, developerSpecialization, developerExperience, developerSalary, skills);
                        developerController.update(developer);
                        System.out.println("Developer updated");
                        exit = true;
                        returnToMainMenuBar();
                        break;
                    } else {
                        System.out.println("There is list of projects:");
                        System.out.println("--------------------------");

                        try {
                            Thread.currentThread().sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        projectController.readAll();


                        do {
                            System.out.println("Enter ID of project you're going to add:");
                            userInput = br.readLine().trim().toLowerCase();

                            try {
                                Class.forName(DBConnectionDAO.JDBC_DRIVER);
                                Connection connection = DriverManager.getConnection(DBConnectionDAO.URL_DATABASE, DBConnectionDAO.USERNAME, DBConnectionDAO.PASSWORD);
                                PreparedStatement prepStatReadProjects = connection.prepareStatement(JavaIOProjectDAOImpl.SHOW_PROJECT);
                                prepStatReadProjects.setInt(1, Integer.parseInt(userInput));
                                ResultSet resultSet = prepStatReadProjects.executeQuery();

                                while(resultSet.next()) {
                                    int projectId = resultSet.getInt("id");

                                    if (projectId == Integer.parseInt(userInput)) {
                                        String projectName = resultSet.getString("name");
                                        String projectVersion = resultSet.getString("version");
                                        Integer projectCost = resultSet.getInt("cost");

                                        project = new Project(projectId, projectName, projectVersion, projectCost);
                                        projects.add(project);
                                    }
                                }

                                Developer developer = new Developer(developerId, developerName, developerSpecialization, developerExperience, developerSalary, skills, projects);
                                developerController.update(developer);
                                System.out.println("Developer updated");

                                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_PROJECTS_DEVELOPERS);
                                preparedStatement.setInt(1, Integer.parseInt(userInput));
                                preparedStatement.setInt(2, developerId);




                                preparedStatement.executeUpdate();

                                resultSet.close();
                                prepStatReadProjects.close();
                                connection.close();


                            } catch (ClassNotFoundException | SQLException e) {
                                e.printStackTrace();
                            }

                            System.out.println("Add another one project? y = yes, n = no:");
                            userInput = br.readLine().trim().toLowerCase();
                            if(userInput.equals("n")) {
                                exit = true;
                                skills.clear();
                                skill = null;
                                projects.clear();
                                project = null;
                                returnToMainMenuBar();
                                break;
                            }
                        } while(!exit);
                        break;
                    }
                } while (true);

            } while (!exit);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showDeveloperById() {
        try {
            do {
                System.out.println("Enter developer's ID or c to cancel: ");
                userInput = br.readLine().trim();
                System.out.println();
                if(!userInput.equals("c")) {
                    developerController.read(Integer.parseInt(userInput));
                    System.out.println();
                    returnToMainMenuBar();
                    break;
                } else {
                    System.out.println();
                    returnToMainMenuBar();
                    break;
                }
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAllDevelopers() {
        developerController.readAll();
    }

    public void updateDeveloper() {
        boolean exit = false;

        String userInputDevName;
        String userInputDevSpecialization;
        Integer userInputDevExperience;
        Integer userInputDevSalary;

        try {
            do {
                System.out.println("Enter developer's ID you are going to update or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();
                if(userInput.equals("c")) {
                    break;
                } else {
                    Integer id = Integer.parseInt(userInput);
                    System.out.println("There is a developer you are going to update:");
                    System.out.println("---------------------------------------------");
                    developerController.read(id);

                    do {
                        System.out.println("Change: ");
                        System.out.println("1.     name?");
                        System.out.println("2.     specialization?");
                        System.out.println("3.     experience?");
                        System.out.println("4.     salary?");
                        System.out.println("5. Change all fields?");
                        System.out.println("6. Cancel");

                        userInput = br.readLine().trim().toLowerCase();

                        if(userInput.equals("6")) {
                            break;
                        } else {
                            switch (userInput) {
                                case "1":
                                    System.out.println("Enter new developer's name or c to cancel:");
                                    userInput = br.readLine().trim().toLowerCase();
                                    if(userInput.equals("c")) {
                                        break;
                                    } else {
                                        userInputDevName = userInput;

                                        Developer developer = new Developer(developerId, userInputDevName, developerSpecialization, developerExperience, developerSalary);
                                        developerController.update(developer);
                                        break;
                                    }
                                case "2":
                                    System.out.println("Enter new developer's specialization or c to cancel:");
                                    userInput = br.readLine().trim();
                                    if(userInput.equals("c")) {
                                        break;
                                    } else {
                                        userInputDevSpecialization = userInput;

                                        Developer developer = new Developer(developerId, developerName, userInputDevSpecialization, developerExperience, developerSalary);
                                        developerController.update(developer);
                                        break;
                                    }
                                case "3":
                                    System.out.println("Enter new developer's experience or c to cancel:");
                                    userInput = br.readLine().trim();
                                    if(userInput.equals("c")) {
                                        break;
                                    } else {
                                        userInputDevExperience = Integer.parseInt(userInput);

                                        Developer developer = new Developer(developerId, developerName, developerSpecialization, userInputDevExperience, developerSalary);
                                        developerController.update(developer);
                                        break;
                                    }
                                case "4":
                                    System.out.println("Enter new developer's salary or c to cancel:");
                                    userInput = br.readLine().trim();
                                    if(userInput.equals("c")) {
                                        break;
                                    } else {
                                        userInputDevSalary = Integer.parseInt(userInput);

                                        Developer developer = new Developer(developerId, developerName, developerSpecialization, developerExperience, userInputDevSalary);
                                        developerController.update(developer);
                                        break;
                                    }
                                case "5":
                                    do {
                                        System.out.println("Enter developer's name or c to cancel:");
                                        userInput = br.readLine().trim().toLowerCase();
                                        if (userInput.equals("c")) {
                                            exit = true;
                                            break;
                                        } else {
                                            developerName = userInput;
                                            break;
                                        }
                                    } while(true);

                                    do {
                                        if(!exit) {
                                            System.out.println("Enter developer's specialization:");
                                            userInput = br.readLine().trim().toLowerCase();
                                            if(userInput.equals("c")) {
                                                exit = true;
                                                break;
                                            } else {
                                                developerSpecialization = userInput;
                                                break;
                                            }
                                        } else {
                                            break;
                                        }
                                    } while(true);

                                    do {
                                        if(!exit) {
                                            System.out.println("Enter developer's experience:");
                                            userInput = br.readLine().trim().toLowerCase();
                                            if(userInput.equals("c")) {
                                                exit = true;
                                                break;
                                            } else {
                                                developerExperience = Integer.parseInt(userInput);
                                                break;
                                            }
                                        } else {
                                            break;
                                        }
                                    } while(true);

                                    do {
                                        if(!exit) {
                                            System.out.println("Enter developer's salary:");
                                            userInput = br.readLine().trim().toLowerCase();
                                            if(userInput.equals("c")) {
                                                break;
                                            } else {
                                                developerSalary = Integer.parseInt(userInput);
                                                break;
                                            }
                                        } else {
                                            break;
                                        }
                                    } while(true);

                                    Developer developer = new Developer(developerId, developerName, developerSpecialization, developerExperience, developerSalary);
                                    developerController.update(developer);
                                    break;
                            }
                        }
                        break;
                    }  while(!exit);
                }
                break;
            } while(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDeveloper() {
        String userInput;
        boolean exit = false;

        try {
            do {
                System.out.println("Enter developer's ID you are going to delete or c to cancel:");
                userInput = br.readLine().trim();
                if(!userInput.equals("c")) {
                    developerController.delete(Integer.parseInt(userInput));
                    break;
                } else {
                    break;
                }
            } while(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void returnToMainMenuBar() {
        try {
            System.out.print("Returning to main menu.");
            Thread.currentThread().sleep(300);
            System.out.print(".");
            Thread.currentThread().sleep(300);
            System.out.print(".");
            Thread.currentThread().sleep(300);
            System.out.print(".");
            Thread.currentThread().sleep(300);
            System.out.print(".");
            Thread.currentThread().sleep(300);
            System.out.print(".");
            Thread.currentThread().sleep(300);
            System.out.print(".");
            Thread.currentThread().sleep(300);
            System.out.println();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
