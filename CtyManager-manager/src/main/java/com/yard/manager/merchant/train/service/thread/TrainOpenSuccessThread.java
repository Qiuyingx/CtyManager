package com.yard.manager.merchant.train.service.thread;

import com.yard.manager.merchant.train.entity.TrainOpenViewEntity;
import com.yard.manager.modules.notice.service.NoticeService;
import com.yard.manager.modules.util.MailTemplate;
import com.yard.manager.modules.util.MailUtil;
import com.yard.manager.modules.util.PropertiesFactory;
import com.yard.manager.platform.sms.SmsProcess;

public class TrainOpenSuccessThread implements Runnable {
	private TrainOpenViewEntity entity;
	private NoticeService ns;
	
	public TrainOpenSuccessThread(NoticeService ns, TrainOpenViewEntity entity) {
		this.ns = ns;
		this.entity = entity;
	}
	@Override
	public void run() {
		try {
			// 推送消息
			ns.pushUserValiTrainSuccess(entity.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// 发送邮件
			MailUtil mail = new MailUtil(entity.getEmail(), MailTemplate.vali_success_train_subject, 
					MailTemplate.getTrainSuccessHtml(entity.getEmail()));
			mail.sendHtmlMail();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// 发送短信
			SmsProcess.getInstance().sendMsg(entity.getTel(), PropertiesFactory.getTEXT_SMS_TRAIN_SUCCESS());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
