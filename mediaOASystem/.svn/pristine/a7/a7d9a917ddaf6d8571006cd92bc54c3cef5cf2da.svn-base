package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.Department;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 部门相关接口
 *                       
 * @Filename: IDepartmentService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IDepartmentService {

    /**
     * 查询部门列表
     * @return
     */
    List<Department> queryAllDepartment();

    /**
     * 查询部门列表（分页）
     * @param hql
     * @param paramsMap
     * @param pager
     * @return
     */
    List<Department> queryDepartment(String hql, Map<String, Object> paramsMap, PagerInfo pager);

    /**
     * 根据ID获取部门对象
     * @param id
     * @return
     */
    Department getDepartment(Long id);

    /**
     * 添加部门
     * @param department
     */
    void addDepartment(Department department);

    /**
     * 修改部门
     * @param department
     */
    public void updateDepartment(Department department);

    /**
     * 删除部门
     */
    public void delDepartment(Department department);

    /**
     * 验证部门/科组名称是否存在
     */
    public boolean verify_department_name(Long department_id, String department_name);

    /**
     * 验证多部门ID字符串
     */
    public ResultMsg verify_department_ids(String department_ids, String errMsg, boolean allow_blank);

    /**
     * 根据部门IDs获取部门Names
     */
    public String getDepartmentNames(String department_ids);

    /**
     * 删除部门
     */
    public ResultMsg delete_save(Long department_id);

}
