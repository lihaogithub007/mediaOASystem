package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.VacationApply;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 请假申请
 *                       
 * @Filename: IVacationApplyService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IVacationApplyService {

    List<VacationApply> queryVacationApply(Long user_id);

    List<VacationApply> queryVacationApply(String hql, Map<String, Object> paramsMap,
                                           PagerInfo pager);

    VacationApply getVacationApply(Long id);

    void addVacationApply(VacationApply vacationApply);

    void updateVacationApply(VacationApply vacationApply);

    void delVacationApply(VacationApply vacationApply);

    /**
     * 请假申请添加保存
     */
    ResultMsg add_save(VacationApply vacationApply, Long user_id, String vad_arr, String vaa_arr);

    /**
     * 请假申请编辑保存
     */
    ResultMsg edit_save(VacationApply vacationApply, Long user_id, String vad_arr, String vaa_arr);

    /**
     * 销假
     */
    void cancel_save(VacationApply vacationApply);

    /**
     * 删除
     */
    void delete_save(VacationApply vacationApply);

}
