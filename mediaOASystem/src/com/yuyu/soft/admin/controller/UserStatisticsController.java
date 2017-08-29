package com.yuyu.soft.admin.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuyu.soft.base.annotation.SecurityMapping;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.service.ICandidateHireApprovalService;
import com.yuyu.soft.service.IEventApplyFormService;
import com.yuyu.soft.service.IResumeService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;

/**
 * 人员统计分析
 * @Filename: UserStatisticsController.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Controller
public class UserStatisticsController {
    @Resource
    private IUserService                  userService;
    @Resource
    private IResumeService                resumeService;
    @Resource
    private ICandidateHireApprovalService candidateHireApprovalService;

    @SecurityMapping(res_name = "人员统计分析", res_url = "/admin/statistics/user_statistics_list.htm*", res_group = "人员统计分析")
    @RequestMapping({ "/admin/statistics/user_statistics_list.htm" })
    public ModelAndView user_statistics_list(HttpServletRequest request,
                                             HttpServletResponse response, String pageSize,
                                             String currentPage, String entry_dimission_begin_time,
                                             String entry_dimission_end_time,
                                             String recruit_begin_time, String recruit_end_time,
                                             String entry_dimission_count_begin_time,
                                             String entry_dimission_count_end_time) {
        ModelAndView mv = new JModelAndView("admin/statistics/user_statistics_list.html", request,
            response);
        mv.addObject("user_relationship_statistics",
            userService.user_statistics_user_relationship());//聘用类型
        mv.addObject("user_age_statistics", userService.user_age_statistics());//年龄

        int year = CommUtil.getThisYear();
        String format = "yyyy-MM";
        DecimalFormat df = new DecimalFormat("0.00");
        //===============================入离职人数统计begin====================================//
        if (CommUtil.isBlank(entry_dimission_count_begin_time)
            && CommUtil.isBlank(entry_dimission_count_end_time)) {
            entry_dimission_count_begin_time = year + "-01";
            entry_dimission_count_end_time = year + "-12";
        } else if (CommUtil.isBlank(entry_dimission_count_begin_time)
                   && CommUtil.isNotNull(entry_dimission_count_end_time)) {
            year = CommUtil.getYear(entry_dimission_count_end_time, format);
            entry_dimission_count_begin_time = year + "-01";
        } else if (CommUtil.isNotNull(entry_dimission_count_begin_time)
                   && CommUtil.isBlank(entry_dimission_count_end_time)) {
            year = CommUtil.getYear(entry_dimission_count_begin_time, format);
            entry_dimission_count_end_time = year + "-12";
        }
        int begin_year0 = CommUtil.getYear(entry_dimission_count_begin_time, format);
        int begin_month0 = CommUtil.getMonth(entry_dimission_count_begin_time, format);
        int end_year0 = CommUtil.getYear(entry_dimission_count_end_time, format);
        int end_month0 = CommUtil.getMonth(entry_dimission_count_end_time, format);
        if (begin_year0 == 0 || begin_month0 == 0 || end_year0 == 0 || end_month0 == 0
            || begin_year0 > end_year0 || (end_year0 == begin_year0 && begin_month0 > end_month0)
            || (end_year0 - begin_year0 == 1 && end_month0 >= begin_month0)
            || (end_year0 - begin_year0) >= 2) {
            entry_dimission_count_begin_time = year + "-01";
            entry_dimission_count_end_time = year + "-12";
        }

        List<String> entry_dimission_count_year_month_list = getYearMonthList(
            entry_dimission_count_begin_time, entry_dimission_count_end_time);

        Map<String, Object> user_entry_count_count_map = userService.user_entry_count(
            entry_dimission_count_begin_time, entry_dimission_count_end_time,
            entry_dimission_count_year_month_list);
        Map<String, Object> user_dimission_count_count_map = userService.user_dimission_count(
            entry_dimission_count_begin_time, entry_dimission_count_end_time,
            entry_dimission_count_year_month_list);
        Map<String, Object> user_count_count_map = userService
            .user_count(entry_dimission_count_year_month_list);

        mv.addObject("user_entry_count_count_map", user_entry_count_count_map);
        mv.addObject("user_dimission_count_count_map", user_dimission_count_count_map);
        mv.addObject("user_count_count_map", user_count_count_map);
        mv.addObject("entry_dimission_count_begin_time", entry_dimission_count_begin_time);
        mv.addObject("entry_dimission_count_end_time", entry_dimission_count_end_time);

        List<String> tempList = new LinkedList<String>();
        for (int i = 0; i < entry_dimission_count_year_month_list.size(); i++) {
            tempList.add(entry_dimission_count_year_month_list.get(i).replaceAll("_", "-"));
        }
        mv.addObject("entry_dimission_count_year_month_list", tempList);
        //===============================入离职人数统计end====================================//

        //===============================入离职率统计begin====================================//
        if (CommUtil.isBlank(entry_dimission_begin_time)
            && CommUtil.isBlank(entry_dimission_end_time)) {
            entry_dimission_begin_time = year + "-01";
            entry_dimission_end_time = year + "-12";
        } else if (CommUtil.isBlank(entry_dimission_begin_time)
                   && CommUtil.isNotNull(entry_dimission_end_time)) {
            year = CommUtil.getYear(entry_dimission_end_time, format);
            entry_dimission_begin_time = year + "-01";
        } else if (CommUtil.isNotNull(entry_dimission_begin_time)
                   && CommUtil.isBlank(entry_dimission_end_time)) {
            year = CommUtil.getYear(entry_dimission_begin_time, format);
            entry_dimission_end_time = year + "-12";
        }
        int begin_year = CommUtil.getYear(entry_dimission_begin_time, format);
        int begin_month = CommUtil.getMonth(entry_dimission_begin_time, format);
        int end_year = CommUtil.getYear(entry_dimission_end_time, format);
        int end_month = CommUtil.getMonth(entry_dimission_end_time, format);
        if (begin_year == 0 || begin_month == 0 || end_year == 0 || end_month == 0
            || begin_year > end_year || (end_year == begin_year && begin_month > end_month)
            || (end_year - begin_year == 1 && end_month >= begin_month)
            || (end_year - begin_year) >= 2) {
            entry_dimission_begin_time = year + "-01";
            entry_dimission_end_time = year + "-12";
        }

        List<String> entry_dimission_year_month_list = getYearMonthList(entry_dimission_begin_time,
            entry_dimission_end_time);

        Map<String, Object> user_entry_count_map = userService.user_entry_count(
            entry_dimission_begin_time, entry_dimission_end_time, entry_dimission_year_month_list);
        Map<String, Object> user_dimission_count_map = userService.user_dimission_count(
            entry_dimission_begin_time, entry_dimission_end_time, entry_dimission_year_month_list);
        Map<String, Object> resume_count_map = resumeService.resume_count(
            entry_dimission_begin_time, entry_dimission_end_time, entry_dimission_year_month_list);
        Map<String, Object> user_count_map = userService
            .user_count(entry_dimission_year_month_list);
        Map<String, Object> entry_rate_map = new LinkedHashMap<String, Object>();
        Map<String, Object> dimission_rate_map = new LinkedHashMap<String, Object>();
        for (int i = 0; i < entry_dimission_year_month_list.size(); i++) {
            String year_month = entry_dimission_year_month_list.get(i);
            int entry_count = CommUtil.null2Int(user_entry_count_map.get(year_month));
            int resume_count = CommUtil.null2Int(resume_count_map.get(year_month));
            int dimission_count = CommUtil.null2Int(user_dimission_count_map.get(year_month));
            if (resume_count == 0) {
                entry_rate_map.put(year_month, 0);
            } else {
                entry_rate_map.put(year_month, df.format((float) entry_count * 100 / resume_count));
            }
            int user_count = CommUtil.null2Int(user_count_map.get(year_month));
            if (resume_count == 0 || user_count == 0) {
                dimission_rate_map.put(year_month, 0);
            } else {
                dimission_rate_map.put(year_month,
                    df.format((float) dimission_count * 100 / user_count));
            }
        }

        //处理入离职统计x轴
        Map<Integer, String> entry_x_map = new LinkedHashMap<Integer, String>();
        Set<String> keySet = entry_rate_map.keySet();
        int i = 1;
        for (String string : keySet) {
            String replace = string.replace("_", "-");
            entry_x_map.put(i, replace);
            i++;
        }
        mv.addObject("entry_x_map", entry_x_map);

        mv.addObject("entry_rate_map", entry_rate_map);
        mv.addObject("dimission_rate_map", dimission_rate_map);
        mv.addObject("entry_dimission_begin_time", entry_dimission_begin_time);
        mv.addObject("entry_dimission_end_time", entry_dimission_end_time);
        //===============================入离职率统计end====================================//

        //===============================招聘统计begin====================================//
        if (CommUtil.isBlank(recruit_begin_time) && CommUtil.isBlank(recruit_end_time)) {
            recruit_begin_time = year + "-01";
            recruit_end_time = year + "-12";
        } else if (CommUtil.isBlank(recruit_begin_time) && CommUtil.isNotNull(recruit_end_time)) {
            year = CommUtil.getYear(recruit_end_time, format);
            recruit_begin_time = year + "-01";
        } else if (CommUtil.isNotNull(recruit_begin_time) && CommUtil.isBlank(recruit_end_time)) {
            year = CommUtil.getYear(recruit_begin_time, format);
            recruit_end_time = year + "-12";
        }
        int begin_year2 = CommUtil.getYear(recruit_begin_time, format);
        int begin_month2 = CommUtil.getMonth(recruit_begin_time, format);
        int end_year2 = CommUtil.getYear(recruit_end_time, format);
        int end_month2 = CommUtil.getMonth(recruit_end_time, format);
        if (begin_year2 == 0 || begin_month2 == 0 || end_year2 == 0 || end_month2 == 0
            || begin_year2 > end_year2 || (end_year2 == begin_year2 && begin_month2 > end_month2)
            || (end_year2 - begin_year2 == 1 && end_month2 >= begin_month2)
            || (end_year2 - begin_year2) >= 2) {
            recruit_begin_time = year + "-01";
            recruit_end_time = year + "-12";
        }
        List<String> recruit_year_month_list = getYearMonthList(recruit_begin_time,
            recruit_end_time);

        Map<String, Object> inform_interview_map = resumeService.resume_count(recruit_begin_time,
            recruit_end_time, recruit_year_month_list);//通知面试人数
        Map<String, Object> approval_employments_map = candidateHireApprovalService
            .approval_is_hired_count(recruit_begin_time, recruit_end_time, recruit_year_month_list);//审批录用人数
        Map<String, Object> actual_employment_map = userService.user_entry_count(
            recruit_begin_time, recruit_end_time, recruit_year_month_list);//实际录用人数

        //处理招聘统计x轴
        Map<Integer, String> inform_x_map = new LinkedHashMap<Integer, String>();
        Set<String> informkeySet = inform_interview_map.keySet();
        int k = 1;
        for (String string : informkeySet) {
            String replace = string.replace("_", "-");
            inform_x_map.put(k, replace);
            k++;
        }
        mv.addObject("inform_x_map", inform_x_map);

        mv.addObject("inform_interview_map", inform_interview_map);
        mv.addObject("approval_employments_map", approval_employments_map);
        mv.addObject("actual_employment_map", actual_employment_map);
        mv.addObject("recruit_begin_time", recruit_begin_time);
        mv.addObject("recruit_end_time", recruit_end_time);
        //===============================招聘统计end====================================//

        
      //===============================周期统计开始====================================//
        
        String beginTime = CommUtil.getFirstDayOfMonth();//本月第一天
        String endTime = CommUtil.getLastDayOfMonth();//本月最后一天
        //在职人数
        int count_inJob_people = userService.countInJobPeople();
        //本月入职人数
		int count_entry_people = userService.countEntryPeople(beginTime,endTime);
		//本月面试人数
		int count_interview_people = userService.countInterviewPeople(beginTime,endTime);
        //本月离职人数
		int count_dimission_people = userService.countDimissionPeople(beginTime,endTime);
		//入职率
		String enrollment_rate= "0";
		if (count_entry_people != 0) {
			enrollment_rate = df.format((float)count_entry_people * 100 /count_interview_people);
		}
		//离职率
		String turnover_rate = df.format((float)count_dimission_people * 100 /count_inJob_people);
		//本月新入职男士
        int count_entry_man = userService.countEntryMan(beginTime,endTime);
        //本月新入职女士
        int count_entry_woman = userService.countEntryWoman(beginTime,endTime);
        //本月新入职男士百分比
        String count_entry_man_percent = df.format((float)count_entry_man * 100 /count_entry_people);
        //本月新入职女士百分比
        String count_entry_woman_percent = df.format((float)count_entry_woman * 100 /count_entry_people);
        //本月新入职博士
        int count_entry_doctor = userService.countEntryDoctor(beginTime,endTime);
        //本月新入职硕士/研究生
        int count_entry_master = userService.countEntryMaster(beginTime,endTime);
        //本月新入职本科
        int count_entry_undergraduate = userService.countEntryUndergraduate(beginTime,endTime);
        //本月新入职博士百分比
        String count_entry_doctor_percent = df.format((float)count_entry_doctor * 100 /count_entry_people);
        //本月新入职硕士/研究生百分比
        String count_entry_master_percent = df.format((float)count_entry_master * 100 /count_entry_people);
        //本月新入职本科百分比
        String count_entry_undergraduate_percent = df.format((float)count_entry_undergraduate * 100 /count_entry_people);
        //现有人员（包括实习生、试用员工、正式员工）
        int count_now_people = userService.countNowPeople();
        //现有男士
        int count_now_man = userService.countNowMan();
        //现有女士
        int count_now_woman = userService.countNowWoman();
        //现有男士百分比
        String count_now_man_percent = df.format((float)count_now_man * 100 /count_now_people);
        //现有女士百分比
        String count_now_woman_percent = df.format((float)count_now_woman * 100 /count_now_people);
        //现有实习生
        int count_now_internship = userService.countNowInternship();
        //现有试用期员工
        int count_now_probation = userService.countNowProbation();
        //现有正式员工
        int count_now_regular  = userService.countNowRegular();
        //现有博士生
        int count_now_doctor = userService.countNowDoctor();
        //现有研究生
        int count_now_master = userService.countNowMaster();
        //现有本科生
        int count_now_undergraduate = userService.countNowUndergraduate();
        //现有博士生百分比
        String count_now_doctor_percent = df.format((float)count_now_doctor * 100 /count_now_people);
        //现有研究生百分比
        String count_now_master_percent = df.format((float)count_now_master * 100 /count_now_people);
        //现有本科生百分比
        String count_now_undergraduate_percent = df.format((float)count_now_undergraduate * 100 /count_now_people);
        
        //现有实习生百分比
        String count_now_internship_percent = df.format((float)count_now_internship * 100 /count_now_people);
        //现有试用期员工百分比
        String count_now_probation_percent = df.format((float)count_now_probation * 100 /count_now_people);
        //现有正式员工百分比
        String count_now_regular_percent = df.format((float)count_now_regular * 100 /count_now_people);
        /*//本月新增获奖记录
        int count_awards = userService.countAwards(beginTime,endTime);*/
        //本月工作量
        Object[] workLoadStatisticsList = userService.countWorkLoadStatistics(beginTime,endTime);
        List<Object> countWorkLoadStatisticsList = new ArrayList<Object>();
        for (int j = 0; j < workLoadStatisticsList.length; j++) {
        	if (null ==  workLoadStatisticsList[j]) {
        		countWorkLoadStatisticsList.add(j,"0");
			}else {
				countWorkLoadStatisticsList.add(j,(BigDecimal) workLoadStatisticsList[j]);
			}
		}
        
        //本月台内收文
        int count_inside_income_dispatches = userService.countInsideIncomeDispatches(beginTime,endTime);
        //本月台外收文
        int count_outside_income_dispatches = userService.countOutsideIncomeDispatches(beginTime,endTime);
        
        //本月项目申请数
        int count_event_applys = userService.countEventApplys(beginTime,endTime,"");
        //本月预算金额
        List<Object[]> count_event_applys_budget_amount = userService.countBudgetAmount(beginTime,endTime,"");
        //中视前卫
        int count_event_applys_z = userService.countEventApplys(beginTime,endTime,"1");
        //中视前卫预算金额
        List<Object[]> count_event_applys_z_m = userService.countBudgetAmount(beginTime,endTime,"1");
        //国际视同
        int count_event_applys_g = userService.countEventApplys(beginTime,endTime,"2");
        //国际视同预算金额
        List<Object[]> count_event_applys_g_m = userService.countBudgetAmount(beginTime,endTime,"2");
        //成都索贝
        int count_event_applys_c = userService.countEventApplys(beginTime,endTime,"3");
        //成都索贝预算金额
        List<Object[]> count_event_applys_c_m = userService.countBudgetAmount(beginTime,endTime,"3");
        
        //离职信息统计
        List<Object[]> dimission_list = userService.countDimissionList(beginTime,endTime);
        
        
        
        
        
		mv.addObject("count_entry_people", count_entry_people);
		mv.addObject("count_interview_people", count_interview_people);
		mv.addObject("count_dimission_people", count_dimission_people);
		mv.addObject("count_entry_man", count_entry_man);
		mv.addObject("count_entry_woman", count_entry_woman);
		mv.addObject("count_entry_man_percent", count_entry_man_percent);
		mv.addObject("count_entry_woman_percent", count_entry_woman_percent);
		mv.addObject("enrollment_rate", enrollment_rate);
		mv.addObject("turnover_rate", turnover_rate);
		mv.addObject("count_entry_doctor", count_entry_doctor);
		mv.addObject("count_entry_master", count_entry_master);
		mv.addObject("count_entry_undergraduate", count_entry_undergraduate);
		mv.addObject("count_entry_doctor_percent", count_entry_doctor_percent);
		mv.addObject("count_entry_master_percent", count_entry_master_percent);
		mv.addObject("count_entry_undergraduate_percent", count_entry_undergraduate_percent);
		mv.addObject("count_now_people", count_now_people);
		mv.addObject("count_now_man", count_now_man);
		mv.addObject("count_now_woman", count_now_woman);
		mv.addObject("count_now_man_percent", count_now_man_percent);
		mv.addObject("count_now_woman_percent", count_now_woman_percent);
		mv.addObject("count_now_internship", count_now_internship);
		mv.addObject("count_now_probation", count_now_probation);
		mv.addObject("count_now_regular", count_now_regular);
		mv.addObject("count_now_doctor", count_now_doctor);
		mv.addObject("count_now_master", count_now_master);
		mv.addObject("count_now_undergraduate", count_now_undergraduate);
		mv.addObject("count_now_doctor_percent", count_now_doctor_percent);
		mv.addObject("count_now_master_percent", count_now_master_percent);
		mv.addObject("count_now_undergraduate_percent", count_now_undergraduate_percent);
		
		
		mv.addObject("count_now_internship_percent", count_now_internship_percent);
		mv.addObject("count_now_probation_percent", count_now_probation_percent);
		mv.addObject("count_now_regular_percent", count_now_regular_percent);
		/*mv.addObject("count_awards", count_awards);*/
		mv.addObject("countWorkLoadStatisticsList", countWorkLoadStatisticsList);
		mv.addObject("count_inside_income_dispatches", count_inside_income_dispatches);
		mv.addObject("count_outside_income_dispatches", count_outside_income_dispatches);
		mv.addObject("count_event_applys", count_event_applys);
		mv.addObject("count_event_applys", count_event_applys);
		mv.addObject("count_event_applys_budget_amount", count_event_applys_budget_amount);
		mv.addObject("count_event_applys_z", count_event_applys_z);
		mv.addObject("count_event_applys_z_m", count_event_applys_z_m);
		mv.addObject("count_event_applys_g", count_event_applys_g);
		mv.addObject("count_event_applys_g_m", count_event_applys_g_m);
		mv.addObject("count_event_applys_c", count_event_applys_c);
		mv.addObject("count_event_applys_c_m", count_event_applys_c_m);
		mv.addObject("dimission_list", dimission_list);
		mv.addObject("user_dimission_reason_map", Constants.DIMISSION_REASON_MAP);
		
		
      //===============================周期统计结束====================================//
        
        
        return mv;
    }

    public List<String> getYearMonthList(String beginTime, String endTime) {
        String format = "yyyy-MM";
        List<String> yearMonthList = new LinkedList<String>();
        int begin_year = CommUtil.getYear(beginTime, format);
        int begin_month = CommUtil.getMonth(beginTime, format);
        int end_year = CommUtil.getYear(endTime, format);
        int end_month = CommUtil.getMonth(endTime, format);
        if (begin_year == end_year) {
            for (int i = begin_month; i <= end_month; i++) {
                if (i < 10) {
                    yearMonthList.add(begin_year + "_0" + i);
                } else {
                    yearMonthList.add(begin_year + "_" + i);
                }
            }
        } else if (end_year - begin_year == 1) {
            for (int i = begin_month; i <= 12; i++) {
                if (i < 10) {
                    yearMonthList.add(begin_year + "_0" + i);
                } else {
                    yearMonthList.add(begin_year + "_" + i);
                }
            }
            for (int i = 1; i <= end_month; i++) {
                if (i < 10) {
                    yearMonthList.add(end_year + "_0" + i);
                } else {
                    yearMonthList.add(end_year + "_" + i);
                }
            }
        }
        return yearMonthList;
    }

    private Map<String, Object> getUserEntryRate(Map<String, Object> user_entry_count,
                                                 Map<String, Object> resume_count,
                                                 String entry_dimission_begin_time,
                                                 String entry_dimission_end_time) {

        return resume_count;

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
