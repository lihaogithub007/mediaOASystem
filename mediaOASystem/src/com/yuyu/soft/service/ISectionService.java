package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.Duty;
import com.yuyu.soft.entity.Section;
import com.yuyu.soft.entity.SubSection;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 版块相关接口
 *                       
 * @Filename: ISectionService.java
 * @Version: 1.0
 *
 */
public interface ISectionService {

	
	/**
	 * 查询版块分页
	 * @param string
	 * @param paramsMap
	 * @param pager
	 * @return
	 */
	List<Section> querySection(String string, Map<String, Object> paramsMap, PagerInfo pager);

	/**
	 * 验证版块名称是否存在
	 */
	boolean verify_section_name(Long section_id, String section_name);
	
	
	/**
	 * 添加版块 
	 */
	void addSection(Section section);

	/**
	 * 删除版块
	 */
	ResultMsg delete_save(Long id);

	/**
	 *	逻辑删除版块 
	 */
	public  void delSection(Section section);

	/**
	 *	修改版块 
	 */
	public void updateSection(Section section);

	/**
	 * 通过id获取版块
	 */
	public Section getSection(Long id);

	/**
     * 校验版块集合是否存在正常子版块(disabled=false)
     * 存在返回true
     */
    public boolean verify_sub_section_list_contains_normal(List<SubSection> list);

    /**
     * 查询所有版块
     */
	List<Section> queryAllSection();
	
}
