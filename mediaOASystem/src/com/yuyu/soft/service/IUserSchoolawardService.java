package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuyu.soft.entity.Assessment;
import com.yuyu.soft.entity.UserSchoolaward;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 人事所获奖项
 *                       
 * @Filename: IUserSchoolawardService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IUserSchoolawardService {

    List<UserSchoolaward> queryUserSchoolaward(Long user_id, Long assessment_id);

    List<UserSchoolaward> queryUserSchoolawardByAassessment(Long assessment_id);

    List<UserSchoolaward> queryUserSchoolaward(Long user_id);

    List<UserSchoolaward> queryUserSchoolaward(String hql, Map<String, Object> paramsMap,
                                               PagerInfo pager);

    UserSchoolaward getUserSchoolaward(Long id);

    void addUserSchoolaward(UserSchoolaward userSchoolaward);

    void updateUserSchoolaward(UserSchoolaward userSchoolaward);

    void delUserSchoolaward(UserSchoolaward userSchoolaward);

    /**
     * 人事档案所获奖项保存
     */
    ResultMsg user_schoolaward_save(HttpServletRequest request, Long user_id);

    /**
     * 人事档案所获奖项批量保存（评优管理数据同步）
     */
    ResultMsg user_schoolaward_save(Assessment assessment);

    void batchDelUserSchoolaward(List<UserSchoolaward> list);
}
