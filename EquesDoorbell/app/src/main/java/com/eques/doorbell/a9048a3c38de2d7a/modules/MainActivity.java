package com.eques.doorbell.a9048a3c38de2d7a.modules;

import android.app.Activity;
import android.os.Bundle;

import com.eques.doorbell.a9048a3c38de2d7a.R;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		TaskExecutor.getInstance().executeNewThread(new Runnable() {
//			@Override
//			public void run() {
//				String devid = "934f4227e83a4d618f27e2dca860625e";
//				String method ="1";
//				String alertDesc = "猫眼报警";
//				String user = "437492_944dacafb0";
//				JSONObject jsonObject = new JSONBuilder().put("cmd","ALARM").put("user",user).put("devType","CMMY02").put("devId",devid).put("alertTime",System.currentTimeMillis()/1000+"").put("alertType",method+"")
//						.put("alertDesc",alertDesc).put("ext",new JSONBuilder().put("upload",0+"").put("type",1+"").put("fid",0+"").build()).build();
//				Map<String,String> header = new HashMap<String,String>();
//				header.put("cmd","ALARM");
//				JSONObject response = HttpManager.getWulianCloudProvider().post(HandleReceiver.BASEURL+"/OMS/device/alert",header,jsonObject.toString());
//				System.out.println("response:"+response.toString());
//			}
//		});

	}

}
