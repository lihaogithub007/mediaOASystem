package com.yuyu.soft.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.OutsideIncomeDispatches;
import com.yuyu.soft.service.IAttachmentsService;
import com.yuyu.soft.service.IDispatchUnitService;
import com.yuyu.soft.service.IOutsideIncomeDispatchesService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

@Service("outsideIncomeDispatchesService")
public class OutsideIncomeDispatchesServiceImpl implements IOutsideIncomeDispatchesService {

    @Resource
    private IBaseDao<OutsideIncomeDispatches> outsideIncomeDispatchesDao;
    @Resource
    private IDispatchUnitService              dispatchUnitService;
    @Resource
    private IUserService                      userService;
    @Resource
    private IAttachmentsService               attachmentsService;

    @Override
    public List<OutsideIncomeDispatches> queryAllOutsideIncomeDispatches() {
        StringBuilder s = new StringBuilder();
        s.append("from OutsideIncomeDispatches t where t.disabled = false");
        s.append(" order by t.createtime desc");
        return outsideIncomeDispatchesDao.find(s.toString());
    }

    @Override
    public int getCountForExportExcel(String dispatch_unit_name, String income_dispatches_time) {
        String sql = getCountSQL() + getCommonSQL(dispatch_unit_name, income_dispatches_time);
        return outsideIncomeDispatchesDao.countBySql(sql, null);
    }

    @Override
    public List<Object[]> getOutsideIncomeDispatchesForExportExcel(String dispatch_unit_name,
                                                                   String income_dispatches_time,
                                                                   int beginIndex, int pageSize) {

        String sql = getQueryListSQL() + getCommonSQL(dispatch_unit_name, income_dispatches_time);

        List<Object[]> list = (ArrayList<Object[]>) outsideIncomeDispatchesDao.findBySql(sql, null,
            beginIndex, pageSize);

        return list;
    }

    private String getCountSQL() {
        return "select count(oid.id)";
    }

    private String getQueryListSQL() {
        StringBuffer s = new StringBuffer();
        s.append("select");
        s.append(" oid.dispatch_unit_name");//发文单位
        s.append(" ,oid.year_and_document_number");//年份和文号
        s.append(" ,oid.income_dispatches_time");//收文日期
        s.append(" ,oid.print_and_dispatch_time");//印发日期
        s.append(" ,oid.title");//标题 
        s.append(" ,oid.remark");//备注
        return s.toString();
    }

    private String getCommonSQL(String dispatch_unit_name, String income_dispatches_time) {
        StringBuffer s = new StringBuffer();
        s.append(" from tb_outside_income_dispatches oid");
        s.append(" where oid.disabled = 0");
        if (CommUtil.isNotNull(dispatch_unit_name)) {
            s.append(" and oid.dispatch_unit_name like '%").append(dispatch_unit_name).append("%'");
        }
        if (CommUtil.isNotNull(income_dispatches_time)) {
            s.append(" and oid.income_dispatches_time =  str_to_date('")
                .append(income_dispatches_time).append("','%Y-%m-%d')");
        }
        return s.toString();
    }

