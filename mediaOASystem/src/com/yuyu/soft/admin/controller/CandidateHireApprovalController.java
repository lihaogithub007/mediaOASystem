package com.yuyu.soft.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuyu.soft.base.annotation.SecurityMapping;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.CandidateHireApproval;
import com.yuyu.soft.service.ICandidateHireApprovalService;
import com.yuyu.soft.service.IDepartmentService;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.IResumeService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.PageUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

@Controller
public class CandidateHireApprovalController {
    protected static final Log            log = LogFactory
                                                  .getLog(CandidateHireApprovalController.class);

    @Resource
    private IUserService                  userService;
    @Resource
    private IDutyService                  dutyService;
    @Resource
    private IDepartmentService            departmentService;
    @Resource
    private IResumeService                resumeService;
    @Resource
    private ICandidateHireApprovalService candidateHireApprovalService;

    @SecurityMapping(res_name = "应聘人员录用审批列表", res_url = "/admin/user/candidate_hire_approval_list.htm*", res_group = "应聘人员录用审批")
    @RequestMapping({ "/admin/user/candidate_hire_approval_list.htm" })
    public ModelAndView candidate_hire_approval_list(HttpServletRequest request,
                                                     HttpServletResponse response, String pageSize,
                                                     String currentPage, String createtime,
                                                     Integer approval_status, Integer hire_result) {
        ModelAndView mv = new JModelAndView("admin/user/candidate_hire_approval_list.html",
            request, response);
        pageSize = "30";
        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        StringBuilder hql = new StringBuilder();
        hql.append("from CandidateHireApproval t where t.disabled=false");
        if (CommUtil.isNotNull(createtime)) {
            hql.append(" and date_format(t.createtime,'%Y-%m-%d') = str_to_date('")
                .append(createtime.trim()).append("','%Y-%m-%d')");
        }
        if (hire_result != null) {
            hql.append(" and t.hire_result = :hire_result ");
            paramsMap.put("hire_result", hire_result);
        }
        if (approval_status != null) {
            hql.append(" and t.approval_status = :approval_status ");
            paramsMap.put("approval_status", approval_status);
        }
        hql.append(" order by t.createtime desc");
        List<CandidateHireApproval> list = candidateHireApprovalService.queryCandidateHireApproval(
            hql.toString(), paramsMap, pager);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);

