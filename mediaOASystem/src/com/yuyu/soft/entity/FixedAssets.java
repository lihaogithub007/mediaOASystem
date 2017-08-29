package com.yuyu.soft.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 固定资产表                      
 * @Filename: FixedAssets.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_fixed_assets")
public class FixedAssets extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private String            equipment_lifetime_number; //设备终生码

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    @JsonIgnore
    private Equipment         equipment;                //设备名称

    private String            equipment_model;          //型号
    private String            serial_number;            //序列号

    @Column(precision = 12, scale = 2)
    private BigDecimal        unit_price;               //单价/台
    private String            manufacturer_name;        //生产厂家

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User              user;                     //使用人

    private String            user_card_number;         //工作证号
    private String            user_company;             //使用部门（只有一个新媒体新闻编辑部）
    private String            equipment_place;          //设备位置
    private String            remark;                   //备注

    public String getEquipment_lifetime_number() {
        return equipment_lifetime_number;
    }

    public void setEquipment_lifetime_number(String equipment_lifetime_number) {
        this.equipment_lifetime_number = equipment_lifetime_number;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public String getEquipment_model() {
        return equipment_model;
    }

    public void setEquipment_model(String equipment_model) {
        this.equipment_model = equipment_model;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public BigDecimal getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(BigDecimal unit_price) {
        this.unit_price = unit_price;
    }

    public String getManufacturer_name() {
        return manufacturer_name;
    }

    public void setManufacturer_name(String manufacturer_name) {
        this.manufacturer_name = manufacturer_name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUser_card_number() {
        return user_card_number;
    }

    public void setUser_card_number(String user_card_number) {
        this.user_card_number = user_card_number;
    }

    public String getUser_company() {
        return user_company;
    }

    public void setUser_company(String user_company) {
        this.user_company = user_company;
    }

    public String getEquipment_place() {
        return equipment_place;
    }

    public void setEquipment_place(String equipment_place) {
        this.equipment_place = equipment_place;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
