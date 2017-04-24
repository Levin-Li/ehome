package com.eques.doorbell.a9048a3c38de2d7a.tools.logger;

/**
 * Created by Administrator on 2016/10/18.
 */
public class EmptyLogger extends AbstractLogger {
    @Override
    public void debug(String content) {
        if(LoggerFactory.getLevel() <= LoggerFactory.LEVEL_DEBUG){
        }
    }

    @Override
    public void info(String content) {
        if(LoggerFactory.getLevel() <= LoggerFactory.LEVEL_INFO){
        }
    }

    @Override
    public void error(String content) {
        if(LoggerFactory.getLevel() <= LoggerFactory.LEVEL_ERROR){
        }
    }
}
