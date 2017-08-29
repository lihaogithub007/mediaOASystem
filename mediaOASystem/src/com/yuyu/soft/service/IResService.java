package com.yuyu.soft.service;

import java.util.List;

import com.yuyu.soft.entity.Res;
import com.yuyu.soft.entity.ResGroup;

/**
 * 资源相关接口
 *                       
 * @Filename: IResService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IResService {

    Res getRes(Long id);

    /**
     * 添加资源
     */
    void addRes(Res res);

    /**
     * 根据url查询资源
     */
    public List<Res> queryByUrl(String url);

    /**
     * 添加资源保存
     */
    public void add_save(String res_name, String res_url, ResGroup rg);
}
