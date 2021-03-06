package cc.wulian.app.model.device.impls.controlable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.List;

import cc.wulian.app.model.device.category.Category;
import cc.wulian.app.model.device.category.DeviceClassify;
import cc.wulian.app.model.device.interfaces.DeviceShortCutControlItem;
import cc.wulian.app.model.device.interfaces.DeviceShortCutSelectDataItem;
import cc.wulian.app.model.device.interfaces.DialogOrActivityHolder;
import cc.wulian.h5plus.common.Engine;
import cc.wulian.h5plus.common.JsUtil;
import cc.wulian.h5plus.interfaces.H5PlusWebViewContainer;
import cc.wulian.h5plus.view.H5PlusWebView;
import cc.wulian.ihome.wan.entity.AutoActionInfo;
import cc.wulian.ihome.wan.entity.AutoConditionInfo;
import cc.wulian.ihome.wan.util.ConstUtil;
import cc.wulian.ihome.wan.util.StringUtil;
import cc.wulian.smarthomev5.R;
import cc.wulian.smarthomev5.activity.devicesetting.DeviceSettingActivity;
import cc.wulian.smarthomev5.fragment.house.HouseKeeperSelectControlDeviceDataFragment;
import cc.wulian.smarthomev5.service.html5plus.plugins.PluginModel;
import cc.wulian.smarthomev5.service.html5plus.plugins.PluginsManager;
import cc.wulian.smarthomev5.service.html5plus.plugins.SmarthomeFeatureImpl;
import cc.wulian.smarthomev5.tools.MoreMenuPopupWindow;
import cc.wulian.smarthomev5.tools.Preference;
import cc.wulian.smarthomev5.utils.IntentUtil;
import cc.wulian.smarthomev5.utils.LanguageUtil;

/**
 * Created by yuxiaoxuan on 2016/12/30.
 * 中央空调
 */
