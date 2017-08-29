package com.yuyu.soft.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.entity.UserContract;
import com.yuyu.soft.entity.UserContractRenewal;
import com.yuyu.soft.service.IUserContractRenewalService;
import com.yuyu.soft.service.IUserContractService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

@Service("userContractRenewalService")
public class UserContractRenewalServiceImpl implements IUserContractRenewalService {

    @Resource
    private IBaseDao<UserContractRenewal> userContractRenewalDao;
    @Resource
    private IUserService                  userService;
    @Resource
    private IUserContractService          userContractService;

    @Override
    public List<UserContractRenewal> queryUserContractRenewal(Long user_id) {
        if (user_id == null) {
            return new ArrayList<UserContractRenewal>();
        }
        StringBuilder s = new StringBuilder();
        s.append("from UserContractRenewal t where t.disabled = false");
        s.append(" and t.user.id = ").append(user_id);
        s.append(" order by t.createtime asc");
        List<UserContractRenewal> userContractRenewals = userContractRenewalDao.find(s.toString());
        if ((userContractRenewals == null) || (userContractRenewals.size() == 0)) {
            return new ArrayList<UserContractRenewal>();
        }
        return userContractRenewals;
    }

    @Override
    public List<UserContractRenewal> queryUserContractRenewal(String hql,
                                                              Map<String, Object> paramsMap,
                                                              PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(userContractRenewalDao.count("select count(t) " + hql, paramsMap));
            return userContractRenewalDao.find(hql, paramsMap, pager.getStart(),
                pager.getPageSize());
        } else {
            return userContractRenewalDao.find(hql, paramsMap);
        }

    }

    @Override
    public UserContractRenewal getUserContractRenewal(Long id) {
        return userContractRenewalDao.get(UserContractRenewal.class, id);
    }

    @Override
    public void addUserContractRenewal(UserContractRenewal userContractRenewal) {
        this.userContractRenewalDao.save(userContractRenewal);
    }

    @Override
    public void updateUserContractRenewal(UserContractRenewal userContractRenewal) {
        this.userContractRenewalDao.update(userContractRenewal);
    }

    @Override
    public void delUserContractRenewal(UserContractRenewal userContractRenewal) {
        userContractRenewal = getUserContractRenewal(userContractRenewal.getId());
        userContractRenewal.setDisabled(true);
        updateUserContractRenewal(userContractRenewal);
        //this.userContractRenewalDao.delete(userContractRenewal);
    }

    @Override
    public ResultMsg user_contract_renewal_save(HttpServletRequest request, Long user_id) {

        List<UserContract> tempList = userContractService.queryUserContract(user_id);
        if (tempList.size() == 0) {
            return CommUtil.setResultMsgData(null, false, "人事档案合同对象为空，不能续签");
        }

        String user_contract_renewal_id = request.getParameter("user_contract_renewal_id");
        WebForm wf = new WebForm();
        UserContractRenewal userContractRenewal = null;
        if (CommUtil.isBlank(user_contract_renewal_id)) {
            userContractRenewal = (UserContractRenewal) wf.toPo(request, UserContractRenewal.class);
            userContractRenewal.setCreatetime(new Date());
            userContractRenewal.setDisabled(false);
        } else {
            UserContractRenewal dbUserContractRenewal = getUserContractRenewal(Long.valueOf(Long
                .parseLong(user_contract_renewal_id)));
            userContractRenewal = (UserContractRenewal) wf.toPo(request, dbUserContractRenewal);
        }

        ResultMsg rmg = user_contract_renewal_save_check(userContractRenewal, user_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        if (CommUtil.isBlank(user_contract_renewal_id)) {
            addUserContractRenewal(userContractRenewal);
        } else {
            updateUserContractRenewal(userContractRenewal);
        }
        return CommUtil.setResultMsgData(rmg, true, "人事档案合同续签保存成功");
    }

    //校验信息
    private ResultMsg user_contract_renewal_save_check(UserContractRenewal userContractRenewal,
                                                       Long user_id) {
        if (userContractRenewal == null) {
            return CommUtil.setResultMsgData(null, false, "人事档案合同续签对象为空");
        }
        if (user_id == null) {
            return CommUtil.setResultMsgData(null, false, "用户标识为空");
        }
        User user = userService.getUser(user_id);
        if (user == null || user.getId() == null) {
            return CommUtil.setResultMsgData(null, false, "用户对象为空");
        } else {
            userContractRenewal.setUser(user);
        }
        return CommUtil.setResultMsgData(null, true, "人事档案合同续签校验成功");
    }

}
