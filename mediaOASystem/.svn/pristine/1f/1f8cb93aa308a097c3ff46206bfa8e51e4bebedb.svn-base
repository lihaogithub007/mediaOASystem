package com.yuyu.soft.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuyu.soft.base.annotation.SecurityMapping;
import com.yuyu.soft.base.controller.BaseController;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.Equipment;
import com.yuyu.soft.entity.FixedAssets;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IEquipmentService;
import com.yuyu.soft.service.IFixedAssetsService;
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
 * 固定资产管理                      
 * @Filename: FixedAssetsController.java
 * @Version: 1.0
 * @Author: lihao
 */
@Controller
public class FixedAssetsController extends BaseController {

    @Resource
    private IFixedAssetsService fixedAssetsService;

    @Resource
    private IEquipmentService   equipmentService;

    @Resource
    private IUserService        userService;

    /**
     * 固定资产列表
     */
    @SecurityMapping(res_name = "固定资产列表", res_url = "/admin/office/fixed_assets_list.htm*", res_group = "固定资产管理")
    @RequestMapping({ "/admin/office/fixed_assets_list.htm" })
    public ModelAndView fixed_assets_list(HttpServletRequest request, HttpServletResponse response,
                                          String pageSize, String currentPage,
                                          String equipment_lifetime_number, Long equipment_id,
                                          String serial_number, String true_name,
                                          String user_card_number) {
        ModelAndView mv = new JModelAndView("admin/office/fixed_assets_list.html", request,
            response);

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        StringBuilder hql = new StringBuilder();
        hql.append("from FixedAssets t where t.disabled = false ");

        //设备终身码查询条件
        if (CommUtil.isNotNull(equipment_lifetime_number)) {
            hql.append(" and t.equipment_lifetime_number like :equipment_lifetime_number ");
            paramsMap
                .put("equipment_lifetime_number", "%" + equipment_lifetime_number.trim() + "%");
        }
        //设备查询条件
        if (CommUtil.isNotNull(equipment_id)) {
            hql.append(" and t.equipment.id =:equipment_id ");
            paramsMap.put("equipment_id", equipment_id);
        }
        //序列号查询条件
        if (CommUtil.isNotNull(serial_number)) {
            hql.append(" and t.serial_number like :serial_number ");
            paramsMap.put("serial_number", "%" + serial_number.trim() + "%");
        }
        //使用人查询条件
        if (CommUtil.isNotNull(true_name)) {
            hql.append(" and t.user.true_name like :true_name ");
            paramsMap.put("true_name", "%" + true_name.trim() + "%");
        }
        if (CommUtil.isNotNull(user_card_number)) {
            hql.append(" and t.user_card_number like :user_card_number ");
            paramsMap.put("user_card_number", "%" + user_card_number.trim() + "%");
        }
        hql.append(" order by t.createtime desc");

        List<FixedAssets> list = fixedAssetsService.queryFixedAssets(hql.toString(), paramsMap,
            pager);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);

