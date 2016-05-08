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
 * 
 * @author : leihc
 * @date : 2015年6月5日
 * @version : 1.0
 */
public class LevelUpConfig {

	private static Map<Integer, LevelUpInfo> datas = new HashMap<Integer, LevelUpInfo>();
	
	/**
	 * @see cn.dayuanzi.config.IConfig#init()
	 */
	public static void init() {
		Document docXml;
		try {
			SAXReader reader = new SAXReader();
			docXml = reader.read(YardUtils.getResourcesFile("levelUp.xml"));
			Element root = docXml.getRootElement();
			for (Iterator<?> i = root.elementIterator(); i.hasNext();) {
				Element element = (Element) i.next();
				Integer level = Integer.parseInt(element.attributeValue("level"));
				Integer exp = Integer.parseInt(element.attributeValue("exp"));
				Integer lindou = Integer.parseInt(element.attributeValue("lindou"));
				LevelUpInfo info = new LevelUpInfo();
				info.setLevel(level);
				info.setExp(exp);
				info.setLindou(lindou);
				datas.put(level, info);
			}
		} catch (DocumentException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * @see cn.dayuanzi.config.IConfig#reload()
	 */

	public static Map<Integer, LevelUpInfo> getDatas() {
		datas.clear();
		init();
		return datas;
	}
	
}
