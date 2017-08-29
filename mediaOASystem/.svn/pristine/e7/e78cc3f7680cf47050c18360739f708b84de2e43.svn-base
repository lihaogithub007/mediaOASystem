package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuyu.soft.entity.UserContractRenewal;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 人事合同续签
 *                       
 * @Filename: IUserContractRenewalService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IUserContractRenewalService {

    List<UserContractRenewal> queryUserContractRenewal(Long user_id);

    List<UserContractRenewal> queryUserContractRenewal(String hql, Map<String, Object> paramsMap,
                                                       PagerInfo pager);

    UserContractRenewal getUserContractRenewal(Long id);

    void addUserContractRenewal(UserContractRenewal userContractRenewal);

    void updateUserContractRenewal(UserContractRenewal userContractRenewal);

    void delUserContractRenewal(UserContractRenewal userContractRenewal);

    /**
     * 人事档案合同续签保存
     */
    ResultMsg user_contract_renewal_save(HttpServletRequest request, Long user_id);

}
