package com.yuyu.soft.admin.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuyu.soft.base.controller.BaseController;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.Department;
import com.yuyu.soft.entity.Duty;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IDepartmentService;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.Md5;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

/**
 * 个人中心、密码修改
 *                       
 * @Filename: UserCenterController.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Controller
public class UserCenterController {
    protected Logger           log = LoggerFactory.getLogger(UserCenterController.class);
    @Resource
    private IUserService       userService;
    @Resource
    private IDepartmentService departmentService;
    @Resource
    private IDutyService       dutyService;

    @RequestMapping({ "/admin/usercenter/usercenter.htm" })
    public ModelAndView usercenter(HttpServletRequest request, HttpServletResponse response,
                                   String pageSize, String currentPage, Long department_id,
                                   Long duty_id, String true_name) {
        ModelAndView mv = new JModelAndView("admin/usercenter/usercenter.html", request, response);

        User usercenter = userService.getUser(BaseController.getCurrentUser(request).getId());
        List<Department> departments = departmentService.queryAllDepartment();
        List<Duty> dutys = dutyService.queryAllDuty();
        mv.addObject("usercenter", usercenter);
        mv.addObject("user_relationship_map", Constants.USER_RELATIONSHIP_MAP);
        mv.addObject("departments", departments);
        mv.addObject("dutys", dutys);
        return mv;
    }

    @RequestMapping({ "/admin/usercenter/usercenter_edit_save.htm" })
    public void user_edit_save(HttpServletRequest request, HttpServletResponse response,
                               String pageSize, String currentPage, Long id, Long department_id,
                               Long duty_id) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "保存成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "用户标识为空");
        }
        if (department_id == null) {
            CommUtil.setResultMsgData(rmg, false, "部门/科组标识为空");
        }
        if (rmg.getResult()) {
            Department department = departmentService.getDepartment(department_id);
            if (department == null) {
                CommUtil.setResultMsgData(rmg, false, "找不到部门/科组记录");
            }
            if (rmg.getResult()) {
                Duty duty = null;
                if (duty_id != null) {
                    duty = dutyService.getDuty(duty_id);
                    if (duty != null && duty.getDepartment().getId() != department_id) {
                        CommUtil.setResultMsgData(rmg, false, "所选岗位与所选部门/科室没有所属关系");
                    }
                }
                if (rmg.getResult()) {
                    WebForm wf = new WebForm();
                    User dbUser = userService.getUser(id);
                    User user = (User) wf.toPo(request, dbUser);
                    user.setDuty(duty);
                    try {
                        userService.updateUser(user);
                    } catch (Exception e) {
                        CommUtil.setResultMsgData(rmg, false, "保存失败");
                    }
                }
            }
        }
        rmg.setData(CommUtil.getURL(request) + "/admin/usercenter/usercenter.htm");
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    @RequestMapping({ "/admin/usercenter/usercenter_reset_password.htm" })
    public ModelAndView admin_reset_user_password(HttpServletRequest request,
                                                  HttpServletResponse response, String currentPage) {
        return new JModelAndView("admin/usercenter/usercenter_reset_password.html", request,
            response);
    }

    @RequestMapping({ "/admin/usercenter/usercenter_reset_password_save.htm" })
    public void admin_reset_user_password_save(HttpServletRequest request,
                                               HttpServletResponse response, String currentPage,
                                               String old_password, String new_password) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "密码修改成功，请重新登录");
        User user = userService.getUser(BaseController.getCurrentUser(request).getId());
        if (user.getPassword().equals(Md5.getMd5String(old_password).toLowerCase())) {
            try {
                user.setPassword(Md5.getMd5String(new_password).toLowerCase());
                userService.updateUser(user);
            } catch (Exception e) {
                CommUtil.setResultMsgData(rmg, false, "密码修改失败");
            }
        } else {
            CommUtil.setResultMsgData(rmg, false, "原始密码输入错误");
        }
        rmg.setData(CommUtil.getURL(request) + "/logout.htm");
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }
}
