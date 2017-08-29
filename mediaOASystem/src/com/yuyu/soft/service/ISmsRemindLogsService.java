package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.DepartmentAwards;
import com.yuyu.soft.entity.ScheduleSmsRemind;
import com.yuyu.soft.entity.SmsRemindLogs;
import com.yuyu.soft.util.PagerInfo;

/**
 * 短信提醒日志接口
 *                       
 * @Filename: IScheduleSmsRemindService.java
 * @Version: 1.0
 * @Author: lihao
 */
public interface ISmsRemindLogsService {
	
	
    /**
     * 添加短信提醒日志
     */
    void addSmsRemindLogs(SmsRemindLogs smsRemindLogs);

    /**
     * 查询设短信提醒日志列表
     */
	 List<SmsRemindLogs> querySmsRemindLogsList(String hql, Map<String, Object> paramsMap, PagerInfo pager);
	 
	 
	/**
	 * 根据ID获取提醒记录对象
	 */
	SmsRemindLogs getSmsRemindLogs(Long id);

	
	
	int getCountForExportExcel(String true_name, String log_name, String createtime);

	List<Object[]> getProjectCooperationsForExportExcel(String true_name, String log_name, String createtime,
			int beginIndex, int pageSize);
}
