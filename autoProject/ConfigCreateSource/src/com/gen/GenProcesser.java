package com.gen;

import hidden.org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.texen.util.FileUtil;

import com.gen.entity.Table;
import com.gen.util.StringUtil;

public class GenProcesser {

	private List<Table> tables;
	private VelocityEngine velocity = new VelocityEngine();

	public void init() throws Exception {
		Properties properties = new Properties();
		String basePath = String.format("src/%s", Constant.TEMPLATE_DIR);// 这里需要这样写路径！！！
		// 设置模板的路径
		properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, basePath);
		// 初始化velocity 让设置的路径生效
		velocity.init(properties);
		initTables();
	}
	public void destroy() {
		velocity = null;
	}

	private void initTables() throws Exception {
		tables = XmlAnalysis.analysis();
	}

	public void startGen() throws Exception {
		clearFileDir();
		for (Table table : tables) {
			genFile(table);
		}
		//genXml();
	}

	/**
	 * 每次生成前清理目录
	 */
	private void clearFileDir() {
		String dirPath = System.getProperty("user.dir") + Constant.GEN_FILE_DIR;
		try {
			FileUtils.cleanDirectory(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void genFile(Table table) throws Exception {
		if (table == null) {
			return;
		}
		String[] templates = getTemplates();
		
		for (String templateName : templates) {
			Template template = velocity.getTemplate(templateName,
					Constant.ENCODEING);
			VelocityContext context = new VelocityContext();
			context.put("table", table);
			context.put("varName", table.getVarName());
			context.put("entityName",table.getEntityName());
			context.put("package", table.getPackageName());
			context.put("mark", table.getPackageMark());
			context.put("comment",table.getComment());
			context.put("VersionUID", StringUtil.randStr(0, 16));
			context.put("include", "#include");
			context.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date()));
			// 输出路径
			boolean le = false;
			String filePath = getFilePath(templateName, table);
			if (filePath == null) {
				continue;
			}
			if(filePath.endsWith(".txt") ){
				le = true;
			}
			System.out.println(filePath);
			File outFile = new File(filePath);
			if (!outFile.exists()) {
				FileUtil.mkdir(outFile.getParent());
				outFile.createNewFile();
			}
			FileWriter writer = new FileWriter(outFile, le);
			if (template != null) {
				template.merge(context, writer);
			}
			writer.flush();
			writer.close();
		}
	}

	/**
	 * 根据模板名称获取应该生成的文件路径与名称
	 * 
	 * @param templateName
	 * @param table
	 * @return
	 */
	private String getFilePath(String templateName, Table table) {
		// 文件类型
		int index = templateName.lastIndexOf("_");
		if (index == -1) {
			if (!templateName.startsWith("xml")) {
				System.err.println(String.format("模板:%s 缺少类型，跳过生成!",
						templateName));
			}
			return null;
		}
		String fileExt = templateName.substring(index + 1);
		// 结果
		StringBuilder sb = new StringBuilder();
		sb.append(System.getProperty("user.dir"));
		sb.append(Constant.GEN_FILE_DIR);
		templateName = templateName.substring(0, index);
		templateName = templateName.replaceAll("\\$\\{package\\}",
				table.getPackageName());
		templateName = templateName.replaceAll("\\$\\{mark\\}",
				table.getPackageMark());
		templateName = templateName.replaceAll("\\$\\{entityName\\}",
				table.getEntityName());
		templateName = templateName.replaceAll("\\$\\{varName\\}",
				table.getVarName());
		templateName = templateName.replaceAll("\\.", "\\\\");
		templateName = templateName.replaceAll("\\_", "\\\\");
		if ("java".equals(fileExt)) {
			if(templateName.startsWith("action")){
				templateName = templateName.replaceFirst("action",
						"src\\\\");
			}else if(templateName.startsWith("service")){
				templateName = templateName.replaceFirst("service",
						"src\\\\");
			}else if(templateName.startsWith("entity")){
				templateName = templateName.replaceFirst("entity",
						"src\\\\");
			}
		}
		sb.append("\\");
		sb.append(templateName);
		sb.append(".");
		sb.append(fileExt);
		String filePath = sb.toString();
		
		return filePath;
	}

	private String[] getTemplates() {
		String dirPath = System.getProperty("user.dir")
				+ String.format("\\bin\\%s", Constant.TEMPLATE_DIR);
		File dir = new File(dirPath);
		return dir.list(null);
	}

	/**
	 * 生成配置文件
	 * 
	 * @throws IOException
	 */
	private void genXml() throws IOException {
		for (int i = 0; i < tables.size(); i++) {
			boolean isAppend = (i == 0 ? false : true);
			Table table = tables.get(i);
			makeXmlFile(table, "xmlhbm", ".hbm.xml", isAppend);
		}
	}

	private void makeXmlFile(Table table, String templateName,
			String outputFile, boolean isAppend) throws IOException {
		Template template = velocity.getTemplate(templateName,
				Constant.ENCODEING);
		VelocityContext context = new VelocityContext();
		context.put("table", table);
		context.put("package", table.getPackageName());
		outputFile = System.getProperty("user.dir") + "\\genfile\\src\\com\\jeecms\\cms\\entity\\main\\hbm\\"
				+"Cms" +table.getEntityName()+outputFile;
		// 输出路径
		File outFile = new File(outputFile);
		if (!outFile.exists()) {
			FileUtil.mkdir(outFile.getParent());
			outFile.createNewFile();
		}
		FileWriter writer = new FileWriter(outFile, isAppend);
		if (template != null) {
			template.merge(context, writer);
		}
		writer.flush();
		writer.close();
	}
}
