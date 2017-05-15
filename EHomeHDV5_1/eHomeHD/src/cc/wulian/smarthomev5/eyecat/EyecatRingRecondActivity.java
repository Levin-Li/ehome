package cc.wulian.smarthomev5.eyecat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.eques.icvss.utils.Method;

import org.json.JSONObject;

import cc.wulian.smarthomev5.R;

/**
 * Created by Administrator on 2017/4/28.
 */

public class EyecatRingRecondActivity extends Activity {
    private String bid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eyecat_activity_ringrecond);
        bid = getIntent().getStringExtra("bid");

    }

    @Override
    protected void onResume() {
        super.onResume();
        EyecatManager.getInstance().addPacketListener(ringListListener);
        loadRings();

    }
    private void loadRings(){
        long startTime = System.currentTimeMillis() - 1000 * 60 * 60* 24;
        long endTime = System.currentTimeMillis();
        EyecatManager.getInstance().getICVSSUserInstance().equesGetRingRecordList(bid,0,0,100);
    }
    private EyecatManager.PacketListener ringListListener = new EyecatManager.PacketListener() {

        @Override
        public String getMenthod() {
            return Method.METHOD_ALARM_RINGLIST;
        }

        @Override
        public void processPacket(final JSONObject object) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(EyecatRingRecondActivity.this, ""+object.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    };
}
