package com.yuyu.soft.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 版块表
 *                       
 */
@Entity
@Table(name = "tb_section")
public class Section extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long           serialVersionUID          = 1L;

    private String                      section_name;             //版块名称                                     
    
    private String                        remark;				  //备注

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User>                  userList                  = new ArrayList<User>();

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SubSection>                  subSectionList                  = new ArrayList<SubSection>();

	public String getSection_name() {
		return section_name;
	}

	public void setSection_name(String section_name) {
		this.section_name = section_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<SubSection> getSubSectionList() {
		return subSectionList;
	}

	public void setSubSectionList(List<SubSection> subSectionList) {
		this.subSectionList = subSectionList;
	}

    

}
