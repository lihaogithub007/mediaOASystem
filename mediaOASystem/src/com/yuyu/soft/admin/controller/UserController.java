package com.yuyu.soft.admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuyu.soft.base.annotation.SecurityMapping;
import com.yuyu.soft.base.controller.BaseController;
import com.yuyu.soft.base.mv.JModelAndView;
import com.yuyu.soft.entity.Attachments;
import com.yuyu.soft.entity.Department;
import com.yuyu.soft.entity.Duty;
import com.yuyu.soft.entity.Section;
import com.yuyu.soft.entity.SubSection;
import com.yuyu.soft.entity.User;
import com.yuyu.soft.entity.UserBase;
import com.yuyu.soft.entity.UserContract;
import com.yuyu.soft.entity.UserContractRenewal;
import com.yuyu.soft.entity.UserEducation;
import com.yuyu.soft.entity.UserFamilyMember;
import com.yuyu.soft.entity.UserPhoto;
import com.yuyu.soft.entity.UserSchoolaward;
import com.yuyu.soft.entity.UserWork;
import com.yuyu.soft.service.IAttachmentsService;
import com.yuyu.soft.service.IDepartmentService;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.IResGroupService;
import com.yuyu.soft.service.ISectionService;
import com.yuyu.soft.service.ISubSectionService;
import com.yuyu.soft.service.IUserContractRenewalService;
import com.yuyu.soft.service.IUserContractService;
import com.yuyu.soft.service.IUserEducationService;
import com.yuyu.soft.service.IUserFamilyMemberService;
import com.yuyu.soft.service.IUserPhotoService;
import com.yuyu.soft.service.IUserResService;
import com.yuyu.soft.service.IUserSchoolawardService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.service.IUserWorkService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.JsonUtil;
import com.yuyu.soft.util.Md5;
import com.yuyu.soft.util.POIUtil;
import com.yuyu.soft.util.PageUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;
import com.yuyu.soft.util.WebForm;
import com.yuyu.soft.util.word.WordUtil;

/**
 * 用户管理                      
 * @Filename: UserController.java
 * @Version: 1.0
 * @Author: 范光洲
 * @Email: goodfgz@163.com
 *
 */
@Controller
public class UserController extends BaseController {

    @Resource
    private IUserService                userService;
    @Resource
    private IDepartmentService          departmentService;
    @Resource
    private IDutyService                dutyService;
    @Resource
    private IResGroupService            resGroupService;
    @Resource
    private IUserResService             userResService;
    @Resource
    private IUserWorkService            userWorkService;
    @Resource
    private IUserEducationService       userEducationService;
    @Resource
    private IUserSchoolawardService     userSchoolawardService;
    @Resource
    private IUserFamilyMemberService    userFamilyMemberService;
    @Resource
    private IUserContractService        userContractService;
    @Resource
    private IUserContractRenewalService userContractRenewalService;
    @Resource
    private IAttachmentsService         attachmentsService;
    @Resource
    private IUserPhotoService           userPhotoService;
    @Resource
    private ISectionService	            sectionService;
    @Resource
    private ISubSectionService          subSectionService;

