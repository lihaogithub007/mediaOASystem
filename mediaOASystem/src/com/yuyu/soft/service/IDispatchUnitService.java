package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.DispatchUnit;
import com.yuyu.soft.util.PagerInfo;

public interface IDispatchUnitService {

    /**
     * 查询所有发文单位
     */
    List<DispatchUnit> queryAllDispatchUnit();

    /**
     * 查询发文单位列表
     */
    List<DispatchUnit> queryDispatchUnitList(String hql, Map<String, Object> paramsMap,
                                             PagerInfo pager);

    /**
     * 验证发文单位名称是否存在
     */
    boolean verify_dispatch_unit_name(Long dispatch_unit_id, String dispatch_unit_name);

    /**
     * 添加发文单位
     */
    void addDispatchUnit(DispatchUnit dispatchUnit);

    /**
     * 根据ID获取发文单位对象
     */
    DispatchUnit findDispatchUnitById(Long id);

    /**
    * 删除部门及其下所有岗位
    */
    void delDispathcUnit(DispatchUnit dispatchUnit);

    /**
     * 修改发文单位
     */
    void editDispatchUnit(DispatchUnit dispatchUnit);

    /**
     * 添加发文单位保存
     */
    Long add_save(String dispatch_unit_name);

}
