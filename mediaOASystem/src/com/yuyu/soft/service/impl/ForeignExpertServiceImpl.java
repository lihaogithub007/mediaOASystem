package com.yuyu.soft.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.Duty;
import com.yuyu.soft.entity.ForeignExpert;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.IForeignExpertService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

@Service("foreignExpertService")
public class ForeignExpertServiceImpl implements IForeignExpertService {

    @Resource
    private IBaseDao<ForeignExpert> foreignExpertDao;
    @Resource
    private IDutyService            dutyService;

    @Override
    public List<ForeignExpert> queryForeignExpert(String hql, Map<String, Object> paramsMap,
                                                  PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(foreignExpertDao.count("select count(t) " + hql, paramsMap));
            return foreignExpertDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return foreignExpertDao.find(hql, paramsMap);
        }

    }

    @Override
    public ForeignExpert getForeignExpert(Long id) {
        return foreignExpertDao.get(ForeignExpert.class, id);
    }

    @Override
    public void addForeignExpert(ForeignExpert foreignExpert) {
        this.foreignExpertDao.save(foreignExpert);
    }

    @Override
    public void updateForeignExpert(ForeignExpert foreignExpert) {
        this.foreignExpertDao.update(foreignExpert);
    }

    @Override
    public void delForeignExpert(ForeignExpert foreignExpert) {
        foreignExpert = getForeignExpert(foreignExpert.getId());
        foreignExpert.setDisabled(true);
        updateForeignExpert(foreignExpert);
    }

    @Override
    public ResultMsg add_save(ForeignExpert foreignExpert, Long duty_id) {
        ResultMsg rmg = save_check(foreignExpert, duty_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        foreignExpert.setDisabled(false);
        foreignExpert.setCreatetime(new Date());
        addForeignExpert(foreignExpert);
        return CommUtil.setResultMsgData(rmg, true, "外籍专家信息名录保存成功");
    }

    @Override
    public ResultMsg edit_save(ForeignExpert foreignExpert, Long duty_id) {
        ResultMsg rmg = save_check(foreignExpert, duty_id);
        if (!rmg.getResult()) {
            return rmg;
        }
        updateForeignExpert(foreignExpert);
        return CommUtil.setResultMsgData(rmg, true, "外籍专家信息名录编辑保存成功");
    }

    private ResultMsg save_check(ForeignExpert foreignExpert, Long duty_id) {
        if (foreignExpert == null) {
            return CommUtil.setResultMsgData(null, false, "外籍专家信息名录对象为空");
        }
        foreignExpert_save_init(foreignExpert, duty_id);
        return CommUtil.setResultMsgData(null, true, "校验成功");
    }

    private void foreignExpert_save_init(ForeignExpert foreignExpert, Long duty_id) {
        if (foreignExpert == null) {
            return;
        }
        //中文名
        String chinese_name = foreignExpert.getChinese_name();
        if (CommUtil.isBlank(chinese_name)) {
            foreignExpert.setChinese_name("");
        } else if (chinese_name.length() > 50) {
            foreignExpert.setChinese_name(chinese_name.trim().substring(0, 50));
        }
        //英文名
        String english_name = foreignExpert.getEnglish_name();
        if (CommUtil.isBlank(english_name)) {
            foreignExpert.setEnglish_name("");
        } else if (english_name.length() > 50) {
            foreignExpert.setEnglish_name(english_name.trim().substring(0, 100));
        }
        //性别
        Integer sex = foreignExpert.getSex();
        if (sex != null && CommUtil.isBlank(Constants.SEX_MAP.get(sex.toString()))) {
            foreignExpert.setSex(null);
        }

        //职位 
        if (duty_id != null) {
            Duty duty = dutyService.getDuty(duty_id);
            if (duty != null && duty.getId() != null) {
                foreignExpert.setDuty(duty);
            }
        }
        //国籍
        String nationality = foreignExpert.getNationality();
        if (CommUtil.isNotNull(nationality) && nationality.length() > 50) {
            foreignExpert.setNationality("");
        }
        //护照号
        String passport_number = foreignExpert.getPassport_number();
        if (CommUtil.isNotNull(passport_number) && passport_number.length() > 20) {
            foreignExpert.setPassport_number("");
        }
        //合同号
        String contract_number = foreignExpert.getContract_number();
        if (CommUtil.isNotNull(contract_number) && contract_number.length() > 50) {
            foreignExpert.setContract_number("");
        }
        //合同号
        String foreign_expert_certificate = foreignExpert.getForeign_expert_certificate();
        if (CommUtil.isNotNull(foreign_expert_certificate)
            && foreign_expert_certificate.length() > 50) {
            foreignExpert.setForeign_expert_certificate("");
        }
        //工作证号
        String card_number = foreignExpert.getCard_number();
        if (CommUtil.isNotNull(card_number) && card_number.length() > 50) {
            foreignExpert.setCard_number("");
        }
        //学历专业
        String degree_and_major = foreignExpert.getDegree_and_major();
        if (CommUtil.isNotNull(degree_and_major) && degree_and_major.length() > 50) {
            foreignExpert.setDegree_and_major("");
        }
        //来台前媒体领域工作年限
        String media_work_years = foreignExpert.getMedia_work_years();
        if (CommUtil.isNotNull(media_work_years) && media_work_years.length() > 50) {
            foreignExpert.setMedia_work_years("");
        }
        //手机
        String mobile = foreignExpert.getMobile();
        if (CommUtil.isNotNull(mobile) && mobile.length() > 11) {
            foreignExpert.setMobile("");
        }
        //邮箱
        String email = foreignExpert.getEmail();
        if (CommUtil.isNotNull(email) && (email.length() > 50 || email.indexOf("@") < 0)) {
            foreignExpert.setEmail("");
        }
        //地址
        String address = foreignExpert.getAddress();
        if (CommUtil.isNotNull(address) && address.length() > 50) {
            foreignExpert.setAddress("");
        }
    }

    @Override
    public boolean verify_foreign_expert_mobile(Long foreign_expert_id, String mobile) {

        boolean flag = true;
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("mobile", mobile);
        StringBuilder s = new StringBuilder();
        s.append("from ForeignExpert t where t.disabled = false");
        s.append(" and t.mobile =:mobile");
        if (CommUtil.isNotNull(foreign_expert_id)) {
            s.append(" and t.id !=:foreign_expert_id");
            paramsMap.put("foreign_expert_id", foreign_expert_id);
        }
        List<ForeignExpert> foreignExperts = foreignExpertDao.find(s.toString(), paramsMap);
        if ((foreignExperts != null) && (foreignExperts.size() > 0)) {
            flag = false;
        }
        return flag;
    }

    public int getCountForExportExcel(String chinese_name, String english_name, String nationality,
                                      String card_number, String mobile) {
        String sql = getCountSQL()
                     + getCommonSQL(chinese_name, english_name, nationality, card_number, mobile);
        return foreignExpertDao.countBySql(sql, null);
    }

    public List<Object[]> getForeignExpertsForExportExcel(String chinese_name, String english_name,
                                                          String nationality, String card_number,
                                                          String mobile, int beginIndex,
                                                          int pageSize) {
        String sql = getQueryListSQL()
                     + getCommonSQL(chinese_name, english_name, nationality, card_number, mobile);
        List<Object[]> list = (ArrayList<Object[]>) foreignExpertDao.findBySql(sql, null,
            beginIndex, pageSize);

        return list;
    }

    private String getQueryListSQL() {
        return "SELECT  chinese_name,english_name,"
        		+ "case sex when 1 then '男' when 2 then '女' else '' end ,"
        		+ "birthday,d.duty_name,nationality,passport_number,passport_expiration_date,"
        		+ "residence_permit_date,first_sign_time,first_expiration_time,foreign_expert_certificate,"
        		+ "contract_number,current_annual_salary,card_number,vacation_remain_current_contract,"
        		+ "degree_and_major,media_work_years,current_month_salary,"
        		+ "mobile,email,address  ";
    }

    private String getCommonSQL(String chinese_name, String english_name, String nationality,
                                String card_number, String mobile) {
        StringBuffer s = new StringBuffer();
        s.append(" from tb_foreign_expert t ");
        s.append(" LEFT JOIN tb_duty d on t.duty_id = d.id ");
        s.append(" where t.disabled = 0 ");
        if (CommUtil.isNotNull(chinese_name)) {
            s.append(" and t.chinese_name like '%").append(chinese_name.trim()).append("%'");
        }
        if (CommUtil.isNotNull(english_name)) {
            s.append(" and t.english_name like '%").append(english_name.trim()).append("%'");
        }
        if (CommUtil.isNotNull(nationality)) {
            s.append(" and t.nationality like '%").append(nationality.trim()).append("%'");
        }
        if (CommUtil.isNotNull(card_number)) {
            s.append(" and t.card_number like '%").append(card_number.trim()).append("%'");
        }
        if (CommUtil.isNotNull(mobile)) {
            s.append(" and t.mobile like '%").append(mobile.trim()).append("%'");
        }
        return s.toString();
    }

    private String getCountSQL() {
        return "select count(t.id) ";
    }

}
