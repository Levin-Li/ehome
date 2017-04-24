//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.eques.doorbell.a9048a3c38de2d7a.tools.http;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;

public interface HttpProvider {
    JSONObject post(String var1);

    JSONObject post(String var1, Map<String, String> var2);

    JSONObject post(String var1, Map<String, String> var2, byte[] var3);

    JSONObject post(String var1, Map<String, String> var2, String var3);

    JSONObject post(String var1, Map<String, String> var2, Map<String, String> var3);

    JSONObject post(String var1, Map<String, String> var2, JSONObject var3);

    byte[] getPicture(String var1);

    boolean verificationResponse(String var1);
}
