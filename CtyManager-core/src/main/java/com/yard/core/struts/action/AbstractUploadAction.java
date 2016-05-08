package com.yard.core.struts.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;

import com.yard.core.model.ExtMap;
import com.yard.core.model.UploadFile;
import com.opensymphony.xwork2.ActionSupport;

//@Namespace("/upload")
//@Action("{path}")
@InterceptorRefs({
		@InterceptorRef(value = "fileUpload", params = { "maximumSize",
				"104857600" }), @InterceptorRef(value = "defaultStack") })
// "maximumSize", "20971520" , "allowedTypes", "application/x-zip-compressed"}
// @Results({ @Result(name = "TEST", type = "plainText", params = { "charSet",
// "UTF-8", "location", "/upload.html" }) })
public abstract class AbstractUploadAction extends ActionSupport {
	private static final long serialVersionUID = 363109242895052135L;
	private static final int BUFFER_SIZE = 16 * 1024;
	// 上传文件保存的根路径
	private static String fileRoot = Thread.currentThread()
			.getContextClassLoader().getResource("/").getPath()
			+ "../../../";
	// 定义允许上传的文件扩展名
	private ExtMap extMap = new ExtMap();

	/**
	 * 返回上传的文件名
	 * 
	 * @return
	 */
	public abstract String createFileName(String fileName);

	/**
	 * 保存文件上传记录
	 * 
	 * @param filename
	 * @return
	 */
	public abstract boolean saveRecord(UploadFile uploadFile);

	public abstract void printOut(StringBuffer sb, UploadFile uploadFile);

	public abstract void initExt(ExtMap extMap);

	/**
	 * 获取文件名
	 * 
	 * @return
	 */
	public abstract String getFileName();

	/**
	 * 获取文件类型
	 * 
	 * @returnwen
	 */
	public abstract String getContentType();

	/**
	 * 获取上传文件
	 * 
	 * @return
	 */
	public abstract File getFile();

	public abstract String getUrlRoot();

	public abstract String getType();

	/**
	 * 保存文件
	 * 
	 * @return
	 */
	public boolean saveFile(UploadFile uploadFile) {
		try {
			FileInputStream fin = new FileInputStream(getFile());
			File directoryFilePath = new File(uploadFile.getSaveRootPath()
					+ uploadFile.getUrlPath());
			if (!directoryFilePath.exists()) {
				directoryFilePath.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(directoryFilePath + "/"
					+ uploadFile.getSaveName());
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = fin.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			if (null != fos)
				fos.close();
			if (null != fin)
				fin.close();
			return saveRecord(uploadFile);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void printJson(UploadFile uploadResult) throws IOException {
		StringBuffer sb = new StringBuffer();
		printOut(sb, uploadResult);
		ServletActionContext.getResponse().setContentType(
				"text/html;charset=utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		out.print(sb.toString());
		out.flush();
		out.close();
	}

	@Override
	public String execute() throws Exception {
		// System.out.println("FileName:" + getFileName());
		// System.out.println("ContentType:" + getContentType());
		// System.out.println("file:" + getFile());
		UploadFile uploadFile = new UploadFile();
		uploadFile.setState(false);
		uploadFile.setFileName(getFileName());
		String saveName = createFileName(getFileName());
		if (!getExtName().equalsIgnoreCase("")) {
			saveName = saveName + "." + getExtName();
		}
		uploadFile.setSaveName(saveName);
		uploadFile.setSaveRootPath(fileRoot);
		uploadFile.setUrlPath(getUrlRoot() + "/" + getType());
		initExt(extMap);
		if (validateExt()) {
			saveFile(uploadFile);
			uploadFile.setState(true);
			uploadFile.setMsg("文件上传成功");
		} else {
			uploadFile.setMsg("不允许上传该类型文件!");
		}
		printJson(uploadFile);
		return null;
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @return
	 */
	private String getExtName() {
		String extName = "";
		int extIndex = getFileName().lastIndexOf(".") + 1;
		if (extIndex > 0) {
			extName = getFileName().substring(extIndex);
		}
		return extName;
	}

	/**
	 * 检验扩展名
	 * 
	 * @param filename
	 * @param ext
	 * @return
	 */
	private boolean validateExt() {
		String extName = getExtName();
		if (getType() == null || getType().equalsIgnoreCase("")
				|| extMap.keySet().size()==0)
			return true;
		else
			return extMap.containsKey(getType(), extName);
	}

}
