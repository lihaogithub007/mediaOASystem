package com.yuyu.soft.service;

import java.util.List;

import com.yuyu.soft.entity.UserRes;

/**
 * 用户资源相关的接口处理
 *                       
 * @Filename: IUserResService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IUserResService {

    /**
     * 查询用户权限ID
     */
    List<Long> queryUserResIds(Long user_id);

    UserRes getUserRes(Long id);

    void addUserRes(UserRes userRes);

    void delUserRes(UserRes userRes);

    /**
     * 用户权限保存
     */
    void user_res_save(String res_ids, Long user_id);

    /**
     * 查询某用户所有权限
     */
    public List<UserRes> queryByUserId(Long user_id);

}
