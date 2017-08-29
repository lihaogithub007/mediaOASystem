package com.yuyu.soft.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuyu.soft.base.annotation.SecurityMapping;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.AttendanceRecord;
import com.yuyu.soft.entity.Department;
import com.yuyu.soft.service.IAttendanceRecordService;
import com.yuyu.soft.service.IDepartmentService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.POIUtil;
import com.yuyu.soft.util.PageUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

/**
 * 考勤记录
 *                       
 * @Filename: AttendanceRecordController.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Controller
public class AttendanceRecordController {
    protected Logger                 log = LoggerFactory
                                             .getLogger(AttendanceRecordController.class);
    @Resource
    private IDepartmentService       departmentService;
    @Resource
    private IAttendanceRecordService attendanceRecordService;

    @SecurityMapping(res_name = "考勤记录列表", res_url = "/admin/work_attendance/attendance_record_list.htm*", res_group = "考勤记录")
    @RequestMapping({ "/admin/work_attendance/attendance_record_list.htm" })
    public ModelAndView attendance_record_list(HttpServletRequest request,
                                               HttpServletResponse response, String pageSize,
                                               String currentPage, Long department_id,
                                               String true_name, String attendance_record_month) {
        ModelAndView mv = new JModelAndView("admin/work_attendance/attendance_record_list.html",
            request, response);

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        StringBuilder hql = new StringBuilder();
        hql.append("from AttendanceRecord t where t.disabled = false");

        if (CommUtil.isNotNull(department_id)) {
            hql.append(" and t.user.department.id =:department_id ");
            paramsMap.put("department_id", department_id);
        }
        if (CommUtil.isNotNull(true_name)) {
            hql.append(" and t.user.true_name like :true_name ");
            paramsMap.put("true_name", "%" + true_name.trim() + "%");
        }
        if (CommUtil.isBlank(attendance_record_month)) {
            attendance_record_month = CommUtil.getCurrentTime("yyyy-MM");
        }
        hql.append(" and date_format(t.attendance_record_month,'%Y-%m') = date_format('")
            .append(attendance_record_month.trim() + "-01").append("','%Y-%m')");

        hql.append(" order by t.user.department.id asc,t.user.id asc");

        List<AttendanceRecord> list = attendanceRecordService.queryAttendanceRecord(hql.toString(),
            paramsMap, pager);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);

        List<Department> departments = departmentService.queryAllDepartment();
        mv.addObject("departments", departments);
        mv.addObject("currentPage", currentPage);
        mv.addObject("department_id", department_id);
        mv.addObject("true_name", true_name);
        mv.addObject("attendance_record_month", attendance_record_month);
        return mv;
    }

    @SecurityMapping(res_name = "编辑", res_url = "/admin/work_attendance/attendance_record_edit.htm*", res_group = "考勤记录")
    @RequestMapping({ "/admin/work_attendance/attendance_record_edit.htm" })
    public ModelAndView user_edit(HttpServletRequest request, HttpServletResponse response,
                                  String currentPage, Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/work_attendance/attendance_record_list.htm?currentPage="
                            + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "考勤记录标识为空", request, response);
        }
        AttendanceRecord attendanceRecord = attendanceRecordService.getAttendanceRecord(id);
        if (attendanceRecord == null) {
            return CommUtil.errorPage(target_url, "找不到考勤记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/work_attendance/attendance_record_edit.html",
            request, response);
        mv.addObject("obj", attendanceRecord);
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    @RequestMapping({ "/admin/work_attendance/attendance_record_edit_save.htm" })
    public void attendance_record_edit_save(HttpServletRequest request,
                                            HttpServletResponse response, String pageSize,
                                            String currentPage, Long id) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "考勤记录编辑保存成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "考勤记录标识为空");
        }
        if (rmg.getResult()) {
            try {
                WebForm wf = new WebForm();
                AttendanceRecord dbAttendanceRecord = attendanceRecordService
                    .getAttendanceRecord(id);
                AttendanceRecord attendanceRecord = (AttendanceRecord) wf.toPo(request,
                    dbAttendanceRecord);
                rmg = attendanceRecordService.edit_save(attendanceRecord);
            } catch (Exception e) {
                e.printStackTrace();
                CommUtil.setResultMsgData(rmg, false, "考勤记录编辑保存失败");
            }
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/work_attendance/attendance_record_list.htm?currentPage="
                    + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 导出
     */

    @SecurityMapping(res_name = "导出Excel", res_url = "/admin/work_attendance/attendance_record_exportExcel.htm*", res_group = "考勤记录")
    @RequestMapping("/admin/work_attendance/attendance_record_exportExcel.htm")
    public synchronized void attendance_record_exportExcel(HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           String currentPage, Long department_id,
                                                           String true_name,
                                                           String attendance_record_month) {

        List<Object[]> list = null;
        OutputStream out = null;
        SXSSFWorkbook workbook = null;
        SXSSFSheet sheet = null;

        try {
            workbook = new SXSSFWorkbook(100);
            sheet = (SXSSFSheet) workbook.createSheet("考勤统计");

            String attendance_record_month_substring = attendance_record_month.substring(5, 7);
            if (attendance_record_month_substring.substring(0, 1).equals("0")) {
                attendance_record_month_substring = attendance_record_month_substring.substring(1,
                    2);
            }
            //  ___1__月考勤表____  __ ___部门____________组别
            String titleName = "___" + attendance_record_month_substring
                               + "__月考勤表____  __ ___部门____________组别";

            String[] titleNames = { "序号", "部门", "姓名", "员工编号", "应出勤（天）", "实出勤（天）", "公出（天）",
                    "未打卡（次）", "迟到早退（次）", "工作日加班", "休息日加班", "节假日加班", "本月调休", "上月剩余", "本月剩余",
                    "旷工（天）", "病假（天）", "事假（天）", "婚假（天）", "丧假（天）", "本月", "累计", "产检（天）", "产假（天）",
                    "公休日合计", "其他说明", "本人签字" };

            //            POIUtil.setColumnWidth(sheet, titleNames.length);//设置通用列宽
            for (int i = 0; i < 27; i++) {
                sheet.setColumnWidth(i, 20 * 100);//设置标题列宽
            }

            int all_count = attendanceRecordService.getCountForExportExcel(department_id,
                true_name, attendance_record_month);

            if (all_count == 0) {
                fillSheet(sheet, titleName, titleNames, null, 3);
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
                    log.info("【考勤统计导出-分页填充】第" + (i + 1) + "次填充开始====>" + CommUtil.getCurrentTime());

                    list = attendanceRecordService.getAttendanceRecordsForExportExcel(
                        department_id, true_name, attendance_record_month, beginIndex, pageSize);

                    rowNum = 3 + i * pageSize;
                    fillSheet(sheet, titleName, titleNames, list, rowNum);
                    log.info("【考勤统计导出-分页填充】第" + (i + 1) + "次填充结束====>" + CommUtil.getCurrentTime());
                    list.clear();
                }
                list = null;
            }

            response.setContentType("application/vnd.ms-excel");
            String fileName = "考勤统计" + new Date().getTime() + ".xlsx";
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
        if (3 == rowNum) {
            setTitle(sheet, titleName, 0, titleNames.length - 1);
            setTitles(sheet, titleNames, 1);
        }
        this.fillData(sheet, list, rowNum);
    }

    /**
     * 设置标题
     */
    private static void setTitle(SXSSFSheet sheet, String titleName, int row, int merge_cells) {
        CellStyle titleCellStyle = POIUtil.getBaseCellStyle(sheet);
        Font titleFont = POIUtil.getBaseFont(sheet);
        //        titleFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//加粗
        titleFont.setFontHeightInPoints((short) 12);//18号字
        titleCellStyle.setFont(titleFont);
        titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        Row titleRow = sheet.createRow(row);
        titleRow.setHeightInPoints(sheet.getDefaultRowHeightInPoints());//2行字的高度
        Cell cell = titleRow.createCell(0);
        cell.setCellValue(titleName);
        cell.setCellStyle(titleCellStyle);

        CellRangeAddress region = new CellRangeAddress(row, row, 0, merge_cells);
        POIUtil.setRegionBorder(CellStyle.BORDER_THIN, region, sheet, sheet.getWorkbook());
        sheet.addMergedRegion(region);

    }

    /**
     * 设置标题
     */
    private static void setTitles(SXSSFSheet sheet, String[] titleNames, int row) {
        CellStyle titleCellStyle = POIUtil.getBaseCellStyle(sheet);
        Font titleFont = POIUtil.getBaseFont(sheet);
        titleFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        titleFont.setFontHeightInPoints((short) 10);
        titleCellStyle.setFont(titleFont);

        Row row2 = sheet.createRow(row + 1);

        Row titleRow = sheet.createRow(row);
        titleRow.setHeightInPoints((float) (sheet.getDefaultRowHeightInPoints()));//1.5行字的高度
        Cell cell = null;
        for (int i = 0; i < titleNames.length; i++) {
            cell = titleRow.createCell(i);
            cell.setCellStyle(titleCellStyle);
			if (i != 9 && i != 10 && i != 11 && i != 12 && i != 13 && i != 14 && i != 20 
					&& i != 21 && i != 25 && i != 26) {
                cell.setCellValue(titleNames[i]);
                CellRangeAddress region = new CellRangeAddress(1, 2, i, i);
                POIUtil.setRegionBorder(CellStyle.BORDER_THIN, region, sheet, sheet.getWorkbook());
                sheet.addMergedRegion(region);
            }
            if (i == 9) {
                cell.setCellValue("加班(时)");
            }
            if (i == 12) {
                cell.setCellValue("调休(时)");
            }
            if (i == 20) {
                cell.setCellValue("年假(天)");
            }
            if (i == 25) {
            	cell.setCellValue("其他说明");
            }
            if (i == 26) {
            	cell.setCellValue("本人签字");
            }
            
            cell.setCellStyle(titleCellStyle);
        }
        Cell cell9 = (row2).createCell(9);
        cell9.setCellStyle(titleCellStyle);
        cell9.setCellValue("工作日加班");

        Cell cell10 = (row2).createCell(10);
        cell10.setCellStyle(titleCellStyle);
        cell10.setCellValue("休息日加班");

        Cell cell11 = (row2).createCell(11);
        cell11.setCellStyle(titleCellStyle);
        cell11.setCellValue("休假日加班");

        CellRangeAddress region = new CellRangeAddress(1, 1, 9, 11);
        sheet.addMergedRegion(region);

        Cell cell12 = (row2).createCell(12);
        cell12.setCellStyle(titleCellStyle);
        cell12.setCellValue("本月调休");

        Cell cell13 = (row2).createCell(13);
        cell13.setCellStyle(titleCellStyle);
        cell13.setCellValue("上月剩余");

        Cell cell14 = (row2).createCell(14);
        cell14.setCellStyle(titleCellStyle);
        cell14.setCellValue("本月剩余");

        CellRangeAddress region2 = new CellRangeAddress(1, 1, 12, 14);
        sheet.addMergedRegion(region2);

        Cell cell20 = (row2).createCell(20);
        cell20.setCellStyle(titleCellStyle);
        cell20.setCellValue("本月");

        Cell cell21 = (row2).createCell(21);
        cell21.setCellStyle(titleCellStyle);
        cell21.setCellValue("累计");

        CellRangeAddress region3 = new CellRangeAddress(1, 1, 20, 21);
        sheet.addMergedRegion(region3);
        
        Cell cell25 = (row2).createCell(25);
        cell25.setCellStyle(titleCellStyle);
        
        Cell cell26 = (row2).createCell(26);
        cell26.setCellStyle(titleCellStyle);
        

    }

    private void fillData(SXSSFSheet sheet, List<Object[]> list, int rowNum) {

        if (list != null && list.size() > 0 && rowNum >= 3) {
            CellStyle cellStyle = POIUtil.getBaseCellStyle(sheet);
            Font font = POIUtil.getBaseFont(sheet);
            cellStyle.setFont(font);
            for (Object[] objs : list) {
                objs[0] = "多媒体";
                objs[25] = "";
                Cell cell = null;
                Row row = sheet.createRow(rowNum);
                int columnNum = 0;
                // 序号
                cell = row.createCell(columnNum++);
                //创建第26个单元格
                cell.setCellValue(new XSSFRichTextString(rowNum - 2 + ""));
                //                Cell cell26 = row.createCell(columnNum++);
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

    private XSSFRichTextString obj2RichText(Object obj, String string) {
        if (!CommUtil.isNotNull(obj)) {
            return new XSSFRichTextString("");
        }
        try {
            if ("datetime".equals(string)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                return new XSSFRichTextString(dateFormat.format(obj));
            }
        } catch (Exception e) {
        }
        return new XSSFRichTextString("");
    }

    //=============================================导出Excel区域结束======================================================//

}
