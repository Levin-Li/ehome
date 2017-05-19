package cc.wulian.smarthomev5.eyecat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eques.icvss.utils.ELog;
import com.eques.icvss.utils.Method;
import com.yuantuo.customview.ui.WLDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cc.wulian.smarthomev5.R;
import cc.wulian.smarthomev5.eyecat.adapter.RingRecondAdapter;
import cc.wulian.smarthomev5.eyecat.bean.RingRecondinfo;
import cc.wulian.smarthomev5.view.XListView.XListView;

/**
 * Created by Administrator on 2017/4/28.
 */

public class EyecatRingRecondActivity extends Activity {
    private String bid;
    private XListView listView;
    private RingRecondAdapter adapter;
    private List<RingRecondinfo> ringInfos = new ArrayList<RingRecondinfo>();
    public static final int TIME_SIZE =24*60*60*1000;
    public static final int MAX_SIZE =1000;
    private long startTime;
    private long endTime;
    private TextView editTextView;
    private TextView returnTextView;
    private RelativeLayout editPannel;
    private ImageView  checkAllImageView;
    private ImageView deleteImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eyecat_activity_ringrecond);
        bid = getIntent().getStringExtra("bid");
        listView = (XListView)findViewById(R.id.lv_warnning);
        adapter = new RingRecondAdapter(this,new ArrayList<RingRecondinfo>());
        listView.setAdapter(adapter);
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
                WLDialog.Builder builder = new WLDialog.Builder(EyecatRingRecondActivity.this);
                builder.setTitle("修改设备名");
                builder.setMessage("确定删除选中记录？");
                builder.setPositiveButton(android.R.string.ok);
                builder.setNegativeButton(android.R.string.cancel);
                builder.setListener(new WLDialog.MessageListener() {
                    @Override
                    public void onClickPositive(View contentViewLayout) {
                        List<RingRecondinfo> data = adapter.getCheckedData();
                        if(data != null && data.size()>0){
                            String fids[] = new String[data.size()];
                            for(int i=0;i<data.size();i++){
                                fids[i] = data.get(0).getFid();
                            }
                            EyecatManager.getInstance().getICVSSUserInstance().equesDelRingRecord(bid,fids,0);
                        }
                    }

                    @Override
                    public void onClickNegative(View contentViewLayout) {
                    }
                });

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
                    String bid = adapter.getItem(position-1).getBid();
                    String fid = adapter.getItem(position-1).getFid();
                    URL url = EyecatManager.getInstance().getICVSSUserInstance().equesGetRingPicture(fid,bid);
                    Intent intent = new Intent();
                    intent.setClass(EyecatRingRecondActivity.this,EyecatImageDetailActivity.class);
                    intent.putExtra("url",url.toString());
                    startActivity(intent);
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
                loadRings(startTime,endTime);
            }
        });
        returnTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initData();
    }

    private void initData() {
        EyecatManager.getInstance().addPacketListener(ringListListener);
        EyecatManager.getInstance().addPacketListener(deleteRingListener);
        firstLoad();
    }

    private void firstLoad(){
        ringInfos.clear();
        Date now = new Date();
        now.setHours(0);
        now.setMinutes(0);
        now.setSeconds(0);
        startTime = now.getTime();
        endTime = new Date().getTime();
        loadRings(startTime,endTime);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EyecatManager.getInstance().removePacketListener(ringListListener);
        EyecatManager.getInstance().removePacketListener(deleteRingListener);
    }
    private void loadRings(long start,long entTime){
        EyecatManager.getInstance().getICVSSUserInstance().equesGetRingRecordList(bid,start,entTime,MAX_SIZE);
    }
    private EyecatManager.PacketListener deleteRingListener = new EyecatManager.PacketListener(){

        @Override
        public String getMenthod() {
            return Method.METHOD_DELETE_RING;
        }

        @Override
        public void processPacket(JSONObject object) {
            String code = object.optString("code");
            if("4000".equals(code)){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ELog.showToastShort(EyecatRingRecondActivity.this, "删除成功");
                        adapter.removeCheckedData();
                    }
                });
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ELog.showToastShort(EyecatRingRecondActivity.this,"删除失败");
                    }
                });
            }
        }
    };

    private EyecatManager.PacketListener ringListListener = new EyecatManager.PacketListener() {

        @Override
        public String getMenthod() {
            return Method.METHOD_ALARM_RINGLIST;
        }

        @Override
        public void processPacket(final JSONObject object) {
            JSONArray rings = object.optJSONArray("rings");
            final List<RingRecondinfo> ringList = new ArrayList<RingRecondinfo>();
            for(int i=0;i<rings.length();i++){
                try {
                    JSONObject ringObj = rings.getJSONObject(i);
                    RingRecondinfo info = new RingRecondinfo(ringObj.optLong("ringtime"),ringObj.optString("bid"),ringObj.optString("fid"));
                    ringList.add(info);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ringInfos.addAll(ringList);
            if(ringList.size() >=MAX_SIZE ){
                endTime = ringList.get(ringList.size() -1).getRingtime();
                loadRings(startTime,endTime);
            }else{
                endTime = startTime;
                startTime = startTime- TIME_SIZE;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listView.stopLoadMore();
                        listView.stopRefresh();
                        adapter.swapData(ringInfos);
                }
                });
            }

        }
    };
}
