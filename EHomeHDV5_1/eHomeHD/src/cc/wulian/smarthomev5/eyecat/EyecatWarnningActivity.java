package cc.wulian.smarthomev5.eyecat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eques.icvss.utils.Method;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cc.wulian.app.model.device.category.Category;
import cc.wulian.smarthomev5.R;
import cc.wulian.smarthomev5.eyecat.adapter.WarnAdapter;
import cc.wulian.smarthomev5.eyecat.bean.Warninfo;
import cc.wulian.smarthomev5.view.XListView.XListView;

/**
 * Created by Administrator on 2017/4/28.
 */

public class EyecatWarnningActivity extends Activity {
    private String bid;
    private List<Warninfo> list;
    private WarnAdapter adapter;
    private XListView lv_warning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eyecat_activity_warnning);
        bid = getIntent().getStringExtra("bid");

        list = new ArrayList<>();
        adapter = new WarnAdapter(this,list);
        lv_warning = (XListView) findViewById(R.id.lv_warnning);
        lv_warning.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadWarn();
        EyecatManager.getInstance().addPacketListener(alarmListListener);

    }

    @Override
    protected void onPause() {
        super.onPause();
        EyecatManager.getInstance().removePacketListener(alarmListListener);
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
                   JSONArray alarmlist = object.optJSONArray("alarms");
                    for(int i=0;i<alarmlist.length();i++){
                        JSONObject alarm = alarmlist.optJSONObject(i);
                        Log.d("zcz",alarm.toString());
                        String aid = alarm.optString("aid");
                        Long time = alarm.optLong("time");
                        String bid = alarm.optString("bid");
                        int type = alarm.optInt("type");
                        JSONArray pvid = alarm.optJSONArray("pvid");
                        JSONArray fid = alarm.optJSONArray("fid");
                        Warninfo warninfo = new Warninfo();
                        warninfo.setAid(aid);
                        warninfo.setTime(time);
                        warninfo.setBid(bid);
                        warninfo.setType(type);
                        warninfo.setPvid(pvid);
                        warninfo.setFid(fid);
                        list.add(i,warninfo);

                    }
                    adapter.notifyDataSetChanged();
                 }
            });

        }
    };
}
