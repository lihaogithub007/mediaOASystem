package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.OutsideIncomeDispatches;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 台外收文相关接口
 *                       
 * @Filename: IOutsideIncomeDispatchesService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IOutsideIncomeDispatchesService {

    /**
     * 查询所有台外收文列表
     */
    List<OutsideIncomeDispatches> queryAllOutsideIncomeDispatches();

    /**
     * 查询台外收文数量(sql)
     * 用于导出Excel
     */
    int getCountForExportExcel(String dispatch_unit_name, String income_dispatches_time);

    /**
     * 查询台外收文(sql)
     * 用于导出Excel
     */
    List<Object[]> getOutsideIncomeDispatchesForExportExcel(String dispatch_unit_name,
                                                            String income_dispatches_time,
                                                            int beginIndex, int pageSize);

    /**
     * 查询台外收文列表（分页）
     */
    List<OutsideIncomeDispatches> queryOutsideIncomeDispatches(String hql,
                                                               Map<String, Object> paramsMap,
                                                               PagerInfo pager);

    /**
     * 根据ID获取台外收文对象
     */
    OutsideIncomeDispatches getOutsideIncomeDispatches(Long id);

    /**
     * 添加台外收文
     */
    void addOutsideIncomeDispatches(OutsideIncomeDispatches outsideIncomeDispatches);

    /**
     * 更新台外收文
     */
    void updateOutsideIncomeDispatches(OutsideIncomeDispatches outsideIncomeDispatches);

    /**
     * 删除台外收文
     */
    public void delOutsideIncomeDispatches(OutsideIncomeDispatches outsideIncomeDispatches);

    /**
     * 批量删除
     */
    public void batchDelOutsideIncomeDispatches(List<OutsideIncomeDispatches> list);

    /**
     * 添加台外收文保存
     */
    public ResultMsg add_save(OutsideIncomeDispatches outsideIncomeDispatches, Long user_id,
                              String dispatch_unit_name);

    /**
     * 台外收文编辑保存
     */
    public ResultMsg edit_save(OutsideIncomeDispatches outsideIncomeDispatches, Long user_id,
                               String dispatch_unit_name);

}
