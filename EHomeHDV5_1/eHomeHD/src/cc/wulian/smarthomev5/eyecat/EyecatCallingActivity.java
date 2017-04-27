package cc.wulian.smarthomev5.eyecat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eques.icvss.utils.Method;

import org.json.JSONObject;

import java.net.URL;

import cc.wulian.ihome.wan.util.StringUtil;
import cc.wulian.smarthomev5.R;

/**
 * Created by Administrator on 2017/4/26.
 */

public class EyecatCallingActivity extends Activity {
    private String bid;
    private String fid;
    private String sid;
    private TextView deviceNameTextView;
    private ImageView deviceImageView,handupImageView,voiceImageView,videoImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eyecat_ring_calling);
        this.bid = getIntent().getStringExtra("bid");
        this.sid = getIntent().getStringExtra("sid");
        EyecatManager.getInstance().addPacketListener(callListener);
        EyecatManager.getInstance().addPacketListener(callPictureListener);
        initView();
        intiData();
    }
    private void intiData() {

    }

    private void initView(){
        deviceNameTextView = (TextView)findViewById(R.id.eyecat_calling_device_name);
        deviceImageView = (ImageView)findViewById(R.id.eyecat_calling_device_image);
        handupImageView = (ImageView)findViewById(R.id.eyecat_calling_hangup);
        voiceImageView = (ImageView)findViewById(R.id.eyecat_calling_voice);
        videoImageView = (ImageView)findViewById(R.id.eyecat_calling_video);
        deviceNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        handupImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EyecatManager.getInstance().getICVSSUserInstance().equesCloseCall(sid);
                finish();
            }
        });
        voiceImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EyecatCallingActivity.this,EyecatVideoCallActivity.class);
                intent.putExtra("bid", bid);
                intent.putExtra("hasVideo", false);
                EyecatCallingActivity.this.startActivity(intent);
                finish();
            }
        });
        videoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EyecatCallingActivity.this,EyecatVideoCallActivity.class);
                intent.putExtra("bid", bid);
                intent.putExtra("hasVideo", true);
                EyecatCallingActivity.this.startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EyecatManager.getInstance().removePacketListener(callListener);
        EyecatManager.getInstance().removePacketListener(callPictureListener);
    }
    private EyecatManager.PacketListener callListener  = new EyecatManager.PacketListener() {
        @Override
        public String getMenthod() {
            return Method.METHOD_CALL;
        }

        @Override
        public void processPacket(JSONObject object) {
            String callbid =object.optString(Method.ATTR_FROM);
            String state =object.optString(Method.ATTR_ZIGBEE_STATE);
            if(StringUtil.equals(bid,callbid) && "close".equals(state)){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });

            }
        }
    };
    private EyecatManager.PacketListener callPictureListener  = new EyecatManager.PacketListener() {
        @Override
        public String getMenthod() {
            return Method.METHOD_PREVIEW;
        }

        @Override
        public void processPacket(JSONObject object) {
            String callbid =object.optString(Method.ATTR_FROM);
            if(StringUtil.equals(callbid,bid) ){
                fid = object.optString(Method.ATTR_FID);
                URL url = EyecatManager.getInstance().getICVSSUserInstance().equesGetRingPicture(fid,bid);
                System.out.println(url);
            }
        }
    };
}
