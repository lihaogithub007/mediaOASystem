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
import com.yuyu.soft.entity.ContractSmsRemind;
import com.yuyu.soft.service.IContractSmsRemindService;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.PageUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

/**
 * 合同到期提醒管理                       
 *
 */
@Controller
public class ContractSmsRemindController {

    @Resource
    private IContractSmsRemindService contractSmsRemindService;

    @Resource
    private IUserService              userService;

    @Resource
    private IDutyService              dutyService;

    /**
     * 合同到期提醒列表
     */
    @SecurityMapping(res_name = "合同到期提醒列表", res_url = "/admin/sms/contract_sms_remind_list.htm*", res_group = "合同到期提醒")
    @RequestMapping({ "/admin/sms/contract_sms_remind_list.htm" })
    public ModelAndView contract_sms_remind_list(HttpServletRequest request,
                                                 HttpServletResponse response, String pageSize,
                                                 String currentPage, Integer remind_type,
                                                 String true_name, String duty_name, Integer status) {
        ModelAndView mv = new JModelAndView("admin/sms/contract_sms_remind_list.html", request,
            response);

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        StringBuilder hql = new StringBuilder();
        hql.append("from ContractSmsRemind t where 1=1");
        if (CommUtil.isNotNull(remind_type)) {
            hql.append(" and t.remind_type =:remind_type");
            paramsMap.put("remind_type", remind_type);
        }
        if (CommUtil.isNotNull(true_name)) {
            hql.append(" and t.user.true_name like :true_name");
            paramsMap.put("true_name", "%" + true_name.trim() + "%");
        }
        if (CommUtil.isNotNull(duty_name)) {
            hql.append(" and t.duty.duty_name like :duty_name");
            paramsMap.put("duty_name", "%" + duty_name.trim() + "%");
        }
        if (CommUtil.isNotNull(status)) {
            hql.append(" and t.disabled =:disabled");
            boolean disabled = (0 == status) ? false : true;
            paramsMap.put("disabled", disabled);
        }
        hql.append(" order by t.createtime desc");
        List<ContractSmsRemind> list = contractSmsRemindService.queryContractSmsRemind(
            hql.toString(), paramsMap, pager);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);

