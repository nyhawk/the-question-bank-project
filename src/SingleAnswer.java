import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Nia Hawkins
 * @version 6
 *
 * Allows single answer questions in a question bank
 */
public class SingleAnswer extends Question {
    /**
     * constructor
     * @param questionBankID is the initial question bank identifier value
     * @param questionType is the question type
     */
    public SingleAnswer(String questionBankID, QuestionType questionType) {
        super(questionBankID, questionType);
        possibleAnswers = new ArrayList<>();
    }

    /**
     * constructor
     * @param questionBankID is the question bank identifier
     * @param questionType is the question type
     * @param questionText is the question text
     * @param allAnswers is a list of possible answers
     * @param answerIndex is the index of the correct answer in the ArrayList
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
     * @param questionBankID is the question bank identifier
     * @param questionType is the question type
     * @param questionText is the question text
     * @param answerIndex is the index of the correct answer in the ArrayList
     */
    public SingleAnswer(String questionBankID, QuestionType questionType, String questionText, int answerIndex) {
        super(questionBankID, questionType);
        super.questionText = questionText;
        super.answerIndex = answerIndex;
        possibleAnswers = new ArrayList<>();
    }

    /**
     * shows a question
     * @param questionNum is the question number to output
     */
    public void showQuestion(int questionNum) {
        // output question number and question text
        super.showQuestion(questionNum);

        // output possible answers and an answer number for the user to input
        int answerNum = 1;
        for (String possibleAnswer : this.possibleAnswers) {
            System.out.println(answerNum++ + ". " + possibleAnswer);

        }
    }

    /**
     * adds a new question to the database
     */
    @Override
    public void addQuestion() {
        // get input
        System.out.println("Enter the question");
        this.setQuestionText(userInp.nextLine());

        System.out.println("Enter an option, or 'q' when all options entered, up to 10 answers");
        String inpString = userInp.nextLine();
        int numAnswers = 1;

        // add answers to ArrayList, but only allow 10
        while ((!inpString.equalsIgnoreCase("q")) && (numAnswers <= 10)){
            this.possibleAnswers.add(inpString);
            inpString = userInp.nextLine();
            numAnswers++;
        }
        if (numAnswers==10){
            System.out.println("No more answers can be added");
        }

        // show question to user, so they can input which answer is correct
        System.out.println("This is the new question. " +
                "Input the option number that is the correct answer");
        this.showQuestion(1);

        // ensure the user enters a valid option for the correct answer
        do {
            this.answerIndex = userInp.nextInt() - 1;
            if ((answerIndex > possibleAnswers.size() -1 ) || (answerIndex < 0)){
                System.out.println("Invalid answer option selected. Input the correct option");
            }
        } while ((answerIndex > possibleAnswers.size() - 1) || (answerIndex < 0));
        userInp.nextLine();

        // save question to file
        try {
            this.writeQuestionToFile("db.txt");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * checks a quiz question answer
     * @param userAnswer is the user's input
     * @param total is the score
     * @return updated total if answer correct
     */
    @Override
    public int checkAnswer(String userAnswer, int total){
        // if answer correct, add 1 to total
        if (answerIndex == Integer.parseInt(userAnswer)){
            total++;
        }
        return total;
    }

}