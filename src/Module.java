/**
 * @author nih
 * @version 17/3/23
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * this is the Module class which creates and stores modules with their question banks
 */
public class Module {
    private String moduleID;
    private ArrayList<String> questionBanks;
    FileReader fileReader;
    Scanner scanner;

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
     * display module attributes
     *
     * @return module attributes
     */
    public String toString() {
        String output = "ModuleID: " + this.moduleID;
        if (questionBanks != null) {
            output += "questionBanks: " + questionBanks + "\n";
        } else {
            output += "questionBanks: none \n";
        }
        return output;
    }

    /**
     * write question bank to file
     *
     * @param filename is the name of the database file where the question bank is stored
     */
    public void saveQuestionBank(String filename) {

        BufferedWriter fileOutput = null;
        try {
            fileOutput = new BufferedWriter(new FileWriter(filename, true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter printWriter = new PrintWriter(fileOutput);

        printWriter.println(this.questionBanks);

        try {
            fileOutput.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        printWriter.close();
    }


    /**
     * add a new question bank to the module
     *
     * @param questionBankID is the question bank unique identifier
     */
    public void addQuestionBank(String questionBankID) {

        questionBanks.add(questionBankID);
    }

    /**
     * delete empty question bank
     *
     * @param questionBankID is the question bank unique identifier
     */
    public void removeQuestionBank(String questionBankID, String filename) throws IOException {
        // remove from questionBanks list
        for (int i = 0; i < questionBanks.size(); i++) {
            if (questionBanks.get(i).equals(questionBankID)) {
                // check if empty by reading file
                fileReader = new FileReader(filename);
                scanner = new Scanner(fileReader);
                scanner.useDelimiter(";;"); // separator

                while (scanner.hasNextLine()) {
                    String readID = scanner.next();
                    scanner.nextLine();
                    if (!(questionBankID.contains(readID))) {
                        questionBanks.remove(i);
                    } else {
                        System.out.println("Question bank is not empty so cannot be deleted");
                    }
                }
            }
        }
    }
//        /**
//         * get module identifier
//         * @return module identifier
//         */
    public String getModuleID() {
        return this.moduleID;
    }

    /**
     * update moduleID
     * @param newModuleID is the new identifier for the module object
     */
    public void setModuleID (String newModuleID){
        this.moduleID = newModuleID;
    }

    public void loadQuestionBanks (String filename) throws FileNotFoundException {
        fileReader = new FileReader(filename);
        scanner = new Scanner(fileReader);
        scanner.useDelimiter(";;"); // separator

        while (scanner.hasNextLine()) {
            String readID = scanner.next();
            String separateIDs[] = readID.split(":");
            String readModuleID = separateIDs[0];

            scanner.nextLine();
            if (!(questionBanks.contains(readID))) {
                this.addQuestionBank(readID);
            }
        }
        showQuestionBanks();
    }

    public void showQuestionBanks () {
        for (String questionBank : questionBanks) {
            System.out.println(questionBank);
        }
    }
}