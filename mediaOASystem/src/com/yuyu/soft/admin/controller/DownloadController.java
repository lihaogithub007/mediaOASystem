package com.yuyu.soft.admin.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuyu.soft.entity.Attachments;
import com.yuyu.soft.entity.InsideIncomeDispatches;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.entity.UserPhoto;
import com.yuyu.soft.service.IAttachmentsService;
import com.yuyu.soft.service.IInsideIncomeDispatchesService;
import com.yuyu.soft.service.IUserPhotoService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.service.impl.InsideIncomeDispatchesServiceImpl;
import com.yuyu.soft.util.CommUtil;

@Controller
public class DownloadController {

    @Resource
    private IUserService        userService;
    @Resource
    private IAttachmentsService attachmentsService;
    @Resource
    private IUserPhotoService   userPhotoService;
    @Resource
    private IInsideIncomeDispatchesService   insideIncomeDispatchesService;
    

    @RequestMapping({ "/user/user_file_download.htm" })
    public void download(HttpServletRequest request, HttpServletResponse response, Long user_id,
                         String type) {
        response.reset();
        InputStream is = null;
        ZipOutputStream zos = null;
        try {
            response.setContentType("application/octet-stream");
            zos = new ZipOutputStream(response.getOutputStream());
            String fileName = "";
            boolean flag = true;
            if (user_id == null || CommUtil.isBlank(type)) {
                flag = false;
                fileName = "用户编号为空";
            }
            User user = null;
            if (flag) {
                user = this.userService.getUser(user_id);
                if (user == null || user.getUserPhoto() == null
                    || user.getUserPhoto().getId() == null) {
                    flag = false;
                    fileName = "没有证照";
                }
            }
            UserPhoto userPhoto = null;
            if (flag) {
                userPhoto = this.userPhotoService.getUserPhoto(user.getUserPhoto().getId());
                if (userPhoto == null) {
                    flag = false;
                    fileName = "没有证照";
                }
            }
            List<Attachments> attachments = null;
            if (flag) {
                fileName = user.getTrue_name();
                if ("idphoto".equalsIgnoreCase(type)) {
                    attachments = this.attachmentsService.getAttachmentsByIds(userPhoto
                        .getID_attach_ids());
                    fileName = fileName + "身份证照";
                } else if ("degreephoto".equalsIgnoreCase(type)) {
                    attachments = attachmentsService.getAttachmentsByIds(userPhoto
                        .getDegree_attach_ids());
                    fileName = fileName + "学历证明";
                } else if ("bluetwoinchphoto".equalsIgnoreCase(type)) {
                    attachments = attachmentsService.getAttachmentsByIds(userPhoto
                        .getBlue_small_two_inch_attach_ids());
                    fileName = fileName + "蓝底小二寸证件照";
                } else if ("whitetwoinchphoto".equalsIgnoreCase(type)) {
                    attachments = attachmentsService.getAttachmentsByIds(userPhoto
                        .getWhite_small_two_inch_attach_ids());
                    fileName = fileName + "白色小二寸证件照";
                } else if ("othersphoto".equalsIgnoreCase(type)) {
                    attachments = attachmentsService.getAttachmentsByIds(userPhoto
                        .getOthers_attach_ids());
                    fileName = fileName + "其他证照";
                }
                if (attachments == null || attachments.isEmpty()) {
                    flag = false;
                    fileName = "没有证照";
                }
            }

            fileName = CommUtil.encodeExportFileName(request, fileName + new Date().getTime()
                                                              + ".zip");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            if (!flag) {
                return;
            }
            String webPath = CommUtil.getURL(request) + "/";
            for (int i = 0; i < attachments.size(); i++) {
                Attachments attach = this.attachmentsService.getAttachments(attachments.get(i)
                    .getId());
                String path = attach.getSave_path() + "/" + attach.getSave_file_name();
                String filePath = request.getServletContext().getRealPath("/") + path;
                File file = new File(filePath);
                if (file.exists()) {
                    URL url = new URL(webPath + path);
                    zos.putNextEntry(new ZipEntry(attach.getOriginal_file_name()));
                    is = url.openConnection().getInputStream();
                    byte[] buffer = new byte[1024];
                    int r = 0;
                    while ((r = is.read(buffer)) != -1) {
                        zos.write(buffer, 0, r);
                        zos.flush();
                    }
                    zos.flush();
                    is.close();
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    is = null;
                }
            }
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    zos = null;
                }
            }
        }
    }
    
    @RequestMapping({ "/admin/office/download_insideIncomeDispatches.htm" })
    public void download_insideIncomeDispatches(HttpServletRequest request, HttpServletResponse response, Long id) {
        response.reset();
        InputStream is = null;
        ZipOutputStream zos = null;
        try {
            response.setContentType("application/octet-stream");
            zos = new ZipOutputStream(response.getOutputStream());
            String fileName = "";
            boolean flag = true;
            if (id == null ) {
                flag = false;
                fileName = "台内收文编号为空";
            }
            InsideIncomeDispatches insideIncomeDispatches = null;
            if (flag) {
            	insideIncomeDispatches = insideIncomeDispatchesService.getInsideIncomeDispatches(id);
                if (insideIncomeDispatches == null 
                		|| insideIncomeDispatches.getAttach_ids() == null) {
                    flag = false;
                    fileName = "没有证照";
                }
            }
            List<Attachments> attachments = null;
            
            attachments = this.attachmentsService.getAttachmentsByIds(insideIncomeDispatches.getAttach_ids());
            fileName = insideIncomeDispatches.getTitle();
            
            fileName = CommUtil.encodeExportFileName(request, fileName + new Date().getTime()
                                                              + ".zip");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            if (!flag) {
                return;
            }
            String webPath = CommUtil.getURL(request) + "/";
            for (int i = 0; i < attachments.size(); i++) {
                Attachments attach = this.attachmentsService.getAttachments(attachments.get(i)
                    .getId());
                String path = attach.getSave_path() + "/" + attach.getSave_file_name();
                String filePath = request.getServletContext().getRealPath("/") + path;
                File file = new File(filePath);
                if (file.exists()) {
                    URL url = new URL(webPath + path);
                    zos.putNextEntry(new ZipEntry(attach.getOriginal_file_name()));
                    is = url.openConnection().getInputStream();
                    byte[] buffer = new byte[1024];
                    int r = 0;
                    while ((r = is.read(buffer)) != -1) {
                        zos.write(buffer, 0, r);
                        zos.flush();
                    }
                    zos.flush();
                    is.close();
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    is = null;
                }
            }
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    zos = null;
                }
            }
        }
    }

}
