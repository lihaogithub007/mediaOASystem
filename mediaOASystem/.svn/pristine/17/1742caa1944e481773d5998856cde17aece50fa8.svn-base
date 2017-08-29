package com.yuyu.soft.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.Attachments;
import com.yuyu.soft.service.IAttachmentsService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

@Service("attachmentsService")
public class AttachmentsServiceImpl implements IAttachmentsService {

    @Resource
    private IBaseDao<Attachments> attachmentsDao;
    @Resource
    private IUserService          userService;

    @Override
    public List<Attachments> queryAllAttachments() {
        return queryAttachments(null, "");
    }

    @Override
    public List<Attachments> queryAllAttachments(Long user_id) {
        return queryAttachments(user_id, "");
    }

    @Override
    public List<Attachments> queryAllAttachments(String attach_table) {
        return queryAttachments(null, attach_table);
    }

    @Override
    public List<Attachments> queryAttachments(Long user_id, String attach_table) {
        StringBuilder s = new StringBuilder();
        s.append("from Attachments t where t.disabled = false");
        if (CommUtil.isNotNull(user_id)) {
            s.append(" and t.user.id = ").append(user_id);
        }
        if (CommUtil.isNotNull(attach_table)) {
            s.append(" and t.attach_table = ").append(attach_table);
        }
        s.append(" order by t.createtime desc");
        return attachmentsDao.find(s.toString());
    }

    @Override
    public List<Attachments> queryAttachments(String hql, Map<String, Object> paramsMap,
                                              PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(attachmentsDao.count("select count(t) " + hql, paramsMap));
            return attachmentsDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return attachmentsDao.find(hql, paramsMap);
        }

    }

    @Override
    public Attachments getAttachments(Long id) {
        return attachmentsDao.get(Attachments.class, id);
    }

    @Override
    public void addAttachments(Attachments attachments) {
        this.attachmentsDao.save(attachments);
    }

    @Override
    public Attachments addAttachments(Long user_id, String attach_table, String save_file_name,
                                      String original_file_name, String ext_name, String mime_type,
                                      String save_path, float file_size) {
        Attachments attachments = new Attachments();
        attachments.setDisabled(false);
        attachments.setCreatetime(new Date());
        attachments.setAttach_table(attach_table);
        attachments.setSave_file_name(save_file_name);
        attachments.setOriginal_file_name(original_file_name);
        attachments.setExt_name(ext_name);
        attachments.setMime_type(mime_type);
        attachments.setSave_path(save_path);
        attachments.setFile_size(file_size);
        if (user_id != null) {
            attachments.setUser(userService.getUser(user_id));
        }
        addAttachments(attachments);
        return getAttachments(attachments.getId());
    }

    @Override
    public ResultMsg verify_attachment_ids(String attachment_ids, String errMsg, boolean allow_blank) {
        if (!allow_blank && CommUtil.isBlank(attachment_ids)) {
            return CommUtil.setResultMsgData(null, false, errMsg);
        }
        if (allow_blank && CommUtil.isNotNull(attachment_ids)) {
            try {
                String[] attachment_id_arr = attachment_ids.split(",");
                if (attachment_id_arr != null && attachment_id_arr.length > 0) {
                    for (String attachment_id : attachment_id_arr) {
                        Attachments attachments = getAttachments(Long.parseLong(attachment_id
                            .trim()));
                        if (attachments == null) {
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
    public List<Attachments> getAttachmentsByIds(String attachment_ids) {
        List<Attachments> list = new ArrayList<Attachments>();
        if (CommUtil.isBlank(attachment_ids)) {
            return list;
        }
        String[] attachment_id_arr = attachment_ids.split(",");
        if (attachment_id_arr != null && attachment_id_arr.length > 0) {
            for (String attachment_id : attachment_id_arr) {
                Attachments attachments = getAttachments(Long.parseLong(attachment_id.trim()));
                if (attachments == null) {
                    continue;
                } else {
                    list.add(attachments);
                }
            }
        }
        return list;
    }
}
