package com.gmail.mchelplagoverse.LVWhiteBoard;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by lake.smith on 5/20/2018.
 */
public class WBCommandProcessor implements CommandExecutor {



    //String Bank
    //plugin logo
    String pluginLogo = ChatColor.GOLD + "[LV WhiteBoard] ";

    //Plugin Author
    String pluginAuthor = "L4G0130$$";

    //Argument Variables
    String avCourseCode = " <course_code>";
    String avChapter = " chapter";
    String avChapterNumber = " <number>";
    String avMessage = " <message>";
    String avStudent = " <student_name>";

    //Usage strings
    String usageEnroll = pluginLogo + ChatColor.GRAY + "/wb enroll" + avCourseCode;
    String usageBuy = pluginLogo + ChatColor.GRAY + "/wb buy materials" + avCourseCode;
    String usagePrice = pluginLogo + ChatColor.GRAY + "/wb price materials" + avCourseCode;
    String usageFinish = pluginLogo + ChatColor.GRAY + "/wb finish" + avCourseCode + avChapter + avChapterNumber;
    String usageHelp = pluginLogo + ChatColor.GRAY + "/wb help" + avCourseCode + avMessage;
    String usageComplete = pluginLogo + ChatColor.GRAY + "/wb complete" + avCourseCode;
    String usageMessage = pluginLogo + ChatColor.GRAY + "/wb message " + avStudent + avMessage;
    String usageProgress = pluginLogo + ChatColor.GRAY + "/wb progress " + avCourseCode;
    String usage = pluginLogo + ChatColor.GRAY + "Valid commands are: /wb, /wb enroll, /wb buy, /wb price" +
                    " /wb finish, /wb help, /wb message, & /wb complete";

    //message strings
    String processing = pluginLogo + ChatColor.GRAY + "processsing...";
    String error_noPermissionEnroll = pluginLogo + ChatColor.RED + "You don't have permission to enroll in this course.";
    String error_noCourseFound = pluginLogo + ChatColor.RED + "Course was not found.";
    String error_notEnoughMoney = pluginLogo + ChatColor.RED + "You don't have enough money to enroll into this course.";
    String error_chapterNoExist = pluginLogo + ChatColor.RED + "The chapter does not exist";
    String error_notEnoughArguments = pluginLogo + ChatColor.RED + "You did not enter enough arguments.";
    String error_tooManyArguments = pluginLogo + ChatColor.RED + "You did not enter enough arguments.";
    String error_allChaptersNotCompleted = pluginLogo + ChatColor.RED + "All chapters not completed.";
    String error_notEnrolled = pluginLogo + ChatColor.RED + "You are not enrolled in that course.";
    String error_AlreadyEnrolled = pluginLogo + ChatColor.RED + "You are already enrolled in this course...";
    String error_youDidntWriteChapter = pluginLogo + ChatColor.RED + "You didn't write 'chapter'; please try your command again.";
    String error_courseAlreadyCompleted = pluginLogo + ChatColor.RED + "Course is already completed...";
    String error_chapterAlreadyCompleted = pluginLogo + ChatColor.RED + "Chapter is already completed...";
    String error_insuffcientPermission = pluginLogo + ChatColor.RED + "Insuffcient permission to execute this command...";
    String info_CommandComingSoon = pluginLogo + ChatColor.GRAY + "Command coming soon!";
    String info_enrollSuccess = pluginLogo + ChatColor.AQUA + "Success! You were enrolled.";
    String info_chapterCompleteSuccess = pluginLogo + ChatColor.AQUA + "Success! You completed the chapter.";
    String info_courseCompleted = pluginLogo + ChatColor.AQUA + "Congratulations! You completed your course: ";
    String info_accountWith = pluginLogo + ChatColor.GRAY + "You were charged: $";
    String infoConfigReloaded = pluginLogo + ChatColor.GRAY + "Course data was reloaded...";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        //command sender
        Player p = (Player) commandSender;

        //player data file

        //config file

        //economy
        //plugin.getEcononomy();

        //Strings

        //registrar


        Registrar registrar = new Registrar();


