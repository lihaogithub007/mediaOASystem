package com.yuyu.soft.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

@Entity
@Table(name = "tb_user_base")
public class UserBase extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User              user;                       //用户

    private Integer           blood_type;                 //血型：1.A 2.B 3.O 4.AB 5.其他
    private Integer           constellation;              //星座
    private Integer           sex;                        //性别：1.男 2.女
    private Integer           age;                        //年龄
    private String            nation;                     //民族
    private String            native_place;               //籍贯
    private Integer           ID_type;                    //证件类型
    private String            ID_number;                  //证件号码
    private Integer           work_years;                 //工作年限
    private Integer           same_industry_work_years;   //同行业工作年限
    private Integer           political_status;           //政治面貌：1.中共党员 2.共青团员 3.群众 4.其他
    private Date              join_party_date;            //入党（团）时间
    private Date              first_work_date;            //第一次参加工作时间
    private Integer           marriage_status;            //婚姻状况：1.未婚 2.已婚 3.离异 4.丧偶
    private String            condition_of_children;      //婚姻状态：无子女填“无”，有子女填“几子几女”
    private String            highest_education;          //最高学历
    private Date              highest_education_gain_time; //最高学历获得时间
    private String            highest_offering;           //最高学位
    private Date              highest_offering_gain_time; //最高学位获得时间
    private String            foreign_language_level;     //外语种类及水平：请填写所考取最高级别的证书和成绩
    private String            computer_skill;             //计算机水平，填写等级考试名称和级别，无考级填写精通、熟练、一般
    private String            job_titles;                 //职称
    private String            job_titles_level;           //职称等级
    private Date              job_titles_evaluation_time; //职称评定时间
    private String            job_titles_evaluation_unit; //职称评定单位
    private String            job_titles_cert_id;         //职称证书号
    private Integer           is_have_press_card;         //是否有记者证：1.有 0.无
    private String            press_card_no;              //记者证号码
    private String            home_address;               //家庭地址（非租住地址，具体到门牌号）
    private String            home_postcode;              //家庭邮编
    private String            present_address;            //现地址（具体到门牌号）
    private String            present_postcode;           //现邮编
    private String            personnel_file_address;     //人事档案存放地
    private String            personnel_file_postcode;    //人事档案存放地邮编
    private String            domicile_place;             //户口所在地
    private Integer           domicile_type;              //户口性质：1.本市城镇 2.本市农村 3.外埠城镇 4.外埠农村
    private String            birth_place;                //出生地
    private String            emergency_contact_name;     //紧急联系人姓名
    private String            emergency_contact_mobile;   //紧急联系人手机号
    private String            hobby_and_speciality;       //爱好/特长（填写精通的特长和技能，到达可参赛的程度，如有成绩的请填写成绩）

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(Integer blood_type) {
        this.blood_type = blood_type;
    }

    public Integer getConstellation() {
        return constellation;
    }

    public void setConstellation(Integer constellation) {
        this.constellation = constellation;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNative_place() {
        return native_place;
    }

    public void setNative_place(String native_place) {
        this.native_place = native_place;
    }

    public Integer getID_type() {
        return ID_type;
    }

    public void setID_type(Integer iD_type) {
        ID_type = iD_type;
    }

    public String getID_number() {
        return ID_number;
    }

    public void setID_number(String iD_number) {
        ID_number = iD_number;
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

    public Integer getPolitical_status() {
        return political_status;
    }

    public void setPolitical_status(Integer political_status) {
        this.political_status = political_status;
    }

    public Date getJoin_party_date() {
        return join_party_date;
    }

    public void setJoin_party_date(Date join_party_date) {
        this.join_party_date = join_party_date;
    }

    public Date getFirst_work_date() {
        return first_work_date;
    }

    public void setFirst_work_date(Date first_work_date) {
        this.first_work_date = first_work_date;
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

    public String getHighest_education() {
        return highest_education;
    }

    public void setHighest_education(String highest_education) {
        this.highest_education = highest_education;
    }

    public Date getHighest_education_gain_time() {
        return highest_education_gain_time;
    }

    public void setHighest_education_gain_time(Date highest_education_gain_time) {
        this.highest_education_gain_time = highest_education_gain_time;
    }

    public String getHighest_offering() {
        return highest_offering;
    }

    public void setHighest_offering(String highest_offering) {
        this.highest_offering = highest_offering;
    }

    public Date getHighest_offering_gain_time() {
        return highest_offering_gain_time;
    }

    public void setHighest_offering_gain_time(Date highest_offering_gain_time) {
        this.highest_offering_gain_time = highest_offering_gain_time;
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

    public String getJob_titles_level() {
        return job_titles_level;
    }

    public void setJob_titles_level(String job_titles_level) {
        this.job_titles_level = job_titles_level;
    }

    public Date getJob_titles_evaluation_time() {
        return job_titles_evaluation_time;
    }

    public void setJob_titles_evaluation_time(Date job_titles_evaluation_time) {
        this.job_titles_evaluation_time = job_titles_evaluation_time;
    }

    public String getJob_titles_evaluation_unit() {
        return job_titles_evaluation_unit;
    }

    public void setJob_titles_evaluation_unit(String job_titles_evaluation_unit) {
        this.job_titles_evaluation_unit = job_titles_evaluation_unit;
    }

    public String getJob_titles_cert_id() {
        return job_titles_cert_id;
    }

    public void setJob_titles_cert_id(String job_titles_cert_id) {
        this.job_titles_cert_id = job_titles_cert_id;
    }

    public Integer getIs_have_press_card() {
        return is_have_press_card;
    }

    public void setIs_have_press_card(Integer is_have_press_card) {
        this.is_have_press_card = is_have_press_card;
    }

    public String getPress_card_no() {
        return press_card_no;
    }

    public void setPress_card_no(String press_card_no) {
        this.press_card_no = press_card_no;
    }

    public String getHome_address() {
        return home_address;
    }

    public void setHome_address(String home_address) {
        this.home_address = home_address;
    }

    public String getHome_postcode() {
        return home_postcode;
    }

    public void setHome_postcode(String home_postcode) {
        this.home_postcode = home_postcode;
    }

    public String getPresent_address() {
        return present_address;
    }

    public void setPresent_address(String present_address) {
        this.present_address = present_address;
    }

    public String getPresent_postcode() {
        return present_postcode;
    }

    public void setPresent_postcode(String present_postcode) {
        this.present_postcode = present_postcode;
    }

    public String getPersonnel_file_address() {
        return personnel_file_address;
    }

    public void setPersonnel_file_address(String personnel_file_address) {
        this.personnel_file_address = personnel_file_address;
    }

    public String getPersonnel_file_postcode() {
        return personnel_file_postcode;
    }

    public void setPersonnel_file_postcode(String personnel_file_postcode) {
        this.personnel_file_postcode = personnel_file_postcode;
    }

    public String getDomicile_place() {
        return domicile_place;
    }

    public void setDomicile_place(String domicile_place) {
        this.domicile_place = domicile_place;
    }

    public Integer getDomicile_type() {
        return domicile_type;
    }

    public void setDomicile_type(Integer domicile_type) {
        this.domicile_type = domicile_type;
    }

    public String getBirth_place() {
        return birth_place;
    }

    public void setBirth_place(String birth_place) {
        this.birth_place = birth_place;
    }

    public String getEmergency_contact_name() {
        return emergency_contact_name;
    }

    public void setEmergency_contact_name(String emergency_contact_name) {
        this.emergency_contact_name = emergency_contact_name;
    }

    public String getEmergency_contact_mobile() {
        return emergency_contact_mobile;
    }

    public void setEmergency_contact_mobile(String emergency_contact_mobile) {
        this.emergency_contact_mobile = emergency_contact_mobile;
    }

    public String getHobby_and_speciality() {
        return hobby_and_speciality;
    }

    public void setHobby_and_speciality(String hobby_and_speciality) {
        this.hobby_and_speciality = hobby_and_speciality;
    }

}
