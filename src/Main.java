import java.io.FileNotFoundException;
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

                    //validate identifier
                    validIDs = checkID(inpQuestionBankID);
                    if (validIDs == true) {
                        String separateIDs[] = inpQuestionBankID.split(":");
                        String inpModuleID = separateIDs[0];

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
                    // this method gets the question type from the user and initialises the appropriate object
                    // get the question bank to add the new question to
                    System.out.println("Enter the question bank identifier for the new question");
                    String questionBankID = userInp.nextLine();

                    // validate id
                    boolean validID = checkID(questionBankID);
                    if (validID == false) {
                        System.out.println("Invalid question bank identifier");

                    } else if (validID == true) {
                        System.out.println("""
                     Select a question type\s
                      1 - Single answer\s
                      2 - Fill-the-blanks\s
                    """);
                        int inpQuestionType = userInp.nextInt();
                        userInp.nextLine();

                        switch (inpQuestionType) {
                            case 1:
                            // single answer
                                SingleAnswer singleAnswerQuestion = new SingleAnswer(questionBankID, "SingleAnswer");
                                singleAnswerQuestion.setQuestionType("SingleAnswer");
                                singleAnswerQuestion.addQuestion();

                            break;
                            case 2:
                                // fill-the-blanks
                                    FillBlanks fillBlanksQuestion;
                                    fillBlanksQuestion = new FillBlanks(questionBankID, "FillBlanks");
                                    fillBlanksQuestion.setQuestionType("FillBlanks");
                                    fillBlanksQuestion.addQuestion();
                                    break;
                            default:
                                System.out.println("Invalid question type");
                            }
                        }

                    break;

                case 3:
                    // show question banks
                    QuestionBank bank = new QuestionBank(null);
                    try {
                       bank.loadFile("db.txt");
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
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
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid menu option chosen");
            }
        } while (menuOpt != 8);

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
     * @param questionBankID is the question bank identifier
     * @return valid, true if both identifiers are within length range and contains ":"
     */
    public boolean checkID(String questionBankID){
        boolean valid = false;

        if (questionBankID.contains(":")) {
            // split question bank identifier into module and bank identifiers
            String separateIDs[] = questionBankID.split(":");
            String moduleID = separateIDs[0];
            String bankID = separateIDs[1];

            // find the length of the identifiers
            int lengthModuleID = moduleID.length();
            int lengthBankID = bankID.length();

            // validate the lengths
            if ((0 < lengthModuleID) && (lengthModuleID <= 7)) {
                if ((0 < lengthBankID) && (lengthBankID <= 15)) {
                    valid = true;
                }
            }
        }
        return valid;
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
