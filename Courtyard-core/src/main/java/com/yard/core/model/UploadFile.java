package com.yard.core.model;

/**
 * 上传文件信息
 * 
 * @author jacky
 * 
 */
public class UploadFile {
	// 上传状态
	private boolean state;
	private String msg;
	// 文件名 如:test.txt
	private String fileName;
	// 文件保存根路径 如:c:/sdgsgsg/sdgsgddsgs
	private String saveRootPath;
	// 文件保存名 如:afaf-afsaf-asfaf.txt
	private String saveName;
	// 文件路径
	private String urlPath;

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSaveRootPath() {
		return saveRootPath;
	}

	public void setSaveRootPath(String saveRootPath) {
		this.saveRootPath = saveRootPath;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	// //////////////////////////////////////////////

}
