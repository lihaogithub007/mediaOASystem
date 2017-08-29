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
import com.yuyu.soft.entity.UserWork;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.service.IUserWorkService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

@Service("userWorkService")
public class UserWorkServiceImpl implements IUserWorkService {

    @Resource
    private IBaseDao<UserWork> userWorkDao;
    @Resource
    private IUserService       userService;

    @Override
    public List<UserWork> queryUserWork(Long user_id) {
        if (user_id == null) {
            return new ArrayList<UserWork>();
        }
        StringBuilder s = new StringBuilder();
        s.append("from UserWork t where t.disabled = false");
        s.append(" and t.user.id = ").append(user_id);
        s.append(" order by t.createtime asc");
        List<UserWork> userWorks = userWorkDao.find(s.toString());
        if ((userWorks == null) || (userWorks.size() == 0)) {
            return new ArrayList<UserWork>();
        }
        return userWorks;
    }

    @Override
    public List<UserWork> queryUserWork(String hql, Map<String, Object> paramsMap, PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(userWorkDao.count("select count(t) " + hql, paramsMap));
            return userWorkDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return userWorkDao.find(hql, paramsMap);
        }

    }

    @Override
    public UserWork getUserWork(Long id) {
        return userWorkDao.get(UserWork.class, id);
    }

    @Override
    public void addUserWork(UserWork userWork) {
        this.userWorkDao.save(userWork);
    }

    @Override
    public void updateUserWork(UserWork userWork) {
        this.userWorkDao.update(userWork);
    }

    @Override
    public void delUserWork(UserWork userWork) {
        userWork = getUserWork(userWork.getId());
        userWork.setDisabled(true);
        updateUserWork(userWork);
        //this.userWorkDao.delete(userWork);
    }

    @Override
    public ResultMsg user_work_save(HttpServletRequest request, Long user_id) {
        String user_work_id = request.getParameter("user_work_id");
        WebForm wf = new WebForm();
        UserWork userWork = null;
        if (CommUtil.isBlank(user_work_id)) {
            userWork = (UserWork) wf.toPo(request, UserWork.class);
            userWork.setCreatetime(new Date());
            userWork.setDisabled(false);
        } else {
            UserWork dbUserWork = getUserWork(Long.valueOf(Long.parseLong(user_work_id)));
            userWork = (UserWork) wf.toPo(request, dbUserWork);
        }

        ResultMsg rmg = user_work_save_check(userWork, user_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        user_work_save_init(userWork);
        if (CommUtil.isBlank(user_work_id)) {
            addUserWork(userWork);
        } else {
            updateUserWork(userWork);
        }
        return CommUtil.setResultMsgData(rmg, true, "人事档案工作经历保存成功");
    }

    //校验信息
    private ResultMsg user_work_save_check(UserWork userWork, Long user_id) {
        if (userWork == null) {
            return CommUtil.setResultMsgData(null, false, "人事档案工作经历对象为空");
        }
        if (user_id == null) {
            return CommUtil.setResultMsgData(null, false, "用户标识为空");
        }
        User user = userService.getUser(user_id);
        if (user == null || user.getId() == null) {
            return CommUtil.setResultMsgData(null, false, "用户对象为空");
        } else {
            userWork.setUser(user);
        }
        return CommUtil.setResultMsgData(null, true, "人事档案工作经历校验成功");
    }

    //对不符合数据库设计的字段初始化
    private void user_work_save_init(UserWork userWork) {
        if (userWork == null) {
            return;
        }
        //工作单位
        String work_comp_name = userWork.getWork_comp_name();
        if (CommUtil.isNotNull(work_comp_name) && work_comp_name.length() > 50) {
            userWork.setWork_comp_name("");
        }
        //岗位
        String work_duty_name = userWork.getWork_duty_name();
        if (CommUtil.isNotNull(work_duty_name) && work_duty_name.length() > 50) {
            userWork.setWork_duty_name("");
        }
        //工作地点
        String work_place = userWork.getWork_place();
        if (CommUtil.isNotNull(work_place) && work_place.length() > 50) {
            userWork.setWork_place("");
        }
        //工作性质
        Integer work_type = userWork.getWork_type();
        if (work_type != null
            && CommUtil.isBlank(Constants.WORK_TYPE_MAP.get(work_type.toString()))) {
            userWork.setWork_type(1);
        }
    }
}
