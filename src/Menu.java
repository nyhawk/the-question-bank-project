import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Nia Hawkins
 * @version 1
 *
 * calls appropriate methods and initialses objects based on menu option chosen
 */
public class Menu {
    private ArrayList<Module> modules = new ArrayList<>();
    private Scanner userInp = new Scanner(System.in);
    private QuestionBank bank = null;
    public Menu() {
    }
    /**
     * get the menu option selected by user and initialise appropriate objects
     */
    public void manageMenu(int menuOpt) {
        boolean validIDs;
        String questionBankID;
        String moduleID;

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
                    newModule.questionBanks.add(questionBankID);
                    modules.add(newModule);

                    // write new bank to database
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
                    // get question type
                    System.out.println("Select a question type \n 1 - Single answer \n 2 - Fill-the-blanks \n 3 - True-false");
                    int inpQuestionType = userInp.nextInt();
                    userInp.nextLine();

                    // add question
                    QuestionType questionType;
                    if (inpQuestionType == 1) {
                        questionType = QuestionType.SINGLE_ANSWER;
                    } else if (inpQuestionType == 2) {
                        questionType = QuestionType.FILL_BLANKS;
                    } else if (inpQuestionType == 3) {
                        questionType = QuestionType.TRUE_FALSE;
                    } else {
                        System.out.println("Invalid menu option");
                        break;
                    }
                    // add question
                    Question question = SelectQuestion.initialseQuestion(questionBankID, questionType);
                    question.addQuestion();

                } else {
                    System.out.println("Invalid question bank identifier");
                }

                break;

            case 3:
                // show question banks
                System.out.println("Input module identifier");
                moduleID = userInp.nextLine();

                // validate module identifier
                if ((!moduleID.isEmpty()) && (moduleID.length() <= 7)) {
                    Module newModule = new Module(moduleID);
                    try {
                        // get question banks from file
                        newModule.loadQuestionBanks("db.txt");

                        // once banks found, output them
                        boolean banksShown = newModule.showAllQuestionBanks(moduleID);

                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                } else {
                    System.out.println("Invalid module identifier");
                }
                break;

            case 4:
                // show questions
                // get question bank id
                System.out.println("Input the question bank identifier");
                questionBankID = userInp.nextLine();

                // validate id
                validIDs = checkID(questionBankID);
                if (validIDs) {
                    bank = new QuestionBank(questionBankID);
                    try {
                        // find questions in database
                        boolean bankFound = bank.loadQuestions("db.txt");

                        // show questions loaded from database
                        bank.showAllQuestions();
                    } catch (FileNotFoundException e) {
                        System.err.println(e.getMessage());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else{
                    System.out.println("Invalid question bank identifier");
                }
                break;

            case 5:
                // delete question bank

                // get question bank ID
                System.out.println("Input the question bank identifier of the question bank to be deleted");
                questionBankID = userInp.nextLine();

                //validate identifier
                validIDs = checkID(questionBankID);
                String[] separateIDs = questionBankID.split(":");
                if (validIDs) {
                    Module deleteBank = new Module(separateIDs[0]);

                    // delete bank
                    try {
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
                System.out.println("Input the question number of the question to be deleted, " +
                        "displayed next to the question when all questions shown (menu option 4)");
                try {
                    int questionIndex = userInp.nextInt();

                    // user must have seen all questions in a bank to find the question number
                    if (bank != null) {
                        try {
                            bank.removeQuestion("db.txt", questionIndex, bank.questions.size());
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    } else {
                        System.out.println("View a question bank's questions in option 4 before deleting");
                    }
                    break;

                } catch (InputMismatchException e){
                    System.err.println(e);
                }
            case 7:
                // take quiz
                System.out.println("Select an option to take a quiz \n 1. View question banks \n 2. Input question bank");

                int choice = userInp.nextInt();
                userInp.nextLine();

                switch (choice){
                    case 1:
                        // take quiz by outputting question banks for a module
                        System.out.println("Input module identifier");
                        moduleID = userInp.nextLine();

                        // validate id
                        if ((!moduleID.isEmpty()) && (moduleID.length() <= 7)) {
                            Module newModule = new Module(moduleID);
                            try {
                                // get banks from database, and output them
                                newModule.loadQuestionBanks("db.txt");
                                boolean banksShown = newModule.showAllQuestionBanks(moduleID);
                                if (!banksShown){
                                    break;
                                }
                                // get user input
                                System.out.println("Input the question bank identifier");
                                questionBankID = userInp.nextLine();

                                // validate id
                                validIDs = checkID(questionBankID);
                                if (validIDs) {
                                    // load the questions for the inputted bank from database
                                    bank = new QuestionBank(questionBankID);
                                    boolean bankFound = bank.loadQuestions("db.txt");

                                    if (bankFound) {
                                        // take a quiz using the questions in the question bank, including all questions
                                        bank.takeQuiz(bank.questions.size());
                                    }

                                } else{
                                    System.out.println("Invalid question bank identifier");
                                }

                            } catch (IOException e) {
                                System.err.println(e.getMessage());
                            }
                        } else {
                            System.out.println("Invalid module identifier");
                        }
                        break;

                    case 2:
                        // take quiz by user inputting question bank id and number of questions

                        // get and validate question bank id
                        System.out.println("Input the question bank identifier");
                        questionBankID = userInp.nextLine();
                        validIDs = checkID(questionBankID);
                        if (validIDs) {
                            bank = new QuestionBank(questionBankID);
                            try {
                                // load the question bank from the database
                                boolean bankFound = bank.loadQuestions("db.txt");

                                // get the number of questions to show in the quiz
                                System.out.println("Enter the number of questions in the quiz");
                                int totalQuestions = userInp.nextInt();

                                // take a quiz
                                bank.takeQuiz(totalQuestions);

                            } catch (FileNotFoundException e) {
                                System.err.println(e.getMessage());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else{
                            System.out.println("Invalid question bank identifier");
                        }
                        break;

                    default:
                        System.out.println("Invalid option");
                }

                break;

            case 8:
                // quit
                System.out.println("Exiting question bank application");
                System.exit(0);
                break;

            default:
                System.out.println("Invalid menu option chosen");
        }
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
}
