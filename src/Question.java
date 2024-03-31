public class Question {
    String questionText;
    int answerIndex;

    /**
     * constructor for Question class
     * @param newQuestionText becomes initial question stem
     */
    public Question(String newQuestionText, int newAnswerIndex){
        this.questionText = newQuestionText;
        this.answerIndex = newAnswerIndex;
    }

    public String getQuestionText(){
        return this.questionText;
    }

    public void setQuestionText(String newQuestionText){
        this.questionText = newQuestionText;
    }

    public void checkAnswer(){

    }

    public void setAnswerIndex(int index) {
        this.answerIndex = index;
    }

    public void showQuestion() {
        System.out.println( "question text" + questionText);
    }
}