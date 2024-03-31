import java.util.ArrayList;

public class SingleAnswer extends Question {
    private ArrayList<String> possibleAnswers = new ArrayList<>();

    public SingleAnswer(String questionText, int answerIndex) {
        super(questionText, answerIndex);
    }

    public void addAnswer(String answer) {
        possibleAnswers.add(answer);
    }

    public ArrayList<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    @Override
    public void showQuestion() {
        super.showQuestion();
        for (int i = 0; i < possibleAnswers.size(); i++) {
            System.out.println();
        }
    }
}
