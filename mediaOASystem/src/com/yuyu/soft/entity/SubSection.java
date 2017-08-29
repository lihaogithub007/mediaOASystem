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
 * 子版块表
 *
 */
@Entity
@Table(name = "tb_sub_section")
public class SubSection extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long           serialVersionUID          = 1L;

    private String                      sub_section_name;                                               //ziba名称
    
    private String                        remark;														//备注

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    @JsonIgnore
    private Section                  	section;                                                        //部门科组

    @OneToMany(mappedBy = "sub_section", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<User>                  userList                  = new ArrayList<User>();

	public String getSub_section_name() {
		return sub_section_name;
	}

	public void setSub_section_name(String sub_section_name) {
		this.sub_section_name = sub_section_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
    
    

}
