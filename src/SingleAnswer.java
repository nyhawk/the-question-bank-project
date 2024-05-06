import java.io.IOException;
import java.util.ArrayList;

public class SingleAnswer extends Question {
    /**
     * constructor
     * @param questionBankID is the bank identifier
     * @param questionType is the type of question
     */
    public SingleAnswer(String questionBankID, QuestionType questionType) {
        super(questionBankID, questionType);
        possibleAnswers = new ArrayList<>();
    }

    /**
     * constructor
     * @param questionBankID is the bank identifier
     * @param questionType is the type of question
     * @param questionText is the question text
     * @param allAnswers a list of all answers
     * @param answerIndex the location of the correct answer in the array
     */
    public SingleAnswer(String questionBankID, QuestionType questionType, String questionText, ArrayList<String> allAnswers, int answerIndex) {
        super(questionBankID, questionType);
        super.questionText = questionText;
        super.answerIndex = answerIndex;
        possibleAnswers = new ArrayList<>();
        possibleAnswers.addAll(allAnswers);
    }

    /**
     * constructor
     * @param questionBankID is the bank identifier
     * @param questionType is the type of question
     * @param questionText is the question text
     * @param answerIndex is the location of the correct answer in the array
     */
    public SingleAnswer(String questionBankID, QuestionType questionType, String questionText, int answerIndex) {
        super(questionBankID, questionType);
        super.questionText = questionText;
        super.answerIndex = answerIndex;
        possibleAnswers = new ArrayList<>();
    }

    /**
     * shows a question for the quiz
     * @param questionNum is the question number
     */
    public void showQuestion(int questionNum) {
        super.showQuestion(questionNum);
        int answerNum = 1;
        for (String possibleAnswer : this.possibleAnswers) {
            System.out.println(answerNum++ + ". " + possibleAnswer);

        }
    }

    /**
     * add a new question
     */
    public void addQuestion() {
        System.out.println("Enter the question");
        this.setQuestionText(userInp.nextLine());

        System.out.println("Enter an option, or 'q' when all options entered");
        String inpString = userInp.nextLine();
        while (!inpString.equalsIgnoreCase("q")){
            this.possibleAnswers.add(inpString);
            inpString = userInp.nextLine();
        }

        System.out.println("This is the new question. " +
                "Input the option number that is the correct answer");
        this.showQuestion(1);
        this.answerIndex = userInp.nextInt()-1;
        userInp.nextLine();

        // save question to file
        try {
            this.writeQuestionToFile("db.txt");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * check the users answer to a quiz question
     * @param userAnswer the users answer
     * @param score the number of correct questions
     * @return an updated score
     */
    @Override
    public int checkAnswer(String userAnswer, int score){
        if (answerIndex == Integer.parseInt(userAnswer)){
            score++;
        }
        return score;
    }

}