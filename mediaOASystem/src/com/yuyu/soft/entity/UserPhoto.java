package com.yuyu.soft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

@Entity
@Table(name = "tb_user_photo")
public class UserPhoto extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User              user;                           //用户

    private String            ID_attach_ids;                  //身份证照
    private String            degree_attach_ids;              //学历证明
    private String            blue_small_two_inch_attach_ids; //蓝底小二寸证件照
    private String            white_small_two_inch_attach_ids; //白底小二寸证件照
    private String            others_attach_ids;              //其他证照：北京市居住证、党员证、记者证等

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getID_attach_ids() {
        return ID_attach_ids;
    }

    public void setID_attach_ids(String iD_attach_ids) {
        ID_attach_ids = iD_attach_ids;
    }

    public String getDegree_attach_ids() {
        return degree_attach_ids;
    }

    public void setDegree_attach_ids(String degree_attach_ids) {
        this.degree_attach_ids = degree_attach_ids;
    }

    public String getBlue_small_two_inch_attach_ids() {
        return blue_small_two_inch_attach_ids;
    }

    public void setBlue_small_two_inch_attach_ids(String blue_small_two_inch_attach_ids) {
        this.blue_small_two_inch_attach_ids = blue_small_two_inch_attach_ids;
    }

    public String getWhite_small_two_inch_attach_ids() {
        return white_small_two_inch_attach_ids;
    }

    public void setWhite_small_two_inch_attach_ids(String white_small_two_inch_attach_ids) {
        this.white_small_two_inch_attach_ids = white_small_two_inch_attach_ids;
    }

    public String getOthers_attach_ids() {
        return others_attach_ids;
    }

    public void setOthers_attach_ids(String others_attach_ids) {
        this.others_attach_ids = others_attach_ids;
    }

}
