package com.laomu.graduate.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.laomu.graduate.servlet.bean.UserBean;
import com.laomu.graduate.utils.CommonUtil;
import com.mysql.jdbc.log.Log4JLogger;

public class DBManeger {

	private String url = "jdbc:mysql://localhost:3306/graduatedb";
	private String user = "root";
	private String password = "";
	private String driverClass = "com.mysql.jdbc.Driver";

	private Connection conn = null;
	/**
	 * 预处理对象
	 */
	private java.sql.PreparedStatement ps;

	private final int ENV_LOCAL = 0;
	private final int ENV_REMOTE = 1;
	// 本地0，sae服务端1
	private int env = ENV_LOCAL;

	private static DBManeger ins;

	public static DBManeger getIns() {
		if (ins == null) {
			ins = new DBManeger();
		}
		return ins;
	}

	public Connection getConnection() {
		return conn;
	}

	private DBManeger() {
		try {
			if (env == 1) {
				url = "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_gangbiye";
				user = "l04myxz5wj";
				password = "j4kz10w4i0ji003hil100yi22mw0zmlx3jlljmhh";
			}
			Logger logger  =  Logger.getLogger("graduate");
			logger.getRootLogger().debug(new String ("user= "+user + " ,password= " + password ));
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, user, password);
			conn.createStatement().executeUpdate("use graduatedb");
			// createDatabase(CommonDefine.SQLCREATEUSERINFO);
		} catch (SQLException se) {
			System.out.println(driverClass + "数据库加载失败");
			se.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println(driverClass + "数据库加载失败");
			e.printStackTrace();
		}
	}

	public void createDatabase(String sql) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("drop database if exists graduatedb");
			stmt.executeUpdate("create database graduatedb");
			stmt.executeUpdate("use graduatedb");
			stmt.executeUpdate(sql);
			stmt.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
	}

	public boolean addUser(UserBean userInfo) {
		boolean result = false;
		if (userInfo == null || CommonUtil.isEmpty(userInfo.uid) || CommonUtil.isEmpty(userInfo.upassword)
				|| CommonUtil.isEmpty(userInfo.uname)) {
			return result;
		}
		try {
			String sql = "insert into userinfo (uid,upassword,uname) values (?,?,?)";
			java.sql.PreparedStatement sta = conn.prepareStatement(sql);
			sta.setString(1, userInfo.uid);
			sta.setString(2, userInfo.upassword);
			sta.setString(3, userInfo.uname);
			new Log4JLogger("http").logDebug("sql = " + sql);
			sta.executeUpdate();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean selectUserByUserId(String userId) {
		if (CommonUtil.isEmpty(userId)) {
			return false;
		}
		try {
			String sql = "select * from userinfo where uid=" + userId;
			new Log4JLogger("http").logDebug("sql = " + sql);
			java.sql.PreparedStatement sta = conn.prepareStatement(sql);
			ResultSet results = sta.executeQuery(sql);
			if (results != null && results.getRow() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean selectUserByIdAndPassword(String uid, String upassword) {
		if (CommonUtil.isEmpty(uid) || CommonUtil.isEmpty(upassword)) {
			return false;
		}
		try {
			String sql = "select * from userinfo where uid='" + uid + "' and upassword='" + upassword + "'";
			ResultSet results = conn.createStatement().executeQuery(sql);

			if (results != null) {
				results.last();
				if (results.getRow() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			conn = null;
		}
	}
}
