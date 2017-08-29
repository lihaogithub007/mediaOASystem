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
import com.yuyu.soft.entity.HolidaySmsRemind;
import com.yuyu.soft.service.IHolidaySmsRemindService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.PageUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

/**
 * 节日短信问候管理                       
 * @Filename: HolidaySmsRemindController.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Controller
public class HolidaySmsRemindController {

    @Resource
    private IHolidaySmsRemindService holidaySmsRemindService;

    /**
     * 节日短信问候列表
     */
    @SecurityMapping(res_name = "节日短信问候列表", res_url = "/admin/sms/holiday_sms_remind_list.htm*", res_group = "问候短信")
    @RequestMapping({ "/admin/sms/holiday_sms_remind_list.htm" })
    public ModelAndView holiday_sms_remind_list(HttpServletRequest request,
                                                HttpServletResponse response, String pageSize,
                                                String currentPage, String remind_name,
                                                Integer remind_type, Integer status) {
        ModelAndView mv = new JModelAndView("admin/sms/holiday_sms_remind_list.html", request,
            response);

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        StringBuilder hql = new StringBuilder();
        hql.append("from HolidaySmsRemind t where 1=1");
        if (CommUtil.isNotNull(remind_name)) {
            hql.append(" and t.remind_name like :remind_name");
            paramsMap.put("remind_name", "%" + remind_name.trim() + "%");
        }
        if (CommUtil.isNotNull(remind_type)) {
            hql.append(" and t.remind_type =:remind_type");
            paramsMap.put("remind_type", remind_type);
        }
        if (CommUtil.isNotNull(status)) {
            hql.append(" and t.disabled =:disabled");
            boolean disabled = (0 == status) ? false : true;
            paramsMap.put("disabled", disabled);
        }
        hql.append(" order by t.createtime desc");
        List<HolidaySmsRemind> list = holidaySmsRemindService.queryHolidaySmsRemind(hql.toString(),
            paramsMap, pager);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);
        mv.addObject("remind_name", remind_name);
        mv.addObject("remind_type", remind_type);
        mv.addObject("status", status);
        mv.addObject("disabled_map", Constants.DISABLED_MAP);
        mv.addObject("remind_type_map", Constants.REMIND_TYLE_MAP);
        return mv;
    }

    @SecurityMapping(res_name = "启用/关闭", res_url = "/admin/sms/hsr_ajax_edit_save.htm*", res_group = "问候短信")
    @RequestMapping({ "/admin/sms/hsr_ajax_edit_save.htm" })
    public void hsr_ajax_edit_save(HttpServletRequest request, HttpServletResponse response,
                                   Long id, boolean disabled) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "修改启用状态成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "节日短信问候标识为空");
        }
        if (rmg.getResult()) {
            HolidaySmsRemind holidaySmsRemind = holidaySmsRemindService.getHolidaySmsRemind(id);
            if (holidaySmsRemind == null) {
                CommUtil.setResultMsgData(rmg, false, "查不到节日短信问候记录");
            } else {
                holidaySmsRemind.setDisabled(disabled);
                try {
                    holidaySmsRemindService.updateHolidaySmsRemind(holidaySmsRemind);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "修改启用状态失败");
                }
            }
        }
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    @SecurityMapping(res_name = "查看", res_url = "/admin/sms/holiday_sms_remind_view.htm*", res_group = "问候短信")
    @RequestMapping({ "/admin/sms/holiday_sms_remind_view.htm" })
    public ModelAndView holiday_sms_remind_view(HttpServletRequest request,
                                                HttpServletResponse response, String currentPage,
                                                Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/sms/holiday_sms_remind_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "节日短信问候标识为空", request, response);
        }
        HolidaySmsRemind holidaySmsRemind = holidaySmsRemindService.getHolidaySmsRemind(id);
        if (holidaySmsRemind == null) {
            return CommUtil.errorPage(target_url, "找不到节日短信问候记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/sms/holiday_sms_remind_view.html", request,
            response);
        mv.addObject("obj", holidaySmsRemind);
        mv.addObject("currentPage", currentPage);
        mv.addObject("remind_type_map", Constants.REMIND_TYLE_MAP);
        return mv;
    }

    /**
     * 添加节日短信问候
     */
    @SecurityMapping(res_name = "添加", res_url = "/admin/sms/holiday_sms_remind_add.htm*", res_group = "问候短信")
    @RequestMapping({ "/admin/sms/holiday_sms_remind_add.htm" })
    public ModelAndView holiday_sms_remind_add(HttpServletRequest request,
                                               HttpServletResponse response, String pageSize,
                                               String currentPage) {
        ModelAndView mv = new JModelAndView("admin/sms/holiday_sms_remind_add.html", request,
            response);
        mv.addObject("currentPage", currentPage);
        mv.addObject("remind_type_map", Constants.REMIND_TYLE_MAP);
        return mv;
    }

    /**
     * 添加节日短信问候保存
     */
    @RequestMapping({ "/admin/sms/holiday_sms_remind_add_save.htm" })
    public void holiday_sms_remind_add_save(HttpServletRequest request,
                                            HttpServletResponse response, String currentPage) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "节日短信问候保存成功");
        WebForm wf = new WebForm();
        HolidaySmsRemind holidaySmsRemind = (HolidaySmsRemind) wf.toPo(request,
            HolidaySmsRemind.class);
        holidaySmsRemind.setCreatetime(new Date());
        try {
            rmg = holidaySmsRemindService.add_save(holidaySmsRemind);
        } catch (Exception e) {
            e.printStackTrace();
            CommUtil.setResultMsgData(rmg, false, "节日短信问候保存失败");
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/sms/holiday_sms_remind_list.htm?currentPage=" + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 节日短信问候编辑
     */
    @SecurityMapping(res_name = "编辑", res_url = "/admin/sms/holiday_sms_remind_edit.htm*", res_group = "问候短信")
    @RequestMapping({ "/admin/sms/holiday_sms_remind_edit.htm" })
    public ModelAndView holiday_sms_remind_edit(HttpServletRequest request,
                                                HttpServletResponse response, String pageSize,
                                                String currentPage, Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/sms/holiday_sms_remind_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "节日短信问候标识为空", request, response);
        }
        HolidaySmsRemind holidaySmsRemind = holidaySmsRemindService.getHolidaySmsRemind(id);
        if (holidaySmsRemind == null) {
            return CommUtil.errorPage(target_url, "找不到节日短信问候记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/sms/holiday_sms_remind_edit.html", request,
            response);

        mv.addObject("obj", holidaySmsRemind);
        mv.addObject("currentPage", currentPage);
        mv.addObject("remind_type_map", Constants.REMIND_TYLE_MAP);
        return mv;
    }

    /**
     * 节日短信问候编辑保存
     */
    @RequestMapping({ "/admin/sms/holiday_sms_remind_edit_save.htm" })
    public void holiday_sms_remind_edit_save(HttpServletRequest request,
                                             HttpServletResponse response, Long id,
                                             String currentPage) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "节日短信问候编辑保存成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "节日短信问候标识为空");
        }
        if (rmg.getResult()) {
            HolidaySmsRemind dbSSR = holidaySmsRemindService.getHolidaySmsRemind(id);
            if (dbSSR == null) {
                CommUtil.setResultMsgData(rmg, false, "找不到节日短信问候记录");
            } else {
                WebForm wf = new WebForm();
                HolidaySmsRemind holidaySmsRemind = (HolidaySmsRemind) wf.toPo(request, dbSSR);
                try {
                    rmg = holidaySmsRemindService.edit_save(holidaySmsRemind);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "节日短信问候编辑保存失败");
                }
            }
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/sms/holiday_sms_remind_list.htm?currentPage=" + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 节日短信问候删除
     */
    @SecurityMapping(res_name = "删除", res_url = "/admin/sms/holiday_sms_remind_delete.htm*", res_group = "问候短信")
    @RequestMapping({ "/admin/sms/holiday_sms_remind_delete.htm" })
    public ModelAndView holiday_sms_remind_delete(HttpServletRequest request,
                                                  HttpServletResponse response, Long id,
                                                  String currentPage) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/sms/holiday_sms_remind_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "节日短信问候标识为空", request, response);
        }
        HolidaySmsRemind holidaySmsRemind = holidaySmsRemindService.getHolidaySmsRemind(id);
        if (holidaySmsRemind == null) {
            return CommUtil.errorPage(target_url, "找不到节日短信问候记录", request, response);
        }
        try {
            holidaySmsRemindService.delHolidaySmsRemind(holidaySmsRemind);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, "节日短信问候删除失败", request, response);
        }
        return CommUtil.successPage(target_url, "节日短信问候删除成功", request, response);
    }
}
