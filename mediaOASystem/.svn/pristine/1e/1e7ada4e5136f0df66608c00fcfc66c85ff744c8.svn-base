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
import com.yuyu.soft.entity.DepartmentAwards;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IDepartmentAwardsService;
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
 * 部门评奖管理                       
 * @Filename: DepartmentAwardsController.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Controller
public class DepartmentAwardsController {

    protected static final Log       log = LogFactory.getLog(DepartmentAwardsController.class);
    @Resource
    private IDepartmentAwardsService departmentAwardsService;
    @Resource
    private IUserService             userService;
    @Resource
    private IDepartmentService       departmentService;

    /**
     * 部门评奖列表
     */
    @SecurityMapping(res_name = "部门评奖列表", res_url = "/admin/office/department_awards_list.htm*", res_group = "部门评奖")
    @RequestMapping({ "/admin/office/department_awards_list.htm" })
    public ModelAndView department_awards_list(HttpServletRequest request,
                                               HttpServletResponse response, String pageSize,
                                               String currentPage, String awards_time, Long user_id) {
        ModelAndView mv = new JModelAndView("admin/office/department_awards_list.html", request,
            response);

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        StringBuilder hql = new StringBuilder();
        hql.append("from DepartmentAwards t where t.disabled=false");
        if (CommUtil.isNotNull(awards_time)) {
            hql.append(" and t.awards_time = str_to_date('").append(awards_time)
                .append("','%Y-%m-%d')");
        }
        if (CommUtil.isNotNull(user_id)) {
            hql.append(" and (t.awards_user_ids like '%").append(user_id).append(",%'");
            hql.append(" or t.awards_user_ids like '%,").append(user_id).append("%'");
            hql.append(" or t.awards_user_ids like '").append(user_id).append("')");
        }
        hql.append(" order by t.createtime desc");
        List<DepartmentAwards> list = departmentAwardsService.queryDepartmentAwards(hql.toString(),
            paramsMap, pager);
        operList(list);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);

