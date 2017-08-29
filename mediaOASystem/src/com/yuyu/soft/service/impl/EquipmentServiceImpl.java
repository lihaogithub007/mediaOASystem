package com.yuyu.soft.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.Department;
import com.yuyu.soft.entity.DispatchUnit;
import com.yuyu.soft.entity.Equipment;
import com.yuyu.soft.service.IEquipmentService;
import com.yuyu.soft.util.CommUtil;
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
@Service("equipmentService")
public class EquipmentServiceImpl implements IEquipmentService {

    
    @Resource
    private IBaseDao<Equipment> equipmentDao;
    
    /**
     * 查询设备列表
     */
	@Override
	public List<Equipment> queryEquipmentList(String hql, Map<String, Object> paramsMap, PagerInfo pager) {
		if (pager != null) {
            pager.setRowsCount(equipmentDao.count("select count(t) " + hql, paramsMap));
            return equipmentDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return equipmentDao.find(hql, paramsMap);
        }
	}

	/**
	 * 添加设备
	 */
	@Override
	public void addEquipment(Equipment equipment) {
		this.equipmentDao.save(equipment);
	}

	/**
	 * 删除设备
	 */
	@Override
	public void deleteEquipment(Equipment equipment) {
		equipment.setDisabled(true);
		editEquipment(equipment);
	}

	
	/**
	 * 修改设备
	 */
	@Override
	public void editEquipment(Equipment equipment) {
		this.equipmentDao.update(equipment);
	}

	
	/**
     * 根据Id查找设备信息
     * @param id
     */
	@Override
	public Equipment findEquipmentById(Long id) {
		return equipmentDao.get(Equipment.class, id);
	}

	/**
     * 验证设备名称是否存在
     */
	@Override
	public boolean verify_equipment_name(Long equipment_id, String equipment_name) {
		boolean flag = true;

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("equipment_name", equipment_name);
		StringBuilder s = new StringBuilder();
		s.append("from Equipment t where t.disabled = false");
		s.append(" and t.equipment_name =:equipment_name");
		if (CommUtil.isNotNull(equipment_id)) {
			s.append(" and t.id !=:equipment_id");
			paramsMap.put("equipment_id", equipment_id);
		}
		List<Equipment> equipmentUnits = equipmentDao.find(s.toString(), paramsMap);
		if ((equipmentUnits != null) && (equipmentUnits.size() > 0)) {
			flag = false;
		}
		return flag;
	}

	/**
     * 查询全部设备
     */
	public List<Equipment> queryAllEquipment() {
		return equipmentDao.
				find("from Equipment t where t.disabled = false order by t.createtime desc");
	}
	
	/**
     * 根据ID获取设备对象
     */
	public Equipment getEquipment(Long equipment_id){
		return equipmentDao.get(Equipment.class, equipment_id);
	}
	
	/**
	 * 通过设备名称保存
	 */
	public Long addEquipmentByEquipmentName(String equipent_name){
		Equipment equipment = new Equipment();
		equipment.setDisabled(false);
		equipment.setCreatetime(new Date());
		equipment.setEquipment_name(equipent_name);
		addEquipment(equipment);
		return equipment.getId();
	}
	
}

	


