package com.yuyu.soft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 附件表
 *                       
 * @Filename: Attachments.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_attachments")
public class Attachments extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private String            attach_table;         //附件所属表(不同表的附件根据表名进行筛选)
    private String            save_file_name;       //附件名
    private String            original_file_name;   //附件原上传名
    private String            ext_name;             //后缀名
    private String            mime_type;            //mime
    private String            save_path;            //保存路径
    private float             file_size;            //文件大小

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User              user;                 //上传附件用户

    public String getAttach_table() {
        return attach_table;
    }

    public void setAttach_table(String attach_table) {
        this.attach_table = attach_table;
    }

    public String getSave_file_name() {
        return save_file_name;
    }

    public void setSave_file_name(String save_file_name) {
        this.save_file_name = save_file_name;
    }

    public String getOriginal_file_name() {
        return original_file_name;
    }

    public void setOriginal_file_name(String original_file_name) {
        this.original_file_name = original_file_name;
    }

    public String getExt_name() {
        return ext_name;
    }

    public void setExt_name(String ext_name) {
        this.ext_name = ext_name;
    }

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public String getSave_path() {
        return save_path;
    }

    public void setSave_path(String save_path) {
        this.save_path = save_path;
    }

    public float getFile_size() {
        return file_size;
    }

    public void setFile_size(float file_size) {
        this.file_size = file_size;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
