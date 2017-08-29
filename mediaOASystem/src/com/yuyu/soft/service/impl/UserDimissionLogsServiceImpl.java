package com.yuyu.soft.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.entity.UserDimissionLogs;
import com.yuyu.soft.service.IUserDimissionLogsService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.PagerInfo;

/**
 * 离职日志
 *                       
 * @Filename: UserDimissionLogsServiceImpl.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Service("userDimissionLogsService")
public class UserDimissionLogsServiceImpl implements IUserDimissionLogsService {

    @Resource
    private IBaseDao<UserDimissionLogs> userDimissionLogsDao;
    @Resource
    private IUserService                userService;

    public List<UserDimissionLogs> queryUserDimissionLogs(String hql,
                                                          Map<String, Object> paramsMap,
                                                          PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(userDimissionLogsDao.count("select count(t) " + hql, paramsMap));
            return userDimissionLogsDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return userDimissionLogsDao.find(hql, paramsMap);
        }

    }

    @Override
    public UserDimissionLogs getUserDimissionLogs(Long id) {
        return userDimissionLogsDao.get(UserDimissionLogs.class, id);
    }

    @Override
    public void addUserDimissionLogs(UserDimissionLogs userDimissionLogs) {
        this.userDimissionLogsDao.save(userDimissionLogs);
    }

    @Override
    public void updateUserDimissionLogs(UserDimissionLogs userDimissionLogs) {
        this.userDimissionLogsDao.update(userDimissionLogs);
    }

    @Override
    public void delUserDimissionLogs(UserDimissionLogs userDimissionLogs) {
        userDimissionLogs = getUserDimissionLogs(userDimissionLogs.getId());
        userDimissionLogs.setDisabled(true);
        updateUserDimissionLogs(userDimissionLogs);
    }

    @Override
    public void add_dimission_log(Long user_id) {
        try {
            if (user_id == null) {
                return;
            }
            User user = this.userService.getUser(user_id);
            if (user == null || user.getId() == null || user.getUser_status() != 4) {
                return;
            }
            UserDimissionLogs userDimissionLogs = new UserDimissionLogs();
            userDimissionLogs.setCreatetime(new Date());
            userDimissionLogs.setDisabled(false);
            userDimissionLogs.setUser(user);
            addUserDimissionLogs(userDimissionLogs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
