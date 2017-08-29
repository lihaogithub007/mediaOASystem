package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuyu.soft.entity.UserBase;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 人事基础信息
 *                       
 * @Filename: IUserBaseService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IUserBaseService {

    List<UserBase> queryUserBase(String hql, Map<String, Object> paramsMap, PagerInfo pager);

    UserBase getUserBase(Long id);

    void addUserBase(UserBase userBase);

    void updateUserBase(UserBase userBase);

    /**
     * 人事档案基础信息保存
     */
    ResultMsg user_base_save(HttpServletRequest request, Long user_id);

}
