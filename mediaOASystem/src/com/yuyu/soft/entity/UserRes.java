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
@Table(name = "tb_user_res")
public class UserRes extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User              user;                 //用户

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_id")
    private Res               res;                  //资源

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Res getRes() {
        return res;
    }

    public void setRes(Res res) {
        this.res = res;
    }
}
