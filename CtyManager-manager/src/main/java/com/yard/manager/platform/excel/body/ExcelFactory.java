package com.yard.manager.platform.excel.body;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.yard.manager.platform.excel.util.ExcelFontUtil;

public class ExcelFactory {

	/**
	 * 
	 * @param title 单元格标题
	 * @return
	 */
	public static Object[] getTitle(String title,String[] cellTitleName){
		Object[] objs = new Object[2];
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(title);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short)560);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = ExcelFontUtil.fontForTitle(wb);
		//sheet.addMergedRegion( new CellRangeAddress(1, (short) 2, 3, (short) 4));
		if(cellTitleName != null && cellTitleName.length > 0){
			for(int i=0;i<cellTitleName.length;i++){
				HSSFCell cell = row.createCell((short) i);
				cell.setCellValue(cellTitleName[i]);
				cell.setCellStyle(style);
				sheet.setColumnWidth(i, 16*256);
			}
		}
		objs[0] = wb;
		objs[1] = sheet;
		return objs;
	}
	public static void getBody(HSSFWorkbook wb, HSSFSheet sheet,String[][] bodys){
		HSSFCellStyle style = ExcelFontUtil.fontForBody(wb);
		if(bodys != null && bodys.length>0){
			for(int i=0;i<bodys.length;i++){
				HSSFRow row = sheet.createRow((int) i+1);
				row.setHeight((short)300);
				String[] bodyContent = bodys[i];
				if(bodyContent!=null && bodyContent.length>0){
					for(int j=0;j<bodyContent.length;j++){
						HSSFCell cell = row.createCell((short) j);
						cell.setCellValue(bodyContent[j]);
						cell.setCellStyle(style);
					}
				}
			}
		}
	}
	public static String explor(HttpServletRequest request,HSSFWorkbook wb,String files,String fileName){
		String path = request.getSession().getServletContext().getRealPath("/")+File.separator+files+File.separator;
		return explor(wb, path, fileName);
	}
	public static String explor(HSSFWorkbook wb,String realPath ,String files,String fileName){
		String path =realPath+File.separator+files+File.separator;
		return explor(wb, path, fileName);
	}
	public static String explor(HSSFWorkbook wb,String path,String fileName){
		File file =new File(path);    
		//如果文件夹不存在则创建    
		if  (!file.exists()  && !file.isDirectory())      
		{
		    file.mkdirs();
		}
	
		String filename = path+fileName+".xls";
		try {
			FileOutputStream fout = new FileOutputStream(filename);
			wb.write(fout);
			fout.close();
		} catch (IOException e) {
			
		}
		return filename;
	}
}
