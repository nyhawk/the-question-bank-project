import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author Nia Hawkins
 * @version 9
 *
 * creates a question bank and manages questions
 */
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
     * runs a quiz
     * @param totalQuestions is the number of questions chosen to be in the quiz
     */
    public void takeQuiz(int totalQuestions) {
        // randomise question order
        Collections.shuffle(questions);
        ArrayList<Question> quizQuestions = new ArrayList<>();

        /* change the number of questions in the array to match totalQuestions
        * or change totalQuestions to match the number in the question bank
        */

        if (questions.size() > totalQuestions) {
            List<Question> trimmedList = questions.subList(0, totalQuestions);
            quizQuestions.addAll(trimmedList);
        } else {
            quizQuestions.addAll(questions);
            totalQuestions = quizQuestions.size();
        }

        // declare variables
        int total = 0;
        QuestionType currentType;
        ArrayList<String> userAnswers = new ArrayList<>();
        int questionIndex = 0;
        String inpString;
        boolean firstQuestion = true;
        scanner = new Scanner(System.in);
        long startTime = System.currentTimeMillis();

        // keep showing questions
        while (true) {
            if (!firstQuestion){
            /* navigate questions
             b - back
             n - next
             s - submit
             q - quit
            */
                // get menu option input
                inpString = scanner.nextLine();

                // go back
            if ((inpString.equalsIgnoreCase("b") && (questionIndex > 0))) {
                questionIndex--;

                // can't go back if first question
            } else if ((inpString.equalsIgnoreCase("b") && (questionIndex == 0))) {
                System.out.println("This is the first question, enter a different menu option");
                continue;

                // go forwards
            } else if ((inpString.equalsIgnoreCase("n")) && (questionIndex < quizQuestions.size()-1)) {
                questionIndex++;

                // can't go forwards if last question
            } else if ((inpString.equalsIgnoreCase("n")) && (questionIndex >= quizQuestions.size()-1)) {
                System.out.println("This is the last question, enter a different menu option");
                continue;

                // exit quiz without statistics
            } else if (inpString.equalsIgnoreCase("q")) {
                break;

                // end quiz and show statistics
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
            total = handleQuizQuestion(question, questionIndex, userAnswers, total);

            // show navigation options
            System.out.println("b - back \t n - next \t s - submit quiz \t q - quit");

            // so navigation menu not shown at start of quiz
            firstQuestion = false;
        }
    }

    /**
     * show question and check answer
     * @param question is the question to show
     * @param questionIndex is the index of the correct answer in the ArrayList
     * @param userAnswers is a ArrayList of all the user's answers in the quiz
     * @param total is the number of correct answers in the quiz
     * @return total
     */
    private int handleQuizQuestion(Question question, int questionIndex, ArrayList<String> userAnswers, int total) {
        scanner = new Scanner(System.in);
        // output the question, and the users answer
        question.showQuestion(questionIndex + 1);

        System.out.println("Your answer: ");

        // only try to show users input if they have inputted an answer for the question
        if (userAnswers.size() > questionIndex) {
            System.out.println(userAnswers.get(questionIndex));
        }

        System.out.println("If you are unsure, press enter to enter your answer as empty");

        String inpString = scanner.nextLine();
        // update answer if array large enough, else add answer
        if (userAnswers.size() > questionIndex){
            userAnswers.set(questionIndex, inpString);
        } else {
            userAnswers.add(questionIndex, inpString);
        }

        // check the answer and update number of correct answers
        total = question.checkAnswer(inpString, total);
        return total;
    }

    /**
     * show quiz statistics at end of quiz
     * @param score is the total of correct answers
     * @param totalQuestions is the number of questions in the quiz
     * @param answers is a ArrayList of the user's quiz answers
     * @param timeTaken is the time taken to complete the quiz
     */
    private void showResults(int score, int totalQuestions, ArrayList<String> answers, long timeTaken) {
        float percentage = (score/totalQuestions) * 100;
        int unansweredCount = 0;    // number of questions that were not answered

        // find the number of unanswered questions by looking for empty locations in array
        for (String answer : answers) {
            if (answer.isEmpty()) {
                unansweredCount++;
            }
        }

        // convert time to be more readable
        long minutes = timeTaken / (1000 * 60);
        long seconds = (timeTaken / 1000) % 60;
        long milliseconds = timeTaken % 1000;

        // output quiz statistics
        System.out.println("You scored " + score + " on this quiz, which is " + percentage + "% \n" +
                            "You have " + unansweredCount + " unanswered questions, out of a total of "
                            + totalQuestions + " questions \nYou completed the quiz in " + minutes + " minute(s), "
                            + seconds + " second(s), " + milliseconds + " millisecond(s)");

    }

    /**
     * read questions from file into question bank
     * @param filename is the name of the file
     * @return boolean if the question bank is in the file
     * @throws IOException when reading file
     */
    public boolean loadQuestions(String filename) throws IOException {
        fileReader = new FileReader(filename);
        scanner = new Scanner(fileReader);
        scanner.useDelimiter(";;"); // separator
        boolean bankFound = false;

        // read whole file
        while (scanner.hasNext()) {
            String readID = scanner.next();

            // if there is something in readID, continue reading the line
            if ((!readID.isEmpty()) && (!readID.equals("\n"))){
                String readType = scanner.next();

                // to avoid errors if an empty question bank is found
                if (!(readType.isEmpty())) {
                    // get everything else from line
                    String readQuestionText = scanner.next();
                    String readAnswers = scanner.next();
                    int readAnswerIndex = scanner.nextInt();

                    // if the question bank identifier has been found in the file, add the question to the bank
                    if (readID.equalsIgnoreCase(questionBankID)) {
                        QuestionType typeToEnum = QuestionType.valueOf(readType);
                        Question newQuestion = SelectQuestion.initialseQuestion(readID, typeToEnum, readQuestionText, readAnswerIndex);
                        newQuestion.setPossibleAnswers(readAnswers);
                        questions.add(newQuestion);
                        bankFound = true;
                    }
                }
                scanner.nextLine();
            }
        }
        if (!bankFound){
            System.out.println("Bank not found in database, or bank is empty");
        }
        fileReader.close();
        return bankFound;
    }

    /**
     * delete a question
     * @param filename is the name of the file
     * @param questionIndex is the index of the question in the question bank
     * @param numQuestions is the number of questions in the question bank
     * @throws IOException when reading and writing to file
     */
    public void removeQuestion(String filename, int questionIndex, int numQuestions) throws IOException {
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

        // validate the index
        if (questionIndex > numQuestions){
            System.out.println("Invalid question number");
            return;
        }

        // read file
        while (scanner.hasNextLine()) {
            readQuestion = scanner.nextLine();

            // if question bank identifier found
            if ((readQuestion.startsWith(questionBankID))) {

                if (counter == questionIndex){
                    /* if the number of questions with the question bank identifier found matches index
                    delete question by not writing to temp file
                     */
                    System.out.println("Question deleted");
                    counter++;


                    if (numQuestions == 1){
                        /* if there is only 1 question in the question bank
                         modify the question so only ID stored to preserve the question bank in the file
                        */
                        int splitLocation = readQuestion.indexOf(";;");
                        modifiedQuestion = readQuestion.substring(0, splitLocation);
                        bufferedWriter.write(modifiedQuestion + ";;;;;;;;;;" + "\n");
                    }
                } else {
                    // another question in the question bank found
                    counter++;
                    bufferedWriter.write(readQuestion + "\n");
                }
            } else {
                // a question that is not in the question bank
                bufferedWriter.write(readQuestion + "\n");
            }
        }
        bufferedWriter.close();
        fileWriter.close();
        fileReader.close();

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
     * show all questions in a question bank
     */
    public void showAllQuestions(){
        int questionNum = 1;
        boolean questionShown = false;

        // output each question with a question number
        for (Question question : questions) {
            question.showQuestion(questionNum);
            questionNum++;
            System.out.println(); // so questions have a space between them
            questionShown = true;
        }

        if (!questionShown){
            System.out.println("No questions found");
        }
    }
}