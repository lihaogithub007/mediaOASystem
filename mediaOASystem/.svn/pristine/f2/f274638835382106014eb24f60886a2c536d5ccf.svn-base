package com.yuyu.soft.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

@Entity
@Table(name = "tb_user_schoolaward")
public class UserSchoolaward extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User              user;                 //用户
    private Date              award_date;           //获奖时间
    private String            award_name;           //奖项名称
    private String            award_level;          //奖项等级
    private String            award_unit;           //颁发单位

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_id")
    private Assessment        assessment;           //评优管理

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

}
