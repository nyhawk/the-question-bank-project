import java.io.IOException;
import java.util.ArrayList;

public class SingleAnswer extends Question {

    public SingleAnswer(String questionBankID, QuestionType questionType) {
        super(questionBankID, questionType);
        possibleAnswers = new ArrayList<>();
    }

    public SingleAnswer(String questionBankID, QuestionType questionType, String questionText, ArrayList<String> allAnswers, int answerIndex) {
        super(questionBankID, questionType);
        super.questionText = questionText;
        super.answerIndex = answerIndex;
        possibleAnswers = new ArrayList<>();
        possibleAnswers.addAll(allAnswers);
    }

    public SingleAnswer(String questionBankID, QuestionType questionType, String questionText, int answerIndex) {
        super(questionBankID, questionType);
        super.questionText = questionText;
        super.answerIndex = answerIndex;
        possibleAnswers = new ArrayList<>();
    }

    public void showQuestion(int questionNum) {
        super.showQuestion(questionNum);
        int answerNum = 1;
        for (String possibleAnswer : this.possibleAnswers) {
            System.out.println(answerNum++ + ". " + possibleAnswer);

        }
    }

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
        this.setAnswerIndex(userInp.nextInt()-1);
        userInp.nextLine();

        // save question to file
        try {
            this.writeQuestionToFile("db.txt");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @Override
    public int checkAnswer(String userAnswer, int total){
        if (answerIndex == Integer.parseInt(userAnswer)){
            total++;
        }
        return total;
    }

}