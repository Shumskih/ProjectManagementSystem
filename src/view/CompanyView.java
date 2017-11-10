package view;

import controller.CompanyController;
import controller.ProjectController;
import controller.SkillController;
import dao.*;
import model.Company;
import model.Project;
import model.Skill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class CompanyView {
    private CompanyController companyController = new CompanyController();
    private ProjectController projectController = new ProjectController();
    CompaniesProjectsDAO companiesProjectsDAO = new CompaniesProjectsDAO();

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private Integer companyId;
    private String companyName;

    private Project project;
    private Set<Project> projects = new LinkedHashSet<>();

    private String userInput;

    public void createCompany() {
        boolean exit = false;

        try {
             while(!exit) {
                 System.out.println("Enter ID of company or c to cancel:");
                 userInput = br.readLine().trim().toLowerCase();

                 if (userInput.equals("c")) {
                     returnToMainMenuBar();
                     exit = true;
                 } else {
                     companyId = Integer.parseInt(userInput);
                     break;
                 }
             }

                while(!exit) {
                    System.out.println("Enter name of company or c to cancel:");
                    userInput = br.readLine().trim();

                    if (userInput.equals("c")) {
                        returnToMainMenuBar();
                        exit = true;
                    } else {
                        companyName = userInput;

                        Company company = new Company(companyId, companyName);
                        companyController.create(company);
                        break;
                    }
                }

                while(!exit) {
                    System.out.println("Add project to company? y = yes or n = no:");
                    userInput = br.readLine().trim().toLowerCase();

                    while (!userInput.equals("y") && !userInput.equals("n")) {
                        System.out.println("Add project to company? Enter y = yes or n = no:");
                        userInput = br.readLine().trim().toLowerCase();
                    }

                    if (userInput.equals("n")) {
                        returnToMainMenuBar();
                        exit = true;
                    } else {
                        System.out.println("There is list of projects:");
                        System.out.println("--------------------------");
                        projectController.readAll();
                        System.out.println();

                        System.out.println("Enter ID of project you're going to add:");
                        userInput = br.readLine().trim().toLowerCase();

                        companiesProjectsDAO.insert(companyId, Integer.parseInt(userInput));

                        System.out.println("Add another project to company? y = yes or n = no:");
                        userInput = br.readLine().trim().toLowerCase();

                        if(userInput.equals("n")) {
                            returnToMainMenuBar();
                            exit = true;
                        }
                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCompanyById() {
        boolean exit = false;

        try {
            while(!exit) {
                System.out.println("Enter ID of company or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if (!userInput.equals("c")) {
                    companyController.read(Integer.parseInt(userInput));
                } else {
                    returnToMainMenuBar();
                    exit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAllCompanies() {
        boolean exit = false;

        companyController.readAll();
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

    public void updateCompany() {
        boolean exit = false;

        String userInputCompanyName;

        Integer id = null;

        try {
            while(!exit) {
                System.out.println("Enter ID of company you're going to update or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if (userInput.equals("c")) {
                    exit = true;
                    returnToMainMenuBar();
                } else {
                    id = Integer.parseInt(userInput);

                    System.out.println("This is a company you're going to update:");
                    companyController.read(id);
                    break;
                }
            }

            while(true) {
                System.out.println("Change name? y = yes, n = no:");
                userInput = br.readLine().trim().toLowerCase();

                if (userInput.equals("n")) {
                    break;
                } else {
                    System.out.println("Enter new company name:");
                    userInputCompanyName = br.readLine().trim();

                    Company company = new Company(companyId, userInputCompanyName);
                    companyController.update(company);
                    break;
                }
            }

            do {
                System.out.println("Change projects? y = yes, n = no:");
                userInput = br.readLine().trim().toLowerCase();
            } while(!userInput.equals("y") & !userInput.equals("n"));

            if (userInput.equals("n")) {
                exit = true;
                returnToMainMenuBar();
            } else {
                System.out.println("There is list of projects company has:");
                System.out.println("--------------------------------------");
                companiesProjectsDAO.readListOfProjects(id);
            }

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

                        companiesProjectsDAO.deleteByProject(Integer.parseInt(userInput));
                        returnToMainMenuBar();
                        exit = true;
                    } else {
                        System.out.println("There is list of projects:");
                        projectController.readAll();
                        System.out.println();

                        System.out.println("Enter ID of project you're going to add:");
                        userInput = br.readLine().trim().toLowerCase();
                        System.out.println();

                        companiesProjectsDAO.insert(id, Integer.parseInt(userInput));
                        returnToMainMenuBar();
                        exit = true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteCompany() {
        boolean exit = false;

        try {
            while(!exit) {
                System.out.println("There is lis of companies:");
                System.out.println("--------------------------");
                companyController.readAll();
                System.out.println();

                System.out.println("Enter ID of company you are going to delete or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if (!userInput.equals("c")) {
                    companyController.delete(Integer.parseInt(userInput));
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
