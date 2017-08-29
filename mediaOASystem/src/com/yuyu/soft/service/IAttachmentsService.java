package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.Attachments;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 上传附件相关接口
 *                       
 * @Filename: IAttachmentsService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IAttachmentsService {

    /**
     * 查询所有上传附件列表
     */
    List<Attachments> queryAllAttachments();

    /**
     * 查询所有上传附件列表
     */
    List<Attachments> queryAllAttachments(Long user_id);

    /**
     * 查询所有上传附件列表
     */
    List<Attachments> queryAllAttachments(String attach_table);

    /**
     * 查询某部门下所有上传附件
     */
    List<Attachments> queryAttachments(Long user_id, String attach_table);

    /**
     * 查询上传附件列表（分页）
     */
    List<Attachments> queryAttachments(String hql, Map<String, Object> paramsMap, PagerInfo pager);

    /**
     * 根据ID获取上传附件对象
     */
    Attachments getAttachments(Long id);

    /**
     * 添加上传附件
     */
    void addAttachments(Attachments attachments);

    /**
     * 上传文件保存
     */
    public Attachments addAttachments(Long user_id, String attach_table, String save_file_name,
                                      String original_file_name, String ext_name, String mime_type,
                                      String save_path, float file_size);

    /**
     * 验证附件ID字符串
     */
    public ResultMsg verify_attachment_ids(String attachment_ids, String errMsg, boolean allow_blank);

    /**
     * 获取附件集合
     */
    public List<Attachments> getAttachmentsByIds(String attachments_ids);
}
