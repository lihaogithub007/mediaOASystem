package com.yuyu.soft.util;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

public class PageUtil {

    /**
     * 分页校验
     */
    public static String initCurrentPage(String currentPage) {
        if (CommUtil.isBlank(currentPage)) {
            currentPage = "1";
        } else {
            try {
                Integer.parseInt(currentPage);
            } catch (Exception e) {
                currentPage = "1";
            }
        }
        return currentPage;
    }

    public static PagerInfo initPagerInfo(String pageSize, String currentPage) {
        int page_size = Constants.DEFAULT_PAGE_SIZE;
        int page_index = 1;
        try {
            page_size = CommUtil.isBlank(pageSize) ? Constants.DEFAULT_PAGE_SIZE : Integer
                .parseInt(pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            page_index = CommUtil.isBlank(currentPage) ? 1 : Integer.parseInt(currentPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PagerInfo(page_size, page_index);
    }

    public static void savePageInfo2ModelAndView(ModelAndView mv, List list, PagerInfo pagerInfo) {
        mv.addObject("list", list);
        mv.addObject("pageSize", pagerInfo.getPageSize());
        mv.addObject("currentPage", pagerInfo.getPageIndex());
        mv.addObject("totalPage", pagerInfo.getTotalpage());
        mv.addObject("rows", pagerInfo.getRowsCount());
        mv.addObject("gotoPageFormHTML",
            showPageFormHtml(pagerInfo.getPageIndex(), pagerInfo.getTotalpage()));
    }

    public static String showPageFormHtml(int currentPage, int pages) {
        StringBuilder s = new StringBuilder("<ul>");
        if (pages > 0) {
            if (currentPage >= 1) {
                s.append("<li onclick='return gotoPage(1);'><a href='javascript:void(0);'>")
                    .append(currentPage).append("/").append(pages).append("首页</a></li>");
                if (currentPage > 1) {
                    s.append("<li onclick='return gotoPage(").append(currentPage - 1)
                        .append(")'><a href='javascript:void(0);'>上一页</a></li>");
                }
            }
            int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
            if (beginPage <= pages) {
                int i = beginPage;
                for (int j = 0; (i <= pages) && (j < 6); j++) {
                    if (i == currentPage) {
                        s.append("<li class='active'><a href='javascript:void(0);'>").append(i)
                            .append("</a></li>");
                    } else {
                        s.append("<li onclick='return gotoPage(").append(i)
                            .append(")'><a href='javascript:void(0);'>").append(i)
                            .append("</a></li>");
                    }
                    i++;
                }
            }
            if (currentPage <= pages) {
                if (currentPage < pages) {
                    s.append("<li onclick='return gotoPage(").append(currentPage + 1)
                        .append(")'><a href='javascript:void(0);'>下一页</a></li>");
                }
                s.append("<li onclick='return gotoPage(").append(pages)
                    .append(")'><a href='javascript:void(0);'>尾页</a></li>");
            }
        }
        s.append("</ul>");
        return s.toString();
    }
}
