package com.yuyu.soft.admin.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuyu.soft.base.annotation.SecurityMapping;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.Duty;
import com.yuyu.soft.entity.Section;
import com.yuyu.soft.entity.SubSection;
import com.yuyu.soft.service.IDepartmentService;
import com.yuyu.soft.service.IDutyResService;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.IResGroupService;
import com.yuyu.soft.service.ISectionService;
import com.yuyu.soft.service.ISubSectionService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.ResultMsg;

/**
 * 子版块管理                      
 * @Filename: SubSectionController.java
 * @Version: 1.0
 */
@Controller
public class SubSectionController {

    @Resource
    private IDepartmentService departmentService;
    @Resource
    private IDutyService       dutyService;
    @Resource
    private IResGroupService   resGroupService;
    @Resource
    private IDutyResService    dutyResService;
    
    @Resource
    private ISubSectionService    subSectionService;
    
    @Resource
    private ISectionService       sectionService;
    

    /**
     * ajax查询某部门下所有子版块
     * reutrn json
     */
    @RequestMapping({ "/admin/sub_section/ajaxQuerySubSectionUnderSection.htm" })
    public void ajaxQuerySubSectionUnderSection(HttpServletRequest request,
                                             HttpServletResponse response, Long section_id) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "获取部门子版块成功");
        try {
            List<SubSection> list = subSectionService.querySubSectionUnderSection(section_id);
            rmg.setData(list);
        } catch (Exception e) {
            CommUtil.setResultMsgData(rmg, false, "获取部门子版块失败");
        }
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * ajax查询某部门下所有子版块
     * reutrn mv
     */
    @RequestMapping({ "/admin/user/ajaxQuerySubSectionData.htm" })
    public ModelAndView ajaxQuerySubSectionData(HttpServletRequest request, HttpServletResponse response,
                                          String currentPage, Long section_id) {
        ModelAndView mv = new JModelAndView("admin/user/sub_section_ajax_data.html", request, response);
        if (section_id != null) {
            mv.addObject("subSections", subSectionService.querySubSectionUnderSection(section_id));
        }
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    /**
     * ajax 修改子版块名称
     */
    @SecurityMapping(res_name = "子版块编辑", res_url = "/admin/user/sub_section_ajax_edit_save.htm*", res_group = "子版块管理")
    @RequestMapping({ "/admin/user/sub_section_ajax_edit_save.htm" })
    public void sub_section_ajax_edit_save(HttpServletRequest request, HttpServletResponse response,
                                    Long id, String value) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "子版块名称修改成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "子版块标识为空");
        }
        if (rmg.getResult()) {
            if (!CommUtil.isNotNull(value)) {
                CommUtil.setResultMsgData(rmg, false, "子版块名称不能为空");
            } else if (value.trim().length() > 50) {
                CommUtil.setResultMsgData(rmg, false, "子版块名称不能超过50汉字");
            }
        }
        if (rmg.getResult()) {
            SubSection sub_section = subSectionService.getSubSection(id);
            if (sub_section == null) {
                CommUtil.setResultMsgData(rmg, false, "查不到子版块记录");
            } else if (sub_section.getSection() == null || sub_section.getSection().getId() == null) {
                CommUtil.setResultMsgData(rmg, false, "查不到版块记录");
            } else if (!subSectionService.verify_sub_section_name(sub_section.getId(), sub_section.getSection().getId(),
                value)) {
                CommUtil.setResultMsgData(rmg, false, "所属版块下已有此子版块，不能重复");
            } else {
            	sub_section.setSub_section_name(value);
                try {
                    subSectionService.updateSubSection(sub_section);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "子版块名称修改失败");
                }
            }
        }
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 添加子版块
     */
    @SecurityMapping(res_name = "子版块添加", res_url = "/admin/user/sub_section_add.htm*", res_group = "子版块管理")
    @RequestMapping({ "/admin/user/sub_section_add.htm" })
    public ModelAndView sub_section_add(HttpServletRequest request, HttpServletResponse response,
                                 String pageSize, String currentPage, Long section_id) {
        ModelAndView mv = new JModelAndView("admin/user/sub_section_add.html", request, response);
        List<Section> sections = sectionService.queryAllSection();
        mv.addObject("sections", sections);
        mv.addObject("section_id", section_id);
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    /**
     * 添加子版块保存
     */
    @RequestMapping({ "/admin/user/sub_section_add_save.htm" })
    public void duty_add_save(HttpServletRequest request, HttpServletResponse response,
                              String currentPage, Long section_id, String sub_section_name) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "子版块保存成功");
        if (!CommUtil.isNotNull(sub_section_name)) {
            CommUtil.setResultMsgData(rmg, false, "子版块名称不能为空");
        } else if (sub_section_name.trim().length() > 50) {
            CommUtil.setResultMsgData(rmg, false, "子版块名称不能超过50汉字");
        }
        if (rmg.getResult() && section_id == null) {
            CommUtil.setResultMsgData(rmg, false, "所属版块标识为空");
        }
        if (rmg.getResult()) {
        	Section section = sectionService.getSection(section_id);
            if (section == null || section.getId() == null) {
                CommUtil.setResultMsgData(rmg, false, "查不到版块记录");
            } else if (!subSectionService.verify_sub_section_name(null, section_id, sub_section_name)) {
                CommUtil.setResultMsgData(rmg, false, "所属版块下已有此子版块，不能重复");
            } else {
            	SubSection subSection = new SubSection();
            	subSection.setDisabled(false);
            	subSection.setCreatetime(new Date());
            	subSection.setSub_section_name(sub_section_name);
            	subSection.setSection(section);
                try {
                	subSectionService.addSubSection(subSection);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "子版块保存失败");
                }
            }
        }
        rmg.setData(CommUtil.getURL(request) + "/admin/user/section_list.htm?currentPage="
                    + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 子版块删除
     */
    @SecurityMapping(res_name = "子版块删除", res_url = "/admin/user/sub_section_delete.htm*", res_group = "子版块管理")
    @RequestMapping({ "/admin/user/sub_section_delete.htm" })
    public ModelAndView sub_section_delete(HttpServletRequest request, HttpServletResponse response,
                                    Long id, String currentPage) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/user/section_list.htm?currentPage=" + currentPage;
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "子版块删除成功");
        try {
            rmg = subSectionService.delete_save(id);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, "子版块删除失败", request, response);
        }
        if (!rmg.getResult()) {
            return CommUtil.errorPage(target_url, rmg.getMsg(), request, response);
        }
        return CommUtil.successPage(target_url, "子版块删除成功", request, response);
    }

    /**
     * 权限编辑
     *//*
    @SecurityMapping(res_name = "权限编辑", res_url = "/admin/user/duty_res.htm*", res_group = "子版块管理")
    @RequestMapping({ "/admin/user/duty_res.htm" })
    public ModelAndView duty_res(HttpServletRequest request, HttpServletResponse response,
                                 String pageSize, String currentPage, Long id) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/user/department_list.htm?currentPage=" + currentPage;

        if (id == null) {
            return CommUtil.errorPage(target_url, "子版块标识为空", request, response);
        }
        Duty duty = this.dutyService.getDuty(id);
        if (duty == null) {
            return CommUtil.errorPage(target_url, "找不到子版块记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/user/duty_res.html", request, response);
        List<Department> departments = departmentService.queryAllDepartment();
        mv.addObject("obj", duty);
        mv.addObject("currentPage", currentPage);
        mv.addObject("rgs", resGroupService.queryAllResGroup());
        mv.addObject("dutyResIds", dutyResService.queryDutyResIds(duty.getId()));
        return mv;
    }

    *//**
     * 权限编辑保存
     *//*
    @RequestMapping({ "/admin/user/duty_res_save.htm" })
    public void duty_res_save(HttpServletRequest request, HttpServletResponse response,
                              String pageSize, String currentPage, Long id, String res_ids) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "子版块权限编辑保存成功");
        try {
            rmg = dutyService.duty_res_save(id, res_ids);
        } catch (Exception e) {
            CommUtil.setResultMsgData(rmg, false, "子版块权限编辑保存失败");
        }
        rmg.setData(CommUtil.getURL(request) + "/admin/user/department_list.htm?currentPage="
                    + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }*/
}
