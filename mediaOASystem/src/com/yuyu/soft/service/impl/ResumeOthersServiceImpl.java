package com.yuyu.soft.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.Resume;
import com.yuyu.soft.entity.ResumeOthers;
import com.yuyu.soft.service.IResumeOthersService;
import com.yuyu.soft.service.IResumeService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

@Service("resumeOthersService")
public class ResumeOthersServiceImpl implements IResumeOthersService {

    @Resource
    private IBaseDao<ResumeOthers> resumeOthersDao;
    @Resource
    private IResumeService         resumeService;

    @Override
    public List<ResumeOthers> queryResumeOthers(Long resume_id) {
        if (resume_id == null) {
            return new ArrayList<ResumeOthers>();
        }
        StringBuilder s = new StringBuilder();
        s.append("from ResumeOthers t where t.disabled = false");
        s.append(" and t.resume.id = ").append(resume_id);
        s.append(" order by t.createtime asc");
        List<ResumeOthers> resumeOtherss = resumeOthersDao.find(s.toString());
        if ((resumeOtherss == null) || (resumeOtherss.size() == 0)) {
            return new ArrayList<ResumeOthers>();
        }
        return resumeOtherss;
    }

    @Override
    public List<ResumeOthers> queryResumeOthers(String hql, Map<String, Object> paramsMap,
                                                PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(resumeOthersDao.count("select count(t) " + hql, paramsMap));
            return resumeOthersDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return resumeOthersDao.find(hql, paramsMap);
        }

    }

    @Override
    public ResumeOthers getResumeOthers(Long id) {
        return resumeOthersDao.get(ResumeOthers.class, id);
    }

    @Override
    public void addResumeOthers(ResumeOthers resumeOthers) {
        this.resumeOthersDao.save(resumeOthers);
    }

    @Override
    public void updateResumeOthers(ResumeOthers resumeOthers) {
        this.resumeOthersDao.update(resumeOthers);
    }

    @Override
    public void delResumeOthers(ResumeOthers resumeOthers) {
        resumeOthers = getResumeOthers(resumeOthers.getId());
        resumeOthers.setDisabled(true);
        updateResumeOthers(resumeOthers);
        //this.resumeOthersDao.delete(resumeOthers);
    }

    @Override
    public ResultMsg resume_others_save(HttpServletRequest request, Long resume_id) {
        String resume_others_id = request.getParameter("resume_others_id");
        WebForm wf = new WebForm();
        ResumeOthers resumeOthers = null;

        List<ResumeOthers> tempList = queryResumeOthers(resume_id);
        if (tempList.size() == 0) {
            resumeOthers = (ResumeOthers) wf.toPo(request, ResumeOthers.class);
            resumeOthers.setCreatetime(new Date());
            resumeOthers.setDisabled(false);
        } else if (tempList.size() == 1) {
            if (CommUtil.isBlank(resume_others_id)) {
                ResumeOthers dbResumeOthers = tempList.get(0);
                resumeOthers = (ResumeOthers) wf.toPo(request, dbResumeOthers);
            } else {
                ResumeOthers dbResumeOthers = getResumeOthers(Long.valueOf(Long
                    .parseLong(resume_others_id)));
                resumeOthers = (ResumeOthers) wf.toPo(request, dbResumeOthers);
            }
        } else {
            return CommUtil.setResultMsgData(null, false, "简历其他信息存在多条");
        }

        ResultMsg rmg = resume_others_save_check(resumeOthers, resume_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        resume_others_save_init(resumeOthers);
        if (tempList.size() == 0) {
            addResumeOthers(resumeOthers);
            ResumeOthers dbResumeOthers = getResumeOthers(resumeOthers.getId());
            Resume resume = resumeService.getResume(resumeOthers.getResume().getId());
            resume.setResumeOthers(dbResumeOthers);
        } else if (tempList.size() == 1) {
            updateResumeOthers(resumeOthers);
        }
        return CommUtil.setResultMsgData(rmg, true, "简历其他信息保存成功");
    }

    //校验信息
    private ResultMsg resume_others_save_check(ResumeOthers resumeOthers, Long resume_id) {
        if (resumeOthers == null) {
            return CommUtil.setResultMsgData(null, false, "简历其他信息对象为空");
        }
        if (resume_id == null) {
            return CommUtil.setResultMsgData(null, false, "简历标识为空");
        }
        Resume resume = resumeService.getResume(resume_id);
        if (resume == null || resume.getId() == null) {
            return CommUtil.setResultMsgData(null, false, "简历对象为空");
        } else {
            resumeOthers.setResume(resume);
        }
        return CommUtil.setResultMsgData(null, true, "简历其他信息校验成功");
    }

    //对不符合数据库设计的字段初始化
    private void resume_others_save_init(ResumeOthers resumeOthers) {
        if (resumeOthers == null) {
            return;
        }
        //何时何地受到何种奖励
        String reward_name = resumeOthers.getReward_name();
        if (CommUtil.isNotNull(reward_name) && reward_name.length() > 50) {
            resumeOthers.setReward_name("");
        }
        //何时何地受到何种处分
        String punishment_name = resumeOthers.getPunishment_name();
        if (CommUtil.isNotNull(punishment_name) && punishment_name.length() > 50) {
            resumeOthers.setPunishment_name("");
        }
        //离职原因
        String dimission_reason = resumeOthers.getDimission_reason();
        if (CommUtil.isNotNull(dimission_reason)
            && CommUtil.isBlank(Constants.DIMISSION_REASON_MAP.get(dimission_reason))) {
            resumeOthers.setDimission_reason("");
        }
        //与原单位劳动合同情况
        String contract_status_with_old_unit = resumeOthers.getContract_status_with_old_unit();
        if (CommUtil.isNotNull(contract_status_with_old_unit)
            && contract_status_with_old_unit.length() > 50) {
            resumeOthers.setContract_status_with_old_unit("");
        }
        //有无竞业条例
        Integer has_non_competition = resumeOthers.getHas_non_competition();
        if (has_non_competition != null
            && CommUtil.isBlank(Constants.HAVE_OR_NOT_MAP.get(has_non_competition.toString()))) {
            resumeOthers.setHas_non_competition(null);
        }
        //应聘原因
        String apply_reason = resumeOthers.getApply_reason();
        if (CommUtil.isNotNull(apply_reason) && apply_reason.length() > 50) {
            resumeOthers.setApply_reason("");
        }

        //有无亲属在本单位任职
        Integer has_relatives_in_unit = resumeOthers.getHas_relatives_in_unit();
        if (has_relatives_in_unit != null
            && CommUtil.isBlank(Constants.HAVE_OR_NOT_MAP.get(has_relatives_in_unit.toString()))) {
            resumeOthers.setHas_relatives_in_unit(null);
        }
        //如未能被申请岗位录用，有无其他意向岗位
        Integer accept_other_positions = resumeOthers.getAccept_other_positions();
        if (accept_other_positions != null
            && CommUtil.isBlank(Constants.HAVE_OR_NOT_MAP.get(accept_other_positions.toString()))) {
            resumeOthers.setAccept_other_positions(null);
        }
        //是否接受单位调剂
        Integer accept_unit_adjustment = resumeOthers.getAccept_unit_adjustment();
        if (accept_unit_adjustment != null
            && CommUtil.isBlank(Constants.IS_OR_NOT_MAP.get(accept_unit_adjustment.toString()))) {
            resumeOthers.setAccept_unit_adjustment(null);
        }
        //个人评价
        String self_evaluation = resumeOthers.getSelf_evaluation();
        if (CommUtil.isNotNull(self_evaluation) && self_evaluation.length() > 50) {
            resumeOthers.setSelf_evaluation("");
        }
        //其他补充说明或其它个人特别的期望
        String supplement_instruction = resumeOthers.getSupplement_instruction();
        if (CommUtil.isNotNull(supplement_instruction) && supplement_instruction.length() > 50) {
            resumeOthers.setSupplement_instruction("");
        }
    }
}
