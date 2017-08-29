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
import com.yuyu.soft.entity.ResumeWork;
import com.yuyu.soft.service.IResumeService;
import com.yuyu.soft.service.IResumeWorkService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

@Service("resumeWorkService")
public class ResumeWorkServiceImpl implements IResumeWorkService {

    @Resource
    private IBaseDao<ResumeWork> resumeWorkDao;
    @Resource
    private IResumeService       resumeService;

    @Override
    public List<ResumeWork> queryResumeWork(Long resume_id) {
        if (resume_id == null) {
            return new ArrayList<ResumeWork>();
        }
        StringBuilder s = new StringBuilder();
        s.append("from ResumeWork t where t.disabled = false");
        s.append(" and t.resume.id = ").append(resume_id);
        s.append(" order by t.createtime asc");
        List<ResumeWork> resumeWorks = resumeWorkDao.find(s.toString());
        if ((resumeWorks == null) || (resumeWorks.size() == 0)) {
            return new ArrayList<ResumeWork>();
        }
        return resumeWorks;
    }

    @Override
    public List<ResumeWork> queryResumeWork(String hql, Map<String, Object> paramsMap,
                                            PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(resumeWorkDao.count("select count(t) " + hql, paramsMap));
            return resumeWorkDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return resumeWorkDao.find(hql, paramsMap);
        }

    }

    @Override
    public ResumeWork getResumeWork(Long id) {
        return resumeWorkDao.get(ResumeWork.class, id);
    }

    @Override
    public void addResumeWork(ResumeWork resumeWork) {
        this.resumeWorkDao.save(resumeWork);
    }

    @Override
    public void updateResumeWork(ResumeWork resumeWork) {
        this.resumeWorkDao.update(resumeWork);
    }

    @Override
    public void delResumeWork(ResumeWork resumeWork) {
        resumeWork = getResumeWork(resumeWork.getId());
        resumeWork.setDisabled(true);
        updateResumeWork(resumeWork);
        //this.resumeWorkDao.delete(resumeWork);
    }

    @Override
    public ResultMsg resume_work_save(HttpServletRequest request, Long resume_id) {
        String resume_work_id = request.getParameter("resume_work_id");
        WebForm wf = new WebForm();
        ResumeWork resumeWork = null;
        if (CommUtil.isBlank(resume_work_id)) {
            resumeWork = (ResumeWork) wf.toPo(request, ResumeWork.class);
            resumeWork.setCreatetime(new Date());
            resumeWork.setDisabled(false);
        } else {
            ResumeWork dbResumeWork = getResumeWork(Long.valueOf(Long.parseLong(resume_work_id)));
            resumeWork = (ResumeWork) wf.toPo(request, dbResumeWork);
        }

        ResultMsg rmg = resume_work_save_check(resumeWork, resume_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        resume_work_save_init(resumeWork);
        if (CommUtil.isBlank(resume_work_id)) {
            addResumeWork(resumeWork);
        } else {
            updateResumeWork(resumeWork);
        }
        return CommUtil.setResultMsgData(rmg, true, "简历工作经历保存成功");
    }

    //校验信息
    private ResultMsg resume_work_save_check(ResumeWork resumeWork, Long resume_id) {
        if (resumeWork == null) {
            return CommUtil.setResultMsgData(null, false, "简历工作经历对象为空");
        }
        if (resume_id == null) {
            return CommUtil.setResultMsgData(null, false, "简历标识为空");
        }
        Resume resume = resumeService.getResume(resume_id);
        if (resume == null || resume.getId() == null) {
            return CommUtil.setResultMsgData(null, false, "简历对象为空");
        } else {
            resumeWork.setResume(resume);
        }
        return CommUtil.setResultMsgData(null, true, "简历工作经历校验成功");
    }

    //对不符合数据库设计的字段初始化
    private void resume_work_save_init(ResumeWork resumeWork) {
        if (resumeWork == null) {
            return;
        }
        //工作单位
        String work_comp_name = resumeWork.getWork_comp_name();
        if (CommUtil.isNotNull(work_comp_name) && work_comp_name.length() > 50) {
            resumeWork.setWork_comp_name("");
        }
        //岗位
        String work_duty_name = resumeWork.getWork_duty_name();
        if (CommUtil.isNotNull(work_duty_name) && work_duty_name.length() > 50) {
            resumeWork.setWork_duty_name("");
        }
        //工作性质
        Integer work_type = resumeWork.getWork_type();
        if (work_type != null
            && CommUtil.isBlank(Constants.WORK_TYPE_MAP.get(work_type.toString()))) {
            resumeWork.setWork_type(1);
        }
        //证明人
        String voucher_name = resumeWork.getVoucher_name();
        if (CommUtil.isNotNull(voucher_name) && voucher_name.length() > 50) {
            resumeWork.setVoucher_name("");
        }
        //证明人电话
        String voucher_mobile = resumeWork.getVoucher_mobile();
        if (CommUtil.isNotNull(voucher_mobile) && voucher_mobile.length() > 11) {
            resumeWork.setVoucher_mobile("");
        }
    }
}
