package cc.wulian.smarthomev5.eyecat.bean;

import org.json.JSONArray;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/28.
 */

public class Warninfo implements Serializable {

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAlarmDevSn() {
        return alarmDevSn;
    }

    public void setAlarmDevSn(String alarmDevSn) {
        this.alarmDevSn = alarmDevSn;
    }

    public String[] getFid() {
        return fid;
    }

    public void setFid(String[] fid) {
        this.fid = fid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String[] getPvid() {
        return pvid;
    }

    public void setPvid(String[] pvid) {
        this.pvid = pvid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private String aid;
    private String time;
    private String alarmDevSn;
    private String[] fid;
    private String bid;
    private int type;
    private String[]  pvid;




}
