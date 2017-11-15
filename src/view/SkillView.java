package view;

import controller.SkillController;
import decorations.Decorations;
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
               while (!exit) {
                   System.out.println("Enter skill's ID or c to cancel:");
                   userInput = br.readLine().trim().toLowerCase();

                   if (userInput.equals("c")) {
                       Decorations.returnToMainMenu();
                       exit = true;
                   } else {
                       skillId = Integer.parseInt(userInput);
                       break;
                   }
               }

               while (!exit) {
                   System.out.println("Enter name of skill or c to cancel:");
                   userInput = br.readLine().trim();

                   if (userInput.equals("c")) {
                       Decorations.returnToMainMenu();
                       exit = true;
                   } else {
                       skillName = userInput;
                       break;
                   }
               }

               if (!exit) {
                   Skill skill = new Skill(skillId, skillName);
                   skillController.save(skill);

                   do {
                       System.out.println("Create another one skill? y = yes, n =  no:");
                       userInput = br.readLine().trim();
                   } while (!userInput.equals("y") && !userInput.equals("n"));

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

   public void showSkillById() {
       boolean exit = false;
       try {
           while(!exit) {
               System.out.println("Enter ID of skill or c to cancel:");
               userInput = br.readLine().trim().toLowerCase();

               if(!userInput.equals("c")) {
                   skillController.getById(Integer.parseInt(userInput));
               } else {
                   Decorations.returnToMainMenu();
                   exit = true;
               }
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   public void showAllSkills() {
       boolean exit = false;

       skillController.getAll();
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

    public void updateSkill() {
       Integer id = null;
       String userInputSkillName;
       boolean exit = false;

       try {
           while(!exit) {
               System.out.println("Enter ID of skill you're going to update or c to cancel:");
               userInput = br.readLine().trim().toLowerCase();
               if(userInput.equals("c")) {
                   Decorations.returnToMainMenu();
                   exit = true;
               } else {
                   id = Integer.parseInt(userInput);
                   System.out.println("This is a skill you're going to update:");
                   skillController.getById(id);
               }

               if(!exit) {
                   do {
                       System.out.println("Change name? y = yes, n = no:");
                       userInput = br.readLine().trim().toLowerCase();
                   } while (!userInput.equals("y") && !userInput.equals("n"));

                   if (userInput.equals("n")) {
                       Decorations.returnToMainMenu();
                       exit = true;
                   } else {
                       System.out.println("Enter new name of skill or c to cancel:");
                       userInputSkillName = br.readLine().trim();

                       if(userInputSkillName.equals("c")) {
                           Decorations.returnToMainMenu();
                           exit = true;
                       } else {
                           Skill skill = new Skill(id, userInputSkillName);
                           skillController.update(skill);
                       }

                       if(!exit) {
                           do {
                               System.out.println("Update another one skill? y = yes, n =  no:");
                               userInput = br.readLine().trim();
                           } while (!userInput.equals("y") && !userInput.equals("n"));

                           if (userInput.equals("n")) {
                               Decorations.returnToMainMenu();
                               exit = true;
                           }
                       }
                   }
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
