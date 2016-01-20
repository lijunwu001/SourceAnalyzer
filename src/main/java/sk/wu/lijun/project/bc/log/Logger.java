package sk.wu.lijun.project.bc.log;

import sk.wu.lijun.project.bc.enums.LogLevel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

/**
 * Created by Lijun on 2016-01-20.
 */
public class Logger {

    private static Logger logger;
    private OutputStreamWriter logFile = null;

    private Logger(){
    }

    public void setLogFile(String logFile){
        try {
            this.logFile = new OutputStreamWriter(new FileOutputStream(logFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Logger getLogger(){
        if (logger == null){
            logger = new Logger();
        }
        return logger;
    }

    public void log(String msg) {
        String res = getLogLine(LogLevel.INFO, msg);
        writeLog(res);
    }

    public void error(String msg){
        String res = getLogLine(LogLevel.ERROR, msg);
        writeLog(res);
    }

    public void warn(String msg){
        String res = getLogLine(LogLevel.WARN, msg);
        writeLog(res);
    }

    private String getLogLine(LogLevel level, String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append(new Date());
        sb.append(" [");
        switch (level){
            case ERROR:
                sb.append("ERROR");
                break;
            case INFO:
                sb.append("INFO");
                break;
            case WARN:
                sb.append("WARN");
                break;
            default:
                break;
        }
        sb.append("]: ");
        sb.append(msg);
        return sb.toString();
    }

    private void writeLog(String log) {
        if (logFile != null) {
            try {
                logFile.write(log);
                logFile.write("\r\n");
                logFile.flush();
            } catch (IOException e) {
            }
        } else {
            System.out.println(log);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (logFile!=null) logFile.close();
        }catch (Exception e){

        }
        super.finalize();
    }
}
