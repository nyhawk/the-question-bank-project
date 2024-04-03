import java.util.ArrayList;
import java.util.Scanner;

/**
 * the main class from which the application will run
 */
public class Main {
    private ArrayList<Module> modules = new ArrayList<>();

    private Scanner userInp = new Scanner(System.in);

    /**
     * runs the program
     */
    public void runApp(){
        System.out.println("********** The Question Bank **********");
        int menuOpt;
        boolean validIDs;

        do {
            printMenu();
            menuOpt = userInp.nextInt();
            userInp.nextLine();

            switch(menuOpt){
                case 1:
                    // create question bank

                    // get questionBankID from user
                    System.out.println("Input the question bank unique identifier");
                    String inpQuestionBankID = userInp.nextLine();
                    String separateIDs[] = inpQuestionBankID.split(":");
                    String inpModuleID = separateIDs[0];
                    String inpBankID = separateIDs[1];

                    //validate identifiers
                    validIDs = checkID(inpModuleID, inpBankID);
                    if (validIDs == true) {

                        // save relationship between module and question bank
                        Module newModule = new Module(inpModuleID);
                        newModule.addQuestionBank(inpQuestionBankID);
                        modules.add(newModule);
                        System.out.println("New question bank added");

                    } else if (validIDs == false){
                        System.out.println("Invalid question bank identifier inputted");
                    }
                    break;

                case 2:
                    // add question
                    findQuestionType();
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

    /**
     * outputs the menu
     */
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

    /**
     * validate the inputted question bank identifier
     * @param moduleID is the inputted module identifier
     * @param bankID is the inputted bank identifier
     * @return valid, true if both identifiers are within length range
     */
    private boolean checkID(String moduleID, String bankID){
        boolean valid = false;

        // find the length of the identifiers
        int lengthModuleID = moduleID.length();
        int lengthBankID = bankID.length();

        // validate the lengths
        if ((0 < lengthModuleID) && (lengthModuleID <=7)){
            if ((0 < lengthBankID) && (lengthBankID <=15)){
                valid = true;
            }
        }
        return valid;
    }
    private void findQuestionType(){
        System.out.println("""
                Select a question type\s
                 1 - Single answer\s
                 2 - Fill-the-blanks\s
               """);
        int questionType = userInp.nextInt();
        userInp.nextLine();

        switch (questionType){
            case 1:
                // single answer
                SingleAnswer singleAnswerQuestion = new SingleAnswer();
                System.out.println("Enter the question bank identifier for the new question");
                singleAnswerQuestion.addQuestion(userInp.nextLine());
                break;
            case 2:
                // fill-the-blanks
                FillBlanks fillBlanksQuestion = new FillBlanks();
                System.out.println("Enter the question bank identifier for the new question");
                fillBlanksQuestion.addQuestion(userInp.nextLine());
                break;
            default:
                System.out.println("Invalid question type");
        }
    }



        /**
     * initialises the main class and runs the application
     * @param args the command line arguments passed to the application
     */
    public static void main(String[] args) {
        Main app = new Main();
        app.runApp();
    }
}
