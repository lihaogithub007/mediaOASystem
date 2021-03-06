package com.yuyu.soft.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuyu.soft.base.annotation.FormIgnore;
import com.yuyu.soft.entity.common.CommonEntity;
import com.yuyu.soft.util.CommUtil;

/**
 * 用户表
 *                       
 * @Filename: User.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_user")
public class User extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long             serialVersionUID          = 1L;

    private String                        mobile;                                                              //手机号
    @FormIgnore
    private String                        password;                                                            //密码
    private String                        user_name;                                                           //用户名
    private String                        true_name;                                                           //真实姓名
    private String                        email;                                                               //邮箱
    private String                        card_number;                                                         //工作证号
    private Date                          birthday;                                                            //生日
    private String                        tel_number;                                                          //座机
    private Integer                       user_relationship;                                                   //聘用类型：0.台聘, 1.企聘, 2.视通公司聘, 3外籍, 4.索贝聘, 5.前卫聘
    private String                        remark;                                                              //备注
    private String                        tempate_style;                                                       //样式模板：default/gray/purple/navy-blue
    private Boolean                       archive_status;                                                      //归档状态：0.未归档 1.已归档
    private Integer                       user_status;                                                         //用户类型：1.实习、2.试用、3.在职、4.离职
    private Boolean                       is_synchronous;                                                      //是否是同步生成（0.否 1.是）

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonIgnore
    private Department                    department;                                                          //部门/科组
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    @JsonIgnore
    private Section                       section;                                                             //版块

    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "duty_id")
    @JsonIgnore
    private Duty                          duty;                                                                //岗位
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_section_id")
    @JsonIgnore
    private SubSection                          sub_section;                                                          //子版块


	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SmsRemindLogs>           smsRemindLogsList         = new ArrayList<SmsRemindLogs>();          //日程安排提醒日志

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ScheduleSmsRemind>       scheduleSmsRemindList     = new ArrayList<ScheduleSmsRemind>();      //日程安排提醒

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ContractSmsRemind>       contractSmsRemindList     = new ArrayList<ContractSmsRemind>();      //合同到期提醒

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<FixedAssets>             fixedAssetsList           = new ArrayList<FixedAssets>();            //固定资产

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Attachments>             attachmentsList           = new ArrayList<Attachments>();            //上传附件

    @OneToMany(mappedBy = "create_user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<InsideIncomeDispatches>  iidCreateUserList         = new ArrayList<InsideIncomeDispatches>(); //台内发文

    @OneToMany(mappedBy = "update_user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<InsideIncomeDispatches>  iidUpdateUserList         = new ArrayList<InsideIncomeDispatches>(); //台内发文

    @OneToMany(mappedBy = "create_user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OutsideIncomeDispatches> oidCreateUserList         = new ArrayList<OutsideIncomeDispatches>(); //台外发文

    @OneToMany(mappedBy = "update_user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OutsideIncomeDispatches> oidUpdateUserList         = new ArrayList<OutsideIncomeDispatches>(); //台外发文

    @OneToMany(mappedBy = "create_user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<EventApplyForm>          eafCreateUserList         = new ArrayList<EventApplyForm>();         //事项申请表

    @OneToMany(mappedBy = "update_user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<EventApplyForm>          eafUpdateUserList         = new ArrayList<EventApplyForm>();         //事项申请表

    @OneToMany(mappedBy = "leader_user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<EventApplyForm>          eafLeaderUserList         = new ArrayList<EventApplyForm>();         //事项申请表

    @OneToMany(mappedBy = "create_user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DepartmentAwards>        daCreateUserList          = new ArrayList<DepartmentAwards>();       //部门评奖表

    @OneToMany(mappedBy = "update_user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DepartmentAwards>        daUpdateUserList          = new ArrayList<DepartmentAwards>();       //部门评奖表

    @OneToMany(mappedBy = "awards_leader_user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DepartmentAwards>        daLeaderUserList          = new ArrayList<DepartmentAwards>();       //部门评奖表

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<UserRes>                 userResList               = new ArrayList<UserRes>();                //用户资源表

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private UserBase                      userBase;                                                            //人事基础信息

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Where(clause = "disabled=0")
    @JsonIgnore
    private List<UserWork>                userWorkList              = new ArrayList<UserWork>();               //人事档案工作经历

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Where(clause = "disabled=0")
    @JsonIgnore
    private List<UserEducation>           userEducationList         = new ArrayList<UserEducation>();          //人事档案教育经历

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Where(clause = "disabled=0")
    @JsonIgnore
    private List<UserSchoolaward>         userSchoolawardList       = new ArrayList<UserSchoolaward>();        //人事档案所获奖项

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Where(clause = "disabled=0")
    @JsonIgnore
    private List<UserFamilyMember>        userFamilyMemberList      = new ArrayList<UserFamilyMember>();       //人事档案家庭成员

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private UserContract                  userContract;                                                        //人事合同

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private UserPhoto                     userPhoto;                                                           //证件照

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<UserDimissionLogs>       userDimissionLogsList     = new ArrayList<UserDimissionLogs>();      //离职日志

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AttendanceRecord>        attendanceRecordList      = new ArrayList<AttendanceRecord>();       //考勤记录

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<VacationApply>           vacationApplyList         = new ArrayList<VacationApply>();          //请假申请

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<VacationApplyDetails>    vacationApplyDetailsList  = new ArrayList<VacationApplyDetails>();   //请假申请审批

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<VacationApplyApproval>   vacationApplyApprovalList = new ArrayList<VacationApplyApproval>();  //请假申请审批

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OvertimeRecord>          overtimeRecordList        = new ArrayList<OvertimeRecord>();         //加班记录

    public User() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTrue_name() {
        return true_name;
    }

    public void setTrue_name(String true_name) {
        this.true_name = true_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTel_number() {
        return tel_number;
    }

    public void setTel_number(String tel_number) {
        this.tel_number = tel_number;
    }

    public Integer getUser_relationship() {
        return user_relationship;
    }

    public void setUser_relationship(Integer user_relationship) {
        this.user_relationship = user_relationship;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Duty getDuty() {
        return duty;
    }

    public void setDuty(Duty duty) {
        this.duty = duty;
    }

    public String getTempate_style() {
        if (CommUtil.isBlank(tempate_style)) {
            setTempate_style("default");
        }
        return tempate_style;
    }

    public void setTempate_style(String tempate_style) {
        this.tempate_style = tempate_style;
    }

    public List<ScheduleSmsRemind> getScheduleSmsRemindList() {
        return scheduleSmsRemindList;
    }

    public void setScheduleSmsRemindList(List<ScheduleSmsRemind> scheduleSmsRemindList) {
        this.scheduleSmsRemindList = scheduleSmsRemindList;
    }

    public List<FixedAssets> getFixedAssetsList() {
        return fixedAssetsList;
    }

    public void setFixedAssetsList(List<FixedAssets> fixedAssetsList) {
        this.fixedAssetsList = fixedAssetsList;
    }

    public List<Attachments> getAttachmentsList() {
        return attachmentsList;
    }

    public void setAttachmentsList(List<Attachments> attachmentsList) {
        this.attachmentsList = attachmentsList;
    }

    public List<InsideIncomeDispatches> getIidCreateUserList() {
        return iidCreateUserList;
    }

    public void setIidCreateUserList(List<InsideIncomeDispatches> iidCreateUserList) {
        this.iidCreateUserList = iidCreateUserList;
    }

    public List<InsideIncomeDispatches> getIidUpdateUserList() {
        return iidUpdateUserList;
    }

    public void setIidUpdateUserList(List<InsideIncomeDispatches> iidUpdateUserList) {
        this.iidUpdateUserList = iidUpdateUserList;
    }

    public List<OutsideIncomeDispatches> getOidCreateUserList() {
        return oidCreateUserList;
    }

    public void setOidCreateUserList(List<OutsideIncomeDispatches> oidCreateUserList) {
        this.oidCreateUserList = oidCreateUserList;
    }

    public List<OutsideIncomeDispatches> getOidUpdateUserList() {
        return oidUpdateUserList;
    }

    public void setOidUpdateUserList(List<OutsideIncomeDispatches> oidUpdateUserList) {
        this.oidUpdateUserList = oidUpdateUserList;
    }

    public List<EventApplyForm> getEafCreateUserList() {
        return eafCreateUserList;
    }

    public void setEafCreateUserList(List<EventApplyForm> eafCreateUserList) {
        this.eafCreateUserList = eafCreateUserList;
    }

    public List<EventApplyForm> getEafUpdateUserList() {
        return eafUpdateUserList;
    }

    public void setEafUpdateUserList(List<EventApplyForm> eafUpdateUserList) {
        this.eafUpdateUserList = eafUpdateUserList;
    }

    public List<EventApplyForm> getEafLeaderUserList() {
        return eafLeaderUserList;
    }

    public void setEafLeaderUserList(List<EventApplyForm> eafLeaderUserList) {
        this.eafLeaderUserList = eafLeaderUserList;
    }

    public List<DepartmentAwards> getDaCreateUserList() {
        return daCreateUserList;
    }

    public void setDaCreateUserList(List<DepartmentAwards> daCreateUserList) {
        this.daCreateUserList = daCreateUserList;
    }

    public List<DepartmentAwards> getDaUpdateUserList() {
        return daUpdateUserList;
    }

    public void setDaUpdateUserList(List<DepartmentAwards> daUpdateUserList) {
        this.daUpdateUserList = daUpdateUserList;
    }

    public List<DepartmentAwards> getDaLeaderUserList() {
        return daLeaderUserList;
    }

    public void setDaLeaderUserList(List<DepartmentAwards> daLeaderUserList) {
        this.daLeaderUserList = daLeaderUserList;
    }

    public List<SmsRemindLogs> getSmsRemindLogsList() {
        return smsRemindLogsList;
    }

    public void setSmsRemindLogsList(List<SmsRemindLogs> smsRemindLogsList) {
        this.smsRemindLogsList = smsRemindLogsList;
    }

    public List<UserRes> getUserResList() {
        return userResList;
    }

    public void setUserResList(List<UserRes> userResList) {
        this.userResList = userResList;
    }

    public UserBase getUserBase() {
        return userBase;
    }

    public void setUserBase(UserBase userBase) {
        this.userBase = userBase;
    }

    public List<UserWork> getUserWorkList() {
        return userWorkList;
    }

    public void setUserWorkList(List<UserWork> userWorkList) {
        this.userWorkList = userWorkList;
    }

    public List<UserEducation> getUserEducationList() {
        return userEducationList;
    }

    public void setUserEducationList(List<UserEducation> userEducationList) {
        this.userEducationList = userEducationList;
    }

    public List<UserSchoolaward> getUserSchoolawardList() {
        return userSchoolawardList;
    }

    public void setUserSchoolawardList(List<UserSchoolaward> userSchoolawardList) {
        this.userSchoolawardList = userSchoolawardList;
    }

    public List<UserFamilyMember> getUserFamilyMemberList() {
        return userFamilyMemberList;
    }

    public void setUserFamilyMemberList(List<UserFamilyMember> userFamilyMemberList) {
        this.userFamilyMemberList = userFamilyMemberList;
    }

    public UserContract getUserContract() {
        return userContract;
    }

    public void setUserContract(UserContract userContract) {
        this.userContract = userContract;
    }

    public Boolean getArchive_status() {
        return archive_status;
    }

    public void setArchive_status(Boolean archive_status) {
        this.archive_status = archive_status;
    }

    public Integer getUser_status() {
        return user_status;
    }

    public void setUser_status(Integer user_status) {
        this.user_status = user_status;
    }

    public Boolean getIs_synchronous() {
        return is_synchronous;
    }

    public void setIs_synchronous(Boolean is_synchronous) {
        this.is_synchronous = is_synchronous;
    }

    public List<ContractSmsRemind> getContractSmsRemindList() {
        return contractSmsRemindList;
    }

    public void setContractSmsRemindList(List<ContractSmsRemind> contractSmsRemindList) {
        this.contractSmsRemindList = contractSmsRemindList;
    }

    public UserPhoto getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(UserPhoto userPhoto) {
        this.userPhoto = userPhoto;
    }

    public List<UserDimissionLogs> getUserDimissionLogsList() {
        return userDimissionLogsList;
    }

    public void setUserDimissionLogsList(List<UserDimissionLogs> userDimissionLogsList) {
        this.userDimissionLogsList = userDimissionLogsList;
    }

    public List<AttendanceRecord> getAttendanceRecordList() {
        return attendanceRecordList;
    }

    public void setAttendanceRecordList(List<AttendanceRecord> attendanceRecordList) {
        this.attendanceRecordList = attendanceRecordList;
    }

    public List<VacationApply> getVacationApplyList() {
        return vacationApplyList;
    }

    public void setVacationApplyList(List<VacationApply> vacationApplyList) {
        this.vacationApplyList = vacationApplyList;
    }

    public List<VacationApplyDetails> getVacationApplyDetailsList() {
        return vacationApplyDetailsList;
    }

    public void setVacationApplyDetailsList(List<VacationApplyDetails> vacationApplyDetailsList) {
        this.vacationApplyDetailsList = vacationApplyDetailsList;
    }

    public List<VacationApplyApproval> getVacationApplyApprovalList() {
        return vacationApplyApprovalList;
    }

    public void setVacationApplyApprovalList(List<VacationApplyApproval> vacationApplyApprovalList) {
        this.vacationApplyApprovalList = vacationApplyApprovalList;
    }

    public List<OvertimeRecord> getOvertimeRecordList() {
        return overtimeRecordList;
    }

    public void setOvertimeRecordList(List<OvertimeRecord> overtimeRecordList) {
        this.overtimeRecordList = overtimeRecordList;
    }

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public SubSection getSub_section() {
		return sub_section;
	}

	public void setSub_section(SubSection sub_section) {
		this.sub_section = sub_section;
	}
    
}