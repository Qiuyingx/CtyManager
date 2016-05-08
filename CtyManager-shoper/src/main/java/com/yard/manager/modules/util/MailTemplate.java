package com.yard.manager.modules.util;

/**
 * 邮件模板
 * @author : xiaym
 * @date : 2015年8月14日 上午11:34:43
 * @version : 1.0
 */
public class MailTemplate {
	/**
	 * 开店验证通过邮件主题
	 */
	public static String vali_success_train_subject = "恭喜您，您的开店申请已经通过啦！";
	
	/**
	 * 开店验证通过邮件内容
	 * @return
	 */
	public static String getTrainSuccessHtml() {
		return "<h2>恭喜您，开店申请通过，请尽快完善您的资料</h2>";
	}
}
