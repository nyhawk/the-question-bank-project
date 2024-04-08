import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class QuestionBank {
    private String questionBankID;
    private ArrayList<Question> questions = new ArrayList<Question>();

    /**
     * constructor for the class QuestionBank
     * @param startQuestionBankID becomes initial questionBankID value
     */
    public QuestionBank(String startQuestionBankID){
        questionBankID = startQuestionBankID;
    }

    public void addQuestion(Question question){
        questions.add(question);
    }

    /**
     * delete a selected question
     */
    public void removeQuestion(){}

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    /**
     * set question bank identifier
     * @param questionBankID becomes new value of questionBankID attribute
     */
    public void setQuestionBankID(String questionBankID) {
        this.questionBankID = questionBankID;
    }

    public void takeQuiz(){}

    public void loadFile(String filename) throws FileNotFoundException {
        FileReader fileReader = new FileReader(filename);
        Scanner scanner = new Scanner(fileReader);
        scanner.useDelimiter(";"); // separator
        while (scanner.hasNext()){
            String idFromFile = scanner.next();
            String typeFromFile = scanner.next();

            System.out.println(idFromFile);
        }
        scanner.close();
    }
}
