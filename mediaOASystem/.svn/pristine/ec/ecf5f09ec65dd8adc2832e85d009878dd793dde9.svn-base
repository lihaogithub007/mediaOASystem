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
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuyu.soft.base.annotation.SecurityMapping;
import com.yuyu.soft.base.controller.BaseController;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.ProjectCooperation;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IProjectCooperationService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.POIUtil;
import com.yuyu.soft.util.PageUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

/**
 * 协同记录设置
 * @Filename: ProjectCooperation.java
 * @Version: 1.0
 *
 */
@Controller
public class ProjectCooperationController extends BaseController {

    protected Logger                   log = LoggerFactory
                                               .getLogger(ProjectCooperationController.class);

    @Resource
    private IProjectCooperationService projectCooperationService;

    /**
     * 协同记录列表
     */
    @SecurityMapping(res_name = "协同记录列表", res_url = "/admin/projectcoo/cooperation_list.htm*", res_group = "协同记录")
    @RequestMapping({ "/admin/projectcoo/cooperation_list.htm" })
    public ModelAndView equipment_list(HttpServletRequest request, HttpServletResponse response,
                                       String pageSize, String currentPage, String project_name,
                                       String initiator_name, String project_date) {
        ModelAndView mv = new JModelAndView("admin/projectcoo/cooperation_list.html", request,
            response);

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        StringBuilder hql = new StringBuilder();
        hql.append("from ProjectCooperation t where t.disabled = false");

        if (CommUtil.isNotNull(project_name)) {
            hql.append(" and t.project_name like :project_name ");
            paramsMap.put("project_name", "%" + project_name.trim() + "%");
        }
        if (CommUtil.isNotNull(initiator_name)) {
            hql.append(" and t.initiator_name like :initiator_name ");
            paramsMap.put("initiator_name", "%" + initiator_name.trim() + "%");
        }
        if (CommUtil.isNotNull(project_date)) {
            hql.append(" and t.project_date = str_to_date('").append(project_date)
                .append("','%Y-%m-%d')");
        }
        hql.append(" order by t.createtime desc");

        List<ProjectCooperation> list = projectCooperationService.queryProjectCooperationList(
            hql.toString(), paramsMap, pager);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);

        mv.addObject("project_name", project_name);
        mv.addObject("initiator_name", initiator_name);
        mv.addObject("project_date", project_date);

