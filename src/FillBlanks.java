import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FillBlanks extends Question{

    Scanner userInp = new Scanner(System.in);

    public FillBlanks(String questionBankID, String questionType){
        super(questionBankID, questionType);
        possibleAnswers = new ArrayList<>();
    }

    public void setBlank(String blank) {
        possibleAnswers.addFirst(blank);
        super.setAnswerIndex(0);
    }
//
//    public String getBlank() {
//        return blank;
//    }

    public void outputQuestion(){
        String question = super.getQuestionText().replace("{{blank}}","_____");
        System.out.println("Fill in the blank: ");
        System.out.println(question);
    }

//    private void setAnswerIndex(){
//        answerIndex = questionText.indexOf("[blank]");
//    }


    public void addQuestion(){
        FillBlanks newQuestion = new FillBlanks(questionBankID, questionType);

        System.out.println("Enter the question, notating the location of the blank with {{blank}}");
        newQuestion.setQuestionText(userInp.nextLine());

        System.out.println("Enter the missing text");
        newQuestion.setBlank(userInp.nextLine());

        // save question to file
        try {
            newQuestion.writeQuestionToFile("db.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
