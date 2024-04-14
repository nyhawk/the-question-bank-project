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
        System.out.println("Enter the question");
        this.setQuestionText(userInp.nextLine());

        System.out.println("Enter an option, or 'q' when all options entered");
        String inpString = userInp.nextLine();
        while (!inpString.equalsIgnoreCase("q")){
            this.addAnswer(inpString);
            inpString = userInp.nextLine();
        }

        System.out.println("This is the new question. " +
                "Input the option number that is the correct answer");
        this.showQuestion();
        this.setAnswerIndex(userInp.nextInt()-1);
        userInp.nextLine();

        // save question to file
        try {
            this.writeQuestionToFile("db.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAnswer(String answer) {
        possibleAnswers.add(answer);
    }
}