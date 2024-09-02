

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
/**
 * @author nih
 * @version 17/3/23
 * this is the Module class which creates and stores modules with their question banks
 */
public class Module {
    private String moduleID;
    ArrayList<String> questionBanks;
    private Scanner scanner;

    /**
     * constructor for Module
     *
     * @param startModuleID becomes the initial module identifier
     */
    public Module(String startModuleID) {
        moduleID = startModuleID;
        this.questionBanks = new ArrayList<>();
    }

    /**
     * delete empty question bank
     *
     * @param questionBankID is the question bank unique identifier.
     * @param filename is the name of the database.
     * @throws IOException if an I/O error occurs while reading the file
     */
    public void removeQuestionBank(String questionBankID, String filename) throws IOException {
        // remove from questionBanks list
        FileReader fileReader = new FileReader(filename);
        scanner = new Scanner(fileReader);
        scanner.useDelimiter(";;"); // separator
        String tempFilename = "tempFile.txt";
        FileWriter fileWriter = new FileWriter(tempFilename, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        String readQuestion;
        boolean bankFound = false;

        // read file
        while (scanner.hasNextLine()) {
            readQuestion = scanner.nextLine();

            if ((readQuestion.startsWith(questionBankID))) {
                // find bank
                bankFound = true;

                // check bank is empty
                if (readQuestion.contains(";;;;;;;;;;")) {
                    System.out.println("Question bank deleted");
                } else {
                    System.out.println("Question bank is not empty so cannot be deleted");
                }
            } else {
                // add to temp file if not the question bank to delete
                bufferedWriter.write(readQuestion + "\n");
            }
        }
        if (!bankFound){
            System.out.println("Question bank not found");
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
     * loads a module's question banks from the database
     * @param filename is the name of the database
     * @throws IOException if an I/O error occurs when reading the file
     */
    public void loadQuestionBanks (String filename) throws IOException {
        try {
            FileReader fileReader = new FileReader(filename);
            scanner = new Scanner(fileReader);
            scanner.useDelimiter(";;"); // separator
            ArrayList<String> readBanks = new ArrayList<>();
            boolean bankFound = false;

            // read file
            while (scanner.hasNextLine()) {
                String readLine = scanner.nextLine();
                if ((!readLine.isEmpty() && (!readLine.equals("\n")))){
                    // get the question bank identifier
                    int index = readLine.indexOf(";;");

                    String readID = readLine.substring(0, index);
                    readBanks.add(readID);
                    bankFound = true;
                }
            }

            if (!bankFound){
                System.out.println("No question banks found");
            }
            // remove duplicate question banks
            // HashSet doesn't allow duplicates so removes them
            HashSet<String> allInstances = new HashSet<>(readBanks);

            this.questionBanks.clear();
            this.questionBanks.addAll(allInstances);

            fileReader.close();

        } catch (FileNotFoundException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * shows question banks in a module
     * @param moduleID is the module identifier
     * @return boolean if there are banks to show
     */
    public boolean showAllQuestionBanks(String moduleID) {
        boolean banksShown = false;
        for (String questionBank : questionBanks) {
            if ((questionBank.toLowerCase().startsWith(moduleID.toLowerCase()))){
                // if question bank found, output it
                System.out.println(questionBank);
                banksShown = true;
            }
        }
        if (!banksShown){
            System.out.println("No banks to show");

        }
        return banksShown;
    }

    /**
     * write a question bank to the file
     * @param filename is the name of the file
     * @param questionBankID is the question bank identifier
     * @throws IOException when reading and writing to file
     */
    public void writeBankToFile(String filename, String questionBankID) throws IOException {
        FileWriter fileWriter = new FileWriter(filename, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        FileReader fileReader = new FileReader(filename);
        Scanner scanner = new Scanner(fileReader);
        scanner.useDelimiter(";;"); // separator

        String readQuestion;

        // check the bank is not already in the file
        while (scanner.hasNextLine()) {
            readQuestion = scanner.nextLine();

            if (readQuestion.startsWith(questionBankID)) {
                System.out.println("Bank already saved");
                return;
            }
        }

        // if no instances of bank found, add the bank
        bufferedWriter.write(questionBankID + ";;;;;;;;;;" + "\n");
        bufferedWriter.close();
        fileWriter.close();
        System.out.println("New question bank saved");
    }
}