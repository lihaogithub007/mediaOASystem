package com.yuyu.soft.admin.controller;

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
import com.yuyu.soft.base.controller.BaseController;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.UserLogs;
import com.yuyu.soft.service.IUserLogsService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.PageUtil;
import com.yuyu.soft.util.PagerInfo;

/**
 * 用户管理                      
 * @Filename: UserLogsController.java
 * @Version: 1.0
 * @Author: 范光洲
 * @Email: goodfgz@163.com
 *
 */
@Controller
public class UserLogsController extends BaseController {

    @Resource
    private IUserService     userService;
    @Resource
    private IUserLogsService userLogsService;

    @SecurityMapping(res_name = "入离职日志", res_url = "/admin/user/user_logs_list.htm*", res_group = "入离职日志")
    @RequestMapping({ "/admin/user/user_logs_list.htm" })
    public ModelAndView user_list(HttpServletRequest request, HttpServletResponse response,
                                  String pageSize, String currentPage, String true_name,String dimission_time) {

        ModelAndView mv = new JModelAndView("admin/user/user_logs_list.html", request, response);

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        StringBuilder hql = new StringBuilder();
        hql.append("from UserLogs t where t.disabled = false");

        if (CommUtil.isNotNull(true_name)) {
            hql.append(" and t.user.true_name like :true_name ");
            paramsMap.put("true_name", "%" + true_name.trim() + "%");
        }
        if (CommUtil.isNotNull(dimission_time)) {
            hql.append(" and str_to_date(t.dimission_time,'%Y-%m-%d')  = str_to_date('")
                .append(dimission_time).append("','%Y-%m-%d')");
        }
        hql.append(" order by t.createtime desc");

        List<UserLogs> list = userLogsService.queryUserLogs(hql.toString(), paramsMap, pager);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);

        mv.addObject("true_name", true_name);
        mv.addObject("dimission_time", dimission_time);
        mv.addObject("user_dimission_reason_map", Constants.DIMISSION_REASON_MAP);

        return mv;
    }
}
