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
import com.yuyu.soft.base.controller.BaseController;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.Attachments;
import com.yuyu.soft.entity.EventApplyForm;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IAttachmentsService;
import com.yuyu.soft.service.IDepartmentService;
import com.yuyu.soft.service.IEventApplyFormService;
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
 * 事项申请表管理                       
 * @Filename: EventApplyFormController.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Controller
public class EventApplyFormController {

    protected static final Log     log = LogFactory.getLog(EventApplyFormController.class);
    @Resource
    private IEventApplyFormService eventApplyFormService;
    @Resource
    private IUserService           userService;
    @Resource
    private IDepartmentService     departmentService;
    @Resource
    private IAttachmentsService    attachmentsService;

    /**
     * 事项申请表列表
     */
    @SecurityMapping(res_name = "事项申请表列表", res_url = "/admin/office/event_apply_form_list.htm*", res_group = "事项申请表")
    @RequestMapping({ "/admin/office/event_apply_form_list.htm" })
    public ModelAndView event_apply_form_list(HttpServletRequest request,
                                              HttpServletResponse response, String pageSize,
                                              String currentPage, String apply_date,
                                              Long department_id, Long cost_company_id,
                                              String cost_contract) {
        ModelAndView mv = new JModelAndView("admin/office/event_apply_form_list.html", request,
            response);

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        StringBuilder hql = new StringBuilder();
        hql.append("from EventApplyForm t where t.disabled=false");
        if (CommUtil.isNotNull(apply_date)) {
            hql.append(" and t.apply_date = str_to_date('").append(apply_date)
                .append("','%Y-%m-%d')");
        }
        if (CommUtil.isNotNull(department_id)) {
            hql.append(" and (t.apply_department_ids like '%").append(department_id).append(",%'");
            hql.append(" or t.apply_department_ids like '%,").append(department_id).append("%'");
            hql.append(" or t.apply_department_ids like '").append(department_id).append("')");
        }
        if (CommUtil.isNotNull(cost_company_id)) {
            hql.append(" and t.cost_company_id =:cost_company_id");
            paramsMap.put("cost_company_id", cost_company_id);
        }
        if (CommUtil.isNotNull(cost_contract)) {
            hql.append(" and t.cost_contract like:cost_contract");
            paramsMap.put("cost_contract", "%" + cost_contract + "%");
        }
        hql.append(" order by t.createtime desc");
        List<EventApplyForm> list = eventApplyFormService.queryEventApplyForm(hql.toString(),
            paramsMap, pager);
        operList(list);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);

