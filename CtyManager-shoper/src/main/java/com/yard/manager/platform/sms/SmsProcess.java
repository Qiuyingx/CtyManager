package com.yard.manager.platform.sms;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.yard.manager.modules.util.PropertiesFactory;
import com.yard.manager.platform.http.HttpRequestClient;

public class SmsProcess {
	private static final SmsProcess  sms = new SmsProcess();
	
	public static SmsProcess getInstance() {
		return sms;
	}
	
	public String sendMsg(String tel, String msg){
		String code = "";
		try{
			String url = this.getUrlForLmobile(tel, msg);
			HttpRequestClient client = HttpRequestClient.getInstance()
					.setHttpGetUrl(url);
					client.execute();
			String result = client.getResponseText();
			if(StringUtils.isNotBlank(result)){
				SAXReader reader = new SAXReader();
				Document document = reader.read(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(result.getBytes()), "UTF-8")));
				Element root = document.getRootElement();
				this.validateStatusForLmobile(root, result);
			}else{
				return result;
			}
		}catch(Exception ex){
			code = null;
			ex.printStackTrace();
		}
		return code;
	}
	
	/**
	 * 
	 * @param tel
	 * @param code
	 * @return
	 */
	public String getUrlForLmobile(String tel, String msg){
		StringBuilder buffer = new StringBuilder();
		buffer.append(PropertiesFactory.getSMS_URL()).append("?");
		buffer.append("sname=").append(PropertiesFactory.getSMS_SNAME());
		buffer.append("&spwd=").append(PropertiesFactory.getSMS_SPWD());
		buffer.append("&scorpid=").append("");
		buffer.append("&sprdid=").append(PropertiesFactory.getSMS_SPRDID());
		buffer.append("&sdst=").append(tel);
		buffer.append("&smsg=").append(msg+"【邻聚】");
		return buffer.toString();
	}
	
	public String validateStatusForLmobile(Element root, String result){
		String state = root.elementText("State");
		if(!"0".equals(state)){
			return result;
		}
		return state;
	}
	
	public static void main(String[] args) {
		String s = SmsProcess.getInstance().sendMsg("18323897293", "美好的一天。");
		System.out.println("---"+s);
	}
}
