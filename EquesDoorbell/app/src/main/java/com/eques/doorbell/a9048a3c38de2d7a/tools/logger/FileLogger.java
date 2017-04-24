package com.eques.doorbell.a9048a3c38de2d7a.tools.logger;

import android.os.Environment;

import com.eques.doorbell.a9048a3c38de2d7a.util.FileUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/18.
 */
public class FileLogger extends AbstractLogger{
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
            SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyy-MM-dd");
            String logName = fileNameFormat.format(date);
            String fileName = FileUtil.getDir(Environment.getExternalStorageDirectory().getAbsolutePath()+"/wulian/log/").getAbsolutePath()+"/"+logName+".log";
            if(!FileUtil.isFileExists(fileName)){
                FileUtil.createNewFile(fileName);
            }
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
            FileUtil.writeFile(buffer.toString(), fileName, true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
