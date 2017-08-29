package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuyu.soft.entity.ResumeWork;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 简历工作经历
 *                       
 * @Filename: IResumeWorkService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IResumeWorkService {

    List<ResumeWork> queryResumeWork(Long resume_id);

    List<ResumeWork> queryResumeWork(String hql, Map<String, Object> paramsMap, PagerInfo pager);

    ResumeWork getResumeWork(Long id);

    void addResumeWork(ResumeWork resumeWork);

    void updateResumeWork(ResumeWork resumeWork);

    void delResumeWork(ResumeWork resumeWork);

    /**
     * 简历工作经历保存
     */
    ResultMsg resume_work_save(HttpServletRequest request, Long resume_id);

}
