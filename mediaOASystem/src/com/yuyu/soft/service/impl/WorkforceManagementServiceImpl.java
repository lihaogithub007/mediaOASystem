package com.yuyu.soft.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.WorkforceManagement;
import com.yuyu.soft.service.IWorkforceManagementService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

@Service("workforceManagementService")
public class WorkforceManagementServiceImpl implements IWorkforceManagementService {

	@Resource
	private IBaseDao<WorkforceManagement> workforceManagementDao;

	/**
	 * 查询所有排班
	 */
	public List<WorkforceManagement> queryAllWorkforceManagement() {
		StringBuilder s = new StringBuilder();
		s.append("from  WorkforceManagement t where t.disabled = false");
		s.append(" order by t.createtime desc");
		return workforceManagementDao.find(s.toString());
	}

	/**
	 * 导入数据保存
	 */
	public ResultMsg workforce_management_import_save(List<WorkforceManagement> dataList) {
		if (null == dataList || dataList.size() == 0) {
			return CommUtil.setResultMsgData(null, false, "导入数据不能为空");
		}
		List<WorkforceManagement> WorkforceManagements = queryAllWorkforceManagement();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		for (WorkforceManagement workforceManagement : dataList) {
			for (WorkforceManagement workforceManagements : WorkforceManagements) {
				String d1 = CommUtil.parseShortDateTime(workforceManagement.getWork_time());
				String d2 = CommUtil.parseShortDateTime(workforceManagements.getWork_time());

				if (d1.equals(d2)) {
					workforceManagements.setDisabled(true);
				}
			}

			workforceManagement.setCreatetime(new Date());
			workforceManagement.setDisabled(false);
			addworkforceManagement(workforceManagement);
		}

		return CommUtil.setResultMsgData(null, true, "导入数据保存成功");
	}

	public void addworkforceManagement(WorkforceManagement workforceManagement) {
		this.workforceManagementDao.save(workforceManagement);
	}

	/**
	 * 查询排班列表
	 */
	public List<Map<String, Object>> queryWorkforceManagementMap(String sql, Map<String, Object> paramsMap,
			PagerInfo pager) {
		if (pager != null) {
            pager.setRowsCount(workforceManagementDao.countBySql("select count(ts.user_name) " + sql,
                paramsMap));
            return workforceManagementDao.findListMapBySQL("select * " + sql, paramsMap,
                pager.getStart(), pager.getPageSize());
        } else {
            return workforceManagementDao.findListMapBySQL("select * " + sql, paramsMap, 0, -1);
        }
	}

	public int getCountForExportExcel(String user_name, String year){
		String sql = getCountSQL() + getCommonSQL(user_name, year);
		return workforceManagementDao.countBySql(sql, null);
	}

	
	public List<Object[]> getWorkforceManagementForExportExcel(String user_name, String year,int beginIndex, int pageSize){
		String sql = getQueryListSQL() + getCommonSQL(user_name, year);
        List<Object[]> list = (ArrayList<Object[]>) workforceManagementDao.findBySql(sql, null,
            beginIndex, pageSize);

        return list;
	}
	
	private String getQueryListSQL() {
		return "select *";
	}

	private String getCommonSQL(String user_name, String year) {
		 StringBuilder sql = new StringBuilder();
	        sql.append("from (");
	        sql.append("select temp.user_name");
	        sql.append(" ,max(CASE temp.month WHEN '").append(year).append("-01' THEN temp.count ELSE 0 END) AS Jan");
	        sql.append(" ,max(CASE temp.month WHEN '").append(year).append("-02' THEN temp.count ELSE 0 END) AS Feb");
	        sql.append(" ,max(CASE temp.month WHEN '").append(year).append("-03' THEN temp.count ELSE 0 END) AS Mar");
	        sql.append(" ,max(CASE temp.month WHEN '").append(year).append("-04' THEN temp.count ELSE 0 END) AS Apr");
	        sql.append(" ,max(CASE temp.month WHEN '").append(year).append("-05' THEN temp.count ELSE 0 END) AS May");
	        sql.append(" ,max(CASE temp.month WHEN '").append(year).append("-06' THEN temp.count ELSE 0 END) AS Jun");
	        sql.append(" ,max(CASE temp.month WHEN '").append(year).append("-07' THEN temp.count ELSE 0 END) AS Jul");
	        sql.append(" ,max(CASE temp.month WHEN '").append(year).append("-08' THEN temp.count ELSE 0 END) AS Aug");
	        sql.append(" ,max(CASE temp.month WHEN '").append(year).append("-09' THEN temp.count ELSE 0 END) AS Sep");
	        sql.append(" ,max(CASE temp.month WHEN '").append(year).append("-10' THEN temp.count ELSE 0 END) AS Oct");
	        sql.append(" ,max(CASE temp.month WHEN '").append(year).append("-11' THEN temp.count ELSE 0 END) AS Nov");
	        sql.append(" ,max(CASE temp.month WHEN '").append(year).append("-12' THEN temp.count ELSE 0 END) AS Dece");
	        sql.append(" from ( ");
	        sql.append(" SELECT t.user_name,count(t.user_name) as count,DATE_FORMAT(t.work_time,'%Y-%m') as month ");
	        sql.append(" from tb_workforce_management t");
	        sql.append(" where t.disabled = false");
	        sql.append(" and DATE_FORMAT(t.work_time,'%Y') = ").append(year);
	        if (CommUtil.isNotNull(user_name)) {
	        	sql.append(" and t.user_name like '%").append(user_name.trim()).append("%'");
	        }
	        sql.append(" group by t.user_name,DATE_FORMAT(t.work_time,'%Y-%m')");
	        sql.append(" )temp group by temp.user_name");
	        sql.append(" ) ts ");
	        return sql.toString();
	}

	private String getCountSQL() {
		return "select count(ts.user_name)";
	}
	
	
	
}
