package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.VacationApply;
import com.yuyu.soft.entity.VacationApplyApproval;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 请假申请审批
 *                       
 * @Filename: IVacationApplyApprovalService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IVacationApplyApprovalService {

    List<VacationApplyApproval> queryVacationApplyApproval(Long vacation_apply_id);

    List<VacationApplyApproval> queryVacationApplyApproval(String hql,
                                                           Map<String, Object> paramsMap,
                                                           PagerInfo pager);

    VacationApplyApproval getVacationApplyApproval(Long id);

    void addVacationApplyApproval(VacationApplyApproval vacationApplyApproval);

    void updateVacationApplyApproval(VacationApplyApproval vacationApplyApproval);

    void delVacationApplyApproval(VacationApplyApproval vacationApplyApproval);

    void batchDelVacationApplyApproval(VacationApply vacationApply);

    /**
     * 请假申请审批保存
     */
    ResultMsg save(VacationApply vacationApply, String vaa_arr);

}