    /**
     * 用户列表
     */
    @SecurityMapping(res_name = "员工列表", res_url = "/admin/user/user_list.htm*", res_group = "人员档案")
    @RequestMapping({ "/admin/user/user_list.htm" })
    public ModelAndView user_list(HttpServletRequest request, HttpServletResponse response,
                                  String pageSize, String currentPage, Long department_id,
                                  Long duty_id, String true_name, String card_number,
                                  Integer user_status, Integer sex, String age,
                                  Integer domicile_type, Integer user_relationship,
                                  String highest_education, String hobby_and_speciality,
                                  Integer blood_type, Integer constellation, String nation,
                                  Integer ID_type, Integer work_years, Integer political_status,
                                  String join_party_date_beginTime, String join_party_date_endTime,
                                  Integer marriage_status, String condition_of_children,
                                  String highest_offering, String foreign_language_level,
                                  String job_titles_level, Integer is_have_press_card, String sign,
                                  String mobile,Long section_id,Long sub_section_id) {

        ModelAndView mv = new JModelAndView("admin/user/user_list.html", request, response);

        currentPage = PageUtil.initCurrentPage(currentPage);
        PagerInfo pager = PageUtil.initPagerInfo(pageSize, currentPage);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        StringBuilder hql = new StringBuilder();
        hql.append("from User t where t.disabled = false");

        //部门查询条件
        if (CommUtil.isNotNull(department_id)) {
            hql.append(" and t.department.id =:department_id ");
            paramsMap.put("department_id", department_id);
        }
        //岗位查询条件
        if (CommUtil.isNotNull(duty_id)) {
            hql.append(" and t.duty.id =:duty_id ");
            paramsMap.put("duty_id", duty_id);
        }
        //版块
        if (CommUtil.isNotNull(section_id)) {
        	hql.append(" and t.section.id =:section_id ");
        	paramsMap.put("section_id", section_id);
        }
        //子版块
        if (CommUtil.isNotNull(sub_section_id)) {
        	hql.append(" and t.sub_section.id =:sub_section_id ");
        	paramsMap.put("sub_section_id", sub_section_id);
        }
        //姓名
        if (CommUtil.isNotNull(true_name)) {
            hql.append(" and t.true_name like :true_name ");
            paramsMap.put("true_name", "%" + true_name.trim() + "%");
        }
        //工作证号
        if (CommUtil.isNotNull(card_number)) {
            hql.append(" and t.card_number like :card_number ");
            paramsMap.put("card_number", "%" + card_number.trim() + "%");
        }
        //用户状态
        if (user_status == null) {
            hql.append(" and t.user_status in (1,2,3) ");
        } else {
            hql.append(" and t.user_status = :user_status ");
            paramsMap.put("user_status", user_status);
        }

        //性别
        if (sex != null) {
            hql.append(" and t.userBase.sex = :sex ");
            paramsMap.put("sex", sex);
        }
        //年龄
        if (CommUtil.isNotNull(age) && CommUtil.null2Integer(age) != null) {
            hql.append(" and t.userBase.age = :age ");
            paramsMap.put("age", CommUtil.null2Integer(age));
        }
        //户籍类型
        if (domicile_type != null) {
            hql.append(" and t.userBase.domicile_type = :domicile_type ");
            paramsMap.put("domicile_type", domicile_type);
        }
        //聘用类型
        if (user_relationship != null) {
            hql.append(" and t.user_relationship = :user_relationship ");
            paramsMap.put("user_relationship", user_relationship);
        }
        //最高学历
        if (CommUtil.isNotNull(highest_education)) {
            hql.append(" and t.userBase.highest_education = :highest_education ");
            paramsMap.put("highest_education", highest_education);
        }
        //爱好特长
        if (CommUtil.isNotNull(hobby_and_speciality)) {
            hql.append(" and t.userBase.hobby_and_speciality like :hobby_and_speciality ");
            paramsMap.put("hobby_and_speciality", "%" + hobby_and_speciality.trim() + "%");
        }
        //血型   
        if (CommUtil.isNotNull(blood_type)) {
            hql.append(" and t.userBase.blood_type = :blood_type ");
            paramsMap.put("blood_type", blood_type);
        }
        //星座 constellation
        if (CommUtil.isNotNull(constellation)) {
            hql.append(" and t.userBase.constellation = :constellation ");
            paramsMap.put("constellation", constellation);
        }
        //民族  nation
        if (CommUtil.isNotNull(nation)) {
            hql.append(" and t.userBase.nation like :nation ");
            paramsMap.put("nation", "%" + nation.trim() + "%");
        }
        //证件类型 ID_type
        if (CommUtil.isNotNull(ID_type)) {
            hql.append(" and t.userBase.ID_type = :ID_type ");
            paramsMap.put("ID_type", ID_type);
        }
        //工作年限 work_years
        if (CommUtil.isNotNull(work_years)) {
            hql.append(" and t.userBase.work_years = :work_years ");
            paramsMap.put("work_years", work_years);
        }
        //政治面貌  political_status
        if (CommUtil.isNotNull(political_status)) {
            hql.append(" and t.userBase.political_status = :political_status ");
            paramsMap.put("political_status", political_status);
        }
        //入党（团）时间 join_party_date_
        if (CommUtil.isNotNull(join_party_date_beginTime)) {
            hql.append(" and date_format(t.userBase.join_party_date,'%Y-%m-%d') >= str_to_date('")
                .append(join_party_date_beginTime).append("','%Y-%m-%d')");
        }
        if (CommUtil.isNotNull(join_party_date_endTime)) {
            hql.append(" and date_format(t.userBase.join_party_date,'%Y-%m-%d') <= str_to_date('")
                .append(join_party_date_endTime).append("','%Y-%m-%d')");
        }
        //婚姻状况 marriage_status
        if (CommUtil.isNotNull(marriage_status)) {
            hql.append(" and t.userBase.marriage_status = :marriage_status ");
            paramsMap.put("marriage_status", marriage_status);
        }
        //子女情况  condition_of_children
        if (CommUtil.isNotNull(condition_of_children)) {
            hql.append(" and t.userBase.condition_of_children like :condition_of_children ");
            paramsMap.put("condition_of_children", "%" + condition_of_children.trim() + "%");
        }
        //最高学位 highest_offering
        if (CommUtil.isNotNull(highest_offering)) {
            hql.append(" and t.userBase.highest_offering like :highest_offering ");
            paramsMap.put("highest_offering", "%" + highest_offering.trim() + "%");
        }
        //外语种类及水平    foreign_language_level
        if (CommUtil.isNotNull(foreign_language_level)) {
            hql.append(" and t.userBase.foreign_language_level like :foreign_language_level ");
            paramsMap.put("foreign_language_level", "%" + foreign_language_level.trim() + "%");
        }
        //职称等级  job_titles_level
        if (CommUtil.isNotNull(job_titles_level)) {
            hql.append(" and t.userBase.job_titles_level like :job_titles_level ");
            paramsMap.put("job_titles_level", "%" + job_titles_level.trim() + "%");
        }
        //是否有记者证  is_have_press_card
        if (CommUtil.isNotNull(is_have_press_card)) {
            hql.append(" and t.userBase.is_have_press_card = :is_have_press_card ");
            paramsMap.put("is_have_press_card", is_have_press_card);
        }
        //电话
        if (CommUtil.isNotNull(mobile)) {
            hql.append(" and t.mobile like :mobile ");
            paramsMap.put("mobile", "%" + mobile.trim() + "%");
        }

        hql.append(" order by t.department.id asc");

        List<User> list = userService.queryUser(hql.toString(), paramsMap, pager);
        PageUtil.savePageInfo2ModelAndView(mv, list, pager);

        List<Department> departments = departmentService.queryAllDepartment();
        List<Duty> dutys = dutyService.queryAllDuty();
        List<Section> sections = sectionService.queryAllSection();
        List<SubSection> subSections = subSectionService.queryAllSubSection();
        
        mv.addObject("sections", sections);
        mv.addObject("subSections", subSections);
        mv.addObject("department_id", department_id);
        mv.addObject("duty_id", duty_id);
        mv.addObject("section_id", section_id);
        mv.addObject("sub_section_id", sub_section_id);
        mv.addObject("true_name", true_name);
        mv.addObject("card_number", card_number);
        mv.addObject("user_status", user_status);
        mv.addObject("sex", sex);
        mv.addObject("age", age);
        mv.addObject("domicile_type", domicile_type);
        mv.addObject("user_relationship", user_relationship);
        mv.addObject("highest_education", highest_education);
        mv.addObject("hobby_and_speciality", hobby_and_speciality);
        mv.addObject("blood_type", blood_type);
        mv.addObject("constellation", constellation);
        mv.addObject("nation", nation);
        mv.addObject("ID_type", ID_type);
        mv.addObject("work_years", work_years);
        mv.addObject("political_status", political_status);
        mv.addObject("join_party_date_beginTime", join_party_date_beginTime);
        mv.addObject("join_party_date_endTime", join_party_date_endTime);
        mv.addObject("marriage_status", marriage_status);
        mv.addObject("condition_of_children", condition_of_children);
        mv.addObject("highest_offering", highest_offering);
        mv.addObject("foreign_language_level", foreign_language_level);
        mv.addObject("job_titles_level", job_titles_level);
        mv.addObject("is_have_press_card", is_have_press_card);
        mv.addObject("sign", sign);
        mv.addObject("mobile", mobile);

        mv.addObject("departments", departments);
        mv.addObject("dutys", dutys);
        mv.addObject("user_status_map", Constants.USER_STATUS_MAP);//用户状态
        mv.addObject("sex_map", Constants.SEX_MAP);//性别
        mv.addObject("domicile_type_map", Constants.DOMICILE_TYPE_MAP);//户口性质
        mv.addObject("user_relationship_map", Constants.USER_RELATIONSHIP_MAP);
        mv.addObject("user_status_map", Constants.USER_STATUS_MAP);
        mv.addObject("user_blood_type_map", Constants.BLOOD_TYPE_MAP);//血型
        mv.addObject("constellation_map", Constants.CONSTELLATION_NO_DATE_MAP);//星座
        mv.addObject("ID_type_map", Constants.ID_TYPE_MAP);//证件类型
        mv.addObject("political_status_map", Constants.POLITICAL_STATUS_MAP);//政治面貌
        mv.addObject("marriage_status_map", Constants.MARRIAGE_STATUS_MAP);//政治面貌
        mv.addObject("highest_education_map", Constants.EDU_DEGREE_MAP);//政治面貌
        mv.addObject("is_have_press_card_map", Constants.HAVE_OR_NOT_MAP);//有无
        return mv;
    }

