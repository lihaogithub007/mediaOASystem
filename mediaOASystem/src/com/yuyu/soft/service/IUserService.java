package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuyu.soft.entity.User;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 用户相关的接口处理
 *                       
 * @Filename: IUserService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IUserService {

    /**
     * 查询用户数量(sql)
     * 用于导出Excel
     */
    int getCountForExportExcel(Long department_id, Long duty_id, String true_name,
                               String card_number, Integer user_status, Integer sex, String age,
                               Integer domicile_type, Integer user_relationship,
                               String highest_education, String hobby_and_speciality,
                               Integer work_years, String job_titles_level, Integer blood_type,
                               Integer constellation, String nation, String foreign_language_level,
                               Integer iD_type, Integer political_status, String highest_offering,
                               String condition_of_children, Integer marriage_status,
                               String join_party_date_beginTime, String join_party_date_endTime,
                               Integer is_have_press_card, String mobile, Long section_id, Long sub_section_id);

    /**
     * 查询用户(sql)
     * 用于导出Excel
     */
    List<Object[]> getUsersForExportExcel(Long department_id, Long duty_id, String true_name,
                                          String card_number, Integer user_status, Integer sex,
                                          String age, Integer domicile_type,
                                          Integer user_relationship, String highest_education,
                                          String hobby_and_speciality, int beginIndex,
                                          int pageSize, Integer work_years,
                                          String job_titles_level, Integer blood_type,
                                          Integer constellation, String nation,
                                          String foreign_language_level, Integer iD_type,
                                          Integer political_status, String highest_offering,
                                          String condition_of_children, Integer marriage_status,
                                          String join_party_date_beginTime,
                                          String join_party_date_endTime,
                                          Integer is_have_press_card, String mobile, 
                                          Long section_id, Long sub_section_id);

    /**
     * 查询所有用户
     */
    List<User> queryAllUser();

    /**
     * 查询所有用户（指定用户状态，多个用户状态用英文','号分隔）
     */
    List<User> queryAllUserByUserStatus(String user_status);

    /**
     * 查询所有用户（指定用户状态和添加日期，多个用户状态用英文','号分隔）
     */
    List<User> queryAllUserByUserStatusAndCreatetime(String user_status, String yesterday);

    /**
     * 导出通讯录专用
     */
    int getUserAddressBookCountForExportExcel(Long department_id, Long duty_id, String true_name);

    /**
     * 导出通讯录专用
     */
    List<Object[]> getUserAddressBookForExportExcel(Long department_id, Long duty_id,
                                                    String true_name, int beginIndex, int pageSize);

    /**
     * 查询用户列表(分页)
     * @param hql
     * @param paramsMap
     * @param pager
     * @return
     */
    List<User> queryUser(String hql, Map<String, Object> paramsMap, PagerInfo pager);

    /**
     * 查询今日生日的所有用户
     */
    List<User> queryTodayBirthdayUsers();

    /**
     * 根据ID获取用户对象
     * @param id
     * @return
     */
    User getUser(Long id);

    /**
     * 添加用户
     * @param user
     */
    void addUser(User user);

    /**
     * @param user
     */
    public void updateUser(User user);

    /**
     * 逻辑删除user(disabled=1)
     */
    public void delUser(User user);

    /**
     * 添加保存
     */
    public ResultMsg add_save(User user, Long department_id, Long duty_id,
    					String res_ids, Long section_id, Long sub_section_id);

    /**
     * 编辑保存
     */
    public ResultMsg edit_save(User user, Long department_id, Long duty_id, String res_ids,
                               Integer db_user_status, String dimission_reason,
                               String dimission_time, Long section_id, Long sub_section_id);

    /**
     * 验证手机号是否存在
     */
    public boolean verify_user_mobile(Long user_id, String mobile);

    /**
     * 验证用户登录
     */
    public boolean verify_user_login(String mobile, String password);

    /**
     * 查询用户
     */
    public User query_user_login(String mobile, String password);

    /**
     * 验证多用户ID字符串
     */
    public ResultMsg verify_user_ids(String user_ids, String errMsg, boolean allow_blank);

    /**
     * 验证多用户ID字符串
     * 是否为空，是否在某部门下
     */
    ResultMsg verify_user_ids(String user_ids, Long department_id, String errMsg,
                              boolean allow_blank);

    /**
     * 根据用户IDs获取用户Names
     */
    public String getUserNames(String user_ids);

    /**
     * 校验用户集合是否存在正常用户(暂时为disabled=false)
     * 存在返回true
     */
    public boolean verify_user_list_contains_normal(List<User> list);

    /**
     * 根据手机号获取用户
     */
    public List<User> queryUserByMobile(String mobile);

    /**
     * 人事档案基本信息保存
     */
    public ResultMsg user_file_save(HttpServletRequest request);

    /**
     * 查询某部门下所有用户
     */
    public List<User> queryUserByDepartment(Long department_id);

    /**
     * 聘用类型统计
     */
    public Map<String, Object> user_statistics_user_relationship();

    /**
     * 年龄统计（20-65岁）
     */
    public Map<String, Object> user_age_statistics();

    /**
     * 入职人数(为同步入职)
     */
    Map<String, Object> user_entry_count(String beginTime, String endTime,
                                         List<String> yearMonthList);

    /**
     * 离职人数
     */
    Map<String, Object> user_dimission_count(String beginTime, String endTime,
                                             List<String> yearMonthList);

    /**
     * 总人数
     */
    Map<String, Object> user_count(List<String> yearMonthList);
    
    /**
     * 在职人数
     */
    int countInJobPeople();
    
    /**
     * 本月入职人数
     */
	int countEntryPeople(String beginTime, String endTime);

	/**
	 * 本月面试人数
	 */
	int countInterviewPeople(String beginTime, String endTime);
	
	/**
	 * 本月离职人数
	 */
	int countDimissionPeople(String beginTime, String endTime);
	
	/**
	 * 本月入职男士
	 */
	int countEntryMan(String beginTime, String endTime);

	/**
	 * 本月入职女士
	 */
	int countEntryWoman(String beginTime, String endTime);
	
	/**
	 * 本月入职博士
	 */
	int countEntryDoctor(String beginTime, String endTime);
	
	/**
	 * 本月入职硕士
	 */
	int countEntryMaster(String beginTime, String endTime);
	
	/**
	 * 本月入职本科
	 */
	int countEntryUndergraduate(String beginTime, String endTime);
	
	/**
	 * 现有人员
	 */
	int countNowPeople();
	
	/**
	 * 现有男士
	 */
	int countNowMan();
	
	/**
	 * 现有女士
	 */
	int countNowWoman();

	/**
	 * 现有实习生
	 */
	int countNowInternship();

	/**
	 * 现有试用期员工
	 */
	int countNowProbation();

	/**
	 * 现有正式员工
	 */
	int countNowRegular();

	/**
	 * 本月部门获奖记录数 
	 */
	/*int countAwards(String beginTime, String endTime);*/
	
	/**
	 * 本月工作量统计
	 */
	Object[] countWorkLoadStatistics(String beginTime, String endTime);
	
	/**
	 * 本月台内收文
	 */
	int countInsideIncomeDispatches(String beginTime, String endTime);
	
	/**
	 * 本月台外收文
	 */
	int countOutsideIncomeDispatches(String beginTime, String endTime);
	
	/**
	 * 本月事项申请数
	 */
	int countEventApplys(String beginTime, String endTime,String number);

	 /**
	 * 本月事项申请预算金额
	 */
    List<Object[]> countBudgetAmount(String beginTime, String endTime,String number);

    /**
     * 现有博士生
     */
	int countNowDoctor();

	/**
     * 现有研究生
     */
	int countNowMaster();

	/**
     * 现有本科生
     */
	int countNowUndergraduate();

	/**
	 * 离职信息统计
	 */
	List<Object[]> countDimissionList(String beginTime, String endTime);

	
	
}
