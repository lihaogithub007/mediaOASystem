package com.yuyu.soft.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuyu.soft.base.annotation.SecurityMapping;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.OvertimeRecord;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IOvertimeRecordService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.PageUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 加班记录
 *                       
 * @Filename: OvertimeRecordController.java
 * @Version: 1.0
 *
 */
@Controller
public class OvertimeRecordController {

    @Resource
    private IOvertimeRecordService overtimeRecordService;
    @Resource
    private IUserService           userService;

    @SecurityMapping(res_name = "加班记录列表", res_url = "/admin/work_attendance/overtime_record_list.htm*", res_group = "加班记录")
    @RequestMapping({ "/admin/work_attendance/overtime_record_list.htm" })
	public ModelAndView user_list(HttpServletRequest request, HttpServletResponse response, String pageSize,
			String currentPage, String true_name, String overtime_record_month) {
		ModelAndView mv = new JModelAndView("admin/work_attendance/overtime_record_list.html", request, response);

        Date tempDate = CommUtil.formatDate(overtime_record_month + "-01");
        int year = CommUtil.getYear(tempDate);
        int month = CommUtil.getMonth(tempDate);
        int days = CommUtil.getDaysOfMonth(tempDate);
        if (year == 0 || month == 0 || days == 0) {
            year = CommUtil.getThisYear();
            Date date = new Date();
            month = CommUtil.getMonth(date);
            days = CommUtil.getDaysOfMonth(date);
        }
        if (month < 10) {
            overtime_record_month = year + "-0" + month;
        } else {
            overtime_record_month = year + "-" + month;
        }

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);

