package com.yuyu.soft.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

@Entity
@Table(name = "tb_user_contract_renewal")
public class UserContractRenewal extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User              user;                     //用户
    private Date              renewal_sign_time;        //续签合同时间
    private Date              renewal_expiration_time;  //续签合同到期时间
    @Column(precision = 4, scale = 1)
    private BigDecimal        renewal_contract_duration; //续签年限

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getRenewal_sign_time() {
        return renewal_sign_time;
    }

    public void setRenewal_sign_time(Date renewal_sign_time) {
        this.renewal_sign_time = renewal_sign_time;
    }

    public Date getRenewal_expiration_time() {
        return renewal_expiration_time;
    }

    public void setRenewal_expiration_time(Date renewal_expiration_time) {
        this.renewal_expiration_time = renewal_expiration_time;
    }

    public BigDecimal getRenewal_contract_duration() {
        return renewal_contract_duration;
    }

    public void setRenewal_contract_duration(BigDecimal renewal_contract_duration) {
        this.renewal_contract_duration = renewal_contract_duration;
    }

}
