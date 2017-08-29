package com.yuyu.soft.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.ScheduleSmsRemind;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IScheduleSmsRemindService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 日程安排提醒
 *                       
 * @Filename: ScheduleSmsRemindServiceImpl.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Service("scheduleSmsRemindService")
public class ScheduleSmsRemindServiceImpl implements IScheduleSmsRemindService {

    @Resource
    private IBaseDao<ScheduleSmsRemind> scheduleSmsRemindDao;
    @Resource
    private IUserService                userService;

    @Override
    public List<ScheduleSmsRemind> queryScheduleSmsRemindForTimerTask(String current_time) {
        StringBuilder s = new StringBuilder();
        s.append("from ScheduleSmsRemind t where t.disabled = false");
        s.append(" and t.remind_time like '%").append(current_time).append("%'");
        s.append(" order by t.createtime desc");
        return scheduleSmsRemindDao.find(s.toString());
    }

    public List<ScheduleSmsRemind> queryScheduleSmsRemind(String hql,
                                                          Map<String, Object> paramsMap,
                                                          PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(scheduleSmsRemindDao.count("select count(t) " + hql, paramsMap));
            return scheduleSmsRemindDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return scheduleSmsRemindDao.find(hql, paramsMap);
        }

    }

    @Override
    public ScheduleSmsRemind getScheduleSmsRemind(Long id) {
        return scheduleSmsRemindDao.get(ScheduleSmsRemind.class, id);
    }

    @Override
    public void addScheduleSmsRemind(ScheduleSmsRemind scheduleSmsRemind) {
        this.scheduleSmsRemindDao.save(scheduleSmsRemind);
    }

    @Override
    public void updateScheduleSmsRemind(ScheduleSmsRemind scheduleSmsRemind) {
        this.scheduleSmsRemindDao.update(scheduleSmsRemind);
    }

    @Override
    public void delScheduleSmsRemind(ScheduleSmsRemind scheduleSmsRemind) {
        this.scheduleSmsRemindDao.delete(scheduleSmsRemind);
    }

    @Override
    public ResultMsg add_save(ScheduleSmsRemind scheduleSmsRemind, Long user_id) {
        ResultMsg rmg = save_check(scheduleSmsRemind, user_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        if (scheduleSmsRemind.getCreatetime() == null) {
            scheduleSmsRemind.setCreatetime(new Date());
        }
        if (scheduleSmsRemind.getDisabled() == null) {
            scheduleSmsRemind.setDisabled(false);
        }
        addScheduleSmsRemind(scheduleSmsRemind);
        return CommUtil.setResultMsgData(rmg, true, "日程安排提醒保存成功");
    }

    @Override
    public ResultMsg edit_save(ScheduleSmsRemind scheduleSmsRemind, Long user_id) {
        ResultMsg rmg = save_check(scheduleSmsRemind, user_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        updateScheduleSmsRemind(scheduleSmsRemind);
        return CommUtil.setResultMsgData(rmg, true, "日程安排提醒编辑保存成功");
    }

    private ResultMsg save_check(ScheduleSmsRemind scheduleSmsRemind, Long user_id) {
        if (scheduleSmsRemind == null) {
            return CommUtil.setResultMsgData(null, false, "日程安排提醒对象为空");
        }
        if (user_id == null) {
            return CommUtil.setResultMsgData(null, false, "被提醒人标识为空");
        } else {
            User user = userService.getUser(user_id);
            if (user == null) {
                return CommUtil.setResultMsgData(null, false, "找不到用户记录");
            } else {
                scheduleSmsRemind.setUser(user);
            }
        }
        if (CommUtil.isBlank(scheduleSmsRemind.getUser_mobile())) {
            return CommUtil.setResultMsgData(null, false, "被提醒人手机号不能为空");
        } else if (scheduleSmsRemind.getUser_mobile().length() != 11) {
            return CommUtil.setResultMsgData(null, false, "被提醒人手机号格式错误");
        }
        if (CommUtil.isBlank(scheduleSmsRemind.getRemind_name())) {
            return CommUtil.setResultMsgData(null, false, "日程安排提醒名称不能为空");
        } else if (scheduleSmsRemind.getRemind_name().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "日程安排提醒名称最多50字符");
        }
        if (CommUtil.isBlank(scheduleSmsRemind.getRemind_content())) {
            return CommUtil.setResultMsgData(null, false, "日程安排提醒内容不能为空");
        } else if (scheduleSmsRemind.getRemind_content().length() > 255) {
            return CommUtil.setResultMsgData(null, false, "日程安排提醒内容最多255字符");
        }
        if (CommUtil.isBlank(scheduleSmsRemind.getRemind_time())) {
            return CommUtil.setResultMsgData(null, false, "日程安排提醒时间不能为空");
        } else {
            String[] times = scheduleSmsRemind.getRemind_time().split(",");
            if (times == null || times.length == 0) {
                return CommUtil.setResultMsgData(null, false, "日程安排提醒时间不能为空");
            } else {
                for (String remind_time : times) {
                    if (null == CommUtil.formatDate(remind_time.trim(), "yyyy-MM-dd HH:mm:ss")) {
                        return CommUtil.setResultMsgData(null, false, "日程安排提醒时间格式有错");
                    }
                }
            }
        }
        return CommUtil.setResultMsgData(null, true, "日程安排提醒对象校验成功");
    }
}
