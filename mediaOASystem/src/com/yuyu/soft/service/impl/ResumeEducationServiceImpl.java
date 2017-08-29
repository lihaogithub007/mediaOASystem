package com.yuyu.soft.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.Resume;
import com.yuyu.soft.entity.ResumeEducation;
import com.yuyu.soft.service.IResumeEducationService;
import com.yuyu.soft.service.IResumeService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

@Service("resumeEducationService")
public class ResumeEducationServiceImpl implements IResumeEducationService {

    @Resource
    private IBaseDao<ResumeEducation> resumeEducationDao;
    @Resource
    private IResumeService            resumeService;

    @Override
    public List<ResumeEducation> queryResumeEducation(Long resume_id) {
        if (resume_id == null) {
            return new ArrayList<ResumeEducation>();
        }
        StringBuilder s = new StringBuilder();
        s.append("from ResumeEducation t where t.disabled = false");
        s.append(" and t.resume.id = ").append(resume_id);
        s.append(" order by t.createtime asc");
        List<ResumeEducation> resumeEducations = resumeEducationDao.find(s.toString());
        if ((resumeEducations == null) || (resumeEducations.size() == 0)) {
            return new ArrayList<ResumeEducation>();
        }
        return resumeEducations;
    }

    @Override
    public List<ResumeEducation> queryResumeEducation(String hql, Map<String, Object> paramsMap,
                                                      PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(resumeEducationDao.count("select count(t) " + hql, paramsMap));
            return resumeEducationDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return resumeEducationDao.find(hql, paramsMap);
        }

    }

    @Override
    public ResumeEducation getResumeEducation(Long id) {
        return resumeEducationDao.get(ResumeEducation.class, id);
    }

    @Override
    public void addResumeEducation(ResumeEducation resumeEducation) {
        this.resumeEducationDao.save(resumeEducation);
    }

    @Override
    public void updateResumeEducation(ResumeEducation resumeEducation) {
        this.resumeEducationDao.update(resumeEducation);
    }

    @Override
    public void delResumeEducation(ResumeEducation resumeEducation) {
        resumeEducation = getResumeEducation(resumeEducation.getId());
        resumeEducation.setDisabled(true);
        updateResumeEducation(resumeEducation);
        //this.resumeEducationDao.delete(resumeEducation);
    }

    @Override
    public ResultMsg resume_education_save(HttpServletRequest request, Long resume_id) {
        String resume_education_id = request.getParameter("resume_education_id");
        WebForm wf = new WebForm();
        ResumeEducation resumeEducation = null;
        if (CommUtil.isBlank(resume_education_id)) {
            resumeEducation = (ResumeEducation) wf.toPo(request, ResumeEducation.class);
            resumeEducation.setCreatetime(new Date());
            resumeEducation.setDisabled(false);
        } else {
            ResumeEducation dbResumeEducation = getResumeEducation(Long.valueOf(Long
                .parseLong(resume_education_id)));
            resumeEducation = (ResumeEducation) wf.toPo(request, dbResumeEducation);
        }

        ResultMsg rmg = resume_education_save_check(resumeEducation, resume_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        resume_education_save_init(resumeEducation);
        if (CommUtil.isBlank(resume_education_id)) {
            addResumeEducation(resumeEducation);
        } else {
            updateResumeEducation(resumeEducation);
        }
        return CommUtil.setResultMsgData(rmg, true, "简历教育经历保存成功");
    }

    //校验信息
    private ResultMsg resume_education_save_check(ResumeEducation resumeEducation, Long resume_id) {
        if (resumeEducation == null) {
            return CommUtil.setResultMsgData(null, false, "简历教育经历对象为空");
        }
        if (resume_id == null) {
            return CommUtil.setResultMsgData(null, false, "简历标识为空");
        }
        Resume resume = resumeService.getResume(resume_id);
        if (resume == null || resume.getId() == null) {
            return CommUtil.setResultMsgData(null, false, "简历对象为空");
        } else {
            resumeEducation.setResume(resume);
        }
        return CommUtil.setResultMsgData(null, true, "简历教育经历校验成功");
    }

    //对不符合数据库设计的字段初始化
    private void resume_education_save_init(ResumeEducation resumeEducation) {
        if (resumeEducation == null) {
            return;
        }
        //就读院校
        String edu_school_name = resumeEducation.getEdu_school_name();
        if (CommUtil.isNotNull(edu_school_name) && edu_school_name.length() > 50) {
            resumeEducation.setEdu_school_name("");
        }
        //所学专业
        String edu_major = resumeEducation.getEdu_major();
        if (CommUtil.isNotNull(edu_major) && edu_major.length() > 50) {
            resumeEducation.setEdu_major("");
        }
        //学习性质
        String edu_type = resumeEducation.getEdu_type();
        if (CommUtil.isNotNull(edu_type) && CommUtil.isBlank(Constants.EDU_TYPE_MAP.get(edu_type))) {
            resumeEducation.setEdu_type("");
        }
        //学位
        String edu_offering = resumeEducation.getEdu_offering();
        if (CommUtil.isNotNull(edu_offering) && edu_offering.length() > 50) {
            resumeEducation.setEdu_offering("");
        }
    }
}
