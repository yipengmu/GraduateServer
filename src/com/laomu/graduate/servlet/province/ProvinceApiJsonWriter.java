package com.laomu.graduate.servlet.province;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.laomu.graduate.base.BaseApiJsonWriter;

public class ProvinceApiJsonWriter extends BaseApiJsonWriter {
	private ProvinceBean mProvinceBean;

	ProvinceApiJsonWriter(HttpServletResponse response, ProvinceBean proBean) {
		super(response,proBean);
		mProvinceBean = proBean;
	}

	@Override
	public String getOutputJsonData() {
		return new JSONObject().toJSONString(mProvinceBean).toString();
	}

}
