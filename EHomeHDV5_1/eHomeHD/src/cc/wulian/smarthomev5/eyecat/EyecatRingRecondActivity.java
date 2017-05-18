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
        loadRings();
        EyecatManager.getInstance().addPacketListener(ringListListener);

    }
    private void loadRings(){
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
