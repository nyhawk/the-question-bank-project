import java.util.ArrayList;

/**
 * @author Nia Hawkins
 * @version 1
 *
 * select question type
 * allows polymorphism in the rest of the program
 */
public class SelectQuestion {
    /**
     * initialises a question
     * all parameters are passed into the question constructors
     * @param questionBankID is the question bank identifier
     * @param questionType is the question type
     * @param questionText is the question text
     * @param possibleAnswers is a list of possible answers
     * @param answerIndex is the index of the correct answer
     * @return a question object
     */
    public static Question initialseQuestion(String questionBankID, QuestionType questionType, String questionText,
                                             ArrayList<String> possibleAnswers, int answerIndex) {

        return switch (questionType) {
            case SINGLE_ANSWER ->
                    new SingleAnswer(questionBankID, questionType, questionText, possibleAnswers, answerIndex);
            case FILL_BLANKS ->
                    new FillBlanks(questionBankID, questionType, questionText, possibleAnswers, answerIndex);
            case TRUE_FALSE -> new TrueFalse(questionBankID, questionType, questionText, possibleAnswers, answerIndex);
            default -> throw new IllegalArgumentException("Invalid question type");
        };
    }

    /**
     * initialises a question
     * all parameters are passed into the question constructors
     * @param questionBankID is the question bank identifier
     * @param questionType is the question type
     * @return a question object
     */
    public static Question initialseQuestion(String questionBankID, QuestionType questionType) {
        return switch (questionType) {
            case SINGLE_ANSWER -> new SingleAnswer(questionBankID, questionType);
            case FILL_BLANKS -> new FillBlanks(questionBankID, questionType);
            case TRUE_FALSE -> new TrueFalse(questionBankID, questionType);
            default -> throw new IllegalArgumentException("Invalid question type");
        };
    }

    /**
     * initialises a question
     * all parameters are passed into the question constructors
     * @param questionBankID is the question bank identifier
     * @param questionType is the question type
     * @param questionText is the question text
     * @param answerIndex is the index of the correct answer
     * @return a question object
     */
    public static Question initialseQuestion(String questionBankID, QuestionType questionType, String questionText, int answerIndex) {
        return switch (questionType) {
            case SINGLE_ANSWER -> new SingleAnswer(questionBankID, questionType, questionText, answerIndex);
            case FILL_BLANKS -> new FillBlanks(questionBankID, questionType, questionText, answerIndex);
            case TRUE_FALSE -> new TrueFalse(questionBankID, questionType, questionText, answerIndex);
            default -> throw new IllegalArgumentException("Invalid question type");
        };
    }
}
