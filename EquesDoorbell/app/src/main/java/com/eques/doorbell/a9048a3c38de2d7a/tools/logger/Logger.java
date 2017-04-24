package com.eques.doorbell.a9048a3c38de2d7a.tools.logger;

/**
 * Created by Administrator on 2016/10/18.
 */
public interface Logger {
    public void debug(String content);
    public void info(String content);
    public void error(String content);
    public void setClassName(String className);
    public String getClassName();
}
