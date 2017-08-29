package com.yuyu.soft.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.CandidateHireApproval;
import com.yuyu.soft.entity.Department;
import com.yuyu.soft.entity.Duty;
import com.yuyu.soft.entity.Resume;
import com.yuyu.soft.entity.ResumeEducation;
import com.yuyu.soft.entity.ResumeFamilyMember;
import com.yuyu.soft.entity.ResumeWork;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.entity.UserBase;
import com.yuyu.soft.entity.UserEducation;
import com.yuyu.soft.entity.UserFamilyMember;
import com.yuyu.soft.entity.UserWork;
import com.yuyu.soft.service.ICandidateHireApprovalService;
import com.yuyu.soft.service.IDepartmentService;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.IResumeEducationService;
import com.yuyu.soft.service.IResumeFamilyMemberService;
import com.yuyu.soft.service.IResumeService;
import com.yuyu.soft.service.IResumeWorkService;
import com.yuyu.soft.service.IUserBaseService;
import com.yuyu.soft.service.IUserEducationService;
import com.yuyu.soft.service.IUserFamilyMemberService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.service.IUserWorkService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

@Service("candidateHireApprovalService")
public class CandidateHireApprovalServiceImpl implements ICandidateHireApprovalService {

    @Resource
    private IBaseDao<CandidateHireApproval> candidateHireApprovalDao;
    @Resource
    private IDepartmentService              departmentService;
    @Resource
    private IDutyService                    dutyService;
    @Resource
    private IResumeService                  resumeService;
    @Resource
    private IUserService                    userService;
    @Resource
    private IUserBaseService                userBaseService;
    @Resource
    private IUserWorkService                userWorkService;
    @Resource
    private IUserEducationService           userEducationService;
    @Resource
    private IUserFamilyMemberService        userFamilyMemberService;
    @Resource
    private IResumeWorkService              resumeWorkService;
    @Resource
    private IResumeEducationService         resumeEducationService;
    @Resource
    private IResumeFamilyMemberService      resumeFamilyMemberService;

    @Override
    public List<CandidateHireApproval> queryCandidateHireApproval(String ids) {
        if (CommUtil.isBlank(ids)) {
            return new ArrayList<CandidateHireApproval>();
        }
        StringBuilder s = new StringBuilder();
        s.append("from CandidateHireApproval t where t.disabled = false");
        s.append(" and t.id in (").append(ids).append(")");
        s.append(" order by t.createtime desc");
        List<CandidateHireApproval> list = candidateHireApprovalDao.find(s.toString());
        if ((list == null) || (list.size() == 0)) {
            return new ArrayList<CandidateHireApproval>();
        }
        return list;
    }

