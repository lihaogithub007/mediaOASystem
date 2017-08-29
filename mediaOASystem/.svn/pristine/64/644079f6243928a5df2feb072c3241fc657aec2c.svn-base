package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuyu.soft.entity.UserContract;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 人事合同
 *                       
 * @Filename: IUserContractService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IUserContractService {

    List<UserContract> queryUserContract(Long user_id);

    List<UserContract> queryUserContract(String hql, Map<String, Object> paramsMap, PagerInfo pager);

    UserContract getUserContract(Long id);

    void addUserContract(UserContract userContract);

    void updateUserContract(UserContract userContract);

    void delUserContract(UserContract userContract);

    /**
     * 人事档案合同保存
     */
    ResultMsg user_contract_save(HttpServletRequest request, Long user_id);

}
