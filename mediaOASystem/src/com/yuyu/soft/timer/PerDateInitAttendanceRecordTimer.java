package com.yuyu.soft.timer;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.yuyu.soft.entity.AttendanceRecord;
import com.yuyu.soft.entity.TimerLogs;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IAttendanceRecordService;
import com.yuyu.soft.service.ITimerLogsService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;

/**
 * 考勤记录初始化定时任务
 * 每日凌晨一点  
 * @Filename: PerDateInitAttendanceRecordTimer.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Component("perDateInitAttendanceRecordTimer_task")
public class PerDateInitAttendanceRecordTimer {

    protected static final Log       log  = LogFactory
                                              .getLog(PerDateInitAttendanceRecordTimer.class);
    @Resource
    private ITimerLogsService        timerLogsService;
    @Resource
    private IUserService             userService;
    @Resource
    private IAttendanceRecordService attendanceRecordService;

    private static byte[]            lock = new byte[0];

    public void execute() throws IOException {

        String attendance_record_month = CommUtil.getCurrentTime("yyyy-MM-dd");
        log.info("\n【考勤记录初始化】考勤日期：" + attendance_record_month);
        TimerLogs timerLog = timerLogsService.saveLogs("考勤记录初始化定时任务【日】");
        initAttendanceRecord(attendance_record_month);
        timerLogsService.updateLogs(timerLog);
    }

    //初始化月考勤记录
    private void initAttendanceRecord(String attendance_record_month) {
        List<AttendanceRecord> arList = attendanceRecordService.queryAttendanceRecord(null,
            attendance_record_month);

        String yesterday = CommUtil.getDay(-1);
        List<User> userList = userService.queryAllUserByUserStatusAndCreatetime("1,2,3", yesterday);//前一天，非离职员工
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                try {
                    boolean exist = false;
                    if (arList != null && arList.size() > 0) {
                        for (AttendanceRecord ar : arList) {
                            if (ar.getUser() != null && ar.getUser().getId() != null
                                && ar.getUser().getId() == user.getId()) {
                                exist = true;
                                break;
                            }
                        }
                    }
                    if (!exist) {
                        synchronized (lock) {
                            attendanceRecordService.add_save(user, attendance_record_month);
                        }
                    }
                    synchronized (lock) {
                        List<AttendanceRecord> tempList = attendanceRecordService
                            .queryAttendanceRecord(user.getId(), attendance_record_month);
                        if (tempList != null && tempList.size() > 1) {
                            for (int i = 0; i < tempList.size(); i++) {
                                if (i != 0) {
                                    attendanceRecordService
                                        .realDelAttendanceRecord(tempList.get(i));
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("【考勤记录初始化】error==>用户：" + user.getTrue_name() + ", 时间："
                              + attendance_record_month);
                }
            }
        }

    }
}
