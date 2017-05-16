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

public class EyecatWarnningActivity extends Activity {
    private String bid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eyecat_activity_warnning);
        bid = getIntent().getStringExtra("bid");
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
                    Toast.makeText(EyecatWarnningActivity.this, ""+object.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    };
}
