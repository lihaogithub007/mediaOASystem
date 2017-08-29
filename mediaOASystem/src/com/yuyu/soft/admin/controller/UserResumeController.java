package com.yuyu.soft.admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
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

import com.yuyu.soft.base.controller.BaseController;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.Attachments;
import com.yuyu.soft.entity.Resume;
import com.yuyu.soft.entity.ResumeEducation;
import com.yuyu.soft.entity.ResumeFamilyMember;
import com.yuyu.soft.entity.ResumeOthers;
import com.yuyu.soft.entity.ResumeWork;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IAttachmentsService;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.IResumeEducationService;
import com.yuyu.soft.service.IResumeFamilyMemberService;
import com.yuyu.soft.service.IResumeOthersService;
import com.yuyu.soft.service.IResumeService;
import com.yuyu.soft.service.IResumeWorkService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CacheUtils;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.sms.MeilianSmsSender;
import com.yuyu.soft.util.word.WordUtil;

/**
 * 简历管理                      
 * @Filename: UserResumeController.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Controller
public class UserResumeController {

    protected static final Log         log = LogFactory.getLog(UserResumeController.class);
    @Resource
    private IUserService               userService;
    @Resource
    private IResumeService             resumeService;
    @Resource
    private IResumeWorkService         resumeWorkService;
    @Resource
    private IResumeEducationService    resumeEducationService;
    @Resource
    private IResumeFamilyMemberService resumeFamilyMemberService;
    @Resource
    private IResumeOthersService       resumeOthersService;
    @Resource
    private IDutyService               dutyService;
    @Resource
    private IAttachmentsService        attachmentsService;

    /**
     * 手机号验证页面
     */
    @RequestMapping({ "/user/user_resume_mobile_validate.htm" })
    public ModelAndView user_list(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new JModelAndView(
            "admin/user/user_resume/user_resume_mobile_validate.html", request, response);
        return mv;
    }

    /**
     * 获取手机号验证码
     */
    @RequestMapping({ "/getUserResumeMobileCheckCode.htm" })
    public void getUserResumeMobileCheckCode(HttpServletRequest request,
                                             HttpServletResponse response, String mobile) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "短信发送成功");
        if (CommUtil.isBlank(mobile)) {
            CommUtil.setResultMsgData(rmg, false, "手机号不能为空");
        }
        mobile = mobile.trim();
        if (rmg.getResult() && !CommUtil.matcherMobile(mobile)) {
            CommUtil.setResultMsgData(rmg, false, "手机号格式不正确");
        }
        if (rmg.getResult()) {
            List<User> users = userService.queryUserByMobile(mobile);
            if (users != null && users.size() > 0) {
                CommUtil.setResultMsgData(rmg, false, "已存在员工使用该手机号，请更换手机号重试");
            } else {
                String code = CommUtil.randomString(4).toUpperCase();
                String content = "您好，此短信为新媒体智能管理系统简历验证短信，验证码为：" + code + "，如非本人操作请忽略";
                boolean ret = false;
                try {
                    ret = MeilianSmsSender.sendMsg(mobile, content);
                    log.info(content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (ret) {
                    CacheUtils.put("user_resume_mobile_check_code", "user_resume_mobile_check_"
                                                                    + mobile, code);
                } else {
                    CommUtil.setResultMsgData(rmg, false, "短信发送失败");
                }
            }
        }

        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 验证手机号、验证码
     */
    @RequestMapping({ "/user/user_resume_mobile_check.htm" })
    public void user_resume_mobile_check(HttpServletRequest request, HttpServletResponse response,
                                         String mobile, String code) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "验证码验证成功");
        if (CommUtil.isBlank(mobile)) {
            CommUtil.setResultMsgData(rmg, false, "手机号不能为空");
        }
        mobile = mobile.trim();
        if (rmg.getResult() && !CommUtil.matcherMobile(mobile)) {
            CommUtil.setResultMsgData(rmg, false, "手机号格式不正确");
        }
        if (rmg.getResult() && CommUtil.isBlank(code)) {
            CommUtil.setResultMsgData(rmg, false, "验证码不能为空");
        }
        if (rmg.getResult()) {
            mobile = mobile.trim();
            List<User> users = userService.queryUserByMobile(mobile);
            if (users != null && users.size() > 0) {
                CommUtil.setResultMsgData(rmg, false, "已存在员工使用该手机号，请更换手机号重试");
            } else {
                Object cache_code = CacheUtils.get("user_resume_mobile_check_code",
                    "user_resume_mobile_check_" + mobile);

                if (!CommUtil.isNotNull(cache_code)) {
                    CommUtil.setResultMsgData(rmg, false, "非验证码手机或验证码已失效，请重新获取验证");
                } else if (!code.trim().equalsIgnoreCase(cache_code.toString())) {
                    CommUtil.setResultMsgData(rmg, false, "验证码不正确");
                } else {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("mobile", mobile);
                    map.put("code", code);
                    map.put("old_url", "/user_resume_mobile_check.htm?mobile=" + mobile);
                    rmg.setData(map);
                }
            }
        }
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 简历编辑页面
     */
    @RequestMapping({ "/user/user_resume_edit.htm" })
    public ModelAndView user_resume_edit(HttpServletRequest request, HttpServletResponse response,
                                         String hid_mobile, String hid_code, String hid_url) {
        if (CommUtil.isBlank(hid_mobile) || CommUtil.isBlank(hid_code) || CommUtil.isBlank(hid_url)
            || hid_url.trim().indexOf("user_resume_mobile_check.htm?mobile=" + hid_mobile) < 0) {
            return new JModelAndView("admin/user/user_resume/user_resume_mobile_validate.html",
                request, response);
        }
        hid_mobile = hid_mobile.trim();
        List<User> users = userService.queryUserByMobile(hid_mobile);
        if (users != null && users.size() > 0) {
            return CommUtil.fullErrorPage(CommUtil.getURL(request)
                                          + "/user/user_resume_mobile_validate.htm",
                "已存在员工使用该手机号，请更换手机号重试", request, response);
        }

        List<Resume> resumeList = resumeService.queryResumeByMobile(hid_mobile);
        try {
            if (resumeList.size() == 0) {
                resumeService.add_save(hid_mobile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resumeList = resumeService.queryResumeByMobile(hid_mobile);
        if (resumeList.size() == 0) {
            return CommUtil.fullErrorPage(CommUtil.getURL(request)
                                          + "/user/user_resume_mobile_validate.htm", "系统错误，请稍后重试",
                request, response);
        } else if (resumeList.size() == 1) {
            Resume resume = resumeList.get(0);
            if (2 == resume.getResume_status()) {
                resume.setResume_status(1);
                this.resumeService.updateResume(resume);
            }
        } else {
            return CommUtil.fullErrorPage(CommUtil.getURL(request)
                                          + "/user/user_resume_mobile_validate.htm",
                "该手机号存在多条简历记录，请更换手机号后重试", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/user/user_resume/user_resume_edit.html",
            request, response);
        Resume resume = resumeList.get(0);
        mv.addObject("resume", resume);
        mv.addObject("resumeWorkList", resumeWorkService.queryResumeWork(resume.getId()));//工作经历
        mv.addObject("resumeEducationList",
            resumeEducationService.queryResumeEducation(resume.getId()));//教育经历
        mv.addObject("resumeFamilyMemberList",
            resumeFamilyMemberService.queryResumeFamilyMember(resume.getId()));//家庭成员

        mv.addObject("dutys", dutyService.queryAllDuty());//岗位
        mv.addObject("sex_map", Constants.SEX_MAP);//性别
        mv.addObject("political_status_map", Constants.POLITICAL_STATUS_MAP);//政治面貌
        mv.addObject("marriage_status_map", Constants.MARRIAGE_STATUS_MAP);//婚姻状况
        mv.addObject("family_relationship_map", Constants.FAMILY_RELATIONSHIP_MAP);//家庭成员与本人关系
        mv.addObject("work_type_map", Constants.WORK_TYPE_MAP);//工作性质
        mv.addObject("is_or_not_map", Constants.IS_OR_NOT_MAP);//是否
        mv.addObject("have_or_not_map", Constants.HAVE_OR_NOT_MAP);//有无
        mv.addObject("edu_type_map", Constants.EDU_TYPE_MAP);//学习性质
        mv.addObject("dimission_reason_map", Constants.DIMISSION_REASON_MAP);//离职原因
        mv.addObject("attachments", attachmentsService.getAttachmentsByIds(resume.getAttach_ids()));
        return mv;
    }

    /**
     * 简历基础信息
     */
    @RequestMapping({ "/resume/resume_base.htm" })
    public ModelAndView resume_base(HttpServletRequest request, HttpServletResponse response,
                                    Long resume_id, String action, Long duty_id) {
        if (!verify_resume_id(resume_id)) {
            return null;
        }
        Resume resume = resumeService.getResume(resume_id);
        ModelAndView mv = null;
        if (CommUtil.isBlank(action) || "cancel".equals(action) || "view".equals(action)) {
            mv = new JModelAndView("admin/user/user_resume/user_resume_base_view.html", request,
                response);
        } else if ("modify".equals(action)) {
            mv = new JModelAndView("admin/user/user_resume/user_resume_base_edit.html", request,
                response);
        } else if ("save".equals(action)) {
            try {
                resumeService.resume_save(request, resume.getId(), duty_id);
            } catch (Exception e) {
            }
            mv = new JModelAndView("admin/user/user_resume/user_resume_base_view.html", request,
                response);
        }
        mv.addObject("resume", resume);
        mv.addObject("sex_map", Constants.SEX_MAP);//性别
        mv.addObject("political_status_map", Constants.POLITICAL_STATUS_MAP);//政治面貌
        mv.addObject("marriage_status_map", Constants.MARRIAGE_STATUS_MAP);//婚姻状况
        mv.addObject("dutys", dutyService.queryAllDuty());//岗位
        return mv;
    }

    /**
     * 简历工作经历
     */
    @RequestMapping({ "/resume/resume_work.htm" })
    public ModelAndView resume_work(HttpServletRequest request, HttpServletResponse response,
                                    Long resume_id, String action, Long work_id) {
        if (!verify_resume_id(resume_id)) {
            return null;
        }
        Resume resume = resumeService.getResume(resume_id);
        ModelAndView mv = null;
        if (CommUtil.isBlank(action) || "cancel".equals(action) || "view".equals(action)) {
            mv = new JModelAndView("admin/user/user_resume/user_resume_work_view.html", request,
                response);
        } else if ("new".equals(action)) {
            mv = new JModelAndView("admin/user/user_resume/user_resume_work_edit.html", request,
                response);
        } else if ("modify".equals(action)) {
            ResumeWork resumeWork = null;
            if (work_id != null) {
                resumeWork = resumeWorkService.getResumeWork(work_id);
            }
            mv = new JModelAndView("admin/user/user_resume/user_resume_work_edit.html", request,
                response);
            if (resumeWork != null && resumeWork.getId() != null) {
                mv.addObject("resumeWork", resumeWork);
            }
        } else if ("delete".equals(action)) {
            if (work_id != null) {
                try {
                    ResumeWork resumeWork = resumeWorkService.getResumeWork(work_id);
                    resumeWorkService.delResumeWork(resumeWork);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mv = new JModelAndView("admin/user/user_resume/user_resume_work_view.html", request,
                response);
        } else if ("save".equals(action)) {
            try {
                resumeWorkService.resume_work_save(request, resume.getId());
            } catch (Exception e) {
            }
            mv = new JModelAndView("admin/user/user_resume/user_resume_work_view.html", request,
                response);
        }
        mv.addObject("resume", resume);
        mv.addObject("resumeWorkList", resumeWorkService.queryResumeWork(resume.getId()));//工作经历
        mv.addObject("work_type_map", Constants.WORK_TYPE_MAP);//工作性质
        return mv;
    }

    /**
     * 简历教育经历
     */
    @RequestMapping({ "/resume/resume_education.htm" })
    public ModelAndView resume_education(HttpServletRequest request, HttpServletResponse response,
                                         Long resume_id, String action, Long education_id) {
        if (!verify_resume_id(resume_id)) {
            return null;
        }
        Resume resume = resumeService.getResume(resume_id);
        ModelAndView mv = null;
        if (CommUtil.isBlank(action) || "cancel".equals(action) || "view".equals(action)) {
            mv = new JModelAndView("admin/user/user_resume/user_resume_education_view.html",
                request, response);
        } else if ("new".equals(action)) {
            mv = new JModelAndView("admin/user/user_resume/user_resume_education_edit.html",
                request, response);
        } else if ("modify".equals(action)) {
            ResumeEducation resumeEducation = null;
            if (education_id != null) {
                resumeEducation = resumeEducationService.getResumeEducation(education_id);
            }
            mv = new JModelAndView("admin/user/user_resume/user_resume_education_edit.html",
                request, response);
            if (resumeEducation != null && resumeEducation.getId() != null) {
                mv.addObject("resumeEducation", resumeEducation);
            }
        } else if ("delete".equals(action)) {
            if (education_id != null) {
                try {
                    ResumeEducation resumeEducation = resumeEducationService
                        .getResumeEducation(education_id);
                    resumeEducationService.delResumeEducation(resumeEducation);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mv = new JModelAndView("admin/user/user_resume/user_resume_education_view.html",
                request, response);
        } else if ("save".equals(action)) {
            try {
                resumeEducationService.resume_education_save(request, resume.getId());
            } catch (Exception e) {
            }
            mv = new JModelAndView("admin/user/user_resume/user_resume_education_view.html",
                request, response);
        }
        mv.addObject("resume", resume);
        mv.addObject("resumeEducationList",
            resumeEducationService.queryResumeEducation(resume.getId()));//教育经历
        mv.addObject("edu_type_map", Constants.EDU_TYPE_MAP);//学习性质
        return mv;
    }

    /**
     * 简历家庭成员
     */
    @RequestMapping({ "/resume/resume_family_member.htm" })
    public ModelAndView resume_family_member(HttpServletRequest request,
                                             HttpServletResponse response, Long resume_id,
                                             String action, Long familymember_id) {
        if (!verify_resume_id(resume_id)) {
            return null;
        }
        Resume resume = resumeService.getResume(resume_id);
        ModelAndView mv = null;
        if (CommUtil.isBlank(action) || "cancel".equals(action) || "view".equals(action)) {
            mv = new JModelAndView("admin/user/user_resume/user_resume_family_member_view.html",
                request, response);
        } else if ("new".equals(action)) {
            mv = new JModelAndView("admin/user/user_resume/user_resume_family_member_edit.html",
                request, response);
        } else if ("modify".equals(action)) {
            ResumeFamilyMember resumeFamilyMember = null;
            if (familymember_id != null) {
                resumeFamilyMember = resumeFamilyMemberService
                    .getResumeFamilyMember(familymember_id);
            }
            mv = new JModelAndView("admin/user/user_resume/user_resume_family_member_edit.html",
                request, response);
            if (resumeFamilyMember != null && resumeFamilyMember.getId() != null) {
                mv.addObject("resumeFamilyMember", resumeFamilyMember);
            }
        } else if ("delete".equals(action)) {
            if (familymember_id != null) {
                try {
                    ResumeFamilyMember resumeFamilyMember = resumeFamilyMemberService
                        .getResumeFamilyMember(familymember_id);
                    resumeFamilyMemberService.delResumeFamilyMember(resumeFamilyMember);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mv = new JModelAndView("admin/user/user_resume/user_resume_family_member_view.html",
                request, response);
        } else if ("save".equals(action)) {
            try {
                resumeFamilyMemberService.resume_family_member_save(request, resume.getId());
            } catch (Exception e) {
            }
            mv = new JModelAndView("admin/user/user_resume/user_resume_family_member_view.html",
                request, response);
        }
        mv.addObject("resume", resume);
        mv.addObject("resumeFamilyMemberList",
            resumeFamilyMemberService.queryResumeFamilyMember(resume.getId()));//家庭成员
        mv.addObject("family_relationship_map", Constants.FAMILY_RELATIONSHIP_MAP);//家庭成员与本人关系
        return mv;
    }

    /**
     * 简历其他信息
     */
    @RequestMapping({ "/resume/resume_others.htm" })
    public ModelAndView resume_others(HttpServletRequest request, HttpServletResponse response,
                                      Long resume_id, String action, Long resumeothers_id) {
        if (!verify_resume_id(resume_id)) {
            return null;
        }
        Resume resume = resumeService.getResume(resume_id);
        ModelAndView mv = null;
        if (CommUtil.isBlank(action) || "cancel".equals(action) || "view".equals(action)) {
            mv = new JModelAndView("admin/user/user_resume/user_resume_others_view.html", request,
                response);
        } else if ("new".equals(action)) {
            mv = new JModelAndView("admin/user/user_resume/user_resume_others_edit.html", request,
                response);
        } else if ("modify".equals(action)) {
            ResumeOthers resumeOthers = null;
            if (resumeothers_id != null) {
                resumeOthers = resumeOthersService.getResumeOthers(resumeothers_id);
            }
            mv = new JModelAndView("admin/user/user_resume/user_resume_others_edit.html", request,
                response);
            mv.addObject("resumeOthers", resumeOthers);
        } else if ("save".equals(action)) {
            try {
                resumeOthersService.resume_others_save(request, resume.getId());
            } catch (Exception e) {
            }
            mv = new JModelAndView("admin/user/user_resume/user_resume_others_view.html", request,
                response);
        }
        mv.addObject("resume", resume);
        mv.addObject("is_or_not_map", Constants.IS_OR_NOT_MAP);//是否
        mv.addObject("have_or_not_map", Constants.HAVE_OR_NOT_MAP);//有无
        mv.addObject("dimission_reason_map", Constants.DIMISSION_REASON_MAP);//离职原因
        return mv;
    }

    /**
     * 上传证照
     */
    @RequestMapping({ "/resume/resume_photo.htm" })
    public ModelAndView resume_photo(HttpServletRequest request, HttpServletResponse response,
                                     Long resume_id, String action, String attachments_ids) {
        if (!verify_resume_id(resume_id)) {
            return null;
        }
        Resume resume = resumeService.getResume(resume_id);
        ModelAndView mv = null;
        if (CommUtil.isBlank(action) || "cancel".equals(action) || "view".equals(action)) {
            mv = new JModelAndView("admin/user/user_resume/user_resume_photo_view.html", request,
                response);
        } else if ("modify".equals(action)) {
            mv = new JModelAndView("admin/user/user_resume/user_resume_photo_edit.html", request,
                response);
        } else if ("save".equals(action)) {
            try {
                ResultMsg rmg = attachmentsService.verify_attachment_ids(attachments_ids,
                    "上传附件值异常", true);
                if (rmg.getResult()) {
                    resume.setAttach_ids(attachments_ids);
                    resumeService.updateResume(resume);
                }
            } catch (Exception e) {
            }
            mv = new JModelAndView("admin/user/user_resume/user_resume_photo_view.html", request,
                response);
        }
        mv.addObject("attachments", attachmentsService.getAttachmentsByIds(resume.getAttach_ids()));
        mv.addObject("resume", resume);
        return mv;
    }

    @RequestMapping({ "/admin/resume/userResumeUpload.htm" })
    public void upload(HttpServletRequest request, HttpServletResponse response) {
        List<Attachments> list = null;
        try {
            list = BaseController.uploadifyNoUser(request, response, "user_resume",
                attachmentsService);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list == null || list.size() == 0) {
            return;
        }
        System.out.println(BaseController.write2JsonStr(request, list));
        JsonUtil.writeMsg(response, BaseController.write2JsonStr(request, list));
    }

    /**
     * 验证简历ID
     */
    private boolean verify_resume_id(Long resume_id) {
        if (resume_id == null) {
            return false;
        }
        Resume resume = resumeService.getResume(resume_id);
        if (resume == null) {
            return false;
        }
        return true;
    }
    
    
//    @SecurityMapping(res_name = "导出Word", res_url = "/admin/user/user_resume_exportWord.htm*", res_group = "人员档案")
    @RequestMapping("/admin/user/user_resume_exportWord.htm")
    public void user_exportWord(HttpServletRequest request, HttpServletResponse response, Long id,
    																	String currentPage) {
        File file = null;
        InputStream is = null;
        OutputStream out = null;

        try {
            request.setCharacterEncoding("utf-8");
            Resume resume = resumeService.getResume(id);
            Map<String, Object> map = new HashMap<String, Object>();
            
            //应聘岗位
            if (resume.getDuty() != null && CommUtil.isNotNull(resume.getDuty().getDuty_name())) {
                map.put("duty_name", resume.getDuty().getDuty_name());
            } else {
                map.put("duty_name", "");
            }
            //可到岗时间
            if (resume.getAvailable_time() != null && CommUtil.isNotNull(resume.getAvailable_time())) {
                map.put("available_time", resume.getAvailable_time());
            } else {
                map.put("available_time", "");
            }
            map.put("current_salary", CommUtil.null2String(resume.getCurrent_salary())+"元"); //目前薪资
            map.put("expect_salary", CommUtil.null2String(resume.getExpect_salary())+"元"); //期望薪资
            map.put("name", CommUtil.null2String(resume.getName())); //姓名
            map.put("sex", CommUtil.null2String(Constants.SEX_MAP.
            		get(CommUtil.null2String(resume.getSex())))); //性别
            map.put("birthday",CommUtil.parseShortDateTime(resume.getBirthday())); //出生日期
            map.put("political_status", CommUtil.null2String(Constants.POLITICAL_STATUS_MAP.
            		get(CommUtil.null2String(resume.getPolitical_status())))); //政治面貌
            map.put("nation", CommUtil.null2String(resume.getNation())); //民族
            map.put("marriage_status", CommUtil.null2String(Constants.MARRIAGE_STATUS_MAP.
            		get(CommUtil.null2String(resume.getMarriage_status())))+" "+
                    		CommUtil.null2String(resume.getCondition_of_children()));//婚育情况
            map.put("nationality", CommUtil.null2String(resume.getNationality())); //国籍
            map.put("stature_and_body_weight", CommUtil.null2String(
            		resume.getStature())+"cm/"+
            		CommUtil.null2String(resume.getBody_weight())+"kg"); //身高体重
            map.put("health_condition", CommUtil.null2String(resume.getHealth_condition())); //健康状况
            map.put("past_illness_history", CommUtil.null2String(resume.getPast_illness_history())); //过往病史
            map.put("highest_education", CommUtil.null2String(resume.getHighest_education())); //最高学历
            map.put("graduate_school", CommUtil.null2String(resume.getGraduate_school())); //毕业院校
            map.put("major", CommUtil.null2String(resume.getMajor())); //专业
            map.put("foreign_language_level", CommUtil.null2String(resume.getForeign_language_level())); //英语水平等级
            map.put("computer_skill", CommUtil.null2String(resume.getComputer_skill())); //计算机水平
            map.put("job_titles", CommUtil.null2String(resume.getJob_titles())); //获得职称
            map.put("qualification_certificate", CommUtil.null2String(resume.getQualification_certificate())); //资格证书
            map.put("domicile_place", CommUtil.null2String(resume.getDomicile_place())); //户口随在地
            map.put("ID_number", CommUtil.null2String(resume.getID_number())); //身份证号
            map.put("mobile", CommUtil.null2String(resume.getMobile())); //手机
            map.put("email", CommUtil.null2String(resume.getEmail())); //邮箱
            map.put("home_address", CommUtil.null2String(resume.getHome_address())); //家庭地址
            map.put("present_address", CommUtil.null2String(resume.getPresent_address())); //在京居住地址
            map.put("hobby_and_interest", CommUtil.null2String(resume.getHobby_and_interest())); //兴趣爱好
            map.put("skills", CommUtil.null2String(resume.getSkills())); //技能专长
            map.put("work_years", CommUtil.null2String(resume.getWork_years())+"年"); //参加工作年限
            map.put("same_industry_work_years", CommUtil.null2String(resume.getSame_industry_work_years())+"年"); //同行业工作年限
            
            if (resume.getResumeOthers() != null) {
            	ResumeOthers resumeOthers = resume.getResumeOthers();
            	map.put("reward_name", CommUtil.null2String(resumeOthers.
            			getReward_name())); //何时何地受到何种奖励
            	map.put("punishment_name", CommUtil.null2String(resumeOthers.
            			getPunishment_name())); //何时何地受到何种处分
            	map.put("dimission_reason", Constants.DIMISSION_REASON_MAP.get(
            			CommUtil.null2String(resumeOthers.getDimission_reason()))); //离职原因
            	map.put("contract_status_with_old_unit", CommUtil.null2String(resumeOthers.
            			getContract_status_with_old_unit())); //与原单位劳动合同情况
            	map.put("has_non_competition", CommUtil.null2String(Constants.HAVE_OR_NOT_MAP.get(
            			CommUtil.null2String(resumeOthers.getHas_non_competition())))); //有无竞业条例
            	map.put("apply_reason", CommUtil.null2String(resumeOthers.
            			getApply_reason())); //应聘原因
            	map.put("has_relatives_in_unit", CommUtil.null2String(Constants.HAVE_OR_NOT_MAP.get(
            			CommUtil.null2String(resumeOthers.getHas_relatives_in_unit())))); //有无亲属在本单位任职
            	map.put("accept_other_positions", CommUtil.null2String(Constants.HAVE_OR_NOT_MAP.get(
            			CommUtil.null2String(resumeOthers.getAccept_other_positions())))); //如未能被申请岗位录用，有无其他意向岗位
            	map.put("accept_unit_adjustment", CommUtil.null2String(Constants.IS_OR_NOT_MAP.get(
            			CommUtil.null2String(resumeOthers.getAccept_unit_adjustment())))); //是否接受单位调剂
            	map.put("self_evaluation", CommUtil.null2String(resumeOthers.
            			getSelf_evaluation())); //个人评价
            	map.put("supplement_instruction", CommUtil.null2String(resumeOthers.
            			getSupplement_instruction())); //其他补充说明或其它个人特别的期望
			} else {
				map.put("reward_name","");
				map.put("punishment_name","");
				map.put("dimission_reason","");
				map.put("contract_status_with_old_unit","");
				map.put("has_non_competition","");
				map.put("apply_reason","");
				map.put("has_relatives_in_unit","");
				map.put("accept_other_positions","");
				map.put("accept_unit_adjustment","");
				map.put("self_evaluation","");
				map.put("supplement_instruction","");
			}

            map.put("resumeWorkList", coverResumeWorkList(resumeWorkService.queryResumeWork(resume.getId())));//工作经历
            map.put("resumeEducationList",
                covertResumeEducationList(resumeEducationService.queryResumeEducation(resume.getId())));//教育经历
            map.put("resumeFamilyMemberList", covertResumeFamilyMemberList(resumeFamilyMemberService
                .queryResumeFamilyMember(resume.getId())));//家庭成员

            String filePath = request.getServletContext().getRealPath("/") + "upload/word";
            String fileOnlyName = CommUtil.null2String(resume.getName()) + "应聘人员登记表" + new Date().getTime() + ".doc";
            filePath = URLEncoder.encode(filePath, "UTF-8");
            fileOnlyName = URLEncoder.encode(fileOnlyName, "UTF-8");

            file = WordUtil.createWord(map, "resumeFileWordTemplate.ftl", filePath, fileOnlyName);
            is = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            String fileName = CommUtil.null2String(resume.getName()) + "应聘人员登记表" + new Date().getTime() + ".doc";
            fileName = CommUtil.encodeExportFileName(request, fileName);
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

            out = response.getOutputStream();
            byte[] buffer = new byte[1024];//缓冲区
            int bytesToRead = -1;
            while ((bytesToRead = is.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
                out.flush();
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    is = null;
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    is = null;
                }
            }
            if (file != null) {
                file.delete();
            }
        }
    }

    private List<Map<String, String>> coverResumeWorkList(List<ResumeWork> resumeWorkList)throws Exception {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        if (resumeWorkList == null || resumeWorkList.isEmpty()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("work_time_begin_and_work_time_end", "");
            map.put("work_comp_name", "");
            map.put("work_duty_name", "");
            map.put("month_salary", "");
            map.put("work_type", "");
            map.put("voucher_name_and_voucher_mobile", "");
            list.add(map);
        } else {
            for (ResumeWork resumeWork : resumeWorkList) {
                if (!resumeWork.getDisabled()) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("work_time_begin_and_work_time_end",
                        CommUtil.parseShortDateTime(resumeWork.getWork_time_begin())+"至"+
                        		CommUtil.parseShortDateTime(resumeWork.getWork_time_end()));
                    
                    map.put("work_comp_name", CommUtil.null2String(resumeWork.getWork_comp_name()));
                    map.put("work_duty_name", CommUtil.null2String(resumeWork.getWork_duty_name()));
                    map.put("month_salary", CommUtil.null2String(resumeWork.getMonth_salary()));
                    map.put("work_type", CommUtil.null2String(Constants.WORK_TYPE_MAP.get(CommUtil
                        .null2String(resumeWork.getWork_type()))));
                    map.put("voucher_name_and_voucher_mobile", 
                    		CommUtil.null2String(resumeWork.getVoucher_name()+":")+
                    		CommUtil.null2String(resumeWork.getVoucher_mobile()));
                    list.add(map);
                }
            }
        }
        return list;
    }

    private List<Map<String, String>> covertResumeEducationList(List<ResumeEducation> resumeEducationList)
                                                                                                    throws Exception {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        if (resumeEducationList == null || resumeEducationList.isEmpty()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("admission_date_and_graduation_date", "");
            map.put("edu_school_name", "");
            map.put("edu_major", "");
            map.put("edu_type", "");
            map.put("edu_offering", "");
            list.add(map);
        } else {
            for (ResumeEducation resumeEducation : resumeEducationList) {
                if (!resumeEducation.getDisabled()) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("admission_date_and_graduation_date",
                        CommUtil.parseShortDateTime(resumeEducation.getAdmission_date())+"至"+
                        CommUtil.parseShortDateTime(resumeEducation.getGraduation_date()));
                    map.put("edu_school_name",
                        CommUtil.null2String(resumeEducation.getEdu_school_name()));
                    map.put("edu_major", CommUtil.null2String(resumeEducation.getEdu_major()));
                    map.put("edu_type", CommUtil.null2String(Constants.EDU_TYPE_MAP
                        .get(CommUtil.null2String(resumeEducation.getEdu_type()))));
                    map.put("edu_offering", CommUtil.null2String(resumeEducation.getEdu_offering()));
                    list.add(map);
                }
            }
        }
        return list;
    }


    private List<Map<String, String>> covertResumeFamilyMemberList(List<ResumeFamilyMember> resumeFamilyMemberList)
                                                                                                             throws Exception {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        if (resumeFamilyMemberList == null || resumeFamilyMemberList.isEmpty()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("family_member_name", "");
            map.put("family_relationship", "");
            map.put("family_member_unit", "");
            map.put("family_member_duty", "");
            map.put("family_member_work_place", "");
            map.put("family_member_mobile", "");
            list.add(map);
        } else {
            for (ResumeFamilyMember resumeFamilyMember : resumeFamilyMemberList) {
                if (!resumeFamilyMember.getDisabled()) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("family_member_name",
                        CommUtil.null2String(resumeFamilyMember.getFamily_member_name()));
                    map.put("family_relationship", CommUtil
                        .null2String(Constants.FAMILY_RELATIONSHIP_MAP.get(CommUtil
                            .null2String(resumeFamilyMember.getFamily_relationship()))));
                    map.put("family_member_unit",
                        CommUtil.null2String(resumeFamilyMember.getFamily_member_unit()));
                    map.put("family_member_duty",
                        CommUtil.null2String(resumeFamilyMember.getFamily_member_duty()));
                    map.put("family_member_work_place",
                    		CommUtil.null2String(resumeFamilyMember.getFamily_member_work_place()));
                    map.put("family_member_mobile",
                        CommUtil.null2String(resumeFamilyMember.getFamily_member_mobile()));
                    list.add(map);
                }
            }
        }
        return list;
    }

    
}
