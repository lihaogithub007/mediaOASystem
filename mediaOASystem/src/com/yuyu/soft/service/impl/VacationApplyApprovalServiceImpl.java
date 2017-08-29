package com.yuyu.soft.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.entity.VacationApply;
import com.yuyu.soft.entity.VacationApplyApproval;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.service.IVacationApplyApprovalService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

@Service("vacationApplyApprovalService")
public class VacationApplyApprovalServiceImpl implements IVacationApplyApprovalService {

    @Resource
    private IBaseDao<VacationApplyApproval> vacationApplyApprovalDao;
    @Resource
    private IUserService                    userService;

    @Override
    public List<VacationApplyApproval> queryVacationApplyApproval(Long vacation_apply_id) {
        if (vacation_apply_id == null) {
            return new ArrayList<VacationApplyApproval>();
        }
        StringBuilder s = new StringBuilder();
        s.append("from VacationApplyApproval t where t.disabled = false");
        s.append(" and t.vacationApply.id = ").append(vacation_apply_id);
        s.append(" order by t.createtime asc");
        List<VacationApplyApproval> vacationApplyApprovals = vacationApplyApprovalDao.find(s
            .toString());
        if ((vacationApplyApprovals == null) || (vacationApplyApprovals.size() == 0)) {
            return new ArrayList<VacationApplyApproval>();
        }
        return vacationApplyApprovals;
    }

    @Override
    public List<VacationApplyApproval> queryVacationApplyApproval(String hql,
                                                                  Map<String, Object> paramsMap,
                                                                  PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(vacationApplyApprovalDao.count("select count(t) " + hql, paramsMap));
            return vacationApplyApprovalDao.find(hql, paramsMap, pager.getStart(),
                pager.getPageSize());
        } else {
            return vacationApplyApprovalDao.find(hql, paramsMap);
        }

    }

    @Override
    public VacationApplyApproval getVacationApplyApproval(Long id) {
        return vacationApplyApprovalDao.get(VacationApplyApproval.class, id);
    }

    @Override
    public void addVacationApplyApproval(VacationApplyApproval vacationApplyApproval) {
        this.vacationApplyApprovalDao.save(vacationApplyApproval);
    }

    @Override
    public void updateVacationApplyApproval(VacationApplyApproval vacationApplyApproval) {
        this.vacationApplyApprovalDao.update(vacationApplyApproval);
    }

    @Override
    public void delVacationApplyApproval(VacationApplyApproval vacationApplyApproval) {
        vacationApplyApproval = getVacationApplyApproval(vacationApplyApproval.getId());
        vacationApplyApproval.setDisabled(true);
        updateVacationApplyApproval(vacationApplyApproval);
    }

    @Override
    public void batchDelVacationApplyApproval(VacationApply vacationApply) {
        List<VacationApplyApproval> oldList = queryVacationApplyApproval(vacationApply.getId());
        if (oldList != null && oldList.size() > 0) {
            for (VacationApplyApproval vaa : oldList) {
                delVacationApplyApproval(vaa);
            }
        }
    }

    @Override
    public ResultMsg save(VacationApply vacationApply, String vaa_arr) {
        ResultMsg rmg = save_check_and_init(vacationApply, vaa_arr);
        if (!rmg.getResult()) {
            return rmg;
        }
        batchDelVacationApplyApproval(vacationApply);
        List<VacationApplyApproval> vaaList = (List<VacationApplyApproval>) rmg.getData();
        if (vaaList != null && vaaList.size() > 0) {
            for (VacationApplyApproval vaa : vaaList) {
                addVacationApplyApproval(vaa);
            }
        }
        return CommUtil.setResultMsgData(rmg, true, "请假申请明细保存成功");
    }

    private ResultMsg save_check_and_init(VacationApply vacationApply, String vaa_arr) {
        if (vacationApply == null || vacationApply.getId() == null) {
            return CommUtil.setResultMsgData(null, false, "请假申请对象为空");
        }

        try {
            ResultMsg rmg = CommUtil.setResultMsgData(null, true, "校验成功");
            List<VacationApplyApproval> vaaList = new ArrayList<VacationApplyApproval>();
            JSONArray array = JSONArray.fromObject(vaa_arr);
            Date date = new Date();
            for (int i = 0; i < array.size(); i++) {
                JSONObject obj = (JSONObject) array.get(i);
                String department_name = CommUtil.null2String(obj.get("department_name"));
                if (CommUtil.isBlank(department_name)) {
                    return CommUtil.setResultMsgData(rmg, false, "审批部门不能为空");
                }

                Long approval_user_id = CommUtil.null2Long(obj.get("approval_user_id"));
                if (-1 == approval_user_id) {
                    return CommUtil.setResultMsgData(rmg, false, "审批人不能为空");
                }

                User user = userService.getUser(approval_user_id);
                if (user == null || user.getId() == null || user.getDisabled()
                    || user.getUser_status() == 4) {
                    return CommUtil.setResultMsgData(rmg, false, "审批人为空或已离职");
                }
                String approval_text = CommUtil.null2String(obj.get("approval_text"));
                if (CommUtil.isBlank(approval_text)) {
                    return CommUtil.setResultMsgData(rmg, false, "审批意见不能为空");
                }

                VacationApplyApproval vaa = new VacationApplyApproval();
                vaa.setDisabled(false);
                vaa.setCreatetime(date);
                vaa.setVacationApply(vacationApply);
                vaa.setUser(user);
                vaa.setDepartment_name(department_name);
                vaa.setApproval_text(approval_text);
                vaaList.add(vaa);
            }
            rmg.setData(vaaList);
            return rmg;
        } catch (Exception e) {
            return CommUtil.setResultMsgData(null, false, e.getMessage());
        }
    }
}
