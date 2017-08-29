package com.yuyu.soft.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 评优管理                       
 * @Filename: Assessment.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_assessment")
public class Assessment extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long     serialVersionUID    = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonIgnore
    private Department            department;                                            //部门科组
    private String                user_ids;                                              //用户
    private Date                  award_date;                                            //获奖时间
    private String                award_name;                                            //奖项名称
    private String                award_works;                                           //获奖作品
    private String                award_level;                                           //奖项等级
    private String                award_unit;                                            //颁发单位

    @Transient
    private String                user_names;                                            //姓名

    @OneToMany(mappedBy = "assessment", fetch = FetchType.LAZY)
    private List<UserSchoolaward> userSchoolawardList = new ArrayList<UserSchoolaward>();

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(String user_ids) {
        this.user_ids = user_ids;
    }

    public Date getAward_date() {
        return award_date;
    }

    public void setAward_date(Date award_date) {
        this.award_date = award_date;
    }

    public String getAward_name() {
        return award_name;
    }

    public void setAward_name(String award_name) {
        this.award_name = award_name;
    }

    public String getAward_works() {
        return award_works;
    }

    public void setAward_works(String award_works) {
        this.award_works = award_works;
    }

    public String getAward_level() {
        return award_level;
    }

    public void setAward_level(String award_level) {
        this.award_level = award_level;
    }

    public String getAward_unit() {
        return award_unit;
    }

    public void setAward_unit(String award_unit) {
        this.award_unit = award_unit;
    }

    public String getUser_names() {
        return user_names;
    }

    public void setUser_names(String user_names) {
        this.user_names = user_names;
    }

    public List<UserSchoolaward> getUserSchoolawardList() {
        return userSchoolawardList;
    }

    public void setUserSchoolawardList(List<UserSchoolaward> userSchoolawardList) {
        this.userSchoolawardList = userSchoolawardList;
    }
}
