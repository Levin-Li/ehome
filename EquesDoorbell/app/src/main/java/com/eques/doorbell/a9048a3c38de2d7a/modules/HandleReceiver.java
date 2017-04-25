package com.eques.doorbell.a9048a3c38de2d7a.modules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.eques.doorbell.a9048a3c38de2d7a.tools.JSONBuilder;
import com.eques.doorbell.a9048a3c38de2d7a.tools.TaskExecutor;
import com.eques.doorbell.a9048a3c38de2d7a.tools.http.HttpManager;
import com.eques.doorbell.a9048a3c38de2d7a.tools.logger.Logger;
import com.eques.doorbell.a9048a3c38de2d7a.tools.logger.LoggerFactory;
import com.eques.doorbell.a9048a3c38de2d7a.tools.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;


public class HandleReceiver extends BroadcastReceiver {

	//根据不同厂商，需要修改的参数 {
	private static final String TAG = "tp_eques";
	
	private static final String KEY_ID = "a9048a3c38de2d7a";
	
	private static final String EQUES_APPKEY = "FNXiNhNZnRar5QbAWYJ2QX4PNfdkwNNP";

	public static final String BASEURL_TEST = "http://testv2.wulian.cc:52181";
//	public static final String BASEURL = "http://acs.wuliancloud.com:33443";
	public static final String BASEURL = "https://v2.wuliancloud.com:52182";
    //根据不同厂商，需要修改的参数 }
	
	/**
	 * 推送消息的action
	 */
	public static final String ACTION_EQUES_PUSH_MESSAGE = "com.eques.action.PUSH_MESSAGE" + "." + KEY_ID;
	
	/**
	 * 系统启动的action，通知第三方应用启动service
	 */
	public static final String ACTION_EQUES_BOOT_COMPLETED = "com.eques.action.BOOT_COMPLETED" + "." + KEY_ID;
	
	/**
	 * 系统ping的action，保持心跳使用，可以不使用
	 */
	public static final String ACTION_EQUES_PING = "com.eques.action.PING" + "." + KEY_ID;
	
	/**
	 * 第三方应用发送的验证信息
	 */
	public static final String ACTION_EQUES_ACCOUNT_VALIDATION = "com.eques.action.ACCOUNT_VALIDATION";
	
	/**
     * 登录成功后发送设备信息及绑定的用户信息给第三方应用
     */
    public static final String ACTION_EQUES_LOGIN_INFO = "com.eques.action.LOGIN_INFO" + "." + KEY_ID;
	
	/**
	 * 消息方法名，用于区分是那种消息类型
	 */
	public static final String EXTRA_METHOD = "method";
	
	/**
	 * 设备ID
	 */
	public static final String EXTRA_DEVID = "devid";
	
	/**
	 * 是否已经上传完成。
	 *	1. 上传成功
	 *	2. 未上传
	 */
	public static final String EXTRA_UPLOAD = "upload";
	
	/**
	 * 消息的内容类型
	 *	1. 文本
	 *  2. 图片
	 *  3. 视频
	 *  4.  Zip压缩包
	 *  5. 音频
	 */
	public static final String EXTRA_TYPE = "type";
	
	/**
	 * 上传文件的ID
	 *	门铃消息使用，其他消息不使用。
	 */
	public static final String EXTRA_FID = "fid";
	
	/**
	 * 与设备建立绑定关系的用户的用户名
	 */
	public static final String EXTRA_USERNAME = "username";
	
	/**
	 * 设备在移康云端服务器上的唯一标识
	 */
    public static final String EXTRA_DEVBID = "devbid";
    
    public static final String EXTRA_KEY_ID = "key_id";
    
    public static final String EXTRA_EQUES_APPKEY = "eques_appkey";
    
    public static final String EXTRA_JPUSH_APPKEY = "jpush_appkey";
    
    public static final String EXTRA_JPUSH_MASTER_SECRET = "jpush_master_secret";
    
    public static final String EXTRA_JPUSH_APPKEY_BUS = "jpush_appkey_bus";
    
