package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.UserDimissionLogs;
import com.yuyu.soft.util.PagerInfo;

/**
 * 离职日志
 *                       
 * @Filename: IUserDimissionLogsService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IUserDimissionLogsService {

    List<UserDimissionLogs> queryUserDimissionLogs(String hql, Map<String, Object> paramsMap,
                                                   PagerInfo pager);

    UserDimissionLogs getUserDimissionLogs(Long id);

    void addUserDimissionLogs(UserDimissionLogs userDimissionLogs);

    public void updateUserDimissionLogs(UserDimissionLogs userDimissionLogs);

    public void delUserDimissionLogs(UserDimissionLogs userDimissionLogs);

    /**
     * 保存离职日志
     */
    void add_dimission_log(Long user_id);

}
