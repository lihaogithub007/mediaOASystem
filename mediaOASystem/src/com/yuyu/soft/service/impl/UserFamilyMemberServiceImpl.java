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
import com.yuyu.soft.entity.UserFamilyMember;
import com.yuyu.soft.service.IUserFamilyMemberService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

@Service("userFamilyMemberService")
public class UserFamilyMemberServiceImpl implements IUserFamilyMemberService {

    @Resource
    private IBaseDao<UserFamilyMember> userFamilyMemberDao;
    @Resource
    private IUserService               userService;

    @Override
    public List<UserFamilyMember> queryUserFamilyMember(Long user_id) {
        if (user_id == null) {
            return new ArrayList<UserFamilyMember>();
        }
        StringBuilder s = new StringBuilder();
        s.append("from UserFamilyMember t where t.disabled = false");
        s.append(" and t.user.id = ").append(user_id);
        s.append(" order by t.createtime asc");
        List<UserFamilyMember> userFamilyMembers = userFamilyMemberDao.find(s.toString());
        if ((userFamilyMembers == null) || (userFamilyMembers.size() == 0)) {
            return new ArrayList<UserFamilyMember>();
        }
        return userFamilyMembers;
    }

    @Override
    public List<UserFamilyMember> queryUserFamilyMember(String hql, Map<String, Object> paramsMap,
                                                        PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(userFamilyMemberDao.count("select count(t) " + hql, paramsMap));
            return userFamilyMemberDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return userFamilyMemberDao.find(hql, paramsMap);
        }

    }

    @Override
    public UserFamilyMember getUserFamilyMember(Long id) {
        return userFamilyMemberDao.get(UserFamilyMember.class, id);
    }

    @Override
    public void addUserFamilyMember(UserFamilyMember userFamilyMember) {
        this.userFamilyMemberDao.save(userFamilyMember);
    }

    @Override
    public void updateUserFamilyMember(UserFamilyMember userFamilyMember) {
        this.userFamilyMemberDao.update(userFamilyMember);
    }

    @Override
    public void delUserFamilyMember(UserFamilyMember userFamilyMember) {
        userFamilyMember = getUserFamilyMember(userFamilyMember.getId());
        userFamilyMember.setDisabled(true);
        updateUserFamilyMember(userFamilyMember);
        //this.userFamilyMemberDao.delete(userFamilyMember);
    }

    @Override
    public ResultMsg user_family_member_save(HttpServletRequest request, Long user_id) {
        String user_family_member_id = request.getParameter("user_family_member_id");
        WebForm wf = new WebForm();
        UserFamilyMember userFamilyMember = null;
        if (CommUtil.isBlank(user_family_member_id)) {
            userFamilyMember = (UserFamilyMember) wf.toPo(request, UserFamilyMember.class);
            userFamilyMember.setCreatetime(new Date());
            userFamilyMember.setDisabled(false);
        } else {
            UserFamilyMember dbUserFamilyMember = getUserFamilyMember(Long.valueOf(Long
                .parseLong(user_family_member_id)));
            userFamilyMember = (UserFamilyMember) wf.toPo(request, dbUserFamilyMember);
        }

        ResultMsg rmg = user_family_member_save_check(userFamilyMember, user_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        user_family_member_save_init(userFamilyMember);
        if (CommUtil.isBlank(user_family_member_id)) {
            addUserFamilyMember(userFamilyMember);
        } else {
            updateUserFamilyMember(userFamilyMember);
        }
        return CommUtil.setResultMsgData(rmg, true, "人事档案家庭成员保存成功");
    }

    //校验信息
    private ResultMsg user_family_member_save_check(UserFamilyMember userFamilyMember, Long user_id) {
        if (userFamilyMember == null) {
            return CommUtil.setResultMsgData(null, false, "人事档案家庭成员对象为空");
        }
        if (user_id == null) {
            return CommUtil.setResultMsgData(null, false, "用户标识为空");
        }
        User user = userService.getUser(user_id);
        if (user == null || user.getId() == null) {
            return CommUtil.setResultMsgData(null, false, "用户对象为空");
        } else {
            userFamilyMember.setUser(user);
        }
        return CommUtil.setResultMsgData(null, true, "人事档案家庭成员校验成功");
    }

    //对不符合数据库设计的字段初始化
    private void user_family_member_save_init(UserFamilyMember userFamilyMember) {
        if (userFamilyMember == null) {
            return;
        }
        //姓名
        String family_member_name = userFamilyMember.getFamily_member_name();
        if (CommUtil.isNotNull(family_member_name) && family_member_name.length() > 50) {
            userFamilyMember.setFamily_member_name("");
        }
        //与本人关系
        Integer family_relationship = userFamilyMember.getFamily_relationship();
        if (family_relationship != null
            && CommUtil.isBlank(Constants.EDU_DEGREE_MAP.get(family_relationship.toString()))) {
            userFamilyMember.setFamily_relationship(0);
        }

        //工作单位
        String family_member_unit = userFamilyMember.getFamily_member_unit();
        if (CommUtil.isNotNull(family_member_unit) && family_member_unit.length() > 50) {
            userFamilyMember.setFamily_member_unit("");
        }
        //岗位
        String family_member_duty = userFamilyMember.getFamily_member_duty();
        if (CommUtil.isNotNull(family_member_duty) && family_member_duty.length() > 50) {
            userFamilyMember.setFamily_member_duty("");
        }
        //联系电话
        String family_member_mobile = userFamilyMember.getFamily_member_mobile();
        if (CommUtil.isNotNull(family_member_mobile) && family_member_mobile.length() > 11) {
            userFamilyMember.setFamily_member_mobile("");
        }
    }
}
