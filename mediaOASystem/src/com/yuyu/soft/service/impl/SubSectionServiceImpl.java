package com.yuyu.soft.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.Duty;
import com.yuyu.soft.entity.Section;
import com.yuyu.soft.entity.SubSection;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.ISectionService;
import com.yuyu.soft.service.ISubSectionService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 子版块管理
 *                       
 * @Filename: SectionServiceImpl.java
 * @Version: 1.0
 *
 */
@Service("subSectionService")
public class SubSectionServiceImpl implements ISubSectionService {

    @Resource
    private IBaseDao<SubSection> 	 subSectionDao;
    
    @Resource
    private ISectionService	 	 sectionService;
    
    @Resource
    private IDutyService         dutyService;
    @Resource
    private IUserService         userService;
    
    /**
	 * 查询某模块下的子模块
	 */
	public List<SubSection> querySubSectionUnderSection(Long section_id){
		StringBuilder s = new StringBuilder();
        s.append("from SubSection t where t.disabled = false");
        if (CommUtil.isNotNull(section_id)) {
            s.append(" and t.section.id = ").append(section_id);
        }
        s.append(" order by t.createtime desc");
        return subSectionDao.find(s.toString());
	}
	
	/**
	 * 查询所有子版块
	 */
	public List<SubSection> queryAllSubSection(){
		return subSectionDao
	            .find("from SubSection t where t.disabled = false order by t.createtime desc");
	}
	
	public  void delSubSection(SubSection subSection) {
		subSection = getSubSection(subSection.getId());
		subSection.setDisabled(true);
        updateSubSection(subSection);
	}

	public void updateSubSection(SubSection subSection) {
		this.subSectionDao.update(subSection);
	}

	public SubSection getSubSection(Long id) {
		return this.subSectionDao.get(SubSection.class, id);
	}
	
	
	/**
	 * 验证版块下是否存在子版块名称
	 */
	public boolean verify_sub_section_name(Long sub_section_id, Long section_id, String sub_section_name){
		boolean flag = true;

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("sub_section_name", sub_section_name);
        StringBuilder s = new StringBuilder();
        s.append("from SubSection t where t.disabled = false");
        s.append(" and t.sub_section_name =:sub_section_name");
        if (CommUtil.isNotNull(sub_section_id)) {
            s.append(" and t.id !=:sub_section_id");
            paramsMap.put("sub_section_id", sub_section_id);
        }
        if (CommUtil.isNotNull(section_id)) {
            s.append(" and t.section.id =:section_id");
            paramsMap.put("section_id", section_id);
        }

        List<SubSection> subSections = subSectionDao.find(s.toString(), paramsMap);
        if ((subSections != null) && (subSections.size() > 0)) {
            flag = false;
        }
        return flag;
	}
	
	
	/**
	 * 添加子版块
	 * @param subSection
	 */
	public void addSubSection(SubSection subSection){
		this.subSectionDao.save(subSection);
	}
	
	/**
	 * 删除子版块
	 */
	public ResultMsg delete_save(Long id){
		ResultMsg rmg = delete_save_check(id);
        if (!rmg.getResult()) {
            return rmg;
        }
        delSubSection(getSubSection(id));
        return CommUtil.setResultMsgData(rmg, true, "子版块删除成功");
	}
	

	private ResultMsg delete_save_check(Long id) {
        if (id == null) {
            return CommUtil.setResultMsgData(null, false, "子版块标识为空");
        }
        SubSection subSection = getSubSection(id);
        if (subSection == null) {
            return CommUtil.setResultMsgData(null, false, "找不到子版块记录");
        }
        if (userService.verify_user_list_contains_normal(subSection.getUserList())) {
            return CommUtil.setResultMsgData(null, false, "该子版块下存在用户，不能删除");
        }
        return CommUtil.setResultMsgData(null, true, "校验成功");
    }

    
}
