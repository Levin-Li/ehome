package cc.wulian.smarthomev5.eyecat.bean;

import org.json.JSONArray;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/28.
 */

public class Warninfo implements Serializable {


    public Warninfo(String aid, Long time, String alarmDevSn, JSONArray fid, String bid, int type, JSONArray pvid) {
        this.aid = aid;
        this.time = time;
        this.alarmDevSn = alarmDevSn;
        this.fid = fid;
        this.bid = bid;
        this.type = type;
        this.pvid = pvid;
    }

    private String aid;
    private Long time;
    private String alarmDevSn;
    private JSONArray fid;
    private String bid;
    private int type;
    private JSONArray  pvid;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }



    public String getAlarmDevSn() {
        return alarmDevSn;
    }

    public void setAlarmDevSn(String alarmDevSn) {
        this.alarmDevSn = alarmDevSn;
    }

    public JSONArray getFid() {
        return fid;
    }

    public void setFid(JSONArray fid) {
        this.fid = fid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public JSONArray getPvid() {
        return pvid;
    }

    public void setPvid(JSONArray pvid) {
        this.pvid = pvid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }






}
