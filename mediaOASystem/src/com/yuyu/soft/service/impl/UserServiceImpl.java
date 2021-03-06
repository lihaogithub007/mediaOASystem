package com.yuyu.soft.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuyu.soft.base.controller.BaseController;
import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.Department;
import com.yuyu.soft.entity.Duty;
import com.yuyu.soft.entity.Section;
import com.yuyu.soft.entity.SubSection;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IDepartmentService;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.ISectionService;
import com.yuyu.soft.service.ISubSectionService;
import com.yuyu.soft.service.IUserBaseService;
import com.yuyu.soft.service.IUserDimissionLogsService;
import com.yuyu.soft.service.IUserLogsService;
import com.yuyu.soft.service.IUserResService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Md5;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

/**
 * 用户管理
 *                       
 * @Filename: UserServiceImpl.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource
    private IBaseDao<User>            userDao;
    @Resource
    private IDepartmentService        departmentService;
    @Resource
    private IDutyService              dutyService;
    @Resource
    private IUserResService           userResService;
    @Resource
    private IUserBaseService          userBaseService;
    @Resource
    private IUserDimissionLogsService userDimissionLogsService;
    @Resource
    private IUserLogsService          userLogsService;
    @Resource
    private HttpServletRequest        request;
    @Resource
    private ISectionService			  sectionService;
    @Resource
    private ISubSectionService		  subSectionService;

    @Override
    public int getCountForExportExcel(Long department_id, Long duty_id, String true_name,
                                      String card_number, Integer user_status, Integer sex,
                                      String age, Integer domicile_type, Integer user_relationship,
                                      String highest_education, String hobby_and_speciality,
                                      Integer work_years, String job_titles_level,
                                      Integer blood_type, Integer constellation, String nation,
                                      String foreign_language_level, Integer ID_type,
                                      Integer political_status, String highest_offering,
                                      String condition_of_children, Integer marriage_status,
                                      String join_party_date_beginTime,
                                      String join_party_date_endTime, Integer is_have_press_card,
                                      String mobile,Long section_id,Long sub_section_id) {
        String sql = getCountSQL()
                     + getCommonSQL(department_id, duty_id, true_name, card_number, user_status,
                         sex, age, domicile_type, user_relationship, highest_education,
                         hobby_and_speciality, work_years, job_titles_level, blood_type,
                         constellation, nation, foreign_language_level, ID_type, political_status,
                         highest_offering, condition_of_children, marriage_status,
                         join_party_date_beginTime, join_party_date_endTime, is_have_press_card,
                         mobile,section_id,sub_section_id);
        return userDao.countBySql(sql, null);
    }

    @Override
    public List<Object[]> getUsersForExportExcel(Long department_id, Long duty_id,
                                                 String true_name, String card_number,
                                                 Integer user_status, Integer sex, String age,
                                                 Integer domicile_type, Integer user_relationship,
                                                 String highest_education,
                                                 String hobby_and_speciality, int beginIndex,
                                                 int pageSize, Integer work_years,
                                                 String job_titles_level, Integer blood_type,
                                                 Integer constellation, String nation,
                                                 String foreign_language_level, Integer ID_type,
                                                 Integer political_status, String highest_offering,
                                                 String condition_of_children,
                                                 Integer marriage_status,
                                                 String join_party_date_beginTime,
                                                 String join_party_date_endTime,
                                                 Integer is_have_press_card, String mobile,
                                                 Long section_id,Long sub_section_id) {

        String sql = getQueryListSQL()
                     + getCommonSQL(department_id, duty_id, true_name, card_number, user_status,
                         sex, age, domicile_type, user_relationship, highest_education,
                         hobby_and_speciality, work_years, job_titles_level, blood_type,
                         constellation, nation, foreign_language_level, ID_type, political_status,
                         highest_offering, condition_of_children, marriage_status,
                         join_party_date_beginTime, join_party_date_endTime, is_have_press_card,
                         mobile,section_id,sub_section_id);

        List<Object[]> list = (ArrayList<Object[]>) userDao.findBySql(sql, null, beginIndex,
            pageSize);

        return list;
    }

    private String getCountSQL() {
        return "select count(u.id)";
    }

    private String getQueryListSQL() {
        StringBuffer s = new StringBuffer();
        s.append(" select ");
        s.append(" u.true_name");
        s.append(" ,d.department_name");
        s.append(" ,duty.duty_name");
        s.append(" ,s.section_name");
        s.append(" ,ss.sub_section_name");
        s.append(" ,case u.user_relationship ");
        s.append("   when 0 then '台聘'");
        s.append("   when 1 then '企聘'");
        s.append("   when 2 then '视通聘'");
        s.append("   when 3 then '外籍'");
        s.append("   when 4 then '索贝聘'");
        s.append("   when 5 then '前卫聘' ");
        s.append("   else '' ");
        s.append("  end as user_relationship");
        s.append(" ,ub.native_place");
        s.append(" ,case ub.sex when 1 then '男' when 2 then '女' else '' end as sex");
        s.append(" ,ub.age");
        s.append(" ,ub.highest_education");
        s.append(" ,u.card_number ");
        return s.toString();
    }

    private String getCommonSQL(Long department_id, Long duty_id, String true_name,
                                String card_number, Integer user_status, Integer sex, String age,
                                Integer domicile_type, Integer user_relationship,
                                String highest_education, String hobby_and_speciality,
                                Integer work_years, String job_titles_level, Integer blood_type,
                                Integer constellation, String nation,
                                String foreign_language_level, Integer iD_type,
                                Integer political_status, String highest_offering,
                                String condition_of_children, Integer marriage_status,
                                String join_party_date_beginTime, String join_party_date_endTime,
                                Integer is_have_press_card, String mobile,
                                Long section_id,Long sub_section_id) {
        StringBuffer s = new StringBuffer();
        s.append(" from tb_user u");
        s.append(" left join tb_department d on u.department_id = d.id");
        s.append(" left join tb_duty duty on u.duty_id = duty.id");
        s.append(" left join tb_section s on u.department_id = s.id");
        s.append(" left join tb_sub_section ss on u.sub_section_id = ss.id");
        s.append(" left join tb_user_base ub on ub.user_id = u.id and ub.disabled = 0");
        s.append(" where u.disabled = 0");
        if (CommUtil.isNotNull(department_id)) {
            s.append(" and d.id = ").append(department_id);
        }
        if (CommUtil.isNotNull(duty_id)) {
            s.append(" and duty.id = ").append(duty_id);
        }
        if (CommUtil.isNotNull(section_id)) {
        	s.append(" and s.id = ").append(section_id);
        }
        if (CommUtil.isNotNull(sub_section_id)) {
        	s.append(" and ss.id = ").append(sub_section_id);
        }
        if (CommUtil.isNotNull(true_name)) {
            s.append(" and u.true_name like '%").append(true_name.trim()).append("%'");
        }
        if (CommUtil.isNotNull(card_number)) {
            s.append(" and u.card_number like '%").append(card_number.trim()).append("%'");
        }
        if (CommUtil.isNotNull(user_status)) {
            s.append(" and u.user_status = ").append(user_status);
        }else {
			s.append(" and u.user_status in (1,2,3)");
		}
        if (CommUtil.isNotNull(sex)) {
            s.append(" and ub.sex = ").append(sex);
        }
        if (CommUtil.isNotNull(age) && CommUtil.null2Integer(age) != null) {
            s.append(" and ub.age = ").append(CommUtil.null2Integer(age));
        }
        if (CommUtil.isNotNull(domicile_type)) {
            s.append(" and ub.domicile_type = ").append(domicile_type);
        }
        if (CommUtil.isNotNull(user_relationship)) {
            s.append(" and u.user_relationship = ").append(user_relationship);
        }
        if (CommUtil.isNotNull(highest_education)) {
            s.append(" and ub.highest_education like '%").append(highest_education.trim())
                .append("%'");
        }
        if (CommUtil.isNotNull(hobby_and_speciality)) {
            s.append(" and ub.hobby_and_speciality like '%").append(hobby_and_speciality.trim())
                .append("%'");
        }
        //工作年限
        if (CommUtil.isNotNull(work_years)) {
            s.append(" and ub.work_years = ").append(work_years);
        }
        //职称等级
        if (CommUtil.isNotNull(job_titles_level)) {
            s.append(" and ub.job_titles_level like '%").append(job_titles_level.trim())
                .append("%'");
        }
        //血型
        if (CommUtil.isNotNull(blood_type)) {
            s.append(" and ub.blood_type = ").append(blood_type);
        }
        //星座
        if (CommUtil.isNotNull(constellation)) {
            s.append(" and ub.constellation = ").append(constellation);
        }
        //民族
        if (CommUtil.isNotNull(nation)) {
            s.append(" and ub.nation like '%").append(nation.trim()).append("%'");
        }
        //外语种类及水平
        if (CommUtil.isNotNull(foreign_language_level)) {
            s.append(" and ub.foreign_language_level like '%")
                .append(foreign_language_level.trim()).append("%'");
        }
        //证件类型
        if (CommUtil.isNotNull(iD_type)) {
            s.append(" and ub.iD_type = ").append(iD_type);
        }
        //政治面貌
        if (CommUtil.isNotNull(political_status)) {
            s.append(" and ub.political_status = ").append(political_status);
        }
        //最高学习
        if (CommUtil.isNotNull(highest_offering)) {
            s.append(" and ub.highest_offering like '%").append(highest_offering.trim())
                .append("%'");
        }
        //子女情况
        if (CommUtil.isNotNull(condition_of_children)) {
            s.append(" and ub.condition_of_children like '%").append(condition_of_children.trim())
                .append("%'");
        }
        //婚姻情况
        if (CommUtil.isNotNull(marriage_status)) {
            s.append(" and ub.marriage_status = ").append(marriage_status);
        }
        //入党时间
        if (CommUtil.isNotNull(join_party_date_beginTime)) {
            s.append(" and date_format(ub.join_party_date,'%Y-%m-%d') >= str_to_date(' ")
                .append(join_party_date_beginTime).append("','%Y-%m-%d')");
        }
        if (CommUtil.isNotNull(join_party_date_endTime)) {
            s.append(" and date_format(ub.join_party_date,'%Y-%m-%d') <= str_to_date(' ")
                .append(join_party_date_endTime).append("','%Y-%m-%d')");
        }
        //是否有记者证
        if (CommUtil.isNotNull(is_have_press_card)) {
            s.append(" and ub.is_have_press_card = ").append(is_have_press_card);
        }
        //电话号
        if (CommUtil.isNotNull(mobile)) {
            s.append(" and u.mobile like '%").append(mobile.trim()).append("%'");
        }

        s.append(" order by d.id asc, u.id asc");
        return s.toString();
    }

    @Override
    public List<User> queryAllUser() {
        StringBuilder s = new StringBuilder();
        s.append("from User t where t.disabled = false");
        s.append(" and t.user_status <> 4");
        s.append(" order by t.createtime desc");
        return userDao.find(s.toString());
    }

    @Override
    public List<User> queryAllUserByUserStatus(String user_status) {
        return queryAllUserByUserStatusAndCreatetime(user_status, null);
    }

    @Override
    public List<User> queryAllUserByUserStatusAndCreatetime(String user_status, String yesterday) {
        StringBuilder s = new StringBuilder();
        s.append("from User t where t.disabled = false");
        if (CommUtil.isNotNull(user_status)) {
            s.append(" and t.user_status in (").append(user_status.trim()).append(")");
        }
        if (CommUtil.isNotNull(yesterday)) {
            s.append(" and date_format(t.createtime,'%Y-%m-%d') = date_format('")
                .append(yesterday.trim()).append("','%Y-%m-%d')");
        }
        s.append(" order by t.createtime desc");
        return userDao.find(s.toString());
    }

    @Override
    public int getUserAddressBookCountForExportExcel(Long department_id, Long duty_id,
                                                     String true_name) {
        String sql = getUserAddressBookCountSQL()
                     + getUserAddressBookCommonSQL(department_id, duty_id, true_name);
        return userDao.countBySql(sql, null);
    }

    @Override
    public List<Object[]> getUserAddressBookForExportExcel(Long department_id, Long duty_id,
                                                           String true_name, int beginIndex,
                                                           int pageSize) {

        String sql = getUserAddressBookQueryListSQL()
                     + getUserAddressBookCommonSQL(department_id, duty_id, true_name);

        List<Object[]> list = (ArrayList<Object[]>) userDao.findBySql(sql, null, beginIndex,
            pageSize);

        return list;
    }

    private String getUserAddressBookCountSQL() {
        return "select count(u.id)";
    }

    private String getUserAddressBookQueryListSQL() {
        StringBuffer s = new StringBuffer();
        s.append("select");
        s.append(" d.department_name");//部门/科组
        s.append(" ,duty.duty_name");//岗位名称
        s.append(" ,u.true_name");//姓名
        s.append(" ,u.card_number");//工作证号
        s.append(" ,u.user_relationship");//员工关系
        s.append(" ,u.tel_number");//座机
        s.append(" ,u.mobile");//手机号
        s.append(" ,u.remark ");//备注
        return s.toString();
    }

    private String getUserAddressBookCommonSQL(Long department_id, Long duty_id, String true_name) {
        StringBuffer s = new StringBuffer();
        s.append(" from tb_user u");
        s.append(" left join tb_department d on u.department_id = d.id");
        s.append(" left join tb_duty duty on u.duty_id = duty.id");
        s.append(" where u.disabled = 0 ");
        if (CommUtil.isNotNull(department_id)) {
            s.append(" and d.id = ").append(department_id);
        }
        if (CommUtil.isNotNull(duty_id)) {
            s.append(" and duty.id = ").append(duty_id);
        }
        if (CommUtil.isNotNull(true_name)) {
            s.append(" and u.true_name like '%").append(true_name.trim()).append("%'");
        }
        s.append(" order by u.createtime desc");
        return s.toString();
    }

    public List<User> queryUser(String hql, Map<String, Object> paramsMap, PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(userDao.count("select count(t) " + hql, paramsMap));
            return userDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return userDao.find(hql, paramsMap);
        }

    }

    @Override
    public List<User> queryTodayBirthdayUsers() {
        StringBuilder s = new StringBuilder();
        s.append("from User t where t.disabled = false");
        s.append(" and date_format(t.birthday,'%m-%d') = date_format(now(),'%m-%d')");
        s.append(" order by t.createtime desc");
        return userDao.find(s.toString());
    }

    @Override
    public User getUser(Long id) {
        return userDao.get(User.class, id);
    }

    @Override
    public void addUser(User user) {
        this.userDao.save(user);
    }

    @Override
    public void updateUser(User user) {
        this.userDao.update(user);
    }

    @Override
    public void delUser(User user) {
        user = getUser(user.getId());
        user.setDisabled(true);
        updateUser(user);
    }

    @Override
    public ResultMsg add_save(User user, Long department_id, Long duty_id, String res_ids,
    							Long section_id, Long sub_section_id) {
        ResultMsg rmg = save_check(user, department_id, duty_id, res_ids,section_id,sub_section_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        user.setCreatetime(new Date());
        user.setDisabled(false);
        user.setArchive_status(false);
        user.setIs_synchronous(false);
        addUser(user);

        user = getUser(user.getId());
        try {
            //保存用户权限
            userResService.user_res_save(res_ids, user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user.getUser_status() != null) {
            if (3 == user.getUser_status()) {
                this.userLogsService.add_log(user.getId(), BaseController.getCurrentUser(request)
                    .getId(), "办理入职",null,null);
            } else if (4 == user.getUser_status()) {
                userDimissionLogsService.add_dimission_log(user.getId());
                this.userLogsService.add_log(user.getId(), BaseController.getCurrentUser(request)
                    .getId(), "办理离职",null,null);
            }
        }
        return CommUtil.setResultMsgData(rmg, true, "用户添加成功");
    }

    @Override
    public ResultMsg edit_save(User user, Long department_id, Long duty_id, String res_ids,
                               Integer db_user_status,String dimission_reason,
                               String dimission_time,Long section_id,Long sub_section_id) {
        ResultMsg rmg = save_check(user, department_id, duty_id, res_ids,section_id,sub_section_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        updateUser(user);
        user = getUser(user.getId());
        try {
            //保存用户权限
            userResService.user_res_save(res_ids, user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user.getUser_status() != null) {
            if (3 == user.getUser_status() && db_user_status != null && db_user_status != 3) {
                this.userLogsService.add_log(user.getId(), BaseController.getCurrentUser(request)
                    .getId(), "办理入职",null,null);
            } else if (4 == user.getUser_status() && db_user_status != null && db_user_status != 4) {
                userDimissionLogsService.add_dimission_log(user.getId());
                this.userLogsService.add_log(user.getId(), BaseController.getCurrentUser(request)
                    .getId(), "办理离职",dimission_reason,dimission_time);
            }
        }
        return CommUtil.setResultMsgData(rmg, true, "用户编辑保存成功");
    }

    private ResultMsg save_check(User user, Long department_id, Long duty_id, String res_ids,
    							Long section_id, Long sub_section_id) {
        if (user == null) {
            return CommUtil.setResultMsgData(null, false, "用户对象为空");
        }
        if (department_id == null) {
            return CommUtil.setResultMsgData(null, false, "部门/科组标识为空");
        }
        Department department = departmentService.getDepartment(department_id);
        if (department == null) {
            return CommUtil.setResultMsgData(null, false, "找不到部门/科组记录");
        } else {
            user.setDepartment(department);
        }
        Duty duty = null;
        if (duty_id != null) {
            duty = dutyService.getDuty(duty_id);
            if (duty != null && duty.getDepartment().getId() != department_id) {
                return CommUtil.setResultMsgData(null, false, "所选岗位与所选部门/科室没有所属关系");
            }
        }
        user.setDuty(duty);
        
        Section section = null;
        if (null != section_id) {
			section = sectionService.getSection(section_id);
		}
        user.setSection(section);
        SubSection subSection = null;
        if (null != sub_section_id) {
			subSection = subSectionService.getSubSection(sub_section_id);
		}
        user.setSub_section(subSection);
        return CommUtil.setResultMsgData(null, true, "校验成功");
    }

    @Override
    public boolean verify_user_mobile(Long user_id, String mobile) {

        boolean flag = true;
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("mobile", mobile);
        StringBuilder s = new StringBuilder();
        s.append("from User t where t.disabled = false");
        s.append(" and t.mobile =:mobile");
        if (CommUtil.isNotNull(user_id)) {
            s.append(" and t.id !=:user_id");
            paramsMap.put("user_id", user_id);
        }
        List<User> users = userDao.find(s.toString(), paramsMap);
        if ((users != null) && (users.size() > 0)) {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean verify_user_login(String mobile, String password) {

        if (query_user_login(mobile, password) == null) {
            return false;
        }
        return true;
    }

    @Override
    public User query_user_login(String mobile, String password) {
        if (CommUtil.isBlank(mobile) || CommUtil.isBlank(password) || mobile.trim().length() != 11) {
            return null;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        password = Md5.getMd5String(password.trim()).toLowerCase();
        params.put("password", password);

        StringBuilder s = new StringBuilder();
        s.append("from User t where t.disabled = false");
        s.append(" and t.mobile =:mobile");
        s.append(" and t.password = :password");

        List<User> users = userDao.find(s.toString(), params);
        if ((users != null) && (users.size() != 1)) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public ResultMsg verify_user_ids(String user_ids, String errMsg, boolean allow_blank) {
        if (!allow_blank && CommUtil.isBlank(user_ids)) {
            return CommUtil.setResultMsgData(null, false, errMsg);
        }
        if (allow_blank && CommUtil.isNotNull(user_ids)) {
            try {
                String[] user_id_arr = user_ids.split(",");
                if (user_id_arr != null && user_id_arr.length > 0) {
                    for (String user_id : user_id_arr) {
                        User user = getUser(Long.parseLong(user_id.trim()));
                        if (user == null) {
                            return CommUtil.setResultMsgData(null, false, errMsg);
                        }
                    }
                }
            } catch (Exception e) {
                return CommUtil.setResultMsgData(null, false, errMsg);
            }
        }
        return CommUtil.setResultMsgData(null, true, "校验成功");
    }

    @Override
    public ResultMsg verify_user_ids(String user_ids, Long department_id, String errMsg,
                                     boolean allow_blank) {
        if (!allow_blank && CommUtil.isBlank(user_ids)) {
            return CommUtil.setResultMsgData(null, false, errMsg);
        }
        if (department_id == null) {
            return CommUtil.setResultMsgData(null, false, "部门科组标识为空");
        }
        Department department = departmentService.getDepartment(department_id);
        if (department == null || department.getId() == null || department.getDisabled()) {
            return CommUtil.setResultMsgData(null, false, "找不到部门科组记录");
        }
        if (allow_blank && CommUtil.isNotNull(user_ids)) {
            try {
                String[] user_id_arr = user_ids.split(",");
                if (user_id_arr != null && user_id_arr.length > 0) {
                    for (String user_id : user_id_arr) {
                        User user = getUser(Long.parseLong(user_id.trim()));
                        if (user == null) {
                            return CommUtil.setResultMsgData(null, false, errMsg);
                        } else if (user.getDepartment() != null
                                   && user.getDepartment().getId() != null
                                   && user.getDepartment().getId() != department.getId()) {
                            return CommUtil.setResultMsgData(null, false, "存在不属于所选部门科组用户");
                        }
                    }
                }
            } catch (Exception e) {
                return CommUtil.setResultMsgData(null, false, errMsg);
            }
        }
        return CommUtil.setResultMsgData(null, true, "校验成功");
    }

    @Override
    public String getUserNames(String user_ids) {

        if (CommUtil.isBlank(user_ids)) {
            return "";
        }
        String user_names = "";
        String[] user_id_arr = user_ids.split(",");
        if (user_id_arr != null && user_id_arr.length > 0) {
            for (String user_id : user_id_arr) {
                try {
                    User user = getUser(Long.parseLong(user_id.trim()));
                    if (user == null || CommUtil.isBlank(user.getTrue_name())) {
                        continue;
                    } else {
                        user_names += user.getTrue_name() + ",";
                    }
                } catch (Exception e) {
                }
            }
        }
        if (user_names.endsWith(",")) {
            user_names = user_names.substring(0, user_names.length() - 1);
        }
        return user_names;
    }

    @Override
    public boolean verify_user_list_contains_normal(List<User> list) {

        if (list == null || list.size() == 0) {
            return false;
        }
        for (User u : list) {
            if (!u.getDisabled()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<User> queryUserByMobile(String mobile) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("mobile", mobile);
        StringBuilder s = new StringBuilder();
        s.append("from User t where t.disabled = false");
        s.append(" and t.mobile =:mobile");
        List<User> users = userDao.find(s.toString(), paramsMap);
        if ((users == null) || (users.size() == 0)) {
            return new ArrayList<User>();
        }
        return users;
    }

    @Override
    public ResultMsg user_file_save(HttpServletRequest request) {
        WebForm wf = new WebForm();
        Long user_id = null2Long(request.getParameter("user_id"));
        if (user_id == null) {
            return CommUtil.setResultMsgData(null, false, "用户标识为空");
        }
        User dbUser = getUser(user_id);
        User user = (User) wf.toPo(request, dbUser);
        Long department_id = CommUtil.null2Long(request.getParameter("department_id"));
        Long duty_id = CommUtil.null2Long(request.getParameter("duty_id"));
        //
        Long section_id = CommUtil.null2Long(request.getParameter("section_id"));
        Long sub_section_id = CommUtil.null2Long(request.getParameter("sub_section_id"));
        ResultMsg rmg = save_check(user, department_id, duty_id, "",section_id,sub_section_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        updateUser(user);
        rmg = userBaseService.user_base_save(request, user.getId());
        if (!rmg.getResult()) {
            throw new RuntimeException(rmg.getMsg());
        }
        return CommUtil.setResultMsgData(rmg, true, "人事档案基础信息保存成功");
    }

    private Long null2Long(Object s) {
        Long v = null;
        if (s != null) {
            try {
                v = Long.valueOf(Long.parseLong(s.toString()));
            } catch (Exception localException) {
            }
        }
        return v;
    }

    @Override
    public List<User> queryUserByDepartment(Long department_id) {
        StringBuilder s = new StringBuilder();
        s.append("from User t where t.disabled = false");
        if (CommUtil.isNotNull(department_id)) {
            s.append(" and t.department.id = ").append(department_id);
        }
        s.append(" order by t.createtime desc");
        List<User> users = userDao.find(s.toString());
        if ((users == null) || (users.size() == 0)) {
            return new ArrayList<User>();
        }
        return users;
    }

    @Override
    public Map<String, Object> user_statistics_user_relationship() {

        StringBuffer s = new StringBuffer();
        s.append("select");
        s.append(" sum(if(u.user_relationship = 0, 1, 0)) as taipin");
        s.append(" ,sum(if(u.user_relationship = 1, 1, 0)) as qipin");
        s.append(" ,sum(if(u.user_relationship = 2, 1, 0)) as shitongpin");
        s.append(" ,sum(if(u.user_relationship = 3, 1, 0)) as waiji");
        s.append(" ,sum(if(u.user_relationship = 4, 1, 0)) as suobeipin");
        s.append(" ,sum(if(u.user_relationship = 5, 1, 0)) as qianweipin");
        s.append(" from tb_user u");
        s.append(" where u.disabled = 0");
        List<Map<String, Object>> list = userDao.findListMapBySQL(s.toString(), null, 0, -1);
        if (list == null || list.size() != 1) {
            return new HashMap<String, Object>();
        }
        return list.get(0);

    }

    @Override
    public Map<String, Object> user_age_statistics() {
        StringBuffer s = new StringBuffer();
        s.append(" select");
        s.append(" sum(if(ub.age >= 20 && ub.age <=25, 1, 0)) as level_1");
        s.append(" ,sum(if(ub.age >= 26 && ub.age <=30, 1, 0)) as level_2");
        s.append(" ,sum(if(ub.age >= 31 && ub.age <=35, 1, 0)) as level_3");
        s.append(" ,sum(if(ub.age >= 36 && ub.age <=40, 1, 0)) as level_4");
        s.append(" ,sum(if(ub.age >= 41 && ub.age <=45, 1, 0)) as level_5");
        s.append(" ,sum(if(ub.age >= 46 && ub.age <=50, 1, 0)) as level_6");
        s.append(" ,sum(if(ub.age >= 51 && ub.age <=55, 1, 0)) as level_7");
        s.append(" ,sum(if(ub.age >= 56 && ub.age <=60, 1, 0)) as level_8");
        s.append(" ,sum(if(ub.age >= 61 && ub.age <=65, 1, 0)) as level_9");
        s.append(" from tb_user u ");
        s.append(" left join tb_user_base ub on u.id = ub.user_id and ub.disabled = 0");
        s.append(" where u.disabled = 0");
        List<Map<String, Object>> list = userDao.findListMapBySQL(s.toString(), null, 0, -1);
        if (list == null || list.size() != 1) {
            return new HashMap<String, Object>();
        }
        return list.get(0);
    }

    @Override
    public Map<String, Object> user_entry_count(String beginTime, String endTime,
                                                List<String> yearMonthList) {
        Map<String, Object> temp = new HashMap<String, Object>();
        StringBuffer s = new StringBuffer();
        s.append(" select date_format(u.createtime,'%Y-%m') as month, count(u.id) as count from tb_user u");
        s.append(" where u.disabled = 0 and u.is_synchronous = 1");
        s.append(" and date_format(u.createtime,'%Y-%m')>= date_format(str_to_date('")
            .append(beginTime).append("','%Y-%m-%d'),'%Y-%m')");
        s.append(" and date_format(u.createtime,'%Y-%m')<= date_format(str_to_date('")
            .append(endTime).append("','%Y-%m-%d'),'%Y-%m')");
        s.append(" group by date_format(u.createtime,'%Y-%m')");
        List<Map<String, Object>> list = userDao.findListMapBySQL(s.toString(), null, 0, -1);
        if (list == null || list.size() == 0) {
            return temp;
        }
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            temp.put(CommUtil.null2String(map.get("month")).replace("-", "_"), map.get("count"));
        }
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        if (yearMonthList != null && yearMonthList.size() > 0) {
            for (String yearMonth : yearMonthList) {
                if (CommUtil.isNotNull(temp.get(yearMonth))) {
                    result.put(yearMonth, temp.get(yearMonth));
                } else {
                    result.put(yearMonth, 0);
                }
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> user_dimission_count(String beginTime, String endTime,
                                                    List<String> yearMonthList) {
        Map<String, Object> temp = new HashMap<String, Object>();
        StringBuffer s = new StringBuffer();
        s.append(" select date_format(u.createtime,'%Y-%m') as month, count(u.id) as count from tb_user_dimission_logs u");
        s.append(" where u.disabled = 0");
        s.append(" and date_format(u.createtime,'%Y-%m')>= date_format(str_to_date('")
            .append(beginTime).append("','%Y-%m-%d'),'%Y-%m')");
        s.append(" and date_format(u.createtime,'%Y-%m')<= date_format(str_to_date('")
            .append(endTime).append("','%Y-%m-%d'),'%Y-%m')");
        s.append(" group by date_format(u.createtime,'%Y-%m')");
        List<Map<String, Object>> list = userDao.findListMapBySQL(s.toString(), null, 0, -1);
        if (list == null || list.size() == 0) {
            return temp;
        }
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            temp.put(CommUtil.null2String(map.get("month")).replace("-", "_"), map.get("count"));
        }
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        if (yearMonthList != null && yearMonthList.size() > 0) {
            for (String yearMonth : yearMonthList) {
                if (CommUtil.isNotNull(temp.get(yearMonth))) {
                    result.put(yearMonth, temp.get(yearMonth));
                } else {
                    result.put(yearMonth, 0);
                }
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> user_count(List<String> yearMonthList) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        if (yearMonthList != null && yearMonthList.size() > 0) {
            for (String yearMonth : yearMonthList) {
                result.put(yearMonth, getUserCount(yearMonth) + getUserDimissionCount(yearMonth));
            }
        }
        return result;
    }

    private int getUserCount(String yearMonth) {
        StringBuffer s = new StringBuffer();
        s.append("select count(u.id) from tb_user u");
        s.append(" where u.disabled = 0 and u.user_status != 4");
        s.append(" and date_format(u.createtime,'%Y-%m')<= date_format(str_to_date('")
            .append(yearMonth.replaceAll("_", "-")).append("','%Y-%m-%d'),'%Y-%m')");
        return userDao.countBySql(s.toString(), null);
    }

    private int getUserDimissionCount(String yearMonth) {
        StringBuffer s = new StringBuffer();
        s.append("select count(u.id) from tb_user u");
        s.append(" where u.disabled = 0 and u.user_status = 4");
        s.append(" and date_format(u.createtime,'%Y-%m') = date_format(str_to_date('")
            .append(yearMonth.replaceAll("_", "-")).append("','%Y-%m-%d'),'%Y-%m')");
        return userDao.countBySql(s.toString(), null);
    }
    
    /**
     * 在职人数
     */
    public int countInJobPeople(){
    	StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_user u where u.disabled = 0 and u.user_status<>4 ");
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
    }
    
    
    /**
     * 本月入职人数
     */
	public int countEntryPeople(String beginTime, String endTime){
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_user u where u.disabled = 0 and u.is_synchronous=1 ");
        s.append(" and DATE_FORMAT(u.createtime,'%Y-%m-%d') >= DATE_FORMAT('").append(beginTime).append("','%Y-%m-%d') ");
        s.append(" and DATE_FORMAT(u.createtime,'%Y-%m-%d') <= DATE_FORMAT('").append(endTime).append("','%Y-%m-%d') ");
        
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}
	
	/**
	 * 本月面试人数
	 */
	public int countInterviewPeople(String beginTime, String endTime){
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) as count  from tb_resume u where u.disabled = 0 ");
        s.append(" and DATE_FORMAT(u.createtime,'%Y-%m-%d') >= DATE_FORMAT('").append(beginTime).append("','%Y-%m-%d') ");
        s.append(" and DATE_FORMAT(u.createtime,'%Y-%m-%d') <= DATE_FORMAT('").append(endTime).append("','%Y-%m-%d') ");
        
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}
	
	/**
	 * 本月离职人数
	 */
	public int countDimissionPeople(String beginTime, String endTime){
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) as count  from tb_user_logs u where u.disabled = 0 ");
        s.append(" and DATE_FORMAT(u.dimission_time,'%Y-%m-%d') >= DATE_FORMAT('").append(beginTime).append("','%Y-%m-%d') ");
        s.append(" and DATE_FORMAT(u.dimission_time,'%Y-%m-%d') <= DATE_FORMAT('").append(endTime).append("','%Y-%m-%d') ");
        
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}
	

	/**
	 * 本月入职男士
	 */
	public int countEntryMan(String beginTime, String endTime){
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_user u ");
        s.append(" left JOIN tb_user_base b on u.id = b.user_id ");
        s.append(" where u.disabled = 0 and u.is_synchronous=1 ");
        s.append(" and DATE_FORMAT(u.createtime,'%Y-%m-%d') >= DATE_FORMAT('").append(beginTime).append("','%Y-%m-%d') ");
        s.append(" and DATE_FORMAT(u.createtime,'%Y-%m-%d') <= DATE_FORMAT('").append(endTime).append("','%Y-%m-%d') ");
        s.append(" and b.sex=1 ");
        
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}
	
	/**
	 * 本月入职女士
	 */
	public int countEntryWoman(String beginTime, String endTime){
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_user u ");
        s.append(" left JOIN tb_user_base b on u.id = b.user_id ");
        s.append(" where u.disabled = 0 and u.is_synchronous=1 ");
        s.append(" and DATE_FORMAT(u.createtime,'%Y-%m-%d') >= DATE_FORMAT('").append(beginTime).append("','%Y-%m-%d') ");
        s.append(" and DATE_FORMAT(u.createtime,'%Y-%m-%d') <= DATE_FORMAT('").append(endTime).append("','%Y-%m-%d') ");
        s.append(" and b.sex=2 ");
        
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}

	/**
	 * 本月入职博士
	 */
	public int countEntryDoctor(String beginTime, String endTime) {
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_user u ");
        s.append(" left JOIN tb_user_base b on u.id = b.user_id ");
        s.append(" where u.disabled = 0 and u.is_synchronous=1 ");
        s.append(" and DATE_FORMAT(u.createtime,'%Y-%m-%d') >= DATE_FORMAT('").append(beginTime).append("','%Y-%m-%d') ");
        s.append(" and DATE_FORMAT(u.createtime,'%Y-%m-%d') <= DATE_FORMAT('").append(endTime).append("','%Y-%m-%d') ");
        s.append(" and b.highest_education like '博士%' ");
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}

	/**
	 * 本月入职硕士
	 */
	public int countEntryMaster(String beginTime, String endTime) {
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_user u ");
        s.append(" left JOIN tb_user_base b on u.id = b.user_id ");
        s.append(" where u.disabled = 0 and u.is_synchronous=1 ");
        s.append(" and DATE_FORMAT(u.createtime,'%Y-%m-%d') >= DATE_FORMAT('").append(beginTime).append("','%Y-%m-%d') ");
        s.append(" and DATE_FORMAT(u.createtime,'%Y-%m-%d') <= DATE_FORMAT('").append(endTime).append("','%Y-%m-%d') ");
        s.append(" and b.highest_education like '硕士%' ");
        s.append(" or b.highest_education like '研究生%' ");
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}

	/**
	 * 本月入职本科
	 */
	public int countEntryUndergraduate(String beginTime, String endTime) {
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_user u ");
        s.append(" left JOIN tb_user_base b on u.id = b.user_id ");
        s.append(" where u.disabled = 0 and u.is_synchronous=1 ");
        s.append(" and DATE_FORMAT(u.createtime,'%Y-%m-%d') >= DATE_FORMAT('").append(beginTime).append("','%Y-%m-%d') ");
        s.append(" and DATE_FORMAT(u.createtime,'%Y-%m-%d') <= DATE_FORMAT('").append(endTime).append("','%Y-%m-%d') ");
        s.append(" and b.highest_education like '本科%' ");
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}
	
	/**
	 * 现有人员
	 */
	public int countNowPeople(){
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_user u ");
        s.append(" where u.disabled = 0 and u.user_status<>4 ");
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}
	
	/**
	 * 现有男士
	 */
	public int countNowMan(){
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_user u ");
        s.append(" left JOIN tb_user_base b on u.id = b.user_id ");
        s.append(" where u.disabled = 0 and u.user_status<>4 ");
        s.append(" and b.sex = 1 ");
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}
	
	/**
	 * 现有女士
	 */
	public int countNowWoman(){
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_user u ");
        s.append(" left JOIN tb_user_base b on u.id = b.user_id ");
        s.append(" where u.disabled = 0 and u.user_status<>4 ");
        s.append(" and b.sex = 2 ");
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}
	
	/**
	 * 现有实习生
	 */
	public int countNowInternship(){
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_user u ");
        s.append(" where u.disabled = 0 and u.user_status=1 ");
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}
	
	/**
	 * 现有试用期员工
	 */
	public int countNowProbation(){
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_user u ");
        s.append(" where u.disabled = 0 and u.user_status=2 ");
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}
	
	/**
	 * 现有正式员工
	 */
	public int countNowRegular(){
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_user u ");
        s.append(" where u.disabled = 0 and u.user_status=3 ");
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}
	
	/**
     * 现有博士生
     */
	public int countNowDoctor(){
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_user u ");
        s.append(" left JOIN tb_user_base b on u.id = b.user_id ");
        s.append(" where u.disabled = 0  ");
        s.append(" and b.highest_education like '博士%' ");
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}
	
	/**
     * 现有研究生
     */
	public int countNowMaster(){
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_user u ");
        s.append(" left JOIN tb_user_base b on u.id = b.user_id ");
        s.append(" where u.disabled = 0 ");
        s.append(" and b.highest_education like '硕士%' ");
        s.append(" or b.highest_education like '研究生%' ");
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}
	
	/**
     * 现有本科生
     */
	public int countNowUndergraduate(){
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_user u ");
        s.append(" left JOIN tb_user_base b on u.id = b.user_id ");
        s.append(" where u.disabled = 0 ");
        s.append(" and b.highest_education like '本科%' ");
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}
	
	/**
	 * 本月部门获奖记录数 
	 *//*
	public int countAwards(String beginTime, String endTime){
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_assessment u ");
        s.append(" where u.disabled = 0  ");
        s.append(" and DATE_FORMAT(u.createtime,'%Y-%m-%d') >= DATE_FORMAT('").append(beginTime).append("','%Y-%m-%d') ");
        s.append(" and DATE_FORMAT(u.createtime,'%Y-%m-%d') <= DATE_FORMAT('").append(endTime).append("','%Y-%m-%d') ");
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}*/
	
	
	/**
	 * 本月工作量统计
	 */
	public Object[] countWorkLoadStatistics(String beginTime, String endTime){
		StringBuffer s = new StringBuffer();
        s.append("select * ");
        s.append("from (");
        s.append(" select sum(t.bright_spot) as bright_spot");
        s.append(" ,sum(t.app_write) as app_write");
        s.append(" ,sum(t.app_send) as app_send");
        s.append(" ,sum(t.fb_write) as fb_write");
        s.append(" ,sum(t.fb_send) as fb_send");
        s.append(" ,sum(t.weibo_write) as weibo_write");
        s.append(" ,sum(t.weibo_send) as weibo_send");
        s.append(" ,sum(t.twt_write) as twt_write");
        s.append(" ,sum(t.twt_send) as twt_send");
        s.append(" ,sum(t.upload_yt_or_tencent) as upload_yt_or_tencent");
        s.append(" ,sum(t.tumblr_send) as tumblr_send");
        s.append(" ,sum(t.WeChat) as WeChat");
        s.append(" ,sum(t.inst) as inst");
        s.append(" ,sum(t.google) as google");
        s.append(" ,sum(t.pinterest) as pinterest");
        s.append(" ,sum(t.total) as total");
        s.append(" from tb_workload_statistics t where t.disabled = false");
        s.append(" order by t.createtime desc");
        s.append(" ) t");
        List<Object[]> list = (ArrayList<Object[]>) userDao.findBySql(s.toString(),
            null, 0, -1);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
	}
	
	
	/**
	 * 本月台内收文
	 */
	public int countInsideIncomeDispatches(String beginTime,String endTime){
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_inside_income_dispatches u ");
        s.append(" where u.disabled = 0 ");
        s.append(" and DATE_FORMAT(u.income_dispatches_time,'%Y-%m-%d') >= DATE_FORMAT('").append(beginTime).append("','%Y-%m-%d') ");
        s.append(" and DATE_FORMAT(u.income_dispatches_time,'%Y-%m-%d') <= DATE_FORMAT('").append(endTime).append("','%Y-%m-%d') ");
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}
	
	/**
	 * 本月台外收文
	 */
	public int countOutsideIncomeDispatches(String beginTime,String endTime){
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_outside_income_dispatches u ");
        s.append(" where u.disabled = 0 ");
        s.append(" and DATE_FORMAT(u.income_dispatches_time,'%Y-%m-%d') >= DATE_FORMAT('").append(beginTime).append("','%Y-%m-%d') ");
        s.append(" and DATE_FORMAT(u.income_dispatches_time,'%Y-%m-%d') <= DATE_FORMAT('").append(endTime).append("','%Y-%m-%d') ");
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}
	
	/**
	 * 本月事项申请数
	 */
	public int countEventApplys(String beginTime, String endTime,String number){
		StringBuffer s = new StringBuffer();
        s.append(" select count(u.id) from tb_event_apply_form u ");
        s.append(" where u.disabled = 0 ");
        s.append(" and DATE_FORMAT(u.apply_date,'%Y-%m-%d') >= DATE_FORMAT('").append(beginTime).append("','%Y-%m-%d') ");
        s.append(" and DATE_FORMAT(u.apply_date,'%Y-%m-%d') <= DATE_FORMAT('").append(endTime).append("','%Y-%m-%d') ");
        if (CommUtil.isNotNull(number)) {
            s.append(" and u.cost_company_id = ").append(number);
        }
        int countBySql = userDao.countBySql(s.toString(), null);
		return countBySql;
	}
	
	/**
	 * 本月事项申请预算金额
	 */
    public List<Object[]> countBudgetAmount(String beginTime, String endTime,String number){
		StringBuffer s = new StringBuffer();
        s.append(" select sum(u.budget_amount) from tb_event_apply_form u ");
        s.append(" where u.disabled = 0 ");
        s.append(" and DATE_FORMAT(u.apply_date,'%Y-%m-%d') >= DATE_FORMAT('").append(beginTime).append("','%Y-%m-%d') ");
        s.append(" and DATE_FORMAT(u.apply_date,'%Y-%m-%d') <= DATE_FORMAT('").append(endTime).append("','%Y-%m-%d') ");
        if (CommUtil.isNotNull(number)) {
            s.append(" and u.cost_company_id = ").append(number);
        }
        List<Object[]> list = userDao.findBySql(s.toString(), null, 0,-1);
        return list;
	}
	    
    /**
	 * 离职信息统计
	 */
	public List<Object[]> countDimissionList(String beginTime, String endTime){
		StringBuffer s = new StringBuffer();
        s.append(" select count(id),dimission_reason from tb_user_logs u  ");
        s.append(" where u.disabled = 0 ");
        s.append(" and DATE_FORMAT(u.dimission_time,'%Y-%m-%d') >= DATE_FORMAT('").append(beginTime).append("','%Y-%m-%d') ");
        s.append(" and DATE_FORMAT(u.dimission_time,'%Y-%m-%d') <= DATE_FORMAT('").append(endTime).append("','%Y-%m-%d') ");
        s.append(" group by dimission_reason ");
        List<Object[]> list = userDao.findBySql(s.toString(), null, 0,-1);
        return list;
	}
	    
	    
}
