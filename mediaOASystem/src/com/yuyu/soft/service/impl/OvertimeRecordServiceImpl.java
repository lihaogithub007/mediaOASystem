package com.yuyu.soft.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.OvertimeRecord;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IOvertimeRecordService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

@Service("overtimeRecordService")
public class OvertimeRecordServiceImpl implements IOvertimeRecordService {

    @Resource
    private IBaseDao<OvertimeRecord> overtimeRecordDao;
    @Resource
    private IUserService             userService;

    @Override
    public List<OvertimeRecord> queryOvertimeRecord(String hql, Map<String, Object> paramsMap,
                                                    PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(overtimeRecordDao.count("select count(t) " + hql, paramsMap));
            return overtimeRecordDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return overtimeRecordDao.find(hql, paramsMap);
        }

    }

    @Override
    public List<Map<String, Object>> queryOvertimeRecordMap(String sql,
                                                            Map<String, Object> paramsMap,
                                                            PagerInfo pager) {
        if (pager != null) {
            pager
                .setRowsCount(overtimeRecordDao.countBySql("select count(t.id) " + sql, paramsMap));
            return overtimeRecordDao.findListMapBySQL("select * " + sql, paramsMap,
                pager.getStart(), pager.getPageSize());
        } else {
            return overtimeRecordDao.findListMapBySQL("select * " + sql, paramsMap, 0, -1);
        }
    }

    @Override
    public OvertimeRecord getOvertimeRecord(Long id) {
        return overtimeRecordDao.get(OvertimeRecord.class, id);
    }

    @Override
    public void addOvertimeRecord(OvertimeRecord overtimeRecord) {
        this.overtimeRecordDao.save(overtimeRecord);
    }

    @Override
    public void updateOvertimeRecord(OvertimeRecord overtimeRecord) {
        this.overtimeRecordDao.update(overtimeRecord);
    }

    @Override
    public void delOvertimeRecord(OvertimeRecord overtimeRecord) {
        overtimeRecord = getOvertimeRecord(overtimeRecord.getId());
        overtimeRecord.setDisabled(true);
        updateOvertimeRecord(overtimeRecord);
    }

    @Override
    public OvertimeRecord getOvertimeRecordByUserIdAndDate(Long user_id, Date date) {

        if (user_id == null || date == null) {
            return null;
        }
        StringBuffer s = new StringBuffer();
        s.append("from OvertimeRecord t where t.disabled = false");
        s.append(" and t.user.id = ").append(user_id);
        s.append(" and date_format(t.overtime_date,'%Y-%m-%d') = '")
            .append(CommUtil.parseShortDateTime(date)).append("'");

        List<OvertimeRecord> list = overtimeRecordDao.find(s.toString());
        if (list == null || list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            for (OvertimeRecord or : list) {
                delOvertimeRecord(or);
            }
            return null;
        }
    }

    @Override
    public ResultMsg save(HttpServletRequest request, Long id, Long user_id, String begin_time,
                          String end_time) {

        WebForm wf = new WebForm();
        OvertimeRecord overtimeRecord = null;
        if (id == null) {
            overtimeRecord = (OvertimeRecord) wf.toPo(request, OvertimeRecord.class);
            overtimeRecord.setCreatetime(new Date());
            overtimeRecord.setDisabled(false);
        } else {
            OvertimeRecord dbOvertimeRecord = getOvertimeRecord(id);
            if (dbOvertimeRecord == null || dbOvertimeRecord.getId() == null
                || dbOvertimeRecord.getDisabled()) {
                return CommUtil.setResultMsgData(null, false, "找不到加班记录");
            }
            overtimeRecord = (OvertimeRecord) wf.toPo(request, dbOvertimeRecord);
        }
        ResultMsg rmg = save_check_and_init(overtimeRecord, user_id, begin_time, end_time);
        if (!rmg.getResult()) {
            return rmg;
        }
        if (id == null) {
            addOvertimeRecord(overtimeRecord);
        } else {
            updateOvertimeRecord(overtimeRecord);
        }
        CommUtil.setResultMsgData(rmg, true, "加班记录保存成功");
        overtimeRecord = getOvertimeRecord(overtimeRecord.getId());
        overtimeRecord.setUser_id(overtimeRecord.getUser().getId());
        overtimeRecord.setOvertime_datestr(CommUtil.parseShortDateTime(overtimeRecord
            .getOvertime_date()));
        String time = CommUtil.parseShortDateTime(overtimeRecord
                .getOvertime_date()).substring(0, 7);
        List<OvertimeRecord> list = queryOvertimeRecords(user_id,time);
        
        int totalMinutes = 0;
        String totalTime = null;
        
        for (OvertimeRecord overtimeRecord2 : list) {
        	String work_hours = overtimeRecord2.getWork_hours();
        	if (work_hours != null && !work_hours.equals("")) {
				if (work_hours.contains("-")) {
					int hours = Integer.parseInt(work_hours.substring(1, work_hours.indexOf(":")));
					int minute = Integer.parseInt(work_hours.substring(work_hours.indexOf(":") + 1));
					totalMinutes -= hours * 60;
					totalMinutes -= minute;
				} else {
					int hours = Integer.parseInt(work_hours.substring(0, work_hours.indexOf(":")));
					int minute = Integer.parseInt(work_hours.substring(work_hours.indexOf(":") + 1));
					totalMinutes += hours * 60;
					totalMinutes += minute;
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
        overtimeRecord.setTotalTime(totalTime);
        rmg.setData(overtimeRecord);
        return rmg;
    }


	private List<OvertimeRecord> queryOvertimeRecords(Long id, String time) {
		
		StringBuilder s = new StringBuilder();
        s.append(" from OvertimeRecord t where t.disabled = false");
        s.append(" and DATE_FORMAT(t.overtime_date,'%Y-%m') = '").append(time).append("'");
        s.append(" and t.user.id = ").append(id);
        return overtimeRecordDao.find(s.toString());
	}

    private ResultMsg save_check_and_init(OvertimeRecord overtimeRecord, Long user_id,
                                          String begin_time, String end_time) {
        if (overtimeRecord == null) {
            return CommUtil.setResultMsgData(null, false, "加班记录对象为空");
        }
        if (CommUtil.isBlank(begin_time)) {
            return CommUtil.setResultMsgData(null, false, "加班开始时间为空");
        }
        if (CommUtil.isBlank(end_time)) {
            return CommUtil.setResultMsgData(null, false, "加班结束时间为空");
        }
        if (user_id == null) {
            return CommUtil.setResultMsgData(null, false, "用户标识为空");
        }
        User user = userService.getUser(user_id);
        if (user == null || user.getId() == null || user.getDisabled()
            || user.getUser_status() == 4) {
            return CommUtil.setResultMsgData(null, false, "用户不存在或已离职");
        }
        overtimeRecord.setUser(user);
        Date overtime_date = overtimeRecord.getOvertime_date();
        if (overtime_date == null) {
            return CommUtil.setResultMsgData(null, false, "加班记录日期为空");
        }
        Integer overtime_status = overtimeRecord.getOvertime_status();
        if (overtime_status == null
            || CommUtil.isBlank(Constants.OVERTIME_STATUS_MAP.get(overtime_status.toString()))) {
            return CommUtil.setResultMsgData(null, false, "加班类型为空或不符规定");
        }

        String data = CommUtil.parseShortDateTime(overtime_date);
        String work_begin_time = data + " " + begin_time.trim() + ":00";
        String work_end_time = data + " " + end_time.trim() + ":00";

        if (CommUtil.formatDate(work_begin_time, "yyyy-MM-dd HH:mm:ss") == null) {
            return CommUtil.setResultMsgData(null, false, "加班开始时间为空");
        }
        overtimeRecord.setWork_begin_time(CommUtil.formatDate(work_begin_time,
            "yyyy-MM-dd HH:mm:ss"));

        if (CommUtil.formatDate(work_end_time, "yyyy-MM-dd HH:mm:ss") == null) {
            return CommUtil.setResultMsgData(null, false, "加班结束时间为空");
        }
        overtimeRecord.setWork_end_time(CommUtil.formatDate(work_end_time, "yyyy-MM-dd HH:mm:ss"));

        begin_time = CommUtil.parseDateTime("HH:mm", overtimeRecord.getWork_begin_time());
        end_time = CommUtil.parseDateTime("HH:mm", overtimeRecord.getWork_end_time());
        try {
            int begin_hour = Integer.parseInt(begin_time.split(":")[0]);
            int begin_minute = Integer.parseInt(begin_time.split(":")[1]);
            int end_hour = Integer.parseInt(end_time.split(":")[0]);
            int end_minute = Integer.parseInt(end_time.split(":")[1]);

            if (begin_hour > end_hour || (begin_hour == end_hour && begin_minute > end_minute)) {
                return CommUtil.setResultMsgData(null, false, "加班开始时间在加班结束时间之后");
            }

            int base_minutes = 0;
            if (overtime_status == 1) {
                base_minutes = 8 * 60 + 30;
            }

            int result_minutes = (end_hour * 60 + end_minute) - (begin_hour * 60 + begin_minute)
                                 - base_minutes;
            String work_hours = "";
            if (result_minutes < 0) {
                work_hours = "-";
            }
            int hour = Math.abs(result_minutes) / 60;
            int minute = Math.abs(result_minutes) % 60;

            work_hours = work_hours + hour + ":";
            if (minute < 10) {
                work_hours = work_hours + "0" + minute;
            } else {
                work_hours = work_hours + minute;
            }
            overtimeRecord.setWork_hours(work_hours);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.setResultMsgData(null, false, "加班日期格式不正确");
        }
        return CommUtil.setResultMsgData(null, true, "校验成功");
    }
}
