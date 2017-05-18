package cc.wulian.smarthomev5.eyecat;

import android.app.Activity;
import android.os.Bundle;

import com.eques.icvss.utils.Method;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cc.wulian.smarthomev5.R;
import cc.wulian.smarthomev5.eyecat.bean.Warninfo;

/**
 * Created by Administrator on 2017/4/28.
 */

public class EyecatWarnningActivity extends Activity {
    private String bid;
    private List<Warninfo> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eyecat_activity_warnning);
        list = new ArrayList<>();
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadWarn();
        EyecatManager.getInstance().addPacketListener(alarmListListener);

    }
    private void loadWarn(){
        EyecatManager.getInstance().getICVSSUserInstance().equesGetAlarmMessageList(bid,0,0,100);
    }
    private EyecatManager.PacketListener alarmListListener = new EyecatManager.PacketListener() {

        @Override
        public String getMenthod() {
            return Method.METHOD_ALARM_ALMLIST;
        }

        @Override
        public void processPacket(final JSONObject object) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray alarms = object.optJSONArray("alarms");
                    Warninfo warninfo =null;
                    for(int i=0;i<alarms.length();i++){
                        warninfo = list.get(i);
                        JSONObject warn = null;
                        try {
                            warn = alarms.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        warninfo.setBid(warn.optString("bid"));
                        warninfo.setAid(warn.optString("aid"));
                        warninfo.setTime(warn.optString("time"));
                        warninfo.setAlarmDevSn(warn.optString("alarmDevSn"));
                        warninfo.setType(warn.optInt("type"));
                        JSONArray fids = warn.optJSONArray("fids");
                        String[] fid = new String[fids.length()];
                        for(int j=0;j<fids.length();j++){
                            try {
                                fid[j] = fids.getJSONObject(j).optString("fid");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        warninfo.setFid(fid);
                        JSONArray pvids = warn.optJSONArray("pvid");
                        String[] pvid = new String[fids.length()];
                        for(int j=0;j<fids.length();j++){
                            try {
                                pvid[j] = pvids.getJSONObject(j).optString("pvid");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        warninfo.setPvid(pvid);
                        list.add(warninfo);
                    }


                }
            });

        }
    };
}
