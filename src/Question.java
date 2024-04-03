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
    public Question(String questionBankID){
        //validate id
        Pattern idFormat = Pattern.compile(".+:.+");
        Matcher matchFormat = idFormat.matcher(questionBankID);
        boolean formatCorrect = matchFormat.find();
        if (formatCorrect == false){
            System.out.println("Invalid question bank identifier");
        }

    }

    public void addQuestion(){
        // default question format, overridden in subclasses

    }
    public String getQuestionText(){
        return this.questionText;
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