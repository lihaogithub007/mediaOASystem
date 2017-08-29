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
import com.yuyu.soft.service.IUserContractService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

@Service("userContractService")
public class UserContractServiceImpl implements IUserContractService {

    @Resource
    private IBaseDao<UserContract> userContractDao;
    @Resource
    private IUserService           userService;

    @Override
    public List<UserContract> queryUserContract(Long user_id) {
        if (user_id == null) {
            return new ArrayList<UserContract>();
        }
        StringBuilder s = new StringBuilder();
        s.append("from UserContract t where t.disabled = false");
        s.append(" and t.user.id = ").append(user_id);
        s.append(" order by t.createtime asc");
        List<UserContract> userContracts = userContractDao.find(s.toString());
        if ((userContracts == null) || (userContracts.size() == 0)) {
            return new ArrayList<UserContract>();
        }
        return userContracts;
    }

    @Override
    public List<UserContract> queryUserContract(String hql, Map<String, Object> paramsMap,
                                                PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(userContractDao.count("select count(t) " + hql, paramsMap));
            return userContractDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return userContractDao.find(hql, paramsMap);
        }

    }

    @Override
    public UserContract getUserContract(Long id) {
        return userContractDao.get(UserContract.class, id);
    }

    @Override
    public void addUserContract(UserContract userContract) {
        this.userContractDao.save(userContract);
    }

    @Override
    public void updateUserContract(UserContract userContract) {
        this.userContractDao.update(userContract);
    }

    @Override
    public void delUserContract(UserContract userContract) {
        userContract = getUserContract(userContract.getId());
        userContract.setDisabled(true);
        updateUserContract(userContract);
        //this.userContractDao.delete(userContract);
    }

    @Override
    public ResultMsg user_contract_save(HttpServletRequest request, Long user_id) {
        String user_contract_id = request.getParameter("user_contract_id");
        WebForm wf = new WebForm();
        UserContract userContract = null;

        List<UserContract> tempList = queryUserContract(user_id);
        if (tempList.size() == 0) {
            userContract = (UserContract) wf.toPo(request, UserContract.class);
            userContract.setCreatetime(new Date());
            userContract.setDisabled(false);
        } else if (tempList.size() == 1) {
            if (CommUtil.isBlank(user_contract_id)) {
                UserContract dbUserContract = tempList.get(0);
                userContract = (UserContract) wf.toPo(request, dbUserContract);
            } else {
                UserContract dbUserContract = getUserContract(Long.valueOf(Long
                    .parseLong(user_contract_id)));
                userContract = (UserContract) wf.toPo(request, dbUserContract);
            }
        } else {
            return CommUtil.setResultMsgData(null, false, "人事档案合同存在多条");
        }

        ResultMsg rmg = user_contract_save_check(userContract, user_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        user_contract_save_init(userContract);
        if (tempList.size() == 0) {
            addUserContract(userContract);
        } else if (tempList.size() == 1) {
            updateUserContract(userContract);
        }
        return CommUtil.setResultMsgData(rmg, true, "人事档案合同保存成功");
    }

    //校验信息
    private ResultMsg user_contract_save_check(UserContract userContract, Long user_id) {
        if (userContract == null) {
            return CommUtil.setResultMsgData(null, false, "人事档案合同对象为空");
        }
        if (user_id == null) {
            return CommUtil.setResultMsgData(null, false, "用户标识为空");
        }
        User user = userService.getUser(user_id);
        if (user == null || user.getId() == null) {
            return CommUtil.setResultMsgData(null, false, "用户对象为空");
        } else {
            userContract.setUser(user);
        }
        return CommUtil.setResultMsgData(null, true, "人事档案合同校验成功");
    }

    //对不符合数据库设计的字段初始化
    private void user_contract_save_init(UserContract userContract) {
        if (userContract == null) {
            return;
        }
        //餐卡卡号
        String meal_card_number = userContract.getMeal_card_number();
        if (CommUtil.isNotNull(meal_card_number) && meal_card_number.length() > 50) {
            userContract.setMeal_card_number("");
        }
        //公务卡号
        String business_card_number = userContract.getBusiness_card_number();
        if (CommUtil.isNotNull(business_card_number) && business_card_number.length() > 50) {
            userContract.setBusiness_card_number("");
        }
        //合同类型
        Integer contract_type = userContract.getContract_type();
        if (contract_type != null
            && CommUtil.isBlank(Constants.CONTRACT_TYPE_MAP.get(contract_type.toString()))) {
            userContract.setContract_type(0);
        }
    }
}
