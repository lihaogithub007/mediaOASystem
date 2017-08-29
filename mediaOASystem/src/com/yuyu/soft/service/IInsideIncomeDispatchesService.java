package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.InsideIncomeDispatches;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 台内收文相关接口
 *                       
 * @Filename: IInsideIncomeDispatchesService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IInsideIncomeDispatchesService {

    /**
     * 查询所有台内收文列表
     */
    List<InsideIncomeDispatches> queryAllInsideIncomeDispatches();

    /**
     * 查询台内收文数量(sql)
     * 用于导出Excel
     */
    int getCountForExportExcel(Long dispatch_unit_id, String print_and_dispatch_time);

    /**
     * 查询台内收文(sql)
     * 用于导出Excel
     */
    List<Object[]> getInsideIncomeDispatchesForExportExcel(Long dispatch_unit_id,
                                                           String print_and_dispatch_time,
                                                           int beginIndex, int pageSize);

    /**
     * 查询台内收文列表（分页）
     */
    List<InsideIncomeDispatches> queryInsideIncomeDispatches(String hql,
                                                             Map<String, Object> paramsMap,
                                                             PagerInfo pager);

    /**
     * 根据ID获取台内收文对象
     */
    InsideIncomeDispatches getInsideIncomeDispatches(Long id);

    /**
     * 添加台内收文
     */
    void addInsideIncomeDispatches(InsideIncomeDispatches insideIncomeDispatches);

    /**
     * 更新台内收文
     */
    void updateInsideIncomeDispatches(InsideIncomeDispatches insideIncomeDispatches);

    /**
     * 删除台内收文
     */
    public void delInsideIncomeDispatches(InsideIncomeDispatches insideIncomeDispatches);

    /**
     * 批量删除
     */
    public void batchDelInsideIncomeDispatches(List<InsideIncomeDispatches> list);

    /**
     * 添加台内收文保存
     */
    public ResultMsg add_save(InsideIncomeDispatches insideIncomeDispatches, Long user_id,
                              Long dispatch_unit_id, String dispatch_unit_name);

    /**
     * 台内收文编辑保存
     */
    public ResultMsg edit_save(InsideIncomeDispatches insideIncomeDispatches, Long user_id,
                               Long dispatch_unit_id, String dispatch_unit_name);

}
