import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * provides a template for all questions
 */
public abstract class Question {
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

    /**
     * add a question
     */
    public void addQuestion() {}

    /**
     * set question text
     * @param questionText is new value of question text
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     * get question text
     * @return question text
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * set answers read from file
     * @param answers is a string of answers from the file
     */
    public void setPossibleAnswers(String answers){
        String answersFromFile[] = answers.substring(1, (answers.length()-1)).split(", ");
        possibleAnswers.addAll(Arrays.asList(answersFromFile));
    }

    /**
     * show a question
     * @param questionNum is the question number to be shown
     */
    public void showQuestion(int questionNum) {
        System.out.println("Question " + questionNum + "\n" + questionText);
    }

    /**
     * write question to the file
     * @param filename is the file name
     * @throws IOException when writing to file
     */
    public void writeQuestionToFile(String filename) throws IOException {
        String tempFilename = "tempFile.txt";

        FileWriter fileWriter = new FileWriter(tempFilename, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        FileReader fileReader = new FileReader(filename);
        Scanner scanner = new Scanner(fileReader);
        scanner.useDelimiter(";;"); // separator

        boolean questionAdded = false;
        String readQuestion;
        boolean bankFound = false;

        // read file
        while (scanner.hasNextLine()) {
            readQuestion = scanner.nextLine();

            // if the question bank is empty, overwrite it with the new question
            // else just write what has been read back into temp file

            if ((readQuestion.startsWith(questionBankID)) && (readQuestion.contains(";;;;;;;;;;"))) {
                bufferedWriter.write(this.toString() + "\n");
                questionAdded = true;

            } else if (readQuestion.startsWith(questionBankID)){
                bankFound = true;

            } else {
                bufferedWriter.write(readQuestion + "\n");
            }
        }

        // if the question has not been added after reading the file, append the question
        if (!questionAdded && bankFound)  {
            bufferedWriter.append(this.toString());
            questionAdded = true;
        }

        bufferedWriter.close();
        fileWriter.close();
        fileReader.close();

        if (questionAdded) {
            System.out.println("New question saved");

        } else if (!bankFound){
            System.out.println("Question bank identifier for the question not found. " +
                    "Add a question bank before adding a question");
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

    /**
     * get object attributes
     * @return object attributes
     */
    public String toString() {
        String output = questionBankID + ";;" + questionType + ";;" + questionText
                        + ";;" + possibleAnswers + ";;" + answerIndex + ";;\n";
        return output;
    }

    /**
     * check quiz question answer
     * @param inpString is the user's answer
     * @param total is the number of correct quiz answers
     * @return updated number of correct quiz answers
     */
    public abstract int checkAnswer(String inpString, int total);
}