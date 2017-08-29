package com.yuyu.soft.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.Assessment;
import com.yuyu.soft.entity.Department;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.entity.UserSchoolaward;
import com.yuyu.soft.service.IAssessmentService;
import com.yuyu.soft.service.IDepartmentService;
import com.yuyu.soft.service.IUserSchoolawardService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

@Service("userSchoolawardService")
public class UserSchoolawardServiceImpl implements IUserSchoolawardService {

    @Resource
    private IBaseDao<UserSchoolaward> userSchoolawardDao;
    @Resource
    private IUserService              userService;
    @Resource
    private IAssessmentService        assessmentService;
    @Resource
    private IDepartmentService        departmentService;

    @Override
    public List<UserSchoolaward> queryUserSchoolaward(Long user_id, Long assessment_id) {
        if (user_id == null || assessment_id == null) {
            return new ArrayList<UserSchoolaward>();
        }
        StringBuilder s = new StringBuilder();
        s.append("from UserSchoolaward t where t.disabled = false");
        s.append(" and t.user.id = ").append(user_id);
        s.append(" and t.assessment.id = ").append(assessment_id);
        s.append(" order by t.createtime asc");
        List<UserSchoolaward> userSchoolawards = userSchoolawardDao.find(s.toString());
        if ((userSchoolawards == null) || (userSchoolawards.size() == 0)) {
            return new ArrayList<UserSchoolaward>();
        }
        return userSchoolawards;
    }

    @Override
    public List<UserSchoolaward> queryUserSchoolawardByAassessment(Long assessment_id) {
        if (assessment_id == null) {
            return new ArrayList<UserSchoolaward>();
        }
        StringBuilder s = new StringBuilder();
        s.append("from UserSchoolaward t where t.disabled = false");
        s.append(" and t.assessment.id = ").append(assessment_id);
        s.append(" order by t.createtime asc");
        List<UserSchoolaward> userSchoolawards = userSchoolawardDao.find(s.toString());
        if ((userSchoolawards == null) || (userSchoolawards.size() == 0)) {
            return new ArrayList<UserSchoolaward>();
        }
        return userSchoolawards;
    }

    @Override
    public List<UserSchoolaward> queryUserSchoolaward(Long user_id) {
        if (user_id == null) {
            return new ArrayList<UserSchoolaward>();
        }
        StringBuilder s = new StringBuilder();
        s.append("from UserSchoolaward t where t.disabled = false");
        s.append(" and t.user.id = ").append(user_id);
        s.append(" order by t.createtime asc");
        List<UserSchoolaward> userSchoolawards = userSchoolawardDao.find(s.toString());
        if ((userSchoolawards == null) || (userSchoolawards.size() == 0)) {
            return new ArrayList<UserSchoolaward>();
        }
        return userSchoolawards;
    }

