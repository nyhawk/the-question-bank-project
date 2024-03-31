public class FillBlanks extends Question{
    private String blank;

    public FillBlanks(String questionText, int answerIndex){
        super(questionText, answerIndex);
    }

    public void setBlank(String blank) {
        this.blank = blank;
    }

    public String getBlank() {
        return blank;
    }

    public void outputQuestion(){
        String question = super.getQuestionText().replace("{blank}","_____");
        System.out.println("Fill in the blank: ");
        System.out.println(question);
    }

    private void setAnswerIndex(){
        answerIndex = questionText.indexOf("{blank}");
    }
}
