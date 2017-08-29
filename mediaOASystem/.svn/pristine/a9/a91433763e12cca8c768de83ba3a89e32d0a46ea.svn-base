package com.yuyu.soft.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.InsideIncomeDispatches;
import com.yuyu.soft.service.IAttachmentsService;
import com.yuyu.soft.service.IDispatchUnitService;
import com.yuyu.soft.service.IInsideIncomeDispatchesService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

@Service("insideIncomeDispatchesService")
public class InsideIncomeDispatchesServiceImpl implements IInsideIncomeDispatchesService {

    @Resource
    private IBaseDao<InsideIncomeDispatches> insideIncomeDispatchesDao;
    @Resource
    private IDispatchUnitService             dispatchUnitService;
    @Resource
    private IUserService                     userService;
    @Resource
    private IAttachmentsService              attachmentsService;

    @Override
    public List<InsideIncomeDispatches> queryAllInsideIncomeDispatches() {
        StringBuilder s = new StringBuilder();
        s.append("from InsideIncomeDispatches t where t.disabled = false");
        s.append(" order by t.createtime desc");
        return insideIncomeDispatchesDao.find(s.toString());
    }

    @Override
    public int getCountForExportExcel(Long dispatch_unit_id, String print_and_dispatch_time) {
        String sql = getCountSQL() + getCommonSQL(dispatch_unit_id, print_and_dispatch_time);
        return insideIncomeDispatchesDao.countBySql(sql, null);
    }

    @Override
    public List<Object[]> getInsideIncomeDispatchesForExportExcel(Long dispatch_unit_id,
                                                                  String print_and_dispatch_time,
                                                                  int beginIndex, int pageSize) {

        String sql = getQueryListSQL() + getCommonSQL(dispatch_unit_id, print_and_dispatch_time);

        List<Object[]> list = (ArrayList<Object[]>) insideIncomeDispatchesDao.findBySql(sql, null,
            beginIndex, pageSize);

        return list;
    }

    private String getCountSQL() {
        return "select count(iid.id)";
    }

    private String getQueryListSQL() {
        StringBuffer s = new StringBuffer();
        s.append("select");
        s.append(" du.dispatch_unit_name");//发文单位
        s.append(" ,iid.year_and_document_number");//年份和文号
        s.append(" ,iid.print_and_dispatch_time");//印发日期
        s.append(" ,iid.title");//标题 
        s.append(" ,iid.remark");//备注
        return s.toString();
    }

    private String getCommonSQL(Long dispatch_unit_id, String print_and_dispatch_time) {
        StringBuffer s = new StringBuffer();
        s.append(" from tb_inside_income_dispatches iid");
        s.append(" left join tb_dispatch_unit du");
        s.append(" on iid.dispatch_unit_id = du.id");
        s.append(" where iid.disabled = 0");
        if (CommUtil.isNotNull(dispatch_unit_id)) {
            s.append(" and du.id = ").append(dispatch_unit_id);
        }
        if (CommUtil.isNotNull(print_and_dispatch_time)) {
            s.append(" and iid.print_and_dispatch_time =  str_to_date('")
                .append(print_and_dispatch_time).append("','%Y-%m-%d')");
        }
        return s.toString();
    }

    @Override
    public List<InsideIncomeDispatches> queryInsideIncomeDispatches(String hql,
                                                                    Map<String, Object> paramsMap,
                                                                    PagerInfo pager) {
        if (pager != null) {
            pager
                .setRowsCount(insideIncomeDispatchesDao.count("select count(t) " + hql, paramsMap));
            return insideIncomeDispatchesDao.find(hql, paramsMap, pager.getStart(),
                pager.getPageSize());
        } else {
            return insideIncomeDispatchesDao.find(hql, paramsMap);
        }

    }

    @Override
    public InsideIncomeDispatches getInsideIncomeDispatches(Long id) {
        return insideIncomeDispatchesDao.get(InsideIncomeDispatches.class, id);
    }

    @Override
    public void addInsideIncomeDispatches(InsideIncomeDispatches insideIncomeDispatches) {
        this.insideIncomeDispatchesDao.save(insideIncomeDispatches);
    }

    @Override
    public void updateInsideIncomeDispatches(InsideIncomeDispatches insideIncomeDispatches) {
        this.insideIncomeDispatchesDao.update(insideIncomeDispatches);
    }

