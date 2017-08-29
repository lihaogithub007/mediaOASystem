package com.yuyu.soft.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 外籍专家信息名录表                     
 * @Filename: ForeignExpert.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_foreign_expert")
public class ForeignExpert extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private String            chinese_name;                    //中文名
    private String            english_name;                    //英文名
    private Integer           sex;                             //性别：1.男 2.女
    private Date              birthday;                        //出生日期
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "duty_id")
    private Duty              duty;                            //职位  
    private String            nationality;                     //国籍
    private String            passport_number;                 //护照号
    private Date              passport_expiration_date;        //护照有效期
    private Date              residence_permit_date;           //居留到期时间
    private String            contract_number;                 //合同号
    private Date              first_sign_time;                 //首次签约
    private Date              first_expiration_time;           //合同终止时间
    private String            foreign_expert_certificate;      //专家证
    @Column(precision = 12, scale = 2)
    private BigDecimal        current_annual_salary;           //目前年薪
    private String            card_number;                     //工作证号
    private Integer           vacation_remain_current_contract; //休假剩余当前合同
    private String            degree_and_major;                //学历专业
    private String            media_work_years;                //来台前媒体领域工作年限
    @Column(precision = 12, scale = 2)
    private BigDecimal        current_month_salary;            //目前月薪
    private String            mobile;                          //手机
    private String            email;                           //邮箱
    private String            address;                         //地址

    public String getChinese_name() {
        return chinese_name;
    }

    public void setChinese_name(String chinese_name) {
        this.chinese_name = chinese_name;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public void setEnglish_name(String english_name) {
        this.english_name = english_name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Duty getDuty() {
        return duty;
    }

    public void setDuty(Duty duty) {
        this.duty = duty;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPassport_number() {
        return passport_number;
    }

    public void setPassport_number(String passport_number) {
        this.passport_number = passport_number;
    }

    public Date getPassport_expiration_date() {
        return passport_expiration_date;
    }

    public void setPassport_expiration_date(Date passport_expiration_date) {
        this.passport_expiration_date = passport_expiration_date;
    }

    public Date getResidence_permit_date() {
        return residence_permit_date;
    }

    public void setResidence_permit_date(Date residence_permit_date) {
        this.residence_permit_date = residence_permit_date;
    }

    public String getContract_number() {
        return contract_number;
    }

    public void setContract_number(String contract_number) {
        this.contract_number = contract_number;
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

    public String getForeign_expert_certificate() {
        return foreign_expert_certificate;
    }

    public void setForeign_expert_certificate(String foreign_expert_certificate) {
        this.foreign_expert_certificate = foreign_expert_certificate;
    }

    public BigDecimal getCurrent_annual_salary() {
        return current_annual_salary;
    }

    public void setCurrent_annual_salary(BigDecimal current_annual_salary) {
        this.current_annual_salary = current_annual_salary;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public Integer getVacation_remain_current_contract() {
        return vacation_remain_current_contract;
    }

    public void setVacation_remain_current_contract(Integer vacation_remain_current_contract) {
        this.vacation_remain_current_contract = vacation_remain_current_contract;
    }

    public String getDegree_and_major() {
        return degree_and_major;
    }

    public void setDegree_and_major(String degree_and_major) {
        this.degree_and_major = degree_and_major;
    }

    public String getMedia_work_years() {
        return media_work_years;
    }

    public void setMedia_work_years(String media_work_years) {
        this.media_work_years = media_work_years;
    }

    public BigDecimal getCurrent_month_salary() {
        return current_month_salary;
    }

    public void setCurrent_month_salary(BigDecimal current_month_salary) {
        this.current_month_salary = current_month_salary;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