        mv.addObject("dutys", dutyService.queryAllDuty());
        mv.addObject("remind_type", remind_type);
        mv.addObject("true_name", true_name);
        mv.addObject("duty_name", duty_name);
        mv.addObject("status", status);
        mv.addObject("disabled_map", Constants.DISABLED_MAP);
        return mv;
    }

    @SecurityMapping(res_name = "启用/关闭", res_url = "/admin/sms/csr_ajax_edit_save.htm*", res_group = "合同到期提醒")
    @RequestMapping({ "/admin/sms/csr_ajax_edit_save.htm" })
    public void csr_ajax_edit_save(HttpServletRequest request, HttpServletResponse response,
                                   Long id, boolean disabled) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "修改启用状态成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "合同到期提醒标识为空");
        }
        if (rmg.getResult()) {
            ContractSmsRemind contractSmsRemind = contractSmsRemindService.getContractSmsRemind(id);
            if (contractSmsRemind == null) {
                CommUtil.setResultMsgData(rmg, false, "查不到合同到期提醒记录");
            } else {
                contractSmsRemind.setDisabled(disabled);
                try {
                    contractSmsRemindService.updateContractSmsRemind(contractSmsRemind);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "修改启用状态失败");
                }
            }
        }
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    @SecurityMapping(res_name = "查看", res_url = "/admin/sms/contract_sms_remind_view.htm*", res_group = "合同到期提醒")
    @RequestMapping({ "/admin/sms/contract_sms_remind_view.htm" })
    public ModelAndView contract_sms_remind_view(HttpServletRequest request,
                                                 HttpServletResponse response, String currentPage,
                                                 Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/sms/schedule_sms_remind_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "合同到期提醒标识为空", request, response);
        }
        ContractSmsRemind contractSmsRemind = contractSmsRemindService.getContractSmsRemind(id);
        if (contractSmsRemind == null) {
            return CommUtil.errorPage(target_url, "找不到合同到期提醒记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/sms/contract_sms_remind_view.html", request,
            response);
        mv.addObject("obj", contractSmsRemind);
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    /**
     * 添加合同到期提醒
     */
    @SecurityMapping(res_name = "添加", res_url = "/admin/sms/contract_sms_remind_add.htm*", res_group = "合同到期提醒")
    @RequestMapping({ "/admin/sms/contract_sms_remind_add.htm" })
    public ModelAndView contract_sms_remind_add(HttpServletRequest request,
                                                HttpServletResponse response, String pageSize,
                                                String currentPage) {
        ModelAndView mv = new JModelAndView("admin/sms/contract_sms_remind_add.html", request,
            response);

        mv.addObject("users", userService.queryAllUser());
        mv.addObject("dutys", dutyService.queryAllDuty());
        return mv;
    }

    /**
     * 添加合同到期提醒保存
     */
    @RequestMapping({ "/admin/sms/contract_sms_remind_add_save.htm" })
    public void contract_sms_remind_add_save(HttpServletRequest request,
                                             HttpServletResponse response, String currentPage,
                                             Long user_id, Long duty_id) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "合同到期提醒保存成功");
        WebForm wf = new WebForm();
        ContractSmsRemind contractSmsRemind = (ContractSmsRemind) wf.toPo(request,
            ContractSmsRemind.class);
        contractSmsRemind.setCreatetime(new Date());
        try {
            rmg = contractSmsRemindService.add_save(contractSmsRemind, user_id, duty_id);
        } catch (Exception e) {
            CommUtil.setResultMsgData(rmg, false, "合同到期提醒保存失败");
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/sms/contract_sms_remind_list.htm?currentPage=" + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 合同到期提醒编辑
     */
    @SecurityMapping(res_name = "编辑", res_url = "/admin/sms/contract_sms_remind_edit.htm*", res_group = "合同到期提醒")
    @RequestMapping({ "/admin/sms/contract_sms_remind_edit.htm" })
    public ModelAndView contract_sms_remind_edit(HttpServletRequest request,
                                                 HttpServletResponse response, String pageSize,
                                                 String currentPage, Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/sms/contract_sms_remind_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "合同到期提醒标识为空", request, response);
        }
        ContractSmsRemind contractSmsRemind = contractSmsRemindService.getContractSmsRemind(id);
        if (contractSmsRemind == null) {
            return CommUtil.errorPage(target_url, "找不到合同到期提醒记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/sms/contract_sms_remind_edit.html", request,
            response);

        mv.addObject("obj", contractSmsRemind);
        mv.addObject("users", userService.queryAllUser());
        mv.addObject("dutys", dutyService.queryAllDuty());
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    /**
     * 合同到期提醒编辑保存
     */
    @RequestMapping({ "/admin/sms/contract_sms_remind_edit_save.htm" })
    public void contract_sms_remind_edit_save(HttpServletRequest request,
                                              HttpServletResponse response, Long id, Long user_id,
                                              Long duty_id, String currentPage) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "合同到期提醒编辑保存成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "合同到期提醒标识为空");
        }
        if (rmg.getResult()) {
            ContractSmsRemind csr = contractSmsRemindService.getContractSmsRemind(id);
            if (csr == null) {
                CommUtil.setResultMsgData(rmg, false, "找不到合同到期提醒记录");
            } else {
                WebForm wf = new WebForm();
                ContractSmsRemind contractSmsRemind = (ContractSmsRemind) wf.toPo(request, csr);
                try {
                    rmg = contractSmsRemindService.edit_save(contractSmsRemind, user_id, duty_id);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "合同到期提醒编辑保存失败");
                }
            }
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/sms/contract_sms_remind_list.htm?currentPage=" + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 合同到期提醒删除
     */
    @SecurityMapping(res_name = "删除", res_url = "/admin/sms/contract_sms_remind_delete.htm*", res_group = "合同到期提醒")
    @RequestMapping({ "/admin/sms/contract_sms_remind_delete.htm" })
    public ModelAndView contract_sms_remind_delete(HttpServletRequest request,
                                                   HttpServletResponse response, Long id,
                                                   String currentPage) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/sms/contract_sms_remind_list.htm?currentPage=" + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "合同到期提醒标识为空", request, response);
        }
        ContractSmsRemind contractSmsRemind = contractSmsRemindService.getContractSmsRemind(id);
        if (contractSmsRemind == null) {
            return CommUtil.errorPage(target_url, "找不到合同到期提醒记录", request, response);
        }
        try {
            contractSmsRemindService.delContractSmsRemind(contractSmsRemind);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, "合同到期提醒设置删除失败", request, response);
        }
        return CommUtil.successPage(target_url, "合同到期提醒设置删除成功", request, response);
    }
}
