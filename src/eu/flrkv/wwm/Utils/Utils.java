package eu.flrkv.wwm.Utils;

public class Utils {


    public static void consoleLog(String pPrefix, String pMessage)
    {
        System.out.println("["+pPrefix+"] " + pMessage);
    }

    public static void exitProgram(int pStatus)
    {
        Utils.consoleLog("INFO", "Program is stopping...");
        System.exit(pStatus);
    }

    public static void sleep(long pMillis)
    {
        try {
            Thread.sleep(pMillis);
        } catch (InterruptedException e) {
            // Null
        }
    }



}
