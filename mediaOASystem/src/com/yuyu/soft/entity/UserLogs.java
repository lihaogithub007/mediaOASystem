package com.yuyu.soft.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

@Entity
@Table(name = "tb_user_logs")
public class UserLogs extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User              user;                 //用户

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user_id")
    private User              createUser;           //操作者

    private String            log_info;             //日志内容
    
    private String            dimission_reason;     //离职原因
    
    private Date              dimission_time;		//离职时间
    
    
    
    public String getDimission_reason() {
		return dimission_reason;
	}

	public void setDimission_reason(String dimission_reason) {
		this.dimission_reason = dimission_reason;
	}

	public Date getDimission_time() {
		return dimission_time;
	}

	public void setDimission_time(Date dimission_time) {
		this.dimission_time = dimission_time;
	}

	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public String getLog_info() {
        return log_info;
    }

    public void setLog_info(String log_info) {
        this.log_info = log_info;
    }
}
