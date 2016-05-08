package com.yard.manager.merchant.train.service.thread;

import com.yard.manager.merchant.train.entity.TrainOpenViewEntity;
import com.yard.manager.modules.notice.service.NoticeService;
import com.yard.manager.modules.util.PropertiesFactory;
import com.yard.manager.platform.sms.SmsProcess;

public class TrainOpenFailedThread implements Runnable {
	private TrainOpenViewEntity entity;
	private NoticeService ns;
	
	public TrainOpenFailedThread(NoticeService ns, TrainOpenViewEntity entity) {
		this.ns = ns;
		this.entity = entity;
	}
	@Override
	public void run() {
		try {
			// 推送消息
			ns.pushUserValiTrainFailed(entity.getUserId(), entity.getRemark());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// 发送短信
			SmsProcess.getInstance().sendMsg(entity.getTel(), PropertiesFactory.getTEXT_SMS_TRAIN_FAILED());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
