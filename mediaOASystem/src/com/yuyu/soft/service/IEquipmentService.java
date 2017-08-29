package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.Department;
import com.yuyu.soft.entity.DispatchUnit;
import com.yuyu.soft.entity.Equipment;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.util.PagerInfo;

/**
 * 设备管理接口
 *                       
 * @Filename: SyscfgService.java
 * @Version: 1.0
 * @Author:lihao
 * @Email: lihao220281@126.com
 *
 */
public interface IEquipmentService {


	/**
     * 查询设备列表
     * @param hql
     * @param paramsMap
     * @param pager
     * @return
     */
	List<Equipment> queryEquipmentList(String hql, Map<String, Object> paramsMap, PagerInfo pager);
    
	
	/**
     * 添加设备
     * @param equipment
     */
    void addEquipment(Equipment equipment);
    
    
    
    /**
     * 删除设备
     * @param equipment
     */
    void deleteEquipment(Equipment equipment);

    /**
     * 修改设备
     * @param equipment
     */
	void editEquipment(Equipment equipment);

	/**
     * 根据Id查找设备信息
     * @param id
     */
	Equipment findEquipmentById(Long id);

	/**
     * 验证设备名称是否存在
     */
	boolean verify_equipment_name(Long equipment_id, String equipment_name);

	
	/**
     * 查询全部设备
     */
	List<Equipment> queryAllEquipment();

	/**
     * 根据ID获取设备对象
     */
	Equipment getEquipment(Long equipment_id);


	/**
	 * 通过设备名称保存
	 * @return id
	 */
	Long addEquipmentByEquipmentName(String equipent_name);

    
    
}
