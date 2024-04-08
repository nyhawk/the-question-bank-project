import java.io.IOException;

public class SingleAnswer extends Question {
    public SingleAnswer(String questionBankID, String questionType) {
        super(questionBankID, questionType);

    }

    public void showQuestion() {
        super.showQuestion();
        for (int i = 0; i < possibleAnswers.size(); i++) {
            System.out.println();
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
        for (int index = 0; index < newQuestion.possibleAnswers.size(); index++) {
            System.out.println(index + ". " + newQuestion.possibleAnswers.get(index));
        }

        newQuestion.answerIndex = userInp.nextInt();
        userInp.nextLine();

        // save question to file
        try {
            newQuestion.writeQuestionToFile("db.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}