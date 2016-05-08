package com.cddayuanzi.weixin.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.cddayuanzi.wxsdk.sendmessage.responseentity.AcceptMessage;
import com.yard.core.util.SHA1;

/**
 * 微信入口处理
 * 
 * @author xiayuanming
 *
 */
public class WxMainManager {

	/**
	 * 消息校验
	 * 
	 * 校验流程： 加密/校验流程如下： 1. 将token、timestamp、nonce三个参数进行字典序排序 2. 将三个参数字符串拼接成一个字符串进行sha1加密 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
	 * 
	 * @param TOKEN
	 *            TOKEN
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 *            随机数
	 * @param signature
	 *            微信加密签名
	 * @return
	 */
	public static boolean verification(String TOKEN, String timestamp, String nonce, String signature) {
		List<String> list = new ArrayList<String>();
		list.add(TOKEN);
		list.add(timestamp);
		list.add(nonce);
		Collections.sort(list);
		String temp = StringUtils.join(list.toArray());
		String digest = SHA1.digest(temp);

		if (!digest.equals(signature)) {
			return false;
		}

		return true;
	}

	public static void downVali(HttpServletResponse response, String echostr) {
		if (!StringUtils.isBlank(echostr)) {
			PrintWriter w;
			try {
				w = response.getWriter();
				w.write(echostr);
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
	}

	/**
	 * 命令解析
	 * 
	 * @param command
	 * @return
	 */
	public static String[] analysisCommand(String command) {
		if (StringUtils.isBlank(command)) {
			return new String[] {};
		} else if (command.equals("+")) {
			return new String[] { "+" };
		} else {
			String[] commands = StringUtils.split(command, "\\+");
			return commands;
		}
	}

	/**
	 * 微信交互信息解析
	 * 
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static AcceptMessage getMessage(HttpServletRequest request) throws IOException {
		AcceptMessage message = new AcceptMessage();
		BufferedReader br = request.getReader();
		StringBuilder sb = new StringBuilder();
		String str = null;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		}
		String xml = sb.toString();

		if (xml != null) {
			try {
				Document doc = DocumentHelper.parseText(xml);
				Element root = doc.getRootElement();
				Iterator<Node> it = root.nodeIterator();

				while (it.hasNext()) {
					Node n = it.next();
					String nodeName = n.getName();
					String nodeValue = n.getStringValue();

					if ("ToUserName".equals(nodeName)) {
						message.setToUserName(nodeValue);
					} else if ("FromUserName".equals(nodeName)) {
						message.setFromUserName(nodeValue);
					} else if ("CreateTime".equals(nodeName)) {
						message.setCreateTime(nodeValue);
					} else if ("MsgType".equals(nodeName)) {
						message.setMsgType(nodeValue);
					} else if ("MsgId".equals(nodeName)) {
						message.setMsgId(nodeValue);
					} else if ("Title".equals(nodeName)) {
						message.setTitle(nodeValue);
					} else if ("Description".equals(nodeName)) {
						message.setDescription(nodeValue);
					} else if ("Url".equals(nodeName)) {
						message.setUrl(nodeValue);
					} else if ("Location_x".equals(nodeName)) {
						message.setLocation_x(nodeValue);
					} else if ("Location_y".equals(nodeValue)) {
						message.setLocation_y(nodeValue);
					} else if ("Scale".equals(nodeName)) {
						message.setScale(nodeValue);
					} else if ("Label".equals(nodeName)) {
						message.setLabel(nodeValue);
					} else if ("MediaId".equals(nodeName)) {
						message.setMediaId(nodeValue);
					} else if ("ThumbMediaId".equals(nodeName)) {
						message.setThumbMediaId(nodeValue);
					} else if ("PicUrl".equals(nodeName)) {
						message.setPicUrl(nodeValue);
					} else if ("Content".equals(nodeName)) {
						message.setContent(nodeValue);
					} else if ("Format".equals(nodeName)) {
						message.setFormat(nodeValue);
					} else if ("Format".equals(nodeName)) {
						message.setFormat(nodeValue);
					} else if ("Event".equals(nodeName)) {
						message.setEvent(nodeValue);
					} else if ("EventKey".equals(nodeName)) {
						message.setEventKey(nodeValue);
					} else if ("Ticket".equals(nodeName)) {
						message.setTicket(nodeValue);
					}

				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			} finally {
				/* br.close(); */
			}
		}
		return message;
	}

}
