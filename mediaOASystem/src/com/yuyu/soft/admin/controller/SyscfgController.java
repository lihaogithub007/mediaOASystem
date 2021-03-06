package com.yuyu.soft.admin.controller;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuyu.soft.base.annotation.SecurityMapping;
import com.yuyu.soft.base.controller.BaseController;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.ContractSmsRemind;
import com.yuyu.soft.entity.DispatchUnit;
import com.yuyu.soft.entity.Equipment;
import com.yuyu.soft.service.IContractSmsRemindService;
import com.yuyu.soft.service.IDispatchUnitService;
import com.yuyu.soft.service.IEquipmentService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.PageUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 系统设置
 * @Filename: SyscfgController.java
 * @Version: 1.0
 * @Author: lihao
 * @Email: lihao220281@126.com
 *
 */
@Controller
public class SyscfgController extends BaseController {

    protected Logger             log = LoggerFactory.getLogger(SyscfgController.class);

    @Resource
    private IEquipmentService    equipmentService;

    @Resource
    private IDispatchUnitService dispatchUnitService;
    
    @Resource
    private IContractSmsRemindService contractSmsRemindService;
    
    /**
     * 设备管理列表
     */
    @SecurityMapping(res_name = "设备管理列表", res_url = "/admin/syscfg/equipment_list.htm*", res_group = "设备管理")
    @RequestMapping({ "/admin/syscfg/equipment_list.htm" })
    public ModelAndView equipment_list(HttpServletRequest request, HttpServletResponse response,
                                       String pageSize, String currentPage, String equipment_name) {
        ModelAndView mv = new JModelAndView("/admin/syscfg/equipment_list.html", request, response);

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        StringBuilder hql = new StringBuilder();
        hql.append("from Equipment t where t.disabled = false");

        // 按设备名称模糊查询
        if (CommUtil.isNotNull(equipment_name)) {
            hql.append(" and t.equipment_name like :equipment_name ");
            paramsMap.put("equipment_name", "%" + equipment_name.trim() + "%");
        }

        hql.append(" order by t.createtime asc");

        List<Equipment> list = equipmentService
            .queryEquipmentList(hql.toString(), paramsMap, pager);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);

        mv.addObject("equipment_name", equipment_name);

