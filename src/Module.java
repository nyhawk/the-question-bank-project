/**
 * @author nih
 * @version 17/3/23
 */

import java.util.ArrayList;
public class Module {
    private String moduleID;
    private String moduleName;
    private int yearOfStudy;

    private String teacher;
    private ArrayList<String> questionBanks;

    public Module (String startModuleID, String startModuleName){
        moduleID = startModuleID;
        moduleName = startModuleName;
    }
    public String toString(){
        String output = "ModuleID: " + this.moduleID + "\n moduleName: " + this.moduleName + "\n yearOfStudy: " + this.yearOfStudy
                + "\n teacher: " + this.teacher + "\n";
        if (questionBanks != null) {
            output += "questionBanks: " + questionBanks + "\n";
        } else {
            output += "questionBanks: none \n";
        }
        return output;
    }

    public void saveQuestionBank(){}

    public void loadQuestionBanks(){}
    public void addQuestionBank(String questionBankID){
        questionBanks = new ArrayList<>();
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

    public String getModuleName(){
        return this.moduleName;
    }

    public void setModuleName(String newModuleName){
        this.moduleName = newModuleName;
    }

    public int getYearOfStudy(){
        return this.yearOfStudy;
    }

    public void setYearOfStudy(int newYearOfStudy){
        this.yearOfStudy = newYearOfStudy;
    }

    public String getTeacher(){return this.teacher;}

    public void setTeacher(String newTeacher){this.teacher = newTeacher;}



}
