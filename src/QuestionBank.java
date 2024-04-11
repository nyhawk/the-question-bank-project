import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class QuestionBank {
    private String questionBankID;
    private ArrayList<Question> questions;

    /**
     * constructor for the class QuestionBank
     * @param startQuestionBankID becomes initial questionBankID value
     */
    public QuestionBank(String startQuestionBankID){
        questionBankID = startQuestionBankID;
        questions = new ArrayList<Question>();
    }

    public void addQuestion(Question question){
        questions.add(question);
    }

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
        scanner.useDelimiter(";;"); // separator

        while (scanner.hasNext()){
            String readID = scanner.next();
            if (!readID.isEmpty()){
                String readType = scanner.next();
                String readQuestionText = scanner.next();
                String readAnswers = scanner.next();
                int readAnswerIndex = scanner.nextInt();

                if (readID.equals(questionBankID)) {
                    if (readType.equals("SingleAnswer")) {
                        SingleAnswer newQuestion = new SingleAnswer(readID, readType);
                        newQuestion.setQuestionText(readQuestionText);
                        newQuestion.setPossibleAnswers(readAnswers);
                        newQuestion.setAnswerIndex(readAnswerIndex);
                        newQuestion.showQuestion();
                        this.addQuestion(newQuestion);

                    } else if (readType.equals("FillBlanks")) {
                        FillBlanks newQuestion = new FillBlanks(readID, readType);
                        newQuestion.setQuestionText(readQuestionText);
                        newQuestion.setPossibleAnswers(readAnswers);
                        newQuestion.setAnswerIndex(readAnswerIndex);
                        this.addQuestion(newQuestion);
                        newQuestion.showQuestion();
                    }
                }
            scanner.nextLine();
            }
        }
        scanner.close();
    }

    public void showAllQuestions(){
        for (Question question : questions) {
            System.out.println(question);
        }
    }

    public void removeQuestion(String filename, String questionBankID) throws IOException {
        // remove from file
        FileReader fileReader = new FileReader(filename);
        Scanner scanner = new Scanner(fileReader);
        scanner.useDelimiter(";;"); // separator
        String tempFilename = "tempFile.txt";
        FileWriter fileWriter = new FileWriter(tempFilename, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        while (scanner.hasNextLine()) {
            String readID = scanner.next();

            scanner.nextLine();
            if (!(readID.contains(questionBankID))) {
                bufferedWriter.write(this.toString());
            }
        }
        bufferedWriter.close();
        fileWriter.close();

        // rename file
        File tempFile = new File(tempFilename);
        File oldFile = new File(filename);
        oldFile.delete();
        tempFile.renameTo(oldFile);
        tempFile.delete();
    }
}