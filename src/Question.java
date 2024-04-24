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

    QuestionType questionType;

    /**
     * constructor for Question class
     */
    public Question(String newQuestionBankID, QuestionType newQuestionType) {
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
        String answersFromFile[] = answers.substring(1, (answers.length()-1)).split(", ");
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

            if ((readQuestion.startsWith(questionBankID)) && (readQuestion.contains(";;;;;;;;;;"))) {
                bufferedWriter.write(this.toString() + "\n");
                questionAdded = true;

            } else {
                bufferedWriter.write(readQuestion + "\n");
            }
        }

        // if the question has not been added after reading the file, append the question
        if (!questionAdded) {
            bufferedWriter.append(this.toString());
            questionAdded = true;
        }

        bufferedWriter.close();
        fileWriter.close();
        fileReader.close();
        scanner.close();

        if (questionAdded){
        System.out.println("New question saved");
        } else {
            System.out.println("New question failed to save to file");
        }
        // rename file
        File tempFile = new File(tempFilename);
        File oldFile = new File(filename);

        if (oldFile.exists()) {
            if (oldFile.delete()) {
                if (tempFile.renameTo(oldFile)) {
                    System.out.println("File renamed successfully");
                } else {
                    System.out.println("Failed to rename file");
                }
            } else {
                System.out.println("Failed to delete old file");
            }
        } else {
            System.out.println("Old file does not exist");
        }

    }

    public String toString() {
        String answers = "";
//        for (String possibleAnswers : possibleAnswers){
//            // extra comma here when nothing in answers
//            answers = answers + "," + possibleAnswers;
//        }
        String output = questionBankID + ";;" + questionType + ";;" + questionText
                        + ";;" + possibleAnswers + ";;" + answerIndex + ";;\n";
        return output;
    }

    public void setAnswerIndex(int newAnswerIndex){
        answerIndex = newAnswerIndex;
    }

    public void setQuestionType(QuestionType questionType){
        this.questionType = questionType;
    }
}