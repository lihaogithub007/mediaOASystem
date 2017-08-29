package com.yuyu.soft.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.Department;
import com.yuyu.soft.service.IDepartmentService;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 部门管理
 *                       
 * @Filename: DepartmentServiceImpl.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Service("departmentService")
public class DepartmentServiceImpl implements IDepartmentService {

    @Resource
    private IBaseDao<Department> departmentDao;
    @Resource
    private IDutyService         dutyService;
    @Resource
    private IUserService         userService;

    @Override
    public List<Department> queryAllDepartment() {
        return departmentDao
            .find("from Department t where t.disabled = false order by t.createtime desc");
    }

    public List<Department> queryDepartment(String hql, Map<String, Object> paramsMap,
                                            PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(departmentDao.count("select count(t) " + hql, paramsMap));
            return departmentDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return departmentDao.find(hql, paramsMap);
        }

    }

    @Override
    public Department getDepartment(Long id) {
        return departmentDao.get(Department.class, id);
    }

    @Override
    public void addDepartment(Department department) {
    	this.departmentDao.save(department);
    }

    @Override
    public void updateDepartment(Department department) {
        this.departmentDao.update(department);
    }

    @Override
    public void delDepartment(Department department) {
        department = getDepartment(department.getId());
        department.setDisabled(true);
        updateDepartment(department);
    }

    @Override
    public boolean verify_department_name(Long department_id, String department_name) {
        boolean flag = true;

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("department_name", department_name);
        StringBuilder s = new StringBuilder();
        s.append("from Department t where t.disabled = false");
        s.append(" and t.department_name =:department_name");
        if (CommUtil.isNotNull(department_id)) {
            s.append(" and t.id !=:department_id");
            paramsMap.put("department_id", department_id);
        }
        List<Department> departments = departmentDao.find(s.toString(), paramsMap);
        if ((departments != null) && (departments.size() > 0)) {
            flag = false;
        }
        return flag;
    }

    @Override
    public ResultMsg verify_department_ids(String department_ids, String errMsg, boolean allow_blank) {
        if (!allow_blank && CommUtil.isBlank(department_ids)) {
            return CommUtil.setResultMsgData(null, false, errMsg);
        }
        if (allow_blank && CommUtil.isNotNull(department_ids)) {

            try {
                String[] department_id_arr = department_ids.split(",");
                if (department_ids != null && department_id_arr.length > 0) {
                    for (String department_id : department_id_arr) {
                        Department department = getDepartment(Long.parseLong(department_id.trim()));
                        if (department == null) {
                            return CommUtil.setResultMsgData(null, false, errMsg);
                        }
                    }
                }
            } catch (Exception e) {
                return CommUtil.setResultMsgData(null, false, errMsg);
            }
        }
        return CommUtil.setResultMsgData(null, true, "校验成功");
    }

    @Override
    public String getDepartmentNames(String department_ids) {

        if (CommUtil.isBlank(department_ids)) {
            return "";
        }
        String department_names = "";
        String[] department_id_arr = department_ids.split(",");
        if (department_id_arr != null && department_id_arr.length > 0) {
            for (String department_id : department_id_arr) {
                try {
                    Department department = getDepartment(Long.parseLong(department_id.trim()));
                    if (department == null || CommUtil.isBlank(department.getDepartment_name())) {
                        continue;
                    } else {
                        department_names += department.getDepartment_name() + ",";
                    }
                } catch (Exception e) {
                }
            }
        }
        if (department_names.endsWith(",")) {
            department_names = department_names.substring(0, department_names.length() - 1);
        }
        return department_names;
    }

    @Override
    public ResultMsg delete_save(Long id) {
        ResultMsg rmg = delete_save_check(id);
        if (!rmg.getResult()) {
            return rmg;
        }
        delDepartment(getDepartment(id));
        return CommUtil.setResultMsgData(rmg, true, "部门/科组删除成功");
    }

    private ResultMsg delete_save_check(Long id) {
        if (id == null) {
            return CommUtil.setResultMsgData(null, false, "部门/科组标识为空");
        }
        Department department = getDepartment(id);
        if (department == null) {
            return CommUtil.setResultMsgData(null, false, "找不到部门/科组记录");
        }
        if (dutyService.verify_duty_list_contains_normal(department.getDutyList())) {
            return CommUtil.setResultMsgData(null, false, "该部门/科组下存在岗位，不能删除");
        }
        if (userService.verify_user_list_contains_normal(department.getUserList())) {
            return CommUtil.setResultMsgData(null, false, "该部门/科组下存在用户，不能删除");
        }
        return CommUtil.setResultMsgData(null, true, "校验成功");
    }
}
