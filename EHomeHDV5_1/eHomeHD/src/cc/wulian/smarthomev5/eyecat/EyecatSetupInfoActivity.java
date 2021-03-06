package cc.wulian.smarthomev5.eyecat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eques.icvss.utils.Method;

import org.json.JSONObject;

import cc.wulian.smarthomev5.R;

/**
 * Created by Administrator on 2017/5/3.
 */

public class EyecatSetupInfoActivity extends Activity {
    private TextView eyecat_return,setup_id,wifiname,version;
    private String bid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eyecat_activity_setup_info);
        bid = getIntent().getStringExtra("bid");
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EyecatManager.getInstance().addPacketListener(deviceInfoListern);
        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EyecatManager.getInstance().removePacketListener(deviceInfoListern);
    }

    private void initView(){
        eyecat_return = (TextView) findViewById(R.id.eyecat_return);
        eyecat_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setup_id = (TextView) findViewById(R.id.setup_id);
        wifiname = (TextView) findViewById(R.id.wifiname);
        version = (TextView) findViewById(R.id.version);
    }
    private void initData(){
        EyecatManager.getInstance().getICVSSUserInstance().equesGetDeviceInfo(bid);
    }
    private EyecatManager.PacketListener deviceInfoListern = new EyecatManager.PacketListener() {

        @Override
        public String getMenthod() {
            return Method.METHOD_DEVICEINFO_RESULT;
        }

        @Override
        public void processPacket(final JSONObject object) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setup_id.setText(object.optString("from"));
                    wifiname.setText(object.optString("wifi_config"));
                    version.setText(object.optString("sw_version"));
                }
            });
        }
    };
}
