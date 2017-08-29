package com.yuyu.soft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 用户资源表                     
 * @Filename: ResGroup.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_duty_res")
public class DutyRes extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "duty_id")
    private Duty              duty;                 //岗位

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_id")
    private Res               res;                  //资源

    public Duty getDuty() {
        return duty;
    }

    public void setDuty(Duty duty) {
        this.duty = duty;
    }

    public Res getRes() {
        return res;
    }

    public void setRes(Res res) {
        this.res = res;
    }
}
