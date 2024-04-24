import java.io.IOException;
import java.util.ArrayList;

public class FillBlanks extends Question{

    public FillBlanks(String questionBankID, QuestionType questionType){
        super(questionBankID, questionType);
        possibleAnswers = new ArrayList<>();
    }

    public void setBlank(String blank) {

    }
//
//    public String getBlank() {
//        return blank;
//    }
@Override
    public void showQuestion(){
        String question = super.getQuestionText().replace("{{blank}}","_____");
        System.out.println("Fill in the blank: ");
        System.out.println(question);
    }


    public void addQuestion(){
        FillBlanks newQuestion = new FillBlanks(questionBankID, QuestionType.FILL_BLANKS);
        String inpQuestion;
        do {
            System.out.println("Enter the question, notating the location of the blank with {{blank}}");
            inpQuestion = userInp.nextLine();
            newQuestion.setQuestionText(inpQuestion);
        } while (!inpQuestion.contains("{{blank}}"));



        System.out.println("Enter the missing text");
        possibleAnswers.addFirst(userInp.nextLine());
        super.setAnswerIndex(0);

        // save question to file
        try {
            newQuestion.writeQuestionToFile("db.txt");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
