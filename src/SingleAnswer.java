import java.io.IOException;
import java.util.ArrayList;

public class SingleAnswer extends Question {

    public SingleAnswer(String questionBankID, String questionType) {
        super(questionBankID, questionType);
        possibleAnswers = new ArrayList<>();
    }

    public void showQuestion() {
        super.showQuestion();
        int questionNum = 1;
        for (String possibleAnswer : this.possibleAnswers) {
            System.out.println(questionNum++ + ". " + possibleAnswer);

        }
    }

    public void addQuestion() {
        SingleAnswer newQuestion = new SingleAnswer(questionBankID, questionType);

        System.out.println("Enter the question");
        newQuestion.setQuestionText(userInp.nextLine());

        System.out.println("Enter an option, or 'q' when all options entered");
        String inpString = userInp.nextLine();
        while (!inpString.equalsIgnoreCase("q")){
            newQuestion.addAnswer(inpString);
            inpString = userInp.nextLine();
        }

        System.out.println("This is the new question. " +
                "Input the option number that is the correct answer");
        newQuestion.showQuestion();
        newQuestion.setAnswerIndex(userInp.nextInt()-1);
        userInp.nextLine();

        // save question to file
        try {
            newQuestion.writeQuestionToFile("db.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAnswer(String answer) {
        possibleAnswers.add(answer);
    }
}