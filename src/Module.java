/**
 * @author nih
 * @version 17/3/23
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
public class Module {
    private String moduleID;
    private ArrayList<String> questionBanks;

    public Module (String startModuleID){
        moduleID = startModuleID;
        this.questionBanks = new ArrayList<>();
    }
    public String toString(){
        String output = "ModuleID: " + this.moduleID;
        if (questionBanks != null) {
            output += "questionBanks: " + questionBanks + "\n";
        } else {
            output += "questionBanks: none \n";
        }
        return output;
    }

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

    public void loadQuestionBanks(){}
    public void addQuestionBank(String questionBankID){

        questionBanks.add(questionBankID);
    }

    public void removeQuestionBank(String questionBankID){
        for (int i = 0; i < questionBanks.size(); i++){
            if (questionBanks.get(i).equals(questionBankID)){
                questionBanks.remove(i);
            }
        }
    }

    public String getModuleID(){
        return this.moduleID;
    }

    public void setModuleID(String newModuleID) {
        this.moduleID = newModuleID;
    }





}
