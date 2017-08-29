package com.yuyu.soft.admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuyu.soft.base.annotation.SecurityMapping;
import com.yuyu.soft.base.controller.BaseController;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.Attachments;
import com.yuyu.soft.entity.WorkforceManagement;
import com.yuyu.soft.service.IAttachmentsService;
import com.yuyu.soft.service.IWorkforceManagementService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.POIUtil;
import com.yuyu.soft.util.PageUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 排班统计
 *                       
 * @Filename: WorkforceManagementController.java
 * @Version: 1.0
 *
 */
@Controller
public class WorkforceManagementController {
    protected Logger                    log = LoggerFactory
                                                .getLogger(WorkforceManagementController.class);
    @Resource
    private IWorkforceManagementService workforceManagementService;
    @Resource
    private IAttachmentsService         attachmentsService;

    @SecurityMapping(res_name = "排班管理列表", res_url = "/admin/work_attendance/workforce_management_list.htm*", res_group = "排班管理")
    @RequestMapping({ "/admin/work_attendance/workforce_management_list.htm" })
    public ModelAndView workforce_management_list(HttpServletRequest request,
                                                  HttpServletResponse response, String pageSize,
                                                  String currentPage, String user_name, String year) {
        //默认显示当前年
        if (year == null) {
            Date date = new Date();
            String time = CommUtil.parseShortDateTime(date);
            year = time.substring(0, 4);
        }

        ModelAndView mv = new JModelAndView("admin/work_attendance/workforce_management_list.html",
            request, response);

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);

