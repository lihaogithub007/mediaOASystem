package com.yuyu.soft.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;


/**
 * 发文单位实体类
 * @Filename: TbEquipment.java
 * @Version: 1.0
 * @Author: lihao
 * @Email: lihao220281@126.com
 *
 */
@Entity
@Table(name = "tb_dispatch_unit")
public class DispatchUnit extends CommonEntity {

	private static final long serialVersionUID = 1L;
	
	private String 			  dispatch_unit_name;                          //发文单位名称

	public String getDispatch_unit_name() {
		return dispatch_unit_name;
	}

	public void setDispatch_unit_name(String dispatch_unit_name) {
		this.dispatch_unit_name = dispatch_unit_name;
	}

	@Override
	public String toString() {
		return "DispatchUnit [dispatch_unit_name=" + dispatch_unit_name + "]";
	}
	
	
	
	
}
