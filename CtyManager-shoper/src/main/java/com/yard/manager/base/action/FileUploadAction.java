package com.yard.manager.base.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.constant.UploadFilePath;
import com.yard.manager.base.util.JsonResult;
import com.yard.core.util.DateUtil;
import com.opensymphony.xwork2.ActionContext;

@Results({ @Result(type = "json", name = "upload", params = {"contentType", "text/html", "root", "map"}) })
public class FileUploadAction extends BaseAction {
	private static final long serialVersionUID = -2656947961953357874L;
	private static final String ACTIONNAME_CARBRAND = "carbrand";
	private File file;
	private String fileFileName;

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	@Actions( { @Action("/content/uploadfile"), @Action("/uploadfile/" + ACTIONNAME_CARBRAND) })  
	public String saveFile() {
		String actionName = ActionContext.getContext().getName();
		
		// 文件保存路径
		String savePath = "";
		String fileUrl = "";
		int fileLen = 0;
		
		if (actionName.equals(ACTIONNAME_CARBRAND)) {
			savePath = UploadFilePath.CARBRAND_IMG_PATH;
			fileUrl = UploadFilePath.CARBRAND_IMG_URL;
			fileLen = UploadFilePath.CARBRAND_IMG_LEN;
		} else {
			savePath = UploadFilePath.MAIN_IMG_PATH + DateUtil.formatNoLineDate(new Date()) + "/";
			fileUrl = UploadFilePath.MAIN_IMG_URL + DateUtil.formatNoLineDate(new Date()) + "/";
			fileLen = UploadFilePath.MAIN_IMG_LEN;
		}
		
		File tmp = new File(savePath); // 判断文件夹是否存在,如果不存在则创建文件夹
		if (!tmp.exists()) {
			tmp.mkdir();
		}
		String[] fileSuffix = { ".jpeg", ".jpg", ".png", ".bmp", "gif" };// 文件类型限制
		try {
			// 验证文件名是否为这几种文件
			boolean isImgFile = false;
			for (String suffix : fileSuffix) {
				if (fileFileName.toLowerCase().endsWith(suffix)) {
					isImgFile = true;
					break;
				}
			}

			if (!isImgFile) {
				JsonResult.toJson(map, false, "上传的图片类型只能为jpeg,jpg,png,bmp,gif这几种类型");
				return MAP;
			}

			// 判断文件大小是否超过指定尺寸
			if (formatFileSize(file) > fileLen) {
				JsonResult.toJson(map, false, "图片大小必须在" + fileLen + "K以内");
				return MAP;
			}

			// 通过临时文件路径得到新的文件名
			String fileName = file.getPath().split("upload_")[1].split("\\.")[0];
			String fileNameInfo[] = fileFileName.split("\\.");
			if (fileNameInfo.length >= 2) {
				fileName += "." + fileNameInfo[fileNameInfo.length - 1]; // 加上原本文件的后辍名
			} else {
				fileName += ".jpg"; // 指定一个后辍名
			}

			// 将临时文件流保存到指定目录
			FileInputStream inputStream = new FileInputStream(file);
			FileOutputStream outputStream = new FileOutputStream(savePath + fileName);

			byte[] buf = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, length);
			}

			inputStream.close();
			outputStream.flush();
			outputStream.close();

			JsonResult.toJson(map, true, "");
			map.put("fileUrl", fileUrl + fileName);
			map.put("fileName", fileName);
			map.put("srcFileName", fileFileName);
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.toJson(map, false, "服务器繁忙，请稍候再试！");
		}

		return "upload";
	}

	/**
	 * 文件大小格式化(单位:kb)
	 * 
	 * @param file
	 * @return
	 */
	private long formatFileSize(File file) {
		return file.length() / 1024;
	}
}
