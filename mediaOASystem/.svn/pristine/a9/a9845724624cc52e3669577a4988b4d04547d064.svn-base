package com.yuyu.soft.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.ScheduleSmsRemind;
import com.yuyu.soft.entity.SmsRemindLogs;
import com.yuyu.soft.service.ISmsRemindLogsService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
@Service("smsRemindLogs")
public class SmsRemindLogsServiceImpl implements ISmsRemindLogsService {

	
	@Resource
    private IBaseDao<SmsRemindLogs> smsRemindLogsDao;
	
	/**
     * 添加短信提醒日志
     */
	@Override
	public void addSmsRemindLogs(SmsRemindLogs smsRemindLogs) {
		this.smsRemindLogsDao.save(smsRemindLogs);
	}

	
    /**
     * 查询设备列表
     */
	public List<SmsRemindLogs> querySmsRemindLogsList(String hql, Map<String, Object> paramsMap, PagerInfo pager){
		if (pager != null) {
            pager.setRowsCount(smsRemindLogsDao.count("select count(t) " + hql, paramsMap));
            return smsRemindLogsDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return smsRemindLogsDao.find(hql, paramsMap);
        }
	}
	
	/**
	 * 根据ID获取提醒记录对象
	 */
	public SmsRemindLogs getSmsRemindLogs(Long id){
		return smsRemindLogsDao.get(SmsRemindLogs.class, id);
		
	}


	@Override
	public int getCountForExportExcel(String true_name, String log_name, String createtime) {
		String sql = getCountSQL() + getCommonSQL(true_name, log_name, createtime);
        return smsRemindLogsDao.countBySql(sql, null);
	}


	private String getCommonSQL(String true_name, String log_name, String createtime) {
		StringBuffer s = new StringBuffer();
        s.append(" FROM `tb_sms_remind_logs` t ");
        s.append(" LEFT JOIN tb_user u on t.user_id = u.id ");
        s.append(" where t.disabled = 0");
        if (CommUtil.isNotNull(true_name)) {
            s.append(" and u.true_name like '%").append(true_name.trim()).append("%'");
        }
        if (CommUtil.isNotNull(log_name)) {
            s.append(" and t.log_name like '%").append(log_name.trim()).append("%'");
        }
        if (CommUtil.isNotNull(createtime)) {
            s.append(" and  DATE_FORMAT(t.createtime,'%Y-%m-%d') = '")
                .append(createtime).append("'");
        }
        s.append(" order by t.createtime desc ");
        return s.toString();
	}


	private String getCountSQL() {
		return "SELECT count(t.id)";
	}


	@Override
	public List<Object[]> getProjectCooperationsForExportExcel(String true_name, String log_name, String createtime,
			int beginIndex, int pageSize) {
		String sql = getQueryListSQL()
                + getCommonSQL(true_name, log_name, createtime);

		List<Object[]> list = (ArrayList<Object[]>) smsRemindLogsDao.findBySql(sql, null, beginIndex,
       pageSize);
		return list;
	}


	private String getQueryListSQL() {
		return "select u.true_name , t.log_name,t.log_info,t.createtime,"
				+ "case t.log_result when 0 then '失败' when 1 then '成功' else '' end ";
	}
}
