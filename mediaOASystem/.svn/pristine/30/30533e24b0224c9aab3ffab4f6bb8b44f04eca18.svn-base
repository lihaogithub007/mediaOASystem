package com.yuyu.soft.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.DispatchUnit;
import com.yuyu.soft.service.IDispatchUnitService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;

/**
 * 发文单位管理
 * 
 * @Filename: DispatchUnitServiceImpl.java
 * @Version: 1.0
 * @Author: lihao
 *
 */
@Service("dispatchUnitService")
public class DispatchUnitServiceImpl implements IDispatchUnitService {

    @Resource
    private IBaseDao<DispatchUnit> dispatchUnitDao;

    @Override
    public List<DispatchUnit> queryAllDispatchUnit() {
        StringBuilder s = new StringBuilder();
        s.append("from DispatchUnit t where t.disabled = false");
        s.append(" order by t.createtime asc");
        return dispatchUnitDao.find(s.toString());
    }

    @Override
    public List<DispatchUnit> queryDispatchUnitList(String hql, Map<String, Object> paramsMap,
                                                    PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(dispatchUnitDao.count("select count(t) " + hql, paramsMap));
            return dispatchUnitDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return dispatchUnitDao.find(hql, paramsMap);
        }
    }

    /**
     * 验证发文单位名称是否存在
     */
    @Override
    public boolean verify_dispatch_unit_name(Long dispatch_unit_id, String dispatch_unit_name) {
        boolean flag = true;//true代表不存在

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("dispatch_unit_name", dispatch_unit_name);
        StringBuilder s = new StringBuilder();
        s.append("from DispatchUnit t where t.disabled = false");
        s.append(" and t.dispatch_unit_name =:dispatch_unit_name");
        if (CommUtil.isNotNull(dispatch_unit_id)) {
            s.append(" and t.id !=:dispatch_unit_id");
            paramsMap.put("dispatch_unit_id", dispatch_unit_id);
        }
        List<DispatchUnit> dispatchUnits = dispatchUnitDao.find(s.toString(), paramsMap);
        if ((dispatchUnits != null) && (dispatchUnits.size() > 0)) {
            flag = false;
        }
        return flag;
    }

    /**
     * 添加发文单位
     * @param dispatchUnit
     */
    @Override
    public void addDispatchUnit(DispatchUnit dispatchUnit) {
        this.dispatchUnitDao.save(dispatchUnit);
    }

    /**
     * 根据ID获取发文单位对象
     */
    @Override
    public DispatchUnit findDispatchUnitById(Long id) {
        return dispatchUnitDao.get(DispatchUnit.class, id);
    }

    /**
    * 删除发文单位
    */
    public void delDispathcUnit(DispatchUnit dispatchUnit) {
    	dispatchUnit.setDisabled(true);
    	editDispatchUnit(dispatchUnit);
    }

    /**
     * 修改发文单位
     */
    @Override
    public void editDispatchUnit(DispatchUnit dispatchUnit) {
        this.dispatchUnitDao.update(dispatchUnit);
    }

    @Override
    public Long add_save(String dispatch_unit_name) {
        if (CommUtil.isBlank(dispatch_unit_name)) {
            return null;
        }
        DispatchUnit dispatchUnit = new DispatchUnit();
        dispatchUnit.setDisabled(false);
        dispatchUnit.setCreatetime(new Date());
        dispatchUnit.setDispatch_unit_name(dispatch_unit_name);
        addDispatchUnit(dispatchUnit);
        return dispatchUnit.getId();
    }

}
