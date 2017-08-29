package com.yuyu.soft.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.EventApplyForm;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IAttachmentsService;
import com.yuyu.soft.service.IDepartmentService;
import com.yuyu.soft.service.IDispatchUnitService;
import com.yuyu.soft.service.IEventApplyFormService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

@Service("eventApplyFormService")
public class EventApplyFormServiceImpl implements IEventApplyFormService {

    private static final String BigDecimal = null;
	@Resource
    private IBaseDao<EventApplyForm> eventApplyFormDao;
    @Resource
    private IDispatchUnitService     dispatchUnitService;
    @Resource
    private IUserService             userService;
    @Resource
    private IDepartmentService       departmentService;
    @Resource
    private IAttachmentsService      attachmentsService;

    @Override
    public List<EventApplyForm> queryAllEventApplyForm() {
        StringBuilder s = new StringBuilder();
        s.append("from EventApplyForm t where t.disabled = false");
        s.append(" order by t.createtime desc");
        return eventApplyFormDao.find(s.toString());
    }

    @Override
    public int getCountForExportExcel(String apply_date, String department_id,
                                      Integer cost_company_id, String cost_contract) {
        String sql = getCountSQL()
                     + getCommonSQL(apply_date, department_id, cost_company_id, cost_contract);
        return eventApplyFormDao.countBySql(sql, null);
    }

    @Override
    public List<Object[]> getEventApplyFormForExportExcel(String apply_date, String department_id,
                                                          Integer cost_company_id,
                                                          String cost_contract, int beginIndex,
                                                          int pageSize) {

        String sql = getQueryListSQL()
                     + getCommonSQL(apply_date, department_id, cost_company_id, cost_contract);
        List<Object[]> list = (ArrayList<Object[]>) eventApplyFormDao.findBySql(sql, null,
            beginIndex, pageSize);

        return list;
    }

    private String getCountSQL() {
        return "select count(eaf.id)";
    }

    private String getQueryListSQL() {
        StringBuffer s = new StringBuffer();
        s.append("select eaf.project_name");
        s.append(" ,eaf.apply_date");
        s.append(" ,eaf.project_code");
        s.append(" ,eaf.budget_amount");
        s.append(" ,eaf.apply_user_ids");
        s.append(" ,eaf.apply_department_ids");
        s.append(" ,lu.true_name");
        s.append(" ,eaf.agent_user_name");
        s.append(" ,eaf.cost_company_id");
        s.append(" ,eaf.cost_contract");
        s.append(" ,eaf.cost_contract_amount");
        return s.toString();
    }

    private String getCommonSQL(String apply_date, String department_id, Integer cost_company_id,
                                String cost_contract) {
        StringBuffer s = new StringBuffer();
        s.append(" from tb_event_apply_form eaf");
        s.append(" left join tb_user lu");
        s.append(" on eaf.leader_user_id = lu.id");
        s.append(" where eaf.disabled = 0");
        if (CommUtil.isNotNull(apply_date)) {
            s.append(" and eaf.apply_date =  str_to_date('").append(apply_date)
                .append("','%Y-%m-%d')");
        }
        if (CommUtil.isNotNull(department_id)) {
            s.append(" and (eaf.apply_department_ids like '%").append(department_id).append(",%'");
            s.append(" or eaf.apply_department_ids like '%,").append(department_id).append("%'");
            s.append(" or eaf.apply_department_ids like '").append(department_id).append("')");
        }
        if (CommUtil.isNotNull(cost_company_id)) {
            s.append(" and eaf.cost_company_id =").append(cost_company_id);
        }
        if (CommUtil.isNotNull(cost_contract)) {
            s.append(" and eaf.cost_contract like '%").append(cost_contract).append("%'");
        }
        return s.toString();
    }

