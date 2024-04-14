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
        String tempFilename = "tempFile.txt";

        FileWriter fileWriter = new FileWriter(tempFilename, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        FileReader fileReader = new FileReader(filename);
        Scanner scanner = new Scanner(fileReader);
        scanner.useDelimiter(";;"); // separator

        boolean questionAdded = false;
        String readQuestion;

        while (scanner.hasNextLine()) {
            readQuestion = scanner.nextLine();

            // if the question bank is empty, overwrite it with the new question
            // else just write what has been read back into temp file

            if ((readQuestion.startsWith(questionBankID)) && (readQuestion.contains(";;;;;;;;;;"))){
                bufferedWriter.write(this.toString());
                questionAdded = true;

            } else {
                bufferedWriter.write(readQuestion);
            }
        }

        // if the question has not been added after reading the file, append the question
        if (!questionAdded){
            bufferedWriter.append(this.toString());
            questionAdded = true;
        }
        bufferedWriter.close();
        fileWriter.close();

        if (questionAdded){
        System.out.println("New question saved");
        } else {
            System.out.println("New question failed to save to file");
        }
        // rename file
        File tempFile = new File(tempFilename);
        File oldFile = new File(filename);
        oldFile.delete();
        tempFile.renameTo(oldFile);
    }

    public String toString() {
        String answers = "";
        for (String possibleAnswers : possibleAnswers){
            answers = answers + "," + possibleAnswers;
        }
        String output = questionBankID + ";;" + questionType + ";;" + questionText
                        + ";;" + answers + ";;" + answerIndex + ";;\n";
        return output;
    }

    public void setAnswerIndex(int newAnswerIndex){
        answerIndex = newAnswerIndex;
    }

    public void setQuestionType(String questionType){
        this.questionType = questionType;
    }
}