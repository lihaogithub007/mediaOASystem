package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuyu.soft.entity.UserEducation;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 人事教育经历
 *                       
 * @Filename: IUserEducationService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IUserEducationService {

    List<UserEducation> queryUserEducation(Long user_id);

    List<UserEducation> queryUserEducation(String hql, Map<String, Object> paramsMap,
                                           PagerInfo pager);

    UserEducation getUserEducation(Long id);

    void addUserEducation(UserEducation userEducation);

    void updateUserEducation(UserEducation userEducation);

    void delUserEducation(UserEducation userEducation);

    /**
     * 人事档案教育经历保存
     */
    ResultMsg user_education_save(HttpServletRequest request, Long user_id);

}
