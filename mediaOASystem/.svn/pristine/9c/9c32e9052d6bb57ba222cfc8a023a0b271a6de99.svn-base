package com.yuyu.soft.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.DepartmentAwards;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IDepartmentAwardsService;
import com.yuyu.soft.service.IDepartmentService;
import com.yuyu.soft.service.IDispatchUnitService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

@Service("departmentAwardsService")
public class DepartmentAwardsServiceImpl implements IDepartmentAwardsService {

    @Resource
    private IBaseDao<DepartmentAwards> departmentAwardsDao;
    @Resource
    private IDispatchUnitService       dispatchUnitService;
    @Resource
    private IUserService               userService;
    @Resource
    private IDepartmentService         departmentService;

    @Override
    public List<DepartmentAwards> queryAllDepartmentAwards() {
        StringBuilder s = new StringBuilder();
        s.append("from DepartmentAwards t where t.disabled = false");
        s.append(" order by t.createtime desc");
        return departmentAwardsDao.find(s.toString());
    }

    @Override
    public int getCountForExportExcel(String awards_time, Long user_id) {
        String sql = getCountSQL() + getCommonSQL(awards_time, user_id);
        return departmentAwardsDao.countBySql(sql, null);
    }

    @Override
    public List<Object[]> getDepartmentAwardsForExportExcel(String awards_time, Long user_id,
                                                            int beginIndex, int pageSize) {

        String sql = getQueryListSQL() + getCommonSQL(awards_time, user_id);
        List<Object[]> list = (ArrayList<Object[]>) departmentAwardsDao.findBySql(sql, null,
            beginIndex, pageSize);

        return list;
    }

    private String getCountSQL() {
        return "select count(da.id)";
    }

    private String getQueryListSQL() {
        StringBuffer s = new StringBuffer();
        s.append("select da.awards_time");//参评时间            
        s.append(" ,da.awards_name");//奖项名称            
        s.append(" ,da.awards_works");//参评作品         
        s.append(" ,lu.true_name");//参评项目负责人
        s.append(" ,da.awards_department_ids");//参加科组
        s.append(" ,da.awards_user_ids");//参评人员
        s.append(" ,da.awards_result");//评奖结果 
        s.append(" ,da.awards_bonus");//奖金（元）
        s.append(" ,da.remark");//备注
        return s.toString();
    }

    private String getCommonSQL(String awards_time, Long user_id) {
        StringBuffer s = new StringBuffer();
        s.append(" from tb_department_awards da");
        s.append(" left join tb_user lu");
        s.append(" on da.awards_leader_user_id = lu.id");
        s.append(" where da.disabled = 0");
        if (CommUtil.isNotNull(awards_time)) {
            s.append(" and da.awards_time =  str_to_date('").append(awards_time)
                .append("','%Y-%m-%d')");
        }
        if (CommUtil.isNotNull(user_id)) {
            s.append(" and (da.awards_user_ids like '%").append(user_id).append(",%'");
            s.append(" or da.awards_user_ids like '%,").append(user_id).append("%'");
            s.append(" or da.awards_user_ids like '").append(user_id).append("')");
        }
        return s.toString();
    }

    @Override
    public List<DepartmentAwards> queryDepartmentAwards(String hql, Map<String, Object> paramsMap,
                                                        PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(departmentAwardsDao.count("select count(t) " + hql, paramsMap));
            return departmentAwardsDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return departmentAwardsDao.find(hql, paramsMap);
        }

    }

    @Override
    public DepartmentAwards getDepartmentAwards(Long id) {
        return departmentAwardsDao.get(DepartmentAwards.class, id);
    }

    @Override
    public void addDepartmentAwards(DepartmentAwards departmentAwards) {
        this.departmentAwardsDao.save(departmentAwards);
    }

    @Override
    public void updateDepartmentAwards(DepartmentAwards departmentAwards) {
        this.departmentAwardsDao.update(departmentAwards);
    }

    @Override
    public void delDepartmentAwards(DepartmentAwards departmentAwards) {
        departmentAwards = getDepartmentAwards(departmentAwards.getId());
        departmentAwards.setDisabled(true);
        updateDepartmentAwards(departmentAwards);
    }

    @Override
    public void batchDelDepartmentAwards(List<DepartmentAwards> list) {
        if (list != null && list.size() > 0) {
            for (DepartmentAwards da : list) {
                delDepartmentAwards(da);
            }
        }
    }

    @Override
    public ResultMsg add_save(DepartmentAwards departmentAwards, Long user_id, Long leader_user_id) {
        ResultMsg rmg = save_check(departmentAwards, user_id, leader_user_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        departmentAwards.setDisabled(false);
        departmentAwards.setCreatetime(new Date());
        departmentAwards.setCreate_user(userService.getUser(user_id));
        addDepartmentAwards(departmentAwards);
        return CommUtil.setResultMsgData(rmg, true, "部门评奖保存成功");
    }

    @Override
    public ResultMsg edit_save(DepartmentAwards departmentAwards, Long user_id, Long leader_user_id) {
        ResultMsg rmg = save_check(departmentAwards, user_id, leader_user_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        departmentAwards.setUpdate_time(new Date());
        departmentAwards.setUpdate_user(userService.getUser(user_id));
        updateDepartmentAwards(departmentAwards);
        return CommUtil.setResultMsgData(rmg, true, "部门评奖编辑保存成功");
    }

    private ResultMsg save_check(DepartmentAwards departmentAwards, Long user_id,
                                 Long leader_user_id) {
        if (departmentAwards == null) {
            return CommUtil.setResultMsgData(null, false, "部门评奖对象为空");
        }
        if (user_id == null) {
            return CommUtil.setResultMsgData(null, false, "登录超时，请重新登录后操作");
        }
        if (leader_user_id == null) {
            return CommUtil.setResultMsgData(null, false, "负责人标识为空");
        }
        User leader_user = userService.getUser(leader_user_id);
        if (leader_user == null) {
            return CommUtil.setResultMsgData(null, false, "找不到负责人对应用户对象");
        } else {
            departmentAwards.setAwards_leader_user(leader_user);
        }
        ResultMsg rmg = userService.verify_user_ids(departmentAwards.getAwards_user_ids(),
            "参评人员值异常", true);
        if (!rmg.getResult()) {
            return rmg;
        }
        rmg = departmentService.verify_department_ids(departmentAwards.getAwards_department_ids(),
            "参加科组值异常", true);
        if (!rmg.getResult()) {
            return rmg;
        }
        if (CommUtil.isBlank(departmentAwards.getAwards_name())) {
            return CommUtil.setResultMsgData(null, false, "奖项名称不能为空");
        }
        if (departmentAwards.getAwards_name().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "奖项名称最多50字符");
        }
        if (CommUtil.isBlank(departmentAwards.getAwards_works())) {
            return CommUtil.setResultMsgData(null, false, "参评作品不能为空");
        }
        if (departmentAwards.getAwards_works().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "参评作品最多50字符");
        }
        if (CommUtil.isNotNull(departmentAwards.getAwards_result())
            && departmentAwards.getAwards_result().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "评奖结果最多50字符");
        }
        return CommUtil.setResultMsgData(null, true, "部门评奖对象校验成功");
    }
}
