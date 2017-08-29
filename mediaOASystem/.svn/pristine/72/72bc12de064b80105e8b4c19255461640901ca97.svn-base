package com.yuyu.soft.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 部门评奖表
 *                       
 * @Filename: DepartmentAwards.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_department_awards")
public class DepartmentAwards extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private Date              awards_time;            //参评时间
    private String            awards_name;            //奖项名称
    private String            awards_works;           //参评作品

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "awards_leader_user_id")
    @JsonIgnore
    private User              awards_leader_user;     //参评项目负责人ID

    private String            awards_department_ids;  //参加科组(部门/科组ID，多个部门/科组用逗号分隔)
    @Transient
    private String            awards_department_names; //参加科组(显示用)
    private String            awards_user_ids;        //参评人员(用户ID，多个用户用逗号分隔)
    @Transient
    private String            awards_user_names;      //参评人员
    private String            awards_result;          //评奖结果
    @Column(precision = 12, scale = 2)
    private BigDecimal        awards_bonus;           //奖金（元）
    private String            remark;                 //备注

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user_id")
    @JsonIgnore
    private User              create_user;            //创建者

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user_id")
    @JsonIgnore
    private User              update_user;            //修改者

    private Date              update_time;            //修改时间

    public Date getAwards_time() {
        return awards_time;
    }

    public void setAwards_time(Date awards_time) {
        this.awards_time = awards_time;
    }

    public String getAwards_name() {
        return awards_name;
    }

    public void setAwards_name(String awards_name) {
        this.awards_name = awards_name;
    }

    public String getAwards_works() {
        return awards_works;
    }

    public void setAwards_works(String awards_works) {
        this.awards_works = awards_works;
    }

    public User getAwards_leader_user() {
        return awards_leader_user;
    }

    public void setAwards_leader_user(User awards_leader_user) {
        this.awards_leader_user = awards_leader_user;
    }

    public String getAwards_department_ids() {
        return awards_department_ids;
    }

    public void setAwards_department_ids(String awards_department_ids) {
        this.awards_department_ids = awards_department_ids;
    }

    public String getAwards_department_names() {
        return awards_department_names;
    }

    public void setAwards_department_names(String awards_department_names) {
        this.awards_department_names = awards_department_names;
    }

    public String getAwards_user_ids() {
        return awards_user_ids;
    }

    public void setAwards_user_ids(String awards_user_ids) {
        this.awards_user_ids = awards_user_ids;
    }

    public String getAwards_user_names() {
        return awards_user_names;
    }

    public void setAwards_user_names(String awards_user_names) {
        this.awards_user_names = awards_user_names;
    }

    public String getAwards_result() {
        return awards_result;
    }

    public void setAwards_result(String awards_result) {
        this.awards_result = awards_result;
    }

    public BigDecimal getAwards_bonus() {
        return awards_bonus;
    }

    public void setAwards_bonus(BigDecimal awards_bonus) {
        this.awards_bonus = awards_bonus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
