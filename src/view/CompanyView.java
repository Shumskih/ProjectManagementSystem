package view;

import controller.CompanyController;
import controller.ProjectController;
import dao.*;
import decorations.Decorations;
import model.Company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CompanyView {
    private CompanyController companyController = new CompanyController();
    private ProjectController projectController = new ProjectController();

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private Integer companyId;
    private String companyName;

    private String userInput;

    public void createCompany() {
        boolean exit = false;

        try {
             while(!exit) {
                 System.out.println("Enter ID of company or c to cancel:");
                 userInput = br.readLine().trim().toLowerCase();

                 if (userInput.equals("c")) {
                     Decorations.returnToMainMenu();
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
                        Decorations.returnToMainMenu();
                        exit = true;
                    } else {
                        companyName = userInput;

                        Company company = new Company(companyId, companyName);
                        companyController.save(company);
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

                        companyController.insertComProj(companyId, Integer.parseInt(userInput));

                        System.out.println("Add another project to company? y = yes or n = no:");
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

    public void showCompanyById() {
        boolean exit = false;

        try {
            while(!exit) {
                System.out.println("Enter ID of company or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if (!userInput.equals("c")) {
                    companyController.getById(Integer.parseInt(userInput));
                } else {
                    Decorations.returnToMainMenu();
                    exit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAllCompanies() {
        boolean exit = false;

        companyController.getAll();
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

    public void updateCompany() {
        boolean exit = false;

        String userInputCompanyName;

        Integer id = null;

        try {
            while(!exit) {
                System.out.println("Enter ID of company you're going to update or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if (userInput.equals("c")) {
                    Decorations.returnToMainMenu();
                    exit = true;
                } else {
                    id = Integer.parseInt(userInput);

                    System.out.println("This is a company you're going to update:");
                    companyController.getById(id);
                    break;
                }
            }

            while(!exit) {
                do {
                    System.out.println("Change name? y = yes, n = no:");
                    userInput = br.readLine().trim().toLowerCase();
                } while(!userInput.equals("y") & !userInput.equals("n"));

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

            while(!exit) {
                do {
                    System.out.println("Change projects? y = yes, n = no:");
                    userInput = br.readLine().trim().toLowerCase();
                } while (!userInput.equals("y") & !userInput.equals("n"));

                if (userInput.equals("n")) {
                    exit = true;
                    Decorations.returnToMainMenu();
                } else {
                    System.out.println("There is list of projects company has:");
                    companyController.getListOfProjects(id);
                }
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

                        companyController.deleteByProject(Integer.parseInt(userInput));
                        Decorations.returnToMainMenu();
                        exit = true;
                    } else {
                        System.out.println("There is list of projects:");
                        projectController.getAll();
                        System.out.println();

                        System.out.println("Enter ID of project you're going to add:");
                        userInput = br.readLine().trim().toLowerCase();
                        System.out.println();

                        companyController.insertComProj(id, Integer.parseInt(userInput));
                        Decorations.returnToMainMenu();
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
                companyController.getAll();
                System.out.println();

                System.out.println("Enter ID of company you are going to delete or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if (!userInput.equals("c")) {
                    companyController.delete(Integer.parseInt(userInput));
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
