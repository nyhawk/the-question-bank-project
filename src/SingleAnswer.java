import java.util.ArrayList;
import java.util.Scanner;

public class SingleAnswer extends Question {
    private ArrayList<String> possibleAnswers;
    int answerIndex;
    Scanner userInp = new Scanner(System.in);

    public SingleAnswer(String questionBankID) {
        super(questionBankID);
        possibleAnswers = new ArrayList<>();
    }

    public void addAnswer(String answer) {
        possibleAnswers.add(answer);
    }

    public ArrayList<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void showQuestion() {
        super.showQuestion();
        for (int i = 0; i < possibleAnswers.size(); i++) {
            System.out.println();
        }
    }

    public void addQuestion() {
        SingleAnswer newQuestion = new SingleAnswer(questionBankID);
        String inpString = null;

        System.out.println("Enter the question");
        questionText = userInp.nextLine();

        while (!inpString.toLowerCase().equals("done")){
            System.out.println("Enter an option, or 'done' when all options entered");
            inpString = userInp.nextLine();
            newQuestion.addAnswer(inpString);
        }

        System.out.println("This is the new question. " +
                "Input the option number that is the correct answer");
        for (int index = 0; index < newQuestion.possibleAnswers.size(); index++) {
            System.out.println(index + ". " + newQuestion.possibleAnswers.get(index));
        }
        newQuestion.answerIndex = userInp.nextInt();
        userInp.nextLine();
    }
}

