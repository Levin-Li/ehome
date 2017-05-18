package cc.wulian.smarthomev5.eyecat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eques.icvss.utils.Method;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cc.wulian.smarthomev5.R;
import cc.wulian.smarthomev5.eyecat.adapter.RingRecondAdapter;
import cc.wulian.smarthomev5.eyecat.adapter.WarnAdapter;
import cc.wulian.smarthomev5.eyecat.bean.RingRecondinfo;
import cc.wulian.smarthomev5.eyecat.bean.Warninfo;
import cc.wulian.smarthomev5.view.XListView.XListView;

/**
 * Created by Administrator on 2017/4/28.
 */

public class EyecatWarnningActivity extends Activity {
    private String bid;
    private XListView listView;
    private WarnAdapter adapter;
    private List<Warninfo> warninfos = new ArrayList<Warninfo>();
    public static final int MAX_SIZE =1000;
    public static final int TIME_SIZE =24*60*60*1000;
    private long startTime;
    private long endTime;
    private TextView editTextView;
    private TextView returnTextView;
    private RelativeLayout editPannel;
    private ImageView checkAllImageView;
    private ImageView deleteImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eyecat_activity_warnning);
        bid = getIntent().getStringExtra("bid");
        listView = (XListView)findViewById(R.id.lv_warnning);
        adapter = new WarnAdapter(this,warninfos);
        editTextView = (TextView)findViewById(R.id.eyecat_edit);
        returnTextView = (TextView)findViewById(R.id.eyecat_return);
        editPannel = (RelativeLayout) findViewById(R.id.edit_pannel);
        editTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = editTextView.getTag();
                if(tag == null) {
                    editTextView.setTag(true);
                    editTextView.setText("取消");
                    editPannel.setVisibility(View.VISIBLE);
                    adapter.toggerEdit();
                }else{
                    editTextView.setTag(null);
                    editTextView.setText("编辑");
                    editPannel.setVisibility(View.GONE);
                    adapter.toggerEdit();
                }
            }
        });
        checkAllImageView = (ImageView) findViewById(R.id.edit_check_all);
        checkAllImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.checkedSize() >= adapter.getCount()){
                    adapter.uncheckAll();
                    checkAllImageView.setImageResource(R.drawable.device_led_adjust_normal);
                }else{
                    adapter.checkAll();
                    checkAllImageView.setImageResource(R.drawable.device_led_adjust_select);
                }
            }
        });
        deleteImageView = (ImageView) findViewById(R.id.edit_delete_select);
        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Warninfo> data = adapter.getCheckedData();
                if(data != null && data.size()>0){
                    String aids[] = new String[data.size()];
                    for(int i=0;i<data.size();i++){
                        aids[i] = data.get(0).getAid();
                    }
                    EyecatManager.getInstance().getICVSSUserInstance().equesDelAlarmMessage(bid,aids,0);
                }
            }
        });
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(adapter.isEdit()){
                    adapter.toggleCheck(position-1);
                    if(adapter.checkedSize() >= adapter.getCount()){
                        checkAllImageView.setImageResource(R.drawable.device_led_adjust_select);
                    }else{
                        checkAllImageView.setImageResource(R.drawable.device_led_adjust_normal);
                    }
                }else{

                }
            }
        });
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                firstLoad();
            }

            @Override
            public void onLoadMore() {
                loadAlarms(startTime,endTime);
            }
        });
        returnTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        EyecatManager.getInstance().addPacketListener(alarmListListener);
        EyecatManager.getInstance().addPacketListener(deleteAlarmListener);
        firstLoad();

    }
    private void firstLoad(){
        warninfos.clear();
        Date now = new Date();
        now.setHours(0);
        now.setMinutes(0);
        now.setSeconds(0);
        startTime = now.getTime();
        endTime = new Date().getTime();
        loadAlarms(startTime,endTime);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EyecatManager.getInstance().removePacketListener(alarmListListener);
        EyecatManager.getInstance().removePacketListener(deleteAlarmListener);
    }
    private void loadAlarms(long start,long entTime){
        EyecatManager.getInstance().getICVSSUserInstance().equesGetAlarmMessageList(bid,0,0,MAX_SIZE);
    }
    private EyecatManager.PacketListener deleteAlarmListener = new EyecatManager.PacketListener(){

        @Override
        public String getMenthod() {
            return Method.METHOD_DELETE_ALARM;
        }

        @Override
        public void processPacket(JSONObject object) {
            String code = object.optString("code");
            if("4000".equals(code)){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(EyecatWarnningActivity.this,"删除成功",Toast.LENGTH_SHORT);
                        adapter.removeCheckedData();
                    }
                });
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(EyecatWarnningActivity.this,"删除失败",Toast.LENGTH_SHORT);
                    }
                });
            }
        }
    };
    private EyecatManager.PacketListener alarmListListener = new EyecatManager.PacketListener() {

        @Override
        public String getMenthod() {
            return Method.METHOD_ALARM_ALMLIST;
        }

        @Override
        public void processPacket(final JSONObject object) {
            JSONArray alarms = object.optJSONArray("alarms");
            Log.d("zcz",alarms.toString());
            final List<Warninfo> warnList = new ArrayList<Warninfo>();
            for(int i=0;i<alarms.length();i++){
                try {
                    JSONObject warnObj = alarms.getJSONObject(i);
                    Warninfo info = new Warninfo(warnObj.optString("aid"),warnObj.optLong("time"),warnObj.optString("alarmDevSn"),warnObj.optJSONArray("fid"),warnObj.optString("bid"),warnObj.optInt("type"),warnObj.optJSONArray("pvid"));
                    warnList.add(info);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            warninfos.addAll(warnList);
            Log.d("zcz","aid"+warninfos.get(0).getAid());
            adapter.notifyDataSetChanged();
            if(warnList.size() >=MAX_SIZE ){
                Log.d("zcz","aid1"+warninfos.get(0).getAid());
                endTime = warnList.get(warnList.size() -1).getTime();
                loadAlarms(startTime,endTime);
            }else{
                endTime = startTime;
                startTime = startTime- TIME_SIZE;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listView.stopLoadMore();
                        listView.stopRefresh();
                        adapter.swapData(warninfos);
                    }
                });
            }

        }
    };
}
