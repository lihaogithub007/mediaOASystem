package com.yuyu.soft.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.entity.VacationApply;
import com.yuyu.soft.entity.VacationApplyDetails;
import com.yuyu.soft.service.IAttachmentsService;
import com.yuyu.soft.service.IAttendanceRecordService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.service.IVacationApplyApprovalService;
import com.yuyu.soft.service.IVacationApplyDetailsService;
import com.yuyu.soft.service.IVacationApplyService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

@Service("vacationApplyService")
public class VacationApplyServiceImpl implements IVacationApplyService {

    @Resource
    private IBaseDao<VacationApply>       vacationApplyDao;
    @Resource
    private IUserService                  userService;
    @Resource
    private IVacationApplyDetailsService  vacationApplyDetailsService;
    @Resource
    private IVacationApplyApprovalService vacationApplyApprovalService;
    @Resource
    private IAttendanceRecordService      attendanceRecordService;
    @Resource
    private IAttachmentsService           attachmentsService;

    @Override
    public List<VacationApply> queryVacationApply(Long user_id) {
        if (user_id == null) {
            return new ArrayList<VacationApply>();
        }
        StringBuilder s = new StringBuilder();
        s.append("from VacationApply t where t.disabled = false");
        s.append(" and t.user.id = ").append(user_id);
        s.append(" order by t.createtime asc");
        List<VacationApply> vacationApplys = vacationApplyDao.find(s.toString());
        if ((vacationApplys == null) || (vacationApplys.size() == 0)) {
            return new ArrayList<VacationApply>();
        }
        return vacationApplys;
    }

