package cc.wulian.smarthomev5.eyecat.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/11.
 */

public class RingRecondinfo implements Serializable {
    public long getRingtime() {
        return ringtime;
    }

    public void setRingtime(long ringtime) {
        this.ringtime = ringtime;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public RingRecondinfo(long ringtime, String bid, String fid) {
        this.ringtime = ringtime;
        this.bid = bid;
        this.fid = fid;
    }

    private long ringtime;
    private String bid;
    private  String fid;

}
