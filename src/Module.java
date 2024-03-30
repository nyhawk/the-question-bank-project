/**
 * @author nih
 * @version 17/3/23
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * this is the Module class which creates and stores modules with their question banks
 */
public class Module {
    private String moduleID;
    private ArrayList<String> questionBanks;

    /**
     * constructor for Module
     * @param startModuleID becomes the initial module identifier
     */
    public Module (String startModuleID){
        moduleID = startModuleID;
        this.questionBanks = new ArrayList<>();
    }

    /**
     * display module attributes
     * @return module attributes
     */
    public String toString(){
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
     * @param filename is the name of the database file where the question bank is stored
     */
    public void saveQuestionBank(String filename){

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
     * read question banks from file
     * @param filename is the file to read the data from
     */
    public void loadQuestionBanks(String filename){}

    /**
     * add a new question bank to the module
     * @param questionBankID is the question bank unique identifier
     */
    public void addQuestionBank(String questionBankID){

        questionBanks.add(questionBankID);
    }

    /**
     * delete empty question bank
     * @param questionBankID is the question bank unique identifier
     */
    public void removeQuestionBank(String questionBankID){
        for (int i = 0; i < questionBanks.size(); i++){
            if (questionBanks.get(i).equals(questionBankID)){
                questionBanks.remove(i);
            }
        }
    }

    /**
     * get module identifier
     * @return module identifier
     */
    public String getModuleID(){
        return this.moduleID;
    }

    /**
     * update moduleID
     * @param newModuleID is the new identifier for the module object
     */
    public void setModuleID(String newModuleID) {
        this.moduleID = newModuleID;
    }
}