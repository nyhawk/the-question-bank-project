import java.util.ArrayList;
import java.util.Scanner;

public class QuestionBank {
    private String questionBankID;
    private ArrayList<Question> questions = new ArrayList<Question>();

    /**
     * constructor for the class QuestionBank
     * @param startQuestionBankID becomes initial questionBankID value
     */
    public QuestionBank(String startQuestionBankID){
        questionBankID = startQuestionBankID;
    }

    /**
     * add a new question
     * @param newQuestion
     */
    public void addQuestion(Question newQuestion){ // note: I'm not sure if this param is correct, needs checking
        Scanner userInp = new Scanner(System.in);

        System.out.println("Select question type \n 1 - Single answer \n 2 - Fill-the-blanks");
        int option = userInp.nextInt();
        userInp.nextLine();
        if (option == 1){
            // call single answer method
        } else if (option == 2){
            // call fill the blanks method
        } else {
            System.out.println("Invalid menu option selected");
        }

    }

    /**
     * delete a selected question
     */
    public void removeQuestion(){}

    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
