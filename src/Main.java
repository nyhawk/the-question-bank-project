import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * the main class from which the application will run
 */
public class Main {
    private final ArrayList<Module> modules = new ArrayList<>();

    private final Scanner userInp = new Scanner(System.in);

    /**
     * runs the program
     */
    public void runApp() {
        System.out.println("********** The Question Bank **********");
        int menuOpt;
        boolean validIDs;
        String questionBankID;
        QuestionBank bank = null;

        do {
            printMenu();
            menuOpt = userInp.nextInt();
            userInp.nextLine();

            switch (menuOpt) {
                case 1:
                    // create question bank

                    // get questionBankID from user
                    System.out.println("Input the question bank unique identifier");
                    questionBankID = userInp.nextLine();

                    //validate identifier
                    validIDs = checkID(questionBankID);
                    if (validIDs) {
                        String[] separateIDs = questionBankID.split(":");
                        String inpModuleID = separateIDs[0];

                        // save relationship between module and question bank
                        Module newModule = new Module(inpModuleID);
                        newModule.addQuestionBank(questionBankID);
                        modules.add(newModule);

                        try {
                            newModule.writeBankToFile("db.txt", questionBankID);
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Invalid question bank identifier inputted");
                    }
                    break;

                case 2:
                    // add question
                    // this method gets the question type from the user and initialises the appropriate object
                    // get the question bank to add the new question to
                    System.out.println("Enter the question bank identifier for the new question");
                    questionBankID = userInp.nextLine();

                    // validate id
                    validIDs = checkID(questionBankID);
                    if (validIDs) {
                        System.out.println("Select a question type \n 1 - Single answer \n 2 - Fill-the-blanks");
                        int inpQuestionType = userInp.nextInt();
                        userInp.nextLine();

                        switch (inpQuestionType) {
                            case 1:
                                // single answer
                                SingleAnswer singleAnswerQuestion = new SingleAnswer(questionBankID,
                                        QuestionType.SINGLE_ANSWER);
                                singleAnswerQuestion.setQuestionType(QuestionType.SINGLE_ANSWER);
                                singleAnswerQuestion.addQuestion();

                                break;
                            case 2:
                                // fill-the-blanks
                                FillBlanks fillBlanksQuestion;
                                fillBlanksQuestion = new FillBlanks(questionBankID, QuestionType.FILL_BLANKS);
                                fillBlanksQuestion.setQuestionType(QuestionType.FILL_BLANKS);
                                fillBlanksQuestion.addQuestion();
                                break;
                            default:
                                System.out.println("Invalid question type");
                        }
                    } else {
                        System.out.println("Invalid question bank identifier");
                    }

                    break;

                case 3:
                    // show question banks
                    System.out.println("Input module identifier");
                    String moduleID = userInp.nextLine();
                    if ((!moduleID.isEmpty()) && (moduleID.length() <= 7)) {
                        Module newModule = new Module(moduleID);
                        try {
                            newModule.loadQuestionBanks("db.txt");
                            newModule.showQuestionBanks();

                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                    break;

                case 4:
                    // show questions
                    System.out.println("Input the question bank identifier");
                    questionBankID = userInp.nextLine();
                    validIDs = checkID(questionBankID);
                    if (validIDs) {
                        bank = new QuestionBank(questionBankID);
                        try {
                            bank.loadFile("db.txt");
                        } catch (FileNotFoundException e) {
                            System.err.println(e.getMessage());
                        }
                    } else{
                        System.out.println("Invalid question bank identifier");
                    }
                    break;

                case 5:
                    // delete question bank
                    System.out.println("Input the question bank identifier of the question bank to be deleted");

                    questionBankID = userInp.nextLine();

                    //validate identifier
                    validIDs = checkID(questionBankID);
                    String separateIDs[] = questionBankID.split(":");
                    if (validIDs) {
                        Module deleteBank = new Module(separateIDs[0]);

                        // load the module's question banks
                        try {
                            deleteBank.loadQuestionBanks("db.txt");
                            deleteBank.removeQuestionBank(questionBankID, "db.txt");
                        } catch (IOException e){
                            System.err.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Invalid question bank identifier inputted");
                    }

                    break;

                case 6:
                    // delete question
                    System.out.println("Input the index of the question to be deleted, " +
                            "displayed next to the question when all questions shown (menu option 4)");
                    int questionIndex = userInp.nextInt();

                    if (bank != null) {
                        try {
                            bank.removeQuestion("db.txt", questionIndex);
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    } else {
                        System.out.println("View a question bank's questions in option 4 before deleting");
                    }
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
    public void printMenu() {
        System.out.println("\n Select an option \n " +
                "1 - Add empty question bank \n 2 - Add question \n " +
                "3 - Show question banks \n 4 - Show questions \n " +
                "5 - Delete question bank \n 6 - Delete question \n " +
                "7 - Take quiz \n 8 - Exit \n");
    }

    /**
     * validate the inputted question bank identifier
     *
     * @param questionBankID is the question bank identifier
     * @return valid, true if both identifiers are within length range and contains ":"
     */
    public boolean checkID(String questionBankID) {
        boolean valid = false;

        if (questionBankID.contains(":")) {
            // split question bank identifier into module and bank identifiers
            String[] separateIDs = questionBankID.split(":");
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
     *
     * @param args the command line arguments passed to the application
     */
    public static void main(String[] args) {
        Main app = new Main();
        app.runApp();
    }


}
