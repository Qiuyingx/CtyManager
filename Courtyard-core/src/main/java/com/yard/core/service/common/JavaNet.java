package com.yard.core.service.common;
///**
// * 
// */
//package org.hbb.weihotel.service.common;
//
//import java.io.InputStream;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.sql.SQLException;
//
//import org.iteam.commons.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
///**
// * @Title: JavaNet.java
// * @Description:
// * @author Wangchaoyong
// * @date 上午9:08:05
// * @version V1.0
// */
//public class JavaNet {
//	private static String httpUrl1 = null;
//	private static Logger log = LoggerFactory.getLogger(JavaNet.class);
//	static {
//		try {
//			httpUrl1 = ParameterService.getServer().queryObj("CASMENAME");
//		} catch (SQLException e) {
//			log.info(e.getMessage());
//		}
//	}
//
//	public static boolean service(String tel, String mobile,
//			String companyName, String email) throws Exception {
//		if (null == httpUrl1 || httpUrl1.equals(""))
//			return false;
//		try {
//			String sJson = "[{\"tel\":\"" + tel + "\"," + "\"mobile\":\""
//					+ mobile + "\"," + "\"companyName\":\"" + companyName
//					+ "\"," + "\"email\":\"" + email + "\"" + "}]";
//			URL url = new URL(httpUrl1);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setDoInput(true);
//			conn.setDoOutput(true);
//			conn.setRequestMethod("POST");
//			conn.setRequestProperty("User-Agent",
//					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//			PrintWriter out = new PrintWriter(new OutputStreamWriter(
//					conn.getOutputStream(), "utf-8"));
//			out.print("jsons=" + sJson);
//			out.flush();
//			out.close();
//			InputStream is = conn.getInputStream();
//			byte buffer[] = new byte[is.available()];
//			StringBuffer sb = new StringBuffer();
//			int i = 0;
//			while ((i = is.read(buffer)) != -1) {
//				sb.append(new String(buffer, 0, i, "UTF-8"));
//			}
//			JSONObject json = new JSONObject(sb.toString());
//			if (Boolean.parseBoolean(json.get("msg").toString()))
//				return true;
//			return false;
//		} catch (Exception e) {
//			log.info(e.getMessage());
//			return false;
//		}
//	}
//
//	public static void main(String[] args) throws Exception {
//		JavaNet.service("", "", "", "");
//	}
//}
