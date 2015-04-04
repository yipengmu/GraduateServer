package com.laomu.graduate.utils.http;

import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;
import com.laomu.graduate.bean.http.JGResponse;
import com.laomu.graduate.bean.http.Ret;


public class ResponseHelper {

	public static String formatResponse(int retCode, String retMsg, HashMap<String, Object> data){
		if(retCode == 0){
			//成功
			JGResponse res = new JGResponse(new Ret(retCode, retMsg), data);
			return JSONObject.toJSONString(res);
		}else{
			JGResponse res = new JGResponse(new Ret(retCode, retMsg), data);
			return JSONObject.toJSONString(res);
		}
	}
}
