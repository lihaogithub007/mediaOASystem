package com.yuyu.soft.service;

import java.util.List;

import com.yuyu.soft.entity.UserPhoto;
import com.yuyu.soft.util.ResultMsg;

/**
 * 上传证照
 *                       
 * @Filename: IUserPhotoService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IUserPhotoService {

    List<UserPhoto> queryUserPhotosByUserId(Long user_id);

    UserPhoto getUserPhoto(Long id);

    void addUserPhoto(UserPhoto userPhoto);

    void updateUserPhoto(UserPhoto userPhoto);

    public void delUserPhoto(UserPhoto userPhoto);

    public ResultMsg user_photo_save(Long user_id, String photo_column, String attachments_ids);

}
