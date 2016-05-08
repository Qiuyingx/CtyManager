package com.yard.manager.platform.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.yard.manager.modules.util.YardUtils;

/**
 * 
 * @author : leihc
 * @date : 2015年6月8日
 * @version : 1.0
 */
public class GainExpConfig {

	private final static Map<Integer, ExpInfo> datas = new HashMap<Integer, ExpInfo>();
	/**
	 * @see cn.dayuanzi.config.IConfig#init()
	 */
	public static void init() {
		Document docXml;
		try {
			SAXReader reader = new SAXReader();
			docXml = reader.read(YardUtils.getResourcesFile("gainExp.xml"));
			Element root = docXml.getRootElement();
			for (Iterator<?> i = root.elementIterator(); i.hasNext();) {
				Element element = (Element) i.next();
				ExpInfo info = new ExpInfo();
				Integer id = Integer.parseInt(element.attributeValue("id"));
				int everyTime = Integer.parseInt(element.attributeValue("exp"));
				info.setId(id);
				info.setRemark(element.attributeValue("remark"));
				info.setExp(everyTime);
				info.setImage(element.attributeValue("image"));
				info.setDec(element.attributeValue("dec"));
				if(!StringUtils.isBlank(element.attributeValue("limitDaily"))){
					info.setLimitDaily(Integer.parseInt(element.attributeValue("limitDaily")));
				}
				datas.put(id, info);
			}
		} catch (DocumentException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	public static Map<Integer, ExpInfo> getDatas() {
		datas.clear();
		init();
		return datas;
	}
	
}
