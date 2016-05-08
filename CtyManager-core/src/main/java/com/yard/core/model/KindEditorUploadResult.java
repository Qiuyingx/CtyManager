package com.yard.core.model;

import java.io.Serializable;

/**
 * KindEditor上传返回结果
 * 
 * @author VICTOR
 *
 */
public class KindEditorUploadResult implements Serializable {
	private static final long serialVersionUID = 3136168298554523499L;
	private int error;
	private String url;
	private String message;

	public KindEditorUploadResult(boolean error, String p) {
		super();
		if (error) {
			this.error = 1;
			this.url = "";
			this.message = p;
		} else {
			this.error = 0;
			this.url = p;
			this.message = "OK";
		}

	}

	public KindEditorUploadResult(int error, String url, String message) {
		super();
		this.error = error;
		this.url = url;
		this.message = message;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
