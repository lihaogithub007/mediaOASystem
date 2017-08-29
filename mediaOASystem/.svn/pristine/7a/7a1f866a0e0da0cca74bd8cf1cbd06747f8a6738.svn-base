package com.yuyu.soft.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

/**
 * 弹窗处理
 * @Filename: DepartmentController.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Controller
public class DialogsController {
    @Resource
    private IUserService       userService;
    @Resource
    private IDepartmentService departmentService;
    @Resource
    private IDutyService       dutyService;

    @RequestMapping({ "/admin/user/user_multiple_select_dialog.htm" })
    public ModelAndView ajaxQueryDutyData(HttpServletRequest request, HttpServletResponse response,
                                          String currentPage, String pageSize, String user_ids) {
        ModelAndView mv = new JModelAndView("dialogs/user_multiple_select_dialog.html", request,
            response);

        List<User> list = new ArrayList<User>();
        if (CommUtil.isNotNull(user_ids)) {
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            StringBuilder hql = new StringBuilder();
            hql.append("from User t where t.disabled = false");
            hql.append(" and t.id in (").append(user_ids).append(")");
            hql.append(" order by t.createtime desc");
            list = userService.queryUser(hql.toString(), paramsMap, null);
        }

        List<Department> departments = departmentService.queryAllDepartment();
        List<Duty> dutys = dutyService.queryAllDuty();
        List<User> users = userService.queryAllUser();

        mv.addObject("list", list);
        mv.addObject("users", users);
        mv.addObject("departments", departments);
        mv.addObject("dutys", dutys);
        return mv;
    }
}
