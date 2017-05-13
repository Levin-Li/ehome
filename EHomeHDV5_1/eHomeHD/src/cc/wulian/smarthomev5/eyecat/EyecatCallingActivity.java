package cc.wulian.smarthomev5.eyecat;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eques.icvss.utils.Method;

import org.json.JSONObject;

import java.io.IOException;
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
    private MediaPlayer mMediaPlayer;
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN: {
                slient();
                return true;
            }
            case KeyEvent.KEYCODE_VOLUME_UP: {
                slient();
                return true;
            }
            case KeyEvent.KEYCODE_VOLUME_MUTE: {
                slient();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    private void intiData() {
        startAlarm();
    }
    private void stopAlarm(){
        if(mMediaPlayer != null){
            try {
                mMediaPlayer.stop();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private void slient(){
        if(mMediaPlayer != null){
            try {
                mMediaPlayer.setVolume(0.0f, 0.0f);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private void startAlarm() {
        mMediaPlayer = MediaPlayer.create(this, getSystemDefultRingtoneUri());
        mMediaPlayer.setLooping(true);
        try {
            mMediaPlayer.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }
    private Uri getSystemDefultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_RINGTONE);
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
                stopAlarm();
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
                stopAlarm();
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
                stopAlarm();
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
                        stopAlarm();
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
