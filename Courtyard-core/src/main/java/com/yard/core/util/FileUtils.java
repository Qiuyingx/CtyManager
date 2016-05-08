package com.yard.core.util;
//package org.hbb.weihotel.util;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.struts2.ServletActionContext;
//
//
//public class FileUtils {
//	protected static String fileRoot = ServletActionContext.getServletContext()
//			.getRealPath("/");
//	static {
//		try {
//			fileRoot = fileRoot.substring(0,
//					fileRoot.lastIndexOf("\\", fileRoot.length() - 2))
//					.replaceAll("\\\\", "/");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	private static final int BUFFER_SIZE = 16 * 1024;
//
//	public static void printOut(StringBuffer sb, UploadFile uploadFile) {
//		sb.append("{");
//		if (uploadFile.isState()) {
//			sb.append("\"state\":true,\"url\":\"" + uploadFile.getUrlPath()
//					+ "/" + uploadFile.getSaveName() + "\"");
//		} else {
//			sb.append("\"state\":false,\"message\":\"" + uploadFile.getMsg()
//					+ "\"");
//		}
//		sb.append("}");
//	}
//
//	/**
//	 * 文件上传保存（Linux下不兼容）
//	 * 
//	 * @param file
//	 * @param ContentType
//	 * @param fileName
//	 * @param urlRoot
//	 *            要存放的文件夹
//	 * @param extMap
//	 *            允许保存文件的后缀
//	 * @return 保存文件的详细信息
//	 */
//	public static UploadFile saveFile(File file, String ContentType,
//			String fileName, String urlRoot, ExtMap extMap) {
//		urlRoot = "/res-manager/" + urlRoot;
//		File directoryFilePath = new File(fileRoot + urlRoot);
//		if (!directoryFilePath.exists()) {
//			directoryFilePath.mkdirs();
//		}
//		UploadFile uploadFile = new UploadFile();
//		uploadFile.setFileName(fileName);
//		uploadFile.setUrlPath(urlRoot);
//		uploadFile.setSaveRootPath(fileRoot);
//		if (!validateExt(fileName, ContentType, extMap)) {
//			uploadFile.setState(false);
//			uploadFile.setMsg("不允许上传该文件类型");
//		} else {
//			try {
//				uploadFile.setSaveName(PkGenerator.GenUUID("") + "."
//						+ getExtName(fileName));
//				org.apache.commons.io.FileUtils.copyFile(file, new File(
//						directoryFilePath + "/" + uploadFile.getSaveName()));
//				uploadFile.setState(true);
//				uploadFile.setMsg("文件上传成功");
//			} catch (IOException e) {
//				uploadFile.setState(false);
//				uploadFile.setMsg("文件上传失败");
//			}
//		}
//		return uploadFile;
//	}
//
//	/**
//	 * 文件上传保存
//	 * 
//	 * @param file
//	 * @param ContentType
//	 * @param fileName
//	 * @param urlRoot
//	 *            要存放的文件夹
//	 * @param extMap
//	 *            允许保存文件的后缀
//	 * @return 保存文件的详细信息
//	 * @throws FileNotFoundException
//	 */
//	public static UploadFile save(File file, String ContentType,
//			String fileName, String urlRoot, ExtMap extMap) {
//		UploadFile uploadFile = new UploadFile();
//		;
//		try {
//			urlRoot = "/credit-mall-res/" + urlRoot;
//			FileInputStream fin = new FileInputStream(file);
//			File directoryFilePath = new File(fileRoot + urlRoot);
//			if (!directoryFilePath.exists()) {
//				directoryFilePath.mkdirs();
//			}
//			uploadFile.setFileName(fileName);
//			uploadFile.setUrlPath(urlRoot);
//			uploadFile.setSaveRootPath(fileRoot);
//			if (!validateExt(fileName, ContentType, extMap)) {
//				uploadFile.setState(false);
//				uploadFile.setMsg("不允许上传该文件类型");
//			} else {
//				try {
//					uploadFile.setSaveName(PkGenerator.GenUUID("")
//							+ "." + getExtName(fileName));
//					FileOutputStream fos = new FileOutputStream(
//							directoryFilePath + "/" + uploadFile.getSaveName());
//					byte[] buffer = new byte[BUFFER_SIZE];
//					int len = 0;
//					while ((len = fin.read(buffer)) > 0) {
//						fos.write(buffer, 0, len);
//					}
//					if (null != fos)
//						fos.close();
//					if (null != fin)
//						fin.close();
//					uploadFile.setState(true);
//					uploadFile.setMsg("文件上传成功");
//				} catch (IOException e) {
//					uploadFile.setState(false);
//					uploadFile.setMsg("文件上传失败");
//				}
//			}
//		} catch (FileNotFoundException e) {
//			uploadFile.setState(false);
//			uploadFile.setMsg("文件上传失败");
//			e.printStackTrace();
//		}
//		return uploadFile;
//	}
//
//	/**
//	 * 文件上传保存
//	 * 
//	 * @param files
//	 * @param ContentTypes
//	 * @param fileNames
//	 * @param urlRoot
//	 *            要存放的文件夹
//	 * @param extMap
//	 *            允许保存文件的后缀
//	 * @return 保存文件的详细信息
//	 */
//	public static List<UploadFile> saveFile(File[] files,
//			String[] ContentTypes, String[] fileNames, String urlRoot,
//			ExtMap extMap) {
//		List<UploadFile> list = new ArrayList<UploadFile>();
//		for (int i = 0; i < files.length; i++) {
//			list.add(saveFile(files[i], ContentTypes[i], fileNames[i], urlRoot,
//					extMap));
//		}
//		for (UploadFile uf : list) {
//			if (!uf.isState()) {
//				for (UploadFile uploadFile : list) {
//					if (uploadFile.isState()
//							&& null != uploadFile.getSaveName()) {
//						StringBuffer sb = new StringBuffer(
//								uploadFile.getUrlPath());
//						sb.append("/");
//						sb.append(uploadFile.getSaveName());
//						removeFile(sb.toString());
//					}
//				}
//				return null;
//			}
//		}
//		return list;
//	}
//
//	public static boolean removeFile(String filePathName) {
//		File file = new File(fileRoot + "/" + filePathName);
//		if (file.exists()) {
//			file.delete();
//			return true;
//		}
//		return false;
//	}
//
//	/**
//	 * 多个删除
//	 * 
//	 * @param uploadFiles
//	 */
//	public static void removeFile(List<UploadFile> uploadFiles) {
//		for (UploadFile uploadFile : uploadFiles) {
//			removeFile(uploadFile);
//		}
//	}
//
//	public static void removeFile(UploadFile uploadFile) {
//		if (!uploadFile.isState()) {
//			StringBuffer sb = new StringBuffer(uploadFile.getUrlPath());
//			sb.append("/");
//			sb.append(uploadFile.getSaveName());
//			removeFile(sb.toString());
//		}
//	}
//
//	/**
//	 * 取得文件大小
//	 * 
//	 * @param f
//	 * @return
//	 * @throws Exception
//	 */
//	public static long getFileSize(File f) throws Exception// 取得文件夹大小
//	{
//		long s = 0;
//		if (f.exists()) {
//			FileInputStream fis = new FileInputStream(f);
//			s = fis.available();
//		} else {
//			f.createNewFile();
//		}
//		return s;
//
//	}
//
//	/**
//	 * 
//	 * @param f
//	 *            文件
//	 * @param filesize
//	 *            限定文件的大小
//	 * @return false 上传的文件过大 true 可以上传
//	 * @throws Exception
//	 */
//	public static boolean limitSize(File f, long filesize) throws Exception {
//		long size = getFileSize(f);
//		if (size > filesize) {
//			return false;
//		}
//		return true;
//	}
//
//	/**
//	 * 多个文件的总大小
//	 * 
//	 * @param f
//	 * @return
//	 * @throws Exception
//	 */
//	public static long getFileSizes(File[] f) throws Exception {
//		long size = 0;
//		for (File file : f) {
//			size += getFileSize(file);
//		}
//		return size;
//	}
//
//	private static String getExtName(String fileName) {
//		String extName = "";
//		int extIndex = fileName.lastIndexOf(".") + 1;
//		if (extIndex > 0) {
//			extName = fileName.substring(extIndex);
//		}
//		return extName;
//	}
//
//	private static String getType(String contentType) {
//		String s = contentType.substring(0, contentType.indexOf("/"));
//		if (s.indexOf("application") >= 0)
//			s = "application";
//		else if (s.indexOf("image") >= 0)
//			s = "image";
//		else if (s.indexOf("text") >= 0)
//			s = "text";
//
//		return s;
//	}
//
//	private static boolean validateExt(String fileName, String contentType,
//			ExtMap extMap) {
//		if (extMap == null) {
//			return true;
//		} else {
//			String extName = getExtName(fileName);
//			if (getType(contentType) == null
//					|| getType(contentType).equalsIgnoreCase("")
//					|| extMap.keySet().size() == 0)
//				return true;
//			else
//				return extMap.containsKey(getType(contentType), extName);
//		}
//	}
//}
