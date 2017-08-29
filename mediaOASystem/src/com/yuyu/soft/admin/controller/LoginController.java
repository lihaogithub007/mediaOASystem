package com.yuyu.soft.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuyu.soft.base.controller.BaseController;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.Attachments;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IAttachmentsService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.ResultMsg;

/**
 * 登录
 *                       
 * @Filename: LoginController.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Controller
public class LoginController {
    @Resource
    private IUserService userService;
    
    @Resource
    private IAttachmentsService attachmentsService;
    
    @RequestMapping({ "/login.htm" })
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response)
                                                                                       throws IOException {
        ModelAndView mv = new JModelAndView("admin/login/login.html", request, response);
        
        return mv;
    }

    @RequestMapping({ "/doLogin.htm" })
    public void doLogin(HttpServletRequest request, HttpServletResponse response, String mobile,
                        String password) throws IOException {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "用户登录成功");
        if (CommUtil.isBlank(mobile)) {
            CommUtil.setResultMsgData(rmg, false, "手机号码不能为空");
        } else if (mobile.trim().length() != 11) {
            CommUtil.setResultMsgData(rmg, false, "手机号码不正确");
        }
        if (rmg.getResult() && CommUtil.isBlank(password)) {
            CommUtil.setResultMsgData(rmg, false, "密码不能为空");
        }
        try {
            if (rmg.getResult() && !userService.verify_user_login(mobile, password)) {
                CommUtil.setResultMsgData(rmg, false, "手机号或密码错误");
            } else {
                request.getSession().setAttribute(Constants.USER_SESSION_KEY,
                    userService.query_user_login(mobile, password));
            }
        } catch (Exception e) {
            e.printStackTrace();
            CommUtil.setResultMsgData(rmg, false, e.getMessage());
        }
        
        rmg.setData(CommUtil.getURL(request) + "/index.htm");
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    @RequestMapping({ "/logout.htm" })
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response)
                                                                                        throws IOException {
        ModelAndView mv = new JModelAndView("admin/login/login.html", request, response);
        HttpSession session = request.getSession();
        session.setAttribute(Constants.USER_SESSION_KEY, null);
        session.setAttribute("refererUrl", "");
        return mv;
    }
}
