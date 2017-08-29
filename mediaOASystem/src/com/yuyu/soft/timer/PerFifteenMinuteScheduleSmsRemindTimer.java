package com.yuyu.soft.timer;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.yuyu.soft.entity.ScheduleSmsRemind;
import com.yuyu.soft.entity.SmsRemindLogs;
import com.yuyu.soft.entity.TimerLogs;
import com.yuyu.soft.service.IScheduleSmsRemindService;
import com.yuyu.soft.service.ISmsRemindLogsService;
import com.yuyu.soft.service.ITimerLogsService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.sms.MeilianSmsSender;

/**
 * 日程安排提醒定时任务
 * 每隔15分钟                      
 * @Filename: PerFifteenMinuteScheduleSmsRemindTimer.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Component("perFifteenMinuteScheduleSmsRemindTimer_task")
public class PerFifteenMinuteScheduleSmsRemindTimer {

    protected static final Log        log = LogFactory
                                              .getLog(PerFifteenMinuteScheduleSmsRemindTimer.class);
    @Resource
    private ITimerLogsService         timerLogsService;
    @Resource
    private IUserService              userService;
    @Resource
    private IScheduleSmsRemindService scheduleSmsRemindService;
    @Resource
    private ISmsRemindLogsService     smsRemindLogsService;

    public void execute() throws IOException {

        String current_time = CommUtil.getCurrentTime();
        log.info("\n【日程安排提醒】当前时间：" + current_time);
        int minute = -1;
        try {
            minute = Integer.parseInt(current_time.substring(14, 16));
        } catch (Exception e) {
            log.error("\n【日程安排提醒】字符串转数字失败");
        }
        if (-1 == minute) {
            return;
        }
        if (minute % 15 == 0) {
            current_time = current_time.substring(0, current_time.length() - 3);
        }
        TimerLogs timerLog = timerLogsService.saveLogs("日程安排提醒定时任务");

        try {
            sendScheduleSms(current_time);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("\n【日程安排提醒】发送失败，时间：" + CommUtil.getCurrentTime());
        }

        timerLogsService.updateLogs(timerLog);
    }

    private void sendScheduleSms(String current_time) {
        List<ScheduleSmsRemind> ssrList = scheduleSmsRemindService
            .queryScheduleSmsRemindForTimerTask(current_time);
        if (ssrList == null || ssrList.size() == 0) {
            log.info("\n【日程安排提醒】无日程安排提醒：" + CommUtil.getCurrentTime());
            return;
        }
        for (ScheduleSmsRemind ssr : ssrList) {
            String mobile = ssr.getUser_mobile();
            String content = ""
            /*+ ssr.getRemind_name() + ", 内容："*/
            + ssr.getRemind_content();
            try {
                if (CommUtil.isBlank(mobile) && mobile.trim().length() != 11) {
                    log.error("【日程安排提醒】用户：" + ssr.getUser().getTrue_name() + "手机号为空或有误：" + mobile);
                    continue;
                }
                boolean flag = MeilianSmsSender.sendMsg(mobile, content);
                log.info("\n【日程安排提醒】【短信发送成功】手机号：" + mobile + ", 内容：" + content + ", 时间："
                         + CommUtil.getCurrentTime());

                //保存到日志表
                SmsRemindLogs smsRemindLogs = new SmsRemindLogs();
                smsRemindLogs.setCreatetime(new Date());
                smsRemindLogs.setDisabled(false);
                smsRemindLogs.setUser(ssr.getUser());
                smsRemindLogs.setLog_name(ssr.getRemind_name());
                smsRemindLogs
                    .setLog_info("【短信提醒】手机号：" + mobile + ", 内容：" + ssr.getRemind_content());
                smsRemindLogs.setLog_result(flag);
                smsRemindLogsService.addSmsRemindLogs(smsRemindLogs);

            } catch (Exception e) {
                e.printStackTrace();
                log.error("【节日短信问候】【短信发送失败】手机号：" + mobile + ", 内容：" + content + ", 时间："
                          + CommUtil.getCurrentTime());
            }
        }
    }
}
