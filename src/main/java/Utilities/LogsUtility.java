package Utilities;

import org.apache.logging.log4j.LogManager;

// this class concerns with the Logs like for TestNG and my Logs
public class LogsUtility {

    public static final String Logs_Path = "testPages.test-outputs/Logs";

    public static void LoggerTrace(String message) {

        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].toString())
                .trace(message);
    }

    public static void LoggerDebug(String message) {

        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].toString()).debug(message);
    }

    public static void LoggerInfo(String message) {

        LogManager.getLogger(Thread.currentThread().getStackTrace()[3].toString()).info(message);
    }

    public static void LoggerWarn(String message) {

        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].toString()).warn(message);
    }

    public static void LoggerError(String message) {

        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].toString()).error(message);
    }

    public static void LoggerFetal(String message) {

        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].toString()).fatal(message);
    }

}
