import java.io.IOException;
import java.util.ArrayList;

public class FillBlanks extends Question{

    public FillBlanks(String questionBankID, QuestionType questionType){
        super(questionBankID, questionType);
        super.answerIndex = 0;
        possibleAnswers = new ArrayList<>();
    }

    public FillBlanks(String questionBankID, QuestionType questionType, String questionText, int answerIndex) {
        super(questionBankID, questionType);
        super.questionText = questionText;
        super.answerIndex = answerIndex;
        possibleAnswers = new ArrayList<>();
    }

    public FillBlanks(String questionBankID, QuestionType questionType, String questionText, ArrayList<String> allAnswers, int answerIndex) {
        super(questionBankID, questionType);
        super.questionText = questionText;
        super.answerIndex = answerIndex;
        possibleAnswers = new ArrayList<>();
        possibleAnswers.addAll(allAnswers);
    }

//
//    public String getBlank() {
//        return blank;
//    }
@Override
    public void showQuestion(int questionNum){
        String question = super.getQuestionText().replace("{{blank}}","_____");
        System.out.println("Question "+questionNum + "\n");
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
        newQuestion.possibleAnswers.addFirst(userInp.nextLine());

        // save question to file
        try {
            newQuestion.writeQuestionToFile("db.txt");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
@Override
    public int checkAnswer(String userAnswer, int total){
        if (possibleAnswers.getFirst().equalsIgnoreCase(userAnswer)){
            total++;
        }
        return total;
    }
}
