package com.yuyu.soft.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.Duty;
import com.yuyu.soft.entity.DutyRes;
import com.yuyu.soft.entity.Res;
import com.yuyu.soft.service.IDutyResService;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.IResService;
import com.yuyu.soft.util.CommUtil;

/**
 * 岗位资源
 *                       
 * @Filename: DutyResServiceImpl.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Service("dutyResService")
public class DutyResServiceImpl implements IDutyResService {

    @Resource
    private IBaseDao<DutyRes> dutyResDao;
    @Resource
    private IDutyService      dutyService;
    @Resource
    private IResService       resService;

    @Override
    public DutyRes getDutyRes(Long id) {
        return dutyResDao.get(DutyRes.class, id);
    }

    @Override
    public void addDutyRes(DutyRes dutyRes) {
        this.dutyResDao.save(dutyRes);

    }

    @Override
    public void delDutyRes(DutyRes dutyRes) {
        this.dutyResDao.delete(dutyRes);
    }

    @Override
    public List<Long> queryDutyResIds(Long duty_id) {
        if (duty_id == null) {
            return new ArrayList<Long>();
        }
        StringBuffer s = new StringBuffer();
        s.append(" select res_id from tb_duty_res");
        s.append(" where disabled = 0 and  duty_id = ").append(duty_id);
        List<Long> list = dutyResDao.executeNativeQuery(s.toString(), null, 0, -1);
        if (list == null || list.size() == 0) {
            return new ArrayList<Long>();
        }
        return list;
    }

    @Override
    public void duty_res_save(String res_ids, Long duty_id) {
        if (duty_id == null) {
            return;
        }
        Duty duty = dutyService.getDuty(duty_id);
        if (duty == null) {
            return;
        }
        try {
            duty_res_delete(duty_id);
            if (CommUtil.isNotNull(res_ids)) {
                String[] res_id_arr = res_ids.split(",");
                if (res_id_arr != null && res_id_arr.length > 0) {
                    for (String res_id : res_id_arr) {
                        Res res = resService.getRes(Long.parseLong(res_id.trim()));
                        if (res == null) {
                            continue;
                        } else {
                            DutyRes dutyRes = new DutyRes();
                            dutyRes.setDisabled(false);
                            dutyRes.setCreatetime(new Date());
                            dutyRes.setDuty(duty);
                            dutyRes.setRes(res);
                            addDutyRes(dutyRes);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除用户原来权限
    private void duty_res_delete(Long duty_id) {
        if (duty_id == null) {
            return;
        }
        List<DutyRes> dutyResList = queryByDutyId(duty_id);
        if (dutyResList != null && dutyResList.size() > 0) {
            for (DutyRes dutyRes : dutyResList) {
                dutyRes = getDutyRes(dutyRes.getId());
                delDutyRes(dutyRes);
            }
        }
    }

    @Override
    public List<DutyRes> queryByDutyId(Long duty_id) {
        StringBuilder s = new StringBuilder();
        s.append("from DutyRes t where t.disabled = false");
        s.append(" and t.duty.id = ").append(duty_id);
        return dutyResDao.find(s.toString());
    }

}
