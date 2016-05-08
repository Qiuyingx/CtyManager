package com.yard.manager.platform.excel.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * excel 样式集
 * @author xiayuanming
 *
 */
public class ExcelFontUtil {

	/**
	 * 布局：水平垂直居中；
	 * 字体：微软雅黑   11号字体 粗体
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle fontForTitle(HSSFWorkbook wb){
		HSSFCellStyle style = wb.createCellStyle();
		
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFFont font = wb.createFont();
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short)11);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		style.setFont(font);
		
		return style;
	}
	
	/**
	 * 布局：垂直居中；
	 * 字体：微软雅黑   10号字体 
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle fontForBody(HSSFWorkbook wb){
		HSSFCellStyle style = wb.createCellStyle();
		
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFFont font = wb.createFont();
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short)10);
		
		style.setFont(font);
		
		return style;
	}
}
