package cc.wulian.smarthomev5.eyecat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eques.icvss.core.module.user.BuddyType;
import com.eques.icvss.utils.Method;
import com.yuantuo.customview.ui.WLDialog;

import org.json.JSONObject;

import cc.wulian.h5plus.common.JsUtil;
import cc.wulian.ihome.wan.util.StringUtil;
import cc.wulian.smarthomev5.R;
import cc.wulian.smarthomev5.service.html5plus.plugins.SmarthomeFeatureImpl;


/**
 * Created by Administrator on 2017/3/9.
 */

public class EyecatQRcodeActivity extends Activity implements View.OnClickListener
{
    private String password,wifiSsid;
    private Button eyecat_next;
    private ImageView eyecat_qrcode;
    private LinearLayout eyecat_return;
    public static final String TAG = "EyecatQRcodeActivity";
    private String reqId;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable errorRunnable = new Runnable() {
        @Override
        public void run() {
            Intent i = new Intent(EyecatQRcodeActivity.this, EyecatBindActivity.class);
            i.putExtra("flag", false);
            startActivity(i);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eyecat_activity_qrcode);
        initView();
        initData();
    }
    private void initView(){
        eyecat_return = (LinearLayout) findViewById(R.id.eyecat_return);
        eyecat_return.setOnClickListener(this);
        eyecat_qrcode = (ImageView) findViewById(R.id.eyecat_qrcode);
        eyecat_next = (Button) findViewById(R.id.eyecat_next);
        eyecat_next.setOnClickListener(this);
        eyecat_next.setVisibility(View.GONE);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        Window window=alertDialog.getWindow();
        window.setContentView(R.layout.eyecat_my_dialog);
        Button iknow = (Button) window.findViewById(R.id.eyecat_iknow);
        iknow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertDialog.dismiss();
                handler.postDelayed(errorRunnable,3*60*1000);
            }
        });

    }
    private void initData(){
        EyecatManager.getInstance().login();
        Intent intent = getIntent();
        password = intent.getStringExtra("pwd");
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        wifiSsid = getWifiInfoSSid(wifiManager);
        Bitmap bitmap = EyecatManager.getInstance().getICVSSUserInstance().equesCreateQrcode(wifiSsid, password, EyecatManager.KEYID, EyecatManager.getUserName(),
                BuddyType.TYPE_WIFI_DOOR_R22, 230);

        eyecat_qrcode.setImageBitmap(bitmap);


    }

    @Override
    protected void onResume() {
        super.onResume();
        EyecatManager.getInstance().addPacketListener(scanReqListener);
        EyecatManager.getInstance().addPacketListener(scanResultListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EyecatManager.getInstance().addPacketListener(scanReqListener);
        EyecatManager.getInstance().removePacketListener(scanResultListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(errorRunnable);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.eyecat_return:
                finish();
                break;
            case R.id.eyecat_next:
                Intent i = new Intent(EyecatQRcodeActivity.this, EyecatWaitingActivity.class);
                i.putExtra("reqId", reqId);
                startActivity(i);
                finish();
        }
    }

    public String getWifiInfoSSid(WifiManager wifiManager) {
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null) {
            String ssid = wifiInfo.getSSID();
            if (ssid != null) {
                int i = ssid.indexOf("\"");
                if (i != -1) {
                    ssid = ssid.replaceAll("\"", "");
                }
            }
            return ssid;
        }
        return null;
    }
    private EyecatManager.PacketListener scanReqListener = new EyecatManager.PacketListener() {
        @Override
        public String getMenthod() {
            return Method.METHOD_ONADDBDY_REQ;
        }

        @Override
        public void processPacket(JSONObject object) {
            reqId = object.optString(Method.ATTR_REQID);
            handler.removeCallbacks(errorRunnable);
            JSONObject extra = object.optJSONObject(Method.ATTR_EXTRA);
            if(extra != null){
                final String oldbdy = extra.optString(Method.ATTR_OLDBDY);
                if(!StringUtil.isNullOrEmpty(oldbdy)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            WLDialog.Builder builder = new WLDialog.Builder(EyecatQRcodeActivity.this);
                            builder.setTitle("提示");
                            String str = "已绑定其他账号，请先解绑后再绑定。";
//                            str = String.format(str,oldbdy);
                            builder.setMessage(str);
                            builder.setPositiveButton("取消操作");
                            builder.setListener(new WLDialog.MessageListener() {
                                @Override
                                public void onClickPositive(View contentViewLayout) {
                                    JsUtil.getInstance().execCallback(SmarthomeFeatureImpl.pWebview, SmarthomeFeatureImpl.callbackid,"1", JsUtil.OK, false);
                                    finish();
                                }

                                @Override
                                public void onClickNegative(View contentViewLayout) {

                                }
                            });
                            builder.create().show();
                        }
                    });
                    return ;
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    eyecat_next.setVisibility(View.VISIBLE);
                }
            });
        }
    };
    private EyecatManager.PacketListener scanResultListener = new EyecatManager.PacketListener() {
        @Override
        public String getMenthod() {
            return Method.METHOD_ONADDBDY_RESULT;
        }

        @Override
        public void processPacket(JSONObject object) {
            handler.removeCallbacks(errorRunnable);

            String code = object.optString(Method.ATTR_EQUES_SDK_CODE);
            if("4407".equals(code)){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(EyecatQRcodeActivity.this,"设备已经绑定，无需再绑定",Toast.LENGTH_LONG).show();
                        JsUtil.getInstance().execCallback(SmarthomeFeatureImpl.pWebview, SmarthomeFeatureImpl.callbackid,"0", JsUtil.OK, false);
                        finish();
                    }
                });
            }else if("4402".equals(code)){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(EyecatQRcodeActivity.this,"请求超时，请重新扫描",Toast.LENGTH_LONG).show();
                    }
                });
            }

        }
    };
}
