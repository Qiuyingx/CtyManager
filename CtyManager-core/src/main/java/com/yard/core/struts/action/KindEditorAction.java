package com.yard.core.struts.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import com.yard.core.model.KindEditorFileManagerResult;
import com.yard.core.model.KindEditorUploadResult;
import com.yard.core.util.Constant;

@SuppressWarnings({"unchecked", "rawtypes"})
public class KindEditorAction extends JsonAction {
	private static final long serialVersionUID = -7674492537218956709L;
	private File imgFile; // 上传的文件
	private String imgFileFileName; // 文件名称
	private String imgFileContentType; // 文件类型
	private String dir;

	@Action("/upload")
	public String upload() {
		HttpServletRequest requert = ServletActionContext.getRequest();
		try {
			requert.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		// 文件保存目录路径
		String savePath = Constant.SAVE_PATH;
		// String savePath =
		// ServletActionContext.getServletContext().getRealPath("/") +
		// "/../weihotel-app/attached/";
		// D:\software\apache-tomcat-8.0.9\wtpwebapps\weihotel-manager/attached/
		// 文件保存目录URL
		// String saveUrl = ServletActionContext.getRequest().getContextPath() +
		// "/attached/";
		String saveUrl = Constant.SAVE_URL;
		// String saveUrl = "/weihotel-app/attached/";
		// /weihotel-manager/attached/
		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

		// 最大文件大小
		// long maxSize = 1000000;

		// response.setContentType("text/html; charset=UTF-8");

		if (imgFile == null) {
			return error("请选择文件。");
		}
		// 检查目录
		File uploadDir = new File(savePath);
		if (!uploadDir.isDirectory()) {
			return error("上传目录不存在。");
		}
		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			return error("上传目录没有写权限。");
		}

		String dirName = dir;
		if (dirName == null) {
			dirName = "image";
		}
		if (!extMap.containsKey(dirName)) {
			return error("文件类型不支持。");
		}
		// 创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		// if (imgFile != null) {
		// // File savedir = new File(realpath);
		// // if (!savedir.getParentFile().exists())
		// // savedir.getParentFile().mkdirs();
		// // for (int i = 0; i < image; i++) {
		// // File savefile = new File(savedir, imageFileName[i]);
		// // FileUtils.copyFile(image[i], savefile);
		// // }
		// ActionContext.getContext().put("message", "文件上传成功");
		// }

		String fileName = imgFileFileName;
		// long fileSize = imgFile.getTotalSpace();
		// 检查文件大小
		// if (fileSize > maxSize) {
		// return error("上传文件大小超过限制。");
		// }
		// 检查扩展名
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {
			return error("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
		try {
			File uploadedFile = new File(savePath, newFileName);
			// item.write(uploadedFile);
			FileUtils.copyFile(imgFile, uploadedFile);
		} catch (Exception e) {
			return error("上传文件失败。");
		}

		// obj.addProperty("error", 0);
		// obj.addProperty("url", saveUrl + newFileName);

		object = new KindEditorUploadResult(false, saveUrl + newFileName);
		return OBJECT;
	}

	@Action("/fileManager")
	public String fileManager() {
		HttpServletRequest requert = ServletActionContext.getRequest();
		// 文件保存目录路径
		String rootPath = ServletActionContext.getServletContext().getRealPath("/") + "/../weihotel-app/attached/";
		// D:\software\apache-tomcat-8.0.9\wtpwebapps\weihotel-manager/attached/
		// 文件保存目录URL
		// String saveUrl = ServletActionContext.getRequest().getContextPath() +
		// "/attached/";
		String rootUrl = "/weihotel-app/attached/";
		// 图片扩展名
		String[] fileTypes = new String[] { "gif", "jpg", "jpeg", "png", "bmp" };

		String dirName = dir;
		if (dirName != null) {
			if (!Arrays.<String> asList(new String[] { "image", "flash", "media", "file" }).contains(dirName)) {
				return error("无效目录名。");
			}
			rootPath += dirName + "/";
			rootUrl += dirName + "/";
			File saveDirFile = new File(rootPath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
		}
		// 根据path参数，设置各路径和URL
		String path = requert.getParameter("path") != null ? requert.getParameter("path") : "";
		String currentPath = rootPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = currentDirPath.substring(0, currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}

		// 排序形式，name or size or type
		String order = requert.getParameter("order") != null ? requert.getParameter("order").toLowerCase() : "name";

		// 不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0) {
			return error("不允许访问。");
		}
		// 最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {
			return error("参数无效。");
		}
		// 目录不存在或不是目录
		File currentPathFile = new File(currentPath);
		if (!currentPathFile.isDirectory()) {
			return error("目录不存在。");
		}

		// 遍历目录取的文件信息
		List<Hashtable> fileList = new ArrayList<Hashtable>();
		if (currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if (file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if (file.isFile()) {
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String> asList(fileTypes).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}

		if ("size".equals(order)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		object = new KindEditorFileManagerResult(moveupDirPath, currentDirPath, currentUrl, fileList.size(), fileList);
		return OBJECT;
	}

	public String error(String message) {
		object = new KindEditorUploadResult(true, message);
		return OBJECT;
	}

	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public String getImgFileFileName() {
		return imgFileFileName;
	}

	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	public String getImgFileContentType() {
		return imgFileContentType;
	}

	public void setImgFileContentType(String imgFileContentType) {
		this.imgFileContentType = imgFileContentType;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public class NameComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable) a;
			Hashtable hashB = (Hashtable) b;
			if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String) hashA.get("filename")).compareTo((String) hashB.get("filename"));
			}
		}
	}

	public class SizeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable) a;
			Hashtable hashB = (Hashtable) b;
			if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
				return 1;
			} else {
				if (((Long) hashA.get("filesize")) > ((Long) hashB.get("filesize"))) {
					return 1;
				} else if (((Long) hashA.get("filesize")) < ((Long) hashB.get("filesize"))) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}

	public class TypeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable) a;
			Hashtable hashB = (Hashtable) b;
			if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String) hashA.get("filetype")).compareTo((String) hashB.get("filetype"));
			}
		}
	}
}
