package view;

import controller.DeveloperController;
import controller.ProjectController;
import controller.SkillController;
import decorations.Decorations;
import model.Developer;
import model.Project;
import model.Skill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.Set;

public class DeveloperView {
    private DeveloperController developerController = new DeveloperController();
    private SkillController skillController = new SkillController();
    private ProjectController projectController = new ProjectController();

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private Integer developerId;
    private String developerName;
    private String developerSpecialization;
    private Integer developerExperience;
    private Integer developerSalary;

    private String userInput;

    public void createDeveloper() {
        boolean exit = false;

        try {
            while(!exit) {
                System.out.println("Enter developer's ID or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if (userInput.equals("c")) {
                    Decorations.returnToMainMenu();
                    exit = true;
                } else {
                    developerId = Integer.parseInt(userInput);
                    break;
                }
            }

            while(!exit) {
                System.out.println("Enter developer's name or c to cancel:");
                userInput = br.readLine().trim();

                if (userInput.equals("c")) {
                    Decorations.returnToMainMenu();
                    exit = true;
                } else {
                    developerName = userInput;
                    break;
                }
            }

            while(!exit) {
                System.out.println("Enter developer's specialization or c to cancel:");
                userInput = br.readLine().trim();

                if (userInput.equals("c")) {
                    Decorations.returnToMainMenu();
                    exit = true;
                } else {
                    developerSpecialization = userInput;
                    break;
                }
            }

            while(!exit) {
                System.out.println("Enter developer's experience or c to cancel:");
                userInput = br.readLine().trim();

                if (userInput.equals("c")) {
                    Decorations.returnToMainMenu();
                    exit = true;
                } else {
                    developerExperience = Integer.parseInt(userInput);
                    break;
                }
            }

            while(!exit) {
                System.out.println("Enter developer's salary or c to cancel:");
                userInput = br.readLine().trim();

                if (userInput.equals("c")) {
                    Decorations.returnToMainMenu();
                    exit = true;
                } else {
                    developerSalary = Integer.parseInt(userInput);

                    Developer developer = new Developer(developerId, developerName, developerSpecialization, developerExperience, developerSalary);
                    developerController.save(developer);
                    break;
                }
            }

            while(!exit) {
                while (!userInput.equals("y") && !userInput.equals("n")) {
                    System.out.println("Add skill to developer? Enter y = yes or n = no:");
                    userInput = br.readLine().trim().toLowerCase();
                }

                if (userInput.equals("n")) {
                    userInput = "";
                    break;
                } else {
                    System.out.println("There is list of skills:");
                    skillController.getAll();
                    System.out.println();

                    System.out.println("Enter ID of skill you're going to add:");
                    userInput = br.readLine().trim().toLowerCase();
                    System.out.println();

                    developerController.insertDevSkill(developerId, Integer.parseInt(userInput));

                    System.out.println("Add another one skill? y = yes, n = no:");
                    userInput = br.readLine().trim().toLowerCase();

                    if(userInput.equals("n")) {
                        userInput = "";
                        break;
                    }
                }
            }

                while(!exit) {
                    while (!userInput.equals("y") && !userInput.equals("n")) {
                        System.out.println("Add project to developer? Enter y = yes or n = no:");
                        userInput = br.readLine().trim().toLowerCase();
                    }

                    if (userInput.equals("n")) {
                        Decorations.returnToMainMenu();
                        exit = true;
                    } else {
                        System.out.println("There is list of projects:");
                        System.out.println("--------------------------");
                        projectController.getAll();
                        System.out.println();


                        System.out.println("Enter ID of project you're going to add:");
                        userInput = br.readLine().trim().toLowerCase();
                        System.out.println();

                        developerController.insertProjDev(Integer.parseInt(userInput), developerId);

                        System.out.println("Add another one project? y = yes, n = no:");
                        userInput = br.readLine().trim().toLowerCase();

                        if(userInput.equals("n")) {
                            Decorations.returnToMainMenu();
                            exit = true;
                        }
                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showDeveloperById() {
        boolean exit = false;

        try {
            while(!exit) {
                System.out.println("Enter developer's ID or c to cancel: ");
                userInput = br.readLine().trim();

                if(!userInput.equals("c")) {
                    developerController.getById(Integer.parseInt(userInput));
                } else {
                    Decorations.returnToMainMenu();
                    exit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAllDevelopers() {
        boolean exit = false;

        developerController.getAll();
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
                    Decorations.returnToMainMenu();
                    exit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateDeveloper() {
        boolean exit = false;

        Integer devId = null;
        String name = null;
        String specialization = null;
        Integer experience = null;
        Integer salary = null;
        Set<Skill> skills = new LinkedHashSet<>();
        Set<Project> projects = new LinkedHashSet<>();

        Developer developer;

        String userInputDevName;
        String userInputDevSpecialization;
        Integer userInputDevExperience;
        Integer userInputDevSalary;

        Integer id = null;

        try {
            while(!exit) {
                System.out.println("Enter developer's ID you are going to update or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if(userInput.equals("c")) {
                    Decorations.returnToMainMenu();
                    exit = true;
                } else {
                    id = Integer.parseInt(userInput);
                    developerId = id;

                    System.out.println("There is a developer you are going to update:");
                    developer = developerController.getById(id);

                    devId = developer.getId();
                    name = developer.getName();
                    specialization = developer.getSpecialization();
                    experience = developer.getExperience();
                    salary = developer.getSalary();
                    skills = developer.getSkills();
                    projects = developer.getProjects();
                }

                    while(!exit) {
                        System.out.println("Change: ");
                        System.out.println("    1.name?");
                        System.out.println("    2.specialization?");
                        System.out.println("    3.experience?");
                        System.out.println("    4.salary?");
                        System.out.println("    5.skills?");
                        System.out.println("    6.projects?");
                        System.out.println("    7.Change all fields?");
                        System.out.println("8.Cancel");

                        userInput = br.readLine().trim().toLowerCase();

                        if(userInput.equals("8")) {
                            Decorations.returnToMainMenu();
                            exit = true;
                        } else {
                            switch (userInput) {
                                case "1":
                                    System.out.println("Enter new developer's name or c to cancel:");
                                    userInput = br.readLine().trim();

                                    if(userInput.equals("c")) {
                                        Decorations.returnToMainMenu();
                                        exit = true;
                                    } else {
                                        userInputDevName = userInput;

                                        developer = new Developer(devId, userInputDevName, specialization, experience, salary, skills, projects);
                                        developerController.update(developer);
                                        break;
                                    }
                                case "2":
                                    System.out.println("Enter new developer's specialization or c to cancel:");
                                    userInput = br.readLine().trim();

                                    if(userInput.equals("c")) {
                                        Decorations.returnToMainMenu();
                                        exit = true;
                                    } else {
                                        userInputDevSpecialization = userInput;

                                        developer = new Developer(devId, name, userInputDevSpecialization, experience, salary);
                                        developerController.update(developer);
                                        break;
                                    }
                                case "3":
                                    System.out.println("Enter new developer's experience or c to cancel:");
                                    userInput = br.readLine().trim();

                                    if(userInput.equals("c")) {
                                        Decorations.returnToMainMenu();
                                        exit = true;
                                    } else {
                                        userInputDevExperience = Integer.parseInt(userInput);

                                        developer = new Developer(devId, name, specialization, userInputDevExperience, salary);
                                        developerController.update(developer);
                                        break;
                                    }
                                case "4":
                                    System.out.println("Enter new developer's salary or c to cancel:");
                                    userInput = br.readLine().trim();

                                    if(userInput.equals("c")) {
                                        Decorations.returnToMainMenu();
                                        exit = true;
                                    } else {
                                        userInputDevSalary = Integer.parseInt(userInput);

                                       developer = new Developer(devId, name, specialization, experience, userInputDevSalary);
                                        developerController.update(developer);
                                        break;
                                    }
                                case "5":
                                    System.out.println("There is list of skills developer has:");
                                    System.out.println("--------------------------------------");
                                    developerController.getListOfSkills(id);

                                    while(!exit) {
                                        System.out.println("Delete skill or insert new? d = delete, i = insert new:");
                                        userInput = br.readLine().trim().toLowerCase();

                                        if (!userInput.equals("d") && !userInput.equals("i")) {
                                            do {
                                                System.out.println("Enter d to delete skill or i to insert new:");
                                                userInput = br.readLine().trim().toLowerCase();
                                            } while (!userInput.equals("d") && !userInput.equals("i"));

                                        } else {
                                            if (userInput.equals("d")) {
                                                System.out.println("Enter ID of skill you're going to delete:");
                                                userInput = br.readLine().trim().toLowerCase();
                                                System.out.println();

                                                developerController.deleteBySkill(Integer.parseInt(userInput));
                                                Decorations.returnToMainMenu();
                                                exit = true;
                                            } else {
                                                System.out.println("There is list of skills:");
                                                skillController.getAll();
                                                System.out.println();

                                                System.out.println("Enter ID of skill you're going to add:");
                                                userInput = br.readLine().trim().toLowerCase();
                                                System.out.println();

                                                developerController.insertDevSkill(id, Integer.parseInt(userInput));
                                                Decorations.returnToMainMenu();
                                                exit = true;
                                            }
                                        }
                                    }
                                case "6":
                                    System.out.println("There is list of projects developer has:");
                                    System.out.println("--------------------------------------");
                                    developerController.getListOfProjects(id);

                                    while(!exit) {
                                        System.out.println("Delete project or insert new? d = delete, i = insert new:");
                                        userInput = br.readLine().trim().toLowerCase();

                                        if (!userInput.equals("d") && !userInput.equals("i")) {
                                            do {
                                                System.out.println("Enter d to delete project or i to insert new:");
                                                userInput = br.readLine().trim().toLowerCase();
                                            } while (!userInput.equals("d") && !userInput.equals("i"));

                                        } else {
                                            if (userInput.equals("d")) {
                                                System.out.println("Enter ID of project you're going to delete:");
                                                userInput = br.readLine().trim().toLowerCase();
                                                System.out.println();

                                                developerController.deleteByProject(Integer.parseInt(userInput));
                                                Decorations.returnToMainMenu();
                                                exit = true;
                                            } else {
                                                System.out.println("There is list of projects:");
                                                projectController.getAll();
                                                System.out.println();

                                                System.out.println("Enter ID of project you're going to add:");
                                                userInput = br.readLine().trim().toLowerCase();
                                                System.out.println();

                                                developerController.insertProjDev(Integer.parseInt(userInput), id);
                                                Decorations.returnToMainMenu();
                                                exit = true;
                                            }
                                        }
                                    }
                                case "7":
                                    while(!exit) {
                                        System.out.println("Enter developer's name or c to cancel:");
                                        userInput = br.readLine().trim().toLowerCase();

                                        if (userInput.equals("c")) {
                                            Decorations.returnToMainMenu();
                                            exit = true;
                                        } else {
                                            developerName = userInput;
                                            break;
                                        }
                                    }

                                    while(!exit ){
                                        System.out.println("Enter developer's specialization:");
                                        userInput = br.readLine().trim().toLowerCase();

                                        if(userInput.equals("c")) {
                                            Decorations.returnToMainMenu();
                                            exit = true;
                                        } else {
                                            developerSpecialization = userInput;
                                            break;
                                        }
                                    }

                                    while(!exit) {
                                        System.out.println("Enter developer's experience:");
                                        userInput = br.readLine().trim().toLowerCase();

                                        if(userInput.equals("c")) {
                                            Decorations.returnToMainMenu();
                                            exit = true;
                                        } else {
                                            developerExperience = Integer.parseInt(userInput);
                                            break;
                                        }
                                    }

                                    while(!exit) {
                                        System.out.println("Enter developer's salary:");
                                        userInput = br.readLine().trim().toLowerCase();

                                        if(userInput.equals("c")) {
                                            Decorations.returnToMainMenu();
                                            exit = true;
                                        } else {
                                            developerSalary = Integer.parseInt(userInput);
                                            break;
                                        }
                                    }

                                    if(!exit) {
                                        developer = new Developer(developerId, developerName, developerSpecialization, developerExperience, developerSalary);
                                        developerController.update(developer);
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

    public void deleteDeveloper() {
        boolean exit = false;

        String userInput;

        try {
            while(!exit) {
                System.out.println("Enter developer's ID you are going to delete or c to cancel:");
                userInput = br.readLine().trim();

                if(!userInput.equals("c")) {
                    developerController.delete(Integer.parseInt(userInput));
                    Decorations.returnToMainMenu();
                    break;
                } else {
                    Decorations.returnToMainMenu();
                    exit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