        return mv;
    }

    /**
     * 协同记录详情
     */
    @SecurityMapping(res_name = "查看", res_url = "/admin/projectcoo/cooperation_view.htm*", res_group = "协同记录")
    @RequestMapping({ "/admin/projectcoo/cooperation_view.htm" })
    public ModelAndView project_cooperation_list(HttpServletRequest request,
                                                 HttpServletResponse response, String currentPage,
                                                 Long id) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/projectcoo/cooperation_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "项目协同记录标识为空", request, response);
        }
        ProjectCooperation projectCooperation = projectCooperationService.getProjectCooperation(id);
        if (projectCooperation == null) {
            return CommUtil.errorPage(target_url, "找不到项目协同记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/projectcoo/cooperation_view.html", request,
            response);

        mv.addObject("obj", projectCooperation);
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    /**
     * 添加协同记录
     */
    @SecurityMapping(res_name = "添加", res_url = "/admin/projectcoo/cooperation_add.htm*", res_group = "协同记录")
    @RequestMapping({ "/admin/projectcoo/cooperation_add.htm" })
    public ModelAndView project_cooperation_add(HttpServletRequest request,
                                                HttpServletResponse response, String pageSize,
                                                String currentPage) {
        ModelAndView mv = new JModelAndView("/admin/projectcoo/cooperation_add.html", request,
            response);
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    /**
     * 添加协同记录(保存添加信息)
     */
    @RequestMapping({ "/admin/projectcoo/cooperation_add_save.htm" })
    public void project_cooperation_add_save(HttpServletRequest request,
                                             HttpServletResponse response, String pageSize,
                                             String currentPage, Long id) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "协同记录保存成功");
        WebForm wf = new WebForm();
        ProjectCooperation projectCooperation = (ProjectCooperation) wf.toPo(request,
            ProjectCooperation.class);
        User user = BaseController.getCurrentUser(request);
        try {
            rmg = projectCooperationService.add_save(projectCooperation);
        } catch (Exception e) {
            e.printStackTrace();
            CommUtil.setResultMsgData(rmg, false, "协同记录保存失败");
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/projectcoo/cooperation_list.htm?currentPage=" + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 删除协同记录
     */
    @SecurityMapping(res_name = "删除", res_url = "/admin/projectcoo/cooperation_delete.htm*", res_group = "协同记录")
    @RequestMapping({ "/admin/projectcoo/cooperation_delete.htm" })
    public ModelAndView project_cooperation_delete(HttpServletRequest request,
                                                   HttpServletResponse response, String pageSize,
                                                   String currentPage, Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/projectcoo/cooperation_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "协同记录标识为空", request, response);
        }
        ProjectCooperation projectCooperation = projectCooperationService.getProjectCooperation(id);
        if (projectCooperation == null) {
            return CommUtil.errorPage(target_url, "找不到协同记录", request, response);
        }
        try {
            projectCooperationService.deleteProjectCooperation(projectCooperation);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, "协同记录删除失败", request, response);
        }
        return CommUtil.successPage(target_url, "协同记录删除成功", request, response);

    }

    /**
     * 编辑协同记录
     */
    @SecurityMapping(res_name = "编辑", res_url = "/admin/projectcoo/cooperation_edit.htm*", res_group = "协同记录")
    @RequestMapping({ "/admin/projectcoo/cooperation_edit.htm" })
    public ModelAndView equipment_edit(HttpServletRequest request, HttpServletResponse response,
                                       String pageSize, String currentPage, Long id) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/projectcoo/cooperation_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "协同记录标识为空", request, response);
        }
        ProjectCooperation projectCooperation = projectCooperationService.getProjectCooperation(id);
        if (projectCooperation == null) {
            return CommUtil.errorPage(target_url, "找不到协同记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/projectcoo/cooperation_edit.html", request,
            response);

        mv.addObject("obj", projectCooperation);
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    /**
     * 编辑设备(保存编辑信息)
     */
    @RequestMapping({ "/admin/projectcoo/cooperation_edit_save.htm" })
    public void equipment_edit_save(HttpServletRequest request, HttpServletResponse response,
                                    String pageSize, String currentPage, Long id) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "协同记录编辑保存成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "协同记录标识为空");
        }
        if (rmg.getResult()) {
            ProjectCooperation pc = projectCooperationService.getProjectCooperation(id);
            if (pc == null) {
                CommUtil.setResultMsgData(rmg, false, "找不到协同记录");
            } else {
                WebForm wf = new WebForm();
                ProjectCooperation projectCooperation = (ProjectCooperation) wf.toPo(request, pc);
                try {
                    rmg = projectCooperationService.edit_save(projectCooperation);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "协同记录编辑保存失败");
                }
            }
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/projectcoo/cooperation_list.htm?currentPage=" + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));

    }

    //=============================================导出Excel区域开始======================================================//
    @SecurityMapping(res_name = "导出Excel", res_url = "/admin/projectcoo/cooperation_exportExcel.htm*", res_group = "协同记录")
    @RequestMapping("/admin/projectcoo/cooperation_exportExcel.htm")
    public synchronized void inside_income_dispatches_exportExcel(HttpServletRequest request,
                                                                  HttpServletResponse response,
                                                                  String currentPage,
                                                                  Long dispatch_unit_id,
                                                                  String print_and_dispatch_time,
                                                                  String project_name,
                                                                  String initiator_name,
                                                                  String project_date) {

        List<Object[]> list = null;
        OutputStream out = null;
        SXSSFWorkbook workbook = null;
        SXSSFSheet sheet = null;

        try {
            workbook = new SXSSFWorkbook(100);
            sheet = (SXSSFSheet) workbook.createSheet("台内收文");

            String titleName = "协同记录表";
            String[] titleNames = { "序号", "日期", "项目名称", "发起人", "合作方", "结果", "备注" };
            POIUtil.setColumnWidth(sheet, titleNames.length);//设置通用列宽
            sheet.setColumnWidth(4, 20 * 512);//设置标题列宽

            int all_count = projectCooperationService.getCountForExportExcel(project_name,
                initiator_name, project_date);

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
                    log.info("【协同记录导出-分页填充】第" + (i + 1) + "次填充开始====>" + CommUtil.getCurrentTime());

                    list = projectCooperationService.getProjectCooperationsForExportExcel(
                        project_name, initiator_name, project_date, beginIndex, pageSize);

                    rowNum = 2 + i * pageSize;
                    fillSheet(sheet, titleName, titleNames, list, rowNum);
                    log.info("【协同记录导出-分页填充】第" + (i + 1) + "次填充结束====>" + CommUtil.getCurrentTime());
                    list.clear();
                }
                list = null;
            }

            response.setContentType("application/vnd.ms-excel");
            String fileName = "协同记录" + new Date().getTime() + ".xlsx";
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