        mv.addObject("apply_date", apply_date);
        mv.addObject("department_id", department_id);
        mv.addObject("cost_company_id", cost_company_id);
        mv.addObject("cost_contract", cost_contract);
        mv.addObject("departments", departmentService.queryAllDepartment());
        mv.addObject("cost_company_map", Constants.COST_COMPANY_MAP);
        return mv;
    }

    private void operList(List<EventApplyForm> list) {
        if (list != null && list.size() > 0) {
            for (EventApplyForm eaf : list) {
                eaf.setApply_user_names(userService.getUserNames(eaf.getApply_user_ids()));
                eaf.setApply_department_names(departmentService.getDepartmentNames(eaf
                    .getApply_department_ids()));
            }
        }
    }

    //=============================================导出Excel区域开始======================================================//
    @SecurityMapping(res_name = "导出Excel", res_url = "/admin/office/event_apply_form_exportExcel.htm*", res_group = "事项申请表")
    @RequestMapping("/admin/office/event_apply_form_exportExcel.htm")
    public synchronized void event_apply_form_exportExcel(HttpServletRequest request,
                                                          HttpServletResponse response,
                                                          String currentPage, String apply_date,
                                                          String department_id,
                                                          Integer cost_company_id,
                                                          String cost_contract) {

        List<Object[]> list = null;
        OutputStream out = null;
        SXSSFWorkbook workbook = null;
        SXSSFSheet sheet = null;

        try {
            workbook = new SXSSFWorkbook(100);
            sheet = (SXSSFSheet) workbook.createSheet("事项申请表");

            String titleName = "事项申请统计表";
            String[] titleNames = { "序号", "项目名称", "日期", "编号", "项目预算金额", "项目申请人", "申请科组", "负责人",
                    "经办人", "费用支出公司", "费用所属合同", "所属合同金额" };
            POIUtil.setColumnWidth(sheet, titleNames.length);//设置通用列宽
            sheet.setColumnWidth(1, 15 * 512);//设置标题列宽
            sheet.setColumnWidth(5, 15 * 512);//设置标题列宽
            sheet.setColumnWidth(6, 15 * 512);//设置标题列宽

            int all_count = eventApplyFormService.getCountForExportExcel(apply_date, department_id,
                cost_company_id, cost_contract);

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
                    log.info("【事项申请表导出-分页填充】第" + (i + 1) + "次填充开始====>" + CommUtil.getCurrentTime());

                    list = eventApplyFormService.getEventApplyFormForExportExcel(apply_date,
                        department_id, cost_company_id, cost_contract, beginIndex, pageSize);

                    rowNum = 2 + i * pageSize;
                    fillSheet(sheet, titleName, titleNames, list, rowNum);
                    log.info("【事项申请表导出-分页填充】第" + (i + 1) + "次填充结束====>" + CommUtil.getCurrentTime());
                    list.clear();
                }
                list = null;
            }

            response.setContentType("application/vnd.ms-excel");
            String fileName = "事项申请表" + new Date().getTime() + ".xlsx";
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
                        cell.setCellValue(obj2RichText(objs[index], "datetime"));
                    } else if (4 == index) {
                        cell.setCellValue(obj2RichText(objs[index], "user_ids"));
                    } else if (5 == index) {
                        cell.setCellValue(obj2RichText(objs[index], "department_ids"));
                    } else if (8 == index) {
                        cell.setCellValue(obj2RichText(objs[index], "cost_company_id"));
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
            if ("datetime".equals(string)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                return new XSSFRichTextString(dateFormat.format(obj));
            } else if ("user_ids".equals(string)) {
                return new XSSFRichTextString(userService.getUserNames(CommUtil.null2String(obj)));
            } else if ("department_ids".equals(string)) {
                return new XSSFRichTextString(departmentService.getDepartmentNames(CommUtil
                    .null2String(obj)));
            } else if ("cost_company_id".equals(string)) {
                return new XSSFRichTextString(Constants.COST_COMPANY_MAP.get(CommUtil
                    .null2String(obj)));
            }
        } catch (Exception e) {
        }
        return new XSSFRichTextString("");
    }

    //=============================================导出Excel区域结束======================================================//
    @SecurityMapping(res_name = "查看", res_url = "/admin/office/event_apply_form_view.htm*", res_group = "事项申请表")
    @RequestMapping({ "/admin/office/event_apply_form_view.htm" })
    public ModelAndView event_apply_form_view(HttpServletRequest request,
                                              HttpServletResponse response, String currentPage,
                                              Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/office/event_apply_form_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "事项申请表标识为空", request, response);
        }
        EventApplyForm eventApplyForm = eventApplyFormService.getEventApplyForm(id);
        if (eventApplyForm == null) {
            return CommUtil.errorPage(target_url, "找不到事项申请表记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/office/event_apply_form_view.html", request,
            response);

        eventApplyForm.setApply_user_names(userService.getUserNames(eventApplyForm
            .getApply_user_ids()));
        eventApplyForm.setApply_department_names(departmentService
            .getDepartmentNames(eventApplyForm.getApply_department_ids()));
        mv.addObject("obj", eventApplyForm);
        mv.addObject("currentPage", currentPage);
        mv.addObject("departments", departmentService.queryAllDepartment());
        mv.addObject("users", userService.queryAllUser());
        mv.addObject("attachments",
            attachmentsService.getAttachmentsByIds(eventApplyForm.getAttach_ids()));
        mv.addObject("cost_company_map", Constants.COST_COMPANY_MAP);
        return mv;
    }

    @RequestMapping({ "/admin/office/eafUpload.htm" })
    public void upload(HttpServletRequest request, HttpServletResponse response) {
        List<Attachments> list = null;
        try {
            list = BaseController.uploadify(request, response, "event_apply_form",
                attachmentsService);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list == null || list.size() == 0) {
            return;
        }
        System.out.println(BaseController.write2JsonStr(request, list));
        JsonUtil.writeMsg(response, BaseController.write2JsonStr(request, list));
    }

    /**
     * 添加事项申请表
     */
    @SecurityMapping(res_name = "添加", res_url = "/admin/office/event_apply_form_add.htm*", res_group = "事项申请表")
    @RequestMapping({ "/admin/office/event_apply_form_add.htm" })
    public ModelAndView event_apply_form_add(HttpServletRequest request,
                                             HttpServletResponse response, String pageSize,
                                             String currentPage) {
        ModelAndView mv = new JModelAndView("admin/office/event_apply_form_add.html", request,
            response);
        mv.addObject("currentPage", currentPage);
        mv.addObject("departments", departmentService.queryAllDepartment());
        mv.addObject("users", userService.queryAllUser());
        mv.addObject("cost_company_map", Constants.COST_COMPANY_MAP);
        return mv;
    }

    /**
     * 添加事项申请表保存
     */
    @RequestMapping({ "/admin/office/event_apply_form_add_save.htm" })
    public void event_apply_form_add_save(HttpServletRequest request, HttpServletResponse response,
                                          String currentPage, Long leader_user_id,
                                          String apply_department_ids, String attachments_ids) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "事项申请表保存成功");
        WebForm wf = new WebForm();
        EventApplyForm eventApplyForm = (EventApplyForm) wf.toPo(request, EventApplyForm.class);
        User user = BaseController.getCurrentUser(request);
        try {
            eventApplyForm.setAttach_ids(attachments_ids);
            eventApplyForm.setApply_department_ids(apply_department_ids);
            rmg = eventApplyFormService.add_save(eventApplyForm, user.getId(), leader_user_id);
        } catch (Exception e) {
            e.printStackTrace();
            CommUtil.setResultMsgData(rmg, false, "事项申请表保存失败");
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/office/event_apply_form_list.htm?currentPage=" + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 事项申请表编辑
     */
    @SecurityMapping(res_name = "编辑", res_url = "/admin/office/event_apply_form_edit.htm*", res_group = "事项申请表")
    @RequestMapping({ "/admin/office/event_apply_form_edit.htm" })
    public ModelAndView event_apply_form_edit(HttpServletRequest request,
                                              HttpServletResponse response, String pageSize,
                                              String currentPage, Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/office/event_apply_form_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "事项申请表标识为空", request, response);
        }
        EventApplyForm eventApplyForm = eventApplyFormService.getEventApplyForm(id);
        if (eventApplyForm == null) {
            return CommUtil.errorPage(target_url, "找不到事项申请表记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/office/event_apply_form_edit.html", request,
            response);
        eventApplyForm.setApply_user_names(userService.getUserNames(eventApplyForm
            .getApply_user_ids()));
        mv.addObject("obj", eventApplyForm);
        mv.addObject("currentPage", currentPage);
        mv.addObject("departments", departmentService.queryAllDepartment());
        mv.addObject("users", userService.queryAllUser());
        mv.addObject("cost_company_map", Constants.COST_COMPANY_MAP);
        mv.addObject("attachments",
            attachmentsService.getAttachmentsByIds(eventApplyForm.getAttach_ids()));
        return mv;
    }

    /**
     * 事项申请表编辑保存
     */
    @RequestMapping({ "/admin/office/event_apply_form_edit_save.htm" })
    public void event_apply_form_edit_save(HttpServletRequest request,
                                           HttpServletResponse response, Long id,
                                           String currentPage, Long leader_user_id,
                                           String apply_department_ids, String attachments_ids) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "事项申请表编辑保存成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "事项申请表标识为空");
        }
        if (rmg.getResult()) {
            EventApplyForm dbOID = eventApplyFormService.getEventApplyForm(id);
            if (dbOID == null) {
                CommUtil.setResultMsgData(rmg, false, "找不到事项申请表记录");
            } else {
                WebForm wf = new WebForm();
                EventApplyForm eventApplyForm = (EventApplyForm) wf.toPo(request, dbOID);
                try {
                    eventApplyForm.setAttach_ids(attachments_ids);
                    eventApplyForm.setApply_department_ids(apply_department_ids);
                    User user = BaseController.getCurrentUser(request);
                    rmg = eventApplyFormService.edit_save(eventApplyForm, user.getId(),
                        leader_user_id);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "事项申请表编辑保存失败");
                }
            }
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/office/event_apply_form_list.htm?currentPage=" + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 事项申请表删除
     */
    @SecurityMapping(res_name = "删除", res_url = "/admin/office/event_apply_form_delete.htm*", res_group = "事项申请表")
    @RequestMapping({ "/admin/office/event_apply_form_delete.htm" })
    public ModelAndView event_apply_form_delete(HttpServletRequest request,
                                                HttpServletResponse response, Long id,
                                                String currentPage) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/office/event_apply_form_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "事项申请表标识为空", request, response);
        }
        EventApplyForm eventApplyForm = eventApplyFormService.getEventApplyForm(id);
        if (eventApplyForm == null) {
            return CommUtil.errorPage(target_url, "找不到事项申请表记录", request, response);
        }
        try {
            eventApplyFormService.delEventApplyForm(eventApplyForm);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, "事项申请表删除失败", request, response);
        }
        return CommUtil.successPage(target_url, "事项申请表删除成功", request, response);
    }
}
