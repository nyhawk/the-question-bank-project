import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
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
        // randomise question order
        Collections.shuffle(questions);

        int total = 0;

        QuestionType currentType;

        ArrayList<String> userAnswers = new ArrayList<>();
        int questionIndex = 0;

        String inpString = scanner.nextLine();
        int inpInt;

        while (true){
            /* navigate questions
             b - back
             n - next
             s - submit
             q - quit
            */
            if ((inpString.equalsIgnoreCase("b") && (questionIndex > 0))){
              questionIndex--;

            } else if ((inpString.equalsIgnoreCase("n")) && (questionIndex < questions.size())){
              questionIndex++;

            } else if (inpString.equalsIgnoreCase("q")){
              break;

            } else if (inpString.equalsIgnoreCase("s")){
                showResults(total, questions.size(), userAnswers);
            }

            // get and output the question
            currentType = questions.get(questionIndex).questionType;
            if (currentType==QuestionType.SINGLE_ANSWER){
              SingleAnswer question = new SingleAnswer(questions.get(questionIndex).questionBankID, currentType,
                                                        questions.get(questionIndex).questionText,
                                                        questions.get(questionIndex).possibleAnswers,
                                                        questions.get(questionIndex).answerIndex);
              question.showQuestion(questionIndex+1);
              System.out.println("Your answer: " + userAnswers.get(questionIndex));
              inpInt = scanner.nextInt();
              userAnswers.add(questionIndex, String.valueOf(inpInt));
              question.checkAnswer(inpInt, total);

            } else if (currentType==QuestionType.FILL_BLANKS){
              FillBlanks question = new FillBlanks(questions.get(questionIndex).questionBankID, currentType,
                                                    questions.get(questionIndex).questionText,
                                                    questions.get(questionIndex).possibleAnswers,
                                                    questions.get(questionIndex).answerIndex);
              question.showQuestion(questionIndex+1);
              System.out.println("Your answer: " + userAnswers.get(questionIndex));
              inpString = scanner.nextLine();
              userAnswers.add(questionIndex, inpString);
              question.checkAnswer(inpString, total);
            }
        }
    }

    private void showResults(int score, int totalQuestions, ArrayList<String> answers) {
        float percentage = (score/totalQuestions) * 100;
        int unansweredCount = 0;
        for (String answer : answers) {
            if (answer.isEmpty()) {
                unansweredCount++;
            }
        }

        System.out.println("You scored " + score + " on this quiz, which is " + percentage + "%");
        System.out.println("You have " + unansweredCount + " unanswered questions, " +
                            "out of a total of " + totalQuestions + " questions");
    }


    public void loadFile(String filename) throws FileNotFoundException {
        fileReader = new FileReader(filename);
        scanner = new Scanner(fileReader);
        scanner.useDelimiter(";;"); // separator
        int counter = 1;
        while (scanner.hasNext()) {
            String readID = scanner.next();
            if ((!readID.isEmpty()) && (!readID.equals("\n"))){
                String readType = scanner.next();
                String readQuestionText = scanner.next();
                String readAnswers = scanner.next();
                int readAnswerIndex = scanner.nextInt();

                if (readID.equals(questionBankID)) {
                    System.out.println("Question " + counter++);

                    QuestionType typeToEnum = QuestionType.valueOf(readType);
                    if (typeToEnum == QuestionType.SINGLE_ANSWER) {
                        SingleAnswer newQuestion = new SingleAnswer(readID, QuestionType.SINGLE_ANSWER, readQuestionText, readAnswerIndex);
                        newQuestion.setPossibleAnswers(readAnswers);
                        this.addQuestion(newQuestion);

                    } else if (typeToEnum == QuestionType.FILL_BLANKS) {
                        FillBlanks newQuestion = new FillBlanks(readID, QuestionType.FILL_BLANKS, readQuestionText, readAnswerIndex);
                        newQuestion.setPossibleAnswers(readAnswers);
                        this.addQuestion(newQuestion);
                    }
                }
                scanner.nextLine();
                System.out.println(); // so questions have a space between them
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
            if ((readQuestion.startsWith(questionBankID))) {
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

    public void showAllQuestions(){
        int questionNum = 1;
        for (Question question : questions) {
            question.showQuestion(questionNum);
            questionNum++;
        }
    }
}
