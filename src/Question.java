import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import  java.util.regex.Matcher;
public class Question {
    String questionText;

    String questionBankID;

    /**
     * constructor for Question class
     * 
     */
    public Question(String newQuestionBankID) {
        questionBankID = newQuestionBankID;
    }

    public void addQuestion(String questionBankID){
        // default question format, overridden in subclasses
        Question newQuestion = new Question(questionBankID);

    }
    public String getQuestionText(){
        return questionText;
    }



    public void checkAnswer(){

    }


    public void showQuestion() {
        System.out.println( "question text" + questionText);
    }

    public void writeQuestionToFile(String filename)throws IOException {
        FileWriter fileWriter = new FileWriter(filename);
        PrintWriter printWriter = new PrintWriter(fileWriter);


    }
}