    @Override
    public List<UserSchoolaward> queryUserSchoolaward(String hql, Map<String, Object> paramsMap,
                                                      PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(userSchoolawardDao.count("select count(t) " + hql, paramsMap));
            return userSchoolawardDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return userSchoolawardDao.find(hql, paramsMap);
        }

    }

    @Override
    public UserSchoolaward getUserSchoolaward(Long id) {
        return userSchoolawardDao.get(UserSchoolaward.class, id);
    }

    @Override
    public void addUserSchoolaward(UserSchoolaward userSchoolaward) {
        this.userSchoolawardDao.save(userSchoolaward);
    }

    @Override
    public void updateUserSchoolaward(UserSchoolaward userSchoolaward) {
        this.userSchoolawardDao.update(userSchoolaward);
    }

    @Override
    public void delUserSchoolaward(UserSchoolaward userSchoolaward) {
        userSchoolaward = getUserSchoolaward(userSchoolaward.getId());
        userSchoolaward.setDisabled(true);
        updateUserSchoolaward(userSchoolaward);
        //this.userSchoolawardDao.delete(userSchoolaward);
    }

    @Override
    public ResultMsg user_schoolaward_save(HttpServletRequest request, Long user_id) {
        String user_schoolaward_id = request.getParameter("user_schoolaward_id");
        WebForm wf = new WebForm();
        UserSchoolaward userSchoolaward = null;
        if (CommUtil.isBlank(user_schoolaward_id)) {
            userSchoolaward = (UserSchoolaward) wf.toPo(request, UserSchoolaward.class);
            userSchoolaward.setCreatetime(new Date());
            userSchoolaward.setDisabled(false);
        } else {
            UserSchoolaward dbUserSchoolaward = getUserSchoolaward(Long.valueOf(Long
                .parseLong(user_schoolaward_id)));
            userSchoolaward = (UserSchoolaward) wf.toPo(request, dbUserSchoolaward);
        }

        ResultMsg rmg = user_schoolaward_save_check(userSchoolaward, user_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        user_schoolaward_save_init(userSchoolaward);
        if (CommUtil.isBlank(user_schoolaward_id)) {
            addUserSchoolaward(userSchoolaward);
        } else {
            updateUserSchoolaward(userSchoolaward);
        }
        return CommUtil.setResultMsgData(rmg, true, "人事档案所获奖项保存成功");
    }

    //校验信息
    private ResultMsg user_schoolaward_save_check(UserSchoolaward userSchoolaward, Long user_id) {
        if (userSchoolaward == null) {
            return CommUtil.setResultMsgData(null, false, "人事档案所获奖项对象为空");
        }
        if (user_id == null) {
            return CommUtil.setResultMsgData(null, false, "用户标识为空");
        }
        User user = userService.getUser(user_id);
        if (user == null || user.getId() == null) {
            return CommUtil.setResultMsgData(null, false, "用户对象为空");
        } else {
            userSchoolaward.setUser(user);
        }
        return CommUtil.setResultMsgData(null, true, "人事档案所获奖项校验成功");
    }

    //对不符合数据库设计的字段初始化
    private void user_schoolaward_save_init(UserSchoolaward userSchoolaward) {
        if (userSchoolaward == null) {
            return;
        }
        //奖项名称
        String award_name = userSchoolaward.getAward_name();
        if (CommUtil.isNotNull(award_name) && award_name.length() > 50) {
            userSchoolaward.setAward_name("");
        }
        //奖项等级
        String award_level = userSchoolaward.getAward_level();
        if (CommUtil.isNotNull(award_level) && award_level.length() > 50) {
            userSchoolaward.setAward_level("");
        }
        //颁发单位
        String award_unit = userSchoolaward.getAward_unit();
        if (CommUtil.isNotNull(award_unit) && award_unit.length() > 50) {
            userSchoolaward.setAward_unit("");
        }
    }

    @Override
    public ResultMsg user_schoolaward_save(Assessment assessment) {
        ResultMsg rmg = user_schoolaward_save_check(assessment);
        if (!rmg.getResult()) {
            return rmg;
        }
        assessment = assessmentService.getAssessment(assessment.getId());

        List<UserSchoolaward> tempList = queryUserSchoolawardByAassessment(assessment.getId());
        batchDelUserSchoolaward(tempList);//如果存在删除

        Department department = departmentService.getDepartment(assessment.getDepartment().getId());
        String user_ids = assessment.getUser_ids();
        List<User> users = null;
        if (CommUtil.isBlank(user_ids)) {
            users = userService.queryUserByDepartment(department.getId());
        } else if (user_ids.indexOf(",") >= 0) {
            String[] user_id_arr = user_ids.split(",");
            users = new ArrayList<User>();
            for (int i = 0; i < user_id_arr.length; i++) {
                Long user_id = CommUtil.null2Long(user_id_arr[i]);
                if (!verify_user_id(user_id)) {
                    return CommUtil.setResultMsgData(null, false, "评优管理对象用户值异常");
                }
                User user = userService.getUser(user_id);
                users.add(user);
            }
        } else {
            Long user_id = CommUtil.null2Long(user_ids);
            if (!verify_user_id(user_id)) {
                return CommUtil.setResultMsgData(null, false, "评优管理对象用户值异常");
            }
            users = new ArrayList<User>();
            User user = userService.getUser(user_id);
            users.add(user);
        }
        if (users != null && users.size() > 0) {
            user_schoolaward_batch_save(users, assessment);
        }
        return CommUtil.setResultMsgData(rmg, true, "人事档案所获奖项保存成功");
    }

    //批量保存
    private void user_schoolaward_batch_save(List<User> users, Assessment assessment) {
        if (users == null || users.size() == 0 || assessment == null || assessment.getId() == null) {
            return;
        }
        for (User user : users) {
            user = userService.getUser(user.getId());
            UserSchoolaward userSchoolaward = new UserSchoolaward();
            userSchoolaward.setCreatetime(new Date());
            userSchoolaward.setDisabled(false);
            userSchoolaward.setUser(user);
            userSchoolaward.setAssessment(assessment);
            userSchoolaward.setAward_date(assessment.getAward_date());
            userSchoolaward.setAward_level(assessment.getAward_level());
            userSchoolaward.setAward_name(assessment.getAward_name());
            userSchoolaward.setAward_unit(assessment.getAward_unit());
            addUserSchoolaward(userSchoolaward);
        }
    }

    @Override
    public void batchDelUserSchoolaward(List<UserSchoolaward> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        for (UserSchoolaward userSchoolaward : list) {
            delUserSchoolaward(userSchoolaward);
        }
    }

    private boolean verify_user_id(Long user_id) {
        if (user_id == -1) {
            return false;
        } else {
            User user = userService.getUser(user_id);
            if (user == null || user.getId() == null) {
                return false;
            }
        }
        return true;
    }

    private ResultMsg user_schoolaward_save_check(Assessment assessment) {
        if (assessment == null || assessment.getId() == null || assessment.getDisabled()) {
            return CommUtil.setResultMsgData(null, false, "评优管理对象为空");
        }
        if (assessment.getDepartment() == null || assessment.getDepartment().getId() == null
            || assessment.getDepartment().getDisabled()) {
            return CommUtil.setResultMsgData(null, false, "评优管理部门科组对象为空");
        }
        return CommUtil.setResultMsgData(null, true, "校验成功");
    }
}
