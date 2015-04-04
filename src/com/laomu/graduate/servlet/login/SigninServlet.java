package com.laomu.graduate.servlet.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laomu.graduate.base.BaseHttpServlet;
import com.laomu.graduate.database.DBManeger;
import com.laomu.graduate.servlet.bean.UserBean;
import com.laomu.graduate.utils.CommonUtil;
import com.laomu.graduate.utils.http.ResponseHelper;

/**
 * Servlet implementation class SigninServlet
 */
@WebServlet("/SigninServlet")
public class SigninServlet extends BaseHttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SigninServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserBean user = new UserBean();
		user.uid = CommonUtil.getStringParam(request, "uid");
		user.uname = CommonUtil.getStringParam(request, "uname");
		user.upassword = CommonUtil.getStringParam(request, "upassword");

		boolean bExist = DBManeger.getIns().selectUserByUserId(user.uid);
		if (!bExist) {
			boolean addResult = DBManeger.getIns().addUser(user);
			if (addResult) {
				response.getWriter().print(ResponseHelper.formatResponse(0, "注册成功", user));
			} else {
				response.getWriter().print(ResponseHelper.formatResponse(-1, "注册失败", null));
			}
		} else {
			response.getWriter().print(ResponseHelper.formatResponse(-1, "该用户已存在，请更换账号", user));
		}
	}

}
