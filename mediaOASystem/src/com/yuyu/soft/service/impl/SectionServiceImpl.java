package com.yuyu.soft.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.Department;
import com.yuyu.soft.entity.Duty;
import com.yuyu.soft.entity.Section;
import com.yuyu.soft.entity.SubSection;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.ISectionService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 版块管理
 *                       
 * @Filename: SectionServiceImpl.java
 * @Version: 1.0
 *
 */
@Service("sectionService")
public class SectionServiceImpl implements ISectionService {

    @Resource
    private IBaseDao<Section> 	 sectionDao;
    
    @Resource
    private ISectionService	 	 sectionService;
    
    @Resource
    private IDutyService         dutyService;
    @Resource
    private IUserService         userService;
    
    
    
	@Override
	public List<Section> querySection(String hql, Map<String, Object> paramsMap, PagerInfo pager) {
		 if (pager != null) {
	            pager.setRowsCount(sectionDao.count("select count(t) " + hql, paramsMap));
	            return sectionDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
	        } else {
	            return sectionDao.find(hql, paramsMap);
	        }
	}
    
    
	/**
	 * 验证版块名称是否存在
	 */
	public boolean verify_section_name(Long section_id, String section_name){
		boolean flag = true;

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("section_name", section_name);
        StringBuilder s = new StringBuilder();
        s.append("from Section t where t.disabled = false");
        s.append(" and t.section_name =:section_name");
        if (CommUtil.isNotNull(section_id)) {
            s.append(" and t.id !=:section_id");
            paramsMap.put("section_id", section_id);
        }
        List<Section> sections = sectionDao.find(s.toString(), paramsMap);
        if ((sections != null) && (sections.size() > 0)) {
            flag = false;
        }
        return flag;		
	}
    
	/**
	 * 添加版块 
	 */
	public void addSection(Section section){
		this.sectionDao.save(section);
	}

	/**
	 *	逻辑删除版块 
	 */
	public  void delSection(Section section) {
		section = getSection(section.getId());
		section.setDisabled(true);
        updateSection(section);
	}

	/**
	 *	修改版块 
	 */
	public void updateSection(Section section) {
		this.sectionDao.update(section);
	}

	/**
	 * 通过id获取版块
	 */
	public Section getSection(Long id) {
		return this.sectionDao.get(Section.class, id);
	}
	
	/**
	 * 删除版块
	 */
	public ResultMsg delete_save(Long id){
		ResultMsg rmg = delete_save_check(id);
        if (!rmg.getResult()) {
            return rmg;
        }
        delSection(getSection(id));
        return CommUtil.setResultMsgData(rmg, true, "版块删除成功");
	}
	


	private ResultMsg delete_save_check(Long id) {
        if (id == null) {
            return CommUtil.setResultMsgData(null, false, "版块标识为空");
        }
        Section	section = getSection(id);
        if (section == null) {
            return CommUtil.setResultMsgData(null, false, "找不到版块记录");
        }
        if (sectionService.verify_sub_section_list_contains_normal(section.getSubSectionList())) {
            return CommUtil.setResultMsgData(null, false, "该版块下存在子版块，不能删除");
        }
        if (userService.verify_user_list_contains_normal(section.getUserList())) {
            return CommUtil.setResultMsgData(null, false, "该版块下存在用户，不能删除");
        }
        return CommUtil.setResultMsgData(null, true, "校验成功");
    }
	
	/**
     * 校验版块集合是否存在正常子版块(disabled=false)
     * 存在返回true
     */
    public boolean verify_sub_section_list_contains_normal(List<SubSection> list){
    	if (list == null || list.size() == 0) {
            return false;
        }
        for (SubSection subSection : list) {
            if (!subSection.getDisabled()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 查询所有版块
     */
	public List<Section> queryAllSection(){
		return sectionDao
	            .find("from Section t where t.disabled = false order by t.createtime desc");
	}
}
