package com.yuyu.soft.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuyu.soft.base.annotation.SecurityMapping;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.Department;
import com.yuyu.soft.service.IDepartmentService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.PageUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 部门/科组管理
 * @Filename: DepartmentController.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Controller
public class DepartmentController {

    @Resource
    private IDepartmentService departmentService;

    /**
     * 部门/科组列表
     */
    @SecurityMapping(res_name = "部门/科组列表", res_url = "/admin/user/department_list.htm*", res_group = "组织机构")
    @RequestMapping({ "/admin/user/department_list.htm" })
    public ModelAndView user_list(HttpServletRequest request, HttpServletResponse response,
                                  String pageSize, String currentPage, String department_name) {
        ModelAndView mv = new JModelAndView("admin/user/department_list.html", request, response);

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        StringBuilder hql = new StringBuilder();
        hql.append("from Department t where t.disabled = false");
        if (CommUtil.isNotNull(department_name)) {
            hql.append(" and t.department_name like :department_name");
            paramsMap.put("department_name", "%" + department_name.trim() + "%");
        }
        hql.append(" order by t.createtime desc");
        List<Department> list = departmentService.queryDepartment(hql.toString(), paramsMap, pager);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);
        mv.addObject("department_name", department_name);
        mv.addObject("list", list);
        return mv;
    }

    /**
     * ajax 修改部门名称
     */
    @SecurityMapping(res_name = "部门/科组编辑", res_url = "/admin/user/department_ajax_edit_save.htm*", res_group = "组织机构")
    @RequestMapping({ "/admin/user/department_ajax_edit_save.htm" })
    public void duty_ajax_edit_save(HttpServletRequest request, HttpServletResponse response,
                                    Long id, String value) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "部门/科组名称修改成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "岗位标识为空");
        }
        if (rmg.getResult()) {
            if (!CommUtil.isNotNull(value)) {
                CommUtil.setResultMsgData(rmg, false, "部门/科组名称不能为空");
            } else if (value.trim().length() > 50) {
                CommUtil.setResultMsgData(rmg, false, "部门/科组名称不能超过50汉字");
            }
        }
        if (rmg.getResult()) {
            Department department = departmentService.getDepartment(id);
            if (department == null) {
                CommUtil.setResultMsgData(rmg, false, "查不到部门/科组记录");
            } else if (!departmentService.verify_department_name(department.getId(), value)) {
                CommUtil.setResultMsgData(rmg, false, "部门/科组名称已存在，不能重复");
            } else {
                department.setDepartment_name(value);
                try {
                    departmentService.updateDepartment(department);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "部门/科组名称修改失败");
                }
            }
        }
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 添加部门/科组
     */
    @SecurityMapping(res_name = "部门/科组添加", res_url = "/admin/user/department_add.htm*", res_group = "组织机构")
    @RequestMapping({ "/admin/user/department_add.htm" })
    public ModelAndView user_add(HttpServletRequest request, HttpServletResponse response,
                                 String pageSize, String currentPage) {
        ModelAndView mv = new JModelAndView("admin/user/department_add.html", request, response);
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    /**
     * 添加部门/科组保存
     */
    @RequestMapping({ "/admin/user/department_add_save.htm" })
    public void department_add_save(HttpServletRequest request, HttpServletResponse response,
                                    String department_name) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "部门/科组保存成功");
        if (!CommUtil.isNotNull(department_name)) {
            CommUtil.setResultMsgData(rmg, false, "部门/科组名称不能为空");
        } else if (department_name.trim().length() > 50) {
            CommUtil.setResultMsgData(rmg, false, "部门/科组名称不能超过50汉字");
        }
        if (rmg.getResult()) {
            if (!departmentService.verify_department_name(null, department_name)) {
                CommUtil.setResultMsgData(rmg, false, "部门/科组名称已存在，不能重复");
            } else {
                Department department = new Department();
                department.setDisabled(false);
                department.setCreatetime(new Date());
                department.setDepartment_name(department_name);
                try {
                    departmentService.addDepartment(department);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "部门/科组保存失败");
                }
            }
        }
        rmg.setData(CommUtil.getURL(request) + "/admin/user/department_list.htm");
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 部门/科组删除(同时删除其下所有岗位)
     */
    @SecurityMapping(res_name = "部门/科组删除", res_url = "/admin/user/department_delete.htm*", res_group = "组织机构")
    @RequestMapping({ "/admin/user/department_delete.htm" })
    public ModelAndView department_delete(HttpServletRequest request, HttpServletResponse response,
                                          Long id, String currentPage) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/user/department_list.htm?currentPage=" + currentPage;
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "部门/科组删除成功");
        try {
            rmg = departmentService.delete_save(id);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, "部门/科组删除失败", request, response);
        }
        if (!rmg.getResult()) {
            return CommUtil.errorPage(target_url, rmg.getMsg(), request, response);
        }
        return CommUtil.successPage(target_url, "部门/科组删除成功", request, response);
    }
}
