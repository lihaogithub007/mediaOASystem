package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.UserLogs;
import com.yuyu.soft.util.PagerInfo;

/**
 * 用户入离职日志
 *                       
 *
 */
public interface IUserLogsService {

    List<UserLogs> queryUserLogs(String hql, Map<String, Object> paramsMap, PagerInfo pager);

    UserLogs getUserLogs(Long id);

    void addUserLogs(UserLogs userLogs);

    /**
     * 保存日志
     */
    void add_log(Long user_id, Long create_user_id, String log_info,
    				String dimission_reason, String dimission_time);
}