    @Override
    public List<VacationApply> queryVacationApply(String hql, Map<String, Object> paramsMap,
                                                  PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(vacationApplyDao.count("select count(t) " + hql, paramsMap));
            return vacationApplyDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return vacationApplyDao.find(hql, paramsMap);
        }

    }

    @Override
    public VacationApply getVacationApply(Long id) {
        return vacationApplyDao.get(VacationApply.class, id);
    }

    @Override
    public void addVacationApply(VacationApply vacationApply) {
        this.vacationApplyDao.save(vacationApply);
    }

    @Override
    public void updateVacationApply(VacationApply vacationApply) {
        this.vacationApplyDao.update(vacationApply);
    }

    @Override
    public void delVacationApply(VacationApply vacationApply) {
        vacationApply = getVacationApply(vacationApply.getId());
        vacationApply.setDisabled(true);
        updateVacationApply(vacationApply);
    }

    @Override
    public ResultMsg add_save(VacationApply vacationApply, Long user_id, String vad_arr,
                              String vaa_arr) {
        ResultMsg rmg = save_check_and_init(vacationApply, user_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        vacationApply.setDisabled(false);
        vacationApply.setCreatetime(new Date());
        addVacationApply(vacationApply);

        vacationApply = getVacationApply(vacationApply.getId());
        rmg = vacationApplyDetailsService.save(vacationApply, vad_arr);
        if (!rmg.getResult()) {
            throw new RuntimeException(rmg.getMsg());
        }
        vacationApply = getVacationApply(vacationApply.getId());
        String attach_ids = vacationApply.getAttach_ids();
        List<VacationApplyDetails> vadList = vacationApplyDetailsService.queryVacationApplyDetails(
            vacationApply.getUser().getId(), vacationApply.getId());
        if (vadList != null && vadList.size() > 0) {
            boolean flag = false;
            String msg = "";
            for (VacationApplyDetails vad : vadList) {
                vad = vacationApplyDetailsService.getVacationApplyDetails(vad.getId());
                Integer leave_type = vad.getLeave_type();
                if (5 == leave_type) {
                    flag = true;
                    msg = "请附子女出生证明";
                    break;
                } else if (6 == leave_type) {
                    flag = true;
                    msg = "请附结婚证复印件";
                    break;
                }
            }
            if (flag) {
                if (CommUtil.isBlank(attach_ids)) {
                    throw new RuntimeException(msg);
                }
            } else {
                if (CommUtil.isNotNull(attach_ids)) {
                    vacationApply.setAttach_ids("");
                    updateVacationApply(vacationApply);
                }
            }
        }
        vacationApply = getVacationApply(vacationApply.getId());
        rmg = vacationApplyApprovalService.save(vacationApply, vaa_arr);
        if (!rmg.getResult()) {
            throw new RuntimeException(rmg.getMsg());
        }

        return CommUtil.setResultMsgData(rmg, true, "请假申请保存成功");
    }

    @Override
    public ResultMsg edit_save(VacationApply vacationApply, Long user_id, String vad_arr,
                               String vaa_arr) {
        ResultMsg rmg = save_check_and_init(vacationApply, user_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        updateVacationApply(vacationApply);
        vacationApply = getVacationApply(vacationApply.getId());

        rmg = vacationApplyDetailsService.save(vacationApply, vad_arr);
        if (!rmg.getResult()) {
            throw new RuntimeException(rmg.getMsg());
        }
        vacationApply = getVacationApply(vacationApply.getId());
        String attach_ids = vacationApply.getAttach_ids();
        List<VacationApplyDetails> vadList = vacationApplyDetailsService.queryVacationApplyDetails(
            vacationApply.getUser().getId(), vacationApply.getId());
        if (vadList != null && vadList.size() > 0) {
            boolean flag = false;
            String msg = "";
            for (VacationApplyDetails vad : vadList) {
                vad = vacationApplyDetailsService.getVacationApplyDetails(vad.getId());
                Integer leave_type = vad.getLeave_type();
                if (5 == leave_type) {
                    flag = true;
                    msg = "请附子女出生证明";
                    break;
                } else if (6 == leave_type) {
                    flag = true;
                    msg = "请附结婚证复印件";
                    break;
                }
            }
            if (flag) {
                if (CommUtil.isBlank(attach_ids)) {
                    throw new RuntimeException(msg);
                }
            } else {
                if (CommUtil.isNotNull(attach_ids)) {
                    vacationApply.setAttach_ids("");
                    updateVacationApply(vacationApply);
                }
            }
        }
        vacationApply = getVacationApply(vacationApply.getId());
        rmg = vacationApplyApprovalService.save(vacationApply, vaa_arr);
        if (!rmg.getResult()) {
            throw new RuntimeException(rmg.getMsg());
        }

        return CommUtil.setResultMsgData(rmg, true, "请假申请保存成功");
    }

    private ResultMsg save_check_and_init(VacationApply vacationApply, Long user_id) {
        if (vacationApply == null) {
            return CommUtil.setResultMsgData(null, false, "请假申请对象为空");
        }
        if (user_id == null) {
            return CommUtil.setResultMsgData(null, false, "申请人标识为空");
        }
        User user = userService.getUser(user_id);
        if (user == null || user.getId() == null || user.getDisabled()
            || user.getUser_status() == 4) {
            return CommUtil.setResultMsgData(null, false, "申请人对象为空或已离职");
        }
        vacationApply.setUser(user);

        Date vacation_begin_date = vacationApply.getVacation_begin_date();
        if (vacation_begin_date == null) {
            return CommUtil.setResultMsgData(null, false, "请假开始时间不能为空");
        }
        Date vacation_end_date = vacationApply.getVacation_end_date();
        if (vacation_end_date == null) {
            return CommUtil.setResultMsgData(null, false, "请假结束时间不能为空");
        }
        if (vacation_begin_date.after(vacation_end_date)) {
            return CommUtil.setResultMsgData(null, false, "请假开始时间不能在请假结束时间之后");
        }
        //TODO 时间冲突校验
        List<VacationApply> tempList = queryVacationApply(user.getId());
        if (tempList != null && tempList.size() > 0) {
            for (VacationApply va : tempList) {
                if (va.getCancel_vacation_date() == null) {
                    if (vacationApply != null && vacationApply.getId() == va.getId()) {
                        continue;
                    }
                    Date temp_vacation_begin_date = va.getVacation_begin_date();
                    Date temp_vacation_end_date = va.getVacation_end_date();
                    if (isTimeConflict(vacation_begin_date, vacation_end_date,
                        temp_vacation_begin_date, temp_vacation_end_date)) {
                        return CommUtil.setResultMsgData(null, false, "请假时间存在冲突");
                    }
                }
            }
        }

        BigDecimal vacation_dates = vacationApply.getVacation_dates();
        if (vacation_dates == null) {
            return CommUtil.setResultMsgData(null, false, "请假天数不能为空");
        }
        String remark = vacationApply.getRemark();
        if (CommUtil.isNotNull(remark) && remark.length() > 255) {
            vacationApply.setRemark(remark.substring(0, 255));
        }
        ResultMsg rmg = attachmentsService.verify_attachment_ids(vacationApply.getAttach_ids(),
            "上传附件值异常", true);
        if (!rmg.getResult()) {
            return rmg;
        }
        return CommUtil.setResultMsgData(null, true, "校验成功");
    }

    /**
     * 判断时间是否有冲突
     * 有冲突 return true
     */
    private boolean isTimeConflict(Date begin_time, Date end_time, Date begin_time2, Date end_time2) {
        return ((begin_time.getTime() >= begin_time2.getTime()) && begin_time.getTime() <= end_time2
            .getTime())
               || ((begin_time2.getTime() >= begin_time.getTime()) && begin_time2.getTime() <= end_time
                   .getTime());
    }

    @Override
    public void cancel_save(VacationApply vacationApply) {
        vacationApply = getVacationApply(vacationApply.getId());
        vacationApply.setCancel_vacation_date(new Date());
        updateVacationApply(vacationApply);
        back_leave_days(vacationApply);
    }

    private void back_leave_days(VacationApply vacationApply) {
        List<VacationApplyDetails> oldList = vacationApplyDetailsService.queryVacationApplyDetails(
            null, vacationApply.getId());
        if (oldList != null && oldList.size() > 0) {
            for (VacationApplyDetails vad : oldList) {
                vad = vacationApplyDetailsService.getVacationApplyDetails(vad.getId());
                attendanceRecordService.update_leave_days(vad, "subtract");
            }
        }
    }

    @Override
    public void delete_save(VacationApply vacationApply) {
        delVacationApply(vacationApply);
        back_leave_days(vacationApply);
    }
}
