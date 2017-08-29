package com.yuyu.soft.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 简历表
 *                       
 * @Filename: Resume.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_resume")
public class Resume extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long           serialVersionUID          = 1L;

    private String                      mobile;                                                            //手机号
    private String                      name;                                                              //姓名
    private Integer                     sex;                                                               //性别：1.男 2.女
    private Date                        birthday;                                                          //出生日期

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "duty_id")
    private Duty                        duty;                                                              //应聘岗位
    private String                      available_time;                                                    //可到岗时间
    @Column(precision = 12, scale = 2)
    private BigDecimal                  current_salary;                                                    //目前薪资（税前）
    @Column(precision = 12, scale = 2)
    private BigDecimal                  expect_salary;                                                     //期望薪资（税前）
    private Integer                     political_status;                                                  //政治面貌：1.中共党员 2.共青团员 3.群众 4.其他
    private String                      nation;                                                            //民族
    private Integer                     marriage_status;                                                   //婚姻状况：1.未婚 2.已婚 3.离异 4.丧偶
    private String                      condition_of_children;                                             //子女情况：无子女填“无”，有子女填“几子几女”
    private String                      nationality;                                                       //国籍
    @Column(precision = 4, scale = 1)
    private BigDecimal                  stature;                                                           //身高（cm）
    @Column(precision = 5, scale = 1)
    private BigDecimal                  body_weight;                                                       //体重（kg）
    private String                      health_condition;                                                  //健康状况
    private String                      past_illness_history;                                              //过往病史
    private String                      highest_education;                                                 //最高学历
    private String                      graduate_school;                                                   //毕业院校
    private String                      major;                                                             //专业
    private String                      foreign_language_level;                                            //外语种类及水平
    private String                      computer_skill;                                                    //计算机水平
    private String                      job_titles;                                                        //获得职称
    private String                      qualification_certificate;                                         //资格证书
    private String                      domicile_place;                                                    //户口所在地
    private String                      ID_number;                                                         //证件号码
    private String                      email;                                                             //邮箱
    private String                      home_address;                                                      //家庭地址
    private String                      present_address;                                                   //在京居住地址
    private String                      hobby_and_interest;                                                //兴趣爱好
    private String                      skills;                                                            //技能专长
    private Integer                     work_years;                                                        //工作年限
    private Integer                     same_industry_work_years;                                          //同行业工作年限
    private Integer                     resume_status;                                                     //简历状态：1.面试 2.未录用 3.已录用
    private String                      attach_ids;                                                        //上传图片

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY)
    @Where(clause = "disabled=0")
    private List<ResumeWork>            resumeWorkList            = new ArrayList<ResumeWork>();           //工作经历

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY)
    @Where(clause = "disabled=0")
    private List<ResumeEducation>       resumeEducationList       = new ArrayList<ResumeEducation>();      //教育经历

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY)
    @Where(clause = "disabled=0")
    private List<ResumeFamilyMember>    resumeFamilyMemberList    = new ArrayList<ResumeFamilyMember>();   //家庭成员

    @OneToOne(mappedBy = "resume", fetch = FetchType.EAGER)
    @Where(clause = "disabled=0")
    private ResumeOthers                resumeOthers;

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY)
    @Where(clause = "disabled=0")
    @JsonIgnore
    private List<CandidateHireApproval> candidateHireApprovalList = new ArrayList<CandidateHireApproval>();

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Duty getDuty() {
        return duty;
    }

    public void setDuty(Duty duty) {
        this.duty = duty;
    }

    public String getAvailable_time() {
        return available_time;
    }

    public void setAvailable_time(String available_time) {
        this.available_time = available_time;
    }

    public BigDecimal getCurrent_salary() {
        return current_salary;
    }

    public void setCurrent_salary(BigDecimal current_salary) {
        this.current_salary = current_salary;
    }

    public BigDecimal getExpect_salary() {
        return expect_salary;
    }

    public void setExpect_salary(BigDecimal expect_salary) {
        this.expect_salary = expect_salary;
    }

    public Integer getPolitical_status() {
        return political_status;
    }

    public void setPolitical_status(Integer political_status) {
        this.political_status = political_status;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Integer getMarriage_status() {
        return marriage_status;
    }

    public void setMarriage_status(Integer marriage_status) {
        this.marriage_status = marriage_status;
    }

    public String getCondition_of_children() {
        return condition_of_children;
    }

    public void setCondition_of_children(String condition_of_children) {
        this.condition_of_children = condition_of_children;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public BigDecimal getStature() {
        return stature;
    }

    public void setStature(BigDecimal stature) {
        this.stature = stature;
    }

    public BigDecimal getBody_weight() {
        return body_weight;
    }

    public void setBody_weight(BigDecimal body_weight) {
        this.body_weight = body_weight;
    }

    public String getHealth_condition() {
        return health_condition;
    }

    public void setHealth_condition(String health_condition) {
        this.health_condition = health_condition;
    }

    public String getPast_illness_history() {
        return past_illness_history;
    }

    public void setPast_illness_history(String past_illness_history) {
        this.past_illness_history = past_illness_history;
    }

    public String getHighest_education() {
        return highest_education;
    }

    public void setHighest_education(String highest_education) {
        this.highest_education = highest_education;
    }

    public String getGraduate_school() {
        return graduate_school;
    }

    public void setGraduate_school(String graduate_school) {
        this.graduate_school = graduate_school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getForeign_language_level() {
        return foreign_language_level;
    }

    public void setForeign_language_level(String foreign_language_level) {
        this.foreign_language_level = foreign_language_level;
    }

    public String getComputer_skill() {
        return computer_skill;
    }

    public void setComputer_skill(String computer_skill) {
        this.computer_skill = computer_skill;
    }

    public String getJob_titles() {
        return job_titles;
    }

    public void setJob_titles(String job_titles) {
        this.job_titles = job_titles;
    }

    public String getQualification_certificate() {
        return qualification_certificate;
    }

    public void setQualification_certificate(String qualification_certificate) {
        this.qualification_certificate = qualification_certificate;
    }

    public String getDomicile_place() {
        return domicile_place;
    }

    public void setDomicile_place(String domicile_place) {
        this.domicile_place = domicile_place;
    }

    public String getID_number() {
        return ID_number;
    }

    public void setID_number(String iD_number) {
        ID_number = iD_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHome_address() {
        return home_address;
    }

    public void setHome_address(String home_address) {
        this.home_address = home_address;
    }

    public String getPresent_address() {
        return present_address;
    }

    public void setPresent_address(String present_address) {
        this.present_address = present_address;
    }

    public String getHobby_and_interest() {
        return hobby_and_interest;
    }

    public void setHobby_and_interest(String hobby_and_interest) {
        this.hobby_and_interest = hobby_and_interest;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Integer getWork_years() {
        return work_years;
    }

    public void setWork_years(Integer work_years) {
        this.work_years = work_years;
    }

    public Integer getSame_industry_work_years() {
        return same_industry_work_years;
    }

    public void setSame_industry_work_years(Integer same_industry_work_years) {
        this.same_industry_work_years = same_industry_work_years;
    }

    public List<ResumeWork> getResumeWorkList() {
        return resumeWorkList;
    }

    public void setResumeWorkList(List<ResumeWork> resumeWorkList) {
        this.resumeWorkList = resumeWorkList;
    }

    public List<ResumeEducation> getResumeEducationList() {
        return resumeEducationList;
    }

    public void setResumeEducationList(List<ResumeEducation> resumeEducationList) {
        this.resumeEducationList = resumeEducationList;
    }

    public List<ResumeFamilyMember> getResumeFamilyMemberList() {
        return resumeFamilyMemberList;
    }

    public void setResumeFamilyMemberList(List<ResumeFamilyMember> resumeFamilyMemberList) {
        this.resumeFamilyMemberList = resumeFamilyMemberList;
    }

    public ResumeOthers getResumeOthers() {
        return resumeOthers;
    }

    public void setResumeOthers(ResumeOthers resumeOthers) {
        this.resumeOthers = resumeOthers;
    }

    public Integer getResume_status() {
        return resume_status;
    }

    public void setResume_status(Integer resume_status) {
        this.resume_status = resume_status;
    }

    public List<CandidateHireApproval> getCandidateHireApprovalList() {
        return candidateHireApprovalList;
    }

    public void setCandidateHireApprovalList(List<CandidateHireApproval> candidateHireApprovalList) {
        this.candidateHireApprovalList = candidateHireApprovalList;
    }

    public String getAttach_ids() {
        return attach_ids;
    }

    public void setAttach_ids(String attach_ids) {
        this.attach_ids = attach_ids;
    }
}