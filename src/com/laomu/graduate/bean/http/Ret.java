package com.laomu.graduate.bean.http;

public class Ret {
	public int retCode = 0;
	public String retMsg = "";
	
	public Ret(int retCode, String retMsg) {
		super();
		this.retCode = retCode;
		this.retMsg = retMsg;
	}
}