        if(args.length == 0 && command.getLabel().equalsIgnoreCase("wb")){
            p.sendMessage("LV WhiteBoard, by: " + pluginAuthor);
            p.sendMessage(usage);
            return true;
        }

        if (args.length == 1){

            switch (args[0].toLowerCase()){

                case "enroll":
                    p.sendMessage(usageEnroll);
                    return true;

                case "buy":
                    p.sendMessage(usageBuy);
                    p.sendMessage(info_CommandComingSoon);
                    return true;

                case "price":
                    p.sendMessage(usagePrice);
                    p.sendMessage(info_CommandComingSoon);
                    return true;

                case "finish":
                    p.sendMessage(usageFinish);
                    return true;

                case "help":
                    p.sendMessage(usageHelp);
                    p.sendMessage(info_CommandComingSoon);
                    return true;

                case "message":
                    p.sendMessage(usageMessage);
                    return true;

                case "complete":
                    p.sendMessage(usageComplete);
                    return true;

                case "progress":
                    p.sendMessage(usageProgress);
                    return true;

                case "reload":
                    if(p.hasPermission("lvwb.reload")){
                        registrar.reloadConfig();
                        p.sendMessage(infoConfigReloaded);
                        return true;
                    }else
                    {
                        p.sendMessage(error_insuffcientPermission);
                        return  true;
                    }

                default:
                    p.sendMessage(usage);
                    return true;
            }

        }

        if (args.length == 2){

            switch (args[0].toLowerCase()){

                //done
                case "enroll":
                    p.sendMessage(processing);

                    if (registrar.courseExist(args[1], p)) {
                        //moves on to next code block

                        if (registrar.permission(p, args[1])) {
                            //moves on to next code block

                            if(registrar.hasEnoughMoney(p, args[1])){

                                //fixed
                                if(registrar.enroll(p, args[1])){
                                    p.sendMessage(error_AlreadyEnrolled);
                                    return true;
                                }
                                else{
                                    p.sendMessage(info_accountWith + registrar.amountWithdrawn(args[1]));
                                    p.sendMessage(info_enrollSuccess);
                                    return true;}
                            }
                            else {
                                p.sendMessage(error_notEnoughMoney);
                                return true;
                            }
                        }
                        else{
                            p.sendMessage(error_noPermissionEnroll);
                            return true;
                        }
                    }
                    else {
                        p.sendMessage(error_noCourseFound);
                        return true;
                    }

                case "finish":
                    p.sendMessage(error_notEnoughArguments);
                    p.sendMessage(usageFinish);
                    return true;

                //done
                case "complete":
                    p.sendMessage(processing);

                    if (registrar.courseExist(args[1], p)){

                        if (registrar.isEnrolled(p, args[1])){

                            if(registrar.allChaptersComplete (p, args[1])){

                                if(!registrar.courseCompleted(p, args[1])) {

                                    registrar.complete(p, args[1]);

                                    p.sendMessage(info_courseCompleted + args[1]);
                                    return true;

                                } else {
                                    p.sendMessage(error_courseAlreadyCompleted);
                                    return true;
                                }

                            }
                            else {
                                p.sendMessage(error_allChaptersNotCompleted);
                                return true;
                            }
                        }
                        else {
                            p.sendMessage(error_notEnrolled);
                            return true;
                        }

                    }
                    else {
                        p.sendMessage(error_noCourseFound);
                        return true;
                    }

                case "buy":
                    p.sendMessage(usageBuy);
                    p.sendMessage(info_CommandComingSoon);
                    return true;

                case "price":
                    p.sendMessage(usagePrice);
                    p.sendMessage(info_CommandComingSoon);
                    return true;

                case "help":
                    p.sendMessage(usageHelp);
                    p.sendMessage(info_CommandComingSoon);
                    return true;

                case "message":
                    p.sendMessage(usageMessage);
                    p.sendMessage(info_CommandComingSoon);
                    return true;

                //done
                case "progress":

                    p.sendMessage(processing);

                    if (registrar.courseExist(args[1], p)){

                        if (registrar.isEnrolled(p, args[1])){

                            registrar.progressCheck(p, args[1]);
                            return true;
                        }
                        else {
                            p.sendMessage(error_notEnrolled);
                            return true;
                        }

                    }
                    else {
                        p.sendMessage(error_noCourseFound);
                        return true;
                    }

                default:
                    p.sendMessage(usage);
                    return true;

            }
        }

