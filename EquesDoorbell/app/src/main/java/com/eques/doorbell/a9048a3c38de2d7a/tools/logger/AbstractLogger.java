package com.eques.doorbell.a9048a3c38de2d7a.tools.logger;

/**
 * Created by Administrator on 2016/10/18.
 */
public abstract class AbstractLogger implements Logger {
    private String className;
    @Override
    public void error(String content) {

    }

    @Override
    public void info(String content) {

    }

    @Override
    public void debug(String content) {

    }

    @Override
    public String getClassName() {
        return this.className;
    }

    @Override
    public void setClassName(String className) {
        this.className =className;
    }
    protected String getLevelString(int level){
        if(level == LoggerFactory.LEVEL_DEBUG){
            return "debug";
        }else if(level == LoggerFactory.LEVEL_INFO){
            return "info";
        }else if(level == LoggerFactory.LEVEL_ERROR){
            return "error";
        }
        return "";
    }
}
