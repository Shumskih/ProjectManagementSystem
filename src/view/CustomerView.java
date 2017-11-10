package view;

import controller.CustomerController;
import controller.ProjectController;
import dao.CustomersProjectsDAO;
import model.Customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CustomerView {
    private CustomerController customerController = new CustomerController();
    private ProjectController projectController = new ProjectController();
    private CustomersProjectsDAO customersProjectsDAO = new CustomersProjectsDAO();

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private Integer customerId;
    private String customerName;

    private String userInput;

    public void createCustomer() {
        boolean exit = false;

        try {
            while(!exit){
                System.out.println("Enter customer's ID or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if(userInput.equals("c")) {
                    returnToMainMenuBar();
                    exit = true;
                } else {
                    customerId = Integer.parseInt(userInput);
                    break;
                }
            }

            while(!exit){
                System.out.println("Enter customer's name or c to cancel:");
                userInput = br.readLine().trim();

                if(userInput.equals("c")) {
                    returnToMainMenuBar();
                    exit = true;
                } else {
                    customerName = userInput;

                    Customer customer = new Customer(customerId, customerName);
                    customerController.create(customer);
                    break;
                }
            }

            while(!exit) {
                System.out.println("Add project to customer? y = yes, n = no:");
                userInput = br.readLine().trim().toLowerCase();

                while (!userInput.equals("y") && !userInput.equals("n")) {
                    System.out.println("Add project to customer? Enter y = yes or n = no:");
                    userInput = br.readLine().trim().toLowerCase();
                }

                if(userInput.equals("n")) {
                    returnToMainMenuBar();
                    exit = true;
                } else {
                    System.out.println("There is list of projects:");
                    System.out.println("--------------------------");
                    projectController.readAll();
                    System.out.println();

                    System.out.println("Enter ID of project you're going to add:");
                    userInput = br.readLine().trim().toLowerCase();

                    customersProjectsDAO.insert(customerId, Integer.parseInt(userInput));

                    System.out.println("Add another project? y = yes, n = no:");
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

    public void showCustomerById() {
        boolean exit = false;

        try {
            while(!exit) {
                System.out.println("Enter ID of customer or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if(!userInput.equals("c")) {
                    customerController.read(Integer.parseInt(userInput));
                } else {
                    returnToMainMenuBar();
                    exit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAllCustomers() {
        boolean exit = false;

        customerController.readAll();
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

    public void updateCustomer() {
        boolean exit = false;

        String userInputCustomerName;

        Integer id = null;

        try {
            while(!exit) {
                System.out.println("Enter ID of customer you're going to update or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if (userInput.equals("c")) {
                    exit = true;
                    returnToMainMenuBar();
                } else {
                    id = Integer.parseInt(userInput);

                    System.out.println("This is a customer you're going to update:");
                    customerController.read(id);
                    break;
                }
            }

            while(true) {
                System.out.println("Change name? y = yes, n = no:");
                userInput = br.readLine().trim().toLowerCase();

                if (userInput.equals("n")) {
                    break;
                } else {
                    System.out.println("Enter new customer name:");
                    userInputCustomerName = br.readLine().trim();

                    Customer customer = new Customer(customerId, userInputCustomerName);
                    customerController.update(customer);
                    break;
                }
            }


            do {
                System.out.println("Change project? y = yes, n = no:");
                userInput = br.readLine().trim().toLowerCase();
                System.out.println();
            } while (!userInput.equals("y") & !userInput.equals("n"));

            if (userInput.equals("n")) {
                exit = true;
                returnToMainMenuBar();
            } else {
                System.out.println("There is list of projects customer has:");
                customerController.readListOfProjects(id);
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

                        customersProjectsDAO.deleteByProject(Integer.parseInt(userInput));
                        returnToMainMenuBar();
                        exit = true;
                    } else {
                        System.out.println("There is list of projects:");
                        projectController.readAll();
                        System.out.println();

                        System.out.println("Enter ID of project you're going to add:");
                        userInput = br.readLine().trim().toLowerCase();
                        System.out.println();

                        customersProjectsDAO.insert(id, Integer.parseInt(userInput));
                        returnToMainMenuBar();
                        exit = true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteCustomer() {
        boolean exit = false;

        try {
            while(!exit) {
                System.out.println("There is lis of customers:");
                System.out.println("--------------------------");
                customerController.readAll();
                System.out.println();

                System.out.println("Enter ID of customer you're going to delete or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if(!userInput.equals("c")) {
                    customerController.delete(Integer.parseInt(userInput));
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
