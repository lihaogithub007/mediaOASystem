package com.yuyu.soft.timer;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("stat_Task")
public class StartTaskTimer {
    protected org.slf4j.Logger log = LoggerFactory.getLogger(StartTaskTimer.class);

    private void execute() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
            log.error("定时任务StartTaskTimer：" + e.getMessage());
        }

    }

}
