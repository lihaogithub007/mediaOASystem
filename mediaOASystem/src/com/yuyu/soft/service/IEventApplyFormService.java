package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.EventApplyForm;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 事项申请表相关接口
 *                       
 * @Filename: IEventApplyFormService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IEventApplyFormService {

    /**
     * 查询所有事项申请表列表
     */
    List<EventApplyForm> queryAllEventApplyForm();

    /**
     * 查询事项申请表数量(sql)
     * 用于导出Excel
     */
    int getCountForExportExcel(String apply_date, String department_id, Integer cost_company_id,
                               String cost_contract);

    /**
     * 查询事项申请表(sql)
     * 用于导出Excel
     * 1.申请日期
     * 2.申请科组
     * 3.费用支出公司
     * 4.费用所属合同
     */
    List<Object[]> getEventApplyFormForExportExcel(String apply_date, String department_id,
                                                   Integer cost_company_id, String cost_contract,
                                                   int beginIndex, int pageSize);

    /**
     * 查询事项申请表列表（分页）
     */
    List<EventApplyForm> queryEventApplyForm(String hql, Map<String, Object> paramsMap,
                                             PagerInfo pager);

    /**
     * 根据ID获取事项申请表对象
     */
    EventApplyForm getEventApplyForm(Long id);

    /**
     * 添加事项申请表
     */
    void addEventApplyForm(EventApplyForm eventApplyForm);

    /**
     * 更新事项申请表
     */
    void updateEventApplyForm(EventApplyForm eventApplyForm);

    /**
     * 删除事项申请表
     */
    public void delEventApplyForm(EventApplyForm eventApplyForm);

    /**
     * 批量删除
     */
    public void batchDelEventApplyForm(List<EventApplyForm> list);

    /**
     * 添加事项申请表保存
     */
    public ResultMsg add_save(EventApplyForm eventApplyForm, Long user_id, Long leader_user_id);

    /**
     * 事项申请表编辑保存
     */
    public ResultMsg edit_save(EventApplyForm eventApplyForm, Long user_id, Long leader_user_id);
    

}
