package com.yard.core.struts.action;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 通用文件下载器(本地文件)
 * 
 * @author Administrator
 * 
 */
@Results({ @Result(name = "DOWNLOAD", type = "stream", params = {
		"contentType", "application/octet-stream", "inputName", "inputStream",
		"contentDisposition", "attachment;filename=${fileName}", "bufferSize",
		"4096" }) })
@ExceptionMappings({ @ExceptionMapping(exception = "java.lang.Exception", result = "success", params = {
		"param1", "val1" }) })
public abstract class AbstractDownloadAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InputStream inputStream;
	public String fileName = "";
	public String saveName = "";
	// 上传文件保存的根路径
	private static String fileRoot = Thread.currentThread()
			.getContextClassLoader().getResource("/").getPath()
			+ "../../../";

	public abstract String getUrlRoot();

	public InputStream getInputStream() throws Exception {
		proc();
		String file = fileRoot + "/" + getUrlRoot() + "/" + saveName;
		return new FileInputStream(file);// 从系统磁盘文件读取数据
	}

	public abstract void proc();

	public String execute() throws Exception {
		
		return "DOWNLOAD";

	}

}
