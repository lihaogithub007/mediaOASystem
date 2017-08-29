package com.yuyu.soft.timer;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.yuyu.soft.entity.HolidaySmsRemind;
import com.yuyu.soft.entity.TimerLogs;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IHolidaySmsRemindService;
import com.yuyu.soft.service.ITimerLogsService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.sms.MeilianSmsSender;

/**
 * 节日短信问候定时任务
 *                       
 * @Filename: PerDateHolidaySmsRemindTimer.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Component("perDateHolidaySmsRemindTimer_task")
public class PerDateHolidaySmsRemindTimer {

    protected static final Log       log = LogFactory.getLog(PerDateHolidaySmsRemindTimer.class);
    @Resource
    private ITimerLogsService        timerLogsService;
    @Resource
    private IUserService             userService;
    @Resource
    private IHolidaySmsRemindService holidaySmsRemindService;

    public void execute() throws IOException {

        TimerLogs timerLog = timerLogsService.saveLogs("节日短信问候定时任务");
        try {
            sendHolidaySms();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("节日短信问候发送失败，时间：" + CommUtil.getCurrentTime());
        }
        try {
            sendTodayBirthdaySms();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("生日短信问候发送失败，时间：" + CommUtil.getCurrentTime());
        }

        timerLogsService.updateLogs(timerLog);
    }

    //今天的节日短信问候
    private void sendHolidaySms() {
        List<HolidaySmsRemind> todayHolidaySmsReminds = holidaySmsRemindService
            .queryTodayHolidaySmsReminds();
        if (todayHolidaySmsReminds == null || todayHolidaySmsReminds.size() == 0) {
            log.info("今日无节日短信问候，时间：" + CommUtil.getCurrentTime());
            return;
        }
        List<User> allUser = userService.queryAllUser();
        if (allUser == null || allUser.size() == 0) {
            log.info("无短信问候用户，时间：" + CommUtil.getCurrentTime());
            return;
        }

        for (HolidaySmsRemind hsr : todayHolidaySmsReminds) {
            /*String content = "【节日短信问候】标题：" + hsr.getRemind_name() + ", 内容："
                             + hsr.getRemind_content();*/
        	String content = hsr.getRemind_content();
            for (User user : allUser) {
                String mobile = user.getMobile();
                if (CommUtil.isBlank(mobile) && mobile.trim().length() != 11) {
                    log.error("【节日短信问候】用户：" + user.getTrue_name() + "手机号为空或有误：" + mobile);
                    continue;
                }
                try {
                    MeilianSmsSender.sendMsg(mobile, content);
                    log.info("【节日短信问候】【短信发送成功】手机号：" + mobile + ", 内容：" + content + ", 时间："
                             + CommUtil.getCurrentTime());
                } catch (Exception e) {
                    log.error("【节日短信问候】【短信发送失败】手机号：" + mobile + ", 内容：" + content + ", 时间："
                              + CommUtil.getCurrentTime());
                }
            }
        }
    }

    //今天的生日短信问候
    private void sendTodayBirthdaySms() {

        List<HolidaySmsRemind> todayBirthDaySmsReminds = holidaySmsRemindService
            .queryTodayBirthDaySmsReminds();
        if (todayBirthDaySmsReminds == null || todayBirthDaySmsReminds.size() == 0) {
            log.info("无生日短信问候，时间：" + CommUtil.getCurrentTime());
            return;
        }
        List<User> todayBirthdayUsers = userService.queryTodayBirthdayUsers();
        if (todayBirthdayUsers == null || todayBirthdayUsers.size() == 0) {
            log.info("今日无生日用户，时间：" + CommUtil.getCurrentTime());
            return;
        }

        for (HolidaySmsRemind hsr : todayBirthDaySmsReminds) {
            /*String content = "【生日短信问候】标题：" + hsr.getRemind_name() + ", 内容："
                             + hsr.getRemind_content();*/
        	String content =  hsr.getRemind_content();
            for (User user : todayBirthdayUsers) {
                String mobile = user.getMobile();
                if (CommUtil.isBlank(mobile) && mobile.trim().length() != 11) {
                    log.info("【生日短信问候】用户：" + user.getTrue_name() + "手机号为空或有误：" + mobile);
                    continue;
                }
                try {
                    MeilianSmsSender.sendMsg(mobile, content);
                    log.info("【生日短信问候】【短信发送成功】手机号：" + mobile + ", 内容：" + content + ", 时间："
                             + CommUtil.getCurrentTime());
                } catch (Exception e) {
                    log.error("【生日短信问候】【短信发送失败】手机号：" + mobile + ", 内容：" + content + ", 时间："
                              + CommUtil.getCurrentTime());
                }
            }
        }
    }
}
