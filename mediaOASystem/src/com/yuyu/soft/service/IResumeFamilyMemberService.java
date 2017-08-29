package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuyu.soft.entity.ResumeFamilyMember;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 简历家庭成员
 *                       
 * @Filename: IResumeFamilyMemberService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IResumeFamilyMemberService {

    List<ResumeFamilyMember> queryResumeFamilyMember(Long resume_id);

    List<ResumeFamilyMember> queryResumeFamilyMember(String hql, Map<String, Object> paramsMap,
                                                     PagerInfo pager);

    ResumeFamilyMember getResumeFamilyMember(Long id);

    void addResumeFamilyMember(ResumeFamilyMember resumeFamilyMember);

    void updateResumeFamilyMember(ResumeFamilyMember resumeFamilyMember);

    void delResumeFamilyMember(ResumeFamilyMember resumeFamilyMember);

    /**
     * 简历家庭成员保存
     */
    ResultMsg resume_family_member_save(HttpServletRequest request, Long resume_id);

}
