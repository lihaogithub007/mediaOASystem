package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuyu.soft.entity.ResumeEducation;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 简历教育经历
 *                       
 * @Filename: IResumeEducationService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IResumeEducationService {

    List<ResumeEducation> queryResumeEducation(Long resume_id);

    List<ResumeEducation> queryResumeEducation(String hql, Map<String, Object> paramsMap,
                                               PagerInfo pager);

    ResumeEducation getResumeEducation(Long id);

    void addResumeEducation(ResumeEducation resumeEducation);

    void updateResumeEducation(ResumeEducation resumeEducation);

    void delResumeEducation(ResumeEducation resumeEducation);

    /**
     * 简历教育经历保存
     */
    ResultMsg resume_education_save(HttpServletRequest request, Long resume_id);

}
