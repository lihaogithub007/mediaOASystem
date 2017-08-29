package com.yuyu.soft.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.ContractSmsRemind;
import com.yuyu.soft.entity.Duty;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IContractSmsRemindService;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 合同到期提醒
 */
@Service("contractSmsRemindService")
public class ContractSmsRemindServiceImpl implements IContractSmsRemindService {

    @Resource
    private IBaseDao<ContractSmsRemind> contractSmsRemindDao;

    @Resource
    private IUserService                userService;

    @Resource
    private IDutyService                dutyService;

    /**
     * 定时任务专用
     */
    public List<ContractSmsRemind> queryContractSmsRemindForTimerTask() {
        StringBuilder s = new StringBuilder();
        s.append("from ContractSmsRemind t where t.disabled = false");
        return contractSmsRemindDao.find(s.toString());
    }

    /**
     * 合同到期提醒列表
     */
    @Override
    public List<ContractSmsRemind> queryContractSmsRemind(String hql,
                                                          Map<String, Object> paramsMap,
                                                          PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(contractSmsRemindDao.count("select count(t) " + hql, paramsMap));
            return contractSmsRemindDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return contractSmsRemindDao.find(hql, paramsMap);
        }
    }

    /**
     * 查询续签合同最大时间
     */
    public List<Object[]> queryContractSmsRemindMaxTime() {

        StringBuilder sql = new StringBuilder();
        sql.append("select temp.user_id, MAX(temp.expiration_time) from(")
            .append(
                "SELECT uc.user_id,uc.first_expiration_time as expiration_time from tb_user_contract uc where uc.disabled = 0 union ")
            .append(
                "select ucr.user_id, ucr.renewal_expiration_time as expiration_time from tb_user_contract_renewal ucr where ucr.disabled=0 ")
            .append(") temp group by temp.user_id");
        List<Object[]> list = contractSmsRemindDao.executeNativeQuery(sql.toString(), null, 0, -1);

        return list;
    }

    /**
     * 添加日程安排提醒保存
     */
    public ResultMsg add_save(ContractSmsRemind contractSmsRemind, Long user_id, Long duty_id) {
        ResultMsg rmg = save_check(contractSmsRemind, user_id, duty_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        if (contractSmsRemind.getCreatetime() == null) {
            contractSmsRemind.setCreatetime(new Date());
        }
        if (contractSmsRemind.getDisabled() == null) {
            contractSmsRemind.setDisabled(false);
        }

        addContractSmsRemind(contractSmsRemind);
        return CommUtil.setResultMsgData(rmg, true, "合同到期提醒保存成功");
    }

    /**
     * 合同到期提醒编辑保存
     */
    public ResultMsg edit_save(ContractSmsRemind contractSmsRemind, Long user_id, Long duty_id) {
        ResultMsg rmg = save_check(contractSmsRemind, user_id, duty_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        updateContractSmsRemind(contractSmsRemind);
        return CommUtil.setResultMsgData(rmg, true, "合同到期提醒编辑保存成功");
    }

    @Override
    public void updateContractSmsRemind(ContractSmsRemind contractSmsRemind) {
        this.contractSmsRemindDao.update(contractSmsRemind);
    }

    @Override
    public void addContractSmsRemind(ContractSmsRemind contractSmsRemind) {
        this.contractSmsRemindDao.save(contractSmsRemind);
    }

    private ResultMsg save_check(ContractSmsRemind contractSmsRemind, Long user_id, Long duty_id) {
        if (contractSmsRemind == null) {
            return CommUtil.setResultMsgData(null, false, "合同到期提醒对象为空");
        }
        if (contractSmsRemind.getRemind_type() == 1) {
            if (user_id == null) {
                return CommUtil.setResultMsgData(null, false, "被提醒人标识为空");
            } else {
                User user = userService.getUser(user_id);
                if (user == null) {
                    return CommUtil.setResultMsgData(null, false, "找不到用户记录");
                } else {
                    contractSmsRemind.setUser(user);
                }
            }
        }
        if (contractSmsRemind.getRemind_type() == 0) {
            if (duty_id == null) {
                return CommUtil.setResultMsgData(null, false, "被提醒岗位标识为空");
            } else {
                Duty duty = dutyService.getDuty(duty_id);
                if (duty == null) {
                    return CommUtil.setResultMsgData(null, false, "找不到岗位记录");
                } else {
                    contractSmsRemind.setDuty(duty);
                }
            }
        }
        String remind_content = contractSmsRemind.getRemind_content();
        if (CommUtil.isBlank(remind_content)) {
            return CommUtil.setResultMsgData(null, false, "提醒内容不能为空");
        } else if (remind_content.length() > 255) {
            contractSmsRemind.setRemind_content(remind_content.substring(0, 255));
        }
        return CommUtil.setResultMsgData(null, true, "合同到期提醒对象校验成功");
    }

    /**
     * 根据ID获取合同到期提醒对象
     * @param id
     * @return
     */
    public ContractSmsRemind getContractSmsRemind(Long id) {
        return contractSmsRemindDao.get(ContractSmsRemind.class, id);
    }

    /**
     * 删除合同到期提醒
     */
    public void delContractSmsRemind(ContractSmsRemind contractSmsRemind) {
        this.contractSmsRemindDao.delete(contractSmsRemind);
    }
}
