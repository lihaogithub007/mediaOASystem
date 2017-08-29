package com.yuyu.soft.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 资源表                       
 * @Filename: ResGroup.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_res")
public class Res extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private String            res_name;                                   //资源组名称
    private String            res_url;                                    //资源url

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rg_id")
    private ResGroup          resGroup;                                   //资源组

    @OneToMany(mappedBy = "res", fetch = FetchType.LAZY)
    private List<UserRes>     userResList      = new ArrayList<UserRes>(); //用户资源

    @OneToMany(mappedBy = "res", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DutyRes>     dutyResList      = new ArrayList<DutyRes>();

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getRes_url() {
        return res_url;
    }

    public void setRes_url(String res_url) {
        this.res_url = res_url;
    }

    public ResGroup getResGroup() {
        return resGroup;
    }

    public void setResGroup(ResGroup resGroup) {
        this.resGroup = resGroup;
    }

    public List<UserRes> getUserResList() {
        return userResList;
    }

    public void setUserResList(List<UserRes> userResList) {
        this.userResList = userResList;
    }

    public List<DutyRes> getDutyResList() {
        return dutyResList;
    }

    public void setDutyResList(List<DutyRes> dutyResList) {
        this.dutyResList = dutyResList;
    }
}