        if (args.length == 3){

            switch (args[0].toLowerCase()){

                case "enroll":
                    p.sendMessage(usageEnroll);
                    p.sendMessage(error_tooManyArguments);
                    return true;

                case "buy":
                    p.sendMessage(usageBuy);
                    p.sendMessage(info_CommandComingSoon);
                    return true;

                case "price":
                    p.sendMessage(usagePrice);
                    p.sendMessage(info_CommandComingSoon);
                    return true;

                case "finish":
                    p.sendMessage(usageFinish);
                    p.sendMessage(error_tooManyArguments);
                    return true;

                case "help":
                    p.sendMessage(usageHelp);
                    p.sendMessage(info_CommandComingSoon);
                    return true;

                case "message":
                    p.sendMessage(usageMessage);
                    p.sendMessage(info_CommandComingSoon);
                    return true;

                case "complete":
                    p.sendMessage(usageComplete);
                    p.sendMessage(error_tooManyArguments);
                    return true;

                case "progress":
                    p.sendMessage(usageProgress);
                    p.sendMessage(error_tooManyArguments);
                    return true;

                default:
                    p.sendMessage(usage);
                    return true;
            }

        }

        if (args.length == 4){

            switch (args[0].toLowerCase()) {

                case "enroll":
                    p.sendMessage(error_tooManyArguments);
                    p.sendMessage(usageEnroll);
                    return true;

                case "buy":
                    p.sendMessage(usageBuy);
                    p.sendMessage(info_CommandComingSoon);
                    return true;

                case "price":
                    p.sendMessage(usagePrice);
                    p.sendMessage(info_CommandComingSoon);
                    return true;

                //done
                case "finish":
                    p.sendMessage(processing);

                    if (registrar.courseExist(args[1], p)) {
                        //moves on to next code block

                        if (registrar.isEnrolled(p, args[1])) {
                            //moves on to next code block

                            if (registrar.wasChapterEntered(args[2])) {
                                //execute code to enroll into course

                                if (registrar.isInteger(args[3])) {
                                    //execute code

                                    if (registrar.doesChapterExist(p, args[1], Integer.parseInt(args[3]))) {
                                        //execute code
                                        if(!registrar.chapterCompleted(p, args[1], Integer.parseInt(args[3]))){

                                            registrar.finish(p, args[1], Integer.parseInt(args[3]));
                                            p.sendMessage(info_chapterCompleteSuccess);
                                            return true;
                                        }
                                        else{
                                            p.sendMessage(error_chapterAlreadyCompleted);
                                              return true;
                                        }

                                    } else {
                                        p.sendMessage(error_chapterNoExist);
                                        return true;
                                    }
                                } else {
                                    p.sendMessage(error_chapterNoExist);
                                    return true;
                                }
                            } else {
                                p.sendMessage(error_youDidntWriteChapter);
                                return true;
                            }
                        } else {
                            p.sendMessage(error_noCourseFound);
                            return true;
                        }
                    } else {
                        p.sendMessage(error_noPermissionEnroll);
                        return true;
                    }

                case "help":
                    p.sendMessage(usageHelp);
                    p.sendMessage(info_CommandComingSoon);
                    return true;

                case "message":
                    p.sendMessage(usageMessage);
                    p.sendMessage(info_CommandComingSoon);
                    return true;

                case "complete":
                    p.sendMessage(error_tooManyArguments);
                    p.sendMessage(usageComplete);
                    return true;

                case "progress":
                    p.sendMessage(error_tooManyArguments);
                    p.sendMessage(usageProgress);
                    return true;

                default:
                    p.sendMessage(usage);
                    return true;
            }
        }

        if (args.length > 4){
            p.sendMessage(error_tooManyArguments);
            p.sendMessage(usage);
            return true;
        }
        else {
            p.sendMessage(usage);
            return true;
            }
        }
}