package com.yuyu.soft.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.Duty;
import com.yuyu.soft.service.IDutyResService;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 岗位管理
 *                       
 * @Filename: DutyServiceImpl.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Service("dutyService")
public class DutyServiceImpl implements IDutyService {

    @Resource
    private IBaseDao<Duty>  dutyDao;
    @Resource
    private IUserService    userService;
    @Resource
    private IDutyResService dutyResService;

    @Override
    public List<Duty> queryAllDuty() {
        return queryDutyUnderDepartment(null);
    }

    @Override
    public List<Duty> queryDutyUnderDepartment(Long department_id) {
        StringBuilder s = new StringBuilder();
        s.append("from Duty t where t.disabled = false");
        if (CommUtil.isNotNull(department_id)) {
            s.append(" and t.department.id = ").append(department_id);
        }
        s.append(" order by t.createtime desc");
        return dutyDao.find(s.toString());
    }

    public List<Duty> queryDuty(String hql, Map<String, Object> paramsMap, PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(dutyDao.count("select count(t) " + hql, paramsMap));
            return dutyDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return dutyDao.find(hql, paramsMap);
        }

    }

    @Override
    public Duty getDuty(Long id) {
        return dutyDao.get(Duty.class, id);
    }

    @Override
    public void addDuty(Duty duty) {
        this.dutyDao.save(duty);
    }

    @Override
    public void updateDuty(Duty duty) {
        this.dutyDao.update(duty);
    }

    @Override
    public void delDuty(Duty duty) {
        duty = getDuty(duty.getId());
        duty.setDisabled(true);
        updateDuty(duty);
    }

    @Override
    public void batchDelDuty(List<Duty> list) {
        if (list != null && list.size() > 0) {
            for (Duty duty : list) {
                delDuty(duty);
            }
        }
    }

    @Override
    public boolean verify_duty_name(Long duty_id, Long department_id, String duty_name) {
        boolean flag = true;

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("duty_name", duty_name);
        StringBuilder s = new StringBuilder();
        s.append("from Duty t where t.disabled = false");
        s.append(" and t.duty_name =:duty_name");
        if (CommUtil.isNotNull(duty_id)) {
            s.append(" and t.id !=:duty_id");
            paramsMap.put("duty_id", duty_id);
        }
        if (CommUtil.isNotNull(department_id)) {
            s.append(" and t.department.id =:department_id");
            paramsMap.put("department_id", department_id);
        }

        List<Duty> dutys = dutyDao.find(s.toString(), paramsMap);
        if ((dutys != null) && (dutys.size() > 0)) {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean verify_duty_list_contains_normal(List<Duty> list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        for (Duty duty : list) {
            if (!duty.getDisabled()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ResultMsg delete_save(Long id) {
        ResultMsg rmg = delete_save_check(id);
        if (!rmg.getResult()) {
            return rmg;
        }
        delDuty(getDuty(id));
        return CommUtil.setResultMsgData(rmg, true, "岗位删除成功");
    }

    private ResultMsg delete_save_check(Long id) {
        if (id == null) {
            return CommUtil.setResultMsgData(null, false, "岗位标识为空");
        }
        Duty duty = getDuty(id);
        if (duty == null) {
            return CommUtil.setResultMsgData(null, false, "找不到岗位记录");
        }
        if (userService.verify_user_list_contains_normal(duty.getUserList())) {
            return CommUtil.setResultMsgData(null, false, "该岗位下存在用户，不能删除");
        }
        return CommUtil.setResultMsgData(null, true, "校验成功");
    }

    @Override
    public ResultMsg duty_res_save(Long id, String res_ids) {
        if (id == null) {
            return CommUtil.setResultMsgData(null, false, "岗位标识为空");
        }
        Duty duty = getDuty(id);
        if (duty == null) {
            return CommUtil.setResultMsgData(null, false, "找不到岗位记录");
        }
        dutyResService.duty_res_save(res_ids, duty.getId());
        return CommUtil.setResultMsgData(null, true, "岗位权限编辑保存成功");
    }
}