        List<String> thList = new LinkedList<String>();
		List<Map<String, Object>> list = queryWorkloadStatisticsMap(true_name, overtime_record_month, pager, thList);


		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		// 获取总时长
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				Map<String, Object> map1 = new LinkedHashMap<String, Object>();
				// map1.put(key, value)
				int totalMinutes = 0;
				for (String key : map.keySet()) {
					map1.put(key, map.get(key));
					String totalTime = null;
					// System.out.println(key+map1.get(key));
					for (int i = 1; i <= days; i++) {
						String dayNumber = "day" + i;
						if (dayNumber.equals(key)) {
							String time = (String) map.get(key);
							if (time != null && !time.equals("")) {
								if (time.contains("-")) {
									int hours = Integer.parseInt(time.substring(1, time.indexOf(":")));
									int minute = Integer.parseInt(time.substring(time.indexOf(":") + 1));
									totalMinutes -= hours * 60;
									totalMinutes -= minute;
								} else {
									int hours = Integer.parseInt(time.substring(0, time.indexOf(":")));
									int minute = Integer.parseInt(time.substring(time.indexOf(":") + 1));
									totalMinutes += hours * 60;
									totalMinutes += minute;
								}
							}
						}
					}
					if (totalMinutes < 0) {
						int total_minutes = Math.abs(totalMinutes);
						int totalHours = total_minutes / 60;
						int totalMinute = total_minutes % 60;
						if (totalMinute < 10) {
							totalTime = "-" + totalHours + ":0" + totalMinute;
						} else {
							totalTime = "-" + totalHours + ":" + totalMinute;
						}
					} else {
						int totalHours = totalMinutes / 60;
						int totalMinute = totalMinutes % 60;
						if (totalMinute < 10) {
							totalTime = "" + totalHours + ":0" + totalMinute;
						} else {
							totalTime = "" + totalHours + ":" + totalMinute;
						}
					}
					map1.put("totalTime", totalTime);
				}
				list2.add(map1);
			}
		}

		PageUtil.savePageInfo2ModelAndView(mv, list2, pager);
        mv.addObject("thList", thList);
        mv.addObject("true_name", true_name);
        mv.addObject("overtime_record_month", overtime_record_month);
        mv.addObject("days", days);
        return mv;
    }

	private List<Map<String, Object>> queryWorkloadStatisticsMap(String true_name, String overtime_record_month,
			PagerInfo pager, List<String> thList) {

        Date tempDate = CommUtil.formatDate(overtime_record_month + "-01");
        int year = CommUtil.getYear(tempDate);
        int month = CommUtil.getMonth(tempDate);
        int days = CommUtil.getDaysOfMonth(tempDate);
        if (year == 0 || month == 0 || days == 0) {
            year = CommUtil.getThisYear();
            Date date = new Date();
            month = CommUtil.getMonth(date);
            days = CommUtil.getDaysOfMonth(date);
        }
        if (month < 10) {
            overtime_record_month = year + "-0" + month;
        } else {
            overtime_record_month = year + "-" + month;
        }

        if (thList == null) {
            thList = new LinkedList<String>();
        }
        StringBuilder sql = new StringBuilder();
        sql.append("from (");
        sql.append(" select u.id");
        sql.append(" ,u.true_name ");
        for (int i = 1; i <= days; i++) {
            thList.add(month + "." + i);
            if (i < 10) {
                sql.append(" ,max(case DATE_FORMAT(tor.overtime_date,'%Y-%m-%d') when '")
						.append(overtime_record_month + "-0" + i).append("' then tor.work_hours else '' end) as day")
						.append(i);
            } else {
                sql.append(" ,max(case DATE_FORMAT(tor.overtime_date,'%Y-%m-%d') when '")
						.append(overtime_record_month + "-" + i).append("' then tor.work_hours else '' end) as day")
						.append(i);
            }
        }
        sql.append(" from tb_user u ");
        sql.append(" left join tb_overtime_record tor");
        sql.append(" on u.id = tor.user_id");
        sql.append("  and tor.disabled = 0");
		sql.append(" and DATE_FORMAT(tor.overtime_date,'%Y-%m') = '").append(overtime_record_month).append("'");
        sql.append(" where u.disabled = 0");
        sql.append(" and u.user_status <> 4");
        if (CommUtil.isNotNull(true_name)) {
            sql.append(" and u.true_name like '%").append(true_name.trim()).append("%'");
        }
        sql.append(" group by u.id");
        sql.append(" order by u.department_id asc, u.id asc");
        sql.append(" ) t");
		List<Map<String, Object>> list = overtimeRecordService.queryOvertimeRecordMap(sql.toString(), null, pager);
        return list;
    }

    @RequestMapping({ "/admin/work_attendance/overtime_record_edit_dialog.htm" })
	public ModelAndView overtime_record_edit_dialog(HttpServletRequest request, HttpServletResponse response,
			String currentPage, String pageSize, Long user_id, String date) {
        if (user_id == null || CommUtil.isBlank(date) || CommUtil.formatDate(date) == null) {
            return null;
        }
        User user = userService.getUser(user_id);
		if (user == null || user.getId() == null || user.getDisabled() || user.getUser_status() == 4) {
            return null;
        }
		ModelAndView mv = new JModelAndView("dialogs/overtime_record_edit_dialog.html", request, response);
		OvertimeRecord overtimeRecord = overtimeRecordService.getOvertimeRecordByUserIdAndDate(user.getId(),
				CommUtil.formatDate(date));
        mv.addObject("obj", overtimeRecord);
        mv.addObject("user", user);
        mv.addObject("date", date);
        mv.addObject("overtime_status_map", Constants.OVERTIME_STATUS_MAP);
        return mv;
    }

    @RequestMapping({ "/admin/work_attendance/overtime_record_edit_save.htm" })
	public void overtime_record_edit_save(HttpServletRequest request, HttpServletResponse response, Long id,
			Long user_id, String currentPage, String begin_time, String end_time) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "加班记录保存成功");
        if (rmg.getResult()) {
            try {
                rmg = overtimeRecordService.save(request, id, user_id, begin_time, end_time);
            } catch (Exception e) {
                CommUtil.setResultMsgData(rmg, false, e.getMessage());
            }
        }
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

}
