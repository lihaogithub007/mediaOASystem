package com.yuyu.soft.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.Assessment;
import com.yuyu.soft.entity.Department;
import com.yuyu.soft.service.IAssessmentService;
import com.yuyu.soft.service.IDepartmentService;
import com.yuyu.soft.service.IUserSchoolawardService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

@Service("assessmentService")
public class AssessmentServiceImpl implements IAssessmentService {

    @Resource
    private IBaseDao<Assessment>    assessmentDao;
    @Resource
    private IUserService            userService;
    @Resource
    private IDepartmentService      departmentService;
    @Resource
    private IUserSchoolawardService userSchoolawardService;

    @Override
    public List<Assessment> queryAllAssessment() {
        StringBuilder s = new StringBuilder();
        s.append("from Assessment t where t.disabled = false");
        s.append(" order by t.createtime desc");
        return assessmentDao.find(s.toString());
    }

    @Override
    public int getCountForExportExcel(Long department_id, Long user_id, String award_date,
                                      String award_name, String award_works, String award_level,
                                      String award_unit) {
        String sql = getCountSQL()
                     + getCommonSQL(department_id, user_id, award_date, award_name, award_works,
                         award_level, award_unit);
        return assessmentDao.countBySql(sql, null);
    }

    @Override
    public List<Object[]> getAssessmentForExportExcel(Long department_id, Long user_id,
                                                      String award_date, String award_name,
                                                      String award_works, String award_level,
                                                      String award_unit, int beginIndex,
                                                      int pageSize) {

        String sql = getQueryListSQL()
                     + getCommonSQL(department_id, user_id, award_date, award_name, award_works,
                         award_level, award_unit);
        List<Object[]> list = (ArrayList<Object[]>) assessmentDao.findBySql(sql, null, beginIndex,
            pageSize);

        return list;
    }

    private String getCountSQL() {
        return "select count(a.id)";
    }

    private String getQueryListSQL() {
        StringBuffer s = new StringBuffer();
        s.append("select");
        s.append(" d.department_name");
        s.append(" ,a.user_ids");
        s.append(" ,a.award_date");
        s.append(" ,a.award_name");
        s.append(" ,a.award_works");
        s.append(" ,a.award_level");
        s.append(" ,a.award_unit");
        return s.toString();
    }

    private String getCommonSQL(Long department_id, Long user_id, String award_date,
                                String award_name, String award_works, String award_level,
                                String award_unit) {
        StringBuffer s = new StringBuffer();
        s.append(" from tb_assessment a");
        s.append(" left join tb_department d on a.department_id = d.id");
        s.append(" where a.disabled = 0");
        if (department_id != null) {
            s.append(" and d.id = ").append(department_id);
        }
        if (CommUtil.isNotNull(user_id)) {
            s.append(" and (a.user_ids like '%").append(user_id).append(",%'");
            s.append(" or a.user_ids like '%,").append(user_id).append("%'");
            s.append(" or a.user_ids like '").append(user_id).append("')");
        }
        if (CommUtil.isNotNull(award_date)) {
            s.append(" and a.award_date =  str_to_date('").append(award_date)
                .append("','%Y-%m-%d')");
        }
        if (CommUtil.isNotNull(award_name)) {
            s.append(" and a.award_name like '%").append(award_name.trim()).append("%'");
        }
        if (CommUtil.isNotNull(award_works)) {
            s.append(" and a.award_works like '%").append(award_works.trim()).append("%'");
        }
        if (CommUtil.isNotNull(award_level)) {
            s.append(" and a.award_level like '%").append(award_level.trim()).append("%'");
        }
        if (CommUtil.isNotNull(award_unit)) {
            s.append(" and a.award_unit like '%").append(award_unit.trim()).append("%'");
        }
        return s.toString();
    }

    @Override
    public List<Assessment> queryAssessment(String hql, Map<String, Object> paramsMap,
                                            PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(assessmentDao.count("select count(t) " + hql, paramsMap));
            return assessmentDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return assessmentDao.find(hql, paramsMap);
        }

    }

    @Override
    public Assessment getAssessment(Long id) {
        return assessmentDao.get(Assessment.class, id);
    }

    @Override
    public void addAssessment(Assessment assessment) {
        this.assessmentDao.save(assessment);
    }

    @Override
    public void updateAssessment(Assessment assessment) {
        this.assessmentDao.update(assessment);
    }

    @Override
    public void delAssessment(Assessment assessment) {
        assessment = getAssessment(assessment.getId());
        assessment.setDisabled(true);
        updateAssessment(assessment);

        userSchoolawardService.batchDelUserSchoolaward(userSchoolawardService
            .queryUserSchoolawardByAassessment(assessment.getId()));
    }

    @Override
    public ResultMsg add_save(Assessment assessment, Long department_id) {
        ResultMsg rmg = save_check(assessment, department_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        assessment.setDisabled(false);
        assessment.setCreatetime(new Date());
        addAssessment(assessment);

        assessment = getAssessment(assessment.getId());
        rmg = userSchoolawardService.user_schoolaward_save(assessment);
        if (!rmg.getResult()) {
            throw new RuntimeException(rmg.getMsg());
        }
        return CommUtil.setResultMsgData(rmg, true, "评优管理保存成功");
    }

    @Override
    public ResultMsg edit_save(Assessment assessment, Long department_id) {
        ResultMsg rmg = save_check(assessment, department_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        updateAssessment(assessment);

        assessment = getAssessment(assessment.getId());
        rmg = userSchoolawardService.user_schoolaward_save(assessment);
        if (!rmg.getResult()) {
            throw new RuntimeException(rmg.getMsg());
        }
        return CommUtil.setResultMsgData(rmg, true, "评优管理编辑保存成功");
    }

    private ResultMsg save_check(Assessment assessment, Long department_id) {
        if (assessment == null) {
            return CommUtil.setResultMsgData(null, false, "评优管理对象为空");
        }
        if (department_id == null) {
            return CommUtil.setResultMsgData(null, false, "部门科组标识为空");
        }
        Department department = departmentService.getDepartment(department_id);
        if (department == null || department.getId() == null || department.getDisabled()) {
            return CommUtil.setResultMsgData(null, false, "找不到部门科组记录");
        }
        assessment.setDepartment(department);

        ResultMsg rmg = userService.verify_user_ids(assessment.getUser_ids(), department.getId(),
            "评优管理对象用户值异常", true);
        if (!rmg.getResult()) {
            return rmg;
        }
        if (!CommUtil.isNotNull(assessment.getAward_date())) {
            return CommUtil.setResultMsgData(null, false, "获奖时间不能为空");
        }
        if (CommUtil.isBlank(assessment.getAward_name())) {
            return CommUtil.setResultMsgData(null, false, "奖项名称不能为空");
        }
        if (assessment.getAward_name().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "奖项名称最多50字符");
        }
        if (CommUtil.isBlank(assessment.getAward_works())) {
            return CommUtil.setResultMsgData(null, false, "获奖作品不能为空");
        }
        if (assessment.getAward_works().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "获奖作品最多50字符");
        }
        if (CommUtil.isBlank(assessment.getAward_level())) {
            return CommUtil.setResultMsgData(null, false, "奖项等级不能为空");
        }
        if (assessment.getAward_level().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "奖项等级最多50字符");
        }
        if (CommUtil.isBlank(assessment.getAward_unit())) {
            return CommUtil.setResultMsgData(null, false, "颁发单位不能为空");
        }
        if (assessment.getAward_unit().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "颁发单位最多50字符");
        }
        return CommUtil.setResultMsgData(null, true, "评优管理对象校验成功");
    }
}
