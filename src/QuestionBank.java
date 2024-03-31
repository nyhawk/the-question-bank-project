import java.util.ArrayList;

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



}
