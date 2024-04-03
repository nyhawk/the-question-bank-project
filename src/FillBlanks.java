import java.util.Scanner;

public class FillBlanks extends Question{
    private String blank;
    Scanner userInp = new Scanner(System.in);

    public FillBlanks(){

    }

    public void setBlank(String blank) {
        this.blank = blank;
    }

    public String getBlank() {
        return blank;
    }

    public void outputQuestion(){
        String question = super.getQuestionText().replace("[blank]","_____");
        System.out.println("Fill in the blank: ");
        System.out.println(question);
    }

//    private void setAnswerIndex(){
//        answerIndex = questionText.indexOf("[blank]");
//    }


    public void addQuestion(){
        System.out.println("Enter the question, notating the blank with [blank]");
        this.questionText = userInp.nextLine();
        System.out.println("Enter the missing text");
        this.blank = userInp.nextLine().toLowerCase();


    }
}
