package com.yuyu.soft.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 排班表
 *                       
 * @Filename: ProjectCooperation.java
 * @Version: 1.0
 *
 */
@Entity
@Table(name = "tb_workforce_management")
public class WorkforceManagement extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long           serialVersionUID          = 1L;

    private String              user_name;				  //姓名
    private String              classes;				  //班次
    private Date                work_time;                //日期
    private String              remark;				      //备注
    
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public Date getWork_time() {
		return work_time;
	}
	public void setWork_time(Date work_time) {
		this.work_time = work_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
    

}