        return mv;
    }

    /**
     * 添加设备(跳转页面)
     */
    @SecurityMapping(res_name = "添加", res_url = "/admin/syscfg/equipment_add.htm*", res_group = "设备管理")
    @RequestMapping({ "/admin/syscfg/equipment_add.htm" })
    public ModelAndView equipment_add(HttpServletRequest request, HttpServletResponse response,
                                      String pageSize, String currentPage) {
        ModelAndView mv = new JModelAndView("/admin/syscfg/equipment_add.html", request, response);
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    /**
     * 添加设备(保存添加信息)
     */
    @RequestMapping({ "/admin/syscfg/equipment_add_save.htm" })
    public void equipment_add_save(HttpServletRequest request, HttpServletResponse response,
                                   String pageSize, String currentPage, String equipment_name,
                                   Long id) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "设备名称保存成功");
        if (!CommUtil.isNotNull(equipment_name)) {
            CommUtil.setResultMsgData(rmg, false, "设备名称不能为空");
        } else if (equipment_name.trim().length() > 50) {
            CommUtil.setResultMsgData(rmg, false, "设备名称不能超过50汉字");
        }
        if (rmg.getResult()) {
            if (!equipmentService.verify_equipment_name(id, equipment_name)) {
                CommUtil.setResultMsgData(rmg, false, "设备名称已存在，不能重复");
            } else {
                Equipment equipment = new Equipment();
                equipment.setDisabled(false);
                equipment.setCreatetime(new Date());
                equipment.setEquipment_name(equipment_name);
                try {
                    equipmentService.addEquipment(equipment);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "设备名称保存失败");
                }
            }
        }
        rmg.setData(CommUtil.getURL(request) + "/admin/syscfg/equipment_list.htm?currentPage="
                    + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 删除设备
     */
    @SecurityMapping(res_name = "删除", res_url = "/admin/syscfg/equipment_delete.htm*", res_group = "设备管理")
    @RequestMapping({ "/admin/syscfg/equipment_delete.htm" })
    public ModelAndView equipment_delete(HttpServletRequest request, HttpServletResponse response,
                                         String pageSize, String currentPage, Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/syscfg/equipment_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "设备标识为空", request, response);
        }
        Equipment equipment = equipmentService.findEquipmentById(id);
        if (equipment == null) {
            return CommUtil.errorPage(target_url, "找不到设备记录", request, response);
        }
        try {
            equipmentService.deleteEquipment(equipment);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, "设备删除失败", request, response);
        }
        return CommUtil.successPage(target_url, "设备删除成功", request, response);

    }

    /**
     * 编辑设备(跳转页面)
     */
    @SecurityMapping(res_name = "编辑", res_url = "/admin/syscfg/equipment_edit.htm*", res_group = "设备管理")
    @RequestMapping({ "/admin/syscfg/equipment_edit.htm" })
    public ModelAndView equipment_edit(HttpServletRequest request, HttpServletResponse response,
                                       String pageSize, String currentPage, Long id,
                                       String equipment_name) {
        ModelAndView mv = new JModelAndView("/admin/syscfg/equipment_edit.html", request, response);
        mv.addObject("equipment_name", equipment_name);
        mv.addObject("equipment_id", id);
        mv.addObject("currentPage", currentPage);

        return mv;
    }

    /**
     * 编辑设备(保存编辑信息)
     */
    @RequestMapping({ "/admin/syscfg/equipment_edit_save.htm" })
    public void equipment_edit_save(HttpServletRequest request, HttpServletResponse response,
                                    String pageSize, String currentPage, Long equipment_id,
                                    String equipment_name) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "设备名称修改成功");
        if (!CommUtil.isNotNull(equipment_name)) {
            CommUtil.setResultMsgData(rmg, false, "设备名称不能为空");
        } else if (equipment_name.trim().length() > 50) {
            CommUtil.setResultMsgData(rmg, false, "设备名称不能超过50汉字");
        }
        if (rmg.getResult()) {
            if (!equipmentService.verify_equipment_name(equipment_id, equipment_name)) {
                CommUtil.setResultMsgData(rmg, false, "设备名称已存在，不能重复");
            } else {
                Equipment equipment = equipmentService.findEquipmentById(equipment_id);
                equipment.setEquipment_name(equipment_name);
                try {
                    equipmentService.editEquipment(equipment);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "设备名称修改失败");
                }
            }
        }
        rmg.setData(CommUtil.getURL(request) + "/admin/syscfg/equipment_list.htm?currentPage="
                    + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 发文单位列表
     */
    @SecurityMapping(res_name = "发文单位列表", res_url = "/admin/syscfg/dispatch_unit_list.htm*", res_group = "发文单位管理")
    @RequestMapping({ "/admin/syscfg/dispatch_unit_list.htm" })
    public ModelAndView dispatch_unit_list(HttpServletRequest request,
                                           HttpServletResponse response, String pageSize,
                                           String currentPage, String dispatch_unit_name) {
        ModelAndView mv = new JModelAndView("/admin/syscfg/dispatch_unit_list.html", request,
            response);

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        StringBuilder hql = new StringBuilder();
        hql.append("from DispatchUnit t where t.disabled = false");

        // 按设备名称模糊查询
        if (CommUtil.isNotNull(dispatch_unit_name)) {
            hql.append(" and t.dispatch_unit_name like :dispatch_unit_name ");
            paramsMap.put("dispatch_unit_name", "%" + dispatch_unit_name.trim() + "%");
        }

        hql.append(" order by t.createtime asc");

        List<DispatchUnit> list = dispatchUnitService.queryDispatchUnitList(hql.toString(),
            paramsMap, pager);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);

        mv.addObject("dispatch_unit_name", dispatch_unit_name);

        return mv;
    }

    /**
     * 添加发文单位(跳转页面)
     */
    @SecurityMapping(res_name = "添加", res_url = "/admin/syscfg/dispatch_unit_add.htm*", res_group = "发文单位管理")
    @RequestMapping({ "/admin/syscfg/dispatch_unit_add.htm" })
    public ModelAndView user_add(HttpServletRequest request, HttpServletResponse response,
                                 String pageSize, String currentPage, Long id) {
        ModelAndView mv = new JModelAndView("admin/syscfg/dispatch_unit_add.html", request,
            response);
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    /**
     * 添加发文单位(保存添加信息)
     */
    @RequestMapping({ "/admin/syscfg/dispatch_unit_save.htm" })
    public void dispatch_unit_add_save(HttpServletRequest request, HttpServletResponse response,
                                       String dispatch_unit_name, Long id) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "发文单位保存成功");
        if (!CommUtil.isNotNull(dispatch_unit_name)) {
            CommUtil.setResultMsgData(rmg, false, "发文单位名称不能为空");
        } else if (dispatch_unit_name.trim().length() > 50) {
            CommUtil.setResultMsgData(rmg, false, "发文单位名称不能超过50汉字");
        }
        if (rmg.getResult()) {
            if (!dispatchUnitService.verify_dispatch_unit_name(id, dispatch_unit_name)) {
                CommUtil.setResultMsgData(rmg, false, "发文单位名称已存在，不能重复");
            } else {
                DispatchUnit dispatchUnit = new DispatchUnit();
                dispatchUnit.setDisabled(false);
                dispatchUnit.setCreatetime(new Date());
                dispatchUnit.setDispatch_unit_name(dispatch_unit_name);
                try {
                    dispatchUnitService.addDispatchUnit(dispatchUnit);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "发文单位保存失败");
                }
            }
        }
        rmg.setData(CommUtil.getURL(request) + "/admin/syscfg/dispatch_unit_list.htm");
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 删除发文单位
     */
    @SecurityMapping(res_name = "删除", res_url = "/admin/syscfg/dispatch_unit_delete.htm*", res_group = "发文单位管理")
    @RequestMapping({ "/admin/syscfg/dispatch_unit_delete.htm" })
    public ModelAndView dispatch_unit_delete(HttpServletRequest request,
                                             HttpServletResponse response, Long id,
                                             String currentPage) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/syscfg/dispatch_unit_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "发文单位标识为空", request, response);
        }
        DispatchUnit dispatchUnit = dispatchUnitService.findDispatchUnitById(id);
        if (dispatchUnit == null) {
            return CommUtil.errorPage(target_url, "找不到发文单位记录", request, response);
        }
        try {
            dispatchUnitService.delDispathcUnit(dispatchUnit);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, "发文单位删除失败", request, response);
        }
        return CommUtil.successPage(target_url, "发文单位删除成功", request, response);
    }

    /**
     * 编辑发文单位(跳转页面)
     */
    @SecurityMapping(res_name = "编辑", res_url = "/admin/syscfg/dispatch_unit_edit.htm*", res_group = "发文单位管理")
    @RequestMapping({ "/admin/syscfg/dispatch_unit_edit.htm" })
    public ModelAndView dispatch_unit_edit(HttpServletRequest request,
                                           HttpServletResponse response, String pageSize,
                                           String currentPage, Long id, String dispatch_unit_name) {
        ModelAndView mv = new JModelAndView("/admin/syscfg/dispatch_unit_edit.html", request,
            response);
        mv.addObject("dispatch_unit_name", dispatch_unit_name);
        mv.addObject("dispatch_unit_id", id);
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    /**
     * 编辑发文单位(保存编辑信息)
     */
    @RequestMapping({ "/admin/syscfg/dispatch_unit_edit_save.htm" })
    public void dispatch_unit_edit_save(HttpServletRequest request, HttpServletResponse response,
                                        String pageSize, String currentPage, Long dispatch_unit_id,
                                        String dispatch_unit_name) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "发文单位名称修改成功");
        if (!CommUtil.isNotNull(dispatch_unit_name)) {
            CommUtil.setResultMsgData(rmg, false, "发文单位名称不能为空");
        } else if (dispatch_unit_name.trim().length() > 50) {
            CommUtil.setResultMsgData(rmg, false, "发文单位名称不能超过50汉字");
        }
        if (rmg.getResult()) {
            if (!dispatchUnitService
                .verify_dispatch_unit_name(dispatch_unit_id, dispatch_unit_name)) {
                CommUtil.setResultMsgData(rmg, false, "发文单位名称已存在，不能重复");
            } else {
                DispatchUnit dispatchUnit = dispatchUnitService
                    .findDispatchUnitById(dispatch_unit_id);
                dispatchUnit.setDispatch_unit_name(dispatch_unit_name);
                try {
                    dispatchUnitService.editDispatchUnit(dispatchUnit);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "发文单位名称修改失败");
                }
            }
        }
        rmg.setData(CommUtil.getURL(request) + "/admin/syscfg/dispatch_unit_list.htm?currentPage="
                    + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

}
