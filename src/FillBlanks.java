import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Nia Hawkins
 * @version 5
 *
 * allows fill-the-blanks questions
 */
public class FillBlanks extends Question{
    /**
     * constructor
     * @param questionBankID is the question bank identifier
     * @param questionType is the question type
     */
    public FillBlanks(String questionBankID, QuestionType questionType){
        super(questionBankID, questionType);
        super.answerIndex = 0;
        possibleAnswers = new ArrayList<>();
    }
    /**
     * constructor
     * @param questionBankID is the question bank identifier
     * @param questionType is the question type
     * @param questionText is the question text
     * @param answerIndex is the index of the correct answer in the ArrayList
     */
    public FillBlanks(String questionBankID, QuestionType questionType, String questionText, int answerIndex) {
        super(questionBankID, questionType);
        super.questionText = questionText;
        super.answerIndex = answerIndex;
        possibleAnswers = new ArrayList<>();
    }

    /**
     * constructor
     * @param questionBankID is the question bank identifier
     * @param questionType is the question type
     * @param questionText is the question text
     * @param allAnswers is an ArrayList of possible answers
     * @param answerIndex is the index of the correct answer in the ArrayList
     */
    public FillBlanks(String questionBankID, QuestionType questionType, String questionText, ArrayList<String> allAnswers, int answerIndex) {
        super(questionBankID, questionType);
        super.questionText = questionText;
        super.answerIndex = answerIndex;
        possibleAnswers = new ArrayList<>();
        possibleAnswers.addAll(allAnswers);
    }

    /**
     * show a question
     * @param questionNum is the question number to be shown
     */
    @Override
    public void showQuestion(int questionNum){
        // replace location of blank with a blank
        String question = super.getQuestionText().replace("{{blank}}","_____");
        System.out.println("Question "+questionNum + "\n");
        System.out.println("Fill in the blank: ");
        System.out.println(question);
    }

    /**
     * add a new question
     */
    @Override
    public void addQuestion(){
        FillBlanks newQuestion = new FillBlanks(questionBankID, QuestionType.FILL_BLANKS);
        String inpQuestion;
        // ensure user inputs {{blank}}
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

    /**
     * check quiz answer
     * @param userAnswer is the user's answer
     * @param total is the number of correct quiz answers
     * @return updated total
     */
    @Override
    public int checkAnswer(String userAnswer, int total){
        // if answer correct, add 1 to total
        if (possibleAnswers.getFirst().equalsIgnoreCase(userAnswer)){
            total++;
        }
        return total;
    }
}
