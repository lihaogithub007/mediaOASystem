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
import com.yuyu.soft.entity.OutsideIncomeDispatches;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IAttachmentsService;
import com.yuyu.soft.service.IOutsideIncomeDispatchesService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.POIUtil;
import com.yuyu.soft.util.PageUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

/**
 * 台外收文管理                       
 * @Filename: OutsideIncomeDispatchesController.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Controller
public class OutsideIncomeDispatchesController {

    protected static final Log              log = LogFactory
                                                    .getLog(OutsideIncomeDispatchesController.class);
    @Resource
    private IOutsideIncomeDispatchesService outsideIncomeDispatchesService;
    @Resource
    private IAttachmentsService             attachmentsService;

    /**
     * 台外收文列表
     */
    @SecurityMapping(res_name = "台外收文列表", res_url = "/admin/office/outside_income_dispatches_list.htm*", res_group = "台外收文管理")
    @RequestMapping({ "/admin/office/outside_income_dispatches_list.htm" })
    public ModelAndView outside_income_dispatches_list(HttpServletRequest request,
                                                       HttpServletResponse response,
                                                       String pageSize, String currentPage,
                                                       String dispatch_unit_name,
                                                       String income_dispatches_time) {
        ModelAndView mv = new JModelAndView("admin/office/outside_income_dispatches_list.html",
            request, response);

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        StringBuilder hql = new StringBuilder();
        hql.append("from OutsideIncomeDispatches t where t.disabled=false");
        if (CommUtil.isNotNull(dispatch_unit_name)) {
            hql.append(" and t.dispatch_unit_name like :dispatch_unit_name");
            paramsMap.put("dispatch_unit_name", "%" + dispatch_unit_name.trim() + "%");
        }
        if (CommUtil.isNotNull(income_dispatches_time)) {
            hql.append(" and t.income_dispatches_time = str_to_date('")
                .append(income_dispatches_time).append("','%Y-%m-%d')");
        }
        hql.append(" order by t.createtime desc");
        List<OutsideIncomeDispatches> list = outsideIncomeDispatchesService
            .queryOutsideIncomeDispatches(hql.toString(), paramsMap, pager);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);

        mv.addObject("dispatch_unit_name", dispatch_unit_name);
        mv.addObject("income_dispatches_time", income_dispatches_time);
        return mv;
    }

    //=============================================导出Excel区域开始======================================================//
    @SecurityMapping(res_name = "导出Excel", res_url = "/admin/office/outside_income_dispatches_exportExcel.htm*", res_group = "台外收文管理")
    @RequestMapping("/admin/office/outside_income_dispatches_exportExcel.htm")
    public synchronized void outside_income_dispatches_exportExcel(HttpServletRequest request,
                                                                   HttpServletResponse response,
                                                                   String currentPage,
                                                                   String dispatch_unit_name,
                                                                   String income_dispatches_time) {

        List<Object[]> list = null;
        OutputStream out = null;
        SXSSFWorkbook workbook = null;
        SXSSFSheet sheet = null;

        try {
            workbook = new SXSSFWorkbook(100);
            sheet = (SXSSFSheet) workbook.createSheet("台外收文");

            String titleName = "台外发文件统计表";
            String[] titleNames = { "序号", "发文单位", "年份和文号", "收文日期", "印发日期", "标题", "备注" };
            POIUtil.setColumnWidth(sheet, titleNames.length);//设置通用列宽
            sheet.setColumnWidth(5, 20 * 512);//设置标题列宽

            int all_count = outsideIncomeDispatchesService.getCountForExportExcel(
                dispatch_unit_name, income_dispatches_time);

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
                    log.info("【台外收文导出-分页填充】第" + (i + 1) + "次填充开始====>" + CommUtil.getCurrentTime());

                    list = outsideIncomeDispatchesService.getOutsideIncomeDispatchesForExportExcel(
                        dispatch_unit_name, income_dispatches_time, beginIndex, pageSize);

                    rowNum = 2 + i * pageSize;
                    fillSheet(sheet, titleName, titleNames, list, rowNum);
                    log.info("【台外收文导出-分页填充】第" + (i + 1) + "次填充结束====>" + CommUtil.getCurrentTime());
                    list.clear();
                }
                list = null;
            }

            response.setContentType("application/vnd.ms-excel");
            String fileName = "台外收文" + new Date().getTime() + ".xlsx";
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
                    if (2 == index || 3 == index) {
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
            if ("datetime".equals(string)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                return new XSSFRichTextString(dateFormat.format(obj));
            }
        } catch (Exception e) {
        }
        return new XSSFRichTextString("");
    }

    //=============================================导出Excel区域结束======================================================//
    @SecurityMapping(res_name = "查看", res_url = "/admin/office/outside_income_dispatches_view.htm*", res_group = "台外收文管理")
    @RequestMapping({ "/admin/office/outside_income_dispatches_view.htm" })
    public ModelAndView outside_income_dispatches_view(HttpServletRequest request,
                                                       HttpServletResponse response,
                                                       String currentPage, Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/office/outside_income_dispatches_list.htm?currentPage="
                            + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "台外收文标识为空", request, response);
        }
        OutsideIncomeDispatches outsideIncomeDispatches = outsideIncomeDispatchesService
            .getOutsideIncomeDispatches(id);
        if (outsideIncomeDispatches == null) {
            return CommUtil.errorPage(target_url, "找不到台外收文记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/office/outside_income_dispatches_view.html",
            request, response);
        mv.addObject("obj", outsideIncomeDispatches);
        mv.addObject("attachments",
            attachmentsService.getAttachmentsByIds(outsideIncomeDispatches.getAttach_ids()));
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    @RequestMapping({ "/admin/office/oidUpload.htm" })
    public void upload(HttpServletRequest request, HttpServletResponse response) {
        List<Attachments> list = null;
        try {
            list = BaseController.uploadify(request, response, "outside_income_dispatches",
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
     * 添加台外收文
     */
    @SecurityMapping(res_name = "添加", res_url = "/admin/office/outside_income_dispatches_add.htm*", res_group = "台外收文管理")
    @RequestMapping({ "/admin/office/outside_income_dispatches_add.htm" })
    public ModelAndView outside_income_dispatches_add(HttpServletRequest request,
                                                      HttpServletResponse response,
                                                      String pageSize, String currentPage) {
        ModelAndView mv = new JModelAndView("admin/office/outside_income_dispatches_add.html",
            request, response);
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    /**
     * 添加台外收文保存
     */
    @RequestMapping({ "/admin/office/outside_income_dispatches_add_save.htm" })
    public void outside_income_dispatches_add_save(HttpServletRequest request,
                                                   HttpServletResponse response,
                                                   String currentPage, String dispatch_unit_name,
                                                   String attachments_ids) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "台外收文保存成功");
        WebForm wf = new WebForm();
        OutsideIncomeDispatches outsideIncomeDispatches = (OutsideIncomeDispatches) wf.toPo(
            request, OutsideIncomeDispatches.class);
        User user = BaseController.getCurrentUser(request);
        try {
            outsideIncomeDispatches.setAttach_ids(attachments_ids);
            rmg = outsideIncomeDispatchesService.add_save(outsideIncomeDispatches, user.getId(),
                dispatch_unit_name);
        } catch (Exception e) {
            e.printStackTrace();
            CommUtil.setResultMsgData(rmg, false, "台外收文保存失败");
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/office/outside_income_dispatches_list.htm?currentPage=" + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 台外收文编辑
     */
    @SecurityMapping(res_name = "编辑", res_url = "/admin/office/outside_income_dispatches_edit.htm*", res_group = "台外收文管理")
    @RequestMapping({ "/admin/office/outside_income_dispatches_edit.htm" })
    public ModelAndView outside_income_dispatches_edit(HttpServletRequest request,
                                                       HttpServletResponse response,
                                                       String pageSize, String currentPage, Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/office/outside_income_dispatches_list.htm?currentPage="
                            + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "台外收文标识为空", request, response);
        }
        OutsideIncomeDispatches outsideIncomeDispatches = outsideIncomeDispatchesService
            .getOutsideIncomeDispatches(id);
        if (outsideIncomeDispatches == null) {
            return CommUtil.errorPage(target_url, "找不到台外收文记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/office/outside_income_dispatches_edit.html",
            request, response);

        mv.addObject("obj", outsideIncomeDispatches);
        mv.addObject("attachments",
            attachmentsService.getAttachmentsByIds(outsideIncomeDispatches.getAttach_ids()));
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    /**
     * 台外收文编辑保存
     */
    @RequestMapping({ "/admin/office/outside_income_dispatches_edit_save.htm" })
    public void outside_income_dispatches_edit_save(HttpServletRequest request,
                                                    HttpServletResponse response, Long id,
                                                    String currentPage, String dispatch_unit_name,
                                                    String attachments_ids) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "台外收文编辑保存成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "台外收文标识为空");
        }
        if (rmg.getResult()) {
            OutsideIncomeDispatches dbOID = outsideIncomeDispatchesService
                .getOutsideIncomeDispatches(id);
            if (dbOID == null) {
                CommUtil.setResultMsgData(rmg, false, "找不到台外收文记录");
            } else {
                WebForm wf = new WebForm();
                OutsideIncomeDispatches outsideIncomeDispatches = (OutsideIncomeDispatches) wf
                    .toPo(request, dbOID);
                try {
                    outsideIncomeDispatches.setAttach_ids(attachments_ids);
                    User user = BaseController.getCurrentUser(request);
                    rmg = outsideIncomeDispatchesService.edit_save(outsideIncomeDispatches,
                        user.getId(), dispatch_unit_name);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "台外收文编辑保存失败");
                }
            }
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/office/outside_income_dispatches_list.htm?currentPage=" + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 台外收文删除
     */
    @SecurityMapping(res_name = "删除", res_url = "/admin/office/outside_income_dispatches_delete.htm*", res_group = "台外收文管理")
    @RequestMapping({ "/admin/office/outside_income_dispatches_delete.htm" })
    public ModelAndView outside_income_dispatches_delete(HttpServletRequest request,
                                                         HttpServletResponse response, Long id,
                                                         String currentPage) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/office/outside_income_dispatches_list.htm?currentPage="
                            + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "台外收文标识为空", request, response);
        }
        OutsideIncomeDispatches outsideIncomeDispatches = outsideIncomeDispatchesService
            .getOutsideIncomeDispatches(id);
        if (outsideIncomeDispatches == null) {
            return CommUtil.errorPage(target_url, "找不到台外收文记录", request, response);
        }
        try {
            outsideIncomeDispatchesService.delOutsideIncomeDispatches(outsideIncomeDispatches);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, "台外收文删除失败", request, response);
        }
        return CommUtil.successPage(target_url, "台外收文删除成功", request, response);
    }
}
