package com.yuyu.soft.admin.controller;

import java.io.IOException;
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
import com.yuyu.soft.base.controller.BaseController;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.Attachments;
import com.yuyu.soft.entity.Department;
import com.yuyu.soft.entity.Duty;
import com.yuyu.soft.entity.VacationApply;
import com.yuyu.soft.entity.VacationApplyApproval;
import com.yuyu.soft.entity.VacationApplyDetails;
import com.yuyu.soft.service.IAttachmentsService;
import com.yuyu.soft.service.IAttendanceRecordService;
import com.yuyu.soft.service.IDepartmentService;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.service.IVacationApplyApprovalService;
import com.yuyu.soft.service.IVacationApplyDetailsService;
import com.yuyu.soft.service.IVacationApplyService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.PageUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

/**
 * 请假申请
 *                       
 * @Filename: WorkOvertimeController.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Controller
public class VacationApplyController {

    @Resource
    private IVacationApplyService         vacationApplyService;
    @Resource
    private IVacationApplyDetailsService  vacationApplyDetailsService;
    @Resource
    private IVacationApplyApprovalService vacationApplyApprovalService;
    @Resource
    private IDepartmentService            departmentService;
    @Resource
    private IDutyService                  dutyService;
    @Resource
    private IUserService                  userService;
    @Resource
    private IAttendanceRecordService      attendanceRecordService;
    @Resource
    private IAttachmentsService           attachmentsService;

    @SecurityMapping(res_name = "请假申请列表", res_url = "/admin/work_attendance/vacation_apply_list.htm*", res_group = "请假申请")
    @RequestMapping({ "/admin/work_attendance/vacation_apply_list.htm" })
    public ModelAndView vacation_apply_list(HttpServletRequest request,
                                            HttpServletResponse response, String pageSize,
                                            String currentPage, Long department_id, Long duty_id,
                                            String true_name, String year) {
        ModelAndView mv = new JModelAndView("admin/work_attendance/vacation_apply_list.html",
            request, response);

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        StringBuilder hql = new StringBuilder();
        hql.append("from VacationApply t where t.disabled = false");

        if (CommUtil.isNotNull(department_id)) {
            hql.append(" and t.user.department.id =:department_id ");
            paramsMap.put("department_id", department_id);
        }
        //岗位查询条件
        if (CommUtil.isNotNull(duty_id)) {
            hql.append(" and t.user.duty.id =:duty_id ");
            paramsMap.put("duty_id", duty_id);
        }
        if (CommUtil.isNotNull(true_name)) {
            hql.append(" and t.user.true_name like :true_name ");
            paramsMap.put("true_name", "%" + true_name.trim() + "%");
        }
        if (CommUtil.isBlank(year)) {
            year = String.valueOf(CommUtil.getThisYear());
        }
        //休假年份
        hql.append(" and (date_format(t.vacation_begin_date,'%Y') = date_format('")
            .append(year.trim() + "-01-01").append("','%Y')");
        hql.append(" or date_format(t.vacation_end_date,'%Y') = date_format('")
            .append(year.trim() + "-01-01").append("','%Y'))");

        hql.append(" order by t.createtime desc");

        List<VacationApply> list = vacationApplyService.queryVacationApply(hql.toString(),
            paramsMap, pager);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);

        List<Department> departments = departmentService.queryAllDepartment();
        List<Duty> dutys = dutyService.queryAllDuty();
        mv.addObject("departments", departments);
        mv.addObject("dutys", dutys);
        mv.addObject("department_id", department_id);
        mv.addObject("duty_id", duty_id);
        mv.addObject("true_name", true_name);
        mv.addObject("year", year);
        return mv;
    }

    @SecurityMapping(res_name = "查看", res_url = "/admin/work_attendance/vacation_apply_view.htm*", res_group = "请假申请")
    @RequestMapping({ "/admin/work_attendance/vacation_apply_view.htm" })
    public ModelAndView vacation_apply_view(HttpServletRequest request,
                                            HttpServletResponse response, String currentPage,
                                            Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/work_attendance/vacation_apply_list.htm?currentPage="
                            + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "请假申请标识为空", request, response);
        }
        VacationApply vacationApply = vacationApplyService.getVacationApply(id);
        if (vacationApply == null) {
            return CommUtil.errorPage(target_url, "找不到请假申请记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/work_attendance/vacation_apply_view.html",
            request, response);
        List<VacationApplyDetails> vadList = vacationApplyDetailsService.queryVacationApplyDetails(
            vacationApply.getUser().getId(), vacationApply.getId());
        List<VacationApplyApproval> vaaList = vacationApplyApprovalService
            .queryVacationApplyApproval(vacationApply.getId());

        mv.addObject("obj", vacationApply);
        mv.addObject("currentPage", currentPage);
        mv.addObject("leave_type_map", Constants.LEAVE_TYPE_MAP);//请假种类
        mv.addObject("vadList", vadList);
        mv.addObject("vaaList", vaaList);
        mv.addObject("is_show", get_show_status(vadList));
        mv.addObject("attachments",
            attachmentsService.getAttachmentsByIds(vacationApply.getAttach_ids()));
        return mv;
    }

    //判断休假类型
    private boolean get_show_status(List<VacationApplyDetails> vadList) {
        if (vadList == null || vadList.size() == 0) {
            return false;
        }
        for (VacationApplyDetails vad : vadList) {
            Integer leave_type = vad.getLeave_type();
            if (2 == leave_type || 5 == leave_type || 6 == leave_type) {
                return true;
            }
        }
        return false;
    }

    /**
     * 添加用户
     */
    @SecurityMapping(res_name = "添加", res_url = "/admin/work_attendance/vacation_apply_add.htm*", res_group = "请假申请")
    @RequestMapping({ "/admin/work_attendance/vacation_apply_add.htm" })
    public ModelAndView vacation_apply_add(HttpServletRequest request,
                                           HttpServletResponse response, String pageSize,
                                           String currentPage) {
        ModelAndView mv = new JModelAndView("admin/work_attendance/vacation_apply_add.html",
            request, response);
        mv.addObject("users", userService.queryAllUser());
        mv.addObject("leave_type_map", Constants.LEAVE_TYPE_MAP);//请假种类
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    @RequestMapping({ "/admin/work_attendance/ajaxAddVacationApplyDetails.htm" })
    public ModelAndView ajaxAddVacationApplyDetails(HttpServletRequest request,
                                                    HttpServletResponse response, String count) {
        ModelAndView mv = new JModelAndView(
            "admin/work_attendance/vacation_apply_details_ajax_data.html", request, response);
        mv.addObject("leave_type_map", Constants.LEAVE_TYPE_MAP);//请假种类
        mv.addObject("count", count);
        return mv;
    }

    @RequestMapping({ "/admin/work_attendance/ajaxAddVacationApplyApproval.htm" })
    public ModelAndView ajaxAddVacationApplyApproval(HttpServletRequest request,
                                                     HttpServletResponse response, String count) {
        ModelAndView mv = new JModelAndView(
            "admin/work_attendance/vacation_apply_approval_ajax_data.html", request, response);
        mv.addObject("users", userService.queryAllUser());
        mv.addObject("count", count);
        return mv;
    }

    /**
     * 获取累计年假
     */
    @RequestMapping({ "/admin/work_attendance/ajax_get_total_annual_leave_days.htm" })
    public void ajax_get_total_annual_leave_days(HttpServletRequest request,
                                                 HttpServletResponse response, String currentPage,
                                                 Long user_id, String vacation_begin_date,
                                                 String vacation_end_date) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "获取信息成功");
        String data = attendanceRecordService.get_total_annual_leave_days_msg(user_id,
            vacation_begin_date, vacation_end_date);
        rmg.setData(data);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    @RequestMapping({ "/admin/work_attendance/vaUpload.htm" })
    public void upload(HttpServletRequest request, HttpServletResponse response) {
        List<Attachments> list = null;
        try {
            list = BaseController
                .uploadify(request, response, "vacation_apply", attachmentsService);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list == null || list.size() == 0) {
            return;
        }
        System.out.println(BaseController.write2JsonStr(request, list));
        JsonUtil.writeMsg(response, BaseController.write2JsonStr(request, list));
    }

    @RequestMapping({ "/admin/work_attendance/vacation_apply_add_save.htm" })
    public void vacation_apply_add_save(HttpServletRequest request, HttpServletResponse response,
                                        String currentPage, Long user_id, String vad_arr,
                                        String vaa_arr, String attachments_ids) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "请假申请保存成功");
        try {
            WebForm wf = new WebForm();
            VacationApply vacationApply = (VacationApply) wf.toPo(request, VacationApply.class);
            vacationApply.setAttach_ids(attachments_ids);
            rmg = vacationApplyService.add_save(vacationApply, user_id, vad_arr, vaa_arr);
        } catch (Exception e) {
            e.printStackTrace();
            CommUtil.setResultMsgData(rmg, false, e.getMessage());
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/work_attendance/vacation_apply_list.htm?currentPage=" + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    @SecurityMapping(res_name = "编辑", res_url = "/admin/work_attendance/vacation_apply_edit.htm*", res_group = "请假申请")
    @RequestMapping({ "/admin/work_attendance/vacation_apply_edit.htm" })
    public ModelAndView vacation_apply_edit(HttpServletRequest request,
                                            HttpServletResponse response, String currentPage,
                                            Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/work_attendance/vacation_apply_list.htm?currentPage="
                            + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "请假申请标识为空", request, response);
        }
        VacationApply vacationApply = vacationApplyService.getVacationApply(id);
        if (vacationApply == null) {
            return CommUtil.errorPage(target_url, "找不到请假申请记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/work_attendance/vacation_apply_edit.html",
            request, response);
        List<VacationApplyDetails> vadList = vacationApplyDetailsService.queryVacationApplyDetails(
            vacationApply.getUser().getId(), vacationApply.getId());
        List<VacationApplyApproval> vaaList = vacationApplyApprovalService
            .queryVacationApplyApproval(vacationApply.getId());

        mv.addObject("obj", vacationApply);
        mv.addObject("users", userService.queryAllUser());
        mv.addObject("currentPage", currentPage);
        mv.addObject("leave_type_map", Constants.LEAVE_TYPE_MAP);//请假种类
        mv.addObject("vadList", vadList);
        mv.addObject("vaaList", vaaList);
        mv.addObject("is_show", get_show_status(vadList));
        mv.addObject("attachments",
            attachmentsService.getAttachmentsByIds(vacationApply.getAttach_ids()));
        return mv;
    }

    @RequestMapping({ "/admin/work_attendance/vacation_apply_edit_save.htm" })
    public void vacation_apply_edit_save(HttpServletRequest request, HttpServletResponse response,
                                         String currentPage, Long id, Long user_id, String vad_arr,
                                         String vaa_arr, String attachments_ids) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "请假申请编辑保存成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "用户标识为空");
        }
        if (rmg.getResult()) {
            try {
                WebForm wf = new WebForm();
                VacationApply dbVacationApply = vacationApplyService.getVacationApply(id);
                VacationApply vacationApply = (VacationApply) wf.toPo(request, dbVacationApply);
                vacationApply.setAttach_ids(attachments_ids);
                rmg = vacationApplyService.edit_save(vacationApply, user_id, vad_arr, vaa_arr);
            } catch (Exception e) {
                e.printStackTrace();
                CommUtil.setResultMsgData(rmg, false, e.getMessage());
            }
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/work_attendance/vacation_apply_list.htm?currentPage=" + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    @SecurityMapping(res_name = "取消", res_url = "/admin/work_attendance/vacation_apply_cancel.htm*", res_group = "请假申请")
    @RequestMapping({ "/admin/work_attendance/vacation_apply_cancel.htm" })
    public ModelAndView vacation_apply_cancel(HttpServletRequest request,
                                              HttpServletResponse response, Long id,
                                              String currentPage) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/work_attendance/vacation_apply_list.htm?currentPage="
                            + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "请假申请标识为空", request, response);
        }
        VacationApply vacationApply = vacationApplyService.getVacationApply(id);
        if (vacationApply == null) {
            return CommUtil.errorPage(target_url, "找不到请假申请记录", request, response);
        }
        try {
            vacationApplyService.cancel_save(vacationApply);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, e.getMessage(), request, response);
        }
        return CommUtil.successPage(target_url, "请假申请取消成功", request, response);
    }

    @SecurityMapping(res_name = "删除", res_url = "/admin/work_attendance/vacation_apply_delete.htm*", res_group = "请假申请")
    @RequestMapping({ "/admin/work_attendance/vacation_apply_delete.htm" })
    public ModelAndView user_delete(HttpServletRequest request, HttpServletResponse response,
                                    Long id, String currentPage) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/work_attendance/vacation_apply_list.htm?currentPage="
                            + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "请假申请标识为空", request, response);
        }
        VacationApply vacationApply = vacationApplyService.getVacationApply(id);
        if (vacationApply == null) {
            return CommUtil.errorPage(target_url, "找不到请假申请记录", request, response);
        }
        try {
            vacationApplyService.delete_save(vacationApply);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, e.getMessage(), request, response);
        }
        return CommUtil.successPage(target_url, "请假申请删除成功", request, response);
    }

}
