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
import com.yuyu.soft.entity.UserEducation;
import com.yuyu.soft.service.IUserEducationService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

@Service("userEducationService")
public class UserEducationServiceImpl implements IUserEducationService {

    @Resource
    private IBaseDao<UserEducation> userEducationDao;
    @Resource
    private IUserService            userService;

    @Override
    public List<UserEducation> queryUserEducation(Long user_id) {
        if (user_id == null) {
            return new ArrayList<UserEducation>();
        }
        StringBuilder s = new StringBuilder();
        s.append("from UserEducation t where t.disabled = false");
        s.append(" and t.user.id = ").append(user_id);
        s.append(" order by t.createtime asc");
        List<UserEducation> userEducations = userEducationDao.find(s.toString());
        if ((userEducations == null) || (userEducations.size() == 0)) {
            return new ArrayList<UserEducation>();
        }
        return userEducations;
    }

    @Override
    public List<UserEducation> queryUserEducation(String hql, Map<String, Object> paramsMap,
                                                  PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(userEducationDao.count("select count(t) " + hql, paramsMap));
            return userEducationDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return userEducationDao.find(hql, paramsMap);
        }

    }

    @Override
    public UserEducation getUserEducation(Long id) {
        return userEducationDao.get(UserEducation.class, id);
    }

    @Override
    public void addUserEducation(UserEducation userEducation) {
        this.userEducationDao.save(userEducation);
    }

    @Override
    public void updateUserEducation(UserEducation userEducation) {
        this.userEducationDao.update(userEducation);
    }

    @Override
    public void delUserEducation(UserEducation userEducation) {
        userEducation = getUserEducation(userEducation.getId());
        userEducation.setDisabled(true);
        updateUserEducation(userEducation);
        //this.userEducationDao.delete(userEducation);
    }

    @Override
    public ResultMsg user_education_save(HttpServletRequest request, Long user_id) {
        String user_education_id = request.getParameter("user_education_id");
        WebForm wf = new WebForm();
        UserEducation userEducation = null;
        if (CommUtil.isBlank(user_education_id)) {
            userEducation = (UserEducation) wf.toPo(request, UserEducation.class);
            userEducation.setCreatetime(new Date());
            userEducation.setDisabled(false);
        } else {
            UserEducation dbUserEducation = getUserEducation(Long.valueOf(Long
                .parseLong(user_education_id)));
            userEducation = (UserEducation) wf.toPo(request, dbUserEducation);
        }

        ResultMsg rmg = user_education_save_check(userEducation, user_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        user_education_save_init(userEducation);
        if (CommUtil.isBlank(user_education_id)) {
            addUserEducation(userEducation);
        } else {
            updateUserEducation(userEducation);
        }
        return CommUtil.setResultMsgData(rmg, true, "人事档案教育经历保存成功");
    }

    //校验信息
    private ResultMsg user_education_save_check(UserEducation userEducation, Long user_id) {
        if (userEducation == null) {
            return CommUtil.setResultMsgData(null, false, "人事档案教育经历对象为空");
        }
        if (user_id == null) {
            return CommUtil.setResultMsgData(null, false, "用户标识为空");
        }
        User user = userService.getUser(user_id);
        if (user == null || user.getId() == null) {
            return CommUtil.setResultMsgData(null, false, "用户对象为空");
        } else {
            userEducation.setUser(user);
        }
        return CommUtil.setResultMsgData(null, true, "人事档案教育经历校验成功");
    }

    //对不符合数据库设计的字段初始化
    private void user_education_save_init(UserEducation userEducation) {
        if (userEducation == null) {
            return;
        }
        //就读院校
        String edu_school_name = userEducation.getEdu_school_name();
        if (CommUtil.isNotNull(edu_school_name) && edu_school_name.length() > 50) {
            userEducation.setEdu_school_name("");
        }
        //所学专业
        String edu_major = userEducation.getEdu_major();
        if (CommUtil.isNotNull(edu_major) && edu_major.length() > 50) {
            userEducation.setEdu_major("");
        }
        //地点
        String edu_place = userEducation.getEdu_place();
        if (CommUtil.isNotNull(edu_place) && edu_place.length() > 50) {
            userEducation.setEdu_place("");
        }
        //学位
        String edu_offering = userEducation.getEdu_offering();
        if (CommUtil.isNotNull(edu_offering) && edu_offering.length() > 50) {
            userEducation.setEdu_offering("");
        }
        //学历
        Integer edu_degree = userEducation.getEdu_degree();
        if (edu_degree != null
            && CommUtil.isBlank(Constants.EDU_DEGREE_MAP.get(edu_degree.toString()))) {
            userEducation.setEdu_degree(6);
        }
    }
}
