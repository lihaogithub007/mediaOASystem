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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.Department;
import com.yuyu.soft.entity.Duty;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IDepartmentService;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.POIUtil;
import com.yuyu.soft.util.PageUtil;
import com.yuyu.soft.util.PagerInfo;

/**
 * 通讯录
 *                       
 * @Filename: AddressBookController.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Controller
public class AddressBookController {
    protected Logger           log = LoggerFactory.getLogger(AddressBookController.class);
    @Resource
    private IUserService       userService;
    @Resource
    private IDepartmentService departmentService;
    @Resource
    private IDutyService       dutyService;

    /**
     * 通讯录
     */
    @RequestMapping({ "/admin/usercenter/usercenter_address_book_list.htm" })
    public ModelAndView usercenter_address_book_list(HttpServletRequest request,
                                                     HttpServletResponse response, String pageSize,
                                                     String currentPage, Long department_id,
                                                     Long duty_id, String true_name) {
        ModelAndView mv = new JModelAndView("admin/usercenter/usercenter_address_book_list.html",
            request, response);

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        StringBuilder hql = new StringBuilder();
        hql.append("from User t where t.disabled = false");

        //部门查询条件
        if (CommUtil.isNotNull(department_id)) {
            hql.append(" and t.department.id =:department_id ");
            paramsMap.put("department_id", department_id);
        }
        //岗位查询条件
        if (CommUtil.isNotNull(duty_id)) {
            hql.append(" and t.duty.id =:duty_id ");
            paramsMap.put("duty_id", duty_id);
        }
        if (CommUtil.isNotNull(true_name)) {
            hql.append(" and t.true_name like :true_name ");
            paramsMap.put("true_name", "%" + true_name.trim() + "%");
        }
        hql.append(" order by t.createtime desc");

        List<User> list = userService.queryUser(hql.toString(), paramsMap, pager);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);

        List<Department> departments = departmentService.queryAllDepartment();
        List<Duty> dutys = dutyService.queryAllDuty();
        mv.addObject("departments", departments);
        mv.addObject("dutys", dutys);
        mv.addObject("department_id", department_id);
        mv.addObject("duty_id", duty_id);
        mv.addObject("true_name", true_name);
        mv.addObject("user_relationship_map", Constants.USER_RELATIONSHIP_MAP);
        return mv;
    }

    //=============================================导出Excel区域开始======================================================//
    @RequestMapping("/admin/usercenter/usercenter_address_book_exportExcel.htm")
    public synchronized void usercenter_address_book_exportExcel(HttpServletRequest request,
                                                                 HttpServletResponse response,
                                                                 String currentPage,
                                                                 Long department_id, Long duty_id,
                                                                 String true_name) {

        List<Object[]> list = null;
        OutputStream out = null;
        SXSSFWorkbook workbook = null;
        SXSSFSheet sheet = null;

        try {
            workbook = new SXSSFWorkbook(100);
            sheet = (SXSSFSheet) workbook.createSheet("通讯录");
            sheet.setDisplayGridlines(false);//设置网格线（无）
            String titleName = "通讯录";
            String[] titleNames = { "序号", "部门/科组", "岗位", "姓名", "工作证号", "员工关系", "座机", "手机号", "备注" };
            POIUtil.setColumnWidth(sheet, titleNames.length);//设置通用列宽
            sheet.setColumnWidth(8, 20 * 512);//设置备注列宽

            int all_count = userService.getUserAddressBookCountForExportExcel(department_id,
                duty_id, true_name);

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
                    log.info("【通讯录导出-分页填充】第" + (i + 1) + "次填充开始====>" + CommUtil.getCurrentTime());

                    list = userService.getUserAddressBookForExportExcel(department_id, duty_id,
                        true_name, beginIndex, pageSize);

                    rowNum = 3 + i * pageSize;
                    fillSheet(sheet, titleName, titleNames, list, rowNum);
                    log.info("【通讯录导出-分页填充】第" + (i + 1) + "次填充结束====>" + CommUtil.getCurrentTime());
                    list.clear();
                }
                list = null;
            }

            response.setContentType("application/vnd.ms-excel");
            String fileName = "通讯录" + new Date().getTime() + ".xlsx";
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
            POIUtil.setTitleNoBorder(sheet, titleName, 0, titleNames.length - 1);
            fillDate(sheet, titleNames.length - 1);
            POIUtil.setTitles(sheet, titleNames, 2);
        }
        this.fillData(sheet, list, rowNum);
    }

    //填充导出日期
    private void fillDate(SXSSFSheet sheet, int cell_num) {
        CellStyle cellStyle = POIUtil.getBaseCellStyle(sheet, CellStyle.BORDER_NONE,
            CellStyle.ALIGN_RIGHT, CellStyle.VERTICAL_CENTER);
        Font font = POIUtil.getBaseFont(sheet);
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);
        Cell cell = null;
        Row row = sheet.createRow(1);
        row.setHeightInPoints(2 * sheet.getDefaultRowHeightInPoints());//2行字的高度
        cell = row.createCell(cell_num);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(new XSSFRichTextString(new SimpleDateFormat("yyyy年MM月dd日")
            .format(new Date())));
    }

    private void fillData(SXSSFSheet sheet, List<Object[]> list, int rowNum) {

        if (list != null && list.size() > 0 && rowNum >= 3) {
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
                    if (4 == index) {
                        cell.setCellValue(obj2RichText(objs[index], "user_relationship"));
                    } else {
                        cell.setCellValue(new XSSFRichTextString(CommUtil.null2String(objs[index])));
                    }
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
            if ("user_relationship".equals(string)) {
                return new XSSFRichTextString(Constants.USER_RELATIONSHIP_MAP.get(CommUtil
                    .null2String(obj)));
            }
        } catch (Exception e) {
        }
        return new XSSFRichTextString("");
    }
    //=============================================导出Excel区域结束======================================================//
}
