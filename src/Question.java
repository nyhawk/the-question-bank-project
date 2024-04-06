import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Question {
    String questionText;

    String questionBankID;

    Scanner userInp = new Scanner(System.in);

    ArrayList<String> possibleAnswers;

    int answerIndex;

    String questionType;

    /**
     * constructor for Question class
     */
    public Question(String newQuestionBankID, String newQuestionType) {
        questionBankID = newQuestionBankID;
        questionType = newQuestionType;
        possibleAnswers = new ArrayList<>();
    }

    public void addQuestion(String questionBankID, String questionType) {
        // default question format, overridden in subclasses
        Question newQuestion = new Question(questionBankID, questionType);


    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void addAnswer(String answer) {
        possibleAnswers.add(answer);
    }

    public ArrayList<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void checkAnswer() {

    }


    public void showQuestion() {
        System.out.println("question text" + questionText);
    }

    public void writeQuestionToFile(String filename) throws IOException {
        FileWriter fileWriter = new FileWriter(filename, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(this.toString());
        bufferedWriter.close();
        fileWriter.close();
        System.out.println("New question saved");
    }

    public String toString() {
        String output = questionBankID + ";" + questionType + ";" + questionText + ";" + possibleAnswers + ";" + answerIndex + "\n";
        return output;
    }

    public void findQuestionType() {
            System.out.println("""
                     Select a question type\s
                      1 - Single answer\s
                      2 - Fill-the-blanks\s
                    """);
            int inpQuestionType = userInp.nextInt();
            userInp.nextLine();

            switch (inpQuestionType) {
                case 1:
                    // single answer
                    SingleAnswer singleAnswerQuestion = new SingleAnswer(questionBankID, questionType);
                   singleAnswerQuestion.setQuestionType("SingleAnswer");
                    singleAnswerQuestion.addQuestion();

                    break;
                case 2:
                    // fill-the-blanks
                    FillBlanks fillBlanksQuestion = new FillBlanks(questionBankID, questionType);
                  fillBlanksQuestion.setQuestionType("FillBlanks");
                    fillBlanksQuestion.addQuestion();
                    break;
                default:
                    System.out.println("Invalid question type");
            }
        }
    public void setQuestionType(String questionType){
        this.questionType = questionType;
    }

    }

