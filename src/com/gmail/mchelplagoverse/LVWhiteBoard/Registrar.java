package com.gmail.mchelplagoverse.LVWhiteBoard;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by lake.smith on 5/20/2018.
 */
public class Registrar{

    Registrar(){

    }


    //enroll variables
    Player p;
    FileConfiguration pData;
    FileConfiguration cData;


    //permission variables

    String permission;
    String c;

    //boolean variables
    boolean result;

    //Common strings for c/p file
    String dot = ".";
    String time;

    //Strings for pData File
    String pDataRoot = "Courses";
    String pDataFirstUseDate = "First_Use";
    String pDataPlayerName = "Player_Name";
    String pDataCourseActual;
    String pDataCourseInstructor= "Instructor";
    String pDataCourseInstructorActual;
    String pDataCourseChapterCount = "Chapter_Count";
    int pDataCourseChapterCountActual;
    String pDataChapterRemaining = "Remaining";
    int pDataChapterRemainingActual;
    String pDataCourseCompleted = "Completed";
    boolean pDataCourseCompletedActual;
    String pDataCourseMaterials = "Materials";
    String pDataCourseMaterialActual;
    String pDataCourseMaterialActualPurchased = "Purchased";
    String pDataCourseChapters = "Chapters";
    String pDataCourseChapter = "chapter";
    String pDataCourseChapterActual;
    String pDataCourseChaptersCompleted = "Completed";
    boolean pDataCourseChaptersCompletedActual;
    String pDataCourseChaptersCompletionDate= "Completed_Date";

    //Strings for cData File

    String cDataRoot = pDataRoot;
    String cCourseName;
    String cCourseInstructor = pDataCourseInstructor;
    String cCourceInstructorActual;
    String cCourseEnabled = "Enabled";
    boolean cCourseEnabledActual;
    String cCoursePermission = "Permission";
    String cCoursePermissionActual;
    String cCourseCost = "Cost";
    double cCourseCostActual;
    String cCourseChapterCount = pDataCourseChapterCount;
    int cCourseChapterCountActual;
    String cCourseLastUpdated = "Last_Updated";
    String cCourseLastUpdatedPlayer = "Player_Name";
    String cCourseLastUpdatedPlayerActual;
    String cCourseLastUpdatedDate = "Date";
    String cCourseLastUpdatedDateActual;
    String cCourseCommands_to_Execute = "Commands_to_Execute";
    String cCourseMaterials = pDataCourseMaterials;
    String cCourseMaterialsActual;
    String cCourseChapters = pDataCourseChapters;
    String cCourseChaptersActual;
    String cCourseChaptersActualTitle = "Title";
    String cCourseChaptersActualTitleActual;

    //done
    public FileConfiguration playerData (Player player){

        String fileName = player.getUniqueId().toString();

        File playerData = new File(BootLVWhiteBoard.inst().getDataFolder() + "//data//", fileName + ".yml");
        this.pData = YamlConfiguration.loadConfiguration(playerData);

        return pData;
    }
    public FileConfiguration courseData (){

        this.cData = BootLVWhiteBoard.inst().getConfig();

        return cData;
    }

    public void reloadConfig(){

        BootLVWhiteBoard.inst().saveConfig();
    }

    //done
    public boolean enroll (Player player, String course){

        this.p = player;
        this.c = course;

        this.pData = playerData(p);
        this.cData = courseData();

        if(!pdataExist(p)){

            playerDataGenerator(p);

            if (!pData.contains(pDataRoot + dot + c)){
                enroll(p, c, pData, cData);
                return false;
            }

            else{
                return true;
            }
        }
        else{
            if (!pData.contains(pDataRoot + dot + c)){
            enroll(p, c, pData, cData);
            return false;
            }

            else{
                return true;
            }}}

    //done
    public String getCurrentDate(){

        Date now = new Date();
        String currentDate = now.toString();

        return currentDate;
    }

    //done
    public void savePData (FileConfiguration inboundData, Player player){

        this.pData = inboundData;
        this.p = player;
        String fileName = p.getUniqueId().toString();
        File playerData = new File(BootLVWhiteBoard.inst().getDataFolder() + "//data//", fileName + ".yml");

        try{
            pData.save(playerData);

        }catch (IOException e){
            p.sendMessage(ChatColor.RED + "Error saving your file; please contact your server's admin for more details...");
            e.printStackTrace();
        }
    }

    //done
    public void playerDataGenerator(Player player){

        this.p = player;
        this.pData = playerData(p);

        this.time = getCurrentDate();

        pData.set(pDataRoot + dot + pDataFirstUseDate, time);
        pData.set(pDataRoot + dot + pDataPlayerName, p.getName());

        savePData(pData, p);
    }




