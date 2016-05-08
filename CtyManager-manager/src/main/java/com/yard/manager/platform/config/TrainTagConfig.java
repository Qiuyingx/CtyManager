/**
 * 
 */
package com.yard.manager.platform.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.yard.manager.modules.util.YardUtils;

/** 
 * @ClassName: TrainTagConfig 
 * @Description: TODO
 * @author qiuyingxiang
 * @date 2015年8月18日 上午10:10:07 
 *  
 */

public class TrainTagConfig {

private static final Map<Integer, TrainTagInfo> TrainTag = new HashMap<Integer, TrainTagInfo>();
	
	/**
	 * @see cn.dayuanzi.config.TopicTagConfig#init()
	 */
	public static void init() {
		Document docXml;
		try {
			SAXReader reader = new SAXReader();
			docXml = reader.read(YardUtils.getResourcesFile("trainTag.xml"));
			Element root = docXml.getRootElement();
			for (Iterator<?> i = root.elementIterator(); i.hasNext();) {
				Element element = (Element) i.next();
				Integer id = Integer.parseInt(element.attributeValue("id"));
				String name = element.attributeValue("name");
				TrainTag.put(id, new TrainTagInfo(id,name));
			}
		} catch (DocumentException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	public static Map<Integer, TrainTagInfo> getTrainTag() {
		TrainTag.clear();
		init();
		return TrainTag;
	}
}
