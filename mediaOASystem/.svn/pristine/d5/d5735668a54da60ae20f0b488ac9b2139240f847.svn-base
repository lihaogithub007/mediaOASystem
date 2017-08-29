package com.yuyu.soft.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * POI工具类
 */
public class POIUtil {
    private POIUtil() {
    }

    /**
     * 文件名分隔符
     */
    private static String SEPARATOR = File.separator;

    /**
     * 以给定的文件路径创建一个新的Excel文件
     * @param filepath 相对于工程的文件路径
     * @throws IOException
     */
    public static File createExcelFile(String excelTemplatePath, String filepath)
                                                                                 throws IOException {

        // 获取到文件的父目录
        String path = POIUtil.getFilePath(filepath);

        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();

        // 复制模板
        FileOutputStream fos = null;
        InputStream fis = null;
        byte[] bytes = new byte[1024];
        int len = 0;
        try {
            fos = new FileOutputStream(file);
            fis = POIUtil.class.getResourceAsStream(excelTemplatePath);
            while ((len = fis.read(bytes)) > 0) {
                fos.write(bytes, 0, len);
            }
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return file;
    }

    /**
     * 上下文路径
     */
    public static String getFilePath(String filepath) {
        if (filepath.startsWith(SEPARATOR)) {
            throw new IllegalArgumentException("【" + filepath + "】不是一个有效的路径");
        }
        return POIUtil.class.getResource("/").getPath() + filepath;
    }

    /**
     * 设置标题
     */
    public static void setTitle(SXSSFSheet sheet, String titleName, int row, int merge_cells) {
        CellStyle titleCellStyle = getBaseCellStyle(sheet);
        Font titleFont = getBaseFont(sheet);
        titleFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//加粗
        titleFont.setFontHeightInPoints((short) 18);//18号字
        titleCellStyle.setFont(titleFont);

        Row titleRow = sheet.createRow(row);
        titleRow.setHeightInPoints(2 * sheet.getDefaultRowHeightInPoints());//2行字的高度
        Cell cell = titleRow.createCell(0);
        cell.setCellValue(titleName);
        cell.setCellStyle(titleCellStyle);
        CellRangeAddress region = new CellRangeAddress(row, row, 0, merge_cells);
        setRegionBorder(CellStyle.BORDER_THIN, region, sheet, sheet.getWorkbook());
        sheet.addMergedRegion(region);

    }

    /**
     * 设置标题(无边框)
     */
    public static void setTitleNoBorder(SXSSFSheet sheet, String titleName, int row, int merge_cells) {
        CellStyle titleCellStyle = getBaseCellStyle(sheet, CellStyle.BORDER_NONE,
            CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
        Font titleFont = getBaseFont(sheet);
        titleFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//加粗
        titleFont.setFontHeightInPoints((short) 18);//18号字
        titleCellStyle.setFont(titleFont);

        Row titleRow = sheet.createRow(row);
        titleRow.setHeightInPoints(2 * sheet.getDefaultRowHeightInPoints());//2行字的高度
        Cell cell = titleRow.createCell(0);
        cell.setCellValue(titleName);
        cell.setCellStyle(titleCellStyle);
        CellRangeAddress region = new CellRangeAddress(row, row, 0, merge_cells);
        setRegionBorder(CellStyle.BORDER_NONE, region, sheet, sheet.getWorkbook());
        sheet.addMergedRegion(region);
    }

    /**
     * 设置标题
     */
    public static void setTitles(SXSSFSheet sheet, String[] titleNames, int row) {
        CellStyle titleCellStyle = getBaseCellStyle(sheet);
        Font titleFont = getBaseFont(sheet);
        titleFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        titleFont.setFontHeightInPoints((short) 12);
        titleCellStyle.setFont(titleFont);

        Row titleRow = sheet.createRow(row);
        titleRow.setHeightInPoints((float) (1.5 * sheet.getDefaultRowHeightInPoints()));//1.5行字的高度
        Cell cell = null;
        for (int i = 0; i < titleNames.length; i++) {
            cell = titleRow.createCell(i);
            cell.setCellStyle(titleCellStyle);
            cell.setCellValue(titleNames[i]);
        }
    }

    /**
     * 基础单元格样式（带边框、水平垂直居中、自动换行）
     */
    public static CellStyle getBaseCellStyle(SXSSFSheet sheet) {
        return getBaseCellStyle(sheet, CellStyle.BORDER_THIN, CellStyle.ALIGN_CENTER,
            CellStyle.VERTICAL_CENTER);
    }

    public static CellStyle getBaseCellStyle(SXSSFSheet sheet, short border, short alignment,
                                             short verticalAlignment) {

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();

        //设置边框
        cellStyle.setBorderBottom(border);
        cellStyle.setBorderLeft(border);
        cellStyle.setBorderTop(border);
        cellStyle.setBorderRight(border);

        //设置水平、垂直居中、自动换行
        cellStyle.setAlignment(alignment);
        cellStyle.setVerticalAlignment(verticalAlignment);
        cellStyle.setWrapText(true);

        return cellStyle;
    }

    /**
     * 基础字体（宋体、正常粗细）
     */
    public static Font getBaseFont(SXSSFSheet sheet) {
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("宋体");
        font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
        /*font.setColor(HSSFColor.RED.index);*/
        return font;
    }

    /**
     * 设置合并单元格的边框
     */
    public static void setRegionBorder(int border, CellRangeAddress region, Sheet sheet, Workbook wb) {
        RegionUtil.setBorderBottom(border, region, sheet, wb);
        RegionUtil.setBorderLeft(border, region, sheet, wb);
        RegionUtil.setBorderRight(border, region, sheet, wb);
        RegionUtil.setBorderTop(border, region, sheet, wb);
    }

    /**
     * 设置列宽
     */
    public static void setColumnWidth(SXSSFSheet sheet, int column) {
        for (int i = 1; i < column; i++) {
            sheet.setColumnWidth(i, 9 * 512);
        }
    }

    public static void setColumnWidth(SXSSFSheet sheet, int column, byte width) {
        for (int i = 1; i < column; i++) {
            sheet.setColumnWidth(i, width * 512);
        }
    }

    /**
     * 计算某列，从哪行到哪行连续数据的和
     * */
    public static int calcCount(int col, int sRow, int eRow, Sheet sheet) {
        int sum = 0;
        for (int i = sRow; i <= eRow; i++) {
            if (sheet.getRow(i) != null && sheet.getRow(i).getCell(col) != null) {
                int j = (int) sheet.getRow(i).getCell(col).getNumericCellValue();
                sum += j;
            }
        }
        return sum;
    }

    public static List<ArrayList<String>> getDataFromExcel(String filePath) {

        File f = new File(filePath);
        if (!f.exists()) {
            return null;
        }
        if (!filePath.endsWith(".xls") && !filePath.endsWith(".xlsx")) {
            return null;
        }
        List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        FileInputStream fis = null;
        Workbook wookbook = null;
        ArrayList<String> rowList = null;
        try {
            fis = new FileInputStream(f);
            if (filePath.endsWith(".xls")) {
                wookbook = new HSSFWorkbook(fis);
            } else if (filePath.endsWith(".xlsx")) {
                wookbook = new XSSFWorkbook(fis);
            }

            Sheet sheet = wookbook.getSheetAt(0);
            int totalRowNum = sheet.getLastRowNum();
            for (int i = 0; i <= totalRowNum; i++) {

                rowList = new ArrayList<String>();
                Row row = sheet.getRow(i);
                if (row != null) {
                    short totalCellNum = row.getLastCellNum();
                    for (short j = 0; j <= totalCellNum + 1; j++) {
                        Cell cell = row.getCell(j);
                        if (cell == null) {
                            rowList.add("");
                            continue;
                        }
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        rowList.add(cell.getRichStringCellValue().getString());
                    }
                    list.add(rowList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
