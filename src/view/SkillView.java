package view;

import controller.SkillController;
import model.Skill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SkillView {
    private SkillController skillController = new SkillController();

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private Integer skillId;
    private String skillName;

    private String userInput;

   public void createSkill() {
       boolean exit = false;

       try {
           while(!exit) {
               System.out.println("Enter skill's ID or c to cancel:");
               userInput = br.readLine().trim().toLowerCase();

               if (userInput.equals("c")) {
                   returnToMainMenuBar();
                   exit = true;
               } else {
                   skillId = Integer.parseInt(userInput);
                   break;
               }
           }

           while(!exit) {
               System.out.println("Enter name of skill or c to cancel:");
               userInput = br.readLine().trim();

               if (userInput.equals("c")) {
                   returnToMainMenuBar();
                   exit = true;
               } else {
                   skillName = userInput;
                   break;
               }
           }

            if(!exit) {
                Skill skill = new Skill(skillId, skillName);
                skillController.create(skill);
                returnToMainMenuBar();
            }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   public void showSkillById() {
       boolean exit = false;
       try {
           while(!exit) {
               System.out.println("Enter ID of skill or c to cancel:");
               userInput = br.readLine().trim().toLowerCase();

               if(!userInput.equals("c")) {
                   skillController.read(Integer.parseInt(userInput));
               } else {
                   returnToMainMenuBar();
                   exit = true;
               }
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   public void showAllSkills() {
       boolean exit = false;

       skillController.readAll();
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

    public void updateSkill() {
       String userInputSkillName;
       boolean exit = false;

       try {
           while(!exit) {
               System.out.println("Enter ID of skill you're going to update or c to cancel:");
               userInput = br.readLine().trim().toLowerCase();
               if(userInput.equals("c")) {
                   returnToMainMenuBar();
                   exit = true;
               } else {
                   Integer id = Integer.parseInt(userInput);
                   System.out.println("This is a skill you're going to update:");
                   skillController.read(id);
                   break;
               }
           }

           while(!exit) {
               System.out.println("Change name? y = yes, n = no:");
               userInput = br.readLine().trim().toLowerCase();

               if(userInput.equals("n")) {
                   returnToMainMenuBar();
                   exit = true;
               } else {
                   System.out.println("Enter new name of skill:");
                   userInputSkillName = br.readLine().trim();

                   Skill skill = new Skill(skillId, userInputSkillName);
                   skillController.update(skill);
                   returnToMainMenuBar();
                   break;
               }
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
    }

   public void deleteSkill() {
       boolean exit = false;

       try {
           while(!exit) {
               System.out.println("Enter ID of skill you are going to delete or c to cancel:");
               userInput = br.readLine().trim().toLowerCase();

               if(!userInput.equals("c")) {
                   skillController.delete(Integer.parseInt(userInput));
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
