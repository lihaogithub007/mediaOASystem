package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuyu.soft.entity.UserFamilyMember;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 人事家庭成员
 *                       
 * @Filename: IUserFamilyMemberService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IUserFamilyMemberService {

    List<UserFamilyMember> queryUserFamilyMember(Long user_id);

    List<UserFamilyMember> queryUserFamilyMember(String hql, Map<String, Object> paramsMap,
                                                 PagerInfo pager);

    UserFamilyMember getUserFamilyMember(Long id);

    void addUserFamilyMember(UserFamilyMember userFamilyMember);

    void updateUserFamilyMember(UserFamilyMember userFamilyMember);

    void delUserFamilyMember(UserFamilyMember userFamilyMember);

    /**
     * 人事档案家庭成员保存
     */
    ResultMsg user_family_member_save(HttpServletRequest request, Long user_id);

}
