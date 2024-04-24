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
        FileReader fileReader = new FileReader(filename);
        scanner = new Scanner(fileReader);
        scanner.useDelimiter(";;"); // separator

        for (int i = 0; i < questionBanks.size(); i++) {
            if (questionBanks.get(i).equals(questionBankID)) {
                // check if empty by reading file
                while (scanner.hasNextLine()) {
                    String readID = scanner.next();
                    scanner.nextLine();
                    if (!(questionBankID.equalsIgnoreCase(readID))) {
                        questionBanks.remove(i);
                    } else {
                        System.out.println("Question bank is not empty so cannot be deleted");
                    }
                }
            }
        }
        fileReader.close();
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

    public void loadQuestionBanks (String filename) throws IOException {
        try {
                FileReader fileReader = new FileReader(filename);
                scanner = new Scanner(fileReader);
                scanner.useDelimiter(";;"); // separator

                while (scanner.hasNextLine()) {
                    String readLine = scanner.nextLine();
                    if ((!readLine.isEmpty() && (!readLine.equals("\n")))){
                        int index = readLine.indexOf(";;");

                        String readID = readLine.substring(0, index);
                        String separateIDs[] = readID.split(":");
                        String readModuleID = separateIDs[0];

                       // scanner.nextLine();
                        if (!(questionBanks.contains(readModuleID))) {
                            this.addQuestionBank(readID);
                        }
                    }
                }

                fileReader.close();
            } catch (FileNotFoundException e){
                System.err.println(e.getMessage());

}
    }

    public void showQuestionBanks () {
        for (String questionBank : questionBanks) {
            System.out.println(questionBank);
        }
    }

    public void writeBankToFile(String filename, String questionBankID) throws IOException {
        FileWriter fileWriter = new FileWriter(filename, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        FileReader fileReader = new FileReader(filename);
        Scanner scanner = new Scanner(fileReader);
        scanner.useDelimiter(";;"); // separator

        String readQuestion;

        // check the module is not already in the file
        while (scanner.hasNextLine()) {
            readQuestion = scanner.nextLine();

            if (readQuestion.startsWith(questionBankID)) {
                System.out.println("Module already saved");
                return;
            }
        }

        // if no instances of module found, add the module
        bufferedWriter.write(questionBankID + ";;;;;;;;;;" + "\n");
        bufferedWriter.close();
        fileWriter.close();
        System.out.println("New question bank saved");
    }
}