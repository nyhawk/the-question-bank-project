import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class QuestionBank {
    private String questionBankID;
    private ArrayList<Question> questions;
    private FileReader fileReader;
    private Scanner scanner;

    /**
     * constructor for the class QuestionBank
     *
     * @param startQuestionBankID becomes initial questionBankID value
     */
    public QuestionBank(String startQuestionBankID) {
        questionBankID = startQuestionBankID;
        questions = new ArrayList<Question>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    /**
     * set question bank identifier
     *
     * @param questionBankID becomes new value of questionBankID attribute
     */
    public void setQuestionBankID(String questionBankID) {
        this.questionBankID = questionBankID;
    }

    public void takeQuiz() {
    }

    public void loadFile(String filename) throws FileNotFoundException {
        fileReader = new FileReader(filename);
        scanner = new Scanner(fileReader);
        scanner.useDelimiter(";;"); // separator
        int counter = 1;
        while (scanner.hasNext()) {
            String readID = scanner.next();
            if (!readID.isEmpty()) {
                String readType = scanner.next();
                String readQuestionText = scanner.next();
                String readAnswers = scanner.next();
                int readAnswerIndex = scanner.nextInt();

                if (readID.equals(questionBankID)) {
                    System.out.println("Question " + counter++);
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

    public void removeQuestion(String filename, int questionIndex) throws IOException {
        // remove from file
        fileReader = new FileReader(filename);
        scanner = new Scanner(fileReader);
        scanner.useDelimiter(";;"); // separator
        String tempFilename = "tempFile.txt";
        FileWriter fileWriter = new FileWriter(tempFilename, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        int counter = 1; // start at 1 as questionIndex starts at 1 to be user-friendly
        String readQuestion;
        String modifiedQuestion;

        while (scanner.hasNextLine()) {
            readQuestion = scanner.nextLine();

            // scanner.nextLine();
            if (!(readQuestion.startsWith(questionBankID))) {
                if (counter == (questionIndex)) {
                    // modify the question so only ID stored to preserve the question bank in the file
                    int splitLocation = readQuestion.indexOf(";;");
                    modifiedQuestion = readQuestion.substring(0, splitLocation);
                    bufferedWriter.write(modifiedQuestion + ";;;;;;;;;;" + "\n");

                }
                counter++;
            } else {
                bufferedWriter.write(readQuestion + "\n");
            }
        }
        bufferedWriter.close();
        fileWriter.close();
        fileReader.close();

        // rename file
        File tempFile = new File(tempFilename);
        File oldFile = new File(filename);
        oldFile.delete();
        tempFile.renameTo(oldFile);

    }
}
