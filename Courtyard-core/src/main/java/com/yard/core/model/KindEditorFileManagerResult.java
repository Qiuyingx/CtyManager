package com.yard.core.model;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;

/**
 * KindEditor文件管理结果对象
 * 
 *
 */
@SuppressWarnings("rawtypes")
public class KindEditorFileManagerResult implements Serializable {
	private static final long serialVersionUID = 3136168298554523499L;
	private String moveup_dir_path;
	private String current_dir_path;
	private String current_url;
	private int total_count;
	
	private List<Hashtable> file_list;

	public KindEditorFileManagerResult(String moveup_dir_path, String current_dir_path, String current_url, int total_count,
			List<Hashtable> file_list) {
		super();
		this.moveup_dir_path = moveup_dir_path;
		this.current_dir_path = current_dir_path;
		this.current_url = current_url;
		this.total_count = total_count;
		this.file_list = file_list;
	}

	public String getMoveup_dir_path() {
		return moveup_dir_path;
	}

	public void setMoveup_dir_path(String moveup_dir_path) {
		this.moveup_dir_path = moveup_dir_path;
	}

	public String getCurrent_dir_path() {
		return current_dir_path;
	}

	public void setCurrent_dir_path(String current_dir_path) {
		this.current_dir_path = current_dir_path;
	}

	public String getCurrent_url() {
		return current_url;
	}

	public void setCurrent_url(String current_url) {
		this.current_url = current_url;
	}

	public int getTotal_count() {
		return total_count;
	}

	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}

	public List<Hashtable> getFile_list() {
		return file_list;
	}

	public void setFile_list(List<Hashtable> file_list) {
		this.file_list = file_list;
	}

}
