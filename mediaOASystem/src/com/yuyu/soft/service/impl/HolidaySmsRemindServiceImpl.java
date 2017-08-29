package com.yuyu.soft.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.HolidaySmsRemind;
import com.yuyu.soft.service.IHolidaySmsRemindService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 节日短信问候
 *                       
 * @Filename: HolidaySmsRemindServiceImpl.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Service("holidaySmsRemindService")
public class HolidaySmsRemindServiceImpl implements IHolidaySmsRemindService {

    @Override
    public List<HolidaySmsRemind> queryTodayHolidaySmsReminds() {
        StringBuilder s = new StringBuilder();
        s.append("from HolidaySmsRemind t where t.disabled = false");
        s.append(" and date_format(t.remind_time,'%m-%d') = date_format(now(),'%m-%d')");
        s.append(" and t.remind_type = 0");
        s.append(" order by t.createtime desc");
        return holidaySmsRemindDao.find(s.toString());
    }

    @Override
    public List<HolidaySmsRemind> queryTodayBirthDaySmsReminds() {
        StringBuilder s = new StringBuilder();
        s.append("from HolidaySmsRemind t where t.disabled = false");
        s.append(" and t.remind_type = 1");
        s.append(" order by t.createtime desc");
        return holidaySmsRemindDao.find(s.toString());
    }

    @Resource
    private IBaseDao<HolidaySmsRemind> holidaySmsRemindDao;

    public List<HolidaySmsRemind> queryHolidaySmsRemind(String hql, Map<String, Object> paramsMap,
                                                        PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(holidaySmsRemindDao.count("select count(t) " + hql, paramsMap));
            return holidaySmsRemindDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return holidaySmsRemindDao.find(hql, paramsMap);
        }

    }

    @Override
    public HolidaySmsRemind getHolidaySmsRemind(Long id) {
        return holidaySmsRemindDao.get(HolidaySmsRemind.class, id);
    }

    @Override
    public void addHolidaySmsRemind(HolidaySmsRemind holidaySmsRemind) {
        this.holidaySmsRemindDao.save(holidaySmsRemind);
    }

    @Override
    public void updateHolidaySmsRemind(HolidaySmsRemind holidaySmsRemind) {
        this.holidaySmsRemindDao.update(holidaySmsRemind);
    }

    @Override
    public void delHolidaySmsRemind(HolidaySmsRemind holidaySmsRemind) {
        this.holidaySmsRemindDao.delete(holidaySmsRemind);
    }

    @Override
    public ResultMsg add_save(HolidaySmsRemind holidaySmsRemind) {
        ResultMsg rmg = save_check(holidaySmsRemind);
        if (!rmg.getResult()) {
            return rmg;
        }
        if (holidaySmsRemind.getCreatetime() == null) {
            holidaySmsRemind.setCreatetime(new Date());
        }
        if (holidaySmsRemind.getDisabled() == null) {
            holidaySmsRemind.setDisabled(false);
        }
        addHolidaySmsRemind(holidaySmsRemind);
        return CommUtil.setResultMsgData(rmg, true, "节日短信问候保存成功");
    }

    @Override
    public ResultMsg edit_save(HolidaySmsRemind holidaySmsRemind) {
        ResultMsg rmg = save_check(holidaySmsRemind);
        if (!rmg.getResult()) {
            return rmg;
        }
        updateHolidaySmsRemind(holidaySmsRemind);
        return CommUtil.setResultMsgData(rmg, true, "节日短信问候编辑保存成功");
    }

    private ResultMsg save_check(HolidaySmsRemind holidaySmsRemind) {
        if (holidaySmsRemind == null) {
            return CommUtil.setResultMsgData(null, false, "节日短信问候对象为空");
        }
        if (CommUtil.isBlank(holidaySmsRemind.getRemind_name())) {
            return CommUtil.setResultMsgData(null, false, "节日短信问候提醒名称不能为空");
        } else if (holidaySmsRemind.getRemind_name().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "节日短信问候提醒名称最多50字符");
        }
        if (CommUtil.isBlank(holidaySmsRemind.getRemind_content())) {
            return CommUtil.setResultMsgData(null, false, "节日短信问候提醒内容不能为空");
        } else if (holidaySmsRemind.getRemind_content().length() > 255) {
            return CommUtil.setResultMsgData(null, false, "节日短信问候提醒内容最多255字符");
        }
        Integer remind_type = holidaySmsRemind.getRemind_type();
        if (remind_type == null) {
            return CommUtil.setResultMsgData(null, false, "节日短信问候提醒类型不能为空");
        } else if (0 == remind_type) {
            if (holidaySmsRemind.getRemind_time() == null) {
                return CommUtil.setResultMsgData(null, false, "节日短信问候提醒时间不能为空");
            }
        } else if (1 == remind_type) {
            holidaySmsRemind.setRemind_time(null);
        } else {
            return CommUtil.setResultMsgData(null, false, "节日短信问候提醒类型值为非法值");
        }
        return CommUtil.setResultMsgData(null, true, "节日短信问候对象校验成功");
    }
}