        List<Map<String, Object>> list = queryWorkforcemanagementsMap(user_name, year, pager);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);
        mv.addObject("user_name", user_name);
        mv.addObject("year", year);
        return mv;
    }

    private List<Map<String, Object>> queryWorkforcemanagementsMap(String user_name, String year,
                                                                   PagerInfo pager) {

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("from (");
        sql.append("select temp.user_name");
        sql.append(" ,max(CASE temp.month WHEN '").append(year)
            .append("-01' THEN temp.count ELSE 0 END) AS Jan");
        sql.append(" ,max(CASE temp.month WHEN '").append(year)
            .append("-02' THEN temp.count ELSE 0 END) AS Feb");
        sql.append(" ,max(CASE temp.month WHEN '").append(year)
            .append("-03' THEN temp.count ELSE 0 END) AS Mar");
        sql.append(" ,max(CASE temp.month WHEN '").append(year)
            .append("-04' THEN temp.count ELSE 0 END) AS Apr");
        sql.append(" ,max(CASE temp.month WHEN '").append(year)
            .append("-05' THEN temp.count ELSE 0 END) AS May");
        sql.append(" ,max(CASE temp.month WHEN '").append(year)
            .append("-06' THEN temp.count ELSE 0 END) AS Jun");
        sql.append(" ,max(CASE temp.month WHEN '").append(year)
            .append("-07' THEN temp.count ELSE 0 END) AS Jul");
        sql.append(" ,max(CASE temp.month WHEN '").append(year)
            .append("-08' THEN temp.count ELSE 0 END) AS Aug");
        sql.append(" ,max(CASE temp.month WHEN '").append(year)
            .append("-09' THEN temp.count ELSE 0 END) AS Sep");
        sql.append(" ,max(CASE temp.month WHEN '").append(year)
            .append("-10' THEN temp.count ELSE 0 END) AS Oct");
        sql.append(" ,max(CASE temp.month WHEN '").append(year)
            .append("-11' THEN temp.count ELSE 0 END) AS Nov");
        sql.append(" ,max(CASE temp.month WHEN '").append(year)
            .append("-12' THEN temp.count ELSE 0 END) AS Dece");
        sql.append(" from ( ");
        sql.append(" SELECT t.user_name,count(t.user_name) as count,DATE_FORMAT(t.work_time,'%Y-%m') as month ");
        sql.append(" from tb_workforce_management t");
        sql.append(" where t.disabled = false");
        sql.append(" and DATE_FORMAT(t.work_time,'%Y') = ").append(year);
        if (CommUtil.isNotNull(user_name)) {
            sql.append(" and t.user_name like '%").append(user_name.trim()).append("%'");
        }
        sql.append(" group by t.user_name,DATE_FORMAT(t.work_time,'%Y-%m')");
        sql.append(" )temp group by temp.user_name");
        sql.append(" ) ts ");

        List<Map<String, Object>> list = workforceManagementService.queryWorkforceManagementMap(
            sql.toString(), paramsMap, pager);
        return list;
    }

    //=============================================导出Excel区域开始======================================================//

    @SecurityMapping(res_name = "导出Excel", res_url = "/admin/work_attendance/workforce_management_exportExcel.htm*", res_group = "排班管理")
    @RequestMapping("/admin/work_attendance/workforce_management_exportExcel.htm")
    public synchronized void workforce_management_exportExcel(HttpServletRequest request,
                                                              HttpServletResponse response,
                                                              String currentPage, String user_name,
                                                              String year) {

        //默认导出当前年
        if (year == null) {
            Date date = new Date();
            String time = CommUtil.parseShortDateTime(date);
            year = time.substring(0, 4);
        }

        List<Object[]> list = null;
        OutputStream out = null;
        SXSSFWorkbook workbook = null;
        SXSSFSheet sheet = null;

        try {
            workbook = new SXSSFWorkbook(100);
            sheet = (SXSSFSheet) workbook.createSheet("排班统计");

            String titleName = year + "年排班统计情况";
            String[] titleNames = { "序号", "姓名", "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月",
                    "9月", "10月", "11月", "12月" };
            POIUtil.setColumnWidth(sheet, titleNames.length, (byte) 6);
            sheet.setColumnWidth(1, 10 * 512);

            int all_count = workforceManagementService.getCountForExportExcel(user_name, year);

            if (all_count == 0) {
                fillSheet(sheet, titleName, titleNames, null, 2);
            } else {
                //计算分页数
                log.info("总大小：" + all_count);
                int pageSize = Constants.DEFAULT_PAGE_SIZE_FOR_EXPORT_EXCEL;
                double temp_pages = (double) all_count / (double) pageSize;
                int pages = (int) Math.ceil(temp_pages);
                int beginIndex = 0;
                int rowNum = 0;
                for (int i = 0; i < pages; i++) {
                    beginIndex = 0 + i * pageSize;
                    log.info("【排班统计导出-分页填充】第" + (i + 1) + "次填充开始====>" + CommUtil.getCurrentTime());

                    list = workforceManagementService.getWorkforceManagementForExportExcel(
                        user_name, year, beginIndex, pageSize);

                    rowNum = 2 + i * pageSize;
                    fillSheet(sheet, titleName, titleNames, list, rowNum);
                    log.info("【排班统计导出-分页填充】第" + (i + 1) + "次填充结束====>" + CommUtil.getCurrentTime());
                    list.clear();
                }
                list = null;
            }

            response.setContentType("application/vnd.ms-excel");
            String fileName = "排班统计" + new Date().getTime() + ".xlsx";
            fileName = CommUtil.encodeExportFileName(request, fileName);
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void fillSheet(SXSSFSheet sheet, String titleName, String[] titleNames,
                           List<Object[]> list, int rowNum) {
        if (2 == rowNum) {
            POIUtil.setTitle(sheet, titleName, 0, titleNames.length - 1);
            POIUtil.setTitles(sheet, titleNames, 1);
        }
        this.fillData(sheet, list, rowNum);
    }

    private void fillData(SXSSFSheet sheet, List<Object[]> list, int rowNum) {

        if (list != null && list.size() > 0 && rowNum >= 2) {
            CellStyle cellStyle = POIUtil.getBaseCellStyle(sheet);
            Font font = POIUtil.getBaseFont(sheet);
            cellStyle.setFont(font);

            for (Object[] objs : list) {
                Cell cell = null;
                Row row = sheet.createRow(rowNum);
                int columnNum = 0;
                // 序号
                cell = row.createCell(columnNum++);
                cell.setCellValue(new XSSFRichTextString(rowNum - 1 + ""));
                cell.setCellStyle(cellStyle);
                for (int index = 0; index < objs.length; index++) {
                    cell = row.createCell(columnNum++);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(new XSSFRichTextString(CommUtil.null2String(objs[index])));
                }
                if (rowNum % 100 == 0) {
                    try {
                        sheet.flushRows();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                rowNum++;
            }
        }
    }

    //    //=============================================导出Excel区域结束======================================================//
    //

    /**
     * 导入 
     */
    @SecurityMapping(res_name = "导入", res_url = "/admin/work_attendance/workforce_management_import.htm*", res_group = "排班管理")
    @RequestMapping({ "/admin/work_attendance/workforce_management_import.htm" })
    public ModelAndView workload_statistics_import(HttpServletRequest request,
                                                   HttpServletResponse response) {

        ModelAndView mv = new JModelAndView(
            "admin/work_attendance/workforce_management_import.html", request, response);
        return mv;
    }

    @RequestMapping({ "/admin/work_attendance/workforce_management_import_save.htm" })
    public void workload_statistics_import_save(HttpServletRequest request,
                                                HttpServletResponse response, String pageSize,
                                                String currentPage, String log_date) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "导入数据保存成功");
        try {
            List<Attachments> list = BaseController.uploadify(request, response,
                "workforce_management", attachmentsService);
            if (list == null || list.size() == 0) {
                CommUtil.setResultMsgData(rmg, false, "文件保存失败");
            } else {
                Attachments attach = list.get(0);
                String file_path = request.getServletContext().getRealPath("/")
                                   + attach.getSave_path() + "/" + attach.getSave_file_name();

                List<WorkforceManagement> dataList = getDataFromExcel(file_path);

                //            	System.out.println(dataList);
                rmg = workforceManagementService.workforce_management_import_save(dataList);
            }
        } catch (IOException e) {
            CommUtil.setResultMsgData(rmg, false, "导入数据保存失败");
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/work_attendance/workforce_management_list.htm");
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    //导入
    private static List<WorkforceManagement> getDataFromExcel(String filePath) {

        List<WorkforceManagement> list_workforce_management = new ArrayList<WorkforceManagement>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
            for (int i = 1; i <= totalRowNum; i++) {

                rowList = new ArrayList<String>();
                Row row = sheet.getRow(i);
                if (row != null) {

                    for (short j = 1; j < 8; j++) {

                        Cell cell = row.getCell(j);

                        if (cell != null && !cell.equals("")) {
                            //获取人名
                            String user_name = cell.toString().trim();
                            if (user_name != null && !user_name.equals("")) {

                               /* //处理带“()”的人名
                                if (user_name.contains("(")) {
                                    user_name = user_name.substring(0, user_name.indexOf("("));
                                }
                                if (user_name.contains("（")) {
                                    user_name = user_name.substring(0, user_name.indexOf("（"));
                                }*/

                                //获取班次
                                Cell clases = row.getCell(0);
                                String classes = clases.toString();
                                if (null == classes || classes.equals("")) {
                                    int k = 1;
                                    while (classes == null || classes.equals("")) {
                                        classes = sheet.getRow(i - k).getCell(0).toString();
                                        k++;
                                    }
                                }

                                //获取排班日期
                                Cell cell_date = sheet.getRow(0).getCell(j);
                                // String worktime =
                                // cell_date.toString().substring(5,15);
                                int start = cell_date.toString().indexOf("(");
                                int end = cell_date.toString().indexOf(")");
                                String worktime = cell_date.toString().substring(start + 1, end);

                                Date work_time = sdf.parse(worktime);
                                //System.out.println(user_name+"\t"+classes+"\t"+worktime);

                                if (user_name != "") {
                                    WorkforceManagement workforceManagement = new WorkforceManagement();
                                    workforceManagement.setUser_name(user_name);
                                    workforceManagement.setClasses(classes);
                                    workforceManagement.setWork_time(work_time);

                                    list_workforce_management.add(workforceManagement);
                                }
                            }
                        }
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        //                        rowList.add(cell.getRichStringCellValue().getString());
                    }
                    //                    list.add(rowList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_workforce_management;
    }

    @SecurityMapping(res_name = "打印", res_url = "/admin/work_attendance/workforce_management_print.htm*", res_group = "排班管理")
    @RequestMapping({ "/admin/work_attendance/workforce_management_print.htm" })
    public ModelAndView workforce_management_print(HttpServletRequest request,
                                                   HttpServletResponse response, String user_name,
                                                   String year) {

        List<Map<String, Object>> list = queryWorkforcemanagementsMap(user_name, year, null);
        ModelAndView mv = new JModelAndView(
            "admin/work_attendance/workforce_management_print.html", request, response);
        mv.addObject("objs", list);
        mv.addObject("user_name", user_name);
        mv.addObject("year", year);
        return mv;
    }

}
