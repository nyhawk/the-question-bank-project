import java.io.IOException;
import java.util.ArrayList;

public class TrueFalse extends Question{


    /**
     * constructor
     * @param questionBankID is the bank identifier
     * @param questionType is the type of question
     */
    public TrueFalse(String questionBankID, QuestionType questionType){
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
    public TrueFalse(String questionBankID, QuestionType questionType, String questionText, int answerIndex) {
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
    public TrueFalse(String questionBankID, QuestionType questionType, String questionText, ArrayList<String> allAnswers, int answerIndex) {
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
        System.out.print("True or false");
        super.showQuestion(questionNum);
    }

    /**
     * adds a new question
     */
    @Override
    public void addQuestion(){
        TrueFalse newQuestion = new TrueFalse(questionBankID, QuestionType.TRUE_FALSE);
        String inpString;

        System.out.println("Enter the question");
        inpString = userInp.nextLine();
        newQuestion.setQuestionText(inpString);


        System.out.println("Enter the answer, T or F");
        do {
            inpString = userInp.nextLine();
            newQuestion.possibleAnswers.addFirst(inpString);
        } while (!inpString.equalsIgnoreCase("T") && !inpString.equalsIgnoreCase("F"));

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