package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.VacationApply;
import com.yuyu.soft.entity.VacationApplyDetails;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 请假申请明细
 *                       
 * @Filename: IVacationApplyDetailsService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IVacationApplyDetailsService {

    List<VacationApplyDetails> queryVacationApplyDetails(Long user_id, Long vacation_apply_id);

    List<VacationApplyDetails> queryVacationApplyDetails(String hql, Map<String, Object> paramsMap,
                                                         PagerInfo pager);

    VacationApplyDetails getVacationApplyDetails(Long id);

    void addVacationApplyDetails(VacationApplyDetails vacationApplyDetails);

    void updateVacationApplyDetails(VacationApplyDetails vacationApplyDetails);

    void delVacationApplyDetails(VacationApplyDetails vacationApplyDetails);

    /**
     * 请假申请明细保存
     */
    ResultMsg save(VacationApply vacationApply, String vad_arr);

    /**
     * 批量删除及考勤记录恢复
     */
    void back_leave_days(VacationApply vacationApply);

}
