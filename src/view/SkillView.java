package view;

import controller.SkillController;
import dao.JavaIODeveloperDAOImpl;
import dao.JavaIOSkillDAOImpl;
import model.Skill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class SkillView {
    private SkillController skillController = new SkillController();

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private Integer skillId;
    private String skillName;

    private String userInput;

   public void createSkill() {
       boolean exit = false;

       try {
           do {
               do {
                   System.out.println("Enter skill's ID or c to cancel:");
                   userInput = br.readLine().trim().toLowerCase();
                   if(userInput.equals("c")) {
                       exit = true;
                       break;
                   } else {
                       skillId = Integer.parseInt(userInput);
                   }
                   break;
               } while(true);

               do {
                   if(!exit) {
                       System.out.println("Enter name of skill or c to cancel:");
                       userInput = br.readLine().trim().toLowerCase();
                       if(userInput.equals("c")) {
                           exit = true;
                           break;
                       } else {
                           skillName = userInput;
                       }
                       break;
                   } else {
                       break;
                   }
               } while(true);

               if(!exit) {
                   Skill skill = new Skill(skillId, skillName);
                   skillController.create(skill);
                   returnToMainMenuBar();
                   break;
               } else {
                   returnToMainMenuBar();
                   break;
               }

           } while(!exit);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   public void showSkillById() {
       try {
           do {
               System.out.println("Enter ID of skill or c to cancel:");
               userInput = br.readLine().trim().toLowerCase();
               if(!userInput.equals("c")) {
                   skillController.read(Integer.parseInt(userInput));
                   returnToMainMenuBar();
                   break;
               } else {
                   returnToMainMenuBar();
                   break;
               }
           } while(true);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   public void showAllSkills() {
       skillController.readAll();
       System.out.println();
       returnToMainMenuBar();
   }

    public void updateSkill() {
       String userInputSkillName;

       try {
           do {
               System.out.println("Enter ID of skill you're going to update or c to cancel:");
               userInput = br.readLine().trim().toLowerCase();
               if(userInput.equals("c")) {
                   break;
               } else {
                   Integer id = Integer.parseInt(userInput);
                   System.out.println("This is a skill you're going to update:");
                   skillController.read(id);
               }
               break;
           } while(true);

           do {
               System.out.println("Change name? y = yes, n = no:");
               userInput = br.readLine().trim().toLowerCase();
               if(userInput.equals("n")) {
                   break;
               } else {
                   System.out.println("Enter new name of skill:");
                   userInputSkillName = br.readLine().trim();

                   Skill skill = new Skill(skillId, userInputSkillName);
                   skillController.update(skill);
                   returnToMainMenuBar();
                   break;
               }
           } while(true);

       } catch (IOException e) {
           e.printStackTrace();
       }

    }

   public void deleteSkill() {
       try {
           do {
               System.out.println("Enter ID of skill you are going to delete or c to cancel:");
               userInput = br.readLine().trim().toLowerCase();
               if(!userInput.equals("c")) {
                   skillController.delete(Integer.parseInt(userInput));
                   returnToMainMenuBar();
                   break;
               } else {
                   returnToMainMenuBar();
                   break;
               }
           } while(true);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    public void returnToMainMenuBar() {
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