    @Override
    public List<EventApplyForm> queryEventApplyForm(String hql, Map<String, Object> paramsMap,
                                                    PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(eventApplyFormDao.count("select count(t) " + hql, paramsMap));
            return eventApplyFormDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return eventApplyFormDao.find(hql, paramsMap);
        }

    }

    @Override
    public EventApplyForm getEventApplyForm(Long id) {
        return eventApplyFormDao.get(EventApplyForm.class, id);
    }

    @Override
    public void addEventApplyForm(EventApplyForm eventApplyForm) {
        this.eventApplyFormDao.save(eventApplyForm);
    }

    @Override
    public void updateEventApplyForm(EventApplyForm eventApplyForm) {
        this.eventApplyFormDao.update(eventApplyForm);
    }

    @Override
    public void delEventApplyForm(EventApplyForm eventApplyForm) {
        eventApplyForm = getEventApplyForm(eventApplyForm.getId());
        eventApplyForm.setDisabled(true);
        updateEventApplyForm(eventApplyForm);
    }

    @Override
    public void batchDelEventApplyForm(List<EventApplyForm> list) {
        if (list != null && list.size() > 0) {
            for (EventApplyForm eaf : list) {
                delEventApplyForm(eaf);
            }
        }
    }

    @Override
    public ResultMsg add_save(EventApplyForm eventApplyForm, Long user_id, Long leader_user_id) {
        ResultMsg rmg = save_check(eventApplyForm, user_id, leader_user_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        eventApplyForm.setDisabled(false);
        eventApplyForm.setCreatetime(new Date());
        eventApplyForm.setCreate_user(userService.getUser(user_id));
        addEventApplyForm(eventApplyForm);
        return CommUtil.setResultMsgData(rmg, true, "事项申请表保存成功");
    }

    @Override
    public ResultMsg edit_save(EventApplyForm eventApplyForm, Long user_id, Long leader_user_id) {
        ResultMsg rmg = save_check(eventApplyForm, user_id, leader_user_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        eventApplyForm.setUpdate_time(new Date());
        eventApplyForm.setUpdate_user(userService.getUser(user_id));
        updateEventApplyForm(eventApplyForm);
        return CommUtil.setResultMsgData(rmg, true, "事项申请表编辑保存成功");
    }

    private ResultMsg save_check(EventApplyForm eventApplyForm, Long user_id, Long leader_user_id) {
        if (eventApplyForm == null) {
            return CommUtil.setResultMsgData(null, false, "事项申请表对象为空");
        }
        if (user_id == null) {
            return CommUtil.setResultMsgData(null, false, "登录超时，请重新登录后操作");
        }
        if (leader_user_id == null) {
            return CommUtil.setResultMsgData(null, false, "负责人标识为空");
        }
        User leader_user = userService.getUser(leader_user_id);
        if (leader_user == null) {
            return CommUtil.setResultMsgData(null, false, "找不到负责人对应用户对象");
        } else {
            eventApplyForm.setLeader_user(leader_user);
        }
        Long cost_company_id = eventApplyForm.getCost_company_id();
        if (cost_company_id == null) {
            return CommUtil.setResultMsgData(null, false, "费用支出公司为空");
        } else if (!(1 == cost_company_id || 2 == cost_company_id || 3 == cost_company_id)) {
            return CommUtil.setResultMsgData(null, false, "费用支出公司值异常");
        }
        ResultMsg rmg = userService.verify_user_ids(eventApplyForm.getApply_user_ids(), "事项申请人值异常",
            true);
        if (!rmg.getResult()) {
            return rmg;
        }
        rmg = departmentService.verify_department_ids(eventApplyForm.getApply_department_ids(),
            "申请科组值异常", true);
        if (!rmg.getResult()) {
            return rmg;
        }
        rmg = attachmentsService.verify_attachment_ids(eventApplyForm.getAttach_ids(), "上传附件值异常",
            true);
        if (!rmg.getResult()) {
            return rmg;
        }
        if (CommUtil.isBlank(eventApplyForm.getProject_name())) {
            return CommUtil.setResultMsgData(null, false, "项目名称不能为空");
        }
        if (eventApplyForm.getProject_name().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "项目名称最多50字符");
        }
        if (CommUtil.isNotNull(eventApplyForm.getProject_code())
            && eventApplyForm.getProject_code().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "编号最多50字符");
        }
        if (CommUtil.isNotNull(eventApplyForm.getAgent_user_name())
            && eventApplyForm.getAgent_user_name().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "经办人最多50字符");
        }
        if (CommUtil.isNotNull(eventApplyForm.getCost_contract())
            && eventApplyForm.getCost_contract().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "费用所属合同最多50字符");
        }
        return CommUtil.setResultMsgData(null, true, "事项申请表对象校验成功");
    }
    
}
