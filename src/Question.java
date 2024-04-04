public class Question {
    String questionText;
    int answerIndex;

    /**
     * constructor for Question class
     * 
     */
    public Question(){

    }

    public void addQuestion(){
        // default question format, overridden in subclasses

    }
    public String getQuestionText(){
        return this.questionText;
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