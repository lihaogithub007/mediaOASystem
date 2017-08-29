package com.yuyu.soft.base.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yuyu.soft.entity.Attachments;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.service.IAttachmentsService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;

public class BaseController {

    protected org.slf4j.Logger log = LoggerFactory.getLogger(BaseController.class);

    /**
     * 获取当前管理员
     * @param request
     * @return
     */
    public static User getCurrentUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(Constants.USER_SESSION_KEY);
    }

    /**
     * 上传文件
     */
    public static List<Attachments> uploadify(HttpServletRequest request,
                                              HttpServletResponse response, String attach_table,
                                              IAttachmentsService attachmentsService)
                                                                                     throws IOException {

        List<Attachments> list = new ArrayList<Attachments>();

        String dateForder = CommUtil.parseShortDateTime(new Date());
        String savePath = Constants.BASE_UPLOAD_PATH + "/" + attach_table + "/" + dateForder;
        String realPath = request.getSession().getServletContext().getRealPath("/");
        File forder = new File(realPath + savePath);
        if (!forder.exists()) {
            forder.mkdirs();
        }
        forder = null;

        System.out.println("当前时间：" + CommUtil.getCurrentTime());

        String originalFilename = "";//上传文件的文件名称
        String extName = "";//文件后缀名 如：.jpg
        String saveFileName = "";//保存的文件名
        float fileSize = -1;
        String mimeType = "";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iterator = multipartRequest.getFileNames();
        while (iterator.hasNext()) {
            MultipartFile file = multipartRequest.getFile(iterator.next());//取得上传文件  
            if (file == null || file.isEmpty() || CommUtil.isBlank(file.getOriginalFilename())) {
                continue;
            }
            originalFilename = file.getOriginalFilename();
            extName = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
            saveFileName = UUID.randomUUID().toString() + extName;
            fileSize = Float.valueOf((float) file.getSize()).floatValue();
            mimeType = file.getContentType();

            File f = new File(realPath + savePath + "/" + saveFileName);
            if (!f.exists()) {
                f.createNewFile();
            }
            DataOutputStream out = new DataOutputStream(new FileOutputStream(f));
            InputStream is = null;
            try {
                is = file.getInputStream();
                int size = (int) fileSize;
                byte[] buffer = new byte[size];
                while (is.read(buffer) > 0) {
                    out.write(buffer);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            } finally {
                if (is != null) {
                    is.close();
                }
                if (out != null) {
                    out.close();
                }
            }
            Attachments attachments = attachmentsService.addAttachments(getCurrentUser(request)
                .getId(), attach_table, saveFileName, originalFilename, extName, mimeType,
                savePath, fileSize);
            list.add(attachments);
        }
        System.out.println("当前时间：" + CommUtil.getCurrentTime());
        return list;
    }

    /**
     * 上传文件
     */
    public static List<Attachments> uploadifyNoUser(HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    String attach_table,
                                                    IAttachmentsService attachmentsService)
                                                                                           throws IOException {

        List<Attachments> list = new ArrayList<Attachments>();

        String dateForder = CommUtil.parseShortDateTime(new Date());
        String savePath = Constants.BASE_UPLOAD_PATH + "/" + attach_table + "/" + dateForder;
        String realPath = request.getSession().getServletContext().getRealPath("/");
        File forder = new File(realPath + savePath);
        if (!forder.exists()) {
            forder.mkdirs();
        }
        forder = null;

        System.out.println("当前时间：" + CommUtil.getCurrentTime());

        String originalFilename = "";//上传文件的文件名称
        String extName = "";//文件后缀名 如：.jpg
        String saveFileName = "";//保存的文件名
        float fileSize = -1;
        String mimeType = "";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iterator = multipartRequest.getFileNames();
        while (iterator.hasNext()) {
            MultipartFile file = multipartRequest.getFile(iterator.next());//取得上传文件  
            if (file == null || file.isEmpty() || CommUtil.isBlank(file.getOriginalFilename())) {
                continue;
            }
            originalFilename = file.getOriginalFilename();
            extName = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
            saveFileName = UUID.randomUUID().toString() + extName;
            fileSize = Float.valueOf((float) file.getSize()).floatValue();
            mimeType = file.getContentType();

            File f = new File(realPath + savePath + "/" + saveFileName);
            if (!f.exists()) {
                f.createNewFile();
            }
            DataOutputStream out = new DataOutputStream(new FileOutputStream(f));
            InputStream is = null;
            try {
                is = file.getInputStream();
                int size = (int) fileSize;
                byte[] buffer = new byte[size];
                while (is.read(buffer) > 0) {
                    out.write(buffer);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            } finally {
                if (is != null) {
                    is.close();
                }
                if (out != null) {
                    out.close();
                }
            }
            Long user_id = null;
            User user = getCurrentUser(request);
            if (user != null && user.getId() != null) {
                user_id = user.getId();
            }
            Attachments attachments = attachmentsService.addAttachments(user_id, attach_table,
                saveFileName, originalFilename, extName, mimeType, savePath, fileSize);
            list.add(attachments);
        }
        System.out.println("当前时间：" + CommUtil.getCurrentTime());
        return list;
    }

    public static String write2JsonStr(HttpServletRequest request, List<Attachments> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        JSONArray array = new JSONArray();
        String url = "";
        for (Attachments attachments : list) {
            JSONObject obj = new JSONObject();
            url = CommUtil.getURL(request) + "/" + attachments.getSave_path() + "/"
                  + attachments.getSave_file_name();
            obj.put("id", attachments.getId());
            obj.put("name", attachments.getOriginal_file_name());
            obj.put("size", attachments.getFile_size());
            obj.put("url", url);
            if (isImage(attachments.getMime_type())) {
                obj.put("thumbnailUrl", url);//text-64.png 缩略图地址    
            }
            if (CommUtil.isOffice(attachments.getExt_name())
                || CommUtil.isPDF(attachments.getExt_name())) {
                obj.put("previewUrl", CommUtil.getURL(request) + "/preview/file_preview.htm?id="
                                      + attachments.getId());
            }
            obj.put("deleteUrl", url);
            obj.put("deleteType", "DELETE");
            array.add(obj);
        }
        JSONObject json = new JSONObject();
        json.put("files", array);
        return json.toString();
    }

    public static boolean isImage(String mimetype) {
        if (CommUtil.isBlank(mimetype)) {
            return false;
        }
        String type = mimetype.split("/")[0];
        return type.equals("image");
    }

}
