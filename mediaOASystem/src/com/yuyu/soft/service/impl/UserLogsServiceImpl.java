package com.yuyu.soft.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.entity.UserLogs;
import com.yuyu.soft.service.IUserLogsService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.PagerInfo;

/**
 * 用户入离职日志
 *                       
 * @Filename: UserLogsServiceImpl.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Service("userLogsService")
public class UserLogsServiceImpl implements IUserLogsService {

    @Resource
    private IBaseDao<UserLogs> userLogsDao;
    @Resource
    private IUserService       userService;

    public List<UserLogs> queryUserLogs(String hql, Map<String, Object> paramsMap, PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(userLogsDao.count("select count(t) " + hql, paramsMap));
            return userLogsDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return userLogsDao.find(hql, paramsMap);
        }

    }

    @Override
    public UserLogs getUserLogs(Long id) {
        return userLogsDao.get(UserLogs.class, id);
    }

    @Override
    public void addUserLogs(UserLogs userLogs) {
        this.userLogsDao.save(userLogs);
    }

    @Override
    public void add_log(Long user_id, Long create_user_id, String log_info,String dimission_reason, String dimission_time) {
        if (user_id == null || create_user_id == null) {
            return;
        }
        User user = this.userService.getUser(user_id);
        User createUser = this.userService.getUser(create_user_id);
        if (user == null || user.getId() == null || createUser == null
            || createUser.getId() == null) {
            return;
        }
        UserLogs userLogs = new UserLogs();
        userLogs.setDisabled(false);
        userLogs.setCreatetime(new Date());
        userLogs.setUser(user);
        userLogs.setCreateUser(createUser);
        userLogs.setLog_info(log_info);
        if (null != dimission_reason) {
        	userLogs.setDimission_reason(dimission_reason);
		}
        if (null != dimission_time) {
        	Date date = null;
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(dimission_time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
				userLogs.setDimission_time(date);
		}
        this.addUserLogs(userLogs);
    }
}
