package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuyu.soft.entity.ResumeOthers;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 简历其他信息
 *                       
 * @Filename: IResumeOthersService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IResumeOthersService {

    List<ResumeOthers> queryResumeOthers(Long resume_id);

    List<ResumeOthers> queryResumeOthers(String hql, Map<String, Object> paramsMap, PagerInfo pager);

    ResumeOthers getResumeOthers(Long id);

    void addResumeOthers(ResumeOthers resumeOthers);

    void updateResumeOthers(ResumeOthers resumeOthers);

    void delResumeOthers(ResumeOthers resumeOthers);

    /**
     * 简历其他信息保存
     */
    ResultMsg resume_others_save(HttpServletRequest request, Long resume_id);

}
