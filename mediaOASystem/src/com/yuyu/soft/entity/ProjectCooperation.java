package com.yuyu.soft.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 项目协同表
 *                       
 * @Filename: ProjectCooperation.java
 * @Version: 1.0
 *
 */
@Entity
@Table(name = "tb_project_cooperation")
public class ProjectCooperation extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long           serialVersionUID          = 1L;

    private Date                project_date;                 //时间
    private String              project_name;				  //项目名称
    private String              initiator_name;				  //发起人
    private String              cooperation_name;			  //合作方
    private String              result;				          //结果
    private String              remark;				          //备注
    
	public Date getProject_date() {
		return project_date;
	}
	public void setProject_date(Date project_date) {
		this.project_date = project_date;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getInitiator_name() {
		return initiator_name;
	}
	public void setInitiator_name(String initiator_name) {
		this.initiator_name = initiator_name;
	}
	public String getCooperation_name() {
		return cooperation_name;
	}
	public void setCooperation_name(String cooperation_name) {
		this.cooperation_name = cooperation_name;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
    

}