        //获取总价
        BigDecimal total_price = BigDecimal.ZERO;
        for (FixedAssets fixedAssets : list) {
            BigDecimal unit_price = fixedAssets.getUnit_price();
            if (unit_price != null) {
                total_price = total_price.add(unit_price);
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");

        List<Equipment> equipments = equipmentService.queryAllEquipment();
        mv.addObject("equipments", equipments);
        mv.addObject("equipment_lifetime_number", equipment_lifetime_number);
        mv.addObject("equipment_id", equipment_id);
        mv.addObject("serial_number", serial_number);
        mv.addObject("true_name", true_name);
        mv.addObject("user_card_number", user_card_number);
        mv.addObject("total_price", df.format(total_price));
        return mv;
    }

    /**
     * 固定资产详情
     */
    @SecurityMapping(res_name = "查看", res_url = "/admin/office/fixed_assets_view.htm*", res_group = "固定资产管理")
    @RequestMapping({ "/admin/office/fixed_assets_view.htm" })
    public ModelAndView fixed_assets_list(HttpServletRequest request, HttpServletResponse response,
                                          String currentPage, Long id) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/office/fixed_assets_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "固定资产标识为空", request, response);
        }
        FixedAssets fixedAssets = fixedAssetsService.getFixedAssets(id);
        if (fixedAssets == null) {
            return CommUtil.errorPage(target_url, "找不到固定资产记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/office/fixed_assets_view.html", request,
            response);

        mv.addObject("obj", fixedAssets);
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    /**
     * 固定资产添加页面
     */
    @SecurityMapping(res_name = "添加", res_url = "/admin/office/fixed_assets_add.htm*", res_group = "固定资产管理")
    @RequestMapping({ "/admin/office/fixed_assets_add.htm" })
    public ModelAndView fixed_assets_add(HttpServletRequest request, HttpServletResponse response,
                                         String currentPage, Long id) {
        ModelAndView mv = new JModelAndView("admin/office/fixed_assets_add.html", request, response);

        List<User> users = userService.queryAllUser();
        List<Equipment> equipments = equipmentService.queryAllEquipment();
        mv.addObject("equipments", equipments);
        mv.addObject("users", users);
        mv.addObject("currentPage", currentPage);

        return mv;

    }

    /**
    * 添加固定资产保存
    */
    @RequestMapping({ "/admin/office/fixed_assets_add_save.htm" })
    public void user_add_save(HttpServletRequest request, HttpServletResponse response,
                              String pageSize, String currentPage, Long equipment_id, Long user_id,
                              String equipment_name) {
        if (!CommUtil.isBlank(equipment_name)) {
            equipment_id = equipmentService.addEquipmentByEquipmentName(equipment_name);
        }

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "固定资产保存成功");
        if (equipment_id == null) {
            CommUtil.setResultMsgData(rmg, false, "设备名称标识为空");
        }
        if (user_id == null) {
            CommUtil.setResultMsgData(rmg, false, "使用人标识为空");
        }

        WebForm wf = new WebForm();
        FixedAssets fixedAssets = (FixedAssets) wf.toPo(request, FixedAssets.class);
        User user = BaseController.getCurrentUser(request);
        Long userId = user.getId();
        try {
            rmg = fixedAssetsService.add_save(fixedAssets, user_id, equipment_id, userId);
        } catch (Exception e) {
            e.printStackTrace();
            CommUtil.setResultMsgData(rmg, false, "固定资产保存失败");
        }
        rmg.setData(CommUtil.getURL(request) + "/admin/office/fixed_assets_list.htm?currentPage="
                    + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 编辑固定资产
     */
    @SecurityMapping(res_name = "编辑", res_url = "/admin/office/fixed_assets_edit.htm*", res_group = "固定资产管理")
    @RequestMapping({ "/admin/office/fixed_assets_edit.htm" })
    public ModelAndView outside_income_dispatches_edit(HttpServletRequest request,
                                                       HttpServletResponse response,
                                                       String pageSize, String currentPage, Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/office/fixed_assets_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "固定资产标识为空", request, response);
        }
        FixedAssets fixedAssets = fixedAssetsService.getFixedAssets(id);
        if (fixedAssets == null) {
            return CommUtil.errorPage(target_url, "找不到固定资产记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/office/fixed_assets_edit.html", request,
            response);
        List<Equipment> equipments = equipmentService.queryAllEquipment();
        List<User> users = userService.queryAllUser();
        mv.addObject("equipments", equipments);
        mv.addObject("users", users);
        mv.addObject("obj", fixedAssets);
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    /**
     * 固定资产编辑保存
     */
    @RequestMapping({ "/admin/office/fixed_assets_edit_save.htm" })
    public void outside_income_dispatches_edit_save(HttpServletRequest request,
                                                    HttpServletResponse response, Long id,
                                                    String currentPage, Long equipment_id,
                                                    Long user_id) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "固定资产编辑保存成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "固定资产标识为空");
        }
        if (rmg.getResult()) {
            FixedAssets fAs = fixedAssetsService.getFixedAssets(id);
            if (fAs == null) {
                CommUtil.setResultMsgData(rmg, false, "找不到固定资产记录");
            } else {
                WebForm wf = new WebForm();
                FixedAssets fixedAssets = (FixedAssets) wf.toPo(request, fAs);
                try {
                    User user = BaseController.getCurrentUser(request);
                    Long userId = user.getId();
                    rmg = fixedAssetsService.edit_save(fixedAssets, user_id, equipment_id, userId);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "固定资产编辑保存失败");
                }
            }
        }
        rmg.setData(CommUtil.getURL(request) + "/admin/office/fixed_assets_list.htm?currentPage="
                    + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 固定资产删除
     */
    @SecurityMapping(res_name = "删除", res_url = "/admin/office/fixed_assets_delete.htm*", res_group = "固定资产管理")
    @RequestMapping({ "/admin/office/fixed_assets_delete.htm" })
    public ModelAndView fixed_assets_delete(HttpServletRequest request,
                                            HttpServletResponse response, Long id,
                                            String currentPage) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/office/fixed_assets_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "固定资产标识为空", request, response);
        }
        FixedAssets fixedAssets = fixedAssetsService.getFixedAssets(id);
        if (fixedAssets == null) {
            return CommUtil.errorPage(target_url, "找不到固定资产记录", request, response);
        }
        try {
            fixedAssetsService.delFixedAssets(fixedAssets);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, "固定资产删除失败", request, response);
        }
        return CommUtil.successPage(target_url, "固定资产删除成功", request, response);
    }

    /**
     * 批量删除固定资产
     */
    @SecurityMapping(res_name = "批量删除", res_url = "/admin/office/fixed_assets_deleteAll.htm*", res_group = "固定资产管理")
    @RequestMapping({ "/admin/office/fixed_assets_deleteAll.htm" })
    public ModelAndView fixed_assets_deleteAll(HttpServletRequest request,
                                               HttpServletResponse response, String delIds,
                                               String currentPage) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/office/fixed_assets_list.htm?currentPage=" + currentPage;
        if (delIds == null) {
            return CommUtil.errorPage(target_url, "固定资产标识为空", request, response);
        }
        try {
            fixedAssetsService.batchDelFixedAssets(delIds);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, "固定资产删除失败", request, response);
        }
        return CommUtil.successPage(target_url, "固定资产删除成功", request, response);

    }

    //=============================================导出Excel区域开始======================================================//
    @SecurityMapping(res_name = "导出Excel", res_url = "/admin/office/fixed_assets_exportExcel.htm*", res_group = "固定资产管理")
    @RequestMapping("/admin/office/fixed_assets_exportExcel.htm")
    public synchronized void fixed_assets_exportExcel(HttpServletRequest request,
                                                      HttpServletResponse response,
                                                      String currentPage,
                                                      String equipment_lifetime_number,
                                                      Long equipment_id, String serial_number,
                                                      String true_name, String user_card_number) {

        List<Object[]> list = null;
        OutputStream out = null;
        SXSSFWorkbook workbook = null;
        SXSSFSheet sheet = null;

        try {
            workbook = new SXSSFWorkbook(100);
            sheet = (SXSSFSheet) workbook.createSheet("固定资产");

            String titleName = "固定资产统计表";
            String[] titleNames = { "序号", "设备终身码", "设备名称", "型号", "序列号", "单价/台", "生产厂家", "使用人",
                    "工作证号", "使用部门", "设备位置", "备注" };
            POIUtil.setColumnWidth(sheet, titleNames.length);//设置通用列宽
            sheet.setColumnWidth(6, 20 * 512);//设置标题列宽

            int all_count = fixedAssetsService.getCountForExportExcel(equipment_lifetime_number,
                equipment_id, serial_number, true_name, user_card_number);
            BigDecimal total_price = fixedAssetsService
                .getTotalPriceForExportExcel(equipment_lifetime_number, equipment_id,
                    serial_number, true_name, user_card_number);
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
                    log.info("【固定资产导出-分页填充】第" + (i + 1) + "次填充开始====>" + CommUtil.getCurrentTime());

                    list = fixedAssetsService.getFixedAssetsForExportExcel(
                        equipment_lifetime_number, equipment_id, serial_number, true_name,
                        user_card_number, beginIndex, pageSize);

                    rowNum = 2 + i * pageSize;
                    fillSheet(sheet, titleName, titleNames, list, rowNum);
                    log.info("【固定资产导出-分页填充】第" + (i + 1) + "次填充结束====>" + CommUtil.getCurrentTime());
                    list.clear();
                }
                list = null;
                fillTotalPrice(sheet, 2 + all_count, total_price);
            }

            response.setContentType("application/vnd.ms-excel");
            String fileName = "固定资产" + new Date().getTime() + ".xlsx";
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

    private void fillTotalPrice(SXSSFSheet sheet, int rowNum, BigDecimal total_price) {
        CellStyle cellStyle = POIUtil.getBaseCellStyle(sheet);
        Font font = POIUtil.getBaseFont(sheet);
        cellStyle.setFont(font);
        //设置总价
        Cell cell = null;
        Row row = sheet.createRow(rowNum);
        int columnNum = 0;
        cell = row.createCell(columnNum++);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(new XSSFRichTextString(CommUtil.null2String("总价")));

        cell = row.createCell(5);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(new XSSFRichTextString(CommUtil.null2String(total_price)));
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