        mv.addObject("dutys", dutyService.queryAllDuty());
        mv.addObject("departments", departmentService.queryAllDepartment());
        mv.addObject("hired_status_map", Constants.HIRED_STATUS_MAP);
        mv.addObject("approval_status_map", Constants.APPROVAL_STATUS_MAP);
        mv.addObject("is_or_not_map", Constants.IS_OR_NOT_MAP);
        mv.addObject("createtime", createtime);
        mv.addObject("hire_result", hire_result);
        mv.addObject("approval_status", approval_status);
        return mv;
    }

    @SecurityMapping(res_name = "查看", res_url = "/admin/user/candidate_hire_approval_view.htm*", res_group = "应聘人员录用审批")
    @RequestMapping({ "/admin/user/candidate_hire_approval_view.htm" })
    public ModelAndView candidate_hire_approval_view(HttpServletRequest request,
                                                     HttpServletResponse response,
                                                     String currentPage, Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/user/candidate_hire_approval_list.htm?currentPage="
                            + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "应聘人员录用审批标识为空", request, response);
        }
        CandidateHireApproval candidateHireApproval = candidateHireApprovalService
            .getCandidateHireApproval(id);
        if (candidateHireApproval == null) {
            return CommUtil.errorPage(target_url, "找不到应聘人员录用审批记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/user/candidate_hire_approval_view.html",
            request, response);

        mv.addObject("obj", candidateHireApproval);
        mv.addObject("currentPage", currentPage);
        mv.addObject("hired_status_map", Constants.HIRED_STATUS_MAP);
        mv.addObject("approval_status_map", Constants.APPROVAL_STATUS_MAP);
        mv.addObject("is_or_not_map", Constants.IS_OR_NOT_MAP);
        return mv;
    }

    @SecurityMapping(res_name = "添加", res_url = "/admin/user/candidate_hire_approval_add.htm*", res_group = "应聘人员录用审批")
    @RequestMapping({ "/admin/user/candidate_hire_approval_add.htm" })
    public ModelAndView candidate_hire_approval_add(HttpServletRequest request,
                                                    HttpServletResponse response, String pageSize,
                                                    String currentPage) {
        ModelAndView mv = new JModelAndView("admin/user/candidate_hire_approval_add.html", request,
            response);
        mv.addObject("currentPage", currentPage);
        mv.addObject("resumes", resumeService.queryResumeForApproval(1, ""));//所有面试状态简历
        mv.addObject("dutys", dutyService.queryAllDuty());
        mv.addObject("departments", departmentService.queryAllDepartment());
        mv.addObject("hired_status_map", Constants.HIRED_STATUS_MAP);
        mv.addObject("approval_status_map", Constants.APPROVAL_STATUS_MAP);
        mv.addObject("is_or_not_map", Constants.IS_OR_NOT_MAP);
        return mv;
    }

    @RequestMapping({ "/admin/user/ajaxQueryHireApprovalResumes.htm" })
    public ModelAndView ajaxQueryHireApprovalResumes(HttpServletRequest request,
                                                     HttpServletResponse response, String resume_ids) {
        if (CommUtil.isBlank(resume_ids)) {
            return null;
        }
        ModelAndView mv = new JModelAndView("admin/user/approval_resume_ajax_data.html", request,
            response);
        mv.addObject("resumes", resumeService.queryResumeForApproval(1, resume_ids));//所有面试状态简历
        mv.addObject("dutys", dutyService.queryAllDuty());
        mv.addObject("departments", departmentService.queryAllDepartment());
        mv.addObject("hired_status_map", Constants.HIRED_STATUS_MAP);
        mv.addObject("approval_status_map", Constants.APPROVAL_STATUS_MAP);
        mv.addObject("is_or_not_map", Constants.IS_OR_NOT_MAP);
        return mv;
    }

    @RequestMapping({ "/admin/user/candidate_hire_approval_add_save.htm" })
    public void candidate_hire_approval_add_save(HttpServletRequest request,
                                                 HttpServletResponse response, String currentPage,
                                                 String data_arr, String type) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "应聘人员录用审批保存成功");
        try {
            rmg = candidateHireApprovalService.add_save(data_arr, type);
        } catch (Exception e) {
            e.printStackTrace();
            CommUtil.setResultMsgData(rmg, false, "应聘人员录用审批保存失败");
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/user/candidate_hire_approval_list.htm?currentPage=" + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    @SecurityMapping(res_name = "编辑", res_url = "/admin/user/candidate_hire_approval_edit.htm*", res_group = "应聘人员录用审批")
    @RequestMapping({ "/admin/user/candidate_hire_approval_edit.htm" })
    public ModelAndView candidate_hire_approval_edit(HttpServletRequest request,
                                                     HttpServletResponse response, String pageSize,
                                                     String currentPage, Long id) {

        String target_url = CommUtil.getURL(request)
                            + "/admin/user/candidate_hire_approval_list.htm?currentPage="
                            + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "应聘人员录用审批标识为空", request, response);
        }
        CandidateHireApproval candidateHireApproval = candidateHireApprovalService
            .getCandidateHireApproval(id);
        if (candidateHireApproval == null) {
            return CommUtil.errorPage(target_url, "找不到应聘人员录用审批记录", request, response);
        }
        if (candidateHireApproval.getApproval_status() == 2) {
            return CommUtil.errorPage(target_url, "应聘人员录用审批已提交，不能修改", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/user/candidate_hire_approval_edit.html",
            request, response);
        mv.addObject("obj", candidateHireApproval);
        mv.addObject("currentPage", currentPage);
        mv.addObject("dutys", dutyService.queryAllDuty());
        mv.addObject("departments", departmentService.queryAllDepartment());
        mv.addObject("hired_status_map", Constants.HIRED_STATUS_MAP);
        mv.addObject("approval_status_map", Constants.APPROVAL_STATUS_MAP);
        mv.addObject("is_or_not_map", Constants.IS_OR_NOT_MAP);
        return mv;
    }

    @RequestMapping({ "/admin/user/candidate_hire_approval_edit_save.htm" })
    public void candidate_hire_approval_edit_save(HttpServletRequest request,
                                                  HttpServletResponse response, Long id,
                                                  String currentPage, Long duty_id,
                                                  Long department_id, String type) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "应聘人员录用审批编辑保存成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "应聘人员录用审批标识为空");
        }
        if (rmg.getResult()) {
            CandidateHireApproval dbFE = candidateHireApprovalService.getCandidateHireApproval(id);
            if (dbFE == null) {
                CommUtil.setResultMsgData(rmg, false, "找不到应聘人员录用审批记录");
            } else {
                WebForm wf = new WebForm();
                CandidateHireApproval candidateHireApproval = (CandidateHireApproval) wf.toPo(
                    request, dbFE);
                try {
                    rmg = candidateHireApprovalService.edit_save(candidateHireApproval, duty_id,
                        department_id, type);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "应聘人员录用审批编辑保存失败");
                }
            }
        }
        rmg.setData(CommUtil.getURL(request)
                    + "/admin/user/candidate_hire_approval_list.htm?currentPage=" + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    @SecurityMapping(res_name = "删除", res_url = "/admin/user/candidate_hire_approval_delete.htm*", res_group = "应聘人员录用审批")
    @RequestMapping({ "/admin/user/candidate_hire_approval_delete.htm" })
    public ModelAndView candidate_hire_approval_delete(HttpServletRequest request,
                                                       HttpServletResponse response, Long id,
                                                       String currentPage) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/user/candidate_hire_approval_list.htm?currentPage="
                            + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "应聘人员录用审批标识为空", request, response);
        }
        CandidateHireApproval candidateHireApproval = candidateHireApprovalService
            .getCandidateHireApproval(id);
        if (candidateHireApproval == null) {
            return CommUtil.errorPage(target_url, "找不到应聘人员录用审批记录", request, response);
        }
        if (candidateHireApproval.getApproval_status() == 2) {
            return CommUtil.errorPage(target_url, "应聘人员录用审批已提交，不能删除", request, response);
        }
        try {
            candidateHireApprovalService.delCandidateHireApproval(candidateHireApproval);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, "应聘人员录用审批删除失败", request, response);
        }
        return CommUtil.successPage(target_url, "应聘人员录用审批删除成功", request, response);
    }

    @SecurityMapping(res_name = "打印", res_url = "/admin/user/candidate_hire_approval_print.htm*", res_group = "应聘人员录用审批")
    @RequestMapping({ "/admin/user/candidate_hire_approval_print.htm" })
    public ModelAndView candidate_hire_approval_print(HttpServletRequest request,
                                                      HttpServletResponse response, String delIds,
                                                      String currentPage, String ids) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/user/candidate_hire_approval_list.htm?currentPage="
                            + currentPage;
        if (ids == null) {
            return CommUtil.errorPage(target_url, "录用审批标识为空", request, response);
        }
        List<CandidateHireApproval> list = candidateHireApprovalService
            .queryCandidateHireApproval(ids);

        ModelAndView mv = new JModelAndView("admin/user/candidate_hire_approval_print.html",
            request, response);
        mv.addObject("objs", list);
        mv.addObject("currentPage", currentPage);
        mv.addObject("dutys", dutyService.queryAllDuty());
        mv.addObject("departments", departmentService.queryAllDepartment());
        mv.addObject("hired_status_map", Constants.HIRED_STATUS_MAP);
        mv.addObject("approval_status_map", Constants.APPROVAL_STATUS_MAP);
        mv.addObject("is_or_not_map", Constants.IS_OR_NOT_MAP);
        mv.addObject("ids", ids);
        return mv;
    }
}
