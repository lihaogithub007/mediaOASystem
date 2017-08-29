package com.yuyu.soft.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuyu.soft.base.annotation.SecurityMapping;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.Section;
import com.yuyu.soft.service.ISectionService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.PageUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 版块管理
 * @Filename: SectionController.java
 * @Version: 1.0
 *
 */
@Controller
public class SectionController {

    @Resource
    private ISectionService sectionService;

    /**
     * 版块列表
     */
    @SecurityMapping(res_name = "版块列表", res_url = "/admin/user/section_list.htm*", res_group = "版块管理")
    @RequestMapping({ "/admin/user/section_list.htm" })
    public ModelAndView user_list(HttpServletRequest request, HttpServletResponse response,
                                  String pageSize, String currentPage, String section_name) {
        ModelAndView mv = new JModelAndView("admin/user/section_list.html", request, response);

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        StringBuilder hql = new StringBuilder();
        hql.append("from Section t where t.disabled = false");
        if (CommUtil.isNotNull(section_name)) {
            hql.append(" and t.section_name like :section_name");
            paramsMap.put("section_name", "%" + section_name.trim() + "%");
        }
        hql.append(" order by t.createtime desc");
        List<Section> list = sectionService.querySection(hql.toString(), paramsMap, pager);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);
        mv.addObject("section_name", section_name);
        mv.addObject("list", list);
        return mv;
    }

    /**
     * ajax 修改版块名称
     */
    @SecurityMapping(res_name = "版块编辑", res_url = "/admin/user/section_ajax_edit_save.htm*", res_group = "版块管理")
    @RequestMapping({ "/admin/user/section_ajax_edit_save.htm" })
    public void section_ajax_edit_save(HttpServletRequest request, HttpServletResponse response,
                                    Long id, String value) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "版块名称修改成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "版块标识为空");
        }
        if (rmg.getResult()) {
            if (!CommUtil.isNotNull(value)) {
                CommUtil.setResultMsgData(rmg, false, "版块名称不能为空");
            } else if (value.trim().length() > 50) {
                CommUtil.setResultMsgData(rmg, false, "版块名称不能超过50汉字");
            }
        }
        if (rmg.getResult()) {
            Section section = sectionService.getSection(id);
            if (section == null) {
                CommUtil.setResultMsgData(rmg, false, "查不到版块记录");
            } else if (!sectionService.verify_section_name(section.getId(), value)) {
                CommUtil.setResultMsgData(rmg, false, "版块名称已存在，不能重复");
            } else {
            	section.setSection_name(value);
                try {
                	sectionService.updateSection(section);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "版块名称修改失败");
                }
            }
        }
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 添加版块
     */
    @SecurityMapping(res_name = "版块添加", res_url = "/admin/user/section_add.htm*", res_group = "版块管理")
    @RequestMapping({ "/admin/user/section_add.htm" })
    public ModelAndView user_add(HttpServletRequest request, HttpServletResponse response,
                                 String pageSize, String currentPage) {
        ModelAndView mv = new JModelAndView("admin/user/section_add.html", request, response);
        mv.addObject("currentPage", currentPage);
        return mv;
    }

    /**
     * 添加版块保存
     */
    @RequestMapping({ "/admin/user/section_add_save.htm" })
    public void section_add_save(HttpServletRequest request, HttpServletResponse response,
                                    String section_name) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "版块保存成功");
        if (!CommUtil.isNotNull(section_name)) {
            CommUtil.setResultMsgData(rmg, false, "版块名称不能为空");
        } else if (section_name.trim().length() > 50) {
            CommUtil.setResultMsgData(rmg, false, "版块名称不能超过50汉字");
        }
        if (rmg.getResult()) {
            if (!sectionService.verify_section_name(null, section_name)) {
                CommUtil.setResultMsgData(rmg, false, "版块名称已存在，不能重复");
            } else {
                Section section = new Section();
                section.setDisabled(false);
                section.setCreatetime(new Date());
                section.setSection_name(section_name);
                try {
                    sectionService.addSection(section);
                } catch (Exception e) {
                    CommUtil.setResultMsgData(rmg, false, "版块保存失败");
                }
            }
        }
        rmg.setData(CommUtil.getURL(request) + "/admin/user/section_list.htm");
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 版块删除(同时删除其下所有子版块)
     */
    @SecurityMapping(res_name = "版块删除", res_url = "/admin/user/section_delete.htm*", res_group = "版块管理")
    @RequestMapping({ "/admin/user/section_delete.htm" })
    public ModelAndView section_delete(HttpServletRequest request, HttpServletResponse response,
                                          Long id, String currentPage) {
        String target_url = CommUtil.getURL(request)
                            + "/admin/user/section_list.htm?currentPage=" + currentPage;
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "版块删除成功");
        try {
            rmg = sectionService.delete_save(id);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, "版块删除失败", request, response);
        }
        if (!rmg.getResult()) {
            return CommUtil.errorPage(target_url, rmg.getMsg(), request, response);
        }
        return CommUtil.successPage(target_url, "版块删除成功", request, response);
    }
}