    public void enroll (Player player, String course, FileConfiguration playerDataFile, FileConfiguration courseDataFile){

        this.p = player;
        this.c = course;
        this.pData = playerDataFile;
        this.cData = courseDataFile;

        this.pDataCourseActual = c;

        this.pDataCourseInstructorActual = cData.getString(cDataRoot + dot + c + dot + cCourseInstructor);
        this.pDataCourseChapterCountActual = cData.getInt(cDataRoot + dot + c + dot + cCourseChapterCount);
        this.pDataChapterRemainingActual = pDataCourseChapterCountActual;
        this.pDataCourseMaterialActual = cData.getString(cDataRoot + dot + c + dot + cCourseMaterials);


        this.pDataCourseCompletedActual = false;
        this.pDataCourseChaptersCompletedActual = false;

        pData.set(pDataRoot + dot + pDataCourseActual + dot + pDataCourseInstructor, pDataCourseInstructorActual);
        pData.set(pDataRoot + dot + pDataCourseActual + dot + pDataCourseChapterCount, pDataCourseChapterCountActual);
        pData.set(pDataRoot + dot + pDataCourseActual + dot + pDataChapterRemaining, pDataChapterRemainingActual);
        pData.set(pDataRoot + dot + pDataCourseActual + dot + pDataCourseCompleted, pDataCourseCompletedActual);
        pData.set(pDataRoot + dot + pDataCourseActual + dot + pDataCourseMaterials, pDataCourseMaterialActual);

        this.pDataCourseChapterCountActual = BootLVWhiteBoard.inst().getConfig().getInt(cDataRoot + dot + c + dot + cCourseChapterCount);

        int i = 0;

        while(i <= pDataCourseChapterCountActual - 1){

            ++i;

            String iToString = Integer.toString(i);

            pData.set(pDataRoot + dot + pDataCourseActual + dot + pDataCourseChapters, pDataCourseChapter + iToString);
            savePData(pData, p);
        }

        int j = 0;

        while(j <= pDataCourseChapterCountActual - 1){

            ++j;

            String jToString = Integer.toString(j);
            pData.set(pDataRoot + dot + pDataCourseActual + dot + pDataCourseChapters + dot + pDataCourseChapter + jToString + dot + pDataCourseChaptersCompleted, false);
            pData.set(pDataRoot + dot + pDataCourseActual + dot + pDataCourseChapters + dot + pDataCourseChapter + jToString + dot + pDataCourseChaptersCompletionDate, "");
            savePData(pData, p);

        }


    }

    public void addChapter (String course, int numberOfChaptersToLoad){

    }

    public void complete (Player player, String course){

        this.p = player;
        this.c = course;

        this.pData = playerData(p);
        this.cData = courseData();

        pData.set(pDataRoot + dot + c + dot + pDataCourseCompleted, true);

        savePData(pData, p);

        List<String> cmdsToExecute = cData.getStringList(cDataRoot + dot + c + dot + cCourseCommands_to_Execute);

        int amountOfCmds = cmdsToExecute.size();

        try {
            for (int i = 0; i < amountOfCmds; i++){

                BootLVWhiteBoard.inst().getServer().dispatchCommand(BootLVWhiteBoard.inst().getServer().getConsoleSender(), cmdsToExecute.get(i).replace("$player", p.getName()));
            }
        }catch (IndexOutOfBoundsException e){

        }

    }

    public boolean chapterCompleted (Player player, String course, int chapterNumber){

        this.p = player;
        this.c = course;

        this.pData = playerData(p);

        this.result = pData.getBoolean(pDataRoot + dot + c + dot + pDataCourseChapters + dot + pDataCourseChapter + chapterNumber + dot + pDataCourseCompleted);

        return result;
    }

    public boolean courseCompleted (Player player, String course){

        this.p = player;
        this.c = course;

        this.pData = playerData(p);

        this.result = pData.getBoolean(pDataRoot + dot + c + dot + pDataCourseChaptersCompleted);

        return result;
    }

    public void finish (Player player, String course, int chapterNumber){

        this.p = player;
        this.c = course;

        this.pData = playerData(p);

        pData.set(pDataRoot + dot + c + dot +
                pDataCourseChapters + dot + pDataCourseChapter + chapterNumber + dot + pDataCourseChaptersCompleted, true );

        pData.set(pDataRoot + dot + c + dot +
                pDataCourseChapters + dot + pDataCourseChapter + chapterNumber + dot + pDataCourseChaptersCompletionDate, getCurrentDate());

        this.pDataChapterRemainingActual = pData.getInt(pDataRoot + dot + c + dot + pDataChapterRemaining);

        int remainder = pDataChapterRemainingActual - 1;

        pData.set(pDataRoot + dot + c + dot + pDataChapterRemaining, remainder);

        savePData(pData, p);
    }

