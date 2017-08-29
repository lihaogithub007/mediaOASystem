package com.yuyu.soft.admin.controller;

import java.util.Arrays;
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
import com.yuyu.soft.entity.ScheduleSmsRemind;
import com.yuyu.soft.service.IScheduleSmsRemindService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.PageUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

/**
 * 日程安排提醒管理                       
 * @Filename: ScheduleSmsRemindController.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Controller
public class ScheduleSmsRemindController {

    @Resource
    private IScheduleSmsRemindService scheduleSmsRemindService;
    @Resource
    private IUserService              userService;

    /**
     * 日程安排提醒列表
     */
    @SecurityMapping(res_name = "日程安排提醒列表", res_url = "/admin/sms/schedule_sms_remind_list.htm*", res_group = "日程安排提醒")
    @RequestMapping({ "/admin/sms/schedule_sms_remind_list.htm" })
    public ModelAndView schedule_sms_remind_list(HttpServletRequest request,
                                                 HttpServletResponse response, String pageSize,
                                                 String currentPage, String remind_name,
                                                 String true_name, String user_mobile,
                                                 Integer status) {
        ModelAndView mv = new JModelAndView("admin/sms/schedule_sms_remind_list.html", request,
            response);

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        StringBuilder hql = new StringBuilder();
        hql.append("from ScheduleSmsRemind t where 1=1");
        if (CommUtil.isNotNull(remind_name)) {
            hql.append(" and t.remind_name like :remind_name");
            paramsMap.put("remind_name", "%" + remind_name.trim() + "%");
        }
        if (CommUtil.isNotNull(true_name)) {
            hql.append(" and t.user.true_name like :true_name");
            paramsMap.put("true_name", "%" + true_name.trim() + "%");
        }
        if (CommUtil.isNotNull(user_mobile)) {
            hql.append(" and t.user.mobile like :user_mobile");
            paramsMap.put("user_mobile", "%" + user_mobile.trim() + "%");
        }
        if (CommUtil.isNotNull(status)) {
            hql.append(" and t.disabled =:disabled");
            boolean disabled = (0 == status) ? false : true;
            paramsMap.put("disabled", disabled);
        }
        hql.append(" order by t.createtime desc");
        List<ScheduleSmsRemind> list = scheduleSmsRemindService.queryScheduleSmsRemind(
            hql.toString(), paramsMap, pager);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);
        mv.addObject("remind_name", remind_name);
        mv.addObject("true_name", true_name);
        mv.addObject("user_mobile", user_mobile);
        mv.addObject("status", status);
        mv.addObject("disabled_map", Constants.DISABLED_MAP);
        return mv;
    }

    @SecurityMapping(res_name = "启用/关闭", res_url = "/admin/sms/ssr_ajax_edit_save.htm*", res_group = "日程安排提醒")
    @RequestMapping({ "/admin/sms/ssr_ajax_edit_save.htm" })
    public void ssr_ajax_edit_save(HttpServletRequest request, HttpServletResponse response,
                                   Long id, boolean disabled) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "修改启用状态成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "日程安排提醒标识为空");
        }
        if (rmg.getResult()) {
            ScheduleSmsRemind scheduleSmsRemind = scheduleSmsRemindService.getScheduleSmsRemind(id);
            if (scheduleSmsRemind == null) {
                CommUtil.setResultMsgData(rmg, false, "查不到日程安排提醒记录");
            } else {
                scheduleSmsRemind.setDisabled(disabled);
                try {
                    scheduleSmsRemindService.updateScheduleSmsRemind(scheduleSmsRemind);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "修改启用状态失败");
                }
            }
        }
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    @SecurityMapping(res_name = "查看", res_url = "/admin/sms/schedule_sms_remind_view.htm*", res_group = "日程安排提醒")
    @RequestMapping({ "/admin/sms/schedule_sms_remind_view.htm" })
    public ModelAndView schedule_sms_remind_view(HttpServletRequest request,
                                                 HttpServletResponse response, String currentPage,
                                                 Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/sms/schedule_sms_remind_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "日程安排提醒标识为空", request, response);
        }
        ScheduleSmsRemind scheduleSmsRemind = scheduleSmsRemindService.getScheduleSmsRemind(id);
        if (scheduleSmsRemind == null) {
            return CommUtil.errorPage(target_url, "找不到日程安排提醒记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/sms/schedule_sms_remind_view.html", request,
            response);
        mv.addObject("obj", scheduleSmsRemind);
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    /**
     * 添加日程安排提醒
     */
    @SecurityMapping(res_name = "添加", res_url = "/admin/sms/schedule_sms_remind_add.htm*", res_group = "日程安排提醒")
    @RequestMapping({ "/admin/sms/schedule_sms_remind_add.htm" })
    public ModelAndView schedule_sms_remind_add(HttpServletRequest request,
                                                HttpServletResponse response, String pageSize,
                                                String currentPage) {
        ModelAndView mv = new JModelAndView("admin/sms/schedule_sms_remind_add.html", request,
            response);
        mv.addObject("users", userService.queryAllUser());
        mv.addObject("currentPage", currentPage);
        mv.addObject("new_line_html", new_line_html());
        return mv;
    }

    private String new_line_html() {
        StringBuffer s = new StringBuffer();
        s.append("<div class='control-group'>");
        s.append("<label class='control-label'></label>");
        s.append("<div class='controls'>");
        s.append("<input type='text' name='remind_d' placeholder='选择日期' class='span3' readonly='readonly' />");
        s.append("<div class='input-append bootstrap-timepicker-component' style='padding-left:3px;'>");
        s.append("<input type='text' name='remind_t' class='m-ctrl-small timepicker-24' placeholder='选择时间' />");
        s.append("<span class='add-on'><i class='icon-time'></i></span>");
        s.append("</div>");
        s.append("<div class='input-append' style='padding-left:3px;'>");
        s.append("<span class='add-on' style='padding:5px 2px 3px;cursor:pointer;' onclick='add(this)'><i class='icon-plus'></i></span>");
        s.append("</div>");
        s.append("<div class='input-append' style='padding-left:3px;'>");
        s.append("<span class='add-on' style='padding:5px 2px 3px;cursor:pointer;' onclick='del(this)'><i class='icon-minus'></i></span>");
        s.append("</div>");
        s.append("</div>");
        s.append("</div>");
        return s.toString();
    }

    /**
     * 添加日程安排提醒保存
     */
    @RequestMapping({ "/admin/sms/schedule_sms_remind_add_save.htm" })
    public void schedule_sms_remind_add_save(HttpServletRequest request,
                                             HttpServletResponse response, String currentPage,
                                             Long user_id) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "日程安排提醒保存成功");
        WebForm wf = new WebForm();
        ScheduleSmsRemind scheduleSmsRemind = (ScheduleSmsRemind) wf.toPo(request,
            ScheduleSmsRemind.class);
        scheduleSmsRemind.setCreatetime(new Date());
        try {
            rmg = scheduleSmsRemindService.add_save(scheduleSmsRemind, user_id);
        } catch (Exception e) {
            CommUtil.setResultMsgData(rmg, false, "日程安排提醒保存失败");
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/sms/schedule_sms_remind_list.htm?currentPage=" + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 日程安排提醒编辑
     */
    @SecurityMapping(res_name = "编辑", res_url = "/admin/sms/schedule_sms_remind_edit.htm*", res_group = "日程安排提醒")
    @RequestMapping({ "/admin/sms/schedule_sms_remind_edit.htm" })
    public ModelAndView schedule_sms_remind_edit(HttpServletRequest request,
                                                 HttpServletResponse response, String pageSize,
                                                 String currentPage, Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/sms/schedule_sms_remind_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "日程安排提醒标识为空", request, response);
        }
        ScheduleSmsRemind scheduleSmsRemind = scheduleSmsRemindService.getScheduleSmsRemind(id);
        if (scheduleSmsRemind == null) {
            return CommUtil.errorPage(target_url, "找不到日程安排提醒记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/sms/schedule_sms_remind_edit.html", request,
            response);
        String[] remind_times = scheduleSmsRemind.getRemind_time().split(",");
        if (remind_times != null && remind_times.length > 0) {
            mv.addObject("remind_times", Arrays.asList(remind_times));
        }

        mv.addObject("obj", scheduleSmsRemind);
        mv.addObject("users", userService.queryAllUser());
        mv.addObject("currentPage", currentPage);
        mv.addObject("new_line_html", new_line_html());
        return mv;
    }

    /**
     * 日程安排提醒编辑保存
     */
    @RequestMapping({ "/admin/sms/schedule_sms_remind_edit_save.htm" })
    public void schedule_sms_remind_edit_save(HttpServletRequest request,
                                              HttpServletResponse response, Long id, Long user_id,
                                              String currentPage) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "日程安排提醒编辑保存成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "日程安排提醒标识为空");
        }
        if (rmg.getResult()) {
            ScheduleSmsRemind dbSSR = scheduleSmsRemindService.getScheduleSmsRemind(id);
            if (dbSSR == null) {
                CommUtil.setResultMsgData(rmg, false, "找不到日程安排提醒记录");
            } else {
                WebForm wf = new WebForm();
                ScheduleSmsRemind scheduleSmsRemind = (ScheduleSmsRemind) wf.toPo(request, dbSSR);
                try {
                    rmg = scheduleSmsRemindService.edit_save(scheduleSmsRemind, user_id);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "日程安排提醒编辑保存失败");
                }
            }
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/sms/schedule_sms_remind_list.htm?currentPage=" + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 日程安排提醒删除
     */
    @SecurityMapping(res_name = "删除", res_url = "/admin/sms/schedule_sms_remind_delete.htm*", res_group = "日程安排提醒")
    @RequestMapping({ "/admin/sms/schedule_sms_remind_delete.htm" })
    public ModelAndView schedule_sms_remind_delete(HttpServletRequest request,
                                                   HttpServletResponse response, Long id,
                                                   String currentPage) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/sms/schedule_sms_remind_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "日程安排提醒标识为空", request, response);
        }
        ScheduleSmsRemind scheduleSmsRemind = scheduleSmsRemindService.getScheduleSmsRemind(id);
        if (scheduleSmsRemind == null) {
            return CommUtil.errorPage(target_url, "找不到日程安排提醒记录", request, response);
        }
        try {
            scheduleSmsRemindService.delScheduleSmsRemind(scheduleSmsRemind);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, "日程安排提醒删除失败", request, response);
        }
        return CommUtil.successPage(target_url, "日程安排提醒删除成功", request, response);
    }
}
