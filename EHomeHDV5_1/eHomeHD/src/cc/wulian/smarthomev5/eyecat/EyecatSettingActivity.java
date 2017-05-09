package cc.wulian.smarthomev5.eyecat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.alibaba.fastjson.JSON;
import com.eques.icvss.utils.Method;
import com.yuantuo.customview.ui.WLDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.wulian.h5plus.common.JsUtil;
import cc.wulian.ihome.wan.core.http.Result;
import cc.wulian.ihome.wan.entity.GatewayInfo;
import cc.wulian.ihome.wan.util.StringUtil;
import cc.wulian.ihome.wan.util.TaskExecutor;
import cc.wulian.smarthomev5.R;
import cc.wulian.smarthomev5.account.WLUserManager;
import cc.wulian.smarthomev5.service.html5plus.plugins.SmarthomeFeatureImpl;
import cc.wulian.smarthomev5.tools.DevicesUserManage;


/**
 * Created by Administrator on 2017/3/30.
 */

public class EyecatSettingActivity extends Activity {
    private RelativeLayout eyecat_setup_name,eyecat_wifi_setting,eyecatCallHistory,eyecatWarnHistory,eyecatPicture,eyecat_setup_info;
    private ToggleButton doorbelllight,human_detection_onoff;
    private TextView setup_name;
    private boolean humanChecked = false;
    private boolean doorLightChecked = false;
    private TextView eyecatBack,restartDeviceTextView,deleteEyeDeviceTextView;
    private String bid;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eyecat_activity_setting);
        bid = getIntent().getStringExtra("bid");
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EyecatManager.getInstance().addPacketListener(deviceNickListener);
        EyecatManager.getInstance().addPacketListener(deviceDetailListener);
        EyecatManager.getInstance().addPacketListener(hummanListener);
        EyecatManager.getInstance().addPacketListener(doorbellLightListener);
        EyecatManager.getInstance().addPacketListener(deviceRemoveListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EyecatManager.getInstance().removePacketListener(deviceDetailListener);
        EyecatManager.getInstance().removePacketListener(hummanListener);
        EyecatManager.getInstance().removePacketListener(doorbellLightListener);
        EyecatManager.getInstance().removePacketListener(deviceRemoveListener);
        EyecatManager.getInstance().removePacketListener(deviceNickListener);
    }

    private void initView(){
        eyecatBack = (TextView) findViewById(R.id.eyecat_return);
        eyecatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsUtil.getInstance().execCallback(SmarthomeFeatureImpl.pWebview, SmarthomeFeatureImpl.callbackid,"0", JsUtil.OK, false);
                finish();
            }
        });
        eyecat_setup_info = (RelativeLayout) findViewById(R.id.eyecat_setup_info);
        eyecat_setup_info.setOnClickListener(myOnClickListener);
        setup_name = (TextView) findViewById(R.id.setup_name);
        eyecat_setup_name = (RelativeLayout) findViewById(R.id.eyecat_setup_name);
        eyecat_setup_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText= new EditText(EyecatSettingActivity.this);
                editText.setHint("设备名称");
                editText.setText(setup_name.getText());
                WLDialog.Builder builder = new WLDialog.Builder(EyecatSettingActivity.this);
                builder.setTitle("修改设备名");
                builder.setContentView(editText);
                builder.setPositiveButton(android.R.string.ok);
                builder.setNegativeButton(android.R.string.cancel);
                builder.setListener(new WLDialog.MessageListener() {
                    @Override
                    public void onClickPositive(View contentViewLayout) {
                        EyecatManager.getInstance().getICVSSUserInstance().equesSetDeviceNick(uid,editText.getText().toString());
                        TaskExecutor.getInstance().execute(new Runnable() {
                            @Override
                            public void run() {
                                Result opResult = WLUserManager.getInstance().getStub().deviceUpdate(bid,null,editText.getText().toString());
                                if(opResult != null && opResult.status == 0){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            setup_name.setText(editText.getText().toString());
                                        }
                                    });
                                }

                            }
                        });
                    }

                    @Override
                    public void onClickNegative(View contentViewLayout) {
                    }
                });
                builder.create().show();
            }
        });
        doorbelllight = (ToggleButton) findViewById(R.id.doorbelllight_onoff);
        doorbelllight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = doorbelllight.isChecked();
                doorLightChecked = isChecked;
                if(isChecked){
                    EyecatManager.getInstance().getICVSSUserInstance().equesSetDoorbellLight(uid,1);
                }else {
                    EyecatManager.getInstance().getICVSSUserInstance().equesSetDoorbellLight(uid,0);
                }
            }
        });
        human_detection_onoff = (ToggleButton) findViewById(R.id.human_detection_onoff);
        human_detection_onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = human_detection_onoff.isChecked();
                humanChecked = isChecked;
                if(isChecked){
                    EyecatManager.getInstance().getICVSSUserInstance().equesSetPirEnable(uid,1);
                }else {
                    EyecatManager.getInstance().getICVSSUserInstance().equesSetPirEnable(uid,0);
                }
            }
        });
        eyecat_wifi_setting = (RelativeLayout) findViewById(R.id.eyecat_wifi_setting);
        eyecat_wifi_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EyecatSettingActivity.this,EyecatWIFISettingOneActivity.class);
                startActivity(intent);
            }
        });
        eyecatCallHistory = (RelativeLayout) findViewById(R.id.history_call_relatvie);
        eyecatCallHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                long start = System.currentTimeMillis() - 1000 * 60 * 60 * 24;
