package com.yard.manager.platform.excel;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import ognl.Ognl;
import ognl.OgnlException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;

// Referenced classes of package net.jt.web.action:
//            MybatisAction
//no
public class ExcelExportAction
{
    String fileNames="44";
    String fileName="D://";
    InputStream excelStream;

	   public static void main(String[] args) {
		   new ExcelExportAction().exportExcel();
	}
	   
    public ExcelExportAction()
    {
    }
    public String exportExcel()
    {
        String temp ="";
        JSONArray columns = new JSONArray();
        JSONArray columns2 = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.accumulate("title", "title1");
        columns2.add(obj);
        columns.add(columns2);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheet1");
        fillResult(workbook, sheet, columns, new ArrayList(), 1, 1, -1);
        fileNames = (new StringBuilder()).append(fileName).append("(").append(createFileName()).append(").xls").toString();
        try
        {
            exportExcel(workbook, fileNames);
        }
        catch(IOException e1) { }
        return "outExcel";
    }

    protected JSONArray createColumns()
    {
        return null;
    }

    protected HSSFSheet fillResult(HSSFWorkbook workbook, HSSFSheet sheet, JSONArray columns, List rows, int x, int y, int rowMax)
    {
        for(int i = 0; i < columns.size() - 1; i++)
            createJsonTitle(workbook, sheet, x, y + i, columns.getJSONArray(i));

        exportResult(workbook, sheet, rows, x, (y + columns.size()) - 1, rowMax, columns.getJSONArray(columns.size() - 1));
        return sheet;
    }

    public void createJsonTitle(HSSFWorkbook workbook, HSSFSheet sheet, int x, int y, JSONArray fields)
    {
        HSSFRow excelrow = sheet.createRow(y);
        int count = fields.size();
        try
        {
            for(int i = 0; i < count; i++)
            {
                JSONObject value = fields.getJSONObject(i);
                if(value.containsKey("title"))
                    createJsonCell(workbook, sheet, excelrow, x + i, value);
            }

        }
        catch(Exception ex) { }
    }

    public void createJsonCell(HSSFWorkbook workbook, HSSFSheet sheet, HSSFRow excelrow, int x, JSONObject field)
    {
        HSSFCellStyle st1 = createTitleStyle(workbook);
        try
        {
            String title = field.getString("title");
            int rowspan = 0;
            int colspan = 0;
            if(field.containsKey("rowspan"))
                rowspan = field.getInt("rowspan");
            if(field.containsKey("colspan"))
                colspan = field.getInt("colspan");
            createCell(excelrow, x, st1, title);
            if(rowspan > 0 || colspan > 0)
                sheet.addMergedRegion(new CellRangeAddress(excelrow.getRowNum(), x, excelrow.getRowNum() + rowspan, x + colspan));
        }
        catch(Exception ex) { }
    }

    public void exportResult(HSSFWorkbook workbook, HSSFSheet sheet, List rows, int x, int y, int rowMax, JSONArray fields)
    {
        int count = rows.size();
        int fcount = fields.size();
        String fieldNames[] = new String[fcount];
        for(int j = 0; j < fcount; j++)
            try
            {
                fieldNames[j] = fields.getJSONObject(j).getString("field");
            }
            catch(Exception ex) { }

        HSSFCellStyle st1 = createCellStyle(workbook);
        for(int i = 0; i < count && i != rowMax; i++)
        {
            Object row = rows.get(i);
            HSSFRow excelrow = sheet.createRow(y + i);
            for(int j = 0; j < fcount; j++)
            {
                if(row == null)
                    continue;
                try
                {
                    Object value = Ognl.getValue(fieldNames[j], row);
                    createCell(excelrow, x + j, st1, value);
                }
                catch(OgnlException e1) { }
            }

        }

    }

    protected HSSFCellStyle createStyle(String name)
    {
        return null;
    }

   

    private void setSheetColumnWidth(String titles_CN[], HSSFSheet sheet)
    {
        for(int i = 0; i < titles_CN.length; i++)
            sheet.setColumnWidth(i, 3000);

    }

    private HSSFCellStyle createTitleStyle(HSSFWorkbook wb)
    {
        HSSFFont boldFont = wb.createFont();
        boldFont.setFontHeight((short)320);
        HSSFCellStyle style = wb.createCellStyle();
        style.setVerticalAlignment((short)2);
        style.setAlignment((short)2);
        style.setFont(boldFont);
        return style;
    }

    private HSSFCellStyle createCellStyle(HSSFWorkbook wb)
    {
        HSSFFont boldFont = wb.createFont();
        boldFont.setFontHeight((short)240);
        HSSFCellStyle style = wb.createCellStyle();
        style.setFont(boldFont);
        style.setVerticalAlignment((short)2);
        style.setAlignment((short)2);
        return style;
    }

    protected void createCell(HSSFRow row, int column, Object value)
    {
        createCell(row, column, null, value);
    }

    protected void createCell(HSSFRow row, int column, HSSFCellStyle style, Object value)
    {
        int cellType = 1;
        if(value == null)
            cellType = 3;
        if(value instanceof String)
            cellType = 1;
        else
        if(value instanceof Integer)
            cellType = 0;
        else
        if(value instanceof Long)
            cellType = 0;
        else
        if(value instanceof Short)
            cellType = 0;
        cellType = 1;
        createCell(row, column, style, cellType, value);
    }

    protected void createCell(HSSFRow row, int column, HSSFCellStyle style, int cellType, Object value)
    {
        HSSFCell cell = row.createCell(column);
        if(value == null)
            return;
        if(style != null)
            cell.setCellStyle(style);
        switch(cellType)
        {
        case 1: // '\001'
            cell.setCellValue((new StringBuilder()).append(value.toString()).append("").toString());
            break;

        case 0: // '\0'
            cell.setCellType(0);
            cell.setCellValue(Double.parseDouble(value.toString()));
            break;
        }
    }

    protected String createFileName()
    {
        Calendar c = Calendar.getInstance();
        int year = c.get(1);
        int month = c.get(2) + 1;
        String month_ = new String((new StringBuilder()).append("").append(month).toString());
        if(month < 10)
            month_ = (new StringBuilder()).append("0").append(month).toString();
        int day = c.get(5);
        String day_ = new String((new StringBuilder()).append("").append(day).toString());
        if(day < 10)
            day_ = (new StringBuilder()).append("0").append(day).toString();
        return (new StringBuilder()).append(year).append(month_).append(day).toString();
    }

    private void exportExcel(HSSFWorkbook workbook, String fileName)
        throws IOException
    {
        fileNames = fileName;
        fileNames = new String(fileName.getBytes("gbk"), "iso8859-1");
        
     /*   ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        baos.flush();
        byte aa[] = baos.toByteArray();
        excelStream = new ByteArrayInputStream(aa, 0, aa.length);
        baos.close();*/
    	try {
			FileOutputStream fout = new FileOutputStream(fileNames);
			workbook.write(fout);
			fout.close();
		} catch (IOException e) {
			
		}
    }

    int getIntValue(Map m, String f)
    {
        Object o = m.get(f);
        if(o instanceof Integer)
            return ((Integer)o).intValue();
        if(o instanceof Long)
        {
            Long o1 = (Long)o;
            return o1.intValue();
        } else
        {
            return 0;
        }
    }

    
}
