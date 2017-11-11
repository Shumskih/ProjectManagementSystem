package view;

import controller.ProjectController;
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
            do {
                do {
                    System.out.println("Enter ID of project or c to cancel:");
                    userInput = br.readLine().trim().toLowerCase();
                    if(userInput.equals("c")) {
                        exit = true;
                        break;
                    } else {
                        projectId = Integer.parseInt(userInput);
                    }
                    break;
                } while(true);

                do {
                    if(!exit) {
                        System.out.println("Enter name of project or c to cancel:");
                        userInput = br.readLine().trim().toLowerCase();
                        if(userInput.equals("c")) {
                            exit = true;
                            break;
                        } else {
                            projectName = userInput;
                            break;
                        }
                    } else {
                        break;
                    }
                } while(true);

                do {
                    if(!exit) {
                        System.out.println("Enter version of project or c to cancel:");
                        userInput = br.readLine().trim().toLowerCase();
                        if(userInput.equals("c")) {
                            exit = true;
                            break;
                        } else {
                            projectVersion = userInput;
                            break;
                        }
                    } else {
                        break;
                    }
                } while(true);

                do {
                    if(!exit) {
                        System.out.println("Enter cost of project or c to cancel:");
                        userInput = br.readLine().trim().toLowerCase();
                        if(userInput.equals("c")) {
                            exit = true;
                            break;
                        } else {
                            projectCost = Integer.parseInt(userInput);
                            break;
                        }
                    } else {
                        break;
                    }
                } while(true);

                if(!exit) {
                    Project project = new Project(projectId, projectName, projectVersion, projectCost);
                    projectController.create(project);
                    returnToMainMenuBar();
                    break;
                } else {
                    break;
                }

            } while(!exit);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showProjectById() {
        boolean exit = false;

        try {
            while(!exit) {
                System.out.println("Enter ID of project or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if(!userInput.equals("c")) {
                    projectController.read(Integer.parseInt(userInput));
                } else {
                    returnToMainMenuBar();
                    exit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showAllProjects() {
        boolean exit = false;

        projectController.readAll();
        System.out.println();

        try {
            while(!exit) {
                System.out.println("Enter c to back to main menu:");
                userInput = br.readLine().trim().toLowerCase();

                while(!userInput.equals("c")) {
                    System.out.println("Enter c to back to main menu:");
                    userInput = br.readLine().trim().toLowerCase();
                }

                if (userInput.equals("c")) {
                    returnToMainMenuBar();
                    exit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateProject() {
        boolean exit = false;

        String userInputProjectName;
        String userInputProjectVersion;
        Integer userInputProjectCost;

        try {
            do {
                System.out.println("Enter ID of project you are going to update or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();
                if(userInput.equals("c")) {
                    break;
                } else {
                    Integer id = Integer.parseInt(userInput);
                    System.out.println("There is a project you are going to update:");
                    projectController.read(id);

                    do {
                        System.out.println("Change: ");
                        System.out.println("1.     name?");
                        System.out.println("2.     version?");
                        System.out.println("3.     cost?");
                        System.out.println("4. Change all fields?");
                        System.out.println("5. Cancel");

                        userInput = br.readLine().trim().toLowerCase();

                        if(userInput.equals("6")) {
                            break;
                        } else {
                            switch (userInput) {
                                case "1":
                                    System.out.println("Enter new name of project or c to cancel:");
                                    userInput = br.readLine().trim();
                                    if(userInput.equals("c")) {
                                        returnToMainMenuBar();
                                        break;
                                    } else {
                                        userInputProjectName = userInput;

                                        Project project = new Project(projectId, userInputProjectName, projectVersion, projectCost);
                                        projectController.update(project);
                                        returnToMainMenuBar();
                                        break;
                                    }
                                case "2":
                                    System.out.println("Enter new version of project or c to cancel:");
                                    userInput = br.readLine().trim();
                                    if(userInput.equals("c")) {
                                        returnToMainMenuBar();
                                        break;
                                    } else {
                                        userInputProjectVersion = userInput;

                                        Project project = new Project(projectId, projectName, userInputProjectVersion, projectCost);
                                        projectController.update(project);
                                        returnToMainMenuBar();
                                        break;
                                    }
                                case "3":
                                    System.out.println("Enter new cost of project or c to cancel:");
                                    userInput = br.readLine().trim();
                                    if(userInput.equals("c")) {
                                        returnToMainMenuBar();
                                        break;
                                    } else {
                                        userInputProjectCost = Integer.parseInt(userInput);

                                        Project project = new Project(projectId, projectName, projectVersion, userInputProjectCost);
                                        projectController.update(project);
                                        returnToMainMenuBar();
                                        break;
                                    }
                                case "4":
                                    do {
                                        System.out.println("Enter new name of project or c to cancel:");
                                        userInput = br.readLine().trim();
                                        if (userInput.equals("c")) {
                                            returnToMainMenuBar();
                                            exit = true;
                                            break;
                                        } else {
                                            projectName = userInput;
                                            break;
                                        }
                                    } while(true);

                                    do {
                                        if(!exit) {
                                            System.out.println("Enter new version of project:");
                                            userInput = br.readLine().trim().toLowerCase();
                                            if(userInput.equals("c")) {
                                                returnToMainMenuBar();
                                                exit = true;
                                                break;
                                            } else {
                                                projectVersion = userInput;
                                                break;
                                            }
                                        } else {
                                            returnToMainMenuBar();
                                            break;
                                        }
                                    } while(true);

                                    do {
                                        if(!exit) {
                                            System.out.println("Enter new cost of project:");
                                            userInput = br.readLine().trim().toLowerCase();
                                            if(userInput.equals("c")) {
                                                returnToMainMenuBar();
                                                break;
                                            } else {
                                                projectCost = Integer.parseInt(userInput);
                                                break;
                                            }
                                        } else {
                                            returnToMainMenuBar();
                                            break;
                                        }
                                    } while(true);

                                    Project project = new Project(projectId, projectName, projectVersion, projectCost);
                                    projectController.update(project);
                                    returnToMainMenuBar();
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

    public void deleteProject() {
        boolean exit = false;

        try {
            while(!exit) {
                System.out.println("Enter ID of project you are going to delete or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if (!userInput.equals("c")) {
                    projectController.delete(Integer.parseInt(userInput));
                    returnToMainMenuBar();
                    break;
                } else {
                    returnToMainMenuBar();
                    exit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void returnToMainMenuBar() {
        try {
            System.out.println();
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