    //=============================================导出Excel区域开始======================================================//
    @SecurityMapping(res_name = "导出Excel", res_url = "/admin/user/user_exportExcel.htm*", res_group = "人员档案")
    @RequestMapping("/admin/user/user_exportExcel.htm")
    public synchronized void user_exportExcel(HttpServletRequest request,
                                              HttpServletResponse response, String currentPage,
                                              Long department_id, Long duty_id, String true_name,
                                              String card_number, Integer user_status, Integer sex,
                                              String age, Integer domicile_type,
                                              Integer user_relationship, String highest_education,
                                              String hobby_and_speciality, Integer blood_type,
                                              Integer constellation, String nation,
                                              Integer ID_type, Integer work_years,
                                              Integer political_status,
                                              String join_party_date_beginTime,
                                              String join_party_date_endTime,
                                              Integer marriage_status,
                                              String condition_of_children,
                                              String highest_offering,
                                              String foreign_language_level,
                                              String job_titles_level, Integer is_have_press_card,
                                              String sign, String mobile,Long section_id,Long sub_section_id) {

        List<Object[]> list = null;
        OutputStream out = null;
        SXSSFWorkbook workbook = null;
        SXSSFSheet sheet = null;

        try {
            workbook = new SXSSFWorkbook(100);
            sheet = (SXSSFSheet) workbook.createSheet("用户");

            String titleName = "用户统计表";
            String[] titleNames = { "序号","姓名", "部门/科组", "岗位", "版块","子版块" ,"聘用类型", "籍贯", "性别", "年龄", "最高学历",
                    "工作证号" };
            POIUtil.setColumnWidth(sheet, titleNames.length);//设置通用列宽

            int all_count = userService.getCountForExportExcel(department_id, duty_id, true_name,
                card_number, user_status, sex, age, domicile_type, user_relationship,
                highest_education, hobby_and_speciality, work_years, job_titles_level, blood_type,
                constellation, nation, foreign_language_level, ID_type, political_status,
                highest_offering, condition_of_children, marriage_status,
                join_party_date_beginTime, join_party_date_endTime, is_have_press_card, mobile,
                section_id,sub_section_id);

            if (all_count == 0) {
                fillSheet(sheet, titleName, titleNames, null, 2);
            } else {
                //计算分页数
                log.info("总大小：" + all_count);
                int pageSize = Constants.DEFAULT_PAGE_SIZE_FOR_EXPORT_EXCEL;
                double temp_pages = (double) all_count / (double) pageSize;
                int pages = (int) Math.ceil(temp_pages);
                int beginIndex = 0;
                int rowNum = 0;
                for (int i = 0; i < pages; i++) {
                    beginIndex = 0 + i * pageSize;
                    log.info("【用户导出-分页填充】第" + (i + 1) + "次填充开始====>" + CommUtil.getCurrentTime());

                    list = userService.getUsersForExportExcel(department_id, duty_id, true_name,
                        card_number, user_status, sex, age, domicile_type, user_relationship,
                        highest_education, hobby_and_speciality, beginIndex, pageSize, work_years,
                        job_titles_level, blood_type, constellation, nation,
                        foreign_language_level, ID_type, political_status, highest_offering,
                        condition_of_children, marriage_status, join_party_date_beginTime,
                        join_party_date_endTime, is_have_press_card, mobile,
                        section_id,sub_section_id);
                    rowNum = 2 + i * pageSize;
                    fillSheet(sheet, titleName, titleNames, list, rowNum);
                    log.info("【用户导出-分页填充】第" + (i + 1) + "次填充结束====>" + CommUtil.getCurrentTime());
                    list.clear();
                }
                list = null;
            }

            response.setContentType("application/vnd.ms-excel");
            String fileName = "用户" + new Date().getTime() + ".xlsx";
            fileName = CommUtil.encodeExportFileName(request, fileName);
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void fillSheet(SXSSFSheet sheet, String titleName, String[] titleNames,
                           List<Object[]> list, int rowNum) {
        if (2 == rowNum) {
            POIUtil.setTitle(sheet, titleName, 0, titleNames.length - 1);
            POIUtil.setTitles(sheet, titleNames, 1);
        }
        this.fillData(sheet, list, rowNum);
    }

    private void fillData(SXSSFSheet sheet, List<Object[]> list, int rowNum) {

        if (list != null && list.size() > 0 && rowNum >= 2) {
            CellStyle cellStyle = POIUtil.getBaseCellStyle(sheet);
            Font font = POIUtil.getBaseFont(sheet);
            cellStyle.setFont(font);

            for (Object[] objs : list) {
                Cell cell = null;
                Row row = sheet.createRow(rowNum);
                int columnNum = 0;
                // 序号
                cell = row.createCell(columnNum++);
                cell.setCellValue(new XSSFRichTextString(rowNum - 1 + ""));
                cell.setCellStyle(cellStyle);
                for (int index = 0; index < objs.length; index++) {
                    cell = row.createCell(columnNum++);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(new XSSFRichTextString(CommUtil.null2String(objs[index])));
                }
                if (rowNum % 100 == 0) {
                    try {
                        sheet.flushRows();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                rowNum++;
            }
        }
    }

    //=============================================导出Excel区域结束======================================================//
    @SecurityMapping(res_name = "查看", res_url = "/admin/user/user_view.htm*", res_group = "人员档案")
    @RequestMapping({ "/admin/user/user_view.htm" })
    public ModelAndView user_view(HttpServletRequest request, HttpServletResponse response,
                                  String currentPage, Long id) {

        String target_url = CommUtil.getURL(request) + "/admin/user/user_list.htm?currentPage="
                            + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "用户标识为空", request, response);
        }
        User user = userService.getUser(id);
        if (user == null) {
            return CommUtil.errorPage(target_url, "找不到用户记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/user/user_view.html", request, response);

        List<Department> departments = departmentService.queryAllDepartment();
        List<Duty> dutys = dutyService.queryAllDuty();
        mv.addObject("obj", user);
        mv.addObject("departments", departments);
        mv.addObject("dutys", dutys);
        mv.addObject("currentPage", currentPage);
        mv.addObject("user_relationship_map", Constants.USER_RELATIONSHIP_MAP);
        mv.addObject("user_status_map", Constants.USER_STATUS_MAP);//用户状态
        mv.addObject("rgs", resGroupService.queryAllResGroup());
        mv.addObject("userResIds", userResService.queryUserResIds(user.getId()));
        mv.addObject("user_status_map", Constants.USER_STATUS_MAP);
        return mv;
    }

    /**
     * 添加用户
     */
    @SecurityMapping(res_name = "添加", res_url = "/admin/user/user_add.htm*", res_group = "人员档案")
    @RequestMapping({ "/admin/user/user_add.htm" })
    public ModelAndView user_add(HttpServletRequest request, HttpServletResponse response,
                                 String pageSize, String currentPage) {
        ModelAndView mv = new JModelAndView("admin/user/user_add.html", request, response);
        List<Department> departments = departmentService.queryAllDepartment();
        List<Duty> dutys = dutyService.queryAllDuty();
        List<Section> sections = sectionService.queryAllSection();
        List<SubSection> subSections = subSectionService.queryAllSubSection();
        
        mv.addObject("departments", departments);
        mv.addObject("dutys", dutys);
        mv.addObject("currentPage", currentPage);
        mv.addObject("user_relationship_map", Constants.USER_RELATIONSHIP_MAP);
        mv.addObject("user_status_map", Constants.USER_STATUS_MAP);//用户状态
        mv.addObject("rgs", resGroupService.queryAllResGroup());
        mv.addObject("user_status_map", Constants.USER_STATUS_MAP);
        mv.addObject("sections", sections);
        mv.addObject("subSections", subSections);
        return mv;
    }

    /**
     * 添加用户保存
     */
    @RequestMapping({ "/admin/user/user_add_save.htm" })
    public void user_add_save(HttpServletRequest request, HttpServletResponse response,
                              String pageSize, String currentPage, Long department_id,
                              Long duty_id, String res_ids,Long section_id,Long sub_section_id) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "用户保存成功");
        try {
            WebForm wf = new WebForm();
            User user = (User) wf.toPo(request, User.class);
            user.setPassword(Constants.DEFAULT_PASSWORD);
            rmg = userService.add_save(user, department_id, duty_id, res_ids,section_id,sub_section_id);
        } catch (Exception e) {
            CommUtil.setResultMsgData(rmg, false, "用户保存失败");
        }
        rmg.setData(CommUtil.getURL(request) + "/admin/user/user_list.htm?currentPage="
                    + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    @SecurityMapping(res_name = "编辑", res_url = "/admin/user/user_edit.htm*", res_group = "人员档案")
    @RequestMapping({ "/admin/user/user_edit.htm" })
    public ModelAndView user_edit(HttpServletRequest request, HttpServletResponse response,
                                  String currentPage, Long id) {

        String target_url = CommUtil.getURL(request) + "/admin/user/user_list.htm?currentPage="
                            + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "用户标识为空", request, response);
        }
        User user = userService.getUser(id);
        if (user == null) {
            return CommUtil.errorPage(target_url, "找不到用户记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/user/user_edit.html", request, response);
        List<Department> departments = departmentService.queryAllDepartment();
        List<Duty> dutys = null;
        if (user.getDepartment() != null && user.getDepartment().getId() != null) {
            dutys = dutyService.queryDutyUnderDepartment(user.getDepartment().getId());
        } else {
            dutys = dutyService.queryAllDuty();
        }
        List<Section> sections = sectionService.queryAllSection();
        List<SubSection> subSections = null;
        if (user.getSection() != null && user.getSection().getId() != null) {
        	subSections = subSectionService.querySubSectionUnderSection(user.getSection().getId());
		}else {
			subSections = subSectionService.queryAllSubSection();
		}
        mv.addObject("obj", user);
        mv.addObject("departments", departments);
        mv.addObject("dutys", dutys);
        mv.addObject("currentPage", currentPage);
        mv.addObject("user_relationship_map", Constants.USER_RELATIONSHIP_MAP);
        mv.addObject("user_status_map", Constants.USER_STATUS_MAP);//用户状态
        mv.addObject("user_dimission_reason_map", Constants.DIMISSION_REASON_MAP);//离职原因
        mv.addObject("rgs", resGroupService.queryAllResGroup());
        mv.addObject("userResIds", userResService.queryUserResIds(user.getId()));
        mv.addObject("user_status_map", Constants.USER_STATUS_MAP);
        mv.addObject("sections", sections);
        mv.addObject("subSections", subSections);
        return mv;
    }

    /**
     * 用户编辑保存
     */
    @RequestMapping({ "/admin/user/user_edit_save.htm" })
    public void user_edit_save(HttpServletRequest request, HttpServletResponse response,
                               String pageSize, String currentPage, Long id, Long department_id,
                               Long duty_id, String res_ids , String dimission_reason,
                               String dimission_time,Long section_id,Long sub_section_id) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "用户编辑保存成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "用户标识为空");
        }
        if (rmg.getResult()) {
            try {
                WebForm wf = new WebForm();
                User dbUser = userService.getUser(id);
                Integer db_user_status = dbUser.getUser_status();
                User user = (User) wf.toPo(request, dbUser);
                rmg = userService.edit_save(user, department_id, duty_id,
                		res_ids, db_user_status,dimission_reason,dimission_time,section_id,sub_section_id);
            } catch (Exception e) {
                CommUtil.setResultMsgData(rmg, false, "用户编辑保存失败");
            }
        }
        rmg.setData(CommUtil.getURL(request) + "/admin/user/user_list.htm?currentPage="
                    + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * 逻辑删除用户
     */
    @SecurityMapping(res_name = "删除", res_url = "/admin/user/user_delete.htm*", res_group = "人员档案")
    @RequestMapping({ "/admin/user/user_delete.htm" })
    public ModelAndView user_delete(HttpServletRequest request, HttpServletResponse response,
                                    Long id, String currentPage) {
        String target_url = CommUtil.getURL(request) + "/admin/user/user_list.htm?currentPage="
                            + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "用户标识为空", request, response);
        }
        User user = userService.getUser(id);
        if (user == null) {
            return CommUtil.errorPage(target_url, "找不到用户记录", request, response);
        }
        try {
            userService.delUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return CommUtil.errorPage(target_url, "用户删除失败", request, response);
        }
        return CommUtil.successPage(target_url, "用户删除成功", request, response);
    }