    //Needs to enroll player into a course
    public boolean permission (Player player, String course) {

        //define player variable
        this.p = player;
        this.c = course;

        this.cData = courseData();

        //testing
        this.permission = cData.getString(this.cDataRoot + this.dot + this.c + this.dot + this.cCoursePermission);

        //assigns exists the result of the permission check
        this.result = p.hasPermission(permission);

        //returns the true/false value for the permission
        return result;
    }

        //Needs to enroll player into a course
    public boolean hasEnoughMoney (Player player, String course) {

        //define player variable
        this.p = player;

        //define course string variable
        this.c = course;

        this.cData = courseData();

        this.cCourseCostActual = cData.getDouble(cDataRoot + dot + c + dot + cCourseCost);

       if (BootLVWhiteBoard.inst().getEcononomy().getBalance(p) >= cCourseCostActual){

           BootLVWhiteBoard.inst().getEcononomy().withdrawPlayer(p, cCourseCostActual);

           this.result = true;
       }
       else{
           this.result = false;
       }

        //returns the true/false value
        return result;
    }

    public double amountWithdrawn(String course){

        this.c = course;

        this.cData = courseData();

        this.cCourseCostActual = cData.getDouble(cDataRoot + dot + c + dot + cCourseCost);

        return cCourseCostActual;

    }

    public boolean courseExist (String course, Player player) {

        //define course variable
        this.c = course;
        this.p = player;

        this.cData = courseData();

        this.result = cData.contains(this.cDataRoot + this.dot + c);


        return result;
    }

    public boolean isEnrolled (Player player, String course){

        //define course variable
        this.c = course;
        this.p = player;

        this.pData = playerData(p);

        this.result = pData.contains(this.pDataRoot + this.dot + c);

        return result;
    }

    public boolean isInteger (String argsThree){
        this.result = true;

        try{
            int testInt = Integer.parseInt(argsThree);

        }catch (Exception e){
            this.result = false;
        }

        return result;
    }

    public boolean doesChapterExist (Player player, String course, int chapterNumber){

        this.c = course;
        this.p = player;

        this.pData = playerData(p);

        this.result = pData.contains(pDataRoot + dot + c + dot + pDataCourseChapters + dot + pDataCourseChapter + chapterNumber);

        return result;
    }

    public boolean wasChapterEntered (String argsTwo){

        this.result = argsTwo.equalsIgnoreCase("chapter");

        return result;
    }

    public boolean allChaptersComplete (Player player, String course){

        this.c = course;
        this.p = player;

        this.pData = playerData(p);

        //this.result = pData.getInt(pDataRoot + dot + c + dot + pDataChapterRemaining) <= 0;

        this.pDataCourseChapterCountActual = cData.getInt(cDataRoot + dot + c + dot + cCourseChapterCount);

        int i = 0;
        int j = 0;

        for (i = 1; i <= pDataCourseChapterCountActual; i++){

            if (pData.getBoolean(pDataRoot + dot + c + dot +
                    pDataCourseChapters + dot + pDataCourseChapter + i + dot + pDataCourseChaptersCompleted)){
                    j++;
                    //p.sendMessage("This is j: " + j);
            }
            //p.sendMessage("This is i: " + i);

        }
        //p.sendMessage("This is j: " + j);
        //p.sendMessage("This is i: " + i);

        result =  j == pDataCourseChapterCountActual;

        return result;
    }

    public void progressCheck(Player player, String course){

        this.p = player;
        this.c = course;

        this.pData = playerData(p);

        p.sendMessage( ChatColor.GRAY + "You have this many chapters(s) remaining: " + pData.getInt(pDataRoot + dot + c + dot + pDataChapterRemaining));

        this.pDataCourseChapterCountActual = cData.getInt(cDataRoot + dot + c + dot + cCourseChapterCount);

        int i = 0;

        for (i = 1; i <= pDataCourseChapterCountActual; i++){

            boolean trueOrFalse = pData.getBoolean(pDataRoot + dot + c + dot +
                    pDataCourseChapters + dot + pDataCourseChapter + i + dot + pDataCourseChaptersCompleted);

            p.sendMessage("Chapter" + i + " complete: " + trueOrFalse );
        }

    }

    public boolean pdataExist(Player player){

        this.p = player;

        this.p = player;
        String fileName = p.getUniqueId().toString();
        File playerData = new File(BootLVWhiteBoard.inst().getDataFolder() + "//data//", fileName + ".yml");

        this.result = playerData.exists();

        return result;
    }
}
