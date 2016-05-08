package com.yard.manager.platform.excel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;

import com.yard.manager.platform.excel.body.ExcelFactory;



public class ExcelProcess {

	/**
	 * 服务器路径
	  * @param sheetTitle 单元表格标题
	  * @param titleName 内容标题
	  * @param bodyValue 内容
	  * @param request
	  * @param files 文件（自定义文件路径《文件夹名称》）
	  * @param fileName 文件名称
	 * @param title
	 */
	 public static String start(String sheetTitle,String[] titleName,String[][] bodyValue,String realPath,String files,String fileName, List<Region> regions){
		 return build(sheetTitle, titleName, bodyValue, realPath, files, fileName, regions);
	 }
	 
	/**
	 * 服务器路径
	  * @param sheetTitle 单元表格标题
	  * @param titleName 内容标题
	  * @param bodyValue 内容
	  * @param request
	  * @param files 文件（自定义文件路径《文件夹名称》）
	  * @param fileName 文件名称
	 * @param title
	 */
	 public static String start(String sheetTitle,String[] titleName,String[][] bodyValue,String realPath,String files,String fileName){
		 return build(sheetTitle, titleName, bodyValue, realPath, files, fileName, null);
	 }
		 
	 /**
	  * 自定义路径
	  * @param sheetTitle 单元表格标题
	  * @param titleName 内容标题
	  * @param bodyValue 内容
	  * @param request
	  * @param files 文件（自定义文件路径《文件夹名称》）
	  * @param fileName 文件名称
	  */
	 public static String start(String sheetTitle,String[] titleName,String[][] bodyValue,HttpServletRequest request,String files,String fileName, List<Region> regions){
		 return build(sheetTitle, titleName, bodyValue, request, files, fileName, regions);
	 }
	 
	 /**
	  * 自定义路径
	  * @param sheetTitle 单元表格标题
	  * @param titleName 内容标题
	  * @param bodyValue 内容
	  * @param request
	  * @param files 文件（自定义文件路径《文件夹名称》）
	  * @param fileName 文件名称
	  */
	 public static String start(String sheetTitle,String[] titleName,String[][] bodyValue,HttpServletRequest request,String files,String fileName){
		 return build(sheetTitle, titleName, bodyValue, request, files, fileName, null);
	 }
	 
	 private static String build(String sheetTitle,String[] titleName,String[][] bodyValue,String realPath,String files,String fileName, List<Region> regions){
		 Object[] wb = com(sheetTitle, titleName, bodyValue, regions);
		 return ExcelFactory.explor((HSSFWorkbook)wb[0],realPath, files, fileName);
	 }
	 private static String build(String sheetTitle,String[] titleName,String[][] bodyValue,HttpServletRequest request,String files,String fileName, List<Region> regions){
		 Object[] wb = com(sheetTitle, titleName, bodyValue, regions);
		 return ExcelFactory.explor(request,(HSSFWorkbook)wb[0], files, fileName);
	 }
	 private static Object[]  com(String sheetTitle,String[] titleName,String[][] bodyValue, List<Region> regions){
		 Object[] wb = ExcelFactory.getTitle(sheetTitle,titleName);
		 region(wb, regions);
		 ExcelFactory.getBody((HSSFWorkbook)wb[0], (HSSFSheet)wb[1], bodyValue);
		 return wb;
	 }
	 
	 /**
	  * 合并单元格
	  * @param web
	  */
	 private static void region(Object[] web, List<Region> regions) {
		 if(regions != null && !regions.isEmpty()) {
			 HSSFSheet sheet = (HSSFSheet)web[1];
			 for(Region region : regions){
				 sheet.addMergedRegion(region);
			 }
		 }
	 }
	 
}