//                long end = System.currentTimeMillis();
//                EyecatManager.getInstance().getICVSSUserInstance().equesGetAlarmMessageList(bid,start,end,100);
//                EyecatManager.getInstance().addPacketListener(alarmListListener);
                Intent intent = new Intent(EyecatSettingActivity.this,EyecatWarnningActivity.class);
                intent.putExtra("bid",bid);
                startActivity(intent);
            }
        });
        eyecatWarnHistory = (RelativeLayout) findViewById(R.id.history_warn_relative);
        eyecatWarnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                long startTime = System.currentTimeMillis() - 1000 * 60 * 60
//                        * 24;
//                long endTime = System.currentTimeMillis();
//                EyecatManager.getInstance().getICVSSUserInstance().equesGetRingRecordList(bid,startTime,endTime,100);
//                EyecatManager.getInstance().addPacketListener(ringListListener);
                Intent intent = new Intent(EyecatSettingActivity.this,EyecatRingRecondActivity.class);
                intent.putExtra("bid",bid);
                startActivity(intent);
            }
        });
        eyecatPicture = (RelativeLayout) findViewById(R.id.eyecat_image_info);
        eyecatPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        restartDeviceTextView = (TextView) findViewById(R.id.restart_tv);
        restartDeviceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WLDialog.Builder builder = new WLDialog.Builder(EyecatSettingActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确定要重启设备?");
                builder.setPositiveButton(android.R.string.ok);
                builder.setNegativeButton(android.R.string.cancel);
                builder.setListener(new WLDialog.MessageListener() {
                    @Override
                    public void onClickPositive(View contentViewLayout) {
                        EyecatManager.getInstance().getICVSSUserInstance().equesRestartDevice(uid);
                    }

                    @Override
                    public void onClickNegative(View contentViewLayout) {
                    }
                });
                builder.create().show();

            }
        });
        deleteEyeDeviceTextView = (TextView) findViewById(R.id.eyecat_delete_device);
        deleteEyeDeviceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WLDialog.Builder builder = new WLDialog.Builder(EyecatSettingActivity.this);
                builder.setTitle("提示");
                builder.setMessage("删除设备会把设备相关的信息都删除!");
                builder.setPositiveButton(android.R.string.ok);
                builder.setNegativeButton(android.R.string.cancel);
                builder.setListener(new WLDialog.MessageListener() {
                    @Override
                    public void onClickPositive(View contentViewLayout) {
                        EyecatManager.getInstance().getICVSSUserInstance().equesDelDevice(bid);
                    }

                    @Override
                    public void onClickNegative(View contentViewLayout) {
                    }
                });
                builder.create().show();

            }
        });

    }
    private void initData(){
        EyecatManager.getInstance().login();
        TaskExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                syncUid();
            }
        });
    }
    void syncUid() {
        int count = 0;
        EyecatManager.EyecatDevice device = null;
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(EyecatSettingActivity.this, "正在尝试连接。。", Toast.LENGTH_SHORT).show();
//            }
//        });
        while (count < 10) {
            device = EyecatManager.getInstance().getDevice(bid);
            if (device == null) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                count++;
            } else {
                break;
            }
        }
        if (device == null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(EyecatSettingActivity.this, "登入失败,请重新尝试", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        } else if (StringUtil.isNullOrEmpty(device.getUid())) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(EyecatSettingActivity.this, "设备不在线，请检查设备", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        } else {
            uid = device.getUid();
            final GatewayInfo deviceInfo = WLUserManager.getInstance().getStub().getSimpleDeviceByUser(bid);
            Log.i("eyecat:", JSON.toJSONString(deviceInfo));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(deviceInfo != null){
                        String deviceName = "";
                        if(StringUtil.isNullOrEmpty(deviceInfo.getDeviceAlias())){
                            deviceName = bid;
                        }else{
                            deviceName = deviceInfo.getDeviceAlias();
                        }
                        setup_name.setText(deviceName);
                    }
                    EyecatManager.getInstance().getICVSSUserInstance().equesGetDeviceInfo(uid);
                }
            });

        }
    }
    private View.OnClickListener myOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.eyecat_setup_info:
                    Intent intent = new Intent(EyecatSettingActivity.this,EyecatSetupInfoActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
    private EyecatManager.PacketListener deviceDetailListener = new EyecatManager.PacketListener() {
        @Override
        public String getMenthod() {
            return Method.METHOD_DEVICEINFO_RESULT;
        }

        @Override
        public void processPacket(final JSONObject object) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int alarmEnable = object.optInt(Method.METHOD_ALARM_ENABLE);
                    if(alarmEnable==1){
                        human_detection_onoff.setChecked(true);
                    }else{
                        human_detection_onoff.setChecked(false);
                    }
                    int doorbellLightEnable = object.optInt(Method.METHOD_DB_LIGHT_ENABLE);
                    if(doorbellLightEnable==1){
                        doorbelllight.setChecked(true);
                    }else{
                        doorbelllight.setChecked(false);
                    }
                }
            });

        }
    };
    private EyecatManager.PacketListener hummanListener = new EyecatManager.PacketListener() {
        @Override
        public String getMenthod() {
            return Method.METHOD_ALARM_ENABLE_RESULT;
        }

        @Override
        public void processPacket(final JSONObject object) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int result = object.optInt("result");
                    if(result==1){
                        if(humanChecked){
                            human_detection_onoff.setChecked(true);
                        }else{
                            human_detection_onoff.setChecked(false);
                        }
                    }
                }
            });

        }
    };
    private EyecatManager.PacketListener doorbellLightListener = new EyecatManager.PacketListener() {
        @Override
        public String getMenthod() {
            return Method.METHOD_DB_LIGHT_ENABLE_RESULT;
        }

        @Override
        public void processPacket(final JSONObject object) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int result = object.optInt("result");
                    if(result==1){
                        if(doorLightChecked){
                            doorbelllight.setChecked(true);
                        }else{
                            doorbelllight.setChecked(false);
                        }
                    }
                }
            });
        }
    };
    private EyecatManager.PacketListener deviceRemoveListener = new EyecatManager.PacketListener() {
        @Override
        public String getMenthod() {
            return Method.METHOD_RMBDY_RESULT;
        }

        @Override
        public void processPacket(final JSONObject object) {
            final String bid = object.optString(Method.ATTR_BUDDY_BID);
            String code = object.optString(Method.ATTR_ERROR_CODE);
            if("4000".equals(code)){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DevicesUserManage.unBindDevice(bid);
                        JsUtil.getInstance().execCallback(SmarthomeFeatureImpl.pWebview, SmarthomeFeatureImpl.callbackid,"0", JsUtil.OK, false);
                        finish();
                    }
                });
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                      Toast.makeText(EyecatSettingActivity.this,"解绑设备失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };
    private EyecatManager.PacketListener deviceNickListener = new EyecatManager.PacketListener() {

        @Override
        public String getMenthod() {
            return Method.METHOD_BDYLIST;
        }

        @Override
        public void processPacket(final JSONObject object) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray bdylist = object.optJSONArray("bdylist");
                    JSONObject bdy = bdylist.optJSONObject(0);
                    String nickname = bdy.optString("nick");
                    setup_name.setText(nickname);
                }
            });

        }
    };


}
