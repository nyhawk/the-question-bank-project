import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class QuestionBank {
    private String questionBankID;
    ArrayList<Question> questions;
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

    /**
     * runs the quiz functionality
     * @param totalQuestions is the number of questions to include in the quiz.
     * questions in the array are randomised,and an array of the correct number of questions is made.
     *
     *
     *
     */
    public void takeQuiz(int totalQuestions) {
        // randomise question order
        Collections.shuffle(questions);
        ArrayList<Question> quizQuestions = new ArrayList<>();

        if (questions.size() > totalQuestions) {
            List<Question> trimmedList = questions.subList(0, totalQuestions);
            quizQuestions.addAll(trimmedList);
        }

        int total = 0;
        QuestionType currentType;
        ArrayList<String> userAnswers = new ArrayList<>();
        int questionIndex = 0;
        String inpString;
        boolean firstQuestion = true;
        scanner = new Scanner(System.in);
        long startTime = System.currentTimeMillis();

        while (true) {
            if (!firstQuestion) {
            /* navigate questions
             b - back
             n - next
             s - submit
             q - quit
            */
                inpString = scanner.nextLine();
                if ((inpString.equalsIgnoreCase("b") && (questionIndex > 0))) {
                    questionIndex--;

                } else if ((inpString.equalsIgnoreCase("b") && (questionIndex == 0))) {
                    System.out.println("This is the first question, enter a different menu option");
                    continue;

                } else if ((inpString.equalsIgnoreCase("n")) && (questionIndex < quizQuestions.size() - 1)) {
                    questionIndex++;

                } else if ((inpString.equalsIgnoreCase("n")) && (questionIndex >= quizQuestions.size() - 1)) {
                    System.out.println("This is the last question, enter a different menu option");
                    continue;

                } else if (inpString.equalsIgnoreCase("q")) {
                    break;

                } else if (inpString.equalsIgnoreCase("s")) {
                    long endTime = System.currentTimeMillis();
                    long timeTaken = endTime - startTime;
                    showResults(total, quizQuestions.size(), userAnswers, timeTaken);
                    break;

                } else {
                    System.out.print("Invalid menu option entered");
                    continue;
                }
            }
            // get and output the question
            currentType = quizQuestions.get(questionIndex).questionType;

            Question question = SelectQuestion.initialseQuestion(quizQuestions.get(questionIndex).questionBankID,
                    currentType, quizQuestions.get(questionIndex).questionText,
                    quizQuestions.get(questionIndex).possibleAnswers,
                    quizQuestions.get(questionIndex).answerIndex);

            // show the question and the users answer if they have inputted an answer
            handleQuizQuestion(question, questionIndex, userAnswers, total);

            // show navigation options
            System.out.println("b - back \t n - next \t s - submit quiz \t q - quit");
            firstQuestion = false;
        }
    }

    /**
     *
     * @param question is the question to be shown
     * @param questionIndex is the location of the question in the ArrayList
     * @param userAnswers is a list of the users answers to the quiz
     * @param score is the number of correct answers
     */
    private void handleQuizQuestion(Question question, int questionIndex, ArrayList<String> userAnswers, int score){
        scanner = new Scanner(System.in);
        // output the question, and the users answer
        question.showQuestion(questionIndex+1);

        System.out.println("Your answer: ");

        // only try to show users input if they have inputted an answer for the question
        if (userAnswers.size() > questionIndex){
            System.out.println(userAnswers.get(questionIndex));
        }

        System.out.println("If you are unsure, press enter to enter your answer as empty");

        String inpString = scanner.nextLine();
        userAnswers.add(questionIndex, inpString);

        // check the answer and update number of correct answers
        question.checkAnswer(inpString, score);
    }

    /**
     * shows the users quiz results at the end of a quiz
     * @param score is the number of correct answers
     * @param totalQuestions is the number of questions in the quiz
     * @param answers is a list of the inputted answers
     * @param timeTaken is the time taken to complete the quiz
     */
    private void showResults(int score, int totalQuestions, ArrayList<String> answers, long timeTaken) {
        float percentage = (score/totalQuestions) * 100;
        int unansweredCount = 0;    // number of questions that were not answered

        // find the number of unanswered questions
        // by looking for empty locations in array
        for (String answer : answers) {
            if (answer.isEmpty()) {
                unansweredCount++;
            }
        }

        long minutes = timeTaken / (1000 * 60);
        long seconds = (timeTaken / 1000) % 60;
        long milliseconds = timeTaken % 1000;

        // output quiz statistics
        System.out.println("You scored " + score + " on this quiz, which is " + percentage + "% \n " +
                            "You have " + unansweredCount + " unanswered questions, out of a total of "
                            + totalQuestions + " questions \n You completed the quiz in " + minutes + " minute(s), "
                            + seconds + " second(s), " + milliseconds + " millisecond(s)");

    }

    /**
     * read the questions from the file into the ArrayList
     * @param filename is the name of the file
     * @throws FileNotFoundException when reading file
     */
    public void loadQuestions(String filename) throws FileNotFoundException {
        fileReader = new FileReader(filename);
        scanner = new Scanner(fileReader);
        scanner.useDelimiter(";;"); // separator

        while (scanner.hasNext()) {
            String readID = scanner.next();
            if ((!readID.isEmpty()) && (!readID.equals("\n"))){
                String readType = scanner.next();
                String readQuestionText = scanner.next();
                String readAnswers = scanner.next();
                int readAnswerIndex = scanner.nextInt();

                if (readID.equals(questionBankID)) {
                    QuestionType typeToEnum = QuestionType.valueOf(readType);
                    Question newQuestion = SelectQuestion.initialseQuestion(readID, typeToEnum, readQuestionText, readAnswerIndex);
                    newQuestion.setPossibleAnswers(readAnswers);
                    questions.add(newQuestion);
                }
                scanner.nextLine();
            }
        }
    }

    /**
     * delete a question
     * @param filename is the name of the file
     * @param questionIndex is the location of the question in the list
     * @throws IOException when file reading and writing
     */
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
            System.out.println(); // so questions have a space between them
        }
    }
}