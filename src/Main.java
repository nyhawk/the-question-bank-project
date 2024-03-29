import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private ArrayList<Module> modules;

    private Scanner userInp;

    public void runApp(){
        System.out.println("WELCOME TO THE QUESTION BANK");
        int menuOpt;

        do {
            printMenu();
            menuOpt = userInp.nextInt();

            switch(menuOpt){
                case 1:
                    // create question bank
                    System.out.println("This menu option has not been programmed yet!");
                    break;

                case 2:
                    // add question
                    System.out.println("This menu option has not been programmed yet!");
                    break;

                case 3:
                    // show question banks
                    System.out.println("This menu option has not been programmed yet!");
                    break;

                case 4:
                    // show questions
                    System.out.println("This menu option has not been programmed yet!");
                    break;

                case 5:
                    // delete question bank
                    System.out.println("This menu option has not been programmed yet!");
                    break;

                case 6:
                    // delete question
                    System.out.println("This menu option has not been programmed yet!");
                    break;

                case 7:
                    // take quiz
                    System.out.println("This menu option has not been programmed yet!");
                    break;

                case 8:
                    // quit
                    System.out.println("Exiting question bank application");
                    break;
                default:
                    System.out.println("Invalid menu option chosen");
            }
        } while (menuOpt != 9);

    }

    public void printMenu(){
        System.out.println("""
                Select an option\s
                 1 - Add empty question bank\s
                 2 - Add question\s
                 3 - Show question banks\s
                 4 - Show questions\s
                 5 - Delete question bank\s
                 6 - Delete question\s
                 7 - Take quiz
                 8 - Exit""");
    }


    public static void main(String[] args) {
        Main app = new Main();
        app.runApp();
    }
}
