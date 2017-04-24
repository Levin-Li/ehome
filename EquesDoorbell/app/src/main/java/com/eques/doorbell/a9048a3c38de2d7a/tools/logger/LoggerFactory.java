package com.eques.doorbell.a9048a3c38de2d7a.tools.logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2016/10/18.
 */
public class LoggerFactory {

    public static int LEVEL_DEBUG =1;
    public static int LEVEL_INFO =2;
    public static int LEVEL_ERROR =3;
    private static boolean isLoggerFile = false;
    private static int level = LEVEL_DEBUG;
    private static Map<String,Logger> loggerMap = new ConcurrentHashMap<String,Logger>();
    public static Logger getLogger(Class clazz){
        if(loggerMap.containsKey(clazz.getName())){
            return loggerMap.get(clazz.getName());
        }else{
            Logger logger = null;
            if(isLoggerFile){
                logger = new FileLogger();
            }
            else{
                logger = new SimpleLogger();
            }
            logger.setClassName(clazz.getName());
            loggerMap.put(clazz.getName(),logger);
            return logger;
        }
    }
    public void reset(){

    }
    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        LoggerFactory.level = level;
    }

    public static boolean isLoggerFile() {
        return isLoggerFile;
    }

    public static void setIsLoggerFile(boolean isLoggerFile) {
        LoggerFactory.isLoggerFile = isLoggerFile;
    }
}
