/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Server.Controller;

import Model.ChatMessage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// TODO: Implement function for separate logs. (One for network, one for thread stuff, one for...?)
/**
 * Creates a .log file for each server execution.
 * <p>Log files are named uuuuMMdd-HHmmss_uuuuMMdd-HHmmss, representing the first and last log made to this file.</p>
 */
public class Logger {
    public static final DateTimeFormatter FILE_NAME_FORMATTER = DateTimeFormatter.ofPattern("uuuuMMdd-HHmmss");
    public static final DateTimeFormatter LOG_ENTRY_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");

    private static Logger instance;
    private final String logPrefix;

    private String lastFilePath;

    private static Logger getInstance(){
        if(instance == null){
            instance = new Logger();
        }
        return instance;
    }

    /**
     * Creates a .log file, named as "dateOfCreation_dateOfLastChange.log"
     * Creates the ./logs/ directory if needed.
     */
    private Logger(){
        logPrefix = getCurrentDateTime(FILE_NAME_FORMATTER) + "_";

        File directory = new File("logs/");
        File logFile = new File(directory, logPrefix + ".log");
        try {
            if (!directory.exists()) {
                if (!directory.mkdir())
                    throw new IOException("Couldn't create directory.");
            }

            if (!logFile.exists()) {
                if (!logFile.createNewFile())
                    throw new IOException("Couldn't create log file.");
            }

            lastFilePath = logFile.getPath();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Writes the logEntry to the currently active .log file.
     * @param logEntry String to be logged.
     */
    public static void log(String logEntry){
        getInstance().logToFile(logEntry);
    }

    /**
     * Logs the information of the ChatMessage to be sent to the currently active .log file.
     * @param cm ChatMessage to be logged.
     */
    public static void logChatMessage(ChatMessage cm){
        String logEntry = String.format("Meddelande: (Fr??n: %s till %s) - Text: %s",
                cm.getSender().getUsername(), cm.getRecipientsNames(), cm.getMsgText());
        if(cm.getMsgIcon() != null){
            logEntry = logEntry.concat(" Bild: " + cm.getMsgIcon().toString());
        }
        getInstance().logToFile(logEntry);
    }

    /**
     * Logs the current connection-status of a user to the currently active .log file.
     * @param username Username of the user to be logged
     * @param status Current connection-status of the user.
     */
    public static void logUserStatus(String username, String status){
        getInstance().logToFile(username + " " + status);
    }

    /**
     * Writes the logEntries to the currently active .log file.
     * Appends the current DateTime as a prefix to each row.
     * @param logEntries Array of strings to be logged.
     */
    public static void log(String[] logEntries){
        Logger logger = getInstance();
        for (String logEntry : logEntries) {
            logger.logToFile(logEntry);
        }
    }

    /**
     * Writes the logEntry to the currently active .log file.
     * Appends the current DateTime as a prefix.
     * @param logEntry String to be logged.
     */
    private void logToFile(String logEntry){
        File f = new File(lastFilePath);

        if(f.exists()){
            try (BufferedWriter bw = new BufferedWriter(
                    new FileWriter(f, StandardCharsets.UTF_8, true))){

                // FileOutputStream fos = new FileOutputStream(f, true);
                bw.write(getCurrentDateTime(LOG_ENTRY_FORMATTER) + ": " + logEntry);
                bw.newLine();
                bw.flush();

                bw.close();

                renameFile();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else {
            System.err.println("LogFile doesn't exist!");
        }
    }

    private void renameFile() throws IOException {
        Path source = Paths.get(lastFilePath);
        Path newSource = Files.move(source, source.resolveSibling(getNewFileName()));
        lastFilePath = new File(String.valueOf(newSource)).getPath(); // this is so dumb
    }

    private String getNewFileName(){
        return logPrefix + getCurrentDateTime(FILE_NAME_FORMATTER) + ".log";
    }

    private static String getCurrentDateTime(DateTimeFormatter dtf){
        return ZonedDateTime.now().toLocalDateTime().format(dtf);
    }

    /**
     * Test Logger functionality!
     * <p>Creates a file in "logs/".
     * Adds the line "I am logging!".
     * Waits for user-input, and then adds another line before closing.
     * </p>
     * @param args args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Logger.log("I am logging!");
        sc.nextLine();
        Logger.log("I am logging 2!");


    }
}
