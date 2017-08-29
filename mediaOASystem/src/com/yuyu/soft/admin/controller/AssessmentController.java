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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuyu.soft.base.annotation.SecurityMapping;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.Assessment;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IAssessmentService;
import com.yuyu.soft.service.IDepartmentService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.POIUtil;
import com.yuyu.soft.util.PageUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

/**
 * 评优管理                       
 * @Filename: AssessmentController.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Controller
public class AssessmentController {

    protected static final Log log = LogFactory.getLog(AssessmentController.class);
    @Resource
    private IAssessmentService assessmentService;
    @Resource
    private IUserService       userService;
    @Resource
    private IDepartmentService departmentService;

    /**
     * 评优管理列表
     */
    @SecurityMapping(res_name = "评优管理列表", res_url = "/admin/user/assessment_list.htm*", res_group = "评优管理")
    @RequestMapping({ "/admin/user/assessment_list.htm" })
    public ModelAndView assessment_list(HttpServletRequest request, HttpServletResponse response,
                                        String pageSize, String currentPage, Long department_id,
                                        Long user_id, String award_date, String award_name,
                                        String award_works, String award_level, String award_unit) {
        ModelAndView mv = new JModelAndView("admin/user/assessment_list.html", request, response);

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        StringBuilder hql = new StringBuilder();
        hql.append("from Assessment t where t.disabled=false");
        if (department_id != null) {
            hql.append(" and t.department.id =:department_id ");
            paramsMap.put("department_id", department_id);
        }
        if (CommUtil.isNotNull(user_id)) {
            hql.append(" and (t.user_ids like '%").append(user_id).append(",%'");
            hql.append(" or t.user_ids like '%,").append(user_id).append("%'");
            hql.append(" or t.user_ids like '").append(user_id).append("')");
        }
        if (CommUtil.isNotNull(award_date)) {
            hql.append(" and t.award_date = str_to_date('").append(award_date)
                .append("','%Y-%m-%d')");
        }
        if (CommUtil.isNotNull(award_name)) {
            hql.append(" and t.award_name like :award_name ");
            paramsMap.put("award_name", "%" + award_name.trim() + "%");
        }
        if (CommUtil.isNotNull(award_works)) {
            hql.append(" and t.award_works like :award_works ");
            paramsMap.put("award_works", "%" + award_works.trim() + "%");
        }
        if (CommUtil.isNotNull(award_level)) {
            hql.append(" and t.award_level like :award_level ");
            paramsMap.put("award_level", "%" + award_level.trim() + "%");
        }
        if (CommUtil.isNotNull(award_unit)) {
            hql.append(" and t.award_unit like :award_unit ");
            paramsMap.put("award_unit", "%" + award_unit.trim() + "%");
        }
        hql.append(" order by t.createtime desc");
        List<Assessment> list = assessmentService.queryAssessment(hql.toString(), paramsMap, pager);
        operList(list);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);

        mv.addObject("departments", departmentService.queryAllDepartment());
        mv.addObject("users", userService.queryAllUser());
        mv.addObject("department_id", department_id);
        mv.addObject("user_id", user_id);
        mv.addObject("award_date", award_date);
        mv.addObject("award_name", award_name);
        mv.addObject("award_works", award_works);
        mv.addObject("award_level", award_level);
        mv.addObject("award_unit", award_unit);
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    private void operList(List<Assessment> list) {
        if (list != null && list.size() > 0) {
            for (Assessment a : list) {
                a.setUser_names(userService.getUserNames(a.getUser_ids()));
            }
        }
    }

    /**
     * ajax查询某部门下所有岗位
     * reutrn json
     */
    @RequestMapping({ "/admin/user/ajaxQueryUserByDepartment.htm" })
    public void ajaxQueryUserByDepartment(HttpServletRequest request, HttpServletResponse response,
                                          Long department_id) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "获取用户成功");
        try {
            List<User> list = userService.queryUserByDepartment(department_id);
            rmg.setData(list);
        } catch (Exception e) {
            CommUtil.setResultMsgData(rmg, false, "获取用户失败");
        }
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    //=============================================导出Excel区域开始======================================================//

    @SecurityMapping(res_name = "导出Excel", res_url = "/admin/user/assessment_exportExcel.htm*", res_group = "评优管理")
    @RequestMapping("/admin/user/assessment_exportExcel.htm")
    public synchronized void assessment_exportExcel(HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    String currentPage, Long department_id,
                                                    Long user_id, String award_date,
                                                    String award_name, String award_works,
                                                    String award_level, String award_unit) {

        List<Object[]> list = null;
        OutputStream out = null;
        SXSSFWorkbook workbook = null;
        SXSSFSheet sheet = null;

        try {
            workbook = new SXSSFWorkbook(100);
            sheet = (SXSSFSheet) workbook.createSheet("评优管理");

            String titleName = "评优管理情况";
            String[] titleNames = { "序号", "科组", "姓名", "获奖时间", "奖项名称", "获奖作品", "奖项等级", "颁发单位" };
            POIUtil.setColumnWidth(sheet, titleNames.length);//设置通用列宽

            int all_count = assessmentService.getCountForExportExcel(department_id, user_id,
                award_date, award_name, award_works, award_level, award_unit);

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
                    log.info("【评优管理导出-分页填充】第" + (i + 1) + "次填充开始====>" + CommUtil.getCurrentTime());

                    list = assessmentService.getAssessmentForExportExcel(department_id, user_id,
                        award_date, award_name, award_works, award_level, award_unit, beginIndex,
                        pageSize);

                    rowNum = 2 + i * pageSize;
                    fillSheet(sheet, titleName, titleNames, list, rowNum);
                    log.info("【评优管理导出-分页填充】第" + (i + 1) + "次填充结束====>" + CommUtil.getCurrentTime());
                    list.clear();
                }
                list = null;
            }

            response.setContentType("application/vnd.ms-excel");
            String fileName = "评优管理" + new Date().getTime() + ".xlsx";
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
                    if (1 == index) {
                        cell.setCellValue(obj2RichText(objs[index], "user_ids"));
                    } else if (2 == index) {
                        cell.setCellValue(obj2RichText(objs[index], "datetime"));
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
            if ("user_ids".equals(string)) {
                return new XSSFRichTextString(userService.getUserNames(CommUtil.null2String(obj)));
            } else if ("datetime".equals(string)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                return new XSSFRichTextString(dateFormat.format(obj));
            }
        } catch (Exception e) {
        }
        return new XSSFRichTextString("");
    }

    //=============================================导出Excel区域结束======================================================//

    @SecurityMapping(res_name = "查看", res_url = "/admin/user/assessment_view.htm*", res_group = "评优管理")
    @RequestMapping({ "/admin/user/assessment_view.htm" })
    public ModelAndView assessment_view(HttpServletRequest request, HttpServletResponse response,
                                        String currentPage, Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/user/assessment_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "评优管理标识为空", request, response);
        }
        Assessment assessment = assessmentService.getAssessment(id);
        if (assessment == null) {
            return CommUtil.errorPage(target_url, "找不到评优管理记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/user/assessment_view.html", request, response);

        assessment.setUser_names(userService.getUserNames(assessment.getUser_ids()));
        mv.addObject("obj", assessment);
        mv.addObject("currentPage", currentPage);
        mv.addObject("departments", departmentService.queryAllDepartment());
        mv.addObject("users", userService.queryAllUser());
        return mv;
    }

    /**
     * 添加评优管理
     */
    @SecurityMapping(res_name = "添加", res_url = "/admin/user/assessment_add.htm*", res_group = "评优管理")
    @RequestMapping({ "/admin/user/assessment_add.htm" })
    public ModelAndView assessment_add(HttpServletRequest request, HttpServletResponse response,
                                       String pageSize, String currentPage) {
        ModelAndView mv = new JModelAndView("admin/user/assessment_add.html", request, response);
        mv.addObject("currentPage", currentPage);
        mv.addObject("departments", departmentService.queryAllDepartment());
        mv.addObject("users", userService.queryAllUser());
        return mv;
    }

    /**
     * 添加评优管理保存
     */
    @RequestMapping({ "/admin/user/assessment_add_save.htm" })
    public void assessment_add_save(HttpServletRequest request, HttpServletResponse response,
                                    String currentPage, Long department_id, String user_ids) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "评优管理保存成功");
        WebForm wf = new WebForm();
        Assessment assessment = (Assessment) wf.toPo(request, Assessment.class);
        try {
            assessment.setUser_ids(user_ids);
            rmg = assessmentService.add_save(assessment, department_id);
        } catch (Exception e) {
            e.printStackTrace();
            CommUtil.setResultMsgData(rmg, false, "评优管理保存失败");
        }
        rmg.setData(CommUtil.getURL(request) + "/admin/user/assessment_list.htm?currentPage="
                    + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 评优管理编辑
     */
    @SecurityMapping(res_name = "编辑", res_url = "/admin/user/assessment_edit.htm*", res_group = "评优管理")
    @RequestMapping({ "/admin/user/assessment_edit.htm" })
    public ModelAndView assessment_edit(HttpServletRequest request, HttpServletResponse response,
                                        String pageSize, String currentPage, Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/user/assessment_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "评优管理标识为空", request, response);
        }
        Assessment assessment = assessmentService.getAssessment(id);
        if (assessment == null) {
            return CommUtil.errorPage(target_url, "找不到评优管理记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/user/assessment_edit.html", request, response);
        assessment.setUser_names(userService.getUserNames(assessment.getUser_ids()));
        mv.addObject("obj", assessment);
        mv.addObject("currentPage", currentPage);
        mv.addObject("departments", departmentService.queryAllDepartment());
        mv.addObject("users", userService.queryAllUser());
        return mv;
    }

    /**
     * 评优管理编辑保存
     */
    @RequestMapping({ "/admin/user/assessment_edit_save.htm" })
    public void assessment_edit_save(HttpServletRequest request, HttpServletResponse response,
                                     Long id, String currentPage, Long department_id,
                                     String user_ids) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "评优管理编辑保存成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "评优管理标识为空");
        }
        if (rmg.getResult()) {
            Assessment dbOID = assessmentService.getAssessment(id);
            if (dbOID == null) {
                CommUtil.setResultMsgData(rmg, false, "找不到评优管理记录");
            } else {
                WebForm wf = new WebForm();
                Assessment assessment = (Assessment) wf.toPo(request, dbOID);
                try {
                    assessment.setUser_ids(user_ids);
                    rmg = assessmentService.edit_save(assessment, department_id);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "评优管理编辑保存失败");
                }
            }
        }
        rmg.setData(CommUtil.getURL(request) + "/admin/user/assessment_list.htm?currentPage="
                    + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 评优管理删除
     */
    @SecurityMapping(res_name = "删除", res_url = "/admin/user/assessment_delete.htm*", res_group = "评优管理")
    @RequestMapping({ "/admin/user/assessment_delete.htm" })
    public ModelAndView assessment_delete(HttpServletRequest request, HttpServletResponse response,
                                          Long id, String currentPage) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/user/assessment_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "评优管理标识为空", request, response);
        }
        Assessment assessment = assessmentService.getAssessment(id);
        if (assessment == null) {
            return CommUtil.errorPage(target_url, "找不到评优管理记录", request, response);
        }
        try {
            assessmentService.delAssessment(assessment);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, "评优管理删除失败", request, response);
        }
        return CommUtil.successPage(target_url, "评优管理删除成功", request, response);
    }
}
