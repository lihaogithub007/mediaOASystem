package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.ContractSmsRemind;
import com.yuyu.soft.entity.ScheduleSmsRemind;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 合同到期提醒接口
 *
 */
public interface IContractSmsRemindService {

	/**
     * 定时任务专用
     */
    List<ContractSmsRemind> queryContractSmsRemindForTimerTask();
	
	
	
    /**
     * 合同到期提醒列表
     */
	public List<ContractSmsRemind> queryContractSmsRemind(String hql, Map<String, Object> paramsMap,
                                                   PagerInfo pager);

	/**
     * 查询每个id签合同最大时间
     */
    public List<Object[]> queryContractSmsRemindMaxTime();

	
	/**
     * 添加合同到期提醒保存
     */
	public ResultMsg add_save(ContractSmsRemind contractSmsRemind, Long user_id, Long duty_id);

	
	/**
     * 添加合同到期提醒
     */
	public void addContractSmsRemind(ContractSmsRemind contractSmsRemind);


	/**
	 * 根据ID获取合同到期提醒对象
	 * @param id
	 * @return
	 */
	public ContractSmsRemind getContractSmsRemind(Long id);


	/**
	 * 删除合同到期提醒
	 */
	public void delContractSmsRemind(ContractSmsRemind contractSmsRemind);

	 /**
     * 合同到期提醒编辑保存
     */
	public ResultMsg edit_save(ContractSmsRemind contractSmsRemind, Long user_id, Long duty_id);



	public void updateContractSmsRemind(ContractSmsRemind contractSmsRemind);


}
