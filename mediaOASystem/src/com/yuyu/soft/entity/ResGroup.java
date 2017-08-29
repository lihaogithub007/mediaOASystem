package com.yuyu.soft.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 资源组表                       
 * @Filename: ResGroup.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_res_group")
public class ResGroup extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private String            res_group_name;                         //资源组名称

    @OneToMany(mappedBy = "resGroup", fetch = FetchType.LAZY)
    private List<Res>         resList          = new ArrayList<Res>();

    public String getRes_group_name() {
        return res_group_name;
    }

    public void setRes_group_name(String res_group_name) {
        this.res_group_name = res_group_name;
    }

    public List<Res> getResList() {
        return resList;
    }

    public void setResList(List<Res> resList) {
        this.resList = resList;
    }

}
