import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

abstract class Question {
    String questionText;

    String questionBankID;

    ArrayList<String> possibleAnswers;

    Scanner userInp = new Scanner(System.in);

    int answerIndex;

    String questionType;

    /**
     * constructor for Question class
     */
    public Question(String newQuestionBankID, String newQuestionType) {
        questionBankID = newQuestionBankID;
        questionType = newQuestionType;

    }

    public void addQuestion(String questionBankID, String questionType) {}
    public ArrayList<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setPossibleAnswers(String answers){
        String answersFromFile[] = answers.split(",");
        possibleAnswers.addAll(Arrays.asList(answersFromFile));
    }

    public void checkAnswer() {

    }


    public void showQuestion() {
        System.out.println(questionText);
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
        String answers = "";
        for (String possibleAnswers : possibleAnswers){
            answers = answers + "," + possibleAnswers;
        }
        String output = questionBankID + ";;" + questionType + ";;" + questionText + ";;" + answers + ";;" + answerIndex + ";;\n";
        return output;
    }

    public void setAnswerIndex(int newAnswerIndex){
        answerIndex = newAnswerIndex;
    }

    public void setQuestionType(String questionType){
        this.questionType = questionType;
    }

    }

