package com.yard.manager.modules.util;

import com.yard.manager.platform.mail.MailSenderInfo;
import com.yard.manager.platform.mail.SimpleMailSender;

/**
 * 邮件发送入口
 * @author : xiaym
 * @date : 2015年8月14日 上午10:10:06
 * @version : 1.0
 */
public class MailUtil {
	private static SimpleMailSender sms = new SimpleMailSender();
	private static MailSenderInfo mailInfo = new MailSenderInfo();
	
	static {
		  
	      mailInfo.setMailServerHost(PropertiesFactory.getMAIL_SERVER_HOST());   
	      mailInfo.setMailServerPort(PropertiesFactory.getMAIL_SERVER_PORT());   
	      mailInfo.setValidate(PropertiesFactory.isMAIL_VALIDATE());   
	      mailInfo.setUserName(PropertiesFactory.getMAIL_USERNAME());   
	      mailInfo.setPassword(PropertiesFactory.getMAIL_PASSWORD());
	      mailInfo.setFromAddress(PropertiesFactory.getMAIL_FROMADDRESS());  
	}
	
	public MailUtil() {
		
	}
	
	public MailUtil(String toAddress, String subject, String content) {
		mailInfo.setToAddress(toAddress);
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
	}
	
	/**
	 * 发送纯文本邮件
	 * @return
	 */
	public boolean sendTextMail() {
		return sms.sendTextMail(mailInfo);
	}
	
	/**
	 * 发送HTML模板邮件
	 * @return
	 */
	public boolean sendHtmlMail() {
		return sms.sendHtmlMail(mailInfo);
	}	
	
	public static void main(String[] args) {
		MailUtil mail = new MailUtil("524768055@qq.com", "邮箱测试", "发来祝福了！");
		mail.sendTextMail();
	}
}
