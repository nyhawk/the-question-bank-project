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
        int inpInt;
        boolean firstQuestion = true;
        scanner = new Scanner(System.in);
        long startTime = System.currentTimeMillis();

        while (true) {
            if (!firstQuestion){
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

            } else if ((inpString.equalsIgnoreCase("n")) && (questionIndex < quizQuestions.size()-1)) {
                questionIndex++;

            } else if ((inpString.equalsIgnoreCase("n")) && (questionIndex >= quizQuestions.size()-1)) {
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
            handleQuizQuestion(question, questionIndex, userAnswers, total);
            //determine the type of question to output
//            if (currentType==QuestionType.SINGLE_ANSWER){
//                // initialise new question from array
//              SingleAnswer question = new SingleAnswer(quizQuestions.get(questionIndex).questionBankID, currentType,
//                      quizQuestions.get(questionIndex).questionText,
//                      quizQuestions.get(questionIndex).possibleAnswers,
//                      quizQuestions.get(questionIndex).answerIndex);
//              // output question and users answer
//                handleQuizQuestion(question, questionIndex, userAnswers, total);
//
//            } else if (currentType==QuestionType.FILL_BLANKS){
//                // initialise a new question
//              FillBlanks question = new FillBlanks(quizQuestions.get(questionIndex).questionBankID, currentType,
//                      quizQuestions.get(questionIndex).questionText,
//                      quizQuestions.get(questionIndex).possibleAnswers,
//                      quizQuestions.get(questionIndex).answerIndex);
//
//              // show the question and the users answer if they have inputted an answer
//                handleQuizQuestion(question, questionIndex, userAnswers, total);
//            }

            // show navigation options
            System.out.println("b - back \t n - next \t s - submit quiz \t q - quit");
            firstQuestion = false;
        }
    }
    private void handleQuizQuestion(Question question, int questionIndex, ArrayList<String> userAnswers, int total){
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
        total = question.checkAnswer(inpString, total);
    }
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


    public void loadQuestions(String filename) throws FileNotFoundException {
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
                    QuestionType typeToEnum = QuestionType.valueOf(readType);
                    Question newQuestion = SelectQuestion.initialseQuestion(readID, typeToEnum, readQuestionText, readAnswerIndex);
                    newQuestion.setPossibleAnswers(readAnswers);
                    questions.add(newQuestion);
//                    if (typeToEnum == QuestionType.SINGLE_ANSWER) {
//                        SingleAnswer newQuestion = new SingleAnswer(readID, QuestionType.SINGLE_ANSWER, readQuestionText, readAnswerIndex);
//                        newQuestion.setPossibleAnswers(readAnswers);
//                        questions.add(newQuestion);
//
//                    } else if (typeToEnum == QuestionType.FILL_BLANKS) {
//                        FillBlanks newQuestion = new FillBlanks(readID, QuestionType.FILL_BLANKS, readQuestionText, readAnswerIndex);
//                        newQuestion.setPossibleAnswers(readAnswers);
//                        questions.add(newQuestion);
//                    }
                }
                scanner.nextLine();
            }
        }
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
            System.out.println(); // so questions have a space between them
        }
    }
}