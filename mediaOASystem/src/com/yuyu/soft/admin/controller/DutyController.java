package com.yuyu.soft.admin.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuyu.soft.base.annotation.SecurityMapping;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.Department;
import com.yuyu.soft.entity.Duty;
import com.yuyu.soft.service.IDepartmentService;
import com.yuyu.soft.service.IDutyResService;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.IResGroupService;
import com.yuyu.soft.service.ISectionService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.ResultMsg;

/**
 * 岗位管理                      
 * @Filename: DutyController.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Controller
public class DutyController {

    @Resource
    private IDepartmentService departmentService;
    @Resource
    private IDutyService       dutyService;
    @Resource
    private IResGroupService   resGroupService;
    @Resource
    private IDutyResService    dutyResService;
    
    /**
     * ajax查询某部门下所有岗位
     * reutrn json
     */
    @RequestMapping({ "/admin/duty/ajaxQueryDutyUnderDepartment.htm" })
    public void ajaxQueryDutyUnderDepartment(HttpServletRequest request,
                                             HttpServletResponse response, Long department_id) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "获取部门岗位成功");
        try {
            List<Duty> list = dutyService.queryDutyUnderDepartment(department_id);
            rmg.setData(list);
        } catch (Exception e) {
            CommUtil.setResultMsgData(rmg, false, "获取部门岗位失败");
        }
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * ajax查询某部门下所有岗位
     * reutrn mv
     */
    @RequestMapping({ "/admin/user/ajaxQueryDutyData.htm" })
    public ModelAndView ajaxQueryDutyData(HttpServletRequest request, HttpServletResponse response,
                                          String currentPage, Long department_id) {
        ModelAndView mv = new JModelAndView("admin/user/duty_ajax_data.html", request, response);
        if (department_id != null) {
            mv.addObject("dutys", dutyService.queryDutyUnderDepartment(department_id));
        }
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    /**
     * ajax 修改岗位名称
     */
    @SecurityMapping(res_name = "岗位编辑", res_url = "/admin/user/duty_ajax_edit_save.htm*", res_group = "组织机构")
    @RequestMapping({ "/admin/user/duty_ajax_edit_save.htm" })
    public void duty_ajax_edit_save(HttpServletRequest request, HttpServletResponse response,
                                    Long id, String value) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "岗位名称修改成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "岗位标识为空");
        }
        if (rmg.getResult()) {
            if (!CommUtil.isNotNull(value)) {
                CommUtil.setResultMsgData(rmg, false, "岗位名称不能为空");
            } else if (value.trim().length() > 50) {
                CommUtil.setResultMsgData(rmg, false, "岗位名称不能超过50汉字");
            }
        }
        if (rmg.getResult()) {
            Duty duty = dutyService.getDuty(id);
            if (duty == null) {
                CommUtil.setResultMsgData(rmg, false, "查不到岗位记录");
            } else if (duty.getDepartment() == null || duty.getDepartment().getId() == null) {
                CommUtil.setResultMsgData(rmg, false, "查不到部门/科组记录");
            } else if (!dutyService.verify_duty_name(duty.getId(), duty.getDepartment().getId(),
                value)) {
                CommUtil.setResultMsgData(rmg, false, "所属部门/科组下已有此岗位，不能重复");
            } else {
                duty.setDuty_name(value);
                try {
                    dutyService.updateDuty(duty);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "岗位名称修改失败");
                }
            }
        }
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 添加岗位
     */
    @SecurityMapping(res_name = "岗位添加", res_url = "/admin/user/duty_add.htm*", res_group = "组织机构")
    @RequestMapping({ "/admin/user/duty_add.htm" })
    public ModelAndView user_add(HttpServletRequest request, HttpServletResponse response,
                                 String pageSize, String currentPage, Long department_id) {
        ModelAndView mv = new JModelAndView("admin/user/duty_add.html", request, response);
        List<Department> departments = departmentService.queryAllDepartment();
        mv.addObject("departments", departments);
        mv.addObject("department_id", department_id);
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    /**
     * 添加岗位保存
     */
    @RequestMapping({ "/admin/user/duty_add_save.htm" })
    public void duty_add_save(HttpServletRequest request, HttpServletResponse response,
                              String currentPage, Long department_id, String duty_name) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "岗位保存成功");
        if (!CommUtil.isNotNull(duty_name)) {
            CommUtil.setResultMsgData(rmg, false, "岗位名称不能为空");
        } else if (duty_name.trim().length() > 50) {
            CommUtil.setResultMsgData(rmg, false, "岗位名称不能超过50汉字");
        }
        if (rmg.getResult() && department_id == null) {
            CommUtil.setResultMsgData(rmg, false, "所属部门/科组标识为空");
        }
        if (rmg.getResult()) {
            Department department = departmentService.getDepartment(department_id);
            if (department == null || department.getId() == null) {
                CommUtil.setResultMsgData(rmg, false, "查不到部门/科组记录");
            } else if (!dutyService.verify_duty_name(null, department_id, duty_name)) {
                CommUtil.setResultMsgData(rmg, false, "所属部门/科组下已有此岗位，不能重复");
            } else {
                Duty duty = new Duty();
                duty.setDisabled(false);
                duty.setCreatetime(new Date());
                duty.setDuty_name(duty_name);
                duty.setDepartment(department);
                try {
                    dutyService.addDuty(duty);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "岗位保存失败");
                }
            }
        }
        rmg.setData(CommUtil.getURL(request) + "/admin/user/department_list.htm?currentPage="
                    + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 岗位删除
     */
    @SecurityMapping(res_name = "岗位删除", res_url = "/admin/user/duty_delete.htm*", res_group = "组织机构")
    @RequestMapping({ "/admin/user/duty_delete.htm" })
    public ModelAndView duty_delete(HttpServletRequest request, HttpServletResponse response,
                                    Long id, String currentPage) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/user/department_list.htm?currentPage=" + currentPage;
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "岗位删除成功");
        try {
            rmg = dutyService.delete_save(id);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, "岗位删除失败", request, response);
        }
        if (!rmg.getResult()) {
            return CommUtil.errorPage(target_url, rmg.getMsg(), request, response);
        }
        return CommUtil.successPage(target_url, "岗位删除成功", request, response);
    }

    /**
     * 权限编辑
     */
    @SecurityMapping(res_name = "权限编辑", res_url = "/admin/user/duty_res.htm*", res_group = "组织机构")
    @RequestMapping({ "/admin/user/duty_res.htm" })
    public ModelAndView duty_res(HttpServletRequest request, HttpServletResponse response,
                                 String pageSize, String currentPage, Long id) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/user/department_list.htm?currentPage=" + currentPage;

        if (id == null) {
            return CommUtil.errorPage(target_url, "岗位标识为空", request, response);
        }
        Duty duty = this.dutyService.getDuty(id);
        if (duty == null) {
            return CommUtil.errorPage(target_url, "找不到岗位记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/user/duty_res.html", request, response);
        List<Department> departments = departmentService.queryAllDepartment();
        mv.addObject("obj", duty);
        mv.addObject("currentPage", currentPage);
        mv.addObject("rgs", resGroupService.queryAllResGroup());
        mv.addObject("dutyResIds", dutyResService.queryDutyResIds(duty.getId()));
        return mv;
    }

    /**
     * 权限编辑保存
     */
    @RequestMapping({ "/admin/user/duty_res_save.htm" })
    public void duty_res_save(HttpServletRequest request, HttpServletResponse response,
                              String pageSize, String currentPage, Long id, String res_ids) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "岗位权限编辑保存成功");
        try {
            rmg = dutyService.duty_res_save(id, res_ids);
        } catch (Exception e) {
            CommUtil.setResultMsgData(rmg, false, "岗位权限编辑保存失败");
        }
        rmg.setData(CommUtil.getURL(request) + "/admin/user/department_list.htm?currentPage="
                    + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }
}
