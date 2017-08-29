package com.yuyu.soft.service;

import java.util.List;

import com.yuyu.soft.entity.ResGroup;

/**
 * 资源组相关接口
 *                       
 * @Filename: IResService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IResGroupService {

    /**
     * 查询所有资源组
     */
    public List<ResGroup> queryAllResGroup();

    /**
     * 查询资源组
     */
    ResGroup getResGroup(Long id);

    /**
     * 添加资源组
     */
    void addResGroup(ResGroup rg);

    /**
     * 根据资源组名称查询资源组
     */
    public ResGroup getByName(String rg_name);

    /**
     * 根据资源组名称查询资源组
     */
    public List<ResGroup> queryByName(String rg_name);

    /**
     * 资源组保存
     */
    public ResGroup add_save(String rg_name);

}
