package com.yuyu.soft.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.Res;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.entity.UserRes;
import com.yuyu.soft.service.IResService;
import com.yuyu.soft.service.IUserResService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;

/**
 * 用户资源
 *                       
 * @Filename: UserResServiceImpl.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Service("userResService")
public class UserResServiceImpl implements IUserResService {

    @Resource
    private IBaseDao<UserRes> userResDao;
    @Resource
    private IUserService      userService;
    @Resource
    private IResService       resService;

    @Override
    public List<Long> queryUserResIds(Long user_id) {
        if (user_id == null) {
            return new ArrayList<Long>();
        }
        User user = userService.getUser(user_id);
        if (user == null) {
            return new ArrayList<Long>();
        }
        StringBuffer s = new StringBuffer();
        s.append("select res_id from tb_user_res");
        s.append(" where disabled = 0 and user_id = ").append(user_id);
        if (user.getDuty() != null && user.getDuty().getId() != null) {
            s.append(" union");
            s.append(" select res_id from tb_duty_res");
            s.append(" where disabled = 0 and  duty_id = ").append(user.getDuty().getId());
        }
        List<Long> list = userResDao.executeNativeQuery(s.toString(), null, 0, -1);
        if (list == null || list.size() == 0) {
            return new ArrayList<Long>();
        }
        return list;
    }

    @Override
    public UserRes getUserRes(Long id) {
        return userResDao.get(UserRes.class, id);
    }

    @Override
    public void addUserRes(UserRes userRes) {
        this.userResDao.save(userRes);

    }

    @Override
    public void delUserRes(UserRes userRes) {
        this.userResDao.delete(userRes);
    }

    @Override
    public void user_res_save(String res_ids, Long user_id) {
        if (user_id == null) {
            return;
        }
        User user = userService.getUser(user_id);
        if (user == null) {
            return;
        }

        try {
            user_res_delete(user_id);
            if (CommUtil.isNotNull(res_ids)) {
                String[] res_id_arr = res_ids.split(",");
                if (res_id_arr != null && res_id_arr.length > 0) {
                    for (String res_id : res_id_arr) {
                        Res res = resService.getRes(Long.parseLong(res_id.trim()));
                        if (res == null) {
                            continue;
                        } else {
                            UserRes userRes = new UserRes();
                            userRes.setDisabled(false);
                            userRes.setCreatetime(new Date());
                            userRes.setUser(user);
                            userRes.setRes(res);
                            addUserRes(userRes);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除用户原来权限
    private void user_res_delete(Long user_id) {
        if (user_id == null) {
            return;
        }
        List<UserRes> userResList = queryByUserId(user_id);
        if (userResList != null && userResList.size() > 0) {
            for (UserRes userRes : userResList) {
                userRes = getUserRes(userRes.getId());
                delUserRes(userRes);
            }
        }
    }

    @Override
    public List<UserRes> queryByUserId(Long user_id) {
        StringBuilder s = new StringBuilder();
        s.append("from UserRes t where t.disabled = false");
        s.append(" and t.user.id = ").append(user_id);
        return userResDao.find(s.toString());
    }

}
