package cc.wulian.smarthomev5.eyecat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import cc.wulian.h5plus.common.JsUtil;
import cc.wulian.smarthomev5.R;
import cc.wulian.smarthomev5.service.html5plus.plugins.SmarthomeFeatureImpl;
import cc.wulian.smarthomev5.tools.DevicesUserManage;


/**
 * Created by Administrator on 2017/3/30.
 */

public class EyecatSettingActivity extends Activity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {
    private RelativeLayout eyecat_setup_info,eyecat_wifi_setting,eyecat_setup_name,eyecat_bind_lock;
    private Intent intent;
    private Switch doorbelllight,human_detection_onoff,do_not_disturb_onoff;
    private TextView setup_name;
    public static Boolean settingflag = false;
    private String nickname = "移康猫眼";
    private TextView deleteEyeDeviceTextView;
    private String bid;
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
    }

    private void initView(){
        setup_name = (TextView) findViewById(R.id.setup_name);
        eyecat_setup_info = (RelativeLayout) findViewById(R.id.eyecat_setup_info);
        eyecat_setup_info.setOnClickListener(this);
        doorbelllight = (Switch) findViewById(R.id.doorbelllight_onoff);
        doorbelllight.setOnCheckedChangeListener(this);
        human_detection_onoff = (Switch) findViewById(R.id.human_detection_onoff);
        human_detection_onoff.setOnCheckedChangeListener(this);
        do_not_disturb_onoff = (Switch) findViewById(R.id.do_not_disturb_onoff);
        do_not_disturb_onoff.setOnCheckedChangeListener(this);
        eyecat_wifi_setting = (RelativeLayout) findViewById(R.id.eyecat_wifi_setting);
        eyecat_wifi_setting.setOnClickListener(this);
        eyecat_setup_name = (RelativeLayout) findViewById(R.id.eyecat_setup_name);
        eyecat_setup_name.setOnClickListener(this);
        deleteEyeDeviceTextView = (TextView) findViewById(R.id.eyecat_delete_device);
        deleteEyeDeviceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DevicesUserManage.unBindDevice(bid);
                EyecatManager.getInstance().getICVSSUserInstance().equesDelDevice(bid);
                JsUtil.getInstance().execCallback(SmarthomeFeatureImpl.pWebview, SmarthomeFeatureImpl.callbackid,"0", JsUtil.OK, false);
                finish();
            }
        });
    }
    private void initData(){
//        EyecatManager.getInstance().getICVSSUserInstance().equesGetDeviceList();
        EyecatManager.getInstance().login();
        setup_name.setText(nickname);
    }
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.eyecat_setup_name:
                final EditText editText= new EditText(EyecatSettingActivity.this);
                editText.setHint("设备名称");
                AlertDialog.Builder builder = new AlertDialog.Builder(EyecatSettingActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                builder.setView(editText);
                builder.setTitle("修改设备名称");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        EyecatManager.getInstance().getICVSSUserInstance().equesSetDeviceNick(Cookies.session.getBid(),editText.getText().toString());
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                builder.create().show();
                break;
            case R.id.eyecat_setup_info:
//                intent = new Intent(EyecatSettingActivity.this,EyecatSetupInfoActivity.class);
//                startActivity(intent);
                break;
            case R.id.eyecat_wifi_setting:
                intent = new Intent(EyecatSettingActivity.this,EyecatWIFISettingOneActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.doorbelllight_onoff:
                settingflag = isChecked;
                if(isChecked){
                    EyecatManager.getInstance().getICVSSUserInstance().equesSetDoorbellLight(Cookies.session.getUid(),1);
                }else {
                    EyecatManager.getInstance().getICVSSUserInstance().equesSetDoorbellLight(Cookies.session.getUid(),0);
                }
                break;
            case R.id.human_detection_onoff:
                settingflag = isChecked;
                if(isChecked){
                    EyecatManager.getInstance().getICVSSUserInstance().equesSetPirEnable(Cookies.session.getUid(),1);
                }else {
                    EyecatManager.getInstance().getICVSSUserInstance().equesSetPirEnable(Cookies.session.getUid(),0);
                }
                break;
            case R.id.do_not_disturb_onoff:
                if(isChecked){

                }else {

                }
                break;

        }
    }

//    protected void handler(Message msg) {
//        switch (msg.what) {
//            case HANDLER_WAHT_MSGRESP:
//                JSONObject json = (JSONObject) msg.obj;
//                String jsonMsg = json.toString();
//                String method = json.optString(Method.METHOD);
//                ELog.v("EyecatSettingActivity",jsonMsg);
//                if(method.equals(Method.METHOD_BDYLIST)){
//                    JSONArray bdylist = json.optJSONArray("bdylist");
//                    JSONObject bdy = bdylist.optJSONObject(0);
//                    nickname = bdy.optString("nick");
//                }else if(method.equals(Method.METHOD_ALARM_ENABLE_RESULT)){
//                    int result = json.optInt("result");
//                    if(result==1){
//                        if(settingflag){
//                            Toast.makeText(this, "人体侦测开关打开成功", Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(this, "人体侦测开关关闭成功", Toast.LENGTH_SHORT).show();
//                        }
//                    }else{
//                        Toast.makeText(this, "人体侦测开关设置失败", Toast.LENGTH_SHORT).show();
//                    }
//                }else if(method.equals(Method.METHOD_DB_LIGHT_ENABLE_RESULT)){
//                    int result = json.optInt("result");
//                    if(result==1){
//                        if(settingflag){
//                            Toast.makeText(this, "门铃灯开关打开成功", Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(this, "门铃灯开关关闭成功", Toast.LENGTH_SHORT).show();
//                        }
//                    }else{
//                        Toast.makeText(this, "门铃灯开关设置失败", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                break;
//        }
//    }


}