    @Override
    public void delInsideIncomeDispatches(InsideIncomeDispatches insideIncomeDispatches) {
        insideIncomeDispatches = getInsideIncomeDispatches(insideIncomeDispatches.getId());
        insideIncomeDispatches.setDisabled(true);
        updateInsideIncomeDispatches(insideIncomeDispatches);
    }

    @Override
    public void batchDelInsideIncomeDispatches(List<InsideIncomeDispatches> list) {
        if (list != null && list.size() > 0) {
            for (InsideIncomeDispatches iid : list) {
                delInsideIncomeDispatches(iid);
            }
        }
    }

    @Override
    public ResultMsg add_save(InsideIncomeDispatches insideIncomeDispatches, Long user_id,
                              Long dispatch_unit_id, String dispatch_unit_name) {
        ResultMsg rmg = save_check(insideIncomeDispatches, user_id, dispatch_unit_id,
            dispatch_unit_name);
        if (!rmg.getResult()) {
            return rmg;
        }
        insideIncomeDispatches.setDisabled(false);
        insideIncomeDispatches.setCreatetime(new Date());
        insideIncomeDispatches.setCreate_user(userService.getUser(user_id));
        addInsideIncomeDispatches(insideIncomeDispatches);
        return CommUtil.setResultMsgData(rmg, true, "台内收文保存成功");
    }

    @Override
    public ResultMsg edit_save(InsideIncomeDispatches insideIncomeDispatches, Long user_id,
                               Long dispatch_unit_id, String dispatch_unit_name) {
        ResultMsg rmg = save_check(insideIncomeDispatches, user_id, dispatch_unit_id,
            dispatch_unit_name);
        if (!rmg.getResult()) {
            return rmg;
        }
        insideIncomeDispatches.setUpdate_time(new Date());
        insideIncomeDispatches.setUpdate_user(userService.getUser(user_id));
        updateInsideIncomeDispatches(insideIncomeDispatches);
        return CommUtil.setResultMsgData(rmg, true, "台内收文编辑保存成功");
    }

    private ResultMsg save_check(InsideIncomeDispatches insideIncomeDispatches, Long user_id,
                                 Long dispatch_unit_id, String dispatch_unit_name) {
        if (insideIncomeDispatches == null) {
            return CommUtil.setResultMsgData(null, false, "台内收文对象为空");
        }
        if (user_id == null) {
            return CommUtil.setResultMsgData(null, false, "登录超时，请重新登录后操作");
        }
        if (dispatch_unit_id == null) {
            return CommUtil.setResultMsgData(null, false, "发文单位标识为空");
        } else if (dispatch_unit_id == -1) {
            if (CommUtil.isBlank(dispatch_unit_name)) {
                return CommUtil.setResultMsgData(null, false, "发文单位选择其他时发文单位名称不能为空");
            } else if (dispatch_unit_name.trim().length() > 50) {
                return CommUtil.setResultMsgData(null, false, "发文单位名称最多50字符");
            } else {
                try {
                    dispatch_unit_id = dispatchUnitService.add_save(dispatch_unit_name);
                } catch (Exception e) {
                    return CommUtil.setResultMsgData(null, false, "发文单位保存失败");
                }
            }
        }
        insideIncomeDispatches.setDispatchUnit(dispatchUnitService
            .findDispatchUnitById(dispatch_unit_id));

        if (CommUtil.isBlank(insideIncomeDispatches.getYear_and_document_number())) {
            return CommUtil.setResultMsgData(null, false, "年份和文号不能为空");
        } else if (insideIncomeDispatches.getYear_and_document_number().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "年份和文号最多50字符");
        }
        if (CommUtil.isBlank(insideIncomeDispatches.getTitle())) {
            return CommUtil.setResultMsgData(null, false, "台内收文标题不能为空");
        } else if (insideIncomeDispatches.getTitle().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "台内收文标题最多50字符");
        }
        if (insideIncomeDispatches.getPrint_and_dispatch_time() == null) {
            return CommUtil.setResultMsgData(null, false, "印发日期不能为空");
        }
        ResultMsg rmg = attachmentsService.verify_attachment_ids(
            insideIncomeDispatches.getAttach_ids(), "上传附件值异常", true);
        if (!rmg.getResult()) {
            return rmg;
        }
        return CommUtil.setResultMsgData(null, true, "台内收文对象校验成功");
    }
}
