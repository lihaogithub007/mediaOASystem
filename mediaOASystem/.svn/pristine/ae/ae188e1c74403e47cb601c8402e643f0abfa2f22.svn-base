package com.yuyu.soft.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.entity.UserBase;
import com.yuyu.soft.service.IUserBaseService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

@Service("userBaseService")
public class UserBaseServiceImpl implements IUserBaseService {

    @Resource
    private IBaseDao<UserBase> userBaseDao;
    @Resource
    private IUserService       userService;

    @Override
    public List<UserBase> queryUserBase(String hql, Map<String, Object> paramsMap, PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(userBaseDao.count("select count(t) " + hql, paramsMap));
            return userBaseDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return userBaseDao.find(hql, paramsMap);
        }

    }

    @Override
    public UserBase getUserBase(Long id) {
        return userBaseDao.get(UserBase.class, id);
    }

    @Override
    public void addUserBase(UserBase userBase) {
        this.userBaseDao.save(userBase);
    }

    @Override
    public void updateUserBase(UserBase userBase) {
        this.userBaseDao.update(userBase);
    }

    @Override
    public ResultMsg user_base_save(HttpServletRequest request, Long user_id) {
        String user_base_id = request.getParameter("user_base_id");
        WebForm wf = new WebForm();
        UserBase userBase = null;
        if (CommUtil.isBlank(user_base_id)) {
            userBase = (UserBase) wf.toPo(request, UserBase.class);
            userBase.setCreatetime(new Date());
            userBase.setDisabled(false);
        } else {
            UserBase dbUserBase = getUserBase(Long.valueOf(Long.parseLong(user_base_id)));
            userBase = (UserBase) wf.toPo(request, dbUserBase);
        }

        ResultMsg rmg = user_base_save_check(userBase, user_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        user_base_save_init(userBase);
        if (CommUtil.isBlank(user_base_id)) {
            addUserBase(userBase);
        } else {
            updateUserBase(userBase);
        }
        return CommUtil.setResultMsgData(rmg, true, "人事档案基础信息保存成功");
    }

    //校验信息
    private ResultMsg user_base_save_check(UserBase userBase, Long user_id) {
        if (userBase == null) {
            return CommUtil.setResultMsgData(null, false, "人事档案基础信息对象为空");
        }
        if (user_id == null) {
            return CommUtil.setResultMsgData(null, false, "用户标识为空");
        }
        User user = userService.getUser(user_id);
        if (user == null || user.getId() == null) {
            return CommUtil.setResultMsgData(null, false, "用户对象为空");
        } else {
            userBase.setUser(user);
        }
        return CommUtil.setResultMsgData(null, true, "人事档案基础信息校验成功");
    }

    //对不符合数据库设计的字段初始化
    private void user_base_save_init(UserBase userBase) {
        if (userBase == null) {
            return;
        }
        //血型
        Integer blood_type = userBase.getBlood_type();
        if (blood_type != null
            && CommUtil.isBlank(Constants.BLOOD_TYPE_MAP.get(blood_type.toString()))) {
            userBase.setBlood_type(null);
        }
        //星座
        Integer constellation = userBase.getConstellation();
        if (constellation != null
            && CommUtil.isBlank(Constants.CONSTELLATION_MAP.get(constellation.toString()))) {
            userBase.setConstellation(null);
        }
        //性别
        Integer sex = userBase.getSex();
        if (sex != null && CommUtil.isBlank(Constants.SEX_MAP.get(sex.toString()))) {
            userBase.setSex(null);
        }
        //年龄
        if (userBase.getUser() != null) {
            userBase.setAge(CommUtil.getAge(userBase.getUser().getBirthday()));
        }
        //民族
        String nation = userBase.getNation();
        if (CommUtil.isNotNull(nation) && nation.length() > 20) {
            userBase.setNation(null);
        }
        //籍贯
        String native_place = userBase.getNative_place();
        if (CommUtil.isNotNull(native_place) && native_place.length() > 50) {
            userBase.setNative_place(null);
        }
        //证件类型
        Integer ID_type = userBase.getID_type();
        if (ID_type != null && CommUtil.isBlank(Constants.ID_TYPE_MAP.get(ID_type.toString()))) {
            userBase.setID_type(null);
        }
        //证件号码
        String ID_number = userBase.getID_number();
        if (CommUtil.isNotNull(ID_number) && ID_number.length() > 20) {
            userBase.setID_number(null);
        }
        //工作年限
        userBase.setWork_years(CommUtil.getAge(userBase.getFirst_work_date()));

        //政治面貌
        Integer political_status = userBase.getPolitical_status();
        if (political_status != null
            && CommUtil.isBlank(Constants.POLITICAL_STATUS_MAP.get(political_status.toString()))) {
            userBase.setPolitical_status(null);
        }
        //婚姻状况
        Integer marriage_status = userBase.getMarriage_status();
        if (marriage_status != null
            && CommUtil.isBlank(Constants.MARRIAGE_STATUS_MAP.get(marriage_status.toString()))) {
            userBase.setMarriage_status(null);
        }
        //子女情况
        String condition_of_children = userBase.getCondition_of_children();
        if (CommUtil.isNotNull(condition_of_children) && condition_of_children.length() > 10) {
            userBase.setCondition_of_children(null);
        }
        //最高学历
        String highest_education = userBase.getHighest_education();
        if (CommUtil.isNotNull(highest_education) && highest_education.length() > 20) {
            userBase.setHighest_education(null);
        }
        //最高学位
        String highest_offering = userBase.getHighest_offering();
        if (CommUtil.isNotNull(highest_offering) && highest_offering.length() > 20) {
            userBase.setHighest_offering(null);
        }
        //外语种类及水平
        String foreign_language_level = userBase.getForeign_language_level();
        if (CommUtil.isNotNull(foreign_language_level) && foreign_language_level.length() > 50) {
            userBase.setForeign_language_level(null);
        }
        //计算机水平
        String computer_skill = userBase.getComputer_skill();
        if (CommUtil.isNotNull(computer_skill) && computer_skill.length() > 50) {
            userBase.setComputer_skill(null);
        }
        //职称
        String job_titles = userBase.getJob_titles();
        if (CommUtil.isNotNull(job_titles) && job_titles.length() > 50) {
            userBase.setJob_titles(null);
        }
        //职称等级
        String job_titles_level = userBase.getJob_titles_level();
        if (CommUtil.isNotNull(job_titles_level) && job_titles_level.length() > 50) {
            userBase.setJob_titles_level(null);
        }
        //职称评定单位
        String job_titles_evaluation_unit = userBase.getJob_titles_evaluation_unit();
        if (CommUtil.isNotNull(job_titles_evaluation_unit)
            && job_titles_evaluation_unit.length() > 50) {
            userBase.setJob_titles_evaluation_unit(null);
        }
        //职称证书号
        String job_titles_cert_id = userBase.getJob_titles_cert_id();
        if (CommUtil.isNotNull(job_titles_cert_id) && job_titles_cert_id.length() > 50) {
            userBase.setJob_titles_cert_id(null);
        }
        //是否有记者证
        Integer is_have_press_card = userBase.getIs_have_press_card();
        if (is_have_press_card != null
            && CommUtil.isBlank(Constants.HAVE_OR_NOT_MAP.get(is_have_press_card.toString()))) {
            userBase.setIs_have_press_card(null);
        }
        //职称证书号
        String press_card_no = userBase.getPress_card_no();
        if (CommUtil.isNotNull(press_card_no) && press_card_no.length() > 50) {
            userBase.setPress_card_no(null);
        }
        //家庭地址
        String home_address = userBase.getHome_address();
        if (CommUtil.isNotNull(home_address) && home_address.length() > 50) {
            userBase.setHome_address(null);
        }
        //家庭邮编
        String home_postcode = userBase.getHome_postcode();
        if (CommUtil.isNotNull(home_postcode) && home_postcode.length() > 10) {
            userBase.setHome_postcode(null);
        }
        //现地址
        String present_address = userBase.getPresent_address();
        if (CommUtil.isNotNull(present_address) && present_address.length() > 50) {
            userBase.setPresent_address(null);
        }
        //现邮编
        String present_postcode = userBase.getPresent_postcode();
        if (CommUtil.isNotNull(present_postcode) && present_postcode.length() > 10) {
            userBase.setPresent_postcode(null);
        }
        //人事档案存放地
        String personnel_file_address = userBase.getPersonnel_file_address();
        if (CommUtil.isNotNull(personnel_file_address) && personnel_file_address.length() > 50) {
            userBase.setPersonnel_file_address(null);
        }
        //人事档案存放地邮编
        String personnel_file_postcode = userBase.getPersonnel_file_postcode();
        if (CommUtil.isNotNull(personnel_file_postcode) && personnel_file_postcode.length() > 10) {
            userBase.setPersonnel_file_postcode(null);
        }
        //户口所在地
        String domicile_place = userBase.getDomicile_place();
        if (CommUtil.isNotNull(domicile_place) && domicile_place.length() > 50) {
            userBase.setDomicile_place(null);
        }
        //户口性质
        Integer domicile_type = userBase.getDomicile_type();
        if (domicile_type != null
            && CommUtil.isBlank(Constants.DOMICILE_TYPE_MAP.get(domicile_type.toString()))) {
            userBase.setDomicile_type(null);
        }
        //出生地
        String birth_place = userBase.getBirth_place();
        if (CommUtil.isNotNull(birth_place) && birth_place.length() > 50) {
            userBase.setBirth_place(null);
        }
        //紧急联系人姓名
        String emergency_contact_name = userBase.getEmergency_contact_name();
        if (CommUtil.isNotNull(emergency_contact_name) && emergency_contact_name.length() > 50) {
            userBase.setEmergency_contact_name(null);
        }
        //紧急联系人手机号
        String emergency_contact_mobile = userBase.getEmergency_contact_mobile();
        if (CommUtil.isNotNull(emergency_contact_mobile) && emergency_contact_mobile.length() > 11) {
            userBase.setEmergency_contact_mobile(null);
        }
        //爱好/特长
        String hobby_and_speciality = userBase.getHobby_and_speciality();
        if (CommUtil.isNotNull(hobby_and_speciality) && hobby_and_speciality.length() > 400) {
            userBase.setHobby_and_speciality(hobby_and_speciality.substring(0, 400));
        }
    }
}