    @Override
    public List<CandidateHireApproval> queryCandidateHireApproval(String hql,
                                                                  Map<String, Object> paramsMap,
                                                                  PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(candidateHireApprovalDao.count("select count(t) " + hql, paramsMap));
            return candidateHireApprovalDao.find(hql, paramsMap, pager.getStart(),
                pager.getPageSize());
        } else {
            return candidateHireApprovalDao.find(hql, paramsMap);
        }

    }

    @Override
    public CandidateHireApproval getCandidateHireApproval(Long id) {
        return candidateHireApprovalDao.get(CandidateHireApproval.class, id);
    }

    @Override
    public void addCandidateHireApproval(CandidateHireApproval candidateHireApproval) {
        this.candidateHireApprovalDao.save(candidateHireApproval);
    }

    @Override
    public void updateCandidateHireApproval(CandidateHireApproval candidateHireApproval) {
        this.candidateHireApprovalDao.update(candidateHireApproval);
    }

    @Override
    public void delCandidateHireApproval(CandidateHireApproval candidateHireApproval) {
        candidateHireApproval = getCandidateHireApproval(candidateHireApproval.getId());
        candidateHireApproval.setDisabled(true);
        updateCandidateHireApproval(candidateHireApproval);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResultMsg add_save(String data_arr, String type) {
        ResultMsg rmg = add_save_check_and_init(data_arr, type);
        if (!rmg.getResult()) {
            return rmg;
        }
        List<CandidateHireApproval> chaList = null;
        try {
            chaList = (List<CandidateHireApproval>) rmg.getData();
        } catch (Exception e) {
            return CommUtil.setResultMsgData(rmg, false, "数据获取失败");
        }
        if (chaList == null || chaList.size() == 0) {
            return CommUtil.setResultMsgData(rmg, false, "数据获取失败");
        }
        for (CandidateHireApproval cha : chaList) {
            addCandidateHireApproval(cha);
            if ("submit".equalsIgnoreCase(type)) {
                synchronous_data(cha);//同步数据
            }
        }
        return CommUtil.setResultMsgData(rmg, true, "应聘人员录用审批保存成功");
    }

    private ResultMsg add_save_check_and_init(String data_arr, String type) {
        if (CommUtil.isBlank(type)
            || !("save".equalsIgnoreCase(type) || "submit".equalsIgnoreCase(type))) {
            return CommUtil.setResultMsgData(null, false, "非法操作");
        }
        if (CommUtil.isBlank(data_arr)) {
            return CommUtil.setResultMsgData(null, false, "保存数据为空");
        }
        try {
            ResultMsg rmg = CommUtil.setResultMsgData(null, true, "校验成功");
            List<CandidateHireApproval> chaList = new ArrayList<CandidateHireApproval>();
            JSONArray array = JSONArray.fromObject(data_arr);
            Date date = new Date();
            BigDecimal tempDecimal = new BigDecimal(-1);
            for (int i = 0; i < array.size(); i++) {
                JSONObject obj = (JSONObject) array.get(i);
                Long resume_id = CommUtil.null2Long(obj.get("resume_id"));
                Long duty_id = CommUtil.null2Long(obj.get("duty_id"));
                if (resume_id == -1) {
                    return CommUtil.setResultMsgData(rmg, false, "审批简历标识为空");
                }
                Resume resume = resumeService.getResume(resume_id);
                if (resume == null || resume.getId() == null || resume.getDisabled()) {
                    return CommUtil.setResultMsgData(rmg, false, "审批简历不存在");
                }
                if (CommUtil.isBlank(resume.getName())) {
                    return CommUtil.setResultMsgData(rmg, false, "录用审批姓名不能为空");
                }
                if (duty_id == -1) {
                    return CommUtil.setResultMsgData(rmg, false, "应聘岗位不能为空");
                }
                Duty duty = dutyService.getDuty(duty_id);
                if (duty == null || duty.getId() == null || duty.getDisabled()) {
                    return CommUtil.setResultMsgData(rmg, false, "应聘岗位不存在");
                }
                Integer hire_result = CommUtil.null2Integer(obj.get("hire_result"));

                if (hire_result == null
                    || CommUtil.isBlank(Constants.IS_OR_NOT_MAP.get(hire_result.toString()))) {
                    return CommUtil.setResultMsgData(rmg, false, "请选择是否录用");
                }
                Integer hired_status = CommUtil.null2Integer(obj.get("hired_status"));
                if (hire_result == 1
                    && CommUtil.isBlank(Constants.HIRED_STATUS_MAP.get(hired_status.toString()))) {
                    return CommUtil.setResultMsgData(rmg, false, "录用状态值不符规定");
                }
                String entry_time = CommUtil.null2String(obj.get("entry_time"));
                String department_approval = CommUtil.null2String(obj.get("department_approval"));
                String leader_approval = CommUtil.null2String(obj.get("leader_approval"));
                if ("submit".equalsIgnoreCase(type)) {
                    if (hire_result == 1 && CommUtil.isBlank(entry_time)) {
                        return CommUtil.setResultMsgData(rmg, false, "请选择入职日期");
                    }
                    if (hire_result == 1 && CommUtil.isBlank(department_approval)) {
                        return CommUtil.setResultMsgData(rmg, false, "请输入科组审批");
                    }
                    if (hire_result == 1 && CommUtil.isBlank(leader_approval)) {
                        return CommUtil.setResultMsgData(rmg, false, "请输入领导审批");
                    }
                }
                BigDecimal written_score = CommUtil.null2BigDecimal(obj.get("written_score"));
                BigDecimal interview_score = CommUtil.null2BigDecimal(obj.get("interview_score"));
                BigDecimal final_score = CommUtil.null2BigDecimal(obj.get("final_score"));
                BigDecimal current_salary = CommUtil.null2BigDecimal(obj.get("current_salary"));
                BigDecimal expect_salary = CommUtil.null2BigDecimal(obj.get("expect_salary"));
                BigDecimal duty_level = CommUtil.null2BigDecimal(obj.get("duty_level"));
                BigDecimal suggest_salary = CommUtil.null2BigDecimal(obj.get("suggest_salary"));
                String personal_information = CommUtil.null2String(obj.get("personal_information"));
                Long department_id = CommUtil.null2Long(obj.get("department_id"));
                Department department = null;
                if (department_id != -1) {
                    department = departmentService.getDepartment(department_id);
                }
                CandidateHireApproval cha = new CandidateHireApproval();
                cha.setCreatetime(date);
                cha.setDisabled(false);
                cha.setResume(resume);
                cha.setName(CommUtil.null2String(resume.getName()));
                cha.setDuty(duty);
                cha.setHire_result(hire_result);
                if (hire_result == 1) {
                    cha.setHired_status(hired_status);
                } else {
                    cha.setHired_status(null);
                }
                if (written_score.compareTo(tempDecimal) > 0) {
                    cha.setWritten_score(written_score);
                }
                if (interview_score.compareTo(tempDecimal) > 0) {
                    cha.setInterview_score(interview_score);
                }
                if (final_score.compareTo(tempDecimal) > 0) {
                    cha.setFinal_score(final_score);
                }
                if (current_salary.compareTo(tempDecimal) > 0) {
                    cha.setCurrent_salary(current_salary);
                }
                if (expect_salary.compareTo(tempDecimal) > 0) {
                    cha.setExpect_salary(expect_salary);
                }
                if (duty_level.compareTo(tempDecimal) > 0) {
                    cha.setDuty_level(duty_level);
                }
                if (suggest_salary.compareTo(tempDecimal) > 0) {
                    cha.setSuggest_salary(suggest_salary);
                }
                cha.setDepartment(department);
                cha.setPersonal_information(personal_information);
                cha.setEntry_time(CommUtil.formatDate(entry_time));
                cha.setDepartment_approval(department_approval);
                cha.setLeader_approval(leader_approval);
                if ("submit".equalsIgnoreCase(type)) {
                    cha.setApproval_status(2);//已提交
                } else {
                    cha.setApproval_status(1);//未提交
                }
                chaList.add(cha);
            }
            rmg.setData(chaList);
            return rmg;
        } catch (Exception e) {
            return CommUtil.setResultMsgData(null, false, e.getMessage());
        }
    }

    //提交时同步数据
    private void synchronous_data(CandidateHireApproval cha) {
        if (cha == null || cha.getId() == null) {
            throw new RuntimeException("录用审批对象为空");
        }
        cha = getCandidateHireApproval(cha.getId());
        if (cha.getDuty() == null || cha.getDuty().getId() == null) {
            throw new RuntimeException("录用审批对象应聘岗位为空");
        }
        if (cha.getResume() == null || cha.getResume().getId() == null) {
            throw new RuntimeException("录用审批对象简历为空");
        }
        Integer hire_result = cha.getHire_result();
        if (hire_result == null
            || CommUtil.isBlank(Constants.IS_OR_NOT_MAP.get(hire_result.toString()))) {
            throw new RuntimeException("请选择是否录用");
        }

        //修改简历状态
        Resume resume = cha.getResume();
        if (resume == null || resume.getId() == null) {
            throw new RuntimeException("找不到简历记录");
        } else {
            resume = resumeService.getResume(resume.getId());
            if (hire_result == 0) {
                resume.setResume_status(2);//未录用
            } else if (hire_result == 1) {
                resume.setResume_status(3);//已录用
            }
        }
        if (resume.getResume_status() == 3) {//录用
            synchronous_user_file(cha);
        }
    }

    //创建用户，同步人事档案
    private void synchronous_user_file(CandidateHireApproval cha) {

        Resume resume = resumeService.getResume(cha.getResume().getId());
        String mobile = resume.getMobile();
        List<User> users = userService.queryUserByMobile(mobile);
        if (users != null && users.size() > 0) {
            throw new RuntimeException("已存在员工使用该手机号，不能创建用户");
        }

        Duty duty = dutyService.getDuty(cha.getDuty().getId());

        User user = new User();
        user.setCreatetime(new Date());
        user.setDisabled(false);
        user.setTrue_name(cha.getName());
        user.setMobile(mobile);
        user.setPassword(Constants.DEFAULT_PASSWORD);
        user.setBirthday(resume.getBirthday());
        user.setDuty(duty);
        user.setDepartment(duty.getDepartment());
        user.setEmail(resume.getEmail());
        user.setArchive_status(false);
        user.setUser_status(cha.getHired_status());
        user.setIs_synchronous(true);
        userService.addUser(user);

        user = userService.getUser(user.getId());
        UserBase userBase = new UserBase();
        userBase.setCreatetime(new Date());
        userBase.setDisabled(false);
        userBase.setUser(user);
        userBase.setSex(resume.getSex());
        userBase.setAge(CommUtil.getAge(user.getBirthday()));
        userBase.setNation(resume.getNation());
        userBase.setID_type(1);//身份证
        userBase.setID_number(resume.getID_number());//身份证号
        userBase.setWork_years(resume.getWork_years());
        userBase.setSame_industry_work_years(resume.getSame_industry_work_years());
        userBase.setPolitical_status(resume.getPolitical_status());
        userBase.setMarriage_status(resume.getMarriage_status());
        userBase.setCondition_of_children(resume.getCondition_of_children());
        userBase.setHighest_education(resume.getHighest_education());
        userBase.setForeign_language_level(resume.getForeign_language_level());
        userBase.setComputer_skill(resume.getComputer_skill());
        userBase.setJob_titles(resume.getJob_titles());
        userBase.setJob_titles_cert_id(resume.getQualification_certificate());
        userBase.setHome_address(resume.getHome_address());
        userBase.setPresent_address(resume.getPresent_address());
        userBase.setDomicile_place(resume.getDomicile_place());
        userBase.setHobby_and_speciality(resume.getHobby_and_interest());
        userBaseService.addUserBase(userBase);

        //工作经历
        List<ResumeWork> resumeWorks = resume.getResumeWorkList();
        if (resumeWorks != null && resumeWorks.size() > 0) {
            for (ResumeWork rw : resumeWorks) {
                rw = this.resumeWorkService.getResumeWork(rw.getId());
                if (!rw.getDisabled()) {
                    UserWork uw = new UserWork();
                    uw.setDisabled(false);
                    uw.setCreatetime(new Date());
                    uw.setUser(user);
                    uw.setWork_time_begin(rw.getWork_time_begin());
                    uw.setWork_time_end(rw.getWork_time_end());
                    uw.setWork_comp_name(rw.getWork_comp_name());
                    uw.setWork_duty_name(rw.getWork_duty_name());
                    uw.setWork_type(rw.getWork_type());
                    userWorkService.addUserWork(uw);
                }
            }
        }

        //教育经历
        List<ResumeEducation> resumeEducations = resume.getResumeEducationList();
        if (resumeEducations != null && resumeEducations.size() > 0) {
            for (ResumeEducation re : resumeEducations) {
                re = this.resumeEducationService.getResumeEducation(re.getId());
                if (!re.getDisabled()) {
                    UserEducation ue = new UserEducation();
                    ue.setUser(user);
                    ue.setDisabled(false);
                    ue.setCreatetime(new Date());
                    ue.setAdmission_date(re.getAdmission_date());
                    ue.setGraduation_date(re.getGraduation_date());
                    ue.setEdu_school_name(re.getEdu_school_name());
                    ue.setEdu_major(re.getEdu_major());
                    ue.setEdu_offering(re.getEdu_offering());
                    userEducationService.addUserEducation(ue);
                }
            }
        }

        //家庭成员
        List<ResumeFamilyMember> resumeFamilyMembers = resume.getResumeFamilyMemberList();
        if (resumeFamilyMembers != null && resumeFamilyMembers.size() > 0) {
            for (ResumeFamilyMember rfm : resumeFamilyMembers) {
                rfm = this.resumeFamilyMemberService.getResumeFamilyMember(rfm.getId());
                if (!rfm.getDisabled()) {
                    UserFamilyMember ufm = new UserFamilyMember();
                    ufm.setDisabled(false);
                    ufm.setCreatetime(new Date());
                    ufm.setUser(user);
                    ufm.setFamily_member_name(rfm.getFamily_member_name());
                    ufm.setFamily_relationship(rfm.getFamily_relationship());
                    ufm.setFamily_member_unit(rfm.getFamily_member_unit());
                    ufm.setFamily_member_duty(rfm.getFamily_member_duty());
                    ufm.setFamily_member_mobile(rfm.getFamily_member_mobile());
                    userFamilyMemberService.addUserFamilyMember(ufm);

                }
            }
        }
    }

    /**
     * 根据简历ID查询录用审批
     */
    private List<CandidateHireApproval> queryByResumeId(Long resume_id) {
        if (resume_id == null) {
            return new ArrayList<CandidateHireApproval>();
        }
        StringBuilder s = new StringBuilder();
        s.append("from CandidateHireApproval t where t.disabled = false");
        s.append(" and t.resume.id = ").append(resume_id);
        List<CandidateHireApproval> tempList = candidateHireApprovalDao.find(s.toString());
        if (tempList == null || tempList.size() == 0) {
            return new ArrayList<CandidateHireApproval>();
        }
        return tempList;
    }

    @Override
    public ResultMsg edit_save(CandidateHireApproval candidateHireApproval, Long duty_id,
                               Long department_id, String type) {

        ResultMsg rmg = edit_save_check_and_init(candidateHireApproval, duty_id, department_id,
            type);
        if (!rmg.getResult()) {
            return rmg;
        }
        updateCandidateHireApproval(candidateHireApproval);
        if ("submit".equalsIgnoreCase(type)) {
            synchronous_data(candidateHireApproval);//同步数据
        }
        return CommUtil.setResultMsgData(rmg, true, "应聘人员录用审批编辑保存成功");
    }

    private ResultMsg edit_save_check_and_init(CandidateHireApproval candidateHireApproval,
                                               Long duty_id, Long department_id, String type) {
        if (CommUtil.isBlank(type)
            || !("save".equalsIgnoreCase(type) || "submit".equalsIgnoreCase(type))) {
            return CommUtil.setResultMsgData(null, false, "非法操作");
        }
        if (candidateHireApproval == null) {
            return CommUtil.setResultMsgData(null, false, "应聘人员录用审批对象为空");
        }
        if (candidateHireApproval.getApproval_status() == 2) {
            return CommUtil.setResultMsgData(null, false, "应聘人员录用审批已提交，不能修改");
        }
        if (duty_id == -1) {
            return CommUtil.setResultMsgData(null, false, "应聘岗位不能为空");
        }
        Duty duty = dutyService.getDuty(duty_id);
        if (duty == null || duty.getId() == null || duty.getDisabled()) {
            return CommUtil.setResultMsgData(null, false, "应聘岗位不存在");
        }
        candidateHireApproval.setDuty(duty);
        Department department = null;
        if (department_id != -1) {
            department = departmentService.getDepartment(department_id);
        }
        candidateHireApproval.setDepartment(department);

        Integer hire_result = candidateHireApproval.getHire_result();
        if (hire_result == null
            || CommUtil.isBlank(Constants.IS_OR_NOT_MAP.get(hire_result.toString()))) {
            return CommUtil.setResultMsgData(null, false, "请选择是否录用");
        }
        Integer hired_status = candidateHireApproval.getHired_status();
        if (hire_result == 1
            && CommUtil.isBlank(Constants.HIRED_STATUS_MAP.get(hired_status.toString()))) {
            return CommUtil.setResultMsgData(null, false, "录用状态值不符规定");
        }
        if (hire_result == 1) {
            candidateHireApproval.setHired_status(hired_status);
        } else {
            candidateHireApproval.setHired_status(null);
        }

        Date entry_time = candidateHireApproval.getEntry_time();
        String department_approval = candidateHireApproval.getDepartment_approval();
        String leader_approval = candidateHireApproval.getLeader_approval();
        if ("submit".equalsIgnoreCase(type)) {
            if (hire_result == 1 && entry_time == null) {
                return CommUtil.setResultMsgData(null, false, "请选择入职日期");
            }
            if (hire_result == 1 && CommUtil.isBlank(department_approval)) {
                return CommUtil.setResultMsgData(null, false, "请输入科组审批");
            }
            if (hire_result == 1 && CommUtil.isBlank(leader_approval)) {
                return CommUtil.setResultMsgData(null, false, "请输入领导审批");
            }
        }
        if ("submit".equalsIgnoreCase(type)) {
            candidateHireApproval.setApproval_status(2);//已提交
        } else {
            candidateHireApproval.setApproval_status(1);//未提交
        }
        return CommUtil.setResultMsgData(null, true, "校验成功");
    }

    @Override
    public Map<String, Object> approval_is_hired_count(String beginTime, String endTime,
                                                       List<String> yearMonthList) {
        Map<String, Object> temp = new HashMap<String, Object>();
        StringBuffer s = new StringBuffer();
        s.append(" select date_format(cha.createtime,'%Y-%m') as month, count(cha.id) as count from tb_candidate_hire_approval cha");
        s.append(" where cha.disabled = 0 and cha.hire_result = 1");
        s.append(" and date_format(cha.createtime,'%Y-%m')>= date_format(str_to_date('")
            .append(beginTime).append("','%Y-%m-%d'),'%Y-%m')");
        s.append(" and date_format(cha.createtime,'%Y-%m')<= date_format(str_to_date('")
            .append(endTime).append("','%Y-%m-%d'),'%Y-%m')");
        s.append(" group by date_format(cha.createtime,'%Y-%m')");
        List<Map<String, Object>> list = candidateHireApprovalDao.findListMapBySQL(s.toString(),
            null, 0, -1);
        if (list == null || list.size() == 0) {
            return temp;
        }
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            temp.put(CommUtil.null2String(map.get("month")).replace("-", "_"), map.get("count"));
        }
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        if (yearMonthList != null && yearMonthList.size() > 0) {
            for (String yearMonth : yearMonthList) {
                if (CommUtil.isNotNull(temp.get(yearMonth))) {
                    result.put(yearMonth, temp.get(yearMonth));
                } else {
                    result.put(yearMonth, 0);
                }
            }
        }
        return result;
    }
}
