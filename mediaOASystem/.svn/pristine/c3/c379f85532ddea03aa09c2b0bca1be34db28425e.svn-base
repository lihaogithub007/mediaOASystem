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
import com.yuyu.soft.entity.ResumeFamilyMember;
import com.yuyu.soft.service.IResumeFamilyMemberService;
import com.yuyu.soft.service.IResumeService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;

@Service("resumeFamilyMemberService")
public class ResumeFamilyMemberServiceImpl implements IResumeFamilyMemberService {

    @Resource
    private IBaseDao<ResumeFamilyMember> resumeFamilyMemberDao;
    @Resource
    private IResumeService               resumeService;

    @Override
    public List<ResumeFamilyMember> queryResumeFamilyMember(Long resume_id) {
        if (resume_id == null) {
            return new ArrayList<ResumeFamilyMember>();
        }
        StringBuilder s = new StringBuilder();
        s.append("from ResumeFamilyMember t where t.disabled = false");
        s.append(" and t.resume.id = ").append(resume_id);
        s.append(" order by t.createtime asc");
        List<ResumeFamilyMember> resumeFamilyMembers = resumeFamilyMemberDao.find(s.toString());
        if ((resumeFamilyMembers == null) || (resumeFamilyMembers.size() == 0)) {
            return new ArrayList<ResumeFamilyMember>();
        }
        return resumeFamilyMembers;
    }

    @Override
    public List<ResumeFamilyMember> queryResumeFamilyMember(String hql,
                                                            Map<String, Object> paramsMap,
                                                            PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(resumeFamilyMemberDao.count("select count(t) " + hql, paramsMap));
            return resumeFamilyMemberDao
                .find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return resumeFamilyMemberDao.find(hql, paramsMap);
        }

    }

    @Override
    public ResumeFamilyMember getResumeFamilyMember(Long id) {
        return resumeFamilyMemberDao.get(ResumeFamilyMember.class, id);
    }

    @Override
    public void addResumeFamilyMember(ResumeFamilyMember resumeFamilyMember) {
        this.resumeFamilyMemberDao.save(resumeFamilyMember);
    }

    @Override
    public void updateResumeFamilyMember(ResumeFamilyMember resumeFamilyMember) {
        this.resumeFamilyMemberDao.update(resumeFamilyMember);
    }

    @Override
    public void delResumeFamilyMember(ResumeFamilyMember resumeFamilyMember) {
        resumeFamilyMember = getResumeFamilyMember(resumeFamilyMember.getId());
        resumeFamilyMember.setDisabled(true);
        updateResumeFamilyMember(resumeFamilyMember);
        //this.resumeFamilyMemberDao.delete(resumeFamilyMember);
    }

    @Override
    public ResultMsg resume_family_member_save(HttpServletRequest request, Long resume_id) {
        String resume_family_member_id = request.getParameter("resume_family_member_id");
        WebForm wf = new WebForm();
        ResumeFamilyMember resumeFamilyMember = null;
        if (CommUtil.isBlank(resume_family_member_id)) {
            resumeFamilyMember = (ResumeFamilyMember) wf.toPo(request, ResumeFamilyMember.class);
            resumeFamilyMember.setCreatetime(new Date());
            resumeFamilyMember.setDisabled(false);
        } else {
            ResumeFamilyMember dbResumeFamilyMember = getResumeFamilyMember(Long.valueOf(Long
                .parseLong(resume_family_member_id)));
            resumeFamilyMember = (ResumeFamilyMember) wf.toPo(request, dbResumeFamilyMember);
        }

        ResultMsg rmg = resume_family_member_save_check(resumeFamilyMember, resume_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        resume_family_member_save_init(resumeFamilyMember);
        if (CommUtil.isBlank(resume_family_member_id)) {
            addResumeFamilyMember(resumeFamilyMember);
        } else {
            updateResumeFamilyMember(resumeFamilyMember);
        }
        return CommUtil.setResultMsgData(rmg, true, "简历家庭成员保存成功");
    }

    //校验信息
    private ResultMsg resume_family_member_save_check(ResumeFamilyMember resumeFamilyMember,
                                                      Long resume_id) {
        if (resumeFamilyMember == null) {
            return CommUtil.setResultMsgData(null, false, "简历家庭成员对象为空");
        }
        if (resume_id == null) {
            return CommUtil.setResultMsgData(null, false, "简历标识为空");
        }
        Resume resume = resumeService.getResume(resume_id);
        if (resume == null || resume.getId() == null) {
            return CommUtil.setResultMsgData(null, false, "简历对象为空");
        } else {
            resumeFamilyMember.setResume(resume);
        }
        return CommUtil.setResultMsgData(null, true, "简历家庭成员校验成功");
    }

    //对不符合数据库设计的字段初始化
    private void resume_family_member_save_init(ResumeFamilyMember resumeFamilyMember) {
        if (resumeFamilyMember == null) {
            return;
        }
        //姓名
        String family_member_name = resumeFamilyMember.getFamily_member_name();
        if (CommUtil.isNotNull(family_member_name) && family_member_name.length() > 50) {
            resumeFamilyMember.setFamily_member_name("");
        }
        //与本人关系
        Integer family_relationship = resumeFamilyMember.getFamily_relationship();
        if (family_relationship != null
            && CommUtil.isBlank(Constants.EDU_DEGREE_MAP.get(family_relationship.toString()))) {
            resumeFamilyMember.setFamily_relationship(0);
        }
        //工作单位
        String family_member_unit = resumeFamilyMember.getFamily_member_unit();
        if (CommUtil.isNotNull(family_member_unit) && family_member_unit.length() > 50) {
            resumeFamilyMember.setFamily_member_unit("");
        }
        //职务
        String family_member_duty = resumeFamilyMember.getFamily_member_duty();
        if (CommUtil.isNotNull(family_member_duty) && family_member_duty.length() > 50) {
            resumeFamilyMember.setFamily_member_duty("");
        }
        //所在地址
        String family_member_work_place = resumeFamilyMember.getFamily_member_work_place();
        if (CommUtil.isNotNull(family_member_work_place) && family_member_work_place.length() > 50) {
            resumeFamilyMember.setFamily_member_work_place("");
        }
        //联系电话
        String family_member_mobile = resumeFamilyMember.getFamily_member_mobile();
        if (CommUtil.isNotNull(family_member_mobile) && family_member_mobile.length() > 11) {
            resumeFamilyMember.setFamily_member_mobile("");
        }
    }
}
