import java.util.ArrayList;

public class SelectQuestion {
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
    public static Question initialseQuestion(String questionBankID, QuestionType questionType) {
        return switch (questionType) {
            case SINGLE_ANSWER -> new SingleAnswer(questionBankID, questionType);
            case FILL_BLANKS -> new FillBlanks(questionBankID, questionType);
            case TRUE_FALSE -> new TrueFalse(questionBankID, questionType);
            default -> throw new IllegalArgumentException("Invalid question type");
        };
    }

    public static Question initialseQuestion(String questionBankID, QuestionType questionType, String questionText, int answerIndex) {
        return switch (questionType) {
            case SINGLE_ANSWER -> new SingleAnswer(questionBankID, questionType, questionText, answerIndex);
            case FILL_BLANKS -> new FillBlanks(questionBankID, questionType, questionText, answerIndex);
            case TRUE_FALSE -> new TrueFalse(questionBankID, questionType, questionText, answerIndex);
            default -> throw new IllegalArgumentException("Invalid question type");
        };
    }
}
