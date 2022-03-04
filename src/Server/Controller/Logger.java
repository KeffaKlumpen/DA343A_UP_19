/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Server.Controller;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// TODO: Implement function for seperate logs. (One for network, one for thread stuff, one for...?)
/**
 * Creates a .log file for each server execution.
 * <p>Log files are named uuuuMMdd-HHmmss-uuuuMMdd-HHmmss, representing the first and last log made to this file.</p>
 */
public class Logger {
    private static final DateTimeFormatter fileNameFormatter = DateTimeFormatter.ofPattern("uuuuMMdd-HHmmss");
    private static final DateTimeFormatter logEntryFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");

    private static Logger instance;
    private final String logPrefix;

    private String lastFilePath;

    private File currentFile;

    private static Logger getInstance(){
        if(instance == null){
            instance = new Logger();
        }
        return instance;
    }

    private Logger(){
        logPrefix = getCurrentDateTime(fileNameFormatter) + "_";
        currentFile = new File("logs/" + logPrefix + ".log");

        try {
            if(!currentFile.createNewFile()){
                throw new IOException("LogFile with that name already exists!");
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            lastFilePath = currentFile.getPath();
        }
    }

    /**
     * Writes the logEntry to the currently active .log file.
     * Appends the current DateTime as a prefix.
     * @param logEntry
     */
    public static void log(String logEntry){
        getInstance().logToFile(logEntry);
    }

    /**
     * Writes the logEntries to the currently active .log file.
     * Appends the current DateTime as a prefix to each row.
     * @param logEntries
     */
    public static void log(String[] logEntries){
        Logger logger = getInstance();
        for (String logEntry : logEntries) {
            logger.logToFile(logEntry);
        }
    }

    private void logToFile(String logEntry){
        File f = new File(lastFilePath);

        if(f.exists()){
            try (BufferedWriter bw = new BufferedWriter(
                    new FileWriter(f, StandardCharsets.UTF_8, true))){

                // FileOutputStream fos = new FileOutputStream(f, true);
                bw.write(getCurrentDateTime(logEntryFormatter) + ": " + logEntry);
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
        return logPrefix + getCurrentDateTime(fileNameFormatter) + ".log";
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
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Logger.log("I am logging!");
        sc.nextLine();
        Logger.log("I am logging 2!");
    }
}