        mv.addObject("awards_time", awards_time);
        mv.addObject("user_id", user_id);
        mv.addObject("departments", departmentService.queryAllDepartment());
        mv.addObject("users", userService.queryAllUser());
        return mv;
    }

    private void operList(List<DepartmentAwards> list) {
        if (list != null && list.size() > 0) {
            for (DepartmentAwards da : list) {
                da.setAwards_user_names(userService.getUserNames(da.getAwards_user_ids()));
                da.setAwards_department_names(departmentService.getDepartmentNames(da
                    .getAwards_department_ids()));
            }
        }
    }

    //=============================================导出Excel区域开始======================================================//

    @SecurityMapping(res_name = "导出Excel", res_url = "/admin/office/department_awards_exportExcel.htm*", res_group = "部门评奖")
    @RequestMapping("/admin/office/department_awards_exportExcel.htm")
    public synchronized void department_awards_exportExcel(HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           String currentPage, String awards_time,
                                                           Long user_id) {

        List<Object[]> list = null;
        OutputStream out = null;
        SXSSFWorkbook workbook = null;
        SXSSFSheet sheet = null;

        try {
            workbook = new SXSSFWorkbook(100);
            sheet = (SXSSFSheet) workbook.createSheet("部门评奖");

            String titleName = "部门评奖情况";
            String[] titleNames = { "序号", "参评时间", "奖项名称", "参评作品", "参评项目负责人", "参加科组", "参评人员",
                    "评奖结果", "奖金", "备注" };
            POIUtil.setColumnWidth(sheet, titleNames.length);//设置通用列宽
            sheet.setColumnWidth(2, 12 * 512);//设置标题列宽
            sheet.setColumnWidth(3, 12 * 512);//设置标题列宽
            sheet.setColumnWidth(5, 12 * 512);//设置标题列宽
            sheet.setColumnWidth(6, 12 * 512);//设置标题列宽

            int all_count = departmentAwardsService.getCountForExportExcel(awards_time, user_id);

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
                    log.info("【部门评奖导出-分页填充】第" + (i + 1) + "次填充开始====>" + CommUtil.getCurrentTime());

                    list = departmentAwardsService.getDepartmentAwardsForExportExcel(awards_time,
                        user_id, beginIndex, pageSize);

                    rowNum = 2 + i * pageSize;
                    fillSheet(sheet, titleName, titleNames, list, rowNum);
                    log.info("【部门评奖导出-分页填充】第" + (i + 1) + "次填充结束====>" + CommUtil.getCurrentTime());
                    list.clear();
                }
                list = null;
            }

            response.setContentType("application/vnd.ms-excel");
            String fileName = "部门评奖" + new Date().getTime() + ".xlsx";
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
                    if (0 == index) {
                        cell.setCellValue(obj2RichText(objs[index], "datetime"));
                    } else if (4 == index) {
                        cell.setCellValue(obj2RichText(objs[index], "department_ids"));
                    } else if (5 == index) {
                        cell.setCellValue(obj2RichText(objs[index], "user_ids"));
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
            }
        } catch (Exception e) {
        }
        return new XSSFRichTextString("");
    }

    //=============================================导出Excel区域结束======================================================//

    @SecurityMapping(res_name = "查看", res_url = "/admin/office/department_awards_view.htm*", res_group = "部门评奖")
    @RequestMapping({ "/admin/office/department_awards_view.htm" })
    public ModelAndView department_awards_view(HttpServletRequest request,
                                               HttpServletResponse response, String currentPage,
                                               Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/office/department_awards_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "部门评奖标识为空", request, response);
        }
        DepartmentAwards departmentAwards = departmentAwardsService.getDepartmentAwards(id);
        if (departmentAwards == null) {
            return CommUtil.errorPage(target_url, "找不到部门评奖记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/office/department_awards_view.html", request,
            response);

        departmentAwards.setAwards_user_names(userService.getUserNames(departmentAwards
            .getAwards_user_ids()));
        departmentAwards.setAwards_department_names(departmentService
            .getDepartmentNames(departmentAwards.getAwards_department_ids()));
        mv.addObject("obj", departmentAwards);
        mv.addObject("currentPage", currentPage);
        mv.addObject("departments", departmentService.queryAllDepartment());
        mv.addObject("users", userService.queryAllUser());
        return mv;
    }

    /**
     * 添加部门评奖
     */
    @SecurityMapping(res_name = "添加", res_url = "/admin/office/department_awards_add.htm*", res_group = "部门评奖")
    @RequestMapping({ "/admin/office/department_awards_add.htm" })
    public ModelAndView department_awards_add(HttpServletRequest request,
                                              HttpServletResponse response, String pageSize,
                                              String currentPage) {
        ModelAndView mv = new JModelAndView("admin/office/department_awards_add.html", request,
            response);
        mv.addObject("currentPage", currentPage);
        mv.addObject("departments", departmentService.queryAllDepartment());
        mv.addObject("users", userService.queryAllUser());
        return mv;
    }

    /**
     * 添加部门评奖保存
     */
    @RequestMapping({ "/admin/office/department_awards_add_save.htm" })
    public void department_awards_add_save(HttpServletRequest request,
                                           HttpServletResponse response, String currentPage,
                                           Long awards_leader_user_id, String awards_department_ids) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "部门评奖保存成功");
        WebForm wf = new WebForm();
        DepartmentAwards departmentAwards = (DepartmentAwards) wf.toPo(request,
            DepartmentAwards.class);
        User user = BaseController.getCurrentUser(request);
        try {
            departmentAwards.setAwards_department_ids(awards_department_ids);
            rmg = departmentAwardsService.add_save(departmentAwards, user.getId(),
                awards_leader_user_id);
        } catch (Exception e) {
            e.printStackTrace();
            CommUtil.setResultMsgData(rmg, false, "部门评奖保存失败");
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/office/department_awards_list.htm?currentPage=" + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 部门评奖编辑
     */
    @SecurityMapping(res_name = "编辑", res_url = "/admin/office/department_awards_edit.htm*", res_group = "部门评奖")
    @RequestMapping({ "/admin/office/department_awards_edit.htm" })
    public ModelAndView department_awards_edit(HttpServletRequest request,
                                               HttpServletResponse response, String pageSize,
                                               String currentPage, Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/office/department_awards_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "部门评奖标识为空", request, response);
        }
        DepartmentAwards departmentAwards = departmentAwardsService.getDepartmentAwards(id);
        if (departmentAwards == null) {
            return CommUtil.errorPage(target_url, "找不到部门评奖记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/office/department_awards_edit.html", request,
            response);
        departmentAwards.setAwards_user_names(userService.getUserNames(departmentAwards
            .getAwards_user_ids()));
        mv.addObject("obj", departmentAwards);
        mv.addObject("currentPage", currentPage);
        mv.addObject("departments", departmentService.queryAllDepartment());
        mv.addObject("users", userService.queryAllUser());
        return mv;
    }

    /**
     * 部门评奖编辑保存
     */
    @RequestMapping({ "/admin/office/department_awards_edit_save.htm" })
    public void department_awards_edit_save(HttpServletRequest request,
                                            HttpServletResponse response, Long id,
                                            String currentPage, Long awards_leader_user_id,
                                            String awards_department_ids) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "部门评奖编辑保存成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "部门评奖标识为空");
        }
        if (rmg.getResult()) {
            DepartmentAwards dbOID = departmentAwardsService.getDepartmentAwards(id);
            if (dbOID == null) {
                CommUtil.setResultMsgData(rmg, false, "找不到部门评奖记录");
            } else {
                WebForm wf = new WebForm();
                DepartmentAwards departmentAwards = (DepartmentAwards) wf.toPo(request, dbOID);
                try {
                    departmentAwards.setAwards_department_ids(awards_department_ids);
                    User user = BaseController.getCurrentUser(request);
                    rmg = departmentAwardsService.edit_save(departmentAwards, user.getId(),
                        awards_leader_user_id);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "部门评奖编辑保存失败");
                }
            }
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/office/department_awards_list.htm?currentPage=" + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 部门评奖删除
     */
    @SecurityMapping(res_name = "删除", res_url = "/admin/office/department_awards_delete.htm*", res_group = "部门评奖")
    @RequestMapping({ "/admin/office/department_awards_delete.htm" })
    public ModelAndView department_awards_delete(HttpServletRequest request,
                                                 HttpServletResponse response, Long id,
                                                 String currentPage) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/office/department_awards_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "部门评奖标识为空", request, response);
        }
        DepartmentAwards departmentAwards = departmentAwardsService.getDepartmentAwards(id);
        if (departmentAwards == null) {
            return CommUtil.errorPage(target_url, "找不到部门评奖记录", request, response);
        }
        try {
            departmentAwardsService.delDepartmentAwards(departmentAwards);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, "部门评奖删除失败", request, response);
        }
        return CommUtil.successPage(target_url, "部门评奖删除成功", request, response);
    }
}
