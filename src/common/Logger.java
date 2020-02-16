package common;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Object for all logging.
 */
public class Logger {

    private static final Logger INSTANCE = new Logger(getCurrentTime());

    private final String logDirPath = "logs/";

    private PrintWriter writer = null;

    private Logger(String logFileName) {
        try {
            this.writer = new PrintWriter(new FileWriter(makeFullPath(logFileName),true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

    private String makeFullPath(String logFileName) {
        return logDirPath + logFileName +".txt";
    }

    public static Logger getInstance() {
        return INSTANCE;
    }

    /**
     * Add error log entry.
     * @param errorMessage
     */
    public void logError(String errorMessage) {
        writer.write(getCurrentTime() + " ERROR: " + errorMessage);
    }

    /**
     * Add info log entry.
     * @param infoMessage
     */
    public void logInfo(String infoMessage) {
        writer.write(getCurrentTime() + " INFO: " + infoMessage);
    }

    private void closeWriter() {
        this.writer.close();
    }
}
