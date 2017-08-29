package com.yuyu.soft.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.entity.UserPhoto;
import com.yuyu.soft.service.IAttachmentsService;
import com.yuyu.soft.service.IUserPhotoService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.ResultMsg;

/**
 * 岗位管理
 *                       
 * @Filename: UserPhotoServiceImpl.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Service("userPhotoService")
public class UserPhotoServiceImpl implements IUserPhotoService {

    private static final List<String> USER_PHOTO_TYPE_LIST = new ArrayList<String>() {
                                                               {
                                                                   add("idphoto");
                                                                   add("degreephoto");
                                                                   add("bluetwoinchphoto");
                                                                   add("whitetwoinchphoto");
                                                                   add("othersphoto");
                                                               }
                                                           };
    @Resource
    private IBaseDao<UserPhoto>       userPhotoDao;
    @Resource
    private IUserService              userService;
    @Resource
    private IAttachmentsService       attachmentsService;

    @Override
    public List<UserPhoto> queryUserPhotosByUserId(Long user_id) {
        StringBuilder s = new StringBuilder();
        s.append("from UserPhoto t where t.disabled = false");
        s.append(" and t.user.id = ").append(user_id);
        s.append(" order by t.createtime desc");
        List<UserPhoto> list = userPhotoDao.find(s.toString());
        if (list == null || list.size() == 0) {
            return new ArrayList<UserPhoto>();
        }
        return list;
    }

    @Override
    public UserPhoto getUserPhoto(Long id) {
        return userPhotoDao.get(UserPhoto.class, id);
    }

    @Override
    public void addUserPhoto(UserPhoto userPhoto) {
        this.userPhotoDao.save(userPhoto);
    }

    @Override
    public void updateUserPhoto(UserPhoto userPhoto) {
        this.userPhotoDao.update(userPhoto);
    }

    @Override
    public void delUserPhoto(UserPhoto userPhoto) {
        userPhoto = getUserPhoto(userPhoto.getId());
        userPhoto.setDisabled(true);
        updateUserPhoto(userPhoto);

    }

    @Override
    public ResultMsg user_photo_save(Long user_id, String photo_column, String attachments_ids) {
        ResultMsg rmg = user_photo_save_check(user_id, photo_column, attachments_ids);
        if (!rmg.getResult()) {
            return rmg;
        }
        User user = userService.getUser(user_id);
        if (user.getUserPhoto() == null || user.getUserPhoto().getId() == null) {
            List<UserPhoto> list = queryUserPhotosByUserId(user_id);
            if (list.size() == 0) {
                UserPhoto userPhoto = new UserPhoto();
                userPhoto.setCreatetime(new Date());
                userPhoto.setDisabled(false);
                userPhoto.setUser(user);
                addUserPhoto(userPhoto);
                userPhoto = getUserPhoto(userPhoto.getId());
                user.setUserPhoto(userPhoto);
            } else if (list.size() == 1) {
                user.setUserPhoto(queryUserPhotosByUserId(user_id).get(0));
            } else {
                return CommUtil.setResultMsgData(rmg, true, "用户证照数据异常");
            }
        }
        if (user.getUserPhoto() == null || user.getUserPhoto().getId() == null) {
            return CommUtil.setResultMsgData(rmg, true, "用户证照数据异常");
        } else {
            UserPhoto userPhoto = getUserPhoto(user.getUserPhoto().getId());
            if ("idphoto".equalsIgnoreCase(photo_column)) {
                userPhoto.setID_attach_ids(attachments_ids);
            } else if ("degreephoto".equalsIgnoreCase(photo_column)) {
                userPhoto.setDegree_attach_ids(attachments_ids);
            } else if ("bluetwoinchphoto".equalsIgnoreCase(photo_column)) {
                userPhoto.setBlue_small_two_inch_attach_ids(attachments_ids);
            } else if ("whitetwoinchphoto".equalsIgnoreCase(photo_column)) {
                userPhoto.setWhite_small_two_inch_attach_ids(attachments_ids);
            } else if ("othersphoto".equalsIgnoreCase(photo_column)) {
                userPhoto.setOthers_attach_ids(attachments_ids);
            }
            updateUserPhoto(userPhoto);
        }
        return CommUtil.setResultMsgData(rmg, true, "上传证照保存成功");
    }

    private ResultMsg user_photo_save_check(Long user_id, String photo_column,
                                            String attachments_ids) {
        if (user_id == null) {
            return CommUtil.setResultMsgData(null, false, "用户标识为空");
        }
        User user = userService.getUser(user_id);
        if (user == null || user.getId() == null) {
            return CommUtil.setResultMsgData(null, false, "用户对象为空");
        }
        if (CommUtil.isBlank(photo_column)
            || !USER_PHOTO_TYPE_LIST.contains(photo_column.toLowerCase().trim())) {
            return CommUtil.setResultMsgData(null, false, "证照类型不正确");
        }
        ResultMsg rmg = attachmentsService.verify_attachment_ids(attachments_ids, "上传附件值异常", true);
        if (!rmg.getResult()) {
            return rmg;
        }
        return CommUtil.setResultMsgData(rmg, true, "上传证照校验成功");
    }

}
