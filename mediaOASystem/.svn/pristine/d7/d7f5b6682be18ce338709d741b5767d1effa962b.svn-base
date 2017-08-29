package com.yuyu.soft.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 设备实体类
 * @Filename: TbEquipment.java
 * @Version: 1.0
 * @Author: lihao
 * @Email: lihao220281@126.com
 *
 */
@Entity
@Table(name = "tb_equipment")
public class Equipment extends CommonEntity{
	
	
	private static final long serialVersionUID = 1L;

	private String 			  equipment_name;                          //设备名称
	
	@OneToMany(mappedBy = "equipment", fetch = FetchType.LAZY)
	private List<FixedAssets> fixedAssetsList = new ArrayList<FixedAssets>(); // 固定资产


	public List<FixedAssets> getFixedAssetsList() {
		return fixedAssetsList;
	}

	public void setFixedAssetsList(List<FixedAssets> fixedAssetsList) {
		this.fixedAssetsList = fixedAssetsList;
	}

	public String getEquipment_name() {
		return equipment_name;
	}

	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}

	@Override
	public String toString() {
		return "TbEquipment [equipment_name=" + equipment_name + "]";
	}
	
	
}
