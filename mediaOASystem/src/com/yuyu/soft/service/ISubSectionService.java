package com.yuyu.soft.service;

import java.util.List;

import com.yuyu.soft.entity.SubSection;
import com.yuyu.soft.util.ResultMsg;

/**
 * 子版块相关接口
 *                       
 * @Filename: ISubSectionService.java
 * @Version: 1.0
 *
 */
public interface ISubSectionService {

	/**
	 * 查询某模块下的子模块
	 */
	List<SubSection> querySubSectionUnderSection(Long section_id);

	/**
	 * 验证版块下是否存在子版块名称
	 */
	boolean verify_sub_section_name(Long sub_section_id, Long section_id, String sub_section_name);

	/**
	 * 添加子版块
	 * @param subSection
	 */
	void addSubSection(SubSection subSection);

	/**
	 * 删除子版块
	 */
	ResultMsg delete_save(Long id);
	
	public  void delSubSection(SubSection subSection);

	public void updateSubSection(SubSection subSection);

	public SubSection getSubSection(Long id);

	/**
	 * 查询所有子版块
	 */
	List<SubSection> queryAllSubSection();
	
}