    @SecurityMapping(res_name = "重置密码", res_url = "/admin/user/admin_reset_user_password.htm*", res_group = "人员档案")
    @RequestMapping({ "/admin/user/admin_reset_user_password.htm" })
    public ModelAndView admin_reset_user_password(HttpServletRequest request,
                                                  HttpServletResponse response, Long id,
                                                  String currentPage) {
        String target_url = CommUtil.getURL(request) + "/admin/user/user_list.htm?currentPage="
                            + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "用户标识为空", request, response);
        }
        User user = userService.getUser(id);
        if (user == null) {
            return CommUtil.errorPage(target_url, "找不到用户记录", request, response);
        }
        try {
            user.setPassword(Md5.getMd5String(Constants.DEFAULT_PASSWORD).toLowerCase());
            userService.updateUser(user);
        } catch (Exception e) {
            return CommUtil.errorPage(target_url, "密码重置失败", request, response);
        }
        return CommUtil.successPage(target_url, "密码重置成功", request, response);
    }

    /**
     * 管理员重置用户密码
     */
    @RequestMapping({ "/admin/user/admin_reset_user_password_save.htm" })
    public void admin_reset_user_password_save(HttpServletRequest request,
                                               HttpServletResponse response, String currentPage,
                                               Long id, String password) {

        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "重置密码保存成功");
        if (id == null) {
            CommUtil.setResultMsgData(rmg, false, "用户标识为空");
        }
        if (rmg.getResult()) {
            User user = userService.getUser(id);
            if (CommUtil.isBlank(password)) {
                password = Constants.DEFAULT_PASSWORD;
            }
            user.setPassword(Md5.getMd5String(password).toLowerCase());
            try {
                userService.updateUser(user);
            } catch (Exception e) {
                CommUtil.setResultMsgData(rmg, false, "重置密码失败");
            }
        }
        rmg.setData(CommUtil.getURL(request) + "/admin/user/user_list.htm?currentPage="
                    + currentPage);
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    /**
     * ajax查找工作证号
     */
    @RequestMapping({ "/admin/user/ajaxQueryUserCardNumber.htm" })
    public void ajaxQueryUserCardNumber(HttpServletRequest request, HttpServletResponse response,
                                        Long id) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "获取工作证号成功");
        try {
            User user = userService.getUser(id);
            String user_card_number = user.getCard_number();
            rmg.setData(user_card_number);
        } catch (Exception e) {
            CommUtil.setResultMsgData(rmg, false, "获取工作证号失败");
        }
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    @RequestMapping({ "/admin/user/ajax_get_user_names.htm" })
    public void ajax_get_user_names(HttpServletRequest request, HttpServletResponse response,
                                    String user_ids) {
        ResultMsg rmg = CommUtil.setResultMsgData(null, true, "获取用户名成功");
        try {
            rmg.setData(userService.getUserNames(user_ids));
        } catch (Exception e) {
            CommUtil.setResultMsgData(rmg, false, "获取用户名失败");
        }
        JsonUtil.writeMsg(response, JsonUtil.write2JsonStr(rmg));
    }

    @SecurityMapping(res_name = "档案编辑", res_url = "/admin/user/user_file_edit.htm*", res_group = "人员档案")
    @RequestMapping({ "/admin/user/user_file_edit.htm" })
    public ModelAndView user_file_edit(HttpServletRequest request, HttpServletResponse response,
                                       Long id, String currentPage) {
        String target_url = CommUtil.getURL(request) + "/admin/user/user_list.htm?currentPage="
                            + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "找不到用户记录", request, response);
        }
        User user = userService.getUser(id);
        if (user == null || user.getId() == null) {
            return CommUtil.errorPage(target_url, "找不到用户记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/user/user_file/user_file_edit.html", request,
            response);
        mv.addObject("user", user);
        mv.addObject("userWorkList", userWorkService.queryUserWork(user.getId()));//工作经历
        mv.addObject("userEducationList", userEducationService.queryUserEducation(user.getId()));//教育经历
        mv.addObject("userSchoolawardList",
            userSchoolawardService.queryUserSchoolaward(user.getId()));//所获奖项
        mv.addObject("userFamilyMemberList",
            userFamilyMemberService.queryUserFamilyMember(user.getId()));//家庭成员
        mv.addObject("userContractRenewalList",
            userContractRenewalService.queryUserContractRenewal(user.getId()));//续签合同

        mvAddAttachments(mv, "", user);
        mv.addObject("user_relationship_map", Constants.USER_RELATIONSHIP_MAP);//员工关系
        mv.addObject("blood_type_map", Constants.BLOOD_TYPE_MAP);//血型
        mv.addObject("sex_map", Constants.SEX_MAP);//性别
        mv.addObject("political_status_map", Constants.POLITICAL_STATUS_MAP);//政治面貌
        mv.addObject("marriage_status_map", Constants.MARRIAGE_STATUS_MAP);//婚姻状况
        mv.addObject("constellation_no_date_map", Constants.CONSTELLATION_NO_DATE_MAP);//星座
        mv.addObject("have_or_not_map", Constants.HAVE_OR_NOT_MAP);//有无
        mv.addObject("domicile_type_map", Constants.DOMICILE_TYPE_MAP);//户口性质
        mv.addObject("ID_type_map", Constants.ID_TYPE_MAP);//身份证
        mv.addObject("work_type_map", Constants.WORK_TYPE_MAP);//工作经历
        mv.addObject("edu_degree_map", Constants.EDU_DEGREE_MAP);//学历
        mv.addObject("family_relationship_map", Constants.FAMILY_RELATIONSHIP_MAP);//家庭成员与本人关系
        mv.addObject("contract_type_map", Constants.CONTRACT_TYPE_MAP);//合同类型
        return mv;
    }

    private void mvAddAttachments(ModelAndView mv, String photo_column, User user) {
        UserPhoto userPhoto = user.getUserPhoto();
        if (userPhoto == null || userPhoto.getId() == null) {
            return;
        }
        userPhoto = userPhotoService.getUserPhoto(userPhoto.getId());

        List<Attachments> idphotoAttachments = attachmentsService.getAttachmentsByIds(userPhoto
            .getID_attach_ids());
        List<Attachments> degreephotoAttachments = attachmentsService.getAttachmentsByIds(userPhoto
            .getDegree_attach_ids());
        List<Attachments> bluetwoinchphotoAttachments = attachmentsService
            .getAttachmentsByIds(userPhoto.getBlue_small_two_inch_attach_ids());
        List<Attachments> whitetwoinchphotoAttachments = attachmentsService
            .getAttachmentsByIds(userPhoto.getWhite_small_two_inch_attach_ids());
        List<Attachments> othersphotoAttachments = attachmentsService.getAttachmentsByIds(userPhoto
            .getOthers_attach_ids());

        if ("idphoto".equalsIgnoreCase(photo_column)) {
            mv.addObject("attachments", idphotoAttachments);
        } else if ("degreephoto".equalsIgnoreCase(photo_column)) {
            mv.addObject("attachments", degreephotoAttachments);
        } else if ("bluetwoinchphoto".equalsIgnoreCase(photo_column)) {
            mv.addObject("attachments", bluetwoinchphotoAttachments);
        } else if ("whitetwoinchphoto".equalsIgnoreCase(photo_column)) {
            mv.addObject("attachments", whitetwoinchphotoAttachments);
        } else if ("othersphoto".equalsIgnoreCase(photo_column)) {
            mv.addObject("attachments", othersphotoAttachments);
        }
        mv.addObject("idphotoAttachments", idphotoAttachments);
        mv.addObject("degreephotoAttachments", degreephotoAttachments);
        mv.addObject("bluetwoinchphotoAttachments", bluetwoinchphotoAttachments);
        mv.addObject("whitetwoinchphotoAttachments", whitetwoinchphotoAttachments);
        mv.addObject("othersphotoAttachments", othersphotoAttachments);

    }

    @SecurityMapping(res_name = "档案打印", res_url = "/admin/user/user_file_print_view.htm*", res_group = "人员档案")
    @RequestMapping({ "/admin/user/user_file_print_view.htm" })
    public ModelAndView user_file_print_view(HttpServletRequest request,
                                             HttpServletResponse response, Long id,
                                             String currentPage) {
        String target_url = CommUtil.getURL(request) + "/admin/user/user_list.htm?currentPage="
                            + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "找不到用户记录", request, response);
        }
        User user = userService.getUser(id);
        if (user == null || user.getId() == null) {
            return CommUtil.errorPage(target_url, "找不到用户记录", request, response);
        }
        ModelAndView mv = new JModelAndView("admin/user/user_file_print_view.html", request,
            response);
        mv.addObject("user", user);
        mv.addObject("userWorkList", userWorkService.queryUserWork(user.getId()));//工作经历
        mv.addObject("userEducationList", userEducationService.queryUserEducation(user.getId()));//教育经历
        mv.addObject("userSchoolawardList",
            userSchoolawardService.queryUserSchoolaward(user.getId()));//所获奖项
        mv.addObject("userFamilyMemberList",
            userFamilyMemberService.queryUserFamilyMember(user.getId()));//家庭成员
        mv.addObject("userContractRenewalList",
            userContractRenewalService.queryUserContractRenewal(user.getId()));//续签合同

        mv.addObject("user_relationship_map", Constants.USER_RELATIONSHIP_MAP);//员工关系
        mv.addObject("blood_type_map", Constants.BLOOD_TYPE_MAP);//血型
        mv.addObject("sex_map", Constants.SEX_MAP);//性别
        mv.addObject("political_status_map", Constants.POLITICAL_STATUS_MAP);//政治面貌
        mv.addObject("marriage_status_map", Constants.MARRIAGE_STATUS_MAP);//婚姻状况
        mv.addObject("constellation_no_date_map", Constants.CONSTELLATION_NO_DATE_MAP);//星座
        mv.addObject("have_or_not_map", Constants.HAVE_OR_NOT_MAP);//有无
        mv.addObject("domicile_type_map", Constants.DOMICILE_TYPE_MAP);//户口性质
        mv.addObject("ID_type_map", Constants.ID_TYPE_MAP);//身份证
        mv.addObject("work_type_map", Constants.WORK_TYPE_MAP);//工作经历
        mv.addObject("edu_degree_map", Constants.EDU_DEGREE_MAP);//学历
        mv.addObject("family_relationship_map", Constants.FAMILY_RELATIONSHIP_MAP);//家庭成员与本人关系
        mv.addObject("contract_type_map", Constants.CONTRACT_TYPE_MAP);//合同类型
        mv.addObject("currentPage", currentPage);
        if (user.getUserPhoto() != null) {
            List<Attachments> attachs = attachmentsService.getAttachmentsByIds(user.getUserPhoto()
                .getWhite_small_two_inch_attach_ids());
            if (attachs != null && attachs.size() > 0) {
                mv.addObject("attach", attachs.get(0));
            }
        }
        return mv;
    }

    @SecurityMapping(res_name = "归档", res_url = "/admin/user/user_file_archive.htm*", res_group = "人员档案")
    @RequestMapping({ "/admin/user/user_file_archive.htm" })
    public ModelAndView user_file_archive(HttpServletRequest request, HttpServletResponse response,
                                          Long id, String currentPage) {
        String target_url = CommUtil.getURL(request) + "/admin/user/user_list.htm?currentPage="
                            + currentPage;
        if (id == null) {
            return CommUtil.errorPage(target_url, "用户标识为空", request, response);
        }
        User user = userService.getUser(id);
        if (user == null) {
            return CommUtil.errorPage(target_url, "找不到用户记录", request, response);
        }
        try {
            user.setArchive_status(true);
            userService.updateUser(user);
        } catch (Exception e) {
            return CommUtil.errorPage(target_url, "归档失败", request, response);
        }
        return CommUtil.successPage(target_url, "归档成功", request, response);
    }

    @SecurityMapping(res_name = "导出Word", res_url = "/admin/user/user_exportWord.htm*", res_group = "人员档案")
    @RequestMapping("/admin/user/user_exportWord.htm")
    public void user_exportWord(HttpServletRequest request, HttpServletResponse response, Long id,
                                String currentPage) {
        File file = null;
        InputStream is = null;
        OutputStream out = null;

        try {
            request.setCharacterEncoding("utf-8");
            User user = userService.getUser(id);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("user_relationship", CommUtil.null2String(Constants.USER_RELATIONSHIP_MAP
                .get(CommUtil.null2String(user.getUser_relationship()))));//聘用类型
            //所属科组
            if (user.getDepartment() != null
                && CommUtil.isNotNull(user.getDepartment().getDepartment_name())) {
                map.put("department_name", user.getDepartment().getDepartment_name());
            } else {
                map.put("department_name", "");
            }
            //岗位
            if (user.getDuty() != null && CommUtil.isNotNull(user.getDuty().getDuty_name())) {
                map.put("duty_name", user.getDuty().getDuty_name());
            } else {
                map.put("duty_name", "");
            }
            map.put("card_number", CommUtil.null2String(user.getCard_number())); //工作证号
            map.put("true_name", CommUtil.null2String(user.getTrue_name()));//姓名
            map.put("birthday", CommUtil.parseDateTime("yyyy-MM-dd", user.getBirthday()));//出生日期
            map.put("mobile", CommUtil.null2String(user.getMobile()));
            map.put("tel_number", CommUtil.null2String(user.getTel_number()));
            map.put("email", CommUtil.null2String(user.getEmail()));
            if (user.getUserBase() != null) {
                UserBase userBase = user.getUserBase();
                map.put("sex", CommUtil.null2String(Constants.SEX_MAP.get(CommUtil
                    .null2String(userBase.getSex()))));
                map.put("nation", CommUtil.null2String(userBase.getNation()));
                map.put("birth_place", CommUtil.null2String(userBase.getBirth_place()));
                map.put("blood_type", CommUtil.null2String(Constants.BLOOD_TYPE_MAP.get(CommUtil
                    .null2String(userBase.getBlood_type()))));
                map.put("constellation", CommUtil.null2String(Constants.CONSTELLATION_NO_DATE_MAP
                    .get(CommUtil.null2String(userBase.getConstellation()))));
                map.put("age", CommUtil.null2String(userBase.getAge()));
                map.put("native_place", CommUtil.null2String(userBase.getNative_place()));
                map.put("ID_type", CommUtil.null2String(Constants.ID_TYPE_MAP.get(CommUtil
                    .null2String(userBase.getID_type()))));
                map.put("ID_number", CommUtil.null2String(userBase.getID_number()));
                map.put("political_status", CommUtil.null2String(Constants.POLITICAL_STATUS_MAP
                    .get(CommUtil.null2String(userBase.getPolitical_status()))));
                map.put("join_party_date",
                    CommUtil.parseShortDateTime(userBase.getJoin_party_date()));
                map.put("first_work_date",
                    CommUtil.parseShortDateTime(userBase.getFirst_work_date()));
                map.put("marriage_status", CommUtil.null2String(Constants.MARRIAGE_STATUS_MAP
                    .get(CommUtil.null2String(userBase.getMarriage_status()))));
                map.put("condition_of_children",
                    CommUtil.null2String(userBase.getCondition_of_children()));
                map.put("work_years", CommUtil.null2String(userBase.getWork_years()));
                map.put(
                    "highest_education",
                    CommUtil.null2String(userBase.getHighest_education())
                            + " "
                            + CommUtil.parseShortDateTime(userBase.getHighest_education_gain_time()));
                map.put(
                    "highest_offering",
                    CommUtil.null2String(userBase.getHighest_offering()) + " "
                            + CommUtil.parseShortDateTime(userBase.getHighest_offering_gain_time()));
                map.put("foreign_language_level",
                    CommUtil.null2String(userBase.getForeign_language_level()));
                map.put("job_titles", CommUtil.null2String(userBase.getJob_titles()));
                map.put("job_titles_level", CommUtil.null2String(userBase.getJob_titles_level()));
                map.put("job_titles_evaluation_time",
                    CommUtil.parseShortDateTime(userBase.getJob_titles_evaluation_time()));
                map.put("computer_skill", CommUtil.null2String(userBase.getComputer_skill()));
                map.put("job_titles_evaluation_unit",
                    CommUtil.null2String(userBase.getJob_titles_evaluation_unit()));
                map.put("job_titles_cert_id",
                    CommUtil.null2String(userBase.getJob_titles_cert_id()));
                map.put("is_have_press_card", CommUtil.null2String(Constants.HAVE_OR_NOT_MAP
                    .get(CommUtil.null2String(userBase.getIs_have_press_card()))));
                map.put("press_card_no", CommUtil.null2String(userBase.getPress_card_no()));
                map.put("home_address", CommUtil.null2String(userBase.getHome_address()));
                map.put("home_postcode", CommUtil.null2String(userBase.getHome_postcode()));
                map.put("present_address", CommUtil.null2String(userBase.getPresent_address()));
                map.put("present_postcode", CommUtil.null2String(userBase.getPresent_postcode()));
                map.put("personnel_file_address",
                    CommUtil.null2String(userBase.getPersonnel_file_address()));
                map.put("personnel_file_postcode",
                    CommUtil.null2String(userBase.getPersonnel_file_postcode()));
                map.put("domicile_place", CommUtil.null2String(userBase.getDomicile_place()));
                map.put("domicile_type", CommUtil.null2String(Constants.DOMICILE_TYPE_MAP
                    .get(CommUtil.null2String(userBase.getDomicile_type()))));
                map.put("emergency_contact_name",
                    CommUtil.null2String(userBase.getEmergency_contact_name()));
                map.put("emergency_contact_mobile",
                    CommUtil.null2String(userBase.getEmergency_contact_mobile()));
                map.put("hobby_and_speciality",
                    CommUtil.null2String(userBase.getHobby_and_speciality()));

            } else {
                map.put("sex", "");
                map.put("nation", "");
                map.put("birth_place", "");
                map.put("blood_type", "");
                map.put("constellation", "");
                map.put("age", "");
                map.put("native_place", "");
                map.put("ID_type", "");
                map.put("ID_number", "");
                map.put("political_status", "");
                map.put("join_party_date", "");
                map.put("first_work_date", "");
                map.put("marriage_status", "");
                map.put("condition_of_children", "");
                map.put("work_years", "");
                map.put("highest_education", "");
                map.put("highest_offering", "");
                map.put("foreign_language_level", "");
                map.put("job_titles", "");
                map.put("job_titles_level", "");
                map.put("job_titles_evaluation_time", "");
                map.put("computer_skill", "");
                map.put("job_titles_evaluation_unit", "");
                map.put("job_titles_cert_id", "");
                map.put("is_have_press_card", "");
                map.put("press_card_no", "");
                map.put("home_address", "");
                map.put("home_postcode", "");
                map.put("present_address", "");
                map.put("present_postcode", "");
                map.put("personnel_file_address", "");
                map.put("personnel_file_postcode", "");
                map.put("domicile_place", "");
                map.put("domicile_type", "");
                map.put("emergency_contact_name", "");
                map.put("emergency_contact_mobile", "");
                map.put("hobby_and_speciality", "");
            }
            if (user.getUserContract() != null) {
                UserContract userContract = user.getUserContract();
                map.put("entry_time", CommUtil.parseShortDateTime(userContract.getEntry_time()));
                map.put("report_time", CommUtil.parseShortDateTime(userContract.getReport_time()));
                map.put("positive_time",
                    CommUtil.parseShortDateTime(userContract.getPositive_time()));
                map.put("first_contract_duration",
                    CommUtil.null2String(userContract.getFirst_contract_duration()) + " (年)");
                map.put("first_sign_time",
                    CommUtil.parseShortDateTime(userContract.getFirst_sign_time()));
                map.put("first_expiration_time",
                    CommUtil.parseShortDateTime(userContract.getFirst_expiration_time()));
                map.put("contract_type", CommUtil.null2String(Constants.CONTRACT_TYPE_MAP
                    .get(CommUtil.null2String(userContract.getContract_type()))));
                map.put("meal_card_number",
                    CommUtil.null2String(userContract.getMeal_card_number()));
                map.put("business_card_number",
                    CommUtil.null2String(userContract.getBusiness_card_number()));
            } else {
                map.put("entry_time", "");
                map.put("report_time", "");
                map.put("positive_time", "");
                map.put("first_contract_duration", "");
                map.put("first_sign_time", "");
                map.put("first_expiration_time", "");
                map.put("contract_type", "");
                map.put("meal_card_number", "");
                map.put("business_card_number", "");
            }

            map.put("userWorkList", covertUserWorkList(userWorkService.queryUserWork(user.getId())));//工作经历
            map.put("userEducationList",
                covertUserEducationList(userEducationService.queryUserEducation(user.getId())));//教育经历
            map.put(
                "userSchoolawardList",
                covertUserSchoolawardList(userSchoolawardService.queryUserSchoolaward(user.getId())));//所获奖项
            map.put("userFamilyMemberList", covertUserFamilyMemberList(userFamilyMemberService
                .queryUserFamilyMember(user.getId())));//家庭成员
            map.put("userContractRenewalList",
                covertUserContractRenewalList(userContractRenewalService
                    .queryUserContractRenewal(user.getId())));//续签合同

            String filePath = request.getServletContext().getRealPath("/") + "upload/word";
            String fileOnlyName = user.getTrue_name() + "员工信息登记表" + new Date().getTime() + ".doc";
            filePath = URLEncoder.encode(filePath, "UTF-8");
            fileOnlyName = URLEncoder.encode(fileOnlyName, "UTF-8");

            file = WordUtil.createWord(map, "userFileWordTemplate.ftl", filePath, fileOnlyName);
            is = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            String fileName = user.getTrue_name() + "员工信息登记表" + new Date().getTime() + ".doc";
            fileName = CommUtil.encodeExportFileName(request, fileName);
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

            out = response.getOutputStream();
            byte[] buffer = new byte[1024];//缓冲区
            int bytesToRead = -1;
            while ((bytesToRead = is.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
                out.flush();
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
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
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    is = null;
                }
            }
            if (file != null) {
                file.delete();
            }
        }
    }

    private List<Map<String, String>> covertUserWorkList(List<UserWork> userWorkList)
                                                                                     throws Exception {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        if (userWorkList == null || userWorkList.isEmpty()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("work_time_begin", "");
            map.put("work_time_end", "");
            map.put("work_comp_name", "");
            map.put("work_duty_name", "");
            map.put("work_place", "");
            map.put("work_type", "");
            list.add(map);
        } else {
            for (UserWork userWork : userWorkList) {
                if (!userWork.getDisabled()) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("work_time_begin",
                        CommUtil.parseShortDateTime(userWork.getWork_time_begin()));
                    map.put("work_time_end",
                        CommUtil.parseShortDateTime(userWork.getWork_time_end()));
                    map.put("work_comp_name", CommUtil.null2String(userWork.getWork_comp_name()));
                    map.put("work_duty_name", CommUtil.null2String(userWork.getWork_duty_name()));
                    map.put("work_place", CommUtil.null2String(userWork.getWork_place()));
                    map.put("work_type", CommUtil.null2String(Constants.WORK_TYPE_MAP.get(CommUtil
                        .null2String(userWork.getWork_type()))));
                    list.add(map);
                }
            }
        }
        return list;
    }

    private List<Map<String, String>> covertUserEducationList(List<UserEducation> userEducationList)
                                                                                                    throws Exception {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        if (userEducationList == null || userEducationList.isEmpty()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("admission_date", "");
            map.put("graduation_date", "");
            map.put("edu_school_name", "");
            map.put("edu_major", "");
            map.put("edu_place", "");
            map.put("edu_degree", "");
            map.put("edu_offering", "");
            list.add(map);
        } else {
            for (UserEducation userEducation : userEducationList) {
                if (!userEducation.getDisabled()) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("admission_date",
                        CommUtil.parseShortDateTime(userEducation.getAdmission_date()));
                    map.put("graduation_date",
                        CommUtil.parseShortDateTime(userEducation.getGraduation_date()));
                    map.put("edu_school_name",
                        CommUtil.null2String(userEducation.getEdu_school_name()));
                    map.put("edu_major", CommUtil.null2String(userEducation.getEdu_major()));
                    map.put("edu_place", CommUtil.null2String(userEducation.getEdu_place()));
                    map.put("edu_degree", CommUtil.null2String(Constants.EDU_DEGREE_MAP
                        .get(CommUtil.null2String(userEducation.getEdu_degree()))));
                    map.put("edu_offering", CommUtil.null2String(userEducation.getEdu_offering()));
                    list.add(map);
                }
            }
        }
        return list;
    }

    private List<Map<String, String>> covertUserSchoolawardList(List<UserSchoolaward> userSchoolawardList)
                                                                                                          throws Exception {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        if (userSchoolawardList == null || userSchoolawardList.isEmpty()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("award_date", "");
            map.put("award_name", "");
            map.put("award_level", "");
            map.put("award_unit", "");
            list.add(map);
        } else {
            for (UserSchoolaward userSchoolaward : userSchoolawardList) {
                if (!userSchoolaward.getDisabled()) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("award_date",
                        CommUtil.parseShortDateTime(userSchoolaward.getAward_date()));
                    map.put("award_name", CommUtil.null2String(userSchoolaward.getAward_name()));
                    map.put("award_level", CommUtil.null2String(userSchoolaward.getAward_level()));
                    map.put("award_unit", CommUtil.null2String(userSchoolaward.getAward_unit()));
                    list.add(map);
                }
            }
        }
        return list;
    }

    private List<Map<String, String>> covertUserFamilyMemberList(List<UserFamilyMember> userFamilyMemberList)
                                                                                                             throws Exception {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        if (userFamilyMemberList == null || userFamilyMemberList.isEmpty()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("family_member_name", "");
            map.put("family_relationship", "");
            map.put("family_member_unit", "");
            map.put("family_member_duty", "");
            map.put("family_member_mobile", "");
            list.add(map);
        } else {
            for (UserFamilyMember userFamilyMember : userFamilyMemberList) {
                if (!userFamilyMember.getDisabled()) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("family_member_name",
                        CommUtil.null2String(userFamilyMember.getFamily_member_name()));
                    map.put("family_relationship", CommUtil
                        .null2String(Constants.FAMILY_RELATIONSHIP_MAP.get(CommUtil
                            .null2String(userFamilyMember.getFamily_relationship()))));
                    map.put("family_member_unit",
                        CommUtil.null2String(userFamilyMember.getFamily_member_unit()));
                    map.put("family_member_duty",
                        CommUtil.null2String(userFamilyMember.getFamily_member_duty()));
                    map.put("family_member_mobile",
                        CommUtil.null2String(userFamilyMember.getFamily_member_mobile()));
                    list.add(map);
                }
            }
        }
        return list;
    }

    private List<Map<String, String>> covertUserContractRenewalList(List<UserContractRenewal> userContractRenewalList)
                                                                                                                      throws Exception {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        if (userContractRenewalList == null || userContractRenewalList.isEmpty()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("renewal_contract_duration", "");
            map.put("renewal_sign_time", "");
            map.put("renewal_expiration_time", "");
            list.add(map);
        } else {
            for (UserContractRenewal userContractRenewal : userContractRenewalList) {
                if (!userContractRenewal.getDisabled()) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("renewal_contract_duration",
                        CommUtil.null2String(userContractRenewal.getRenewal_contract_duration())
                                + " (年)");
                    map.put("renewal_sign_time",
                        CommUtil.parseShortDateTime(userContractRenewal.getRenewal_sign_time()));
                    map.put("renewal_expiration_time", CommUtil
                        .parseShortDateTime(userContractRenewal.getRenewal_expiration_time()));
                    list.add(map);
                }
            }
        }
        return list;
    }
}
