package com.laomu.graduate.bean.http;

import java.util.HashMap;



/*failed
{
	"ret": {
		"retCod": -1,
		"retMsg": “操作失败，未登录"
	},
	"data": {
		"shop": true,
		"detail": true
	}
}
*/
public class JGResponse {
	public Ret ret;
	public HashMap<String ,Object> data;
	
	public JGResponse(Ret ret, HashMap<String, Object> data) {
		super();
		this.ret = ret;
		this.data = data;
	}
}
