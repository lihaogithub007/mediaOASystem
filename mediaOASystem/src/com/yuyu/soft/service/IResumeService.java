package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuyu.soft.entity.Resume;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 简历信息
 *                       
 * @Filename: IResumeService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IResumeService {

    /**
     * 查询简历数量(sql)
     * 用于导出Excel
     */
    int getCountForExportExcel(String name, String highest_education, String apply_job_name,
                               String work_years, Integer resume_status);

    /**
     * 查询简历(sql)
     * 用于导出Excel
     */
    List<Object[]> getResumesForExportExcel(String name, String highest_education,
                                            String apply_job_name, String work_years,
                                            Integer resume_status, int beginIndex, int pageSize);

    List<Resume> queryResume(String hql, Map<String, Object> paramsMap, PagerInfo pager);

    /**
     * 根据简历状态和ID查询简历
     */
    List<Resume> queryResumeForApproval(Integer status, String resumeIds);

    Resume getResume(Long id);

    void addResume(Resume resume);

    void updateResume(Resume resume);

    void delResume(Resume resume);

    /**
     * 根据手机号查询简历
     */
    List<Resume> queryResumeByMobile(String mobile);

    ResultMsg add_save(String mobile);

    /**
     * 简历信息保存
     */
    ResultMsg resume_save(HttpServletRequest request, Long resume_id, Long duty_id);

    /**
     * 面试数量（=新增简历数量）
     */
    Map<String, Object> resume_count(String beginTime, String endTime, List<String> yearMonthList);
}
