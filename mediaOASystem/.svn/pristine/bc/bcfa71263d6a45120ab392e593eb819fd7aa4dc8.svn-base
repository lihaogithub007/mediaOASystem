package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.Duty;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 岗位相关接口
 *                       
 * @Filename: IDutyService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IDutyService {

    /**
     * 查询所有岗位列表
     * @return
     */
    List<Duty> queryAllDuty();

    /**
     * 查询某部门下所有岗位
     * @param department_id
     * @return
     */
    List<Duty> queryDutyUnderDepartment(Long department_id);

    /**
     * 查询岗位列表（分页）
     * @param hql
     * @param paramsMap
     * @param pager
     * @return
     */
    List<Duty> queryDuty(String hql, Map<String, Object> paramsMap, PagerInfo pager);

    /**
     * 根据ID获取岗位对象
     * @param id
     * @return
     */
    Duty getDuty(Long id);

    /**
     * 添加岗位
     * @param duty
     */
    void addDuty(Duty duty);

    /**
     * 修改岗位
     * @param duty
     */
    public void updateDuty(Duty duty);

    /**
     * 删除岗位
     * @param duty
     */
    public void delDuty(Duty duty);

    /**
     * 批量删除
     * @param list
     */
    public void batchDelDuty(List<Duty> list);

    /**
     * 验证某部门下岗位名称是否存在
     */
    public boolean verify_duty_name(Long duty_id, Long department_id, String duty_name);

    /**
     * 校验岗位集合是否存在正常岗位(disabled=false)
     * 存在返回true
     */
    public boolean verify_duty_list_contains_normal(List<Duty> list);

    /**
     * 删除岗位
     */
    public ResultMsg delete_save(Long id);

    /**
     * 岗位权限编辑保存 
     * @return 
     */
    public ResultMsg duty_res_save(Long id, String res_ids);

}
