package com.eques.doorbell.a9048a3c38de2d7a.tools;


import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/9.
 */
public class JSONBuilder {
    private JSONObject jsonObject = new JSONObject();
    public JSONBuilder(){

    }
    public JSONBuilder(JSONObject jsonObject){
    	for(String key : jsonObject.keySet()){
			this.jsonObject.put(key,jsonObject.get(key));
    	}
    }
    public JSONBuilder(Map<String,?> map){
        for(String key :map.keySet()){
			this.jsonObject.put(key,map.get(key));
        }
    }
    public JSONBuilder put(String key,Object value){
		jsonObject.put(key,value);
        return this;
    }
    public JSONObject build(){
        return jsonObject;
    }
}
