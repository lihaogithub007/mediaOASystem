package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.Assessment;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 评优管理相关接口
 *                       
 * @Filename: IAssessmentService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IAssessmentService {

    /**
     * 查询所有评优管理列表
     */
    List<Assessment> queryAllAssessment();

    /**
     * 用于导出Excel
     */

    int getCountForExportExcel(Long department_id, Long user_id, String award_date,
                               String award_name, String award_works, String award_level,
                               String award_unit);

    /**
     * 用于导出Excel
     */
    List<Object[]> getAssessmentForExportExcel(Long department_id, Long user_id, String award_date,
                                               String award_name, String award_works,
                                               String award_level, String award_unit,
                                               int beginIndex, int pageSize);

    /**
     * 查询评优管理列表（分页）
     */
    List<Assessment> queryAssessment(String hql, Map<String, Object> paramsMap, PagerInfo pager);

    /**
     * 根据ID获取评优管理对象
     */
    Assessment getAssessment(Long id);

    /**
     * 添加评优管理
     */
    void addAssessment(Assessment Assessment);

    /**
     * 更新评优管理
     */
    void updateAssessment(Assessment Assessment);

    /**
     * 删除评优管理
     */
    public void delAssessment(Assessment Assessment);

    /**
     * 添加评优管理保存
     */
    public ResultMsg add_save(Assessment Assessment, Long department_id);

    /**
     * 评优管理编辑保存
     */
    public ResultMsg edit_save(Assessment Assessment, Long department_id);

}
