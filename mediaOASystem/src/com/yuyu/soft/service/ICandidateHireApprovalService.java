package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.CandidateHireApproval;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 应聘人员审批
 *                       
 * @Filename: ICandidateHireApprovalService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface ICandidateHireApprovalService {

    /**
     * 根据IDs查询
     */
    List<CandidateHireApproval> queryCandidateHireApproval(String ids);

    List<CandidateHireApproval> queryCandidateHireApproval(String hql,
                                                           Map<String, Object> paramsMap,
                                                           PagerInfo pager);

    CandidateHireApproval getCandidateHireApproval(Long id);

    void addCandidateHireApproval(CandidateHireApproval CandidateHireApproval);

    void updateCandidateHireApproval(CandidateHireApproval CandidateHireApproval);

    void delCandidateHireApproval(CandidateHireApproval CandidateHireApproval);

    /**
     * 添加审批保存
     */
    ResultMsg add_save(String data_arr, String type);

    /**
     * 编辑审批保存
     */
    ResultMsg edit_save(CandidateHireApproval candidateHireApproval, Long duty_id,
                        Long department_id, String type);

    /**
     * 审批录用人数
     */
    Map<String, Object> approval_is_hired_count(String beginTime, String endTime,
                                                List<String> yearMonthList);

}
