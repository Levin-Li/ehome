package cc.wulian.smarthomev5.eyecat;

import android.content.Intent;
import android.util.Log;

import com.eques.icvss.api.ICVSSListener;
import com.eques.icvss.api.ICVSSUserInstance;
import com.eques.icvss.utils.Method;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.wulian.ihome.wan.util.MD5Util;
import cc.wulian.ihome.wan.util.StringUtil;
import cc.wulian.smarthomev5.activity.MainApplication;
import cc.wulian.smarthomev5.service.html5plus.plugins.SmarthomeFeatureImpl;


/**
 * Created by Administrator on 2017/4/18.
 */

public class EyecatManager {
    public static final String DISTRIBUTE_URL = "thirdparty.ecamzone.cc:8443";
    public static final String APPKEY = "FNXiNhNZnRar5QbAWYJ2QX4PNfdkwNNP";
    public static final String KEYID = "a9048a3c38de2d7a";
    private volatile boolean isLogined = false;
    private volatile String loginedUsername = "";
    private static EyecatManager instance = new EyecatManager();
    private Map<String,List<PacketListener>> listeners = new HashMap<String,List<PacketListener>>();
    private Map<String,EyecatDevice> devicesMap = new HashMap<String,EyecatDevice>();
    private PacketListener loginPacketListener = new PacketListener() {
        @Override
        public String getMenthod() {
            return Method.METHOD_EQUES_SDK_LOGIN;
        }

        @Override
        public void processPacket(JSONObject object) {
            String code = object.optString("code");
            if("4000".equals(code)){
                isLogined = true;
                EyecatManager.getInstance().getICVSSUserInstance().equesGetDeviceList();
            }
        }
    };
    private ICVSSListener listener = new ICVSSListener() {
        @Override
        public void onDisconnect(int i) {
            Log.i("eyecat:","disconnect:"+i);
            isLogined = false;
        }

        @Override
        public void onPingPong(int i) {

        }

        @Override
        public void onMeaasgeResponse(JSONObject jsonObject) {
            try {
                Log.i("eyecat:", jsonObject.toString());
                String method = jsonObject.optString("method");
                if (listeners.containsKey(method)) {
                    List<PacketListener> ls = listeners.get(method);
                    if (ls != null) {
                        for (PacketListener l : ls) {
                            l.processPacket(jsonObject);
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                Log.e("eyecat:","error message");
            }
        }
    };
    public ICVSSUserInstance icvss = ICVSSUserModule.getInstance(listener).getIcvss();
    public static String getUserName(){
        String username = null;
        String userId = SmarthomeFeatureImpl.getData(SmarthomeFeatureImpl.Constants.USERID);
        String endPrexix = MD5Util.encrypt(userId+"_$LKl1as34d5fc");
        endPrexix = endPrexix.substring(endPrexix.length()-10);
        username = userId+"_"+endPrexix;
        Log.i("eyecat: username is",username);
        return username;
    }
    public static EyecatManager getInstance(){
        return instance;
    }
    public void login(){
        if(isLogined){
            if(!StringUtil.equals(loginedUsername,getUserName())){
                EyecatManager.getInstance().getICVSSUserInstance().equesUserLogOut();
                isLogined = false;
            }
        }
        if(!isLogined){
            EyecatManager.getInstance().addPacketListener(loginPacketListener);
            EyecatManager.getInstance().addPacketListener(deviceListResultListener);
            EyecatManager.getInstance().addPacketListener(devstResultListener);
            EyecatManager.getInstance().addPacketListener(removedResultListener);
            EyecatManager.getInstance().addPacketListener(callListener);
            EyecatManager.getInstance().getICVSSUserInstance().equesLogin(MainApplication.getApplication(), DISTRIBUTE_URL,getUserName(),APPKEY);
            loginedUsername = getUserName();
        }
    }
    public ICVSSUserInstance getICVSSUserInstance(){
        return icvss;
    }
    public void addPacketListener(PacketListener listener){
        List<PacketListener> ls = listeners.get(listener.getMenthod());
        if(ls == null){
            ls = new ArrayList<PacketListener>();
            listeners.put(listener.getMenthod(),ls);
        }
        if(!ls.contains(listener))
            ls.add(listener);
    }
    public void removePacketListener(PacketListener listener){
        List<PacketListener> ls = listeners.get(listener.getMenthod());
        if(ls != null){
            ls.remove(listener);
        }
    }

    public void putDevice(EyecatDevice device){
        devicesMap.put(device.getBid(),device);
    }
    public void removeDevice(EyecatDevice device){
        devicesMap.remove(device.getBid());
    }
    public EyecatDevice getDevice(String bid){
        return devicesMap.get(bid);
    }
    public static abstract class PacketListener{
        private String menthod;

        public String getMenthod() {
            return menthod;
        }

        public void setMenthod(String menthod) {
            this.menthod = menthod;
        }

        public abstract  void processPacket(JSONObject object);
    }

    public static class EyecatDevice{
        private String uid;
        private String bid;
        private int status;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
    private EyecatManager.PacketListener deviceListResultListener = new EyecatManager.PacketListener() {
        @Override
        public String getMenthod() {
            return Method.METHOD_BDYLIST;
        }

        @Override
        public void processPacket(JSONObject object) {
            JSONArray bdylist = object.optJSONArray(Method.METHOD_BDYLIST);
            for(int i=0 ;i < bdylist.length() ;i++){
                JSONObject obj = bdylist.optJSONObject(i);
                EyecatManager.EyecatDevice device = new EyecatManager.EyecatDevice();
                device.setBid(obj.optString(Method.ATTR_BUDDY_BID));
                device.setStatus(0);
                EyecatManager.getInstance().putDevice(device);
            }
            JSONArray onlineList = object.optJSONArray(Method.ATTR_ONLINES);
            for(int i=0 ;i < onlineList.length() ;i++){
                JSONObject obj = onlineList.optJSONObject(i);
                EyecatManager.EyecatDevice device = new EyecatManager.EyecatDevice();
                device.setBid(obj.optString(Method.ATTR_BUDDY_BID));
                device.setUid(obj.optString(Method.ATTR_BUDDY_UID));
                device.setStatus(obj.optInt(Method.ATTR_BUDDY_STATUS));
                EyecatManager.getInstance().putDevice(device);
            }
           Log.i("eyecat:","get list devices success");
        }
    };
    private EyecatManager.PacketListener devstResultListener = new EyecatManager.PacketListener() {
        @Override
        public String getMenthod() {
            return Method.METHOD_DEVST;
        }

        @Override
        public void processPacket(JSONObject object) {
            EyecatManager.EyecatDevice device = new EyecatManager.EyecatDevice();
            device.setBid(object.optString(Method.ATTR_BUDDY_BID));
            device.setUid(object.optString(Method.ATTR_BUDDY_UID));
            device.setStatus(object.optInt(Method.ATTR_BUDDY_STATUS));
            EyecatManager.getInstance().putDevice(device);
        }
    };
    private EyecatManager.PacketListener removedResultListener = new EyecatManager.PacketListener() {
        @Override
        public String getMenthod() {
            return Method.METHOD_ONBDY_REMOVED;
        }

        @Override
        public void processPacket(JSONObject object) {
            JSONObject removeBdy = object.optJSONObject(Method.ATTR_REMOVED_BDY);
            if(removeBdy != null) {
                String bid = object.optString(Method.ATTR_BUDDY_BID);
                EyecatManager.EyecatDevice device = new EyecatManager.EyecatDevice();
                device.setBid(bid);
                EyecatManager.getInstance().removeDevice(device);
            }
        }
    };
    private EyecatManager.PacketListener callListener  = new EyecatManager.PacketListener() {
        @Override
        public String getMenthod() {
            return Method.METHOD_CALL;
        }

        @Override
        public void processPacket(JSONObject object) {
            final String bid =object.optString(Method.ATTR_FROM);
            final String state =object.optString(Method.ATTR_ZIGBEE_STATE);
            final String sid = object.optString(Method.ATTR_CALL_SID);
            if("open".equals(state)){
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("bid",bid);
                intent.setClassName(MainApplication.getApplication().getPackageName(),EyecatCallingActivity.class.getName());
                intent.putExtra("sid",sid);
                MainApplication.getApplication().startActivity(intent);

            }
        }
    };
}
