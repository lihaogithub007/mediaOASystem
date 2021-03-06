package com.yuyu.soft.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.WorkloadStatistics;
import com.yuyu.soft.service.IWorkloadStatisticsService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.POIUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

@Service("workloadStatisticsService")
public class WorkloadStatisticsServiceImpl implements IWorkloadStatisticsService {

    @Resource
    private IBaseDao<WorkloadStatistics> workloadStatisticsDao;

    @Override
    public List<WorkloadStatistics> queryWorkloadStatistics(String hql,
                                                            Map<String, Object> paramsMap,
                                                            PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(workloadStatisticsDao.count("select count(t) " + hql, paramsMap));
            return workloadStatisticsDao
                .find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return workloadStatisticsDao.find(hql, paramsMap);
        }

    }

    @Override
    public List<Map<String, Object>> queryWorkloadStatisticsMap(String sql,
                                                                Map<String, Object> paramsMap,
                                                                PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(workloadStatisticsDao.countBySql("select count(t.name) " + sql,
                paramsMap));
            return workloadStatisticsDao.findListMapBySQL("select * " + sql, paramsMap,
                pager.getStart(), pager.getPageSize());
        } else {
            return workloadStatisticsDao.findListMapBySQL("select * " + sql, paramsMap, 0, -1);
        }

    }

    @Override
    public int getCountForExportExcel(String name, String beginTime, String endTime) {
        String sql = getCountSQL() + getCommonSQL(name, beginTime, endTime);
        return workloadStatisticsDao.countBySql(sql, null);
    }

    @Override
    public List<Object[]> getWorkloadStatisticsForExportExcel(String name, String beginTime,
                                                              String endTime, int beginIndex,
                                                              int pageSize) {

        String sql = getQueryListSQL() + getCommonSQL(name, beginTime, endTime);
        List<Object[]> list = (ArrayList<Object[]>) workloadStatisticsDao.findBySql(sql, null,
            beginIndex, pageSize);

        return list;
    }

    @Override
    public Object[] getTotalWorkloadStatisticsForExportExcel(String name, String beginTime,
                                                             String endTime) {
        StringBuffer s = new StringBuffer();
        s.append("select * ");
        s.append("from (");
        s.append(" select sum(t.bright_spot) as bright_spot");
        s.append(" ,sum(t.app_write) as app_write");
        s.append(" ,sum(t.app_send) as app_send");
        s.append(" ,sum(t.fb_write) as fb_write");
        s.append(" ,sum(t.fb_send) as fb_send");
        s.append(" ,sum(t.weibo_write) as weibo_write");
        s.append(" ,sum(t.weibo_send) as weibo_send");
        s.append(" ,sum(t.twt_write) as twt_write");
        s.append(" ,sum(t.twt_send) as twt_send");
        s.append(" ,sum(t.upload_yt_or_tencent) as upload_yt_or_tencent");
        s.append(" ,sum(t.tumblr_send) as tumblr_send");
        s.append(" ,sum(t.WeChat) as WeChat");
        s.append(" ,sum(t.inst) as inst");
        s.append(" ,sum(t.google) as google");
        s.append(" ,sum(t.pinterest) as pinterest");
        s.append(" ,sum(t.total) as total");
        s.append(" from tb_workload_statistics t where t.disabled = false");
        if (CommUtil.isNotNull(name)) {
            s.append(" and t.name like '%").append(name.trim()).append("%'");
        }
        if (CommUtil.isNotNull(beginTime)) {
            s.append(" and date_format(t.log_date,'%Y-%m-%d') >= str_to_date('").append(beginTime)
                .append("','%Y-%m-%d')");
        }
        if (CommUtil.isNotNull(endTime)) {
            s.append(" and date_format(t.log_date,'%Y-%m-%d') <= str_to_date('").append(endTime)
                .append("','%Y-%m-%d')");
        }
        s.append(" order by t.createtime desc");
        s.append(" ) t");
        List<Object[]> list = (ArrayList<Object[]>) workloadStatisticsDao.findBySql(s.toString(),
            null, 0, -1);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    private String getCountSQL() {
        return "select count(t.name)";
    }

    private String getQueryListSQL() {
        StringBuffer s = new StringBuffer();
        s.append("select * ");
        return s.toString();
    }

    private String getCommonSQL(String name, String beginTime, String endTime) {
        StringBuffer s = new StringBuffer();
        s.append("from (");
        s.append(" select name");
        s.append(" ,sum(t.bright_spot) as bright_spot");
        s.append(" ,sum(t.app_write) as app_write");
        s.append(" ,sum(t.app_send) as app_send");
        s.append(" ,sum(t.fb_write) as fb_write");
        s.append(" ,sum(t.fb_send) as fb_send");
        s.append(" ,sum(t.weibo_write) as weibo_write");
        s.append(" ,sum(t.weibo_send) as weibo_send");
        s.append(" ,sum(t.twt_write) as twt_write");
        s.append(" ,sum(t.twt_send) as twt_send");
        s.append(" ,sum(t.upload_yt_or_tencent) as upload_yt_or_tencent");
        s.append(" ,sum(t.tumblr_send) as tumblr_send");
        s.append(" ,sum(t.WeChat) as WeChat");
        s.append(" ,sum(t.inst) as inst");
        s.append(" ,sum(t.google) as google");
        s.append(" ,sum(t.pinterest) as pinterest");
        s.append(" ,sum(t.total) as total");
        s.append(" from tb_workload_statistics t where t.disabled = false");
        if (CommUtil.isNotNull(name)) {
            s.append(" and t.name like '%").append(name.trim()).append("%'");
        }
        if (CommUtil.isNotNull(beginTime)) {
            s.append(" and date_format(t.log_date,'%Y-%m-%d') >= str_to_date('").append(beginTime)
                .append("','%Y-%m-%d')");
        }
        if (CommUtil.isNotNull(endTime)) {
            s.append(" and date_format(t.log_date,'%Y-%m-%d') <= str_to_date('").append(endTime)
                .append("','%Y-%m-%d')");
        }
        s.append(" group by t.name");
        s.append(" order by t.createtime desc");
        s.append(" ) t");
        return s.toString();
    }

    @Override
    public WorkloadStatistics getWorkloadStatistics(Long id) {
        return workloadStatisticsDao.get(WorkloadStatistics.class, id);
    }

    @Override
    public void addWorkloadStatistics(WorkloadStatistics workloadStatistics) {
        this.workloadStatisticsDao.save(workloadStatistics);
    }

    @Override
    public void updateWorkloadStatistics(WorkloadStatistics workloadStatistics) {
        this.workloadStatisticsDao.update(workloadStatistics);
    }

    @Override
    public void delWorkloadStatistics(WorkloadStatistics workloadStatistics) {
        if (workloadStatistics == null || workloadStatistics.getId() == null) {
            return;
        }
        workloadStatistics = getWorkloadStatistics(workloadStatistics.getId());
        workloadStatistics.setDisabled(true);
        updateWorkloadStatistics(workloadStatistics);
    }

    @Override
    public void batchDelWorkloadStatistics(List<WorkloadStatistics> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        for (WorkloadStatistics workloadStatistics : list) {
            delWorkloadStatistics(workloadStatistics);
        }
    }

    private List<WorkloadStatistics> queryWorkloadStatisticsByLogDate(String log_date) {
        StringBuffer s = new StringBuffer();
        s.append("from WorkloadStatistics t where t.disabled = false");
        s.append(" and date_format(t.log_date,'%Y-%m-%d') = str_to_date('").append(log_date)
            .append("','%Y-%m-%d')");
        return workloadStatisticsDao.find(s.toString());
    }

    @Override
    public ResultMsg workload_statistics_import_save(String filePath, String log_date) {
        if (CommUtil.isBlank(log_date)) {
            return CommUtil.setResultMsgData(null, false, "计入时间不能为空");
        }
        Date logDate = CommUtil.formatDate(log_date);
        if (logDate == null) {
            return CommUtil.setResultMsgData(null, false, "计入时间格式不正确");
        }
        List<ArrayList<String>> dataList = POIUtil.getDataFromExcel(filePath);
        if (dataList == null) {
            return CommUtil.setResultMsgData(null, false, "文件不存在或格式不正确");
        }

        batchDelWorkloadStatistics(queryWorkloadStatisticsByLogDate(log_date));

        for (int i = 0; i < dataList.size(); i++) {
            List<String> list = dataList.get(i);
            if (i == 0) {
                if (list == null
                    || list.size() == 0
                    || !("姓名".equals(CommUtil.null2String(list.get(0))) && "亮点".equals(CommUtil
                        .null2String(list.get(1))))) {
                    return CommUtil.setResultMsgData(null, false, "文件内容不符规定");
                }
            } else {
                workload_statistics_add(list, logDate);
            }
        }
        return CommUtil.setResultMsgData(null, true, "导入数据保存成功");
    }

    private void workload_statistics_add(List<String> list, Date logDate) {
        if (list == null || list.size() == 0) {
            return;
        }
        String name = CommUtil.null2String(list.get(0));
        if ("总结".equals(name)) {
            return;
        }
        WorkloadStatistics workloadStatistics = new WorkloadStatistics();
        workloadStatistics.setCreatetime(new Date());
        workloadStatistics.setDisabled(false);
        workloadStatistics.setLog_date(logDate);
        workloadStatistics.setName(name);
        int i = 0;
        int size = list.size();
        BigDecimal compDecimal = BigDecimal.ZERO;
        if (++i < size && CommUtil.null2BigDecimal(list.get(i)).compareTo(compDecimal) > 0) {
            workloadStatistics.setBright_spot(CommUtil.null2BigDecimal(list.get(i)));
        } else {
            workloadStatistics.setBright_spot(null);
        }

        if (++i < size && CommUtil.null2BigDecimal(list.get(i)).compareTo(compDecimal) > 0) {
            workloadStatistics.setApp_write(CommUtil.null2BigDecimal(list.get(i)));
        } else {
            workloadStatistics.setApp_write(null);
        }
        if (++i < size && CommUtil.null2BigDecimal(list.get(i)).compareTo(compDecimal) > 0) {
            workloadStatistics.setApp_send(CommUtil.null2BigDecimal(list.get(i)));
        } else {
            workloadStatistics.setApp_send(null);
        }
        if (++i < size && CommUtil.null2BigDecimal(list.get(i)).compareTo(compDecimal) > 0) {
            workloadStatistics.setFb_write(CommUtil.null2BigDecimal(list.get(i)));
        } else {
            workloadStatistics.setFb_write(null);
        }
        if (++i < size && CommUtil.null2BigDecimal(list.get(i)).compareTo(compDecimal) > 0) {
            workloadStatistics.setFb_send(CommUtil.null2BigDecimal(list.get(i)));
        } else {
            workloadStatistics.setFb_send(null);
        }
        if (++i < size && CommUtil.null2BigDecimal(list.get(i)).compareTo(compDecimal) > 0) {
            workloadStatistics.setWeibo_write(CommUtil.null2BigDecimal(list.get(i)));
        } else {
            workloadStatistics.setWeibo_write(null);
        }
        if (++i < size && CommUtil.null2BigDecimal(list.get(i)).compareTo(compDecimal) > 0) {
            workloadStatistics.setWeibo_send(CommUtil.null2BigDecimal(list.get(i)));
        } else {
            workloadStatistics.setWeibo_send(null);
        }
        if (++i < size && CommUtil.null2BigDecimal(list.get(i)).compareTo(compDecimal) > 0) {
            workloadStatistics.setTwt_write(CommUtil.null2BigDecimal(list.get(i)));
        } else {
            workloadStatistics.setTwt_write(null);
        }
        if (++i < size && CommUtil.null2BigDecimal(list.get(i)).compareTo(compDecimal) > 0) {
            workloadStatistics.setTwt_send(CommUtil.null2BigDecimal(list.get(i)));
        } else {
            workloadStatistics.setTwt_send(null);
        }
        if (++i < size && CommUtil.null2BigDecimal(list.get(i)).compareTo(compDecimal) > 0) {
            workloadStatistics.setUpload_yt_or_tencent(CommUtil.null2BigDecimal(list.get(i)));
        } else {
            workloadStatistics.setUpload_yt_or_tencent(null);
        }
        if (++i < size && CommUtil.null2BigDecimal(list.get(i)).compareTo(compDecimal) > 0) {
            workloadStatistics.setTumblr_send(CommUtil.null2BigDecimal(list.get(i)));
        } else {
            workloadStatistics.setTumblr_send(null);
        }
        if (++i < size && CommUtil.null2BigDecimal(list.get(i)).compareTo(compDecimal) > 0) {
            workloadStatistics.setWeChat(CommUtil.null2BigDecimal(list.get(i)));
        } else {
            workloadStatistics.setWeChat(null);
        }
        if (++i < size && CommUtil.null2BigDecimal(list.get(i)).compareTo(compDecimal) > 0) {
            workloadStatistics.setInst(CommUtil.null2BigDecimal(list.get(i)));
        } else {
            workloadStatistics.setInst(null);
        }
        if (++i < size && CommUtil.null2BigDecimal(list.get(i)).compareTo(compDecimal) > 0) {
            workloadStatistics.setGoogle(CommUtil.null2BigDecimal(list.get(i)));
        } else {
            workloadStatistics.setGoogle(null);
        }
        if (++i < size && CommUtil.null2BigDecimal(list.get(i)).compareTo(compDecimal) > 0) {
            workloadStatistics.setPinterest(CommUtil.null2BigDecimal(list.get(i)));
        } else {
            workloadStatistics.setPinterest(null);
        }
        if (++i < size && CommUtil.null2BigDecimal(list.get(i)).compareTo(compDecimal) > 0) {
            workloadStatistics.setTotal(CommUtil.null2BigDecimal(list.get(i)));
        } else {
            workloadStatistics.setTotal(null);
        }
        if (++i < size) {
            workloadStatistics.setOthers(CommUtil.null2String(list.get(++i)));
        }
        if (++i < size) {
            workloadStatistics.setRemark(CommUtil.null2String(list.get(++i)));
        }
        addWorkloadStatistics(workloadStatistics);
    }
}
