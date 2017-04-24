package com.eques.doorbell.a9048a3c38de2d7a.tools.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/18.
 */
public class SimpleLogger extends AbstractLogger {
    @Override
    public void debug(String content) {
        if(LoggerFactory.getLevel() <= LoggerFactory.LEVEL_DEBUG){
            writeLogger(getLevelString(LoggerFactory.LEVEL_DEBUG),content,getClassName());
        }
    }

    @Override
    public void info(String content) {
        if(LoggerFactory.getLevel() <= LoggerFactory.LEVEL_INFO){
            writeLogger(getLevelString(LoggerFactory.LEVEL_INFO),content,getClassName());
        }
    }

    @Override
    public void error(String content) {
        if(LoggerFactory.getLevel() <= LoggerFactory.LEVEL_ERROR){
            writeLogger(getLevelString(LoggerFactory.LEVEL_ERROR),content,getClassName());
        }
    }
    public void writeLogger(String level,String content,String className){
        try {
            Date date = new Date();
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            String time = timeFormat.format(date);
            StringBuffer buffer = new StringBuffer();
            buffer.append(level);
            buffer.append(" ");
            buffer.append(time);
            buffer.append(" ");
            buffer.append(className);
            buffer.append(" - ");
            buffer.append(content);
            buffer.append("\n");
            System.out.println(buffer.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
