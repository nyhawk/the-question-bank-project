import java.io.IOException;
import java.util.ArrayList;

public class FillBlanks extends Question{

    /**
     * constructor
     * @param questionBankID is the bank identifier
     * @param questionType is the type of question
     */
    public FillBlanks(String questionBankID, QuestionType questionType){
        super(questionBankID, questionType);
        super.answerIndex = 0;
        possibleAnswers = new ArrayList<>();
    }


    /**
     * constructor
     * @param questionBankID is the bank identifier
     * @param questionType is the question type
     * @param questionText is the question text
     * @param answerIndex is the location of the correct answer
     */
    public FillBlanks(String questionBankID, QuestionType questionType, String questionText, int answerIndex) {
        super(questionBankID, questionType);
        super.questionText = questionText;
        super.answerIndex = answerIndex;
        possibleAnswers = new ArrayList<>();
    }

    /**
     * constructor
     * @param questionBankID is the bank identifier
     * @param questionType is the question type
     * @param questionText is the question text
     * @param allAnswers is a list of the answer options
     * @param answerIndex is the location of the correct answer
     */
    public FillBlanks(String questionBankID, QuestionType questionType, String questionText, ArrayList<String> allAnswers, int answerIndex) {
        super(questionBankID, questionType);
        super.questionText = questionText;
        super.answerIndex = answerIndex;
        possibleAnswers = new ArrayList<>();
        possibleAnswers.addAll(allAnswers);
    }

    /**
     * shows a question
     * @param questionNum is the question number
     */
    @Override
    public void showQuestion(int questionNum){
        String question = super.getQuestionText().replace("{{blank}}","_____");
        System.out.println("Question "+questionNum + "\n");
        System.out.println("Fill in the blank: ");
        System.out.println(question);
    }

    /**
     * adds a new question
     */
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

    /**
     * check the users answer to a quiz question
     * @param userAnswer the users answer
     * @param score the number of correct questions
     * @return the new score
     */
    @Override
    public int checkAnswer(String userAnswer, int score){
        if (possibleAnswers.getFirst().equalsIgnoreCase(userAnswer)){
            score++;
        }
        return score;
    }
}
