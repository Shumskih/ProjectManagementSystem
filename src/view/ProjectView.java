package view;

import controller.ProjectController;
import decorations.Decorations;
import model.Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProjectView {
    private ProjectController projectController = new ProjectController();

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private Integer projectId;
    private String projectName;
    private String projectVersion;
    private Integer projectCost;

    private String userInput;

    public void createProject() {
        boolean exit = false;

        try {
            while (!exit) {
                while (!exit) {
                    System.out.println("Enter ID of project or c to cancel:");
                    userInput = br.readLine().trim().toLowerCase();

                    if (userInput.equals("c")) {
                        Decorations.returnToMainMenu();
                        exit = true;
                    } else {
                        projectId = Integer.parseInt(userInput);
                        break;
                    }
                }

                while (!exit) {
                    System.out.println("Enter name of project or c to cancel:");
                    userInput = br.readLine().trim().toLowerCase();

                    if (userInput.equals("c")) {
                        Decorations.returnToMainMenu();
                        exit = true;
                    } else {
                        projectName = userInput;
                        break;
                    }
                }

                while (!exit) {
                    System.out.println("Enter version of project or c to cancel:");
                    userInput = br.readLine().trim().toLowerCase();

                    if (userInput.equals("c")) {
                        Decorations.returnToMainMenu();
                        exit = true;
                    } else {
                        projectVersion = userInput;
                        break;
                    }
                }

                while (!exit) {
                    System.out.println("Enter cost of project or c to cancel:");
                    userInput = br.readLine().trim().toLowerCase();

                    if (userInput.equals("c")) {
                        Decorations.returnToMainMenu();
                        exit = true;
                    } else {
                        projectCost = Integer.parseInt(userInput);
                        break;
                    }
                }

                if (!exit) {
                    Project project = new Project(projectId, projectName, projectVersion, projectCost);
                    projectController.save(project);

                    do {
                        System.out.println("Create another one project? y = yes, n =  no:");
                        userInput = br.readLine().trim();
                    } while (!userInput.equals("y") && !userInput.equals("n"));

                    if (userInput.equals("n")) {
                        Decorations.returnToMainMenu();
                        exit = true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showProjectById() {
        boolean exit = false;

        try {
            while (!exit) {
                System.out.println("Enter ID of project or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if (!userInput.equals("c")) {
                    projectController.getById(Integer.parseInt(userInput));
                } else {
                    Decorations.returnToMainMenu();
                    exit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showAllProjects() {
        boolean exit = false;

        projectController.getAll();
        System.out.println();

        try {
            while (!exit) {
                System.out.println("Enter c to back to main menu:");
                userInput = br.readLine().trim().toLowerCase();

                while (!userInput.equals("c")) {
                    System.out.println("Enter c to back to main menu:");
                    userInput = br.readLine().trim().toLowerCase();
                }

                if (userInput.equals("c")) {
                    Decorations.returnToMainMenu();
                    exit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateProject() {
        boolean exit = false;

        Project project;
        Integer id;
        String userInputProjectName;
        String userInputProjectVersion;
        Integer userInputProjectCost;

        try {
            while (!exit) {
                System.out.println("Enter ID of project you are going to update or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if (userInput.equals("c")) {
                    Decorations.returnToMainMenu();
                    exit = true;
                } else {
                    id = Integer.parseInt(userInput);
                    System.out.println("There is a project you are going to update:");
                    project = projectController.getById(id);
                    projectId = project.getId();
                    projectName = project.getName();
                    projectVersion = project.getVersion();
                    projectCost = project.getCost();
                }

                while (!exit) {
                    System.out.println("Change: ");
                    System.out.println("1.     name?");
                    System.out.println("2.     version?");
                    System.out.println("3.     cost?");
                    System.out.println("4. Change all fields?");
                    System.out.println("5. Cancel");

                    userInput = br.readLine().trim().toLowerCase();

                    if (userInput.equals("5")) {
                        Decorations.returnToMainMenu();
                        exit = true;
                    } else {
                        switch (userInput) {
                            case "1":
                                System.out.println("Enter new name of project or c to cancel:");
                                userInput = br.readLine().trim();
                                if (userInput.equals("c")) {
                                    Decorations.returnToMainMenu();
                                    exit = true;
                                    break;
                                } else {
                                    userInputProjectName = userInput;

                                    project = new Project(projectId, userInputProjectName, projectVersion, projectCost);
                                    projectController.update(project);
                                    Decorations.returnToMainMenu();
                                    exit = true;
                                    break;
                                }
                            case "2":
                                System.out.println("Enter new version of project or c to cancel:");
                                userInput = br.readLine().trim();
                                if (userInput.equals("c")) {
                                    Decorations.returnToMainMenu();
                                    exit = true;
                                    break;
                                } else {
                                    userInputProjectVersion = userInput;

                                    project = new Project(projectId, projectName, userInputProjectVersion, projectCost);
                                    projectController.update(project);
                                    Decorations.returnToMainMenu();
                                    exit = true;
                                    break;
                                }
                            case "3":
                                System.out.println("Enter new cost of project or c to cancel:");
                                userInput = br.readLine().trim();
                                if (userInput.equals("c")) {
                                    Decorations.returnToMainMenu();
                                    exit = true;
                                    break;
                                } else {
                                    userInputProjectCost = Integer.parseInt(userInput);

                                    project = new Project(projectId, projectName, projectVersion, userInputProjectCost);
                                    projectController.update(project);
                                    Decorations.returnToMainMenu();
                                    break;
                                }
                            case "4":
                                while (!exit) {
                                    System.out.println("Enter new name of project or c to cancel:");
                                    userInput = br.readLine().trim();
                                    if (userInput.equals("c")) {
                                        Decorations.returnToMainMenu();
                                        exit = true;
                                    } else {
                                        projectName = userInput;
                                        break;
                                    }
                                }

                                while (!exit) {
                                    System.out.println("Enter new version of project:");
                                    userInput = br.readLine().trim().toLowerCase();
                                    if (userInput.equals("c")) {
                                        Decorations.returnToMainMenu();
                                        exit = true;
                                    } else {
                                        projectVersion = userInput;
                                        break;
                                    }
                                }

                                if (!exit) {
                                    System.out.println("Enter new cost of project:");
                                    userInput = br.readLine().trim().toLowerCase();

                                    if (userInput.equals("c")) {
                                        Decorations.returnToMainMenu();
                                        exit = true;
                                    } else {
                                        projectCost = Integer.parseInt(userInput);
                                        break;
                                    }

                                    project = new Project(projectId, projectName, projectVersion, projectCost);
                                    projectController.update(project);
                                    Decorations.returnToMainMenu();
                                    exit = true;
                                    break;
                                }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteProject() {
        boolean exit = false;

        try {
            while (!exit) {
                System.out.println("Enter ID of project you are going to delete or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if (!userInput.equals("c")) {
                    projectController.delete(Integer.parseInt(userInput));

                    do {
                        System.out.println("Delete another one project? y = yes, n =  no:");
                        userInput = br.readLine().trim();
                    } while (!userInput.equals("y") && !userInput.equals("n"));

                    if (userInput.equals("n")) {
                        Decorations.returnToMainMenu();
                        exit = true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
