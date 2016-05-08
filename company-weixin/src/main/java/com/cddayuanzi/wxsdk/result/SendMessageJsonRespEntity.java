package com.cddayuanzi.wxsdk.result;

/**
 * 发送消息接口返回
 * @author jiangbo
 *
 */
public class SendMessageJsonRespEntity extends BaseJsonRespEntity {
	private static final long serialVersionUID = -889805762831296249L;

	private int msg_id;

	public int getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(int msg_id) {
		this.msg_id = msg_id;
	} 
}
