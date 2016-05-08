package com.gen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gen.entity.Column;
import com.gen.entity.Table;
import com.gen.entity.Type;
import com.gen.util.StringUtil;

/**
 * xml文件分析
 * 
 * @author ln
 * 
 */
public class XmlAnalysis {
/**
 * @param args
 */
public static void main(String[] args) {
	String fileDir = System.getProperty("user.dir").concat("\\xml");
}
	public static List<Table> analysis() throws Exception {
		String fileDir = System.getProperty("user.dir").concat("\\xml");
		File dirFile = new File(fileDir);
		File[] files = dirFile.listFiles();
		List<Table> tables = analysisFiles(files);
		return tables;
	}

	/**
	 * @param files
	 * @return
	 * @throws Exception
	 */
	private static List<Table> analysisFiles(File[] files) throws Exception {
		List<Table> tables = new ArrayList<Table>();
		for (File file : files) {
			tables.add(analysisFile(file));
		}
		return tables;
	}

	private static Table analysisFile(File file) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(file);
		Element element = document.getDocumentElement();

		Table table = new Table();

		table.setName(element.getAttribute("tables"));
		table.setComment(element.getAttribute("comment"));
		table.setPackageMark(element.getAttribute("mark"));
		String entityPackage = element.getAttribute("type");
		//父级菜单id
		String parentResStr = element.getAttribute("parentRes");
		if(StringUtil.isNotEmpty(parentResStr)) {
			try{
				table.setParentRes(Integer.parseInt(parentResStr));
			}catch (Exception e) {
				System.err.println(table.getName()+"的父级菜单id不为数字");
			}
		}

		table.setPackageName("com.bluebox.manager");
		
		// 实体名
		String entity = entityPackage.substring(entityPackage.lastIndexOf(".") + 1);
		table.setEntityName(entity);
		// 实体的首字母小写
		String varName = entity.substring(0, 1).toLowerCase()
				+ entity.substring(1);
		table.setVarName(varName);

		NodeList childNodes = element.getChildNodes();
		List<Column> columns = new ArrayList<Column>();
		List<Column> pageShowColumns = new ArrayList<Column>();
		List<Column> queryColumns = new ArrayList<Column>();
		List<Column> editColumns = new ArrayList<Column>();
		List<Column> hiddenColumns = new ArrayList<Column>();
		
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (node.getNodeType() == 3) {//表示为常量
				continue;
			}
			
			NamedNodeMap attributes = node.getAttributes();
			Column column = initColumn(attributes);
			if(column.isPageShow()){
				pageShowColumns.add(column);
			}
			if(column.isPrimaryKey()){
				table.setPrimaryKeyColumn(column);
			}
			if(column.isSort()){
				table.setSortColumn(column);
			}
			if(column.isQuery()){
				queryColumns.add(column);
			}
			if(column.isEdit()){
				editColumns.add(column);
			}else{
				hiddenColumns.add(column);
			}
			columns.add(column);
		}
		table.setColumns(columns);
		table.setPageShowColumns(pageShowColumns);
		table.setQueryColumns(queryColumns);
		table.setEditColumns(editColumns);
		table.setHiddenColumns(hiddenColumns);
		return table;
	}

	/**
	 * 
	 * @param attributes
	 * @return
	 */
	private static Column initColumn(NamedNodeMap attributes) {
		Node node= null;
		Column column = new Column();
		String codeStr = attributes.getNamedItem("column").getNodeValue();
		column.setCode(codeStr);
		 
		column.setType(new Type(attributes.getNamedItem("jdbcType")
				.getNodeValue()));
		//是否不能为空
		try {
			node = attributes.getNamedItem("notNull");
			column.setCheckNull(node == null ? true:Boolean.parseBoolean(node.getNodeValue()));
		} catch (Exception e) {
		}
		node = attributes.getNamedItem("comment");
		column.setComment(node.getNodeValue());
		try{
			node = attributes.getNamedItem("name");
			column.setName(node == null ? codeStr : node.getNodeValue());
			column.setEntityName(node == null ? codeStr.substring(0, 1).toUpperCase()
					+ codeStr.substring(1):node.getNodeValue().substring(0, 1).toUpperCase()
					+ node.getNodeValue().substring(1));
		}catch(Exception e){
			
		}
		//最大长度
		try {
			node = attributes.getNamedItem("maxLength");
			column.setMaxLength(node == null ? 0 : Integer.parseInt(node
					.getNodeValue()));
		} catch (Exception e) {
		}
		//输入类型
		try {
			node = attributes.getNamedItem("inputType");
			column.setInputType(node == null ? 1 : Integer.parseInt(node
					.getNodeValue()));
		} catch (Exception e) {
		}
		//是否作为方法参数
		try {
			node = attributes.getNamedItem("isMethodParam");
			column.setMethodParam(node == null ? false:Boolean.parseBoolean(node.getNodeValue()));
		} catch (Exception e) {
		}
		//是否作为方法参数
		try {
			node = attributes.getNamedItem("isSearchName");
			column.setSearchName(node == null ? false:Boolean.parseBoolean(node.getNodeValue()));
		} catch (Exception e) {
		
		}
		//是否排序
		try {
			node = attributes.getNamedItem("isSort");
			column.setSort(node == null ? false:Boolean.parseBoolean(node.getNodeValue()));
		} catch (Exception e) {
		}
		//是否在页面显示
		try {
			node = attributes.getNamedItem("isPageShow");
			column.setPageShow(node == null ? true:Boolean.parseBoolean(node.getNodeValue()));
		} catch (Exception e) {
		}
		//是否为主键
		try {
			node = attributes.getNamedItem("isPrimaryKey");
            column.setPrimaryKey(node == null ? false:Boolean.parseBoolean(node.getNodeValue()));
		} catch (Exception e) {
		}
		//是否作为查询条件
		try {
			node = attributes.getNamedItem("isQuery");
			column.setQuery(node == null ? false:Boolean.parseBoolean(node.getNodeValue()));
		} catch (Exception e) {
		}
		//是否需要在添加和修改面板中展示
		try{
			node = attributes.getNamedItem("isEdit");
			column.setEdit(node == null ? true:Boolean.parseBoolean(node.getNodeValue()));
		} catch(Exception e){
		}
		node = attributes.getNamedItem("selectName");
		column.setSelectName(node == null ? "Constant.UNIVERSAL_STATUS":node.getNodeValue());
		return column;
	}
}
