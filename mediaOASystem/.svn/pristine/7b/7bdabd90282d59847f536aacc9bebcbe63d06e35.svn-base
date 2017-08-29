package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.WorkforceManagement;
import com.yuyu.soft.entity.WorkloadStatistics;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 
 * 排班统计
 *                       
 * @Filename: IWorkforceManagementService.java
 * @Version: 1.0
 *
 */
public interface IWorkforceManagementService {



    /**
     * 导入数据保存
     */
	ResultMsg workforce_management_import_save(List<WorkforceManagement> dataList);
	
	/**
	 * 添加排班
	 */
	public void addworkforceManagement(WorkforceManagement workforceManagement);

	
	
	/**
	 * 查询所有排班
	 */
	List<WorkforceManagement> queryAllWorkforceManagement();

	
	/**
	 * 查询排班列表
	 */
	List<Map<String, Object>> queryWorkforceManagementMap(String sql, Map<String, Object> paramsMap,
			PagerInfo pager);

	int getCountForExportExcel(String user_name, String year);

	List<Object[]> getWorkforceManagementForExportExcel(String user_name, String year, int beginIndex, int pageSize);
}
