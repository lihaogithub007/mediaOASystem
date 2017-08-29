package com.yuyu.soft.base.mv;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.HttpInclude;

/**重写ModelAndView
 *                       
 * @Filename: JModelAndView.java
 * @Version: 2.7.0
 * @Author: 范光洲
 * @Email: goodfgz@163.com
 *
 */
public class JModelAndView extends ModelAndView {

    public JModelAndView(String viewName) {
        super.setViewName(viewName);
    }

    public JModelAndView(String viewName, HttpServletRequest request, HttpServletResponse response) {

        super.setViewName(viewName);

        String contextPath = request.getContextPath().equals("/") ? "" : request.getContextPath();
        String webPath = CommUtil.getURL(request);
        String port = ":" + CommUtil.null2Int(Integer.valueOf(request.getServerPort()));

        super.addObject("CommUtil", new CommUtil());
        super.addObject("httpInclude", new HttpInclude(request, response));
        super.addObject("webPath", webPath);
    }
}