    public static final String EXTRA_JPUSH_MASTER_SECRET_BUS = "jpush_master_secret_bus";
	PreferenceManager preferenceManager = PreferenceManager.getInstance();
	private Logger logger = LoggerFactory.getLogger(HandleReceiver.class);
	@Override
	public void onReceive(Context context, final Intent intent) {
		String action = intent.getAction();
		logger.debug("action: " + action);
		
		//注意，正式版本不要启动Activity，否则将影响可视门铃的正常使用！！！
		if (ACTION_EQUES_PUSH_MESSAGE.equals(action)) {
			TaskExecutor.getInstance().executeNewThread(new Runnable() {
				@Override
				public void run() {
					//根据不同的消息类型，获取不同的参数
					try {
						Bundle bundle = intent.getExtras();
						int method = intent.getIntExtra(EXTRA_METHOD, 0);
						String devid = "";
						int upload = 0;
						int type = 0;
						String fid = "";
						String username = "";
						if(bundle.containsKey(EXTRA_UPLOAD)) {
							upload = intent.getIntExtra(EXTRA_UPLOAD, 0);
						}
						if(bundle.containsKey(EXTRA_TYPE)) {
							type = intent.getIntExtra(EXTRA_TYPE, 0);
						}
						if(bundle.containsKey(EXTRA_FID)) {
							fid = intent.getStringExtra(EXTRA_FID);
						}
						if(preferenceManager.getGlobalPreference() != null){
							username = preferenceManager.getGlobalPreference().getString("username", "");
							devid = preferenceManager.getGlobalPreference().getString("bid", "");
						}
						if (1 == method || 2 == method) {
							String alertDesc = "";
							if (1 == method) {
								alertDesc = "可视门铃检测到有人在门外";
							}else if (2 == method) {
								alertDesc = "可视门铃检测到有人按门铃";
							}
							JSONObject jsonObject = new JSONBuilder().put("cmd", "ALARM").put("devType", "CMMY02").put("user", username).put("devId", devid).put("alertTime", System.currentTimeMillis() / 1000 + "").put("alertType", method + "")
									.put("alertDesc", alertDesc).put("ext", new JSONBuilder().put("upload", upload + "").put("type", type + "").put("fid", fid).build()).build();
							Map<String, String> header = new HashMap<String, String>();
							header.put("cmd", "ALARM");
							JSONObject result = HttpManager.getWulianCloudProvider().post(BASEURL + "/OMS/device/alert", header, jsonObject.toString());
//							JSONObject result = HttpManager.getWulianCloudProvider().post(BASEURL + "/device/oms/alert", header, jsonObject.toString());
							logger.debug("alarm request:"+jsonObject.toString()+";alrm response:" + result.toString());
						}
						logger.debug(" method: " + method +" username: " + username+ ", devid: " + devid + ", upload: " + upload +
								",type: " + type + ", fid: " + fid);
					}catch (Exception e ){
						e.printStackTrace();
						logger.debug("method : error");
					}
				}
			});

		} else if (ACTION_EQUES_BOOT_COMPLETED.equals(action)) {
			//在监听到该广播后，需要将专属的APPKEY发送给可视门铃端，只有获取到正确的APPKEY后，可视门铃才可以正常的登录。
			logger.debug("set eques appkey: " + EQUES_APPKEY);
			
			Intent in = new Intent(ACTION_EQUES_ACCOUNT_VALIDATION);
			//传入key_id作为验证，要和厂商所使用的二维码中的key_id对应
			in.putExtra(EXTRA_KEY_ID, KEY_ID);
			in.putExtra(EXTRA_EQUES_APPKEY, EQUES_APPKEY);
			
			context.sendBroadcast(in);
			
		} else if (ACTION_EQUES_PING.equals(action)) {

		} else if (ACTION_EQUES_LOGIN_INFO.equals(action)) {
			try {
				String userName = intent.getStringExtra(EXTRA_USERNAME);
				String bid = intent.getStringExtra(EXTRA_DEVBID);
				logger.debug("userName: " + userName + "\t bid: " + bid);
				if(preferenceManager.getGlobalPreference() != null){
					preferenceManager.getGlobalPreference().putString("username", userName);
					preferenceManager.getGlobalPreference().putString("bid", bid);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		} else {
			logger.debug( "error action");
		}
	}
}
