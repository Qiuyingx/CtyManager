package com.yard.manager.platform.sms;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.yard.core.util.DateUtil;
import com.yard.manager.modules.util.PropertiesFactory;
import com.yard.manager.platform.http.HttpRequestClient;

public class SmsProcess {
	private static final SmsProcess  sms = new SmsProcess();
	
	public static SmsProcess getInstance() {
		return sms;
	}
	
/*	public static void main(String[] args) {
		SmsSenderService serive = new SmsSenderService();
		
		String[] mobiles = {"15281089204","18323897293","15982092619","18011511577","15982164728","18080126106","15202863539","18328386520","13558767576","13678151422","13540683055","13739493183","18280145876","13350555991","13628056830","18981987531","15199932733","15202802030","15108268270","18628191408","18615785200","13880718699","13689047965","13558758238","17608003413","18780234015","15982002536","18608008801","13982211327","13982074075","13881839673","18200500442","18628360408","15183796367","15351360985","15928936365","13666257321","15608062815","13880759531","18608000492","13551818075","13658047835","13880720976","13558649212","18280252168","18108295055","15281068225","13608080857","18280141198","15928060483","13795608868","15002841464"};
		String[] mobiles = {"18323897293"};
		
		for(String str : mobiles) {

			String code = new SmsProcess().sendMsg(str);
			System.out.println("发送短信验证码："+code);
		}
	}*/
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
		buffer.append("&smsg=").append(msg+"【觅趣小镇】");
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
		//String[] mobiles = {"15281089204","18323897293","15982092619","18011511577","15982164728","18080126106","15202863539","18328386520","13558767576","13678151422","13540683055","13739493183","18280145876","13350555991","13628056830","18981987531","15199932733","15202802030","15108268270","18628191408","18615785200","13880718699","13689047965","13558758238","17608003413","18780234015","15982002536","18608008801","13982211327","13982074075","13881839673","18200500442","18628360408","15183796367","15351360985","15928936365","13666257321","15608062815","13880759531","18608000492","13551818075","13658047835","13880720976","13558649212","18280252168","18108295055","15281068225","13608080857","18280141198","15928060483","13795608868","15002841464"};
		String[] mobiles = {"18323897293"};
		String text = "恭喜您获得觅趣小镇“最美全家福”活动积极参与奖，奖品为868元汪正儿童摄影套系+漫乐煲120元单次体验名额。儿童摄影套系将由汪正工作人员直接联系您沟通拍摄事宜，相关套系内容请关注我们微信公众号“觅趣小镇”的获奖公布内容。漫乐煲体验名额只适用于年满4岁的小朋友，且需提前预约体验时间，预约电话：028-85248996。非常感谢您对我们平台的支持，祝您生活愉快~";
		 SmsProcess sms = SmsProcess.getInstance();
		for(String str : mobiles) {
			String s = sms.sendMsg(str, text);
		}

		
	}
}
