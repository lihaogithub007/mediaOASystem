package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.DepartmentAwards;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 部门评奖相关接口
 *                       
 * @Filename: IDepartmentAwardsService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IDepartmentAwardsService {

    /**
     * 查询所有部门评奖列表
     */
    List<DepartmentAwards> queryAllDepartmentAwards();

    /**
     * 查询部门评奖数量(sql)
     * 用于导出Excel
     */
    int getCountForExportExcel(String awards_time, Long user_id);

    /**
     * 查询部门评奖(sql)
     * 用于导出Excel
     * 1.申请日期
     * 2.申请科组
     * 3.费用支出公司
     * 4.费用所属合同
     */
    List<Object[]> getDepartmentAwardsForExportExcel(String awards_time, Long user_id,
                                                     int beginIndex, int pageSize);

    /**
     * 查询部门评奖列表（分页）
     */
    List<DepartmentAwards> queryDepartmentAwards(String hql, Map<String, Object> paramsMap,
                                                 PagerInfo pager);

    /**
     * 根据ID获取部门评奖对象
     */
    DepartmentAwards getDepartmentAwards(Long id);

    /**
     * 添加部门评奖
     */
    void addDepartmentAwards(DepartmentAwards departmentAwards);

    /**
     * 更新部门评奖
     */
    void updateDepartmentAwards(DepartmentAwards departmentAwards);

    /**
     * 删除部门评奖
     */
    public void delDepartmentAwards(DepartmentAwards departmentAwards);

    /**
     * 批量删除
     */
    public void batchDelDepartmentAwards(List<DepartmentAwards> list);

    /**
     * 添加部门评奖保存
     */
    public ResultMsg add_save(DepartmentAwards departmentAwards, Long user_id, Long leader_user_id);

    /**
     * 部门评奖编辑保存
     */
    public ResultMsg edit_save(DepartmentAwards departmentAwards, Long user_id, Long leader_user_id);

}
