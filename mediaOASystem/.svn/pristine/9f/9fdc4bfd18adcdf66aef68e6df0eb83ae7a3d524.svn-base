package com.yuyu.soft.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 台内发文表
 *                       
 * @Filename: InsideIncomeDispatches.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_inside_income_dispatches")
public class InsideIncomeDispatches extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispatch_unit_id")
    @JsonIgnore
    private DispatchUnit      dispatchUnit;            //发文单位  

    private String            year_and_document_number; //年份和文号 
    private String            title;                   //标题
    private Date              print_and_dispatch_time; //印发日期
    private Date              income_dispatches_time;  //收文日期
    private String            remark;                  //备注
    private String            attach_ids;              //上传附件(多个附件用逗号分隔)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user_id")
    @JsonIgnore
    private User              create_user;             //创建者

    public Date getIncome_dispatches_time() {
		return income_dispatches_time;
	}

	public void setIncome_dispatches_time(Date income_dispatches_time) {
		this.income_dispatches_time = income_dispatches_time;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user_id")
    @JsonIgnore
    private User              update_user;             //修改者

    private Date              update_time;             //修改时间

    public DispatchUnit getDispatchUnit() {
        return dispatchUnit;
    }

    public void setDispatchUnit(DispatchUnit dispatchUnit) {
        this.dispatchUnit = dispatchUnit;
    }

    public String getYear_and_document_number() {
        return year_and_document_number;
    }

    public void setYear_and_document_number(String year_and_document_number) {
        this.year_and_document_number = year_and_document_number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPrint_and_dispatch_time() {
        return print_and_dispatch_time;
    }

    public void setPrint_and_dispatch_time(Date print_and_dispatch_time) {
        this.print_and_dispatch_time = print_and_dispatch_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttach_ids() {
        return attach_ids;
    }

    public void setAttach_ids(String attach_ids) {
        this.attach_ids = attach_ids;
    }

    public User getCreate_user() {
        return create_user;
    }

    public void setCreate_user(User create_user) {
        this.create_user = create_user;
    }

    public User getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(User update_user) {
        this.update_user = update_user;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