@DeviceClassify(devTypes = {"OZ"}, category = Category.C_CONTROL)
public class WL_OZ_CentralAir extends ControlableDeviceImpl {
    public WL_OZ_CentralAir(Context context, String type )
    {
        super(context, type);
    }
    private String pluginName="Device_OZ_CenterAir.zip";
    private H5PlusWebView webView;
    private TextView textView;
    private int iconDefualt=cc.wulian.app.model.device.R.drawable.device_oz;
    //主页
    private static String controlPagePath="file:///android_asset/Device_OZ_CenterAir/index.html";
    private static String housekeeperPagePath="file:///android_asset/Device_OZ_CenterAir/bulterIndex.html";
    //设备信息
    private String deviceInfoPagePath="file:///android_asset/Device_OZ_CenterAir/centerAirDeviceInfo.html";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveState) {
        View view=inflater.inflate(R.layout.device_ozcentralair,container,false);
        return view;
    }
    private static boolean isPlugin=true;//是否使用插件

    @Override
    public void onViewCreated(View view, Bundle saveState) {
        super.onViewCreated(view, saveState);
        webView = (H5PlusWebView) view.findViewById(R.id.device_webview);
        textView= (TextView) view.findViewById(R.id.search_tv);
        textView.setVisibility(View.GONE);
        SmarthomeFeatureImpl.setData(SmarthomeFeatureImpl.Constants.DEVICEID, this.devID);
        SmarthomeFeatureImpl.setData(SmarthomeFeatureImpl.Constants.GATEWAYID, this.gwID);
//        Engine.bindWebviewToContainer((H5PlusWebViewContainer)this.getCurrentFragment(), webView);
        if(isPlugin){
            getPlugin("index.html","deviceOzIndex",mContext);
        }
        else{
            webView.loadUrl(controlPagePath);
        }
    }
    /*设置设备图标*/
    /*@Override
    public Drawable getDefaultStateSmallIcon() {
        Drawable icon = getResources().getDrawable(R.drawable.device_oz_centralair);
        return icon;
    }*/

    /*设置设备名称*/

    @Override
    public String getDefaultDeviceName() {
        String defaultName = super.getDefaultDeviceName();
        if (StringUtil.isNullOrEmpty(defaultName)) {
            defaultName =getString(cc.wulian.app.model.device.R.string.device_name_central_air_conditioning);
        }
        return defaultName;
    }

    /*设备列表右侧快速控制区域 控制页面*/

    @Override
    public DeviceShortCutControlItem onCreateShortCutView(DeviceShortCutControlItem item, LayoutInflater inflater) {
        if (item == null) {
            item = new shortCutItemForOZ(inflater.getContext());
        }
        item.setWulianDevice(this);
        return super.onCreateShortCutView(item, inflater);
    }
    /*设备控制区域的快捷键*/
    private class shortCutItemForOZ extends DeviceShortCutControlItem{

        private LinearLayout controlableLineLayout = null;
        private ImageView openImageView = null;
        private ImageView stopImageView = null;
        private ImageView closeImageView = null;
        public shortCutItemForOZ(Context context) {
            super(context);
            controlableLineLayout = (LinearLayout) inflater.inflate(R.layout.device_short_cut_control_controlable, null);
            openImageView = (ImageView) controlableLineLayout.findViewById(R.id.device_short_cut_control_open_iv);
            stopImageView = (ImageView) controlableLineLayout.findViewById(R.id.device_short_cut_control_stop_iv);
            closeImageView = (ImageView) controlableLineLayout.findViewById(R.id.device_short_cut_control_close_iv);
            //三个按键 开、关、停都隐藏
            openImageView.setVisibility(View.GONE);
            stopImageView.setVisibility(View.GONE);
            closeImageView.setVisibility(View.GONE);
        }
    }

    /*管家功能设置区域*/

    @Override
    public DeviceShortCutSelectDataItem onCreateShortCutSelectDataView(DeviceShortCutSelectDataItem item, LayoutInflater inflater, AutoActionInfo autoActionInfo) {
        if(item == null){
            item = new WL_OZ_CentralAir.ShortCutControlableDeviceSelectDataItem(inflater.getContext());
        }
        item.setWulianDeviceAndSelectData(this, autoActionInfo);
        return item;
    }

    /*管家快捷控制区域*/
    private class ShortCutControlableDeviceSelectDataItem extends  DeviceShortCutSelectDataItem
    {
        private LinearLayout controlableLineLayout= null;
        private ImageView openImageView;
        private ImageView openImageViewColor;
        private ImageView closeImageView;
        private ImageView stopImageView;
        public ShortCutControlableDeviceSelectDataItem(Context context) {
            super(context);
            controlableLineLayout = (LinearLayout)inflater.inflate(R.layout.device_short_cut_control_controlable, null);
            controlableLineLayout.findViewById(R.id.open_imageview_color_layout).setVisibility(View.GONE);
            openImageView = (ImageView)controlableLineLayout.findViewById(R.id.device_short_cut_control_open_iv);
            stopImageView = (ImageView)controlableLineLayout.findViewById(R.id.device_short_cut_control_stop_iv);
            closeImageView = (ImageView)controlableLineLayout.findViewById(R.id.device_short_cut_control_close_iv);
            openImageView.setVisibility(View.GONE);
            stopImageView.setVisibility(View.GONE);
            closeImageView.setVisibility(View.GONE);
            controlLineLayout.addView(controlableLineLayout);
        }
    }

    /*管家主功能区，以H5形式实现*/

    @Override
    public DialogOrActivityHolder onCreateHouseKeeperSelectControlDeviceDataView(LayoutInflater inflater, AutoActionInfo autoActionInfo) {
        DialogOrActivityHolder holder=new DialogOrActivityHolder();
        holder.setShowDialog(false);
        if(HouseKeeperSelectControlDeviceDataFragment.isShowHouseKeeperSelectControlDeviceDataView){
            HouseKeeperSelectControlDeviceDataFragment.isShowHouseKeeperSelectControlDeviceDataView=false;
            View contentView=inflater.inflate(R.layout.device_ozcentralair,null);
            webView=(H5PlusWebView) contentView.findViewById(R.id.device_webview);
            textView= (TextView) contentView.findViewById(R.id.search_tv);
            textView.setVisibility(View.GONE);
            if(autoActionInfo.getEpData()!=null&&autoActionInfo.getEpData().equals("")){
                SmarthomeFeatureImpl.setData("kBulterEPData", "");
            }else if(autoActionInfo.getEpData()!=null){
                SmarthomeFeatureImpl.setData("kBulterEPData", autoActionInfo.getEpData());
            }
            webView.setWebviewId("OZ_CentralAirBulter");
            SmarthomeFeatureImpl.setData(SmarthomeFeatureImpl.Constants.DEVICEID, this.devID);
            SmarthomeFeatureImpl.setData(SmarthomeFeatureImpl.Constants.GATEWAYID, this.gwID);
            Engine.bindWebviewToContainer((H5PlusWebViewContainer)this.getCurrentFragment(), webView);

            if(isPlugin){
                getPlugin("bulterIndex.html","OZ_CentralAirBulter",DeviceSettingActivity.instance);
            }else{
                Handler handler = new Handler(Looper
                        .getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl(housekeeperPagePath);
                    }
                });
            }
            holder.setContentView(contentView);
            HouseKeeperSelectControlDeviceDataFragment.setActionBarClickRightListener(new HouseKeeperSelectControlDeviceDataFragment.ActionBarClickRightListener() {

                @Override
                public void doSomething(AutoActionInfo autoActionInfo) {
                    String epData=SmarthomeFeatureImpl.getData("kMideaBulterEpData", "");
                    autoActionInfo.setEpData(epData);
                    SmarthomeFeatureImpl.setData("kMideaBulterEpData", epData);
                }
            });
        }
        return holder;
    }

    private void getPlugin(final String urlName, final String htmlID, final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PluginsManager pm = PluginsManager.getInstance();
                pm.getHtmlPlugin(context, pluginName,
                        new PluginsManager.PluginsManagerCallback() {

                            @Override
                            public void onGetPluginSuccess(PluginModel model) {
                                textView.setVisibility(View.GONE);
                                File file = new File(model.getFolder(),
                                        urlName);
                                String uri = "file:///android_asset/disclaimer/error_page_404_en.html";
                                if (file.exists()) {
                                    uri = "file:///" + file.getAbsolutePath();
                                } else if (LanguageUtil.isChina()) {
                                    uri = "file:///android_asset/disclaimer/error_page_404_zh.html";
                                }
                                final String uriString = uri;
                                Preference.getPreferences().saveOzCentralAirUri(uri);
                                Preference.getPreferences().saveHxOZDeviceInfo("file:///"+model.getFolder()+"/centerAirDeviceInfo.html");
                                Handler handler = new Handler(Looper
                                        .getMainLooper());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        webView.setWebviewId(htmlID);
                                        webView.loadUrl(uriString);
                                    }
                                });
                            }

                            @Override
                            public void onGetPluginFailed(final String hint) {
                                if((!Preference.getPreferences().get30ASwichUri().equals("noUri"))){
                                    return;
                                }
                                if (hint != null && hint.length() > 0) {
                                    Handler handler = new Handler(Looper
                                            .getMainLooper());
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(mContext, hint,
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
            }
        }).start();
    }
    @Override
    public void registerEPDataToHTML(H5PlusWebView pWebview, String callBackId,String cmd) {
        this.pWebview = pWebview;
        if(mapCallbackID==null){
            mapCallbackID=new ArrayMap<>();
        }
        mapCallbackID.put(cmd,callBackId);
    }
    @Override
    public String getDevWebViewCallBackId(String cmd, String ep, String mode, String devID) {
        String callbackID = "";
        if (StringUtil.isNullOrEmpty(mode)) {
            mode = "6";
        }
        if (cmd.equals("13")) {
            callbackID = "12-0-" + devID;
        } else {
            callbackID = cmd + "-" + mode + "-" + devID;
        }
        return callbackID;
    }
    /*增加更多菜单项*/

    @Override
    protected List<MoreMenuPopupWindow.MenuItem> getDeviceMenuItems(final MoreMenuPopupWindow manager) {
        List<MoreMenuPopupWindow.MenuItem> items = super.getDeviceMenuItems(manager);
        MoreMenuPopupWindow.MenuItem deviceInfo = new MoreMenuPopupWindow.MenuItem(mContext) {
            @Override
            public void initSystemState() {
                titleTextView.setText(mContext.getString(R.string.setting_device_desc));
                iconImageView.setImageResource(R.drawable.device_setting_more_setting);
            }

            @Override
            public void doSomething() {
//                String setttingFilePath = Preference.getPreferences().getAuCurtainSettingUri();
                SmarthomeFeatureImpl.setData(SmarthomeFeatureImpl.Constants.DEVICEID, WL_OZ_CentralAir.this.devID);
                SmarthomeFeatureImpl.setData(SmarthomeFeatureImpl.Constants.GATEWAYID, WL_OZ_CentralAir.this.gwID);
                SmarthomeFeatureImpl.setData(SmarthomeFeatureImpl.Constants.EP, WL_OZ_CentralAir.this.ep);
                SmarthomeFeatureImpl.setData(SmarthomeFeatureImpl.Constants.EPDATA,WL_OZ_CentralAir.this.epData);
                if(isPlugin){
                    deviceInfoPagePath=Preference.getPreferences().getHxOZDeivceInfo();
                }
                IntentUtil.startHtml5PlusActivity(mContext,
                        deviceInfoPagePath,
                        getDefaultDeviceName(),
                        mContext.getString(R.string.setting_device_desc));
                manager.dismiss();
            }
        };

        if (isDeviceOnLine()) {
            items.add(deviceInfo);
        }
        return items;
    }
}