    @Override
    public List<OutsideIncomeDispatches> queryOutsideIncomeDispatches(String hql,
                                                                      Map<String, Object> paramsMap,
                                                                      PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(outsideIncomeDispatchesDao
                .count("select count(t) " + hql, paramsMap));
            return outsideIncomeDispatchesDao.find(hql, paramsMap, pager.getStart(),
                pager.getPageSize());
        } else {
            return outsideIncomeDispatchesDao.find(hql, paramsMap);
        }

    }

    @Override
    public OutsideIncomeDispatches getOutsideIncomeDispatches(Long id) {
        return outsideIncomeDispatchesDao.get(OutsideIncomeDispatches.class, id);
    }

    @Override
    public void addOutsideIncomeDispatches(OutsideIncomeDispatches outsideIncomeDispatches) {
        this.outsideIncomeDispatchesDao.save(outsideIncomeDispatches);
    }

    @Override
    public void updateOutsideIncomeDispatches(OutsideIncomeDispatches outsideIncomeDispatches) {
        this.outsideIncomeDispatchesDao.update(outsideIncomeDispatches);
    }

    @Override
    public void delOutsideIncomeDispatches(OutsideIncomeDispatches outsideIncomeDispatches) {
        outsideIncomeDispatches = getOutsideIncomeDispatches(outsideIncomeDispatches.getId());
        outsideIncomeDispatches.setDisabled(true);
        updateOutsideIncomeDispatches(outsideIncomeDispatches);
    }

    @Override
    public void batchDelOutsideIncomeDispatches(List<OutsideIncomeDispatches> list) {
        if (list != null && list.size() > 0) {
            for (OutsideIncomeDispatches oid : list) {
                delOutsideIncomeDispatches(oid);
            }
        }
        this.outsideIncomeDispatchesDao.deleteCollection(list);
    }

    @Override
    public ResultMsg add_save(OutsideIncomeDispatches outsideIncomeDispatches, Long user_id,
                              String dispatch_unit_name) {
        ResultMsg rmg = save_check(outsideIncomeDispatches, user_id, dispatch_unit_name);
        if (!rmg.getResult()) {
            return rmg;
        }
        outsideIncomeDispatches.setDisabled(false);
        outsideIncomeDispatches.setCreatetime(new Date());
        outsideIncomeDispatches.setCreate_user(userService.getUser(user_id));
        addOutsideIncomeDispatches(outsideIncomeDispatches);
        return CommUtil.setResultMsgData(rmg, true, "台外收文保存成功");
    }

    @Override
    public ResultMsg edit_save(OutsideIncomeDispatches outsideIncomeDispatches, Long user_id,
                               String dispatch_unit_name) {
        ResultMsg rmg = save_check(outsideIncomeDispatches, user_id, dispatch_unit_name);
        if (!rmg.getResult()) {
            return rmg;
        }
        outsideIncomeDispatches.setUpdate_time(new Date());
        outsideIncomeDispatches.setUpdate_user(userService.getUser(user_id));
        updateOutsideIncomeDispatches(outsideIncomeDispatches);
        return CommUtil.setResultMsgData(rmg, true, "台外收文编辑保存成功");
    }

    private ResultMsg save_check(OutsideIncomeDispatches outsideIncomeDispatches, Long user_id,
                                 String dispatch_unit_name) {
        if (outsideIncomeDispatches == null) {
            return CommUtil.setResultMsgData(null, false, "台外收文对象为空");
        }
        if (user_id == null) {
            return CommUtil.setResultMsgData(null, false, "登录超时，请重新登录后操作");
        }
        if (CommUtil.isBlank(dispatch_unit_name)) {
            return CommUtil.setResultMsgData(null, false, "发文单位选择其他时发文单位名称不能为空");
        } else if (dispatch_unit_name.trim().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "发文单位名称最多50字符");
        }

        if (CommUtil.isBlank(outsideIncomeDispatches.getYear_and_document_number())) {
            return CommUtil.setResultMsgData(null, false, "年份和文号不能为空");
        } else if (outsideIncomeDispatches.getYear_and_document_number().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "年份和文号最多50字符");
        }
        if (CommUtil.isBlank(outsideIncomeDispatches.getTitle())) {
            return CommUtil.setResultMsgData(null, false, "台外收文标题不能为空");
        } else if (outsideIncomeDispatches.getTitle().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "台外收文标题最多50字符");
        }
        if (outsideIncomeDispatches.getIncome_dispatches_time() == null) {
            return CommUtil.setResultMsgData(null, false, "收文日期不能为空");
        }
        if (outsideIncomeDispatches.getPrint_and_dispatch_time() == null) {
            return CommUtil.setResultMsgData(null, false, "印发日期不能为空");
        }
        ResultMsg rmg = attachmentsService.verify_attachment_ids(
            outsideIncomeDispatches.getAttach_ids(), "上传附件值异常", true);
        if (!rmg.getResult()) {
            return rmg;
        }
        return CommUtil.setResultMsgData(null, true, "台外收文对象校验成功");
    }
}
