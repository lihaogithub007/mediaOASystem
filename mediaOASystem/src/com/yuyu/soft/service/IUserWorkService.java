package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuyu.soft.entity.UserWork;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 人事工作经历
 *                       
 * @Filename: IUserWorkService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IUserWorkService {

    List<UserWork> queryUserWork(Long user_id);

    List<UserWork> queryUserWork(String hql, Map<String, Object> paramsMap, PagerInfo pager);

    UserWork getUserWork(Long id);

    void addUserWork(UserWork userWork);

    void updateUserWork(UserWork userWork);

    void delUserWork(UserWork userWork);

    /**
     * 人事档案工作经历保存
     */
    ResultMsg user_work_save(HttpServletRequest request, Long user_id);

}
