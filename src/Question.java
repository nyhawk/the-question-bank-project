import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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
     *
     * class implemented in subclasses
     * add a new question
     *
     */
    public void addQuestion(){}

    /**
     * set question text
     * @param questionText is the new question text
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
     * set possible answers
     * @param answers is a string of answers read from database
     */
    public void setPossibleAnswers(String answers){
        String[] answersFromFile = answers.substring(1, (answers.length()-1)).split(", ");
        possibleAnswers.addAll(Arrays.asList(answersFromFile));
    }

    /**
     * show a question
     * @param questionNum is the question number
     */
    public void showQuestion(int questionNum) {
        System.out.println("Question " + questionNum + "\n" + questionText);
    }

    /**
     * save a question to the database
     * @param filename is the name of the file
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
        String output = questionBankID + ";;" + questionType + ";;" + questionText
                        + ";;" + possibleAnswers + ";;" + answerIndex + ";;\n";
        return output;
    }

    /**
     * check inputted quiz answer
     * @param inpString the users answer
     * @param score the number of correct questions
     * @return the new score
     */
    public abstract int checkAnswer(String inpString, int score);
}