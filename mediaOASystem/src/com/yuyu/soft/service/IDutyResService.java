package com.yuyu.soft.service;

import java.util.List;

import com.yuyu.soft.entity.DutyRes;

/**
 * 岗位资源相关的接口处理
 *                       
 * @Filename: IDutyResService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IDutyResService {

    DutyRes getDutyRes(Long id);

    void addDutyRes(DutyRes dutyRes);

    void delDutyRes(DutyRes dutyRes);

    /**
     * 查询岗位资源ID
     */
    List<Long> queryDutyResIds(Long id);

    void duty_res_save(String res_ids, Long duty_id);

    List<DutyRes> queryByDutyId(Long duty_id);

}
