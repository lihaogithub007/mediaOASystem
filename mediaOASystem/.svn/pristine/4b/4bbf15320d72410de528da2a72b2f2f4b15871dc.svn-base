package com.yuyu.soft.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

@Entity
@Table(name = "tb_user_contract")
public class UserContract extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User              user;                   //用户
    private Date              entry_time;             //入职时间
    private Date              report_time;            //入台报道时间
    private Date              positive_time;          //转正时间
    private Integer           contract_type;          //合同类型：1.固定期限劳动合同、2.无固定期限劳动合同、3.实习合同、4.劳务合同
    private Date              first_sign_time;        //合同首次签订时间
    private Date              first_expiration_time;  //合同首次到期时间
    @Column(precision = 4, scale = 1)
    private BigDecimal        first_contract_duration; //首次签订期限
    private String            meal_card_number;       //餐卡卡号
    private String            business_card_number;   //公务卡号

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(Date entry_time) {
        this.entry_time = entry_time;
    }

    public Date getReport_time() {
        return report_time;
    }

    public void setReport_time(Date report_time) {
        this.report_time = report_time;
    }

    public Date getPositive_time() {
        return positive_time;
    }

    public void setPositive_time(Date positive_time) {
        this.positive_time = positive_time;
    }

    public Integer getContract_type() {
        return contract_type;
    }

    public void setContract_type(Integer contract_type) {
        this.contract_type = contract_type;
    }

    public Date getFirst_sign_time() {
        return first_sign_time;
    }

    public void setFirst_sign_time(Date first_sign_time) {
        this.first_sign_time = first_sign_time;
    }

    public Date getFirst_expiration_time() {
        return first_expiration_time;
    }

    public void setFirst_expiration_time(Date first_expiration_time) {
        this.first_expiration_time = first_expiration_time;
    }

    public BigDecimal getFirst_contract_duration() {
        return first_contract_duration;
    }

    public void setFirst_contract_duration(BigDecimal first_contract_duration) {
        this.first_contract_duration = first_contract_duration;
    }

    public String getMeal_card_number() {
        return meal_card_number;
    }

    public void setMeal_card_number(String meal_card_number) {
        this.meal_card_number = meal_card_number;
    }

    public String getBusiness_card_number() {
        return business_card_number;
    }

    public void setBusiness_card_number(String business_card_number) {
        this.business_card_number = business_card_number;
    }

}